package com.newfivefour.natcher.screens.recentposts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.newfivefour.natcher.app.WindowLoadingSpinner;
import com.newfivefour.natcher.app.component.LoadingComponent;
import com.newfivefour.natcher.R;
import com.newfivefour.natcher.services.PostsRecentService;

public class NatcherFragment extends android.app.Fragment
    implements LoadingComponent {

    private NatcherPresenter mPresenter;
    private RecentPostsView mRecentPostsView;
    private WindowLoadingSpinner mWindowLoadingSpinnerDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new NatcherPresenter(this);
        mWindowLoadingSpinnerDelegate = new WindowLoadingSpinner(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter = null;
        mWindowLoadingSpinnerDelegate = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.natcher_fragment, container, false);
        mRecentPostsView = (RecentPostsView) v.findViewById(R.id.natcher_listview);
        mRecentPostsView.setOverallPageLoadingConnector(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    public void setRecentPosts(PostsRecentService.RecentPosts recentPosts) {
        mRecentPostsView.populateFromServer(recentPosts);
    }

    public void setRecentPostsFromCache(PostsRecentService.RecentPosts recentPosts) {
        mRecentPostsView.populateFromCache(recentPosts);
    }

    public void setRecentPostsError(String s) {
        mRecentPostsView.populateFromServerError();
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadingStart() {
        mWindowLoadingSpinnerDelegate.loadingStart();
    }

    @Override
    public void loadingStop() {
        mWindowLoadingSpinnerDelegate.loadingStop();
    }

}