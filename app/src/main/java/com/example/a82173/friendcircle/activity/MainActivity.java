package com.example.a82173.friendcircle.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;


import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.adapter.ListViewAdapter;
import com.example.a82173.friendcircle.databean.ComentData;
import com.example.a82173.friendcircle.databean.ContentData;
import com.example.a82173.friendcircle.databean.LikeData;
import com.example.a82173.friendcircle.json.HttpHandler;
import com.example.a82173.friendcircle.popup.ActionItem;
import com.example.a82173.friendcircle.popup.TitlePopup;
import com.example.a82173.friendcircle.popup.Util;
import com.example.a82173.friendcircle.sqlite.UserDBHelper;
import com.example.a82173.friendcircle.view.EyeView;
import com.example.a82173.friendcircle.view.PullDownListView;
import com.example.a82173.friendcircle.view.YProgressView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public  class MainActivity extends Activity implements TitlePopup.OnItemOnClickListener{
    static public TitlePopup titlePopup;
    static public int selectPosition;
    private TextView thumbUp;
    private EditText Msg;
    private LinearLayout mAmLlLiuyan;
    private ListView listView;
    private ListViewAdapter adapter;
    private View headView;
    private List data = new ArrayList();
    private ContentData[] contentDatas;
    public static UserDBHelper dbHelper;
    public static SQLiteDatabase db;
    Context mContext;
    PullDownListView pullDownListView;
    YProgressView progressView;
    EyeView eyeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext = this;
        pullDownListView = (PullDownListView) this
                .findViewById(R.id.pullDownListView);
        progressView = (YProgressView) this
                .findViewById(R.id.progressView);
        eyeView = (EyeView) this.findViewById(R.id.eyeView);

        Msg = (EditText) findViewById(R.id.et_msg);
        mAmLlLiuyan = (LinearLayout) findViewById(R.id.pinglun);
        //点击回复触发回复功能
        Button huifu = (Button) findViewById(R.id.btn_save);
        huifu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                saveComment();
            }
        });
        dbHelper = new UserDBHelper(MainActivity.this,"user_db",null,1);
        db =dbHelper.getReadableDatabase();
        HttpHandler httpHandler = new HttpHandler();
        httpHandler.SetHttpJson();
        init();
        initData();


        pullDownListView.setOnPullHeightChangeListener(new PullDownListView.OnPullHeightChangeListener(){
            @Override
            public void onTopHeightChange(int headerHeight,
                                          int pullHeight) {
                // TODO Auto-generated method stub
                float progress = (float) pullHeight
                        / (float) headerHeight;

                if(progress<0.5){
                    progress = 0.0f;
                }else{
                    progress = (progress-0.5f)/0.5f;
                }


                if (progress > 1.0f) {
                    progress = 1.0f;
                }

                if (!pullDownListView.isRefreshing()) {
                    eyeView.setProgress(progress);
                }
            }

            @Override
            public void onBottomHeightChange(int footerHeight,
                                             int pullHeight) {
                // TODO Auto-generated method stub
                float progress = (float) pullHeight
                        / (float) footerHeight;

                if(progress<0.5){
                    progress = 0.0f;
                }else{
                    progress = (progress-0.5f)/0.5f;
                }

                if (progress > 1.0f) {
                    progress = 1.0f;
                }

                if (!pullDownListView.isRefreshing()) {
                    progressView.setProgress(progress);
                }

            }

            @Override
            public void onRefreshing(final boolean isTop) {
                // TODO Auto-generated method stub
                if (isTop) {
                    eyeView.startAnimate();
                } else {
                    progressView.startAnimate();
                }

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        pullDownListView.pullUp();
                        if (isTop) {
                            eyeView.stopAnimate();
                            //刷新完调用的函数
                            initData();
                        } else {
                            progressView.stopAnimate();
                            initData();
                        }
                    }

                }, 3000);
            }

        });
        titlePopup = new TitlePopup(this, Util.dip2px(this, 165), Util.dip2px(
                this, 40));
        titlePopup
                .addAction(new ActionItem(this, "赞", R.drawable.circle_praise));
        titlePopup
                .addAction(new ActionItem(this, "评论", R.drawable.circle_comment));
        titlePopup.setItemOnClickListener(this);
    }
    //根据menu创建ActionBar选项
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        //获取实例
        SearchView searchView = (SearchView) searchItem.getActionView();
        return super.onCreateOptionsMenu(menu);
    }
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_compose:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,NewFriendCircle.class);
                MainActivity.this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //赞和评论被点击后触发的事件
    public void onItemClick(ActionItem item, int position) {
        View SelectView = adapter.getView(selectPosition,null,pullDownListView.getListView());
        switch (position){
            case 1:
                mAmLlLiuyan.setVisibility(View.VISIBLE);
                int Y = SelectView.getHeight();
                if(adapter.getCount() == selectPosition + 1) {
                    pullDownListView.getListView().setSelection(pullDownListView.getListView().getBottom());
                }else{
                    pullDownListView.getListView().setSelectionFromTop(selectPosition+1,0);
                }
                onFocusChange(true);
                break;
            case 0:
                ContentData test = (ContentData) data.get(selectPosition);
                if(test.getLikeData() == null){
                    List likeDatas = new ArrayList();
                    likeDatas.add(new LikeData("点赞"));
                    test.setLikeData(likeDatas);
                }else{
                    test.getLikeData().add(new LikeData("点赞"));
                }
                data.set(selectPosition,test);
                db = dbHelper.getReadableDatabase();
                String sql = "update friendcircle set thumbup=thumbup+1 where megnumber = ?";
                db.execSQL(sql,new Object[]{test.getMegnumber()});
                db.close();
                adapter.notifyDataSetChanged();
                break;
            default:
        }
    }

    //ActionBar的Title的文字居中显示
    public static void centerActionBarTitle(Activity activity)
    {
        int titleId = activity.getResources().getIdentifier("action_bar_title", "id", "android");
        if (titleId<=0)return;
        TextView titleTextView = (TextView)activity.findViewById(titleId);
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        LinearLayout.LayoutParams txvPars = (LinearLayout.LayoutParams) titleTextView.getLayoutParams();
        txvPars.gravity = Gravity.CENTER_HORIZONTAL;
        txvPars.width = metrics.widthPixels;
        titleTextView.setLayoutParams(txvPars);
        titleTextView.setGravity(Gravity.CENTER);
    }
    //回复按钮被点击后触发的事件
        private void saveComment() {
        //判断是否有输入
        if (!TextUtils.isEmpty(Msg.getText())) {
            ContentData test = (ContentData) data.get(selectPosition);
            if(test.getComentDatas() == null) {
                List comentDatas = new ArrayList();
                comentDatas.add(new ComentData("DeathBefall",Msg.getText().toString(), null));
                test.setComentDatas(comentDatas);
            }
            else {
                test.getComentDatas().add(new ComentData("DeathBefall", Msg.getText().toString(), null));
            }
            data.set(selectPosition,test);
            db = dbHelper.getReadableDatabase();
            String sql = "insert into comments(megnumber, commentsstring) values(?,?)";
            db.execSQL(sql,new Object[]{test.getMegnumber(),Msg.getText().toString()});
            db.close();
            adapter.notifyDataSetChanged();
            mAmLlLiuyan.setVisibility(View.GONE);
            onFocusChange(false);
        } else {
            Toast.makeText(this, "请输入内容后在留言", Toast.LENGTH_SHORT).show();
        }
    }

    //输入法显示和隐藏
    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        MainActivity.this.getSystemService(INPUT_METHOD_SERVICE);
                if (isFocus) {
                    //显示输入法
                    Msg.setFocusable(true);
                    Msg.requestFocus();
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(Msg.getWindowToken(), 0);
                }
            }
        }, 100);
    }
    private void init(){
        adapter = new ListViewAdapter(this,data);
        headView = LayoutInflater.from(this).inflate(R.layout.header, null);
        listView = (ListView)pullDownListView.getListView();
        listView.addHeaderView(headView, null, false);
        listView.setAdapter(adapter);

        Cursor cursor =db.rawQuery("select * from friendcircle",null);
        while (cursor.moveToNext()) {
            ContentData contentData = new ContentData("DeathBefall",cursor.getString(cursor.getColumnIndex("megstring")));
            contentData.setMegnumber(cursor.getInt(cursor.getColumnIndex("megnumber")));
            if (cursor.getString(cursor.getColumnIndex("megimage1"))!=null && cursor.getString(cursor.getColumnIndex("megimage2")) == null)
            {
                List image = new ArrayList();
                image.add(cursor.getInt(cursor.getColumnIndex("megimage1")));
                contentData.setImages(image);
            }else {
                List images = new ArrayList();
                images.add(cursor.getInt(cursor.getColumnIndex("megimage1")));
                images.add(cursor.getInt(cursor.getColumnIndex("megimage"+2)));
                int num = 3;
                while (true){
                    if (cursor.getString(cursor.getColumnIndex("megimage"+num))==null)
                    {
                        break;
                    }
                    images.add(cursor.getInt(cursor.getColumnIndex("megimage"+num)));
                    num++;
                }
                contentData.setImages(images);
            }
            if (cursor.getInt(cursor.getColumnIndex("thumbup")) != 0){
                List likeDatas = new ArrayList();
                likeDatas.add(new LikeData("点赞"));
                contentData.setLikeData(likeDatas);
                for(int i = 2; i <= cursor.getInt(cursor.getColumnIndex("thumbup"));i++){
                  contentData.getLikeData().add(new LikeData("点赞"));
                }
            }
            int megnumber = cursor.getInt(cursor.getColumnIndex("megnumber"));
            Cursor commentsstring =db.rawQuery("select * from comments where megnumber = ?",new String[]{Integer.toString(megnumber)});
            List comentDatas = new ArrayList();
            while (commentsstring.moveToNext()){
                comentDatas.add(new ComentData("DeathBefall", commentsstring.getString(commentsstring.getColumnIndex("commentsstring")) , null));
                contentData.setComentDatas(comentDatas);
            }
            data.add(contentData);
        }
        cursor.close();
    }

    private void initData(){
        adapter.notifyDataSetChanged();
    }

}
