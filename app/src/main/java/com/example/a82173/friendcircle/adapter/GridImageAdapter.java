package com.example.a82173.friendcircle.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by SunFly on 2016/10/27.
 */
public class GridImageAdapter extends BaseAdapter {
    private Context context;
    private List<Integer> data;

    public GridImageAdapter(Context context, List<Integer> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(data.get(position));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(5,5,5,5);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dip2px(context,88),dip2px(context,88));
            imageView.setLayoutParams(params);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("gridview image click","点击了"+position);
                    Toast.makeText(context,"点击了"+position, Toast.LENGTH_SHORT).show();
                }
            });
            convertView=imageView;
        }
        return convertView;
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
