package cn.edu.bzu.ie.mysqldb;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity implements View.OnClickListener {
//public class MainActivity4 extends AppCompatActivity {
    private ImageButton bt_material, bt_diameter, bt_method;
    private static EditText et_material, et_diameter, et_method;
    private List<String> materials, diameters, methods;
    private PopupWindow pw_material, pw_diameter, pw_method;
    private View rl_material, rl_diameter, rl_method;

    private Button btn_login;   // 查询用户数量的按钮，登录按钮

    public static String s_material, s_diameter, s_method;

    private UserDao userdao;   // 用户数据库操作类
    private Handler mainHandler ;     // 主线程

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dropdown);

        initView();

        DA_Material da_material = new DA_Material(MainActivity4.this, getMaterials());
        ListView lv_materials = new ListView(MainActivity4.this);
        lv_materials.setAdapter(da_material);
        //textView
        bt_material.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //构造方法写在onCreate方法体中会因为布局没有加载完毕而得不到宽高。
                if (null == pw_material) {
                    //创建一个在输入框下方的popup
                    pw_material = new PopupWindow(lv_materials, rl_material.getWidth(), materials.size() * rl_material.getHeight());
                    pw_material.showAsDropDown(rl_material);

                    pw_material.setFocusable(true);
                    pw_material.setOutsideTouchable(true);
                    pw_material.setBackgroundDrawable(new BitmapDrawable());
                }
                else {
                    if (pw_material.isShowing()) {
                        pw_material.dismiss();
                    }
                    else {
                        pw_material.showAsDropDown(et_material);
                    }
                }
            }
        });

        DA_Diameter da_diameter = new DA_Diameter(MainActivity4.this, getDiameters());
        ListView lv_diameters = new ListView(MainActivity4.this);
        lv_diameters.setAdapter(da_diameter);
        bt_diameter.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //构造方法写在onCreate方法体中会因为布局没有加载完毕而得不到宽高。
                if (null == pw_diameter) {
                    //创建一个在输入框下方的popup
                    pw_diameter = new PopupWindow(lv_diameters, rl_diameter.getWidth(), diameters.size() * rl_diameter.getHeight());
                    pw_diameter.showAsDropDown(rl_diameter);

                    pw_diameter.setFocusable(true);
                    pw_diameter.setOutsideTouchable(true);
                    pw_diameter.setBackgroundDrawable(new BitmapDrawable());
                }
                else {
                    if (pw_diameter.isShowing()) {
                        pw_diameter.dismiss();
                    }
                    else {
                        pw_diameter.showAsDropDown(et_diameter);
                    }
                }
            }
        });

        DA_Method da_method = new DA_Method(MainActivity4.this, getMethods());
        ListView lv_methods = new ListView(MainActivity4.this);
        lv_methods.setAdapter(da_method);
        bt_method.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //构造方法写在onCreate方法体中会因为布局没有加载完毕而得不到宽高。
                if (null == pw_method) {
                    //创建一个在输入框下方的popup
                    pw_method = new PopupWindow(lv_methods, rl_method.getWidth(), methods.size() * rl_method.getHeight());
                    pw_method.showAsDropDown(rl_method);

                    pw_method.setFocusable(true);
                    pw_method.setOutsideTouchable(true);
                    pw_method.setBackgroundDrawable(new BitmapDrawable());
                }
                else {
                    if (pw_method.isShowing()) {
                        pw_method.dismiss();
                    }
                    else {
                        pw_method.showAsDropDown(et_method);
                    }
                }
            }
        });

        doLogin();
    }

    private void initView(){
        rl_material = findViewById(R.id.rl_material);
        bt_material = (ImageButton)findViewById(R.id.bt_material);
        et_material = (EditText)findViewById(R.id.et_material);

        rl_diameter = findViewById(R.id.rl_diameter);
        bt_diameter = (ImageButton)findViewById(R.id.bt_diameter);
        et_diameter = (EditText)findViewById(R.id.et_diameter);

        rl_method = findViewById(R.id.rl_method);
        bt_method = (ImageButton)findViewById(R.id.bt_method);
        et_method = (EditText)findViewById(R.id.et_method);

        btn_login = findViewById(R.id.btn_login);

        userdao = new UserDao();
        mainHandler = new Handler(getMainLooper());   // 获取主线程

        btn_login.setOnClickListener(this);
    }

    public class DA_Material extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private List<String> list_material;
        private TextView content;

        public DA_Material(Context context, List<String> list_material) {
            this.context = context;
            this.list_material = list_material;
        }

        public int getCount() {
            return list_material.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row, null);

            content = (TextView) convertView.findViewById(R.id.text_row);
            String editContent = list_material.get(position);
            content.setText(list_material.get(position).toString());

            content.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    et_material.setText(editContent);
                    pw_material.dismiss();
                    return false;
                }
            });
            return convertView;
        }
    }

    public class DA_Diameter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private List<String> list_diameter;
        private TextView content;

        public DA_Diameter(Context context, List<String> list_diameter) {
            this.context = context;
            this.list_diameter = list_diameter;
        }

        public int getCount() {
            return list_diameter.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row, null);

            content = (TextView) convertView.findViewById(R.id.text_row);
            String editContent = list_diameter.get(position);
            content.setText(list_diameter.get(position).toString());

            content.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    et_diameter.setText(editContent);
                    pw_diameter.dismiss();
                    return false;
                }
            });
            return convertView;
        }
    }

    public class DA_Method extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private List<String> list_method;
        private TextView content;

        public DA_Method(Context context, List<String> list_method) {
            this.context = context;
            this.list_method = list_method;
        }

        public int getCount() {
            return list_method.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row, null);

            content = (TextView) convertView.findViewById(R.id.text_row);
            String editContent = list_method.get(position);
            content.setText(list_method.get(position).toString());

            content.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    et_method.setText(editContent);
                    pw_method.dismiss();
                    return false;
                }
            });
            return convertView;
        }
    }

    private List<String> getMaterials() {
        materials = new ArrayList<String>();
        materials.add("Al-Si-5");
        materials.add("Al-Mg-5");
        materials.add("Al-99.5");
        materials.add("Steel");
        materials.add("Cr-Ni-199");
        materials.add("Cu-Si-3");
        return materials;
    }

    private List<String> getDiameters() {
        diameters = new ArrayList<String>();
        diameters.add("0.8");
        diameters.add("1");
        diameters.add("1.2");
        diameters.add("1.6");
        return diameters;
    }

    private List<String> getMethods() {
        methods = new ArrayList<String>();
        methods.add("MIG");
        methods.add("GMAW");
        return methods;
    }

    @Override
    protected void onStop() {
        super.onStop();
        pw_material.dismiss();
        pw_diameter.dismiss();
        pw_method.dismiss();
    }


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:    // 登录按钮
                doLogin();
                break;
        }
    }

    // 执行登录操作
    private void doLogin(){

        s_material = et_material.getText().toString().trim();
        s_diameter = et_diameter.getText().toString().trim();
        s_method = et_method.getText().toString().trim();

        if(TextUtils.isEmpty(s_material)){
            //CommonUtils.showShortMsg(this, "请输入焊接材料");
            et_material.requestFocus();
        }else if(TextUtils.isEmpty(s_diameter)){
            CommonUtils.showShortMsg(this, "请输入焊接直径");
            et_diameter.requestFocus();
        }else if(TextUtils.isEmpty(s_method)){
            CommonUtils.showShortMsg(this, "请输入焊接方法");
            et_method.requestFocus();
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Userinfo item = userdao.Query(s_material, s_diameter, s_method);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(item == null){
                                CommonUtils.showDlgMsg(MainActivity4.this, "查询失败");
                            }else{
                                //CommonUtils.showLonMsg(MainActivity4.this, "查询成功");
                                //调用参数界面
                                Intent intent = new Intent(MainActivity4.this, UserManagerActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }).start();
        }
    }
    /**用于显示popupWindow内容的适配器*/
    /*public class DropdownAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private List<String> list;
        private TextView content;

        public DropdownAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row, null);

            content = (TextView) convertView.findViewById(R.id.text_row);
            String editContent = list.get(position);
            content.setText(list.get(position).toString());

            content.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    et_material.setText(editContent);
                    pop.dismiss();
                    return false;
                }
            });

            return convertView;
        }
    }*/
}