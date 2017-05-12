package com.example.a82173.friendcircle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a82173.friendcircle.Fragment.LeftFragment;
import com.example.a82173.friendcircle.R;
import com.example.a82173.friendcircle.http.HttpLogin;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.a82173.friendcircle.activity.LoginActivity.userData;

public class ModifyPwdActivity extends SlidingFragmentActivity {
    private ImageView topButton;
    private Button modifyPwdButton;
    private EditText userpwd;
    private EditText usermodifypwd1;
    private EditText usermodifypwd2;
    private HttpLogin httpLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        initSlidingMenu();
        topButton = (ImageView) findViewById(R.id.usermenu);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        modifyPwdButton = (Button) findViewById(R.id.modify);
        userpwd = (EditText) findViewById(R.id.userpwd);
        usermodifypwd1 = (EditText) findViewById(R.id.usermodifypwd1);
        usermodifypwd2 = (EditText) findViewById(R.id.usermodifypwd2);
        httpLogin = new HttpLogin();
        modifyPwdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String edtUserpwd = userpwd.getText().toString();
                final String edtusermodifypwd1 = usermodifypwd1.getText().toString();
                final String edtusermodifypwd2 = usermodifypwd2.getText().toString();
                if (edtUserpwd.isEmpty()) {
                    Toast.makeText(ModifyPwdActivity.this, "原密码不可为空", Toast.LENGTH_SHORT).show();
                } else if (edtusermodifypwd1.isEmpty()) {
                    Toast.makeText(ModifyPwdActivity.this, "新密码不可为空", Toast.LENGTH_SHORT).show();
                } else if (edtusermodifypwd2.isEmpty()) {
                    Toast.makeText(ModifyPwdActivity.this, "请确认新密码", Toast.LENGTH_SHORT).show();
                } else if (!edtusermodifypwd2.equals(edtusermodifypwd1)) {
                    Toast.makeText(ModifyPwdActivity.this, "两次新密码输入不一样，请重新输入", Toast.LENGTH_SHORT).show();
                    usermodifypwd1.setText("");
                    usermodifypwd2.setText("");
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ModifyPwdActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String result = httpLogin.modifyPwd(userData.getUserAccount(), edtUserpwd, edtusermodifypwd2);
                                    try {
                                        JSONObject modifyResult = new JSONObject(result);
                                        if (!modifyResult.getString("check").equals("classcircle-server")) {
                                            Toast.makeText(ModifyPwdActivity.this, "网络传输故障，请稍候尝试", Toast.LENGTH_SHORT).show();
                                        } else if (!modifyResult.getString("error").isEmpty()) {
                                            Toast.makeText(ModifyPwdActivity.this, modifyResult.getString("error"), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Intent intent = new Intent();
                                            intent.setClass(ModifyPwdActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }).start();
                }
            }
        });
    }

    /**
     * 初始化侧边栏
     */
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
}
