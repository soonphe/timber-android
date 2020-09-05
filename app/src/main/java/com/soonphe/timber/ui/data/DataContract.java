package com.soonphe.timber.ui.data;

import com.soonphe.timber.entity.TStats;
import com.soonphe.timber.base.mvp.BasePView;

public class DataContract {
    public interface View extends BasePView {
        void uploadDataSuccess();
    }

    public interface  Presenter  {
        void uploadData(TStats list);
    }
}
