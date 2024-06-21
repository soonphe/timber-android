package com.soonphe.timber.ui.login;

import static com.soonphe.timber.constants.Constants.USER_INFO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.bumptech.glide.Glide;
import com.soonphe.timber.R;
import com.soonphe.timber.base.BaseActivity;
import com.soonphe.timber.entity.PUser;
import com.soonphe.timber.entity.TAdvert;
import com.soonphe.timber.ui.advert.AdvertContract;
import com.soonphe.timber.ui.advert.AdvertPresenter;
import com.soonphe.timber.ui.main.MainActivity;
import com.soonphe.timber.ui.splash.SplashActivity;
import com.soonphe.timber.utils.GlideUtils;
import com.soonphe.timber.view.widget.unlock.CustomerUnlockView;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录Activity
 *
 * @author soonphe
 * @since 1.0
 */
public class LoginActivity extends BaseActivity implements LoginContract.LoginView {

    @Inject
    LoginPresenter presenter;

    @BindView(R.id.usernameEditText)
    EditText usernameEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @Override
    public void startLoading() {

    }

    @Override
    public void endLoading() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        BarUtils.setStatusBarColor(this, 0);
        presenter.attachView(this);
//        Glide.with(this).load(R.mipmap.unlock_bg).into(unlockBgAdvert);
    }

    @OnClick({R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                presenter.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void loginSuccess(PUser user) {
        if (Objects.nonNull(user)){
            CacheMemoryUtils.getInstance().put(USER_INFO, user,24*60*60);
            mOperation.forward(MainActivity.class);
        }else{
            Toast.makeText(getContext(), "登录失败！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initInjector() {
        getComponent().inject(this);
    }



}
