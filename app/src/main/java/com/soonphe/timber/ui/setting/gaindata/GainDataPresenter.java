package com.soonphe.timber.ui.setting.gaindata;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;
import com.soonphe.timber.api.AppApi;
import com.soonphe.timber.constants.Constants;
import com.soonphe.timber.di.PerActivity;
import com.soonphe.timber.pojo.dto.TStatsDto;
import com.soonphe.timber.pojo.dto.TUserDto;
import com.soonphe.timber.entity.TAdvert;
import com.soonphe.timber.entity.TArticle;
import com.soonphe.timber.entity.TBook;
import com.soonphe.timber.entity.TCity;
import com.soonphe.timber.entity.TCityArticle;
import com.soonphe.timber.entity.TDataVersion;
import com.soonphe.timber.entity.TFood;
import com.soonphe.timber.entity.TGame;
import com.soonphe.timber.entity.TMovie;
import com.soonphe.timber.entity.TStats;
import com.soonphe.timber.entity.TUser;
import com.soonphe.timber.entity.TVideo;
import com.soonphe.timber.base.mvp.BasePresenter;
import com.soonphe.timber.ui.setting.download.LogDownloadListener;
import com.soonphe.timber.utils.HTMLFormatUtils;
import com.soonphe.timber.pojo.vo.AdvertVo;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

//import io.reactivex.Observable;

import static com.soonphe.timber.constants.Constants.DOWNLOAD_ExternalStorage_PATH;

import io.reactivex.rxjava3.core.Observable;

@PerActivity
public class GainDataPresenter extends BasePresenter<GainDataContract.View> implements GainDataContract.Presenter {

    private AppApi api;

    @Inject
    public GainDataPresenter(AppApi api) {
        this.api = api;
    }

    @Override
    public void getDataVersion() {
        mDisposable.add(api.getDataVersion().subscribe(list -> {
                    if (LitePal.findFirst(TDataVersion.class) != null) {
                        LitePal.deleteAll(TDataVersion.class);
                    }
                    list.save();
                    mView.getDataVersionSuccess(list);
                },
                throwable -> mView.onError(throwable.getMessage()))
        );
    }

    @Override
    public void getAdvertList() {
        AdvertVo advertVo = new AdvertVo();
        advertVo.setTypeId(12);
        mDisposable.add(api.getAdvertList(advertVo).subscribe(list -> {
                    if (LitePal.findFirst(TAdvert.class) != null) {
                        LitePal.deleteAll(TAdvert.class);
                    }
                    LitePal.saveAll(list);
                    for (TAdvert advert : list) {
                        downloadFile(advert, 0, Constants.BASE_IMAGE_URL + advert.getPicUrl());
                        downloadFile(advert, 1, HTMLFormatUtils.getImgStr(advert.getContent()));

                    }
                    mView.getAdvertSuccess(list);
                },
                throwable -> mView.onError(throwable.getMessage()))
        );
    }

    @Override
    public void getOtherData() {
        mView.startLoading();
        mDisposable.add(
                Observable.mergeArray(
                        api.getAdvertType(),
                        api.getVideoType(),
                        api.getVideoList(1000),
                        api.getGameType(),
                        api.getGameList(1000),
                        api.getBookType(),
                        api.getBookList(1000),
                        api.getFoodType(),
                        api.getFoodList(1000),
                        api.getCityList(1000),
                        api.getCityArticleList(-1, 1000),
                        api.getArticleType(),
                        api.getArticleList(1000))
                        .subscribe((List<? extends LitePalSupport> list) -> {
                                    //删除对应的数据库表文件
                                    if (list.size() > 0) {
                                        LitePal.deleteAll(list.get(0).getClass());
                                    } else {
                                        return;
                                    }
                                    for (LitePalSupport object : list) {
                                        //存储所有结果集
                                        object.save();
                                        /**
                                         * 下载所有文件
                                         * type:0图片,1文件
                                         */
                                        if (object instanceof TVideo) {
                                            downloadFile(object, 0, Constants.BASE_IMAGE_URL + ((TVideo) object).getPosterurl());
                                            downloadFile(object, 1, Constants.BASE_IMAGE_URL + ((TVideo) object).getFilepath());
                                        } else if (object instanceof TGame) {
                                            downloadFile(object, 0, Constants.BASE_IMAGE_URL + ((TGame) object).getPicurl());
                                            downloadFile(object, 1, Constants.BASE_IMAGE_URL + ((TGame) object).getFilepath());
                                        } else if (object instanceof TBook) {
                                            downloadFile(object, 0, Constants.BASE_IMAGE_URL + ((TBook) object).getPicurl());
                                            downloadFile(object, 1, Constants.BASE_IMAGE_URL + ((TBook) object).getFilepath());
                                        } else if (object instanceof TFood) {
                                            downloadFile(object, 0, Constants.BASE_IMAGE_URL + ((TFood) object).getPicurl());
                                        } else if (object instanceof TCity) {
                                            downloadFile(object, 0, Constants.BASE_IMAGE_URL + ((TCity) object).getPicurl());
                                            downloadFile(object, 1, HTMLFormatUtils.getImgStr(((TCity) object).getContent()));
                                        } else if (object instanceof TCityArticle) {
                                            downloadFile(object, 0, Constants.BASE_IMAGE_URL + ((TCityArticle) object).getPicurl());
                                            downloadFile(object, 1, HTMLFormatUtils.getImgStr(((TCityArticle) object).getContent()).split(",")[0]);
                                        } else if (object instanceof TArticle) {
                                            downloadFile(object, 0, Constants.BASE_IMAGE_URL + ((TArticle) object).getPicurl());
                                            if (((TArticle) object).getClassify() == 0) {
                                                downloadFile(object, 1, HTMLFormatUtils.getImgStr(((TArticle) object).getContent()));
                                            } else {
                                                downloadFile(object, 1, Constants.BASE_IMAGE_URL + ((TArticle) object).getPathfile());
                                            }
                                        }
                                    }
                                },
                                throwable -> mView.onError(throwable.getMessage()))
        );
    }

