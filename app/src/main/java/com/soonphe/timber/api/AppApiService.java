package com.soonphe.timber.api;


import com.soonphe.timber.entity.PUser;
import com.soonphe.timber.pojo.dto.TStatsDto;
import com.soonphe.timber.pojo.dto.TUserDto;
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
import com.soonphe.timber.pojo.vo.AdvertVo;

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
    @POST("sysUser/login")
    Observable<PUser> login(@Query("username")String userName, @Query("password")String password);

    /**
     * 获取本地电影列表（测试okhttp动态，传入本地网址请求）
     *
     * @return
     */
    @GET("Home/Interface/index?class=HallUse&method=getVideoList")
    Observable<TMovieBean> getMovieList();

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
    @Headers({"Content-Type: application/json", "Accept:  application/json"})
    @POST("advert/getList")
    Observable<List<TAdvert>> getAdvertList(@Body AdvertVo advertVo);

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
