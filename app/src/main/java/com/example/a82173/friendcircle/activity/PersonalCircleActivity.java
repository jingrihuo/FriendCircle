package com.example.a82173.friendcircle.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.PopupMenu;


import com.example.a82173.friendcircle.Fragment.LeftFragment;
import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.adapter.CircleAdapter;
import com.example.a82173.friendcircle.databean.ComentData;
import com.example.a82173.friendcircle.databean.CommentConfig;
import com.example.a82173.friendcircle.databean.ContentData;
import com.example.a82173.friendcircle.databean.LikeData;
import com.example.a82173.friendcircle.presenter.CirclePresenter;
import com.example.a82173.friendcircle.presenter.view.ICircleView;
import com.bumptech.glide.Glide;
import com.example.a82173.friendcircle.view.CommentListView;
import com.example.a82173.friendcircle.view.DivItemDecoration;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.OnMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.a82173.friendcircle.activity.LoginActivity.userData;


public class PersonalCircleActivity extends SlidingFragmentActivity implements ICircleView,View.OnClickListener{

    private int mScreenHeight;
    private int mEditTextBodyHeight;
    private int mCurrentKeyboardH;
    private int mSelectCircleItemH;
    private int mSelectCommentItemOffset;

    private CirclePresenter mPresenter;
    private CircleAdapter adapter;
    private ContentData[] contentDatas;
    private SuperRecyclerView classCircleView;
    private LinearLayoutManager layoutManager;
    private CommentConfig mCommentConfig;

    private EditText mReply;
    private TextView thumbUp;
    private EditText Msg;
    private LinearLayout mAmLlLiuyan;
    private static Context mContext;
    private InputMethodManager inputMethodManager;
    private RelativeLayout bodyLayout;
    private ImageView topButton;

