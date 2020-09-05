package com.soonphe.timber.ui.article;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.entity.TArticle;
import com.soonphe.timber.entity.TArticleType;
import com.soonphe.timber.base.mvp.BasePresenter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class ArticlePresenter extends BasePresenter<ArticleContract.View> implements ArticleContract.Presenter{


    private AppApi api;

    @Inject
    public ArticlePresenter(AppApi accountApi) {
        this.api = accountApi;
    }

    @Override
    public void getTypeList() {
        //获取类型列表
        List<TArticleType> list = LitePal.findAll(TArticleType.class);
        if (list.size() > 0) {
            mView.getTypeListSuccess(list);
        } else {
            mView.getTypeListSuccess(new ArrayList<>());
        }
    }

    @Override
    public void getArticleList(int typeid) {

        //获取城市文章列表
        List<TArticle> list = LitePal.where("typeid=?", typeid+"").find(TArticle.class);
        if (list.size() > 0) {
            mView.getArticleListSuccess(list);
        } else {
            mView.getArticleListSuccess(new ArrayList<>());
        }
    }
}
