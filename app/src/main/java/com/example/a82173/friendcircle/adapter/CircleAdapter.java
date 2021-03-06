package com.example.a82173.friendcircle.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.customerwidget.AutoWrapLineLayout;
import com.example.a82173.friendcircle.databean.ComentData;
import com.example.a82173.friendcircle.databean.CommentConfig;
import com.example.a82173.friendcircle.databean.ContentData;
import com.example.a82173.friendcircle.databean.LikeData;
import com.example.a82173.friendcircle.http.HttpDynamic;
import com.example.a82173.friendcircle.http.HttpImage;
import com.example.a82173.friendcircle.popup.ActionItem;
import com.example.a82173.friendcircle.popup.TitlePopup;
import com.example.a82173.friendcircle.presenter.CirclePresenter;
import com.example.a82173.friendcircle.view.CommentDialog;
import com.example.a82173.friendcircle.view.CommentListView;
import com.example.a82173.friendcircle.view.MagicTextView;

import me.iwf.photopicker.PhotoPicker;

import static com.example.a82173.friendcircle.activity.LoginActivity.userData;

/**
 * Created by yuritian on 2017/4/19.
 */

public class CircleAdapter extends BaseRecycleViewAdapter {
    public final static int TYPE_HEAD = 0;
    public final static int HEADVIEW_SIZE = 1;
    public static int TYPE_IMG = 0;
    public static int TYPE_Activity = 0;
    public ImageView headView;
    public ImageView bgView;

    private Context context;
    private Activity activity;
    private CirclePresenter circlePresenter;
    private HttpDynamic httpDynamic = new HttpDynamic();


