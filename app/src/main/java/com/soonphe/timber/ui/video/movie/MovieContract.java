package com.soonphe.timber.ui.video.movie;

import com.soonphe.timber.entity.TMovie;
import com.soonphe.timber.base.mvp.BasePView;

import java.util.List;


public class MovieContract {
    interface View extends BasePView {
        void getMovieListSuccess(List<TMovie> list);
    }

    interface  Presenter {
        void getMovieList();
    }
}
