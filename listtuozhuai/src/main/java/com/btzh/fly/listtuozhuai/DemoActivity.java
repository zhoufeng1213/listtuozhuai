package com.btzh.fly.listtuozhuai;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.btzh.fly.listtuozhuai.drag_list_view.DragListView;
import com.btzh.fly.listtuozhuai.drag_list_view.DragListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    private DragListView dvl_drag_list;
//    private List<String> mDataList;

    String [] memus = {"待办任务","代办服务","信息核查", "上下班打卡","日常巡查","下派任务","楼栋信息","户信息","居民信息","法人采集","小区物业","信息志愿者","居民查询","居民通讯录","人口信息查询","紧急处理","寻人(物)启事", "移车通知"};
    int [] images = {R.drawable.main_dbrw_1,R.drawable.main_dbfw_1,R.drawable.main_xxhc_1,R.drawable.main_sxbdk_1,R.drawable.main_rcxc_1,R.drawable.main_xprw_1,R.drawable.main_ldxx_1,R.drawable.main_hxx_1,R.drawable.main_zhxx_1,R.drawable.main_frcj_1,R.drawable.main_xqwy_1,R.drawable.main_zyz_1,R.drawable.main_zhcx_1,R.drawable.main_jmtxl_1,R.drawable.main_xxhc_1,R.drawable.main_jjcl_1,R.drawable.main_xrwqs_1,R.drawable.main_yctz_1};
    List<HashMap<String,Object>> datas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initData();
        getData();
        initView();
    }

    private void getData(){
        for (int i =0;i<memus.length ;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("menu",memus[i]);
            map.put("image",images[i]);
            datas.add(map);
        }
    }

//    private void initData() {
//        mDataList = new ArrayList<>();
//        for (int i = 0; i < 50; ++i) {
//            mDataList.add("条目" + i);
//        }
//    }

    private void initView() {
        dvl_drag_list = (DragListView)this.findViewById(R.id.dvl_drag_list);

        // 2、设置Adapter
        BaseAdapter listAdapter = new MyAdapter(this, datas);
        dvl_drag_list.setAdapter(listAdapter);

        // 3、设置条目点击监听事件
        dvl_drag_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 先判断位置的有效性
                int index = position - dvl_drag_list.getHeaderViewsCount();
                if (index < 0 || index >= datas.size()) {
                    return;
                }
//                toast(datas.get(index).toString());
            }
        });

        // 4、设置条目长按监听事件
        dvl_drag_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 有长按监听事件，为了防止与删除产生冲突，此句必须加上。
                dvl_drag_list.setLongClickFlag();
                int index = position - dvl_drag_list.getHeaderViewsCount();
                if (index < 0 || index >= datas.size()) {
                    return false;
                }
                toast(datas.get(index).toString() + "，被长按");
                return true;
            }
        });

        dvl_drag_list.setDropListener(new DragListView.MyDropListener() {
            @Override
            public void onDrop(List<HashMap<String, Object>> data) {
                Log.e(TAG, "onDrop: list="+data.toString() );
            }
        });
    }

    public class MyAdapter extends DragListViewAdapter<HashMap<String,Object>> {

        public MyAdapter(Context context,List<HashMap<String,Object>> dataList) {
            super(context, dataList);
        }

        @Override
        public View getItemView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_drag_list, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name_drag_list);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.myimageview);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            HashMap<String,Object> map = datas.get(position);
            viewHolder.name.setText(map.get("menu").toString());
            viewHolder.imageView.setImageResource((Integer) map.get("image"));
            return convertView;
        }

        class ViewHolder{
            TextView name;
            ImageView imageView;
        }

    }


    @SuppressWarnings("unchecked")
    private <V  extends View> V $(int id) {
        return (V) findViewById(id);
    }

    private Toast mToast;
    private void toast(String info) {
        if (mToast == null) {
            mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(info);
        mToast.show();
    }

}
