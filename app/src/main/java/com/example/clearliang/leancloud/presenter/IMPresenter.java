package com.example.clearliang.leancloud.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.example.clearliang.leancloud.LeanCloudManager;
import com.example.clearliang.leancloud.interfaces.IMViewInterface;

import java.util.Arrays;


public class IMPresenter {
    IMViewInterface mIMViewInterface;

    public IMPresenter(IMViewInterface IMViewInterface) {
        mIMViewInterface = IMViewInterface;
    }

    public void sendMessage(final Context context, String myName,String toName,String text) {
        LeanCloudManager.getInstance().getClient(myName).createConversation(
                Arrays.asList(toName), "一个会话", null, false, true, new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation avimConversation, AVIMException e) {
                        if (null != e) {
                            Log.i("信息：",e.getMessage());
                        } else {
                            Toast.makeText(context.getApplicationContext(),"会话创建成功",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
