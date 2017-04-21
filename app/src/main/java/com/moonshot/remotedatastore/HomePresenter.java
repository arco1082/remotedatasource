package com.moonshot.remotedatastore;

import android.support.annotation.NonNull;
import android.util.Log;

import com.moonshot.remotedatastore.data.models.Comment;
import com.moonshot.remotedatastore.data.models.IRemoteModel;
import com.moonshot.remotedatastore.data.models.RemoteModelCollection;
import com.moonshot.remotedatastore.rx.DefaultObserver;
import com.moonshot.remotedatastore.rx.SchedulerProvider;
import com.moonshot.remotedatastore.data.source.DataRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by armando_contreras on 4/21/17.
 */

final class HomePresenter implements HomeContract.Presenter {
    private static String TAG = HomePresenter.class.getSimpleName();

    private final DataRepository mDataRepository;
    private HomeContract.HomeView mHomeView;

    private boolean mFirstLoad = true;
    private final CompositeDisposable disposables = new CompositeDisposable();
    @NonNull
    private SchedulerProvider mSchedulerProvider;

    @Inject
    HomePresenter(DataRepository repository,
                          HomeContract.HomeView view,
                          @NonNull SchedulerProvider schedulerProvider) {
        mDataRepository = repository;
        mHomeView = view;
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");
        mHomeView.setPresenter(this);
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void subscribe() {
        //loadTasks(false);
    }

    @Override
    public void unsubscribe() {
        this.mHomeView = null;
        disposables.clear();
    }

    @Inject
    void setupListeners() {
        mHomeView.setPresenter(this);
    }

    @Override
    public void loadItems() {
        mHomeView.setLoadingIndicator(true);

        disposables.clear();


        final Observable<RemoteModelCollection<Comment>> observable = mDataRepository
                .loadItems()
                // Run on a background thread
                .subscribeOn(mSchedulerProvider.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread());

        disposables.add(observable.subscribeWith(new FollowerListObserver()));
    }

    private void processCollection(RemoteModelCollection<Comment> items) {
        if (items.isEmpty()) {
            processEmptyCollection();
        } else {
            List<Comment> comments = (List<Comment>) items.getItems();
            Log.e(TAG, "processCollection" + items.getItems().size());
            mHomeView.showModelList( items.getItems());
        }
    }

    @Override
    public void onError() {
        mHomeView.showError();
    }

    @Override
    public void saveComment(Comment comment) {
        mDataRepository.saveComment(comment);
    }

    private void processEmptyCollection() {
        mHomeView.showNoItems();
    }

    private final class FollowerListObserver extends DefaultObserver<RemoteModelCollection<Comment>> {

        @Override public void onComplete() {
            Log.d(TAG, "onNext");
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, "onError", e);
            HomePresenter.this.onError();
        }

        @Override public void onNext(RemoteModelCollection<Comment> collection) {
            Log.d(TAG, "onNext");
            HomePresenter.this.processCollection(collection);
        }
    }
}