    @Override
    public void getMovieData() {
        mView.startLoading();
        mView.getMovieDataSuccess();
//        mDisposable.add(api.getMovieList().subscribe(result -> {
//                    //存储所有结果集
//                    if (LitePal.findFirst(TMovie.class) != null) {
//                        LitePal.deleteAll(TMovie.class);
//                    }
//                    List<TMovie> list = result.getData().getData().getVideos();
//                    LitePal.saveAll(list);
//
//                    SPUtils.getInstance().put(DOWNLOAD_COUNT, list.size());
//                    //获取drm服务对象
//                    DrmService drmService = DrmService.getService();
//                    boolean isStartService = drmService.startService();
//                    if (!drmService.initDrmdecoder()) {  // 初始化
//                        ToastUtils.showShort("解密模块启动失败，请重启电视盒子或联系管理员");
//                    }
//                    for (TMovie tMovie : list) {
////                        if (tMovie == list.get(0)) {
//                        //根据位置获取当前点击的视频播放url
//                        Uri uri = null;
//                        if (isStartService) {
//                            //获取的url为已解密的直接可播放地址
//                            uri = DrmService.getService().getUrl(
//                                    tMovie.getSave_name(),
//                                    DeviceUtils.getMacAddress(), //传入一个房间或者是盒子的唯一标识
//                                    "192.168.1.6",//服务器端的ip
//                                    "" //订单标识
//                            );
//                        }
//                        downloadFile(tMovie, 0, tMovie.getFace_pic() + "");
//                        downloadFile(tMovie, 1, uri.toString() + "");
//                        //②：这里下载本地服务器资源
////                        String fileName = tMovie.getFile_path();
////                        downloadFile2(tMovie, 1,
////                                Constants.BASE_IMAGE_URL + "/movie/" + fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length()) + "");
//
////                        }
//                    }
//                    mView.getMovieDataSuccess();
//                },
//                throwable -> mView.onError(throwable.getMessage()))
//        );
    }

    /**
     * 使用okDownload下载文件
     *
     * @param object
     * @param type
     * @param downpath
     */
    public void downloadFile2(LitePalSupport object, int type, String downpath) {
        String destFileName = null;
        /**
         * 1905需要设置电影返回名称，否则保存为ts文件
         */
        if ((object instanceof TMovie && type == 1)) {
            //取文件名+尾缀
            destFileName = ((TMovie) object).getFile_name() + "." + FileUtils.getFileExtension(downpath);
        }
        GetRequest<File> request = OkGo.<File>get(downpath + "");

        /**
         * 判断当前是否存在该tag对应的任务
         */
        if (!OkDownload.getInstance().hasTask(downpath)) {
            OkDownload.request(downpath + "", request)    //传入tag和下载请求
//                                .priority()   //优先级，int越大越高
                    .folder(Constants.DOWNLOAD_ExternalStorage_PATH + "")   //下载的文件夹
                    .fileName(destFileName) //下载的文件名
                    .extra1((Serializable) object) //实体对象
                    .extra2(type) //类型：0图片 1文件
                    .save() //第一次创建任务需要save
                    .register(new LogDownloadListener())    //当前任务回调监听:绑定数据库与本地文件路径
                    .start();
        } else {
            DownloadTask downloadTask = OkDownload.getInstance().getTask(downpath);
            /**
             * 任务下载失败则重新下载
             * 等待，下载中，完成等状态不做处理
             */
            if (downloadTask.progress.status == Progress.ERROR) {
                downloadTask.restart();
            }
        }
    }

