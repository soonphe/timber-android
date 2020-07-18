package com.soonphe.timber.ui.book.bookread;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.base.mvp.BasePresenter;

import javax.inject.Inject;

@PerActivity
public class BookreadPresenter extends BasePresenter<BookreadContract.View> implements BookreadContract.Presenter{

    private AppApi api;

    @Inject
    public BookreadPresenter( AppApi accountApi) {
        this.api = accountApi;
    }

}
