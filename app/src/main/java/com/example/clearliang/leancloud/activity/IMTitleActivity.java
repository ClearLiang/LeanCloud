package com.example.clearliang.leancloud.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.clearliang.leancloud.LeanCloudManager;
import com.example.clearliang.leancloud.R;
import com.example.clearliang.leancloud.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressLint("Registered")
public class IMTitleActivity extends BaseActivity {
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_title);

        initView();
        initAdapter();
        initEvent();
    }

    private void initEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.tv_im_name);
                String toName = textView.getText().toString();
                Bundle bundle = getIntent().getExtras();
                String myName = bundle.getString("myName");
                final Intent i = new Intent(IMTitleActivity.this,IMActivity.class);
                i.putExtra("toName",toName);
                i.putExtra("myName",myName);
                LeanCloudManager.getInstance().getClient(myName);
                startActivity(i);
            }
        });
    }

    private void initAdapter() {
        //1.准备数据
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>() ;
        for(int i=0;i<10;i++){
            Map<String,Object> map = new HashMap<String,Object>() ;
            // key对应的from数组
            map.put("iv_im_head", R.drawable.icon_warning) ;
            map.put("tv_im_name", "项目："+i) ;
            list.add(map) ;
        }
        Map<String,Object> map = new HashMap<String,Object>() ;
        map.put("iv_im_head", R.drawable.icon_warning) ;
        map.put("tv_im_name", "Jerry") ;
        list.add(map) ;

        //2.准备SimpleAdapter对象
        // 准白
        String[] from = { "iv_im_head", "tv_im_name" };
        // 对应 Item 子样式 里面控件的id
        int[] to = { R.id.iv_im_head, R.id.tv_im_name } ;
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,R.layout.item, from, to) ;
        //3.添加到listView里面
        mListView.setAdapter(simpleAdapter) ;
    }

    private void initView() {
        mListView = findViewById(R.id.lv_im_title);
    }


}
