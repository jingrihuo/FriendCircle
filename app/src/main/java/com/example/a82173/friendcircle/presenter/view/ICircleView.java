package com.example.a82173.friendcircle.presenter.view;

import com.example.a82173.friendcircle.databean.CommentConfig;

/**
 * Created by yuritian on 2017/4/18.
 */

public interface ICircleView extends IBaseView{
    public void EditTextReplyVisible(int visibility, CommentConfig config);
    public void addLike(int position);
    public void deleteLike(int position);
    public void deleteComment(int position,int comId);
}
