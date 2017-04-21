package com.example.a82173.friendcircle.presenter;

import android.view.View;

import com.example.a82173.friendcircle.databean.CommentConfig;
import com.example.a82173.friendcircle.presenter.view.ICircleView;

/**
 * Created by yuritian on 2017/4/18.
 */

public class CirclePresenter extends BasePresenter<ICircleView> {
    public void showReplyEditText(CommentConfig config){
        getView().EditTextReplyVisible(View.VISIBLE,config);
    }

    public void addLike(int position){
        getView().addLike(position);
    }

    public void deleteLike(int position){
        getView().deleteLike(position);
    }
}
