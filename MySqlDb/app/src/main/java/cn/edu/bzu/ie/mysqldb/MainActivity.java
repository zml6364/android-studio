package cn.edu.bzu.ie.mysqldb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 主界面业务逻辑代码
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_query_count, btn_login;   // 查询用户数量的按钮，登录按钮
    private TextView tv_user_count;   // 用户数量文本框

    private static EditText et_material, et_diameter, et_method;  // 用户名、密码框

    public static String material, diameter, method;

    private UserDao userdao;   // 用户数据库操作类
    private Handler mainHandler ;     // 主线程

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if(msg.what==0){
                int count = (Integer)msg.obj;
                tv_user_count.setText("数据库中的用户数量为："+count);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView(){
        btn_query_count = findViewById(R.id.btn_query_count);
        tv_user_count = findViewById(R.id.tv_user_count);

        et_material = findViewById(R.id.et_material);
        et_diameter = findViewById(R.id.et_diameter);
        et_method = findViewById(R.id.et_method);

        btn_login = findViewById(R.id.btn_login);

        userdao = new UserDao();
        mainHandler = new Handler(getMainLooper());   // 获取主线程

        btn_query_count.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_query_count:   // 查询数量
                doQueryCount();
                break;

            case R.id.btn_login:    // 登录按钮
                doLogin();
                break;
        }
    }

    // 执行查询用户数量的方法
    private void doQueryCount(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = MySqlHelp.getUserSize();
                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = count;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();
    }

    // 执行登录操作
    private void doLogin(){

        material = et_material.getText().toString().trim();
        diameter = et_diameter.getText().toString().trim();
        method = et_method.getText().toString().trim();

        if(TextUtils.isEmpty(material)){
            CommonUtils.showShortMsg(this, "请输入焊接材料");
            et_material.requestFocus();
        }else if(TextUtils.isEmpty(diameter)){
            CommonUtils.showShortMsg(this, "请输入焊接直径");
            et_diameter.requestFocus();
        }else if(TextUtils.isEmpty(method)){
            CommonUtils.showShortMsg(this, "请输入焊接方法");
            et_method.requestFocus();
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Userinfo item = userdao.Query(material, diameter, method);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(item == null){
                                CommonUtils.showDlgMsg(MainActivity.this, "查询失败");
                            }else{
                                CommonUtils.showLonMsg(MainActivity.this, "查询成功");
                                // 调用参数界面
                                Intent intent = new Intent(MainActivity.this, UserManagerActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }).start();
        }
    }
}
