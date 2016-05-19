package com.dwgg.retrofitandrxjava.utils;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Rx工具类
 */
public class RxUtils {

    /**
     * 加载框
     */
    public interface ILoading {
        void showLoading(String tip);

        void hideLoading();
    }

    /**
     * 显示并隐藏loading<br>
     * 一般写在rx操作流的最后，subscribe()之前。
     */
    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> showLoading(final ILoading view) {
        return showLoading(view, null);
    }

    /**
     * 显示并隐藏loading<br>
     * 一般写在rx操作流的最后，subscribe()之前。
     */
    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> showLoading(final ILoading loading, final String tip) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                if (tip == null) {
                                    loading.showLoading("loading");
                                } else {
                                    loading.showLoading(tip);
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
                                loading.hideLoading();
                            }
                        });
            }
        };
    }

}
