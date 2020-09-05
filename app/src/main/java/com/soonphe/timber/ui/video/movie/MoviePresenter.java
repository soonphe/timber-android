package com.soonphe.timber.ui.video.movie;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.entity.TMovie;
import com.soonphe.timber.base.mvp.BasePresenter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class MoviePresenter extends BasePresenter<MovieContract.View> implements MovieContract.Presenter{

    private AppApi api;

    @Inject
    public MoviePresenter(AppApi api) {
        this.api = api;
    }

    @Override
    public void getMovieList( ) {

        List<TMovie> list = LitePal.findAll(TMovie.class);
        if (list.size() > 0) {
            mView.getMovieListSuccess(list);
        } else {
            mView.getMovieListSuccess(new ArrayList<>());
        }
    }
}
