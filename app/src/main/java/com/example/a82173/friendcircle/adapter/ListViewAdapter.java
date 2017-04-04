package com.example.a82173.friendcircle.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.example.a82173.friendcircle.activity.MainActivity;
import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.customerwidget.AutoWrapLineLayout;
import com.example.a82173.friendcircle.databean.ComentData;
import com.example.a82173.friendcircle.databean.ContentData;
import com.example.a82173.friendcircle.databean.LikeData;
import com.example.a82173.friendcircle.databean.LinkData;


/**
 * Created by SunFly on 2016/10/27.
 */
public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private List<ContentData> datas;

    public ListViewAdapter(Context context, List<ContentData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ContentData data = datas.get(position);
        ItemViewHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.data_item_layout,null);
            holder = new ItemViewHolder();
            holder.menu = (ImageButton) convertView.findViewById(R.id.imageButton);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.container = (FrameLayout) convertView.findViewById(R.id.container);
            holder.link = (LinearLayout) convertView.findViewById(R.id.linkcontent);
            holder.likeOrComent = (LinearLayout) convertView.findViewById(R.id.likeOrComent);
            holder.coment = (LinearLayout) convertView.findViewById(R.id.coment);
            convertView.setTag(holder);
        }else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        final ItemViewHolder finalHolder = holder;
        final int finalPosition = position;
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.titlePopup.setAnimationStyle(R.style.cricleBottomAnimation);
                MainActivity.selectPosition = finalPosition;
                MainActivity.titlePopup.show(v);
                }
            });

        //填充用户名称
        holder.username.setText(data.getUsername());
        //填充用户发布信息
        if(data.getContent()!=null){
            holder.content.setText(data.getContent());
            holder.content.setVisibility(View.VISIBLE);
        }else{
            holder.content.setVisibility(View.GONE);
        }
        //填充用户发布的连接信息
        if(data.getLinkData()!=null){
            final LinkData linkData = data.getLinkData();
            ImageView linkimg = (ImageView) holder.link.findViewById(R.id.linkimg);
            linkimg.setImageResource(linkData.getLinkimg());
            TextView linkTitle = (TextView) holder.link.findViewById(R.id.linktitle);
            linkTitle.setText(linkData.getLinktitle());
            linkTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Link Click","将要跳转到:"+linkData.getUrl());
                    Toast.makeText(context,"将要跳转到:"+linkData.getUrl(), Toast.LENGTH_SHORT).show();
                }
            });
            holder.link.setVisibility(View.VISIBLE);
        }else{
            holder.link.setVisibility(View.GONE);
        }
        //填充用户发布图片信息
        if(data.getImages()!=null){
            if(data.getImages().size()>1) {
                GridView images = new GridView(context);
                images.setNumColumns(3);
                images.setAdapter(new GridImageAdapter(context, data.getImages()));
                images.setPadding(0, 0, 0, 0);
                int row = 1;
                if(data.getImages().size()>3){
                    row = 2;
                }else if(data.getImages().size()>6){
                    row = 3;
                }
                //指定gridview高度
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,dip2px(context,88*row));
                images.setLayoutParams(params);
                holder.container.removeAllViews();
                holder.container.addView(images);
                holder.container.setVisibility(View.VISIBLE);
            }else if(data.getImages().size()==1){
                ImageView image = new ImageView(context);
                image.setLeft(0);
                image.setImageResource(data.getImages().get(0));
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                image.setScaleType(ImageView.ScaleType.FIT_START);
                Drawable drawable = context.getResources().getDrawable(data.getImages().get(0),null);
                int dwidth = drawable.getIntrinsicWidth();
                int dheight = drawable.getIntrinsicHeight();
                if(dheight>dwidth){
                    int theight = dip2px(context,180);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        int twidth = theight*dheight/dwidth;
                    params.height = theight;
                    params.width = twidth;
                }else{
                    int twidth = dip2px(context,180);
                    int theight = twidth*dheight/dwidth;
                    params.height = theight;
                    params.width = twidth;
                }
                image.setLayoutParams(params);
                holder.container.removeAllViews();
                holder.container.addView(image);
                holder.container.setVisibility(View.VISIBLE);
            }
        }else {
            holder.container.setVisibility(View.GONE);
        }
        boolean isShowComentOrLike = false;
        //填充评论点赞数据
        AutoWrapLineLayout like = (AutoWrapLineLayout) holder.likeOrComent.findViewById(R.id.like);
        like.removeAllViews();
        LinearLayout coment = (LinearLayout) holder.likeOrComent.findViewById(R.id.coment);
        coment.removeAllViews();
        if(data.getLikeData()!= null) {
            like.setFillMode(AutoWrapLineLayout.MODE_WRAP_CONTENT);
            for(LikeData item : data.getLikeData()) {
                View likeItem = LayoutInflater.from(context).inflate(R.layout.like, null);
                TextView userName = (TextView) likeItem.findViewById(R.id.username);
                userName.setText(item.getUsername());
                like.addView(likeItem);
            }
            isShowComentOrLike = true;
        }
        if(data.getComentDatas()!=null){
            for(final ComentData item : data.getComentDatas()){
                View comentItem = LayoutInflater.from(context).inflate(R.layout.coment, null);
                if(item.getReplyUsername()!=null){
                    comentItem = LayoutInflater.from(context).inflate(R.layout.replycoment, null);
                    TextView replyusername = (TextView) comentItem.findViewById(R.id.replyusername);
                    replyusername.setText(item.getReplyUsername());
                }
                TextView content = (TextView) comentItem.findViewById(R.id.content);
                TextView username = (TextView) comentItem.findViewById(R.id.username);
                content.setText(item.getContent());
                username.setText(item.getUsername());
                comentItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, item.getContent() , Toast.LENGTH_SHORT).show();
                    }
                });
                coment.addView(comentItem);
            }
            isShowComentOrLike = true;
        }
        if(isShowComentOrLike){
            holder.likeOrComent.setVisibility(View.VISIBLE);
        }else{
            holder.likeOrComent.setVisibility(View.GONE);
        }
        return convertView;
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public class ItemViewHolder{
        public ImageButton menu;
        public TextView username;
        public TextView content;
        public FrameLayout container;
        public LinearLayout link;
        public LinearLayout likeOrComent;
        public LinearLayout coment;

        public TextView getUsername() {
            return username;
        }

        public void setUsername(TextView username) {
            this.username = username;
        }

        public TextView getContent() {
            return content;
        }

        public void setContent(TextView content) {
            this.content = content;
        }

        public FrameLayout getContainer() {
            return container;
        }

        public void setContainer(FrameLayout container) {
            this.container = container;
        }
    }

}
