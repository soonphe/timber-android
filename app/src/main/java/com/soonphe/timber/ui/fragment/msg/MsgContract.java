package com.soonphe.timber.ui.fragment.msg;


import com.soonphe.timber.base.mvp.BasePView;
import com.soonphe.timber.entity.PUser;


/**
 * 消息
 *
 * @author soonphe
 * @since 1.0
 */
public interface MsgContract {
    interface MsgView extends BasePView {
        void getUserInfoSuccess(PUser certify);
    }

    interface MsgPresenter {
        void getUserInfo(int userId);

    }
}
