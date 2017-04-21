package com.example.a82173.friendcircle.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.a82173.friendcircle.presenter.view.IBaseView;

import java.lang.ref.WeakReference;

/**
 * Created by yuritian on 2017/4/18.
 */

public class BasePresenter <V extends IBaseView> {
    protected String tag;
    private WeakReference<V> vWeakReference;
    protected Context mContext;

    public void cancleRequest(){
        cancleRequest(tag);
    }

    public void cancleRequest(String tag){
    }

    @UiThread
    public void attachView(V view) {
        vWeakReference = new WeakReference<V>(view);
        mContext = getContext();
    }

    @UiThread
    @Nullable
    public V getView() {
        return vWeakReference == null ? null : vWeakReference.get();
    }

    @UiThread
    public boolean isViewAttached() {
        return vWeakReference != null && vWeakReference.get() != null;
    }

    @UiThread
    public void detachView() {
        if (vWeakReference != null) {
            vWeakReference.clear();
            vWeakReference = null;
        }
    }

    protected Context getContext(){
        if(getView() instanceof Fragment){
            return ((Fragment)getView()).getActivity();
        }else if(getView() instanceof Activity){
            return (Context) getView();
        }else if(getView() instanceof View){
            return ((View)getView()).getContext();
        }
        return null;
    }
}
