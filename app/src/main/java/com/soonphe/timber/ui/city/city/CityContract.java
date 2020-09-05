package com.soonphe.timber.ui.city.city;

import com.soonphe.timber.entity.TCity;
import com.soonphe.timber.base.mvp.BasePView;

import java.util.List;


public class CityContract {
    interface View extends BasePView {
        void getCityListSuccess( List<TCity> list);
    }

    interface  Presenter  {
        void getCityList( );
    }
}
