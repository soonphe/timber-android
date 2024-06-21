package com.soonphe.timber.ui.data;

import com.blankj.utilcode.util.SPUtils;
import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.pojo.dto.TStatsDto;
import com.soonphe.timber.entity.TStats;
import com.soonphe.timber.base.mvp.BasePresenter;

import javax.inject.Inject;

import static com.soonphe.timber.constants.Constants.NETWORK_AVAILABLE;

@PerActivity
public class DataPresenter extends BasePresenter<DataContract.View> implements DataContract.Presenter {

    private AppApi api;

    @Inject
    public DataPresenter(AppApi accountApi) {
        this.api = accountApi;
    }

    @Override
    public void uploadData(TStats tStats) {
        TStatsDto tStatsDto=new TStatsDto(tStats);
        //判断当前网络可用——可用则上传
        if (SPUtils.getInstance().getBoolean(NETWORK_AVAILABLE)) {
            mDisposable.add(api.uploadStats(tStatsDto)
                    .subscribe(categoryMenus -> mView.uploadDataSuccess(),
                            throwable ->
                                    mView.onError(throwable.getMessage())
                    )
            );
        }
    }
}
