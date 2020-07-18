package com.soonphe.timber.ui.food;

import com.soonphe.timber.view.entity.TFood;
import com.soonphe.timber.view.entity.TFoodType;
import com.soonphe.timber.base.mvp.BasePView;

import java.util.List;

public class FoodContract {
    interface View extends BasePView {
        void getTypeListSuccess(List<TFoodType> list );
        void getFoodListSuccess( List<TFood> list);
    }

    interface  Presenter  {
        void getTypeList( );
        void getFoodList(int typeid );
    }
}
