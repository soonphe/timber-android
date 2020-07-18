package com.soonphe.timber.di.component;


import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.di.module.ActivityModule;
import com.soonphe.timber.ui.fragment.mine.MineFragment;
import com.soonphe.timber.ui.fragment.home.HomeFragment;
import com.soonphe.timber.ui.setting.aboutus.AboutUsFragment;
import com.soonphe.timber.ui.setting.gaindata.GainDataFragment;
import com.soonphe.timber.ui.setting.hotspot.HotspotFragment;
import com.soonphe.timber.ui.setting.network.NetworkFragment;
import com.soonphe.timber.ui.setting.version.VersionFragment;
import com.soonphe.timber.ui.video.movie.MovieFragment;
import com.soonphe.timber.ui.video.video.VideoFragment;

import dagger.Component;

/**
 * @Author soonphe
 * @Date 2017-11-21 10:53
 * @Description 需要dagger注入的fragment
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface FragmentComponent {

    void inject(HomeFragment fragment);
    void inject(MineFragment fragment);

    //电影
    void inject(MovieFragment fragment);
    void inject(VideoFragment fragment);

    //设置
    void inject(AboutUsFragment fragment);
    void inject(GainDataFragment fragment);
    void inject(VersionFragment fragment);
    void inject(NetworkFragment fragment);
    void inject(HotspotFragment fragment);

}
