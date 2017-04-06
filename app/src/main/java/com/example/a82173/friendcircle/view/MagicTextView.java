package com.example.a82173.friendcircle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.span.CircleMovementMethod;

/**
 * Created by yuritian on 2017/4/6.
 */

public class MagicTextView extends LinearLayout{
    public static final int Max_Lines = 3;
    private TextView classCircleText;
    private TextView textState;

    public MagicTextView(Context context) {
        super(context);
        init(context);
    }

    public MagicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MagicTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context){
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.magic_text,this);
        classCircleText = (TextView)findViewById(R.id.classCircleText);
        textState = (TextView)findViewById(R.id.textState);
        textState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textStr = textState.getText().toString().trim();
                if("全文".equals(textStr)){
                    classCircleText.setMaxLines(Integer.MAX_VALUE);
                    textState.setText("收起");
                }else{
                    classCircleText.setMaxLines(Max_Lines);
                    textState.setText("全文");
                }
            }
        });
    }
    public void setText(CharSequence content){
        classCircleText.setText(content);
        classCircleText.post(new Runnable() {
            @Override
            public void run() {
                int linCount = classCircleText.getLineCount();
                if(linCount>3){
                    classCircleText.setMaxLines(Max_Lines);
                    textState.setVisibility(View.VISIBLE);
                    textState.setText("全文");
                }else{
                    textState.setVisibility(View.GONE);
                }
            }
        });

        classCircleText.setMovementMethod(new CircleMovementMethod(R.color.name_selector_color,
                R.color.name_selector_color));
    }
}
