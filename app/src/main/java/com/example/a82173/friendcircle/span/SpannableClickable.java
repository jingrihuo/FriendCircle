package com.example.a82173.friendcircle.span;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.activity.MyApplication;

public abstract class SpannableClickable extends ClickableSpan implements View.OnClickListener {

    private int DEFAULT_COLOR_ID = R.color.color_8290AF;
    private int textColorId = DEFAULT_COLOR_ID;

    public SpannableClickable() {

    }

    public SpannableClickable(int textColorId){
        this.textColorId = textColorId;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        int colorValue = MyApplication.getContext().getResources().getColor(
                textColorId);
        ds.setColor(colorValue);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
