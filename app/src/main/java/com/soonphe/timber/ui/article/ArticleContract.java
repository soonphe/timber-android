package com.soonphe.timber.ui.article;

import com.soonphe.timber.entity.TArticle;
import com.soonphe.timber.entity.TArticleType;
import com.soonphe.timber.base.mvp.BasePView;

import java.util.List;


public class ArticleContract {
    interface View extends BasePView {
        void getTypeListSuccess(List<TArticleType> list );
        void getArticleListSuccess( List<TArticle> list);
    }

    interface  Presenter  {
        void getTypeList( );
        void getArticleList(int typeid );
    }
}
