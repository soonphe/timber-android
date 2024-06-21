package com.soonphe.timber.ui.fragment.center;


import com.soonphe.timber.base.mvp.BasePView;
import com.soonphe.timber.entity.PUser;


/**
 * 商城
 *
 * @author soonphe
 * @since 1.0
 */
public interface CenterContract {
    interface CenterView extends BasePView {
        void getUserInfoSuccess(PUser certify);
    }

    interface CenterPresenter {
        void getUserInfo(int userId);

    }
}
