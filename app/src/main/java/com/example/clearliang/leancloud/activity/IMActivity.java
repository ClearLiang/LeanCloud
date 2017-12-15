package com.example.clearliang.leancloud.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.clearliang.leancloud.tools.LeanCloudManager;
import com.example.clearliang.leancloud.R;
import com.example.clearliang.leancloud.adapter.MyAdapter;
import com.example.clearliang.leancloud.base.BaseActivity;
import com.example.clearliang.leancloud.entity.MyMessage;
import com.example.clearliang.leancloud.interfaces.IMViewInterface;
import com.example.clearliang.leancloud.presenter.IMPresenter;
import com.example.clearliang.leancloud.tools.MyDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IMActivity extends BaseActivity implements IMViewInterface {
    protected static AVObject testObject;

    private Button mBtnIMSubmit;
    private EditText mEtIMInput;
    private RecyclerView mRecyclerView;
    private TextView mTvImHead;

    private static List<MyMessage> mMessageList = new ArrayList<>();
    private static MyAdapter myAdapter;
    private LinearLayoutManager mLayoutManager;
    private String myName="",toName="";

    IMPresenter mPresenter;

    public class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            if(message instanceof AVIMTextMessage){
                MyMessage remsg = new MyMessage();
                remsg.setName(toName);
                remsg.setContent(((AVIMTextMessage) message).getText());
                remsg.setType(MyMessage.TYPE_RECEIVE);
                remsg.setTime(MyDate.getDate());
                remsg.setIcon(String.valueOf(R.drawable.icon_warning));
                mMessageList.add(remsg);
                myAdapter.notifyItemInserted(mMessageList.size());//当有新消息时，刷新RecyclerView中的显示
                mRecyclerView.scrollToPosition(mMessageList.size() - 1);//将RecyclerView定位到最后一行
            }
        }

        public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
        mPresenter = new IMPresenter(this);
        testObject = mPresenter.initSave("LeanCloud");

        initView();
        initEvent();
        initAdapter();

    }

    private void initAdapter() {
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //给视图设置大小变化的监听，用于点击输入框时，文本定位
        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //当点击输入框弹出输入法时，布局大小改变，将RecyclerView定位到最后一行
                mRecyclerView.scrollToPosition(mMessageList.size()-1);
            }
        });
        //创建并设置Adapter
        myAdapter = new MyAdapter(mMessageList);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.notifyItemInserted(mMessageList.size());//当有新消息时，刷新RecyclerView中的显示
        mRecyclerView.scrollToPosition(mMessageList.size()-1);//将RecyclerView定位到最后一行

    }

    private void initEvent() {
        mBtnIMSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEtIMInput.getText().toString();
                //信息存储
                mPresenter.saveMessage(testObject,myName,toName,text);
                //界面显示
                if (!isEmptyInput()) {
                    MyMessage msg = new MyMessage();
                    msg.setName(myName);
                    msg.setContent(text);
                    msg.setTime(MyDate.getDate());
                    msg.setType(MyMessage.TYPE_SEND);
                    msg.setIcon(String.valueOf(R.drawable.icon_warning));
                    mMessageList.add(msg);
                    myAdapter.notifyItemInserted(mMessageList.size());//当有新消息时，刷新RecyclerView中的显示
                    mRecyclerView.scrollToPosition(mMessageList.size() - 1);//将RecyclerView定位到最后一行
                    mEtIMInput.setText("");

                    //发送到服务器
                    mPresenter.sendMessage(toName,text,false);
                }
            }
        });
    }

    private void initView() {
        mBtnIMSubmit = findViewById(R.id.btn_im_submit);
        mEtIMInput = findViewById(R.id.et_im_input);
        mRecyclerView = findViewById(R.id.rv_im);
        mTvImHead = findViewById(R.id.tv_im_head);

        Bundle bundle = getIntent().getExtras();
        myName = bundle.getString("myName");
        toName = bundle.getString("toName");

        mTvImHead.setText(toName);
    }

    @Override
    public boolean isEmptyInput() {
        if(TextUtils.isEmpty(mEtIMInput.getText().toString())){
            Toast.makeText(getApplicationContext(),"输入为空",Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            LeanCloudManager.getInstance().close(null);
            finish();
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
