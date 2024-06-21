package com.soonphe.timber.view.widget.titlebar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonphe.timber.view.R;

import androidx.annotation.StringRes;

/**
 * 自定义title——左部返回，中间文件/图片，右部文字/图片
 *
 * @author soonphe
 * @since 1.0
 */
public class AppTitle extends RelativeLayout implements View.OnClickListener, View.OnLongClickListener {

    private Context mContext;
    private ImageButton appBarBack;
    private TextView appBarTitle;
    private ImageButton appBarCenterBtn;
    private ImageButton appBarRightBtn;
    private TextView appBarRightTv;
    private LinearLayout titleRoot;
    private boolean canFinish = true;
    //自定义状态栏
    //    private CustomStatusBar statusLine;

    /**
     * 在xml布局文件中使用时自动调用
     *
     * @param context
     * @param attrs
     */
    public AppTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
        initAttrs(context, attrs);

    }

    /**
     * 初始化view
     *
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_app_bar, this);

        titleRoot = (LinearLayout) findViewById(R.id.title_root);
        appBarBack = (ImageButton) findViewById(R.id.app_bar_back);
        appBarTitle = (TextView) findViewById(R.id.app_bar_title);
        appBarCenterBtn = (ImageButton) findViewById(R.id.app_bar_center_btn);
        appBarRightBtn = (ImageButton) findViewById(R.id.app_bar_right_btn);
        appBarRightTv = (TextView) findViewById(R.id.app_bar_right_tv);

        appBarBack.setOnClickListener(this);
        appBarTitle.setOnClickListener(this);
        appBarCenterBtn.setOnClickListener(this);
        appBarRightBtn.setOnClickListener(this);
        appBarRightTv.setOnClickListener(this);

        appBarBack.setOnLongClickListener(this);
        appBarTitle.setOnLongClickListener(this);
        appBarCenterBtn.setOnLongClickListener(this);
        appBarRightBtn.setOnLongClickListener(this);
        appBarRightTv.setOnLongClickListener(this);
    }

    /**
     * 初始化自定义状态栏，已废弃
     * @param color
     */
    @Deprecated
    public void initStatusBar(int color) {
//        statusLine = findViewById(R.id.status_bar);

        //获取屏幕分辨率（Math.ceil向上取整）
        //高度heightPixel，宽度widthPixel，密度density
        //dpi       ：dots per inch ， 直接来说就是一英寸多少个像素点。常见取值 120，160，240，320。也称作像素密度，简称密度
        //density ： 直接翻译的话貌似叫 密度。常见取值 1.5 ， 1.0 。和标准dpi的比例（160px/inc）
        double statusBarHeight = Math.ceil(25 * mContext.getResources().getDisplayMetrics().density);
        Log.e("","屏幕大小" + mContext.getResources().getDisplayMetrics() + "屏幕密度" + mContext.getResources().getDisplayMetrics().density);

        //判断状态栏高度 默认48
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //dp转px
            int result = this.getResources().getDimensionPixelSize(resourceId);
            Log.e("","屏幕高度大于0");
//            statusLine.setVisibility(View.VISIBLE);
        } else {
            Log.e("","未获取到的屏幕高度");
//            statusLine.setVisibility(View.GONE);
//        设置状态栏透明
//            BarUtils.setStatusBarColor((Activity) mContext);
        }
    }

    /**
     * 初始化属性
     * @param context
     * @param attrs
     */
    public void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppTitle);
        //左部返回按钮:获取属性值，判断默认值，再判断是否显示
        appBarBack.setVisibility(typedArray.getBoolean(R.styleable.AppTitle_showBack, typedArray.getResources().getBoolean(R.bool.default_showBack)) ? VISIBLE : INVISIBLE);
        //是否可以返回
        canFinish = typedArray.getBoolean(R.styleable.AppTitle_canFinish, true);

        //中间标题：获取属性值，设置文字，颜色默认白色，大小默认18sp
        String titleText = typedArray.getString(R.styleable.AppTitle_centreText);
        if (titleText != null && titleText.length() > 0) {
            appBarTitle.setVisibility(VISIBLE);
            appBarTitle.setText(titleText);
            appBarTitle.setTextColor(typedArray.getColor(R.styleable.AppTitle_centreTextColor, typedArray.getResources().getColor(R.color.default_textColor)));
            appBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.AppTitle_centreTextSize, typedArray.getResources().getDimensionPixelSize(R.dimen.default_textSize)));
        }
        //中间按钮：获取属性值，设置图片
        int centerButtonRes = typedArray.getResourceId(R.styleable.AppTitle_centerButtonImgSrc, 0);
        if (centerButtonRes > 0) {
            appBarCenterBtn.setVisibility(VISIBLE);
            appBarCenterBtn.setImageResource(centerButtonRes);
        }
        //右侧按钮获取属性值，设置图片
        int rightButtonRes = typedArray.getResourceId(R.styleable.AppTitle_rightButtonImgSrc, 0);
        if (rightButtonRes > 0) {
            appBarRightBtn.setVisibility(VISIBLE);
            appBarRightBtn.setImageResource(rightButtonRes);
        }
        //右侧文字：获取属性值，设置文字，颜色默认白色，大小默认18sp
        String rightText = typedArray.getString(R.styleable.AppTitle_rightText);
        if (rightText != null && rightText.length() > 0) {
            appBarRightTv.setVisibility(VISIBLE);
            appBarRightTv.setText(typedArray.getString(R.styleable.AppTitle_rightText));
            appBarTitle.setTextColor(typedArray.getColor(R.styleable.AppTitle_rightTextColor, typedArray.getResources().getColor(R.color.default_textColor)));
            appBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.AppTitle_rightTextSize, typedArray.getResources().getDimensionPixelSize(R.dimen.default_textSize)));
        }
        //回收TypedArray，供以后的调用程序重新使用。调用此函数后，您不能再触摸已键入的数组。
        typedArray.recycle();
        if (appBarCenterBtn.isShown() || appBarRightBtn.isShown()) {
            appBarTitle.setMaxWidth(dp2px(120));
        } else if (appBarCenterBtn.isShown() && appBarRightBtn.isShown()) {
            appBarTitle.setMaxWidth(dp2px(150));
        } else {
            appBarTitle.setMaxWidth(dp2px(200));
        }
    }

    /**
     * dp转px
     *
     * @param dpValue
     * @return
     */
    public static int dp2px(float dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    /**
     * 设置是否可以关闭
     * @param canFinish
     * @return
     */
    public AppTitle setCanFinish(boolean canFinish) {
        this.canFinish = canFinish;
        return this;
    }

    /**
     * 设置标题
     * @param title
     * @return
     */
    public AppTitle setTitle(String title) {
        if (!appBarTitle.isShown()) {
            appBarTitle.setVisibility(VISIBLE);
        }
        appBarTitle.setText(title);
        return this;
    }

    /**
     * 设置标题，resId
     * @param titleResId
     * @return
     */
    public AppTitle setTitle(@StringRes int titleResId) {
        if (!appBarTitle.isShown()) {
            appBarTitle.setVisibility(VISIBLE);
        }
        appBarTitle.setText(getResources().getString(titleResId));
        return this;
    }

    /**
     * 设置右侧文字
     * @param rightText
     * @return
     */
    public AppTitle setRightText(String rightText) {
        if (!appBarRightTv.isShown()) {
            appBarRightTv.setVisibility(VISIBLE);
        }
        appBarRightTv.setText(rightText);
        return this;
    }

    /**
     * 设置右侧文章，resId
     * @param rightText
     * @return
     */
    public AppTitle setRightText(@StringRes int rightText) {
        if (!appBarRightTv.isShown()) {
            appBarRightTv.setVisibility(VISIBLE);
        }
        appBarRightTv.setText(getResources().getString(rightText));
        return this;
    }

    /**
     * 设置中间按钮图片
     * @param resId
     * @return
     */
    public AppTitle setCenterBtnSrc(int resId) {
        if (!appBarCenterBtn.isShown()) {
            appBarCenterBtn.setVisibility(VISIBLE);
        }
        appBarCenterBtn.setImageResource(resId);
        return this;
    }

    /**
     * 设置右侧按钮图片
     * @param resId
     * @return
     */
    public AppTitle setRightBtnSrc(int resId) {
        if (!appBarRightBtn.isShown()) {
            appBarRightBtn.setVisibility(VISIBLE);
        }
        appBarRightBtn.setImageResource(resId);
        return this;
    }

    @Override
    public void onClick(View view){
        if (view.getId() == R.id.app_bar_back) {
            Log.d("AppTitle","app_bar_back onClick: " + canFinish);
            if (canFinish) {
                try {
                    ((Activity) getContext()).finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (view.getId() == R.id.app_bar_center_btn) {
            Log.d("AppTitle","app_bar_center_btn onClick: ");
        } else if (view.getId() == R.id.app_bar_title) {
            Log.d("AppTitle","app_bar_title onClick: ");
        } else if (view.getId() == R.id.app_bar_right_tv) {
            Log.d("AppTitle","app_bar_right_tv onClick: ");
            try {
                ((Activity) getContext()).getWindow().getDecorView().invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public ImageButton getAppBarBack() {
        return appBarBack;
    }

    public TextView getAppBarTitle() {
        return appBarTitle;
    }

    public ImageButton getAppBarCenterBtn() {
        return appBarCenterBtn;
    }

    public ImageButton getAppBarRightBtn() {
        return appBarRightBtn;
    }

    public TextView getAppBarRightTv() {
        return appBarRightTv;
    }

    public LinearLayout getTitleRoot() {
        return titleRoot;
    }

    public boolean isCanFinish() {
        return canFinish;
    }

//    public CustomStatusBar getStatusLine() {
//        return statusLine;
//    }
}
