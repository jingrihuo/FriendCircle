package com.example.a82173.friendcircle;

import android.app.Activity;
import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a82173.friendcircle.adapter.ListViewAdapter;
import com.example.a82173.friendcircle.databean.ComentData;
import com.example.a82173.friendcircle.databean.ContentData;
import com.example.a82173.friendcircle.databean.LikeData;
import com.example.a82173.friendcircle.databean.LinkData;
import com.example.a82173.friendcircle.popup.ActionItem;
import com.example.a82173.friendcircle.popup.TitlePopup;
import com.example.a82173.friendcircle.popup.Util;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TitlePopup.OnItemOnClickListener {
    private ListView mylist;
    static public TitlePopup titlePopup;
    private TextView thumbUp;
    private EditText Msg;
    private LinearLayout mAmLlLiuyan;
    private ListView listView;
    private ListViewAdapter adapter;
    private View headView;
    private List data=new ArrayList();
    private ContentData[] contentDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Msg = (EditText) findViewById(R.id.et_msg);
        mylist = (ListView) findViewById(R.id.listView);
        mAmLlLiuyan = (LinearLayout) findViewById(R.id.pinglun);
        //点击回复触发回复功能
        Button huifu = (Button) findViewById(R.id.btn_save);
        huifu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                saveComment();
            }
        });

        init();
        initData();

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
                mylist.setSelection(Y);
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
        setContentView(R.layout.activity_test);
        adapter = new ListViewAdapter(this,data);
        headView = LayoutInflater.from(this).inflate(R.layout.header, null);
        listView = (ListView) findViewById(R.id.listView);
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
    }

    private void initData(){
        //Random random = new Random();
        for(int i= 0;i<6;i++){
            data.add(contentDatas[i]);
        }
        adapter.notifyDataSetChanged();
    }

    //Adapter的构件
//    public class MyListViewAdapter extends BaseAdapter {
//        private static final int LIST1 = 1;
//        private static final int LIST2 = 2;
//        private static final int LIST3 = 3;
//        LayoutInflater inflater;
//        Context mContext;
//
//        public MyListViewAdapter(Context context) {
//            mContext = context;
//            inflater = LayoutInflater.from(mContext);
//        }
//        //有几个Item
//        public int getCount() {
//            return 3;
//        }
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        public int getItemViewType(int position) {
//            int p = position;
//            if (p == 0)
//                return LIST1;
//            else if(p == 1)
//                return LIST2;
//            else
//                return LIST3;
//        }
//
//        @Override
//        //界面的构造
//        public View getView(int position, View convertView, ViewGroup parent) {
//            final ViewHolder1 v1 = new ViewHolder1();
//            final ViewHolder1 v2 = new ViewHolder1();
//            final View myView;
//            int type = getItemViewType(position);
//            if(convertView == null) {
//                switch (type) {
//                    case LIST1:
//                        convertView = inflater.inflate(R.layout.test_2,
//                                parent, false);
//                        break;
//                    case LIST2:
//                        convertView = inflater.inflate(R.layout.test_4,
//                                parent, false);
//                        myView =  convertView;
//                        v1.btn1 = (ImageButton) convertView.findViewById(R.id.TestButton1);
//                        v1.thumbUp = (TextView) convertView.findViewById(R.id.thumbUp);
//                        v1.btn1.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                titlePopup.setAnimationStyle(R.style.cricleBottomAnimation);
//                                //当前被点击的item的点赞框
//                                titlePopup.SetThumbUp(v1.thumbUp);
//                                //保存被点击的View
//                                titlePopup.SetView(myView);
//                                titlePopup.show(view);
//                            }
//                        });
//                        break;
//                    case LIST3:
//                        convertView = inflater.inflate(R.layout.test_3,
//                                parent, false);
//                        v2.btn1 = (ImageButton) convertView.findViewById(R.id.TestButton1);
//                        v2.thumbUp = (TextView) convertView.findViewById(R.id.thumbUp);
//                        myView =  convertView;
//                        v2.btn1.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                titlePopup.setAnimationStyle(R.style.cricleBottomAnimation);
//                                titlePopup.SetThumbUp(v2.thumbUp);
//                                titlePopup.SetView(myView);
//                                titlePopup.show(view);
//                            }
//                        });
//                        break;
//                }
//            }
//            return convertView;
//        }
//        class ViewHolder1 {
//            ImageView image;
//            TextView thumbUp;
//            ImageButton btn1;
//            ImageButton btn2;
//        }
//    }
}
