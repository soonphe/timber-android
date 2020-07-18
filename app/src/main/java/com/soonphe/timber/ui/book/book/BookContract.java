package com.soonphe.timber.ui.book.book;

import com.soonphe.timber.view.entity.TAdvert;
import com.soonphe.timber.view.entity.TBook;
import com.soonphe.timber.view.entity.TBookType;
import com.soonphe.timber.base.mvp.BasePView;

import java.util.List;


public class BookContract {
    interface BookView extends BasePView {
        void getAdvertListSuccess(List<TAdvert> list );
        void getTypeListSuccess(List<TBookType> list );
        void getBookListSuccess( List<TBook> list);
    }

    interface BookPresenter {
        void getAdvertList( );
        void getTypeList( );
        void getBookList(int typeid );
    }
}
