package com.soonphe.timber.view.widget.titlebar;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.soonphe.timber.view.R;
import com.soonphe.timber.view.utils.ResourceUtils;

import androidx.annotation.StringRes;

/**
 * @Author soonphe
 * @Date 2018-08-30 10:41
 * @Description 自定义title——左部返回，中间文件/图片，右部文字/图片
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
//        statusLine = findViewById(R.id.status_bar);
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

        //获取屏幕分辨率（Math.ceil向上取整）
        //高度heightPixel，宽度widthPixel，密度density
        //dpi       ：dots per inch ， 直接来说就是一英寸多少个像素点。常见取值 120，160，240，320。也称作像素密度，简称密度
        //density ： 直接翻译的话貌似叫 密度。常见取值 1.5 ， 1.0 。和标准dpi的比例（160px/inc）
//        double statusBarHeight = Math.ceil(25 * mContext.getResources().getDisplayMetrics().density);
//        LogUtils.e("屏幕大小" + mContext.getResources().getDisplayMetrics()
//                + "屏幕密度" + mContext.getResources().getDisplayMetrics().density);

        //判断状态栏高度 默认48
//        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
        //dp转px
//            result = context.getResources().getDimensionPixelSize(resourceId);
//            LogUtils.e("屏幕高度大于0");
//            statusLine.setVisibility(View.VISIBLE);
//        } else {
//            LogUtils.e("未获取到的屏幕高度");
//            statusLine.setVisibility(View.GONE);
        //设置状态栏透明
//            BarUtils.setStatusBarAlpha((Activity) mContext);
//        }
    }

    public void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppTitle);
        Boolean titleBack = typedArray.getBoolean( R.styleable.AppTitle_showBack,false );
//        Boolean titleBack = typedArray.getBoolean( R.styleable.AppTitle_showBack,typedArray.getResources().getBoolean(R.bool.default_showBack) );
        if (titleBack) {
            appBarBack.setVisibility(VISIBLE);
        }
//        appBarBack.setVisibility(ResourceUtils.getBoolean(typedArray, R.styleable.AppTitle_showBack, R.bool.default_showBack) ? VISIBLE : INVISIBLE);

        canFinish = typedArray.getBoolean( R.styleable.AppTitle_canFinish,true );

        String titleText = typedArray.getString( R.styleable.AppTitle_centreText);
        if (titleText != null && titleText.length() > 0) {
            appBarTitle.setVisibility(VISIBLE);
            appBarTitle.setText(titleText);
            appBarTitle.setTextColor(ResourceUtils.getColor(typedArray, R.styleable.AppTitle_centreTextColor, R.color.default_textColor));
            appBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.getDimenSize(typedArray, R.styleable.AppTitle_centreTextSize, R.dimen.default_textSize));
        }

        int button1Res = typedArray.getResourceId( R.styleable.AppTitle_centerButtonImgSrc, 0);
        if (button1Res > 0) {
//            appBarCenterBtn.setVisibility(button1Res == 0 ? GONE : VISIBLE);
            appBarCenterBtn.setVisibility(VISIBLE);
            appBarCenterBtn.setImageResource(button1Res);
        }

        int button2Res = typedArray.getResourceId( R.styleable.AppTitle_rightButtonImgSrc, 0);
        if (button2Res > 0) {
            appBarRightBtn.setVisibility(VISIBLE);
            appBarRightBtn.setImageResource(button2Res);
        }

        String rtitleText = typedArray.getString( R.styleable.AppTitle_centreText);
        if (rtitleText != null && rtitleText.length() > 0) {
            appBarRightTv.setVisibility(VISIBLE);
            appBarRightTv.setText(ResourceUtils.getString(typedArray, R.styleable.AppTitle_rightText));
            appBarRightTv.setTextColor(ResourceUtils.getColor(typedArray, R.styleable.AppTitle_rightTextColor, R.color.default_textColor));
            appBarRightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.getDimenSize(typedArray, R.styleable.AppTitle_rightTextSize, R.dimen.default_textSize));
        }

        typedArray.recycle();
        if (appBarCenterBtn.isShown() || appBarRightBtn.isShown()) {
            appBarTitle.setMaxWidth(ConvertUtils.dp2px(120));
        } else if (appBarCenterBtn.isShown() && appBarRightBtn.isShown()) {
            appBarTitle.setMaxWidth(ConvertUtils.dp2px(150));
        } else {
            appBarTitle.setMaxWidth(ConvertUtils.dp2px(200));
        }

    }


    public AppTitle setCanFinish(boolean canFinish) {
        this.canFinish = canFinish;
        return this;
    }

    public AppTitle setTitle(String title) {
        if (!appBarTitle.isShown()) {
            appBarTitle.setVisibility(VISIBLE);
        }
        appBarTitle.setText(title);
        return this;
    }

    public AppTitle setTitle(@StringRes int titleResId) {
        if (!appBarTitle.isShown()) {
            appBarTitle.setVisibility(VISIBLE);
        }
        appBarTitle.setText(getResources().getString(titleResId));
        return this;
    }


    public AppTitle setRightText(String rightText) {
        if (!appBarRightTv.isShown()) {
            appBarRightTv.setVisibility(VISIBLE);
        }
        appBarRightTv.setText(rightText);
        return this;
    }


    public AppTitle setRightText(@StringRes int rightText) {
        if (!appBarRightTv.isShown()) {
            appBarRightTv.setVisibility(VISIBLE);
        }
        appBarRightTv.setText(getResources().getString(rightText));
        return this;
    }

    public AppTitle setButton1Src(int resId) {
        if (!appBarCenterBtn.isShown()) {
            appBarCenterBtn.setVisibility(VISIBLE);
        }
        appBarCenterBtn.setImageResource(resId);
        return this;
    }


    public AppTitle setButton2Src(int resId) {
        if (!appBarRightBtn.isShown()) {
            appBarRightBtn.setVisibility(VISIBLE);
        }
        appBarRightBtn.setImageResource(resId);
        return this;
    }

    /**
     * 统一左上角返回
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        //返回
        if (view.getId() == R.id.app_bar_back) {
            if (canFinish) {
                try {
                    ((Activity) getContext()).finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //            //重写：刷新/视图
//            case R.id.app_bar_right_tv:
//                try {
//                    ((Activity) getContext()).getWindow().getDecorView().invalidate();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
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
