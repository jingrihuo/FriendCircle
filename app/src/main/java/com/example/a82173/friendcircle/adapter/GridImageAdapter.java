package com.example.a82173.friendcircle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a82173.friendcircle.http.HttpImage;

import java.util.List;

/**
 * Created by SunFly on 2016/10/27.
 */
public class GridImageAdapter extends BaseAdapter {
    private Context context;
    private List<String> data;
    private HttpImage httpImage;
    private static Bitmap bitmap;

    public GridImageAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        this.httpImage = new HttpImage();
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
        if (convertView == null) {
            ImageView imageView = new ImageView(context);
            bitmap = httpImage.loadDynamicImgs(data.get(position),"/file/");
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(5, 5, 5, 5);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dip2px(context, 70), dip2px(context,70));
            imageView.setLayoutParams(params);
            convertView = imageView;
        }
        return convertView;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