    public CircleAdapter(Context context,Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void setCirclePresenter(CirclePresenter circlePresenter) {
        this.circlePresenter = circlePresenter;
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }
        return 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == 0) {
            View headview = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            viewHolder = new HeaderViewHolder(headview);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item_layout, parent, false);
            viewHolder = new CircleViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (position == 0) {
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            holder.userName.setText(userData.getUserName());
            if (userData.getUserBG()!=null){
                Glide.with(context).load("http://118.89.173.107:8080/ClassCircle/cover/"+ userData.getUserBG()).into(headView);
            }
            if (userData.getUserHeadBg()!=null){
                Glide.with(context).load("http://118.89.173.107:8080/ClassCircle/head/"+ userData.getUserHeadBg()).into(bgView);
            }
            if (TYPE_Activity == 0){
                headView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TYPE_IMG  = 1;
                        PhotoPicker.builder()
                                .setPhotoCount(1)
                                .setShowCamera(true)
                                .setShowGif(false)
                                .setPreviewEnabled(false)
                                .start(activity,PhotoPicker.REQUEST_CODE);
                    }
                });
                bgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TYPE_IMG  = 0;
                        PhotoPicker.builder()
                                .setPhotoCount(1)
                                .setShowCamera(true)
                                .setShowGif(false)
                                .setPreviewEnabled(false)
                                .start(activity,PhotoPicker.REQUEST_CODE);
                    }
                });
            }
        } else {
            final int circlePosition = position - HEADVIEW_SIZE;
            final ContentData data = (ContentData) datas.get(circlePosition);
            final CircleViewHolder holder = (CircleViewHolder) viewHolder;
            holder.userName.setText(data.getUsername());
            holder.userCreatTime.setText("20小时");
            //填充用户发布的文字信息
            if (data.getContent() != null) {
                holder.userContent.setText(data.getContent());
            }
            holder.userContent.setVisibility(data.getContent() != null ? View.VISIBLE : View.GONE);
            if (userData.getUserType().equals("班主任")) {
                holder.deleteBtn.setVisibility(View.VISIBLE);
            } else if (userData.getUserName().equals(data.getUsername())) {
                holder.deleteBtn.setVisibility(View.VISIBLE);
            } else {
                holder.deleteBtn.setVisibility(View.GONE);
            }
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    circlePresenter.deleteDynamic(data.getMegnumber());
                }
            });
            if (data.isLike()) {
                holder.titlePopup.getmActionItems().get(0).mTitle = "取消";
            } else {
                holder.titlePopup.getmActionItems().get(0).mTitle = "赞";
            }
            holder.titlePopup.update();
            holder.titlePopup.setItemOnClickListener(new PopuItemClickLisetener(circlePosition, data));

            holder.likeOrComentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.titlePopup.show(v);
                }
            });

            if (data.getImages() != null) {
                if (data.getImages().size() > 1) {
                    GridView images = new GridView(context);
                    images.setNumColumns(3);
                    images.setAdapter(new GridImageAdapter(context, data.getImages()));
                    images.setPadding(0, 0, 0, 0);
                    int row = 1;
                    if (data.getImages().size() > 3) {
                        row = 2;
                    } else if (data.getImages().size() > 6) {
                        row = 3;
                    }
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dip2px(context, 88 * row));
                    images.setLayoutParams(params);
                    holder.contentImgs.removeAllViews();
                    holder.contentImgs.addView(images);
                    holder.contentImgs.setVisibility(View.VISIBLE);
                } else if (data.getImages().size() == 1) {
                    ImageView image = new ImageView(context);
                    Glide.with(context).load("http://192.168.1.10:8080/ClassCircle/file/"+ data.getImages().get(0)).into(image);
                    image.setLeft(0);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    image.setLayoutParams(params);
                    holder.contentImgs.removeAllViews();
                    holder.contentImgs.addView(image);
                    holder.contentImgs.setVisibility(View.VISIBLE);
                }
            } else {
                holder.contentImgs.setVisibility(View.GONE);
            }


            if (data.getLikeData() != null || data.getComentDatas() != null) {
                if (data.getLikeData() != null) {
                    AutoWrapLineLayout like = (AutoWrapLineLayout) holder.likeOrComent.findViewById(R.id.like);
                    like.removeAllViews();
                    if (data.getLikeData() != null) {
                        like.setFillMode(AutoWrapLineLayout.MODE_WRAP_CONTENT);
                        for (LikeData item : data.getLikeData()) {
                            View likeItem = LayoutInflater.from(context).inflate(R.layout.like, null);
                            TextView userName = (TextView) likeItem.findViewById(R.id.username);
                            userName.setText(item.getUsername());
                            like.addView(likeItem);
                        }
                    }
                } else {
                    AutoWrapLineLayout like = (AutoWrapLineLayout) holder.likeOrComent.findViewById(R.id.like);
                    like.removeAllViews();
                }
                if (data.getComentDatas() != null) {
                    holder.commentList.setOnItemClick(new CommentListView.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            ComentData comentData = data.getComentDatas().get(position);
                            if (userData.getUserName().equals(comentData.getUsername())) {
                                CommentDialog dialog = new CommentDialog(context, comentData, circlePresenter, circlePosition);
                                dialog.show();
                            } else {
                                CommentConfig config = new CommentConfig();
                                config.circlePosition = circlePosition;
                                config.commentPosition = position;
                                config.commentType = CommentConfig.Type.REPLY;
                                config.replyUserAccont = comentData.getUserAccount();
                                config.replyUser = comentData.getUsername();
                                circlePresenter.showReplyEditText(config);
                            }
                        }
                    });
                    holder.commentList.setOnItemLongClick(new CommentListView.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(int position) {
                            ComentData comentData = data.getComentDatas().get(position);
                            CommentDialog dialog = new CommentDialog(context, comentData, circlePresenter, circlePosition);
                            dialog.show();
                        }
                    });
                    holder.commentAdapter.setDatas(data.getComentDatas());
                    holder.commentAdapter.notifyDataSetChanged();
                    holder.commentList.setVisibility(View.VISIBLE);
                } else {
                    holder.commentList.setVisibility(View.GONE);
                }
                holder.likeOrComent.setVisibility(View.VISIBLE);
            } else {
                holder.likeOrComent.setVisibility(View.GONE);
            }
            if (data.getLikeData() != null && data.getComentDatas() != null) {
                holder.digLine.setVisibility(View.VISIBLE);
            } else {
                holder.digLine.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            headView = (ImageView) itemView.findViewById(R.id.headerbg);
            bgView = (ImageView) itemView.findViewById(R.id.header);
            userName = (TextView) itemView.findViewById(R.id.username);
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public class CircleViewHolder extends RecyclerView.ViewHolder {
        /* 动态的内容 */
        public TextView userName;
        public ImageView userImg;
        public MagicTextView userContent;
        public TextView userCreatTime;
        /* 图片部分 */
        public FrameLayout contentImgs;
        /* 点赞和评论部分 */
        public LinearLayout likeOrComent;
        public CommentListView commentList;
        public ImageView likeOrComentButton;
        public CommentAdapter commentAdapter;
        public TitlePopup titlePopup;
        public View digLine;
        /* 动态操作部分*/
        public TextView deleteBtn;

        public CircleViewHolder(View convertView) {
            super(convertView);
            likeOrComentButton = (ImageButton) convertView.findViewById(R.id.likeOrComentButton);
            userName = (TextView) convertView.findViewById(R.id.username);
            userImg = (ImageView) convertView.findViewById(R.id.headimg);
            userContent = (MagicTextView) convertView.findViewById(R.id.content);
            digLine = convertView.findViewById(R.id.lin_dig);
            contentImgs = (FrameLayout) convertView.findViewById(R.id.container);
            likeOrComent = (LinearLayout) convertView.findViewById(R.id.likeOrComent);
            userCreatTime = (TextView) convertView.findViewById(R.id.createTime);
            deleteBtn = (TextView) convertView.findViewById(R.id.deleteBtn);

            commentList = (CommentListView) convertView.findViewById(R.id.commentList);
            commentAdapter = new CommentAdapter(convertView.getContext());
            commentList.setAdapter(commentAdapter);

            titlePopup = new TitlePopup(itemView.getContext());
        }
    }

    private class PopuItemClickLisetener implements TitlePopup.OnItemOnClickListener {
        private int circlePosition;
        private long mLastTime = 0;
        private ContentData mContentData;

        public PopuItemClickLisetener(int position, ContentData contentData) {
            this.circlePosition = position;
            this.mContentData = contentData;
        }

        @Override
        public void onItemClick(ActionItem item, int position) {
            switch (position) {
                case 0:
                    if (System.currentTimeMillis() - mLastTime < 700)
                        return;
                    mLastTime = System.currentTimeMillis();
                    if (circlePresenter != null) {
                        if ("赞".equals(item.mTitle.toString())) {
                            circlePresenter.addLike(circlePosition);
                        } else {
                            circlePresenter.deleteLike(circlePosition);
                        }
                    }
                    break;
                case 1:
                    if (circlePresenter != null) {
                        CommentConfig config = new CommentConfig();
                        config.commentType = CommentConfig.Type.PUBLIC;
                        config.circlePosition = circlePosition;
                        circlePresenter.showReplyEditText(config);
                    }
            }
        }
    }
}
