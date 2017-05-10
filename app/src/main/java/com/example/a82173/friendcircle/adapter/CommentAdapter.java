package com.example.a82173.friendcircle.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.activity.MyApplication;
import com.example.a82173.friendcircle.databean.ComentData;
import com.example.a82173.friendcircle.span.CircleMovementMethod;
import com.example.a82173.friendcircle.view.CommentListView;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter {

    private Context mContext;
    private CommentListView mListview;
    private List<ComentData> mDatas;

    public CommentAdapter(Context context){
        mContext = context;
        mDatas = new ArrayList<ComentData>();
    }

    public CommentAdapter(Context context, List<ComentData> datas){
        mContext = context;
        setDatas(datas);
    }

    public void bindListView(CommentListView listView){
        if(listView == null){
            throw new IllegalArgumentException("CommentListView is null....");
        }
        mListview = listView;
    }

    public void setDatas(List<ComentData> datas){
        if(datas == null ){
            datas = new ArrayList<ComentData>();
        }
        mDatas = datas;
    }

    public List<ComentData> getDatas(){
        return mDatas;
    }

    public int getCount(){
        if(mDatas == null){
            return 0;
        }
        return mDatas.size();
    }

    public ComentData getItem(int position){
        if(mDatas == null){
            return null;
        }
        if(position < mDatas.size()){
            return mDatas.get(position);
        }else{
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private View getView(final int position){

        View convertView = View.inflate(mContext,
                R.layout.im_social_item_comment, null);
        TextView commentTv = (TextView) convertView.findViewById(R.id.commentTv);
        final CircleMovementMethod circleMovementMethod =new CircleMovementMethod();

        final ComentData bean = mDatas.get(position);
        String name = bean.getUsername();
        String toReplyName = "";
        if (bean.getReplyUsername() != null) {
            toReplyName = bean.getReplyUsername();
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setClickableSpan(name));
        if (!TextUtils.isEmpty(toReplyName)) {

            builder.append(" 回复 ");
            builder.append(setClickableSpan(toReplyName));
        }
        builder.append(": ");
        //转换表情字符
        String contentBodyStr = bean.getContent();
        builder.append(contentBodyStr);
        commentTv.setText(builder);
        commentTv.setMovementMethod(circleMovementMethod);

        commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    mListview.getOnItemClickListener().onItemClick(position);
                }
            }
        });
        commentTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    mListview.getOnItemLongClickListener().onItemLongClick(position);
                    return true;
                }
                return false;
            }
        });


        return convertView;
    }

    @NonNull
    private SpannableString setClickableSpan(final String textStr) {
        final SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new ClickableSpan() {
                                    @Override
                                    public void onClick(View widget) {
                                        Toast.makeText(mContext,textStr,Toast.LENGTH_LONG).show();
                                    }
                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        int colorValue = mContext.getResources().getColor(
                                                R.color.color_8290AF);
                                        ds.setColor(colorValue);
                                        ds.setUnderlineText(false);
                                        ds.clearShadowLayer();
                                    }
                                }
                , 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    public void notifyDataSetChanged(){
        if(mListview == null){
            throw new NullPointerException("listview is null, please bindListView first...");
        }
        mListview.removeAllViews();
        if(mDatas == null || mDatas.size() == 0){
            return;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i=0; i<mDatas.size(); i++){
            final int index = i;
            View view = getView(index);
            if(view == null){
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            mListview.addView(view, index, layoutParams);
        }

    }

}
