package com.example.clearliang.leancloud.presenter;

import android.content.Context;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.example.clearliang.leancloud.LeanCloudManager;
import com.example.clearliang.leancloud.interfaces.IMViewInterface;



public class IMPresenter {
    IMViewInterface mIMViewInterface;

    public IMPresenter(IMViewInterface IMViewInterface) {
        mIMViewInterface = IMViewInterface;
    }

    public void sendMessage(final Context context, String myName,String toName,String text,AVIMClient name) {
        LeanCloudManager client = LeanCloudManager.getInstance();
        client.initMessage(context,myName,toName,name);
        client.sendMessage(text);
        client.closeClient();
    }
}
