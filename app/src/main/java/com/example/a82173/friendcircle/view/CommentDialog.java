package com.example.a82173.friendcircle.view;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.databean.ComentData;
import com.example.a82173.friendcircle.presenter.CirclePresenter;

import static com.example.a82173.friendcircle.activity.LoginActivity.userData;

/**
 * Created by yuritian on 2017/4/18.
 */

public class CommentDialog extends Dialog implements View.OnClickListener{
    private Context mContext;
    private ComentData comentDataUser;
    private CirclePresenter mPresenter;
    private int mCirclePosition;

    public CommentDialog(Context context,ComentData userComent,
                         CirclePresenter presenter,int circlePosition) {
        super(context,R.style.comment_dialog);
        this.mContext = context;
        this.comentDataUser = userComent;
        this.mPresenter = presenter;
        this.mCirclePosition = circlePosition;
    }



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comment);
        initWindowsManager();
        initView();
    }
    private void initWindowsManager(){
        Window window = getWindow();
        WindowManager windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int)(display.getWidth()*0.7);
        window.setGravity(Gravity.CENTER);
        window.setAttributes(layoutParams);
    }
    private void initView(){
        TextView copy = (TextView) findViewById(R.id.copy);
        copy.setOnClickListener(this);
        TextView delete = (TextView) findViewById(R.id.delete);
        if (comentDataUser.getUsername().equals(userData.getUserName())){
            delete.setVisibility(View.VISIBLE);
        }else {
            delete.setVisibility(View.GONE);
        }
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.copy:
                ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(comentDataUser.getContent());
                dismiss();
                break;
            case R.id.delete:
                dismiss();
        }
    }
}