    /**
     * 使用okgo下载
     *
     * @param object
     * @param type
     * @param downpath
     */
    public void downloadFile(LitePalSupport object, int type, String downpath) {
        //截取最后的文件名和尾缀
        String localFilePath = DOWNLOAD_ExternalStorage_PATH + downpath.substring(downpath.lastIndexOf("/"), downpath.length()) + "";

        LogUtils.e("要判断的文件地址" + localFilePath);
        //判断本地文件是否存在
        if (FileUtils.isFileExists(localFilePath)) {
            LogUtils.e("本地文件存在" + localFilePath);
            if (object instanceof TAdvert) {
                LogUtils.e("设置广告文件");
                if (type == 0) {
                    ((TAdvert) object).setDownloadPic(localFilePath);
                } else {
                    ((TAdvert) object).setDownloadContent(localFilePath);
                }
                (object).update(((TAdvert) object).getId());
            } else if (object instanceof TVideo) {
                LogUtils.e("设置视频文件");
                if (type == 0) {
                    ((TVideo) object).setDownloadPic(localFilePath);
                } else {
                    ((TVideo) object).setDownloadFile(localFilePath);
                }
                (object).update(((TVideo) object).getId());
            } else if (object instanceof TGame) {
                if (type == 0) {
                    LogUtils.e("设置游戏图片");
                    ((TGame) object).setDownloadPic(localFilePath);
                } else {
                    LogUtils.e("设置游戏文件路径");
                    ((TGame) object).setDownloadFile(localFilePath);
                }
                (object).update(((TGame) object).getId());
            } else if (object instanceof TBook) {
                if (type == 0) {
                    LogUtils.e("设置书吧图片");
                    ((TBook) object).setDownloadPic(localFilePath);
                } else {
                    LogUtils.e("设置书吧文件");
                    ((TBook) object).setDownloadFile(localFilePath);
                }
                (object).update(((TBook) object).getId());
            } else if (object instanceof TFood) {
                LogUtils.e("设置点餐文件");
                ((TFood) object).setDownloadPic(localFilePath);
                (object).update(((TFood) object).getId());
            } else if (object instanceof TCity) {
                LogUtils.e("设置城市文件");
                if (type == 0) {
                    ((TCity) object).setDownloadPic(localFilePath);
                } else {
                    ((TCity) object).setDownloadContent(localFilePath);
                }
                (object).update(((TCity) object).getId());
            } else if (object instanceof TCityArticle) {
                LogUtils.e("设置城市文件");
                if (type == 0) {
                    ((TCityArticle) object).setDownloadPic(localFilePath);
                } else {
                    ((TCityArticle) object).setDownloadContent(localFilePath);
                }
                (object).update(((TCityArticle) object).getId());
            } else if (object instanceof TArticle) {
                LogUtils.e("设置城铁文件");
                if (type == 0) {
                    ((TArticle) object).setDownloadPic(localFilePath);
                } else {
                    ((TArticle) object).setDownloadFile(localFilePath);
                }
                (object).update(((TArticle) object).getId());
            } else if (object instanceof TMovie) {
                LogUtils.e("设置电影文件");
                if (type == 0) {
                    ((TMovie) object).setDownloadPic(localFilePath);
                } else {
                    ((TMovie) object).setDownloadFile(localFilePath);
                }
                (object).update(((TMovie) object).getId());
            }
        } else {

            LogUtils.e("本地文件不存在" + localFilePath);
            String destFileName = null;
            //针对电影文件特殊处理文件名
            if ((object instanceof TMovie && type == 1)) {
                //取文件名+尾缀
                destFileName = ((TMovie) object).getFile_name() + "." + FileUtils.getFileExtension(downpath);
            }

            OkGo.<File>get(downpath)
                    .tag(this)
                    .execute(new FileCallback(destFileName) {
                        @Override
                        public void onSuccess(Response<File> response) {
                            LogUtils.e("获取到的文件路径为：" + response.body().getPath());
                            if (object instanceof TAdvert) {
                                LogUtils.e("设置广告文件");
                                if (type == 0) {
                                    ((TAdvert) object).setDownloadPic(response.body().getPath());
                                } else {
                                    ((TAdvert) object).setDownloadContent(response.body().getPath());
                                }
                                (object).update(((TAdvert) object).getId());
                            } else if (object instanceof TVideo) {
                                LogUtils.e("设置视频文件");
                                if (type == 0) {
                                    ((TVideo) object).setDownloadPic(response.body().getPath());
                                } else {
                                    ((TVideo) object).setDownloadFile(response.body().getPath());
                                }
                                (object).update(((TVideo) object).getId());
                            } else if (object instanceof TGame) {
                                if (type == 0) {
                                    LogUtils.e("设置游戏图片");
                                    ((TGame) object).setDownloadPic(response.body().getPath());
                                } else {
                                    LogUtils.e("设置游戏文件路径");
                                    ((TGame) object).setDownloadFile(response.body().getPath());
                                }
                                (object).update(((TGame) object).getId());
                            } else if (object instanceof TBook) {
                                if (type == 0) {
                                    LogUtils.e("设置书吧图片");
                                    ((TBook) object).setDownloadPic(response.body().getPath());
                                } else {
                                    LogUtils.e("设置书吧文件");
                                    ((TBook) object).setDownloadFile(response.body().getPath());
                                }
                                (object).update(((TBook) object).getId());
                            } else if (object instanceof TFood) {
                                LogUtils.e("设置点餐文件");
                                ((TFood) object).setDownloadPic(response.body().getPath());
                                (object).update(((TFood) object).getId());
                            } else if (object instanceof TCity) {
                                LogUtils.e("设置城市文件");
                                if (type == 0) {
                                    ((TCity) object).setDownloadPic(response.body().getPath());
                                } else {
                                    ((TCity) object).setDownloadContent(response.body().getPath());
                                }
                                (object).update(((TCity) object).getId());
                            } else if (object instanceof TCityArticle) {
                                LogUtils.e("设置城市文章文件");
                                if (type == 0) {
                                    ((TCityArticle) object).setDownloadPic(response.body().getPath());
                                } else {
                                    ((TCityArticle) object).setDownloadContent(response.body().getPath());
                                }
                                (object).update(((TCityArticle) object).getId());
                            } else if (object instanceof TArticle) {
                                LogUtils.e("设置城铁文件");
                                if (type == 0) {
                                    ((TArticle) object).setDownloadPic(response.body().getPath());
                                } else {
                                    ((TArticle) object).setDownloadFile(response.body().getPath());
                                }
                                (object).update(((TArticle) object).getId());
                            } else if (object instanceof TMovie) {
                                LogUtils.e("设置电影文件");
                                if (type == 0) {
                                    ((TMovie) object).setDownloadPic(response.body().getPath());
                                } else {
                                    ((TMovie) object).setDownloadFile(response.body().getPath());
                                }
                                (object).update(((TMovie) object).getId());
                            }
                        }

                        @Override
                        public void downloadProgress(Progress progress) {
                            LogUtils.e(progress.fileName +
                                    "下载大小为：" + progress.currentSize +
                                    "下载进度为：" + progress.fraction * 100 + "%");
                            //回调下载进度
                            super.downloadProgress(progress);
                        }

                        @Override
                        public void onError(Response<File> response) {
                            LogUtils.e("下载报错,相关类:" + object.getClass() + response.message());
                            super.onError(response);
                        }
                    });

        }

    }

