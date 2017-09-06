package com.dwgg.retrofitandrxjava.utils;


import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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
    public static <T> ObservableTransformer<T, T> showLoading(final ILoading view) {
        return showLoading(view, null);
    }

    /**
     * 显示并隐藏loading<br>
     * 一般写在rx操作流的最后，subscribe()之前。
     */
    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> showLoading(final ILoading loading, final String tip) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (tip == null) {
                                    loading.showLoading("loading");
                                } else {
                                    loading.showLoading(tip);
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnTerminate(new Action() {
                            @Override
                            public void run() throws Exception {
                                loading.hideLoading();
                            }
                        });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> validateGitHubResponse(final Context context) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.observeOn(Schedulers.io()).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                            Timber.d("log : %s", Thread.currentThread().getName());
                            Timber.e(throwable, throwable.getMessage());
                    }
                }).observeOn(AndroidSchedulers.mainThread()).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.d("toast : %s", Thread.currentThread().getName());
                        Tools.toast(context, throwable.getMessage());
                    }
                });
            }
        };
    }
}
