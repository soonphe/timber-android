package com.soonphe.timber.utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.soonphe.timber.R;

import java.util.Objects;

/**
 * 展示消息的通用Dialog工具类
 *
 * @author soonphe
 * @since 1.0
 */
public class ShowPushMessageUtils {

    /**
     * 展示弹框，底部右下角弹出
     * 需要判断当前的Activity是否处于前台  如果处于前台Activity才会展示Dialog
     *
     * @param context      上下文
     * @param message      Dialog要展示的消息
     * @param gravity      弹出方向，Gravity.BOTTOM、TOP、CENTER_VERTICAL
     */
    public static void showPushDialog(Activity context, String message, Integer gravity) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        if (null != context) {
            Dialog dialog = new Dialog(context, R.style.pushDialog);
            View view = LayoutInflater.from(context).inflate(R.layout.push_dialog, null, false);
            ImageView imageView = view.findViewById(R.id.push_image);
            GlideUtils.loadImageView(context.getApplicationContext(), message, imageView);
            ImageView cancel = view.findViewById(R.id.iv_cancle);
            cancel.setOnClickListener(v -> dialog.dismiss());
            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);

            Window window = dialog.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            ///attributes.type = WindowManager.LayoutParams.TYPE_PHONE;   //这不是一个程序的窗口,它用来提供与用户交互的界面
            attributes.width = width - 10;
            // attributes.height = height - 300;
            window.setAttributes(attributes);
            if (Objects.nonNull(gravity)){
                window.setGravity(gravity);
            }else{
                window.setGravity(Gravity.BOTTOM);
            }
            dialog.show();
        }
    }
}
