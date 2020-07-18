package com.soonphe.timber.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soonphe.timber.R;
import com.soonphe.timber.view.entity.TFood;
import com.soonphe.timber.view.utils.GlideUtils;

import java.util.List;


/**
 * @Author soonphe
 * @Date 2018-08-28 14:02
 * @Description 餐饮适配器
 */
public class FoodAdapter extends BaseQuickAdapter<TFood, BaseViewHolder> {

    public FoodAdapter(int layoutResId) {
        super(layoutResId);
    }

    public FoodAdapter(int layoutResId, List<TFood> list) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TFood item) {

        GlideUtils.loadImageView(mContext, item.getDownloadPic(),  helper.getView(R.id.foodImage));
        helper.setText(R.id.foodName,item.getName());
        helper.setText(R.id.foodDesc, item.getDescription());
        helper.setText(R.id.foodPrice, String.valueOf(item.getPrice()));
    }
}
