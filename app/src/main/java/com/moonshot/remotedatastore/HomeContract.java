package com.moonshot.remotedatastore;

import com.moonshot.remotedatastore.data.models.Comment;
import com.moonshot.remotedatastore.data.models.IRemoteModel;

import java.util.List;

/**
 * Created by armando_contreras on 4/21/17.
 */
public interface HomeContract {

    interface HomeView extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showModelList(List<Comment> items);

        void showNoItems();

        void showError();

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void loadItems();

        void onError();

        void saveComment(Comment comment);
    }
}