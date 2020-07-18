package com.soonphe.timber.api;


import com.soonphe.timber.dto.TStatsDto;
import com.soonphe.timber.dto.TUserDto;
import com.soonphe.timber.view.entity.TAdvert;
import com.soonphe.timber.view.entity.TAdvertType;
import com.soonphe.timber.view.entity.TArticle;
import com.soonphe.timber.view.entity.TArticleType;
import com.soonphe.timber.view.entity.TBook;
import com.soonphe.timber.view.entity.TBookType;
import com.soonphe.timber.view.entity.TCity;
import com.soonphe.timber.view.entity.TCityArticle;
import com.soonphe.timber.view.entity.TDataVersion;
import com.soonphe.timber.view.entity.TFood;
import com.soonphe.timber.view.entity.TFoodType;
import com.soonphe.timber.view.entity.TGame;
import com.soonphe.timber.view.entity.TGameType;
import com.soonphe.timber.view.entity.TMovieBean;
import com.soonphe.timber.view.entity.TVersion;
import com.soonphe.timber.view.entity.TVideo;
import com.soonphe.timber.view.entity.TVideoType;
import com.soonphe.timber.view.entity.TWorkBench;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author soonphe
 * @Date 2017-11-20 18:04
 * @Description API接口
 */
public interface AppApiService {

    /**
     * 获取1905电影列表
     *
     * @return
     */
    @GET("Home/Interface/index?class=HallUse&method=getVideoList")
    Observable<TMovieBean> getMovieList();

    /**
     * 测试okhttp动态
     * @return
     */
    @GET("workBench/getBenchData")
    Observable<TWorkBench> getWorkBenchData();

    /**
     * 获取广告类型
     *
     * @return
     */
    @GET("advertType/getList")
    Observable<List<TAdvertType>> getAdvertType();

    /**
     * 获取所有的广告数据
     *
     * @return
     */
    @GET("advert/getList")
    Observable<List<TAdvert>> getAdvertList(@Query("pageSize") int pageSize);

    /**
     * 获取视频类型
     *
     * @return
     */
    @GET("videoType/getList")
    Observable<List<TVideoType>> getVideoType();

    /**
     * 获取所有视频
     *
     * @return
     */
    @GET("video/getList")
    Observable<List<TVideo>> getVideoList(@Query("pageSize") int pageSize);

    /**
     * 获取游戏类别
     *
     * @return
     */
    @GET("gameType/getList")
    Observable<List<TGameType>> getGameType();

    /**
     * 获取游戏列表
     *
     * @return
     */
    @GET("game/getList")
    Observable<List<TGame>> getGameList(@Query("pageSize") int pageSize);

    /**
     * 获取书籍类型列表
     *
     * @return
     */
    @GET("bookType/getList")
    Observable<List<TBookType>> getBookType();

    /**
     * 获取所有书籍
     *
     * @return
     */
    @GET("book/getList")
    Observable<List<TBook>> getBookList(@Query("pageSize") int pageSize);

    /**
     * 获取菜单类型列表
     *
     * @return
     */
    @GET("foodType/getList")
    Observable<List<TFoodType>> getFoodType();

    /**
     * 获取所有菜单
     *
     * @return
     */
    @GET("food/getList")
    Observable<List<TFood>> getFoodList(@Query("pageSize") int pageSize);

    /**
     * 获取所有城市
     *
     * @return
     */
    @GET("city/getList")
    Observable<List<TCity>> getCityList(@Query("pageSize") int pageSize);

    /**
     * 获取城市文章列表
     *
     * @return
     */
    @GET("cityArticle/getList")
    Observable<List<TCityArticle>> getCityArticleList(@Query("typeId") int typeId, @Query("pageSize") int pageSize);

    /**
     * 获取城铁文章类型列表
     *
     * @return
     */
    @GET("articleType/getList")
    Observable<List<TArticleType>> getArticleType();

    /**
     * 获取所有城铁文章
     *
     * @return
     */
    @GET("article/getList")
    Observable<List<TArticle>> getArticleList(@Query("pageSize") int pageSize);

    /**
     * 获取APK最新版本
     *
     * @return
     */
    @GET("version/selectTopOne")
    Observable<TVersion> getVersion();

    /**
     * 获取数据最新版本
     *
     * @return
     */
    @GET("dataVersion/selectTopOne")
    Observable<TDataVersion> getDataVersion();

    /**
     * 用户数据上传
     * @param tUser
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept:  application/json"})
    @POST("user/add")
    Observable<Object> uploadUser(@Query("captcher") String captcher, @Body TUserDto tUser);

    /**
     * 统计数据上传
     * @param list
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept:  application/json"})
    @POST("stats/add")
    Observable<Object> uploadStats(@Body TStatsDto list);

    /**
     * 统计数据上传
     * @param list
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept:  application/json"})
    @POST("stats/addList")
    Observable<Object> uploadStatsList(@Body List<TStatsDto> list);



}
