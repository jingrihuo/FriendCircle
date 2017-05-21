package com.example.a82173.friendcircle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.a82173.friendcircle.adapter.CommentAdapter;


public class CommentListView extends LinearLayout {

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs){
        super(context, attrs);

    }

    public CommentListView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }


    public void setAdapter(CommentAdapter adapter){
        adapter.bindListView(this);
    }

    public void setOnItemClick(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClick(OnItemLongClickListener listener){
        mOnItemLongClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return mOnItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener(){
        return mOnItemLongClickListener;
    }


    public static interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public static interface OnItemLongClickListener{
        public void onItemLongClick(int position);
    }
}
