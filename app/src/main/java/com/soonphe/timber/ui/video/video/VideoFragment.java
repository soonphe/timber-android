package com.soonphe.timber.ui.video.video;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.soonphe.timber.R;
import com.soonphe.timber.base.BaseFragmentV4;
import com.soonphe.timber.entity.TVideo;
import com.soonphe.timber.adapter.VideoAdapter;
import com.soonphe.timber.ui.videoplayer.VideoPlayerActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * @Author soonphe
 * @Date 2018-08-21 19:26
 * @Description 电影-小视频
 */
public class VideoFragment extends BaseFragmentV4 implements VideoContract.View {

    @Inject
    VideoPresenter presenter;

    @BindView(R.id.video_recycler)
    RecyclerView videoRecycler;
    @BindView(R.id.video_refresh)
    SmartRefreshLayout videoRefresh;

    private VideoAdapter videoAdapter;

    @Override
    public void startLoading() {

    }

    @Override
    public void endLoading() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_video;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        presenter.attachView(this);

        videoAdapter = new VideoAdapter(R.layout.item_video);
        videoRecycler.setLayoutManager(new GridLayoutManager(getContext(), 4));
        videoRecycler.addItemDecoration(new SpaceDecoration(10));
        videoRecycler.setAdapter(videoAdapter);
        videoRecycler.setNestedScrollingEnabled(false);

        videoAdapter.setOnItemClickListener((adapter, view1, position) -> {
            mOperation.addParameter("video", ((TVideo)adapter.getItem(position)).getId());
            mOperation.forward(VideoPlayerActivity.class);
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.getVideoList();

    }

    @Override
    public void lazyInitBusiness(Context mContext) {

    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
    }

    @Override
    public void getVideoListSuccess(List<TVideo> list) {
        if (list.size()>0){
            videoAdapter.setNewData(list);
        }
    }

}
