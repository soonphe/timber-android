package com.soonphe.timber.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soonphe.timber.R;
import com.soonphe.timber.entity.TVideo;
import com.soonphe.timber.utils.GlideUtils;

import java.util.List;


/**
 * @Author soonphe
 * @Date 2018-08-24 11:48
 * @Description 视频适配器
 */
public class VideoAdapter extends BaseQuickAdapter<TVideo, BaseViewHolder> {

    public VideoAdapter(int layoutResId) {
        super(layoutResId);
    }

    public VideoAdapter(int layoutResId, List<TVideo> list) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TVideo tVideo) {
        GlideUtils.loadImageView(mContext, tVideo.getDownloadPic(), helper.getView(R.id.movie_icon));//设置电影封面
        helper.setText(R.id.movie_title,tVideo.getName());//设置电影名称

    }
}