    private List<ContentData> data = new ArrayList<ContentData>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new CirclePresenter();
        mPresenter.attachView(this);
        mContext = this;
        mAmLlLiuyan = (LinearLayout) findViewById(R.id.comments);
        mReply = (EditText) findViewById(R.id.et_msg);
        userData.setUserType("班主任");
        userData.setUserName("Death");
        //点击回复触发回复功能
        Button huifu = (Button) findViewById(R.id.btn_save);
        topButton = (ImageView) findViewById(R.id.usermenu);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        initView();
        initSlidingMenu();
    }

    @SuppressLint({"ClickableViewAccessibility", "InlinedApi"})
    private void initView() {
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.activity_bg));

        classCircleView = (SuperRecyclerView) findViewById(R.id.classcircleView);
        layoutManager = new LinearLayoutManager(this);
        classCircleView.setLayoutManager(layoutManager);
        classCircleView.addItemDecoration(new DivItemDecoration(2, true));
        classCircleView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        classCircleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mAmLlLiuyan.getVisibility() == View.VISIBLE) {
                    EditTextReplyVisible(View.GONE, null);
                    return true;
                }
                return false;
            }
        });

        classCircleView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getDatas().addAll(data);
                        adapter.notifyDataSetChanged();
                        classCircleView.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        classCircleView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getDatas().addAll(data);
                        adapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        }, 1);

        classCircleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(PersonalCircleActivity.this).pauseRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Glide.with(PersonalCircleActivity.this).resumeRequests();
            }
        });
        adapter = new CircleAdapter(this);
        adapter.setCirclePresenter(mPresenter);
        classCircleView.setAdapter(adapter);
        initDatas();
        adapter.notifyDataSetChanged();
        setViewTreeObserver();
    }

    //加载朋友圈内容
    private void initDatas() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            ContentData contentData = new ContentData("DeathBefall", "测试测试");
            List likeDatas = new ArrayList();
            int likeSize = random.nextInt(6);
            if (likeSize > 0) {
                likeDatas.add(new LikeData("点赞"));
                contentData.setLikeData(likeDatas);
                for (int j = 1; j < likeSize; j++) {
                    contentData.getLikeData().add(new LikeData("点赞"));
                }
            }
            List comentDatas = new ArrayList();
            int comentSize = random.nextInt(6);
            if (comentSize > 0) {
                comentDatas.add(new ComentData("DeathBefall", "测试", null));
                for (int j = 1; j < likeSize; j++) {
                    int reply = random.nextInt(2);
                    if (reply == 0) {
                        comentDatas.add(new ComentData("DeathBefall", "测试", null));
                    } else {
                        comentDatas.add(new ComentData("DeathBefall", "测试", "测试"));
                    }
                }
                contentData.setComentDatas(comentDatas);
            }
            data.add(contentData);
        }
        adapter.setDatas(data);

    }

    private void setViewTreeObserver() {
        bodyLayout = (RelativeLayout) findViewById(R.id.bodyLayout);
        final ViewTreeObserver swipeRefreshLayoutVTO = bodyLayout.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                bodyLayout.getWindowVisibleDisplayFrame(r);
                int statusBarH =  getStatusBarHeight();//状态栏高度
                int screenH = bodyLayout.getRootView().getHeight();
                if(r.top != statusBarH ){
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);
                if(keyboardH == mCurrentKeyboardH){//有变化时才处理，否则会陷入死循环
                    return;
                }

                mCurrentKeyboardH = keyboardH;
                mScreenHeight = screenH;//应用屏幕的高度
                mEditTextBodyHeight = mAmLlLiuyan.getHeight();
                if(layoutManager!=null && mCommentConfig != null){
                    layoutManager.scrollToPositionWithOffset(mCommentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE, getListviewOffset(mCommentConfig)+20);
                }
            }
        });
    }
    //评论弹窗
    @Override
    public void EditTextReplyVisible(int visibility, CommentConfig config) {
        mAmLlLiuyan.setVisibility(visibility);
        mCommentConfig = config;
        measureCircleItemHighAndCommentItemOffset(config);

        if (View.VISIBLE == visibility) {
            mReply.requestFocus();
            inputMethodManager = (InputMethodManager) mReply.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else if (View.GONE == visibility) {
            inputMethodManager = (InputMethodManager) mReply.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(mReply.getWindowToken(), 0);
        }
    }

    @Override
    public void addLike(int position) {
        if (data.get(position).getLikeData() == null) {
            List likeDatas = new ArrayList();
            likeDatas.add(new LikeData(userData.getUserName()));
            data.get(position).setLikeData(likeDatas);
        } else {
            data.get(position).getLikeData().add(new LikeData(userData.getUserName()));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteLike(int position) {
        List<LikeData> items = data.get(position).getLikeData();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getUsername().equals(userData.getUserName())) {
                items.remove(i);
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int getListviewOffset(CommentConfig commentConfig) {
        RelativeLayout titleBar = (RelativeLayout) findViewById(R.id.titlebar);
        if(commentConfig == null)
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        //int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight;
        int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight - titleBar.getHeight();
        if(commentConfig.commentType == CommentConfig.Type.REPLY){
            //回复评论的情况
            listviewOffset = listviewOffset + mSelectCommentItemOffset;
        }
        return listviewOffset;
    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return;

        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = layoutManager.getChildAt(commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE - firstPosition);

        if (selectCircleItem != null) {
            mSelectCircleItemH = selectCircleItem.getHeight();
        }

        if (commentConfig.commentType == CommentConfig.Type.REPLY) {
            //回复评论的情况
            CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
            if (commentLv != null) {
                //找到要回复的评论view,计算出该view距离所属动态底部的距离
                View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                if (selectCommentItem != null) {
                    //选择的commentItem距选择的CircleItem底部的距离
                    mSelectCommentItemOffset = 0;
                    View parentView = selectCommentItem;
                    do {
                        int subItemBottom = parentView.getBottom();
                        parentView = (View) parentView.getParent();
                        if (parentView != null) {
                            mSelectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                        }
                    } while (parentView != null && parentView != selectCircleItem);
                }
            }
        }
    }
    private void initSlidingMenu() {

        // 设置左侧滑动菜单
        setBehindContentView(R.layout.menu_frame_left);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, new LeftFragment()).commit();

        // 实例化滑动菜单对象
        SlidingMenu sm = getSlidingMenu();
        // 设置可以左右滑动的菜单
        sm.setMode(SlidingMenu.LEFT);
        // 设置滑动阴影的宽度
        sm.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动菜单阴影的图像资源
        sm.setShadowDrawable(null);
        // 设置滑动菜单视图的宽度
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        sm.setFadeDegree(0.35f);
        // 设置触摸屏幕的模式,这里设置为全屏
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置下方视图的在滚动时的缩放比例
        sm.setBehindScrollScale(0.0f);

    }


    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void onClick(View v) {

    }
}
