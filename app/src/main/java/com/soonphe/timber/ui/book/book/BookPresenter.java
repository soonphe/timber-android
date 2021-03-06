package com.soonphe.timber.ui.book.book;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.entity.TAdvert;
import com.soonphe.timber.entity.TBook;
import com.soonphe.timber.entity.TBookType;
import com.soonphe.timber.base.mvp.BasePresenter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class BookPresenter extends BasePresenter<BookContract.BookView> implements BookContract.BookPresenter {


    private AppApi api;

    @Inject
    public BookPresenter(AppApi accountApi) {
        this.api = accountApi;
    }


    @Override
    public void getAdvertList() {
        //获取游戏广告
        List<TAdvert> list = LitePal
                .where("typeid = ?", "6")
                .find(TAdvert.class);
        if (list.size() > 0) {
            mView.getAdvertListSuccess(list);
        } else {
            mView.getAdvertListSuccess(new ArrayList<>());
        }
    }

    @Override
    public void getTypeList() {
        //获取类型列表
        List<TBookType> list = LitePal.findAll(TBookType.class);
        if (list.size() > 0) {
            mView.getTypeListSuccess(list);
        } else {
            mView.getTypeListSuccess(new ArrayList<>());
        }
    }

    @Override
    public void getBookList(int typeid) {
        //获取书籍列表
        List<TBook> list = LitePal.where("typeid=?", typeid+"").find(TBook.class);
        if (list.size() > 0) {
            mView.getBookListSuccess(list);
        } else {
            mView.getBookListSuccess(new ArrayList<>());
        }
    }
}
