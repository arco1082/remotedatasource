package com.moonshot.remotedatastore;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moonshot.remotedatastore.data.models.Comment;
import com.moonshot.remotedatastore.data.models.IRemoteModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment implements HomeContract.HomeView {
    private HomeContract.Presenter mPresenter;

    public final static String TAG = HomeActivityFragment.class.getSimpleName();
    private ItemsAdapter mAdapter;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    public static HomeActivityFragment createNew() {
        HomeActivityFragment fragment = new HomeActivityFragment();
        return fragment;
    }

    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, fragmentView);

        GridLayoutManager lLayout = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(lLayout);

        mRecyclerView.setLayoutManager(lLayout);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ItemsAdapter(getActivity(), R.layout.comment_row);
        mRecyclerView.setAdapter(mAdapter);

        return fragmentView;
    }


    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        mPresenter.loadItems();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        ButterKnife.unbind(this);
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showModelList(List<Comment> items) {

    }

    @Override
    public void showError() {

    }

    @Override
    public boolean isActive() {
        return false;
    }


    @Override
    public void showNoItems() {

    }

    private static class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

        public final static String TAG = ItemsAdapter.class.getSimpleName();

        private Context mContext;
        private List<Comment> mItems = new ArrayList<>();
        private int mRowLayout;
        private int lastPosition = -1;

        public interface OnItemClickListener {
            void onItemClick(View view, Comment comment, int position);
        }

        public ItemsAdapter(Context context, int rowLayout) {
            this.mContext = context;
            this.mRowLayout = rowLayout;
        }

        public void swapList(List<Comment> newList) {
            mItems.clear();
            if (newList != null) {
                mItems.addAll(newList);
            }
            this.notifyDataSetChanged();
        }

        @Override
        public ItemsAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(mRowLayout, parent, false);
            return new ItemsAdapter.ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ItemViewHolder holder, final int position) {
            holder.titleView.setText(mItems.get(position).getComment());

        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {

            TextView titleView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                titleView = (TextView) itemView.findViewById(R.id.model_title);
            }

        }
    }
}
