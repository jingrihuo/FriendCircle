package com.example.a82173.friendcircle;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;


import com.example.a82173.friendcircle.adapter.ListViewAdapter;
import com.example.a82173.friendcircle.databean.ComentData;
import com.example.a82173.friendcircle.databean.ContentData;
import com.example.a82173.friendcircle.databean.LikeData;
import com.example.a82173.friendcircle.databean.LinkData;
import com.example.a82173.friendcircle.json.HttpHandler;
import com.example.a82173.friendcircle.popup.ActionItem;
import com.example.a82173.friendcircle.popup.TitlePopup;
import com.example.a82173.friendcircle.popup.Util;
import com.example.a82173.friendcircle.sqlite.UserDBHelper;
import com.example.a82173.friendcircle.view.EyeView;
import com.example.a82173.friendcircle.view.PullDownListView;
import com.example.a82173.friendcircle.view.YProgressView;

import java.util.ArrayList;
import java.util.List;


import static com.example.a82173.friendcircle.json.TestJson.testJson;


public  class MainActivity extends Activity implements TitlePopup.OnItemOnClickListener{
    static public TitlePopup titlePopup;
    private TextView thumbUp;
    private EditText Msg;
    private LinearLayout mAmLlLiuyan;
    private ListView listView;
    private ListViewAdapter adapter;
    private View headView;
    private List data=new ArrayList();
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

        for(int i= 0;i<contentDatas.length;i++){
            data.add(contentDatas[i]);
        }
        adapter.notifyDataSetChanged();

        Msg = (EditText) findViewById(R.id.et_msg);
        mAmLlLiuyan = (LinearLayout) findViewById(R.id.pinglun);
        //点击回复触发回复功能
//        Button huifu = (Button) findViewById(R.id.btn_save);
//        huifu.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveComment();
//            }
//        });
        dbHelper = new UserDBHelper(MainActivity.this,"user_db",null,1);
        db =dbHelper.getReadableDatabase();
//        String str = testJson(this);
        HttpHandler httpHandler = new HttpHandler();
        httpHandler.SetHttpJson();
        savesql();
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
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_compose:
                Toast.makeText(this, "Compose", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //赞和评论被点击后触发的事件
    public void onItemClick(ActionItem item, int position) {
        switch (position){
            case 1:
                mAmLlLiuyan.setVisibility(View.VISIBLE);
                View listItem = titlePopup.GetView();
                int Y = listItem.getMeasuredHeight();
                listView.setSelection(Y);
                onFocusChange(true);
                break;
            case 0:
                Toast.makeText(this,"点赞成功",Toast.LENGTH_SHORT).show();
                String OldthumbUp = titlePopup.GetThumbUp().getText().toString();
                String NewthumbUp = ",点赞";
                OldthumbUp = OldthumbUp+NewthumbUp;
                titlePopup.GetThumbUp().setText(OldthumbUp);
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
//        setContentView(R.layout.activity_test2);
        adapter = new ListViewAdapter(this,data);
        headView = LayoutInflater.from(this).inflate(R.layout.header, null);
        listView = (ListView)pullDownListView.getListView();
        listView.addHeaderView(headView, null, false);
        listView.setAdapter(adapter);
        ContentData contentData = new ContentData("测试1","内容测试，夕阳西下，断桥残雪");
        List images = new ArrayList();
        images.add(R.drawable.header);
        images.add(R.drawable.header);
        images.add(R.drawable.header);
        images.add(R.drawable.header);
        images.add(R.drawable.header);
        ContentData imagesData = new ContentData(images,"测试2","多图测试");
        List image = new ArrayList();
        image.add(R.drawable.headerbg);
        ContentData imageData = new ContentData(image,"测试3","单图测试");
        ContentData linkData = new ContentData("测试4","链接测试：https://www.baidu.com",new LinkData("https://www.baidu.com",R.drawable.header,"作为一个连接标题，我也是有个性的"));
        ContentData likeData = new ContentData("测试5","点赞测试");
        List likeDatas = new ArrayList();
        likeDatas.add(new LikeData("测试1"));
        likeDatas.add(new LikeData("测试2"));
        likeDatas.add(new LikeData("测试3"));
        likeDatas.add(new LikeData("测试4"));
        likeData.setLikeData(likeDatas);

        ContentData comentData = new ContentData("测试6","评论测试");
        List comentDatas = new ArrayList();
        comentDatas .add(new ComentData("测试1", "你这个是错误的答案", "测试2"));
        comentDatas .add(new ComentData("测试2", "我这个是正确的答案", "测试1"));
        comentDatas .add(new ComentData("测试3", "静静的看楼上在装", null));
        comentDatas .add(new ComentData("测试4", "+1", null));
        comentData.setComentDatas(comentDatas);
        comentData.setLikeData(likeDatas);
        contentDatas = new ContentData[]{contentData,imagesData,imageData,linkData,likeData,comentData};


//        Cursor cursor =db.rawQuery("select * from friendcircle",null);
//        int i = 0;
//        contentDatas = new ContentData[7];
//        while (cursor.moveToNext()) {
//            if (cursor.getString(cursor.getColumnIndex("megimage1"))==null)
//            {
//                ContentData contentData = new ContentData("测试1",cursor.getString(cursor.getColumnIndex("megstring")));
//                contentDatas[i] = contentData;
//            }
//            else if (cursor.getString(cursor.getColumnIndex("megimage2"))==null)
//            {
//
//                List image = new ArrayList();
//                image.add(cursor.getInt(cursor.getColumnIndex("megimage1")));
//                ContentData imageData = new ContentData(image,"测试3",cursor.getString(cursor.getColumnIndex("megstring")));
//                contentDatas[i] = imageData;
//            }else {
//                List images = new ArrayList();
//                images.add(cursor.getInt(cursor.getColumnIndex("megimage1")));
//                images.add(cursor.getInt(cursor.getColumnIndex("megimage"+2)));
//                int num = 3;
//                while (true){
//                    if (cursor.getString(cursor.getColumnIndex("megimage"+num))==null)
//                    {
//                        break;
//                    }
//                    images.add(cursor.getInt(cursor.getColumnIndex("megimage"+num)));
//                    num++;
//                }
//                ContentData imageData = new ContentData(images,"测试3",cursor.getString(cursor.getColumnIndex("megstring")));
//                contentDatas[i] = imageData;
//            }
//            i++;
//        }
//        cursor.close();

    }

    private void initData(){
        //Random random = new Random();
        for(int i= 0;i<contentDatas.length;i++){
            data.add(contentDatas[i]);
            Toast.makeText(MainActivity.this,""+i,Toast.LENGTH_LONG).show();
        }
        adapter.notifyDataSetChanged();
    }

    private void savesql(){
        int i = R.drawable.header;
//        String sql = "insert into user(username,userimage) values('DeathBefall',2130837616)";
//        db.execSQL(sql);
    }
}
