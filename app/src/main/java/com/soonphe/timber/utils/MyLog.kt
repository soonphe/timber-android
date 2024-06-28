package com.soonphe.timber.utils

import android.util.Log
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

/**
 * 自定义日志输出类
 */
object MyLog {

    @JvmStatic
    fun d(tag: String, message: String?) {
        Timber.tag(tag).d(message)
    }

    @JvmStatic
    fun i(tag: String, message: String?) {
        Timber.tag(tag).e(message)
    }

    @JvmStatic
    fun w(tag: String, message: String?) {
        Timber.tag(tag).w(message)
    }

    @JvmStatic
    fun e(tag: String, message: String?) {
        Timber.tag(tag).e(message)
    }

    @JvmStatic
    fun e(tag: String, message: String, throwable: Throwable) {
        Timber.tag(tag).e(throwable, message)
    }

    /**
     * TODO：暂时保留。f mean 输出日志到文件
     */
    @JvmStatic
    fun f(tag: String, message: String?) {
        i(tag, message)
    }

    /**
     * 自定义
     */
    @JvmStatic
    fun defLogFunction(tag: String, message: String?) {
        Log.e(tag, message.toString());
    }

    /**
     * 初始化日志
     */
    init {
        Timber.plant(Timber.DebugTree())
//        Timber.plant(FileLoggingTree())
        Timber.plant(CrashReportTree())
//        Timber.plant(BuglyLogTree())
    }

    class CrashReportTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, e: Throwable?) {
            if (e == null) return
            // Ignore network errors
            if (e is IOException) return
            if (e is HttpException) return
//            CrashReport.postCatchedException(e)
        }
    }


}
