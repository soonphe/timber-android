package com.soonphe.timber.ui.video.video;

import com.soonphe.timber.view.entity.TVideo;
import com.soonphe.timber.base.mvp.BasePView;

import java.util.List;


public class VideoContract {
    interface View extends BasePView {
        void getVideoListSuccess(List<TVideo> list);
    }

    interface  Presenter {
        void getVideoList( );
    }
}
