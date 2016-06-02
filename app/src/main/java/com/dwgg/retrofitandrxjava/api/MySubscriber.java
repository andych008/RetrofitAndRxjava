package com.dwgg.retrofitandrxjava.api;

import rx.Subscriber;

/**
 * Created by Andy on 6/1/2016.
 */
public abstract class MySubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }
}
