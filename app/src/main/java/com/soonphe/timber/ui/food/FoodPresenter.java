package com.soonphe.timber.ui.food;

import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.view.entity.TFood;
import com.soonphe.timber.view.entity.TFoodType;
import com.soonphe.timber.base.mvp.BasePresenter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class FoodPresenter extends BasePresenter<FoodContract.View> implements FoodContract.Presenter {

    private AppApi api;

    @Inject
    public FoodPresenter(AppApi accountApi) {
        this.api = accountApi;
    }

    @Override
    public void getTypeList() {
        //获取类型列表
        List<TFoodType> list = LitePal.findAll(TFoodType.class);
        if (list.size() > 0) {
            mView.getTypeListSuccess(list);
        } else {
            mView.getTypeListSuccess(new ArrayList<>());
        }
    }

    @Override
    public void getFoodList(int typeid) {
        //获取列表
        List<TFood> list = LitePal.where("typeid = ?", typeid + "").find(TFood.class);
        if (list.size() > 0) {
            mView.getFoodListSuccess(list);
        } else {
            mView.getFoodListSuccess(new ArrayList<>());
        }
    }
}
