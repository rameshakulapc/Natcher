package com.newfivefour.natcher.screens.postsrecent;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.newfivefour.natcher.R;
import com.newfivefour.natcher.services.PostsRecentService;
import com.newfivefour.natcher.uicomponent.Populatable;
import com.newfivefour.natcher.uicomponent.UiComponentDelegate;
import com.newfivefour.natcher.uicomponent.UiComponentVanilla;
import com.newfivefour.natcher.uicomponent.widgets.LoadingErrorEmptyWidget;
import com.newfivefour.natcher.uicomponent.widgets.SwipeToRefreshWidget;
import com.newfivefour.natcher.uicomponent.widgets.TextViewServerErrorWidget;

public class PostsRecentView extends FrameLayout implements
        UiComponentDelegate<PostsRecentService.RecentPosts>,
        Populatable<PostsRecentService.RecentPosts> {

    private UiComponentVanilla<PostsRecentService.RecentPosts> mUIComponent;
    private ListView mListView;

    public PostsRecentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View rootView = LayoutInflater.from(context).inflate(R.layout.posts_list_view, this, true);
        mListView = (ListView) rootView.findViewById(R.id.listView);

        // Setup the swipe view
        SwipeToRefreshWidget swipe = new SwipeToRefreshWidget((SwipeRefreshLayout) findViewById(R.id.swipe));

        // Setup ui component
        LoadingErrorEmptyWidget loadingErrorEmptyWidget = new LoadingErrorEmptyWidget(this, -1, R.layout.error_container, R.layout.empty_container);
        setupComponent(swipe, loadingErrorEmptyWidget);
    }

    @SuppressWarnings("unused")
    public PostsRecentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    public UiComponentVanilla<PostsRecentService.RecentPosts> getUiComponentDelegate() {
        return mUIComponent;
    }

    @Override
    public void populateOnSuccessfulResponse(PostsRecentService.RecentPosts ob) {
        Parcelable listInstance = mListView.onSaveInstanceState();
        PostsRecentArrayAdapter adapter = new PostsRecentArrayAdapter(getContext(), R.layout.posts_list_item, ob.getPosts());
        mListView.setAdapter(adapter);
        if(listInstance!=null) {
            mListView.onRestoreInstanceState(listInstance);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearContentOnEmptyResponse() {
        mListView.setAdapter(null);
    }

    @Override
    public boolean showInComponentLoading() {
        return mListView.getAdapter() == null || mListView.getAdapter().getCount() == 0;
    }

    @Override
    public boolean showOutOfComponentLoading() {
        return true;
    }

    @Override
    public boolean showInComponentServerError() {
        return mListView.getAdapter() == null || mListView.getAdapter().getCount() == 0;
    }

    @Override
    public boolean showOutOfComponentServerError() {
        return true;
    }

    private void setupComponent(SwipeToRefreshWidget swipe, LoadingErrorEmptyWidget loadingErrorEmptyWidget) {
        mUIComponent = new UiComponentVanilla<>(this);
        mUIComponent
                .setInComponentLoadingDisplay(swipe)
                .setOutOfComponentLoadingDisplay(swipe)
                .setEmptyDisplay(loadingErrorEmptyWidget)
                .setInComponentServerErrorDisplay(loadingErrorEmptyWidget)
                .setOutOfComponentServerErrorDisplay(new TextViewServerErrorWidget(getContext().getApplicationContext()))
                .setRefreshWidget(swipe);
    }

}