    @Override
    public void uploadUserData() {
        mView.startLoading();
        //获取所有未上传过的用户数据
        List<TUser> list = LitePal.where("delflag = ?", "0").find(TUser.class);
        if (list.size() > 0) {
            for (TUser tUser : list) {
                TUserDto tUserDto = new TUserDto(tUser);
                mDisposable.add(api.uploadUser("973570", tUserDto).subscribe(obj -> {
                            //上传成功后更新数据状态码
                            tUser.setDelflag(true);
                            tUser.update(tUser.getId());
                            if (tUser == list.get(list.size() - 1)) {
                                //更新到最新一条时保存数据
                                mView.uploadUserDataSuccess();
                            }
                        },
                        throwable -> mView.onError(throwable.getMessage()))
                );
            }
        }
    }

    @Override
    public void uploadStatsData() {
        //获取所有统计数据
        List<TStats> list = LitePal.findAll(TStats.class);
        if (list.size() > 0) {
            List<TStatsDto> dtoList = new ArrayList<>();
            for (TStats tStats : list) {
                TStatsDto tStatsDto = new TStatsDto(tStats);
                dtoList.add(tStatsDto);
            }
            mDisposable.add(api.uploadStatsList(dtoList).subscribe(obj -> {
                        //①上传成功后删除除最新数据之外的所有数据
                        //②不删除，保留所有数据
                        mView.endLoading();
                        mView.uploadStatsDataSuccess();

                    },
                    throwable -> mView.onError(throwable.getMessage()))
            );

        }
    }


}
