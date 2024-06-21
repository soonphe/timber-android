package com.soonphe.timber.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soonphe.timber.R;
import com.soonphe.timber.entity.PGoods;
import com.soonphe.timber.entity.TAdvert;

import java.util.List;

import static com.soonphe.timber.constants.Constants.BASE_IMAGE_URL;

/**
 * @Author anna
 * @Date 2017-11-22 13:52
 * @Descprition 首页热门收藏HotRecycleViewAdapter
 */

public class HomeHotAdapter extends BaseQuickAdapter<TAdvert, BaseViewHolder> {

    /**
     * 创建adapter
     * @param layoutResId 布局
     */
    public HomeHotAdapter(int layoutResId) {
        super(layoutResId);
    }

    public HomeHotAdapter(int layoutResId, List<TAdvert> list) {
        super(layoutResId,list);
    }

    @Override
    protected void convert(BaseViewHolder helper, TAdvert item) {
        Glide.with(mContext).load(BASE_IMAGE_URL+item.getPicUrl().split(",")[0]).into((ImageView) helper.getView(R.id.iv_hot_pic));
        helper.setText(R.id.tv_name,item.getTitle()).
                setText(R.id.tv_price, item.getContent()+"").
                setText(R.id.tv_buyNum,item.getId()+"人已购");

    }


}
