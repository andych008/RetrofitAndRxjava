package com.dwgg.retrofitandrxjava.vmdata;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.dwgg.retrofitandrxjava.BR;

/**
 *
 * Created by 喵叔catuncle
 * 2016/05/05 21:28
 */
public class GetVM extends BaseObservable{
    private String text;

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }
}
