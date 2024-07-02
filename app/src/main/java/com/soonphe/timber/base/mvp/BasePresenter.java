package com.soonphe.timber.base.mvp;

//import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * BasePresenter 关联持有View对象
 *
 * @author soonphe
 * @since 1.0
 */
public abstract class BasePresenter<T> {

    public    T                   mView;
    protected CompositeDisposable mDisposable;

    /**
     * 添加view引用和订阅事件
     */
    public void attachView(T view) {
        this.mView = view;
        mDisposable = new CompositeDisposable();
    }

    /**
     * 切断view引用和订阅事件
     */
    public void detachView() {
        mView = null;
        mDisposable.clear();
    }

}
