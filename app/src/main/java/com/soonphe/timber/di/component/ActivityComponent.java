package com.soonphe.timber.di.component;

import android.app.Activity;

import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.di.module.ActivityModule;
import com.soonphe.timber.ui.advert.advertise.AdvertContentActivity;
import com.soonphe.timber.ui.article.ArticleActivity;
import com.soonphe.timber.ui.article.article.ArticleContentActivity;
import com.soonphe.timber.ui.book.book.BookActivity;
import com.soonphe.timber.ui.book.bookread.BookreadActivity;
import com.soonphe.timber.ui.city.city.CityActivity;
import com.soonphe.timber.ui.city.cityarticle.CityArticleActivity;
import com.soonphe.timber.ui.city.citydetail.CitydetailActivity;
import com.soonphe.timber.ui.food.FoodActivity;
import com.soonphe.timber.ui.game.game.GameActivity;
import com.soonphe.timber.ui.game.gameplay.GamePlayActivity;
import com.soonphe.timber.ui.login.LoginActivity;
import com.soonphe.timber.ui.main.MainActivity;
import com.soonphe.timber.ui.setting.SettingActivity;
import com.soonphe.timber.ui.splash.SplashActivity;
import com.soonphe.timber.ui.unlock.UnlockActivity;
import com.soonphe.timber.ui.video.CinemaActivity;
import com.soonphe.timber.ui.videoplayer.VideoPlayerActivity;

import dagger.Component;


/**
 * @Author soonphe
 * @Date 2017-11-21 10:52
 * @Description 获取依赖对象，dependencies声明依赖关系
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    /**
     * inject方法：将依赖需求方对象送到Component类中，注入所需对象
     * 注：inject方法的参数不能使用父类接受
     *
     * @param activity
     */
    void inject(MainActivity activity);//主界面

    void inject(SplashActivity activity);//欢迎页

    void inject(LoginActivity activity);//登录

    void inject(UnlockActivity unlockActivity);//解锁屏页

    void inject(AdvertContentActivity advertContentActivity);//广告详情页

    void inject(CinemaActivity cinemaActivity); //电影
    void inject(VideoPlayerActivity videoPlayerActivity); //视频播放界面

    void inject(GameActivity activity);//游戏列表页
    void inject(GamePlayActivity gamePlayActivity); //玩游戏页面

    void inject(BookActivity bookActivity);//书吧
    void inject(BookreadActivity bookreadActivity);//书吧阅读

    void inject(FoodActivity foodActivity);//餐饮

    void inject(CityActivity cityActivity);//城市
    void inject(CitydetailActivity citydetailActivity);//城市详情
    void inject(CityArticleActivity cityArticleActivity);//城市文章

    void inject(ArticleActivity articleActivity);//文章
    void inject(ArticleContentActivity articleContentActivity);//文章富文本

    void inject(SettingActivity activity);  //设置界面






}
