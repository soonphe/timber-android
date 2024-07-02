package com.soonphe.timber.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soonphe.timber.R;
import com.soonphe.timber.entity.PMenu;

import java.util.List;

/**
 * 设置页菜单Adapter（仅文字）
 *
 * @author soonphe
 * @since 1.0
 */
public class MenuAdapter extends BaseQuickAdapter<PMenu, BaseViewHolder> {

    /**
     * 创建adapter
     *
     * @param layoutResId 布局
     */
    public MenuAdapter(int layoutResId) {
        super(layoutResId);
    }

    public MenuAdapter(int layoutResId, List<PMenu> cards) {
        super(layoutResId, cards);
    }

    @Override
    protected void convert(BaseViewHolder helper, PMenu item) {
        if (item.getMenuId() == 0) {
            helper.setBackgroundRes(R.id.item_menu,R.color.text_red);
//            helper.setBackgroundColor(R.id.item_menu, Color.parseColor("#ff0000"));
        }
        helper.setText(R.id.menu_content, item.getName());
    }

}