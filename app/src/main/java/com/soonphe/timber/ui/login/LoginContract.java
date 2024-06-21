package com.soonphe.timber.ui.login;

import android.view.View;

import com.soonphe.timber.base.mvp.BasePView;
import com.soonphe.timber.entity.PCategory;
import com.soonphe.timber.entity.PUser;

import java.util.List;

/**
 * 登录页
 *
 * @author soonphe
 * @since 1.0
 */
public interface LoginContract {

    interface LoginView extends BasePView {
        void loginSuccess(PUser certify);
    }

    interface LoginPresenter {
        void login(String userName, String password);
    }
}
