package com.soonphe.timber.api;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.dto.TStatsDto;
import com.soonphe.timber.dto.TUserDto;
import com.soonphe.timber.entity.TAdvert;
import com.soonphe.timber.entity.TAdvertType;
import com.soonphe.timber.entity.TArticle;
import com.soonphe.timber.entity.TArticleType;
import com.soonphe.timber.entity.TBook;
import com.soonphe.timber.entity.TBookType;
import com.soonphe.timber.entity.TCity;
import com.soonphe.timber.entity.TCityArticle;
import com.soonphe.timber.entity.TDataVersion;
import com.soonphe.timber.entity.TFood;
import com.soonphe.timber.entity.TFoodType;
import com.soonphe.timber.entity.TGame;
import com.soonphe.timber.entity.TGameType;
import com.soonphe.timber.entity.TMovieBean;
import com.soonphe.timber.entity.TVersion;
import com.soonphe.timber.entity.TVideo;
import com.soonphe.timber.entity.TVideoType;
import com.soonphe.timber.entity.TWorkBench;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author soonphe
 * @Date 2017-11-20 18:04
 * @Description API
 */
public class AppApi implements AppApiService {

    OkHttpClient okHttpClient;

    private AppApiService service;

    /**
     * 默认请求（统一请求前缀，统一自定义json解析）
     *
     * @param mOkHttpClient
     */
    public AppApi(OkHttpClient mOkHttpClient) {
        this.okHttpClient = mOkHttpClient;
        Retrofit retrofit =
                new Retrofit.Builder()
                        .client(mOkHttpClient)
                        .baseUrl(Constants.BASE_API_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(com.soonphe.timber.components.retrofit.GsonConverterFactory.create())
                        .build();
        service = retrofit.create(AppApiService.class);
    }

    /**
     * 自定义请求路径
     *
     * @param baseUrl 自定义请求路径
     */
    public AppApiService getAppApi(String baseUrl) {
        Retrofit retrofit =
                new Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(baseUrl)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        //这里使用的是默认json解析
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        return retrofit.create(AppApiService.class);
    }

    /**
     * 封装.subscribeOn(Schedulers.io()).
     * observeOn(AndroidSchedulers.mainThread());
     *
     * @return
     */
    private ObservableTransformer bindUntil() {
        return upstream -> upstream.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Observable<TMovieBean> getMovieList() {
        return getAppApi("http://192.168.1.6:8087/index.php/").getMovieList().compose(bindUntil());
    }

    @Override
    public Observable<TWorkBench> getWorkBenchData() {
        return getAppApi("https://jz-amp.daojia-inc.com/mock/459/").getWorkBenchData().compose(bindUntil());
    }

    @Override
    public Observable<List<TAdvertType>> getAdvertType() {
        return service.getAdvertType().compose(bindUntil());
    }

    @Override
    public Observable<List<TAdvert>> getAdvertList(int pCommonSearchVO) {
        return service.getAdvertList(pCommonSearchVO).compose(bindUntil());
    }

    @Override
    public Observable<List<TVideoType>> getVideoType() {
        return service.getVideoType().compose(bindUntil());
    }

    @Override
    public Observable<List<TVideo>> getVideoList(int pageSize) {
        return service.getVideoList(pageSize).compose(bindUntil());
    }

    @Override
    public Observable<List<TGameType>> getGameType() {
        return service.getGameType().compose(bindUntil());
    }

    @Override
    public Observable<List<TGame>> getGameList(int pageSize) {
        return service.getGameList(pageSize).compose(bindUntil());
    }

    @Override
    public Observable<List<TBookType>> getBookType() {
        return service.getBookType().compose(bindUntil());
    }

    @Override
    public Observable<List<TBook>> getBookList(int pageSize) {
        return service.getBookList(pageSize).compose(bindUntil());
    }

    @Override
    public Observable<List<TFoodType>> getFoodType() {
        return service.getFoodType().compose(bindUntil());
    }

    @Override
    public Observable<List<TFood>> getFoodList(int pageSize) {
        return service.getFoodList(pageSize).compose(bindUntil());
    }

    @Override
    public Observable<List<TCity>> getCityList(int pageSize) {
        return service.getCityList(pageSize).compose(bindUntil());
    }

    @Override
    public Observable<List<TCityArticle>> getCityArticleList(int typeId, int pageSize) {
        return service.getCityArticleList(typeId, pageSize).compose(bindUntil());
    }

    @Override
    public Observable<List<TArticleType>> getArticleType() {
        return service.getArticleType().compose(bindUntil());
    }

    @Override
    public Observable<List<TArticle>> getArticleList(int pageSize) {
        return service.getArticleList(pageSize).compose(bindUntil());
    }


    @Override
    public Observable<TVersion> getVersion() {
        return service.getVersion().compose(bindUntil());
    }

    @Override
    public Observable<TDataVersion> getDataVersion() {
        return service.getDataVersion().compose(bindUntil());
    }

    @Override
    public Observable<Object> uploadUser(String captcher, TUserDto tUser) {
        return service.uploadUser(captcher, tUser).compose(bindUntil());
    }

    @Override
    public Observable<Object> uploadStats(TStatsDto list) {
        return service.uploadStats(list).compose(bindUntil());
    }

    @Override
    public Observable<Object> uploadStatsList(List<TStatsDto> list) {
        return service.uploadStatsList(list).compose(bindUntil());
    }


}
