package com.soonphe.timber.ui.video.video;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.entity.TVideo;
import com.soonphe.timber.base.mvp.BasePresenter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class VideoPresenter extends BasePresenter<VideoContract.View> implements VideoContract.Presenter {

    private AppApi api;

    @Inject
    public VideoPresenter(AppApi api) {
        this.api = api;
    }

    @Override
    public void getVideoList() {

        List<TVideo> list = LitePal.findAll(TVideo.class);
        if (list.size() > 0) {
            mView.getVideoListSuccess(list);
        } else {
            mView.getVideoListSuccess(new ArrayList<>());
        }


    }
}
