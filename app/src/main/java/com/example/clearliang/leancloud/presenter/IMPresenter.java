package com.example.clearliang.leancloud.presenter;

import android.content.Context;

import com.avos.avoscloud.AVObject;
import com.example.clearliang.leancloud.base.BasePresenter;
import com.example.clearliang.leancloud.tools.LeanCloudManager;
import com.example.clearliang.leancloud.interfaces.IMViewInterface;


public class IMPresenter extends BasePresenter<IMViewInterface> {
    IMViewInterface mIMViewInterface;

    public IMPresenter(IMViewInterface IMViewInterface) {
        mIMViewInterface = IMViewInterface;
    }

    public void sendMessage(String toName,final String text,boolean isTransient) {
        LeanCloudManager.getInstance().sendMessage(toName,text,isTransient);
    }

    public AVObject initSave(String objectName){
        AVObject avObject = LeanCloudManager.getInstance().save(objectName);
        return avObject;
    }
    public void saveMessage(AVObject avObject, String myName, String toName, String content){
        LeanCloudManager.getInstance().save(avObject,myName,toName,content);
    }
}
