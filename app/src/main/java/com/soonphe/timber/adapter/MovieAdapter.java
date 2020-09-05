package com.soonphe.timber.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soonphe.timber.R;
import com.soonphe.timber.entity.TMovie;
import com.soonphe.timber.utils.GlideUtils;

import java.util.List;


/**
 * @Author soonphe
 * @Date 2018-08-24 11:48
 * @Description 电影适配器
 */
public class MovieAdapter extends BaseQuickAdapter<TMovie, BaseViewHolder> {

    public MovieAdapter(int layoutResId) {
        super(layoutResId);
    }

    public MovieAdapter(int layoutResId, List<TMovie> list) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TMovie tVideo) {
        GlideUtils.loadImageView(mContext, tVideo.getDownloadPic(), helper.getView(R.id.movie_icon));//设置电影封面
        helper.setText(R.id.movie_title,tVideo.getFile_name());//设置电影名称

    }
}
