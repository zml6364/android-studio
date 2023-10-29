package cn.edu.bzu.ie.mysqldb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 自定义用户数据适配器类
 */
public class LvUserinfoAdapter extends BaseAdapter {
    private Context context;    // 上下文信息
    private List<Userinfo> userinfoList;    // 用户信息数据集合

    public LvUserinfoAdapter(Context context, List<Userinfo> userinfoList) {
        this.context = context;
        this.userinfoList = userinfoList;
    }

    public void setUserinfoList(List<Userinfo> userinfoList) {
        this.userinfoList = userinfoList;
    }

    @Override
    public int getCount() {
        return userinfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return userinfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.user_list_item, null);
            viewHolder = new ViewHolder();
            //viewHolder.tv_material = convertView.findViewById(R.id.tv_material);
            //viewHolder.tv_diameter = convertView.findViewById(R.id.tv_diameter);
            //viewHolder.tv_method = convertView.findViewById(R.id.tv_method);
            viewHolder.tv_pname = convertView.findViewById(R.id.tv_pname);
            viewHolder.tv_pvalue = convertView.findViewById(R.id.tv_pvalue);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 这里进行数据填充
        Userinfo item = userinfoList.get(position);
        //viewHolder.tv_material.setText(item.getMaterial());
        //viewHolder.tv_diameter.setText(item.getDiameter());
        //viewHolder.tv_method.setText(item.getMethod());
        viewHolder.tv_pname.setText(item.getParamName());
        viewHolder.tv_pvalue.setText(item.getParamValue());

        return convertView;
    }

    // 自定义内部类
    private class ViewHolder{
        private TextView tv_material, tv_diameter, tv_method, tv_pname, tv_pvalue;
    }
}
