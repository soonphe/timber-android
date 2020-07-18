package com.soonphe.timber.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soonphe.timber.view.R;
import com.soonphe.timber.view.entity.MenuBarItem;

import java.util.List;


/**
 * @Author soonphe
 * @Date 2017-11-22 15:10
 * @Descprition 首页菜单GridViewAdapter
 */

public class MenuGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<MenuBarItem> mDatas;
    private int mIndex; //页数下标,从0开始
    private int mPageSize;  //每页显示最大条目个数 ,默认是dimes.xml里 HomePageHeaderColumn 属性值的两倍


    public MenuGridViewAdapter(Context context, List<MenuBarItem> mDatas, int mIndex, int mPargerSize) {
        this.context = context;
        this.mDatas = mDatas;
        this.mIndex = mIndex;
        this.mPageSize = mPargerSize;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (mIndex+1)*mPageSize,
     * 如果够，则直接返回每一页显示的最大条目个数mPageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - mIndex * mPageSize);
     */
    @Override
    public int getCount() {
        return mDatas.size() > (mIndex + 1) * mPageSize ?
                mPageSize : (mDatas.size() - mIndex * mPageSize);

    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + mIndex * mPageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + mIndex * mPageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
//            convertView = mLayoutInflater.inflate(R.layout.item_menu_item, parent, false);
            vh = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_menu_item,null);
            vh.tv = (TextView) convertView.findViewById(R.id.tv_type);
            vh.iv = (ImageView) convertView.findViewById(R.id.iv_type);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + mIndex * mPageSize，
         */
        int pos = position + mIndex * mPageSize;
        vh.tv.setText(String.valueOf(mDatas.get(pos).getName()));
        Glide.with(context)
                .load(mDatas.get(pos).getPicUrl())
                .placeholder(R.mipmap.homecare)
                .error(R.mipmap.homecare)
                .into(vh.iv);
        return convertView;
    }


    class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }
}
