package cn.edu.bzu.ie.mysqldb;

import static cn.edu.bzu.ie.mysqldb.MainActivity4.s_diameter;
import static cn.edu.bzu.ie.mysqldb.MainActivity4.s_material;
import static cn.edu.bzu.ie.mysqldb.MainActivity4.s_method;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * 用户管理界面业务逻辑
 */
public class UserManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btn_return;   // 返回图片按钮 ，添加图片按钮

    private UserDao userDao;    // 用户数据库操作实例

    private List<Userinfo> userinfoList;   // 用户数据集合

    private LvUserinfoAdapter lvUserinfoAdapter;   // 用户信息数据适配器

    private ListView lv_user;   // 用户列表组件

    private Handler mainHandler;   // 主线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);

        initView();
        loadUserDb();

    }

    private void initView(){
        btn_return = findViewById(R.id.btn_return);

        lv_user = findViewById(R.id.lv_user);

        userDao = new UserDao();
        mainHandler = new Handler(getMainLooper());

        btn_return.setOnClickListener(this);

    }

    private void loadUserDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                userinfoList = userDao.getDataByMaterialAndDiameterAndMethod(s_material, s_diameter, s_method) ;// 查询参数
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData();
                    }
                });
            }
        }).start();
    }

    // 显示列表数据的方法
    private void showLvData() {
        if (lvUserinfoAdapter == null) {   // 首次加载时的操作
            lvUserinfoAdapter = new LvUserinfoAdapter(this, userinfoList);
            lv_user.setAdapter(lvUserinfoAdapter);
        } else {   // 更新数据时的操作
            lvUserinfoAdapter.setUserinfoList(userinfoList);
            lvUserinfoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_return:
                finish();
                break;
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==1){   // 操作成功
            loadUserDb();   // 重新加载数据
        }
    }*/
}
