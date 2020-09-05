package com.soonphe.timber.ui.advert;

import com.soonphe.timber.entity.TAdvert;
import com.soonphe.timber.base.mvp.BasePView;

import java.util.List;


public class AdvertContract {
    public interface View extends BasePView {
        void getAdvertListSuccess( List<TAdvert> list);
    }

    public interface  Presenter {
        void getAdvertListByType(int typeid );
    }
}
