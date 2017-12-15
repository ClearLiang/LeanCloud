package com.example.clearliang.leancloud.base;


import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.example.clearliang.leancloud.tools.LeanCloudManager;

public class BasePresenter {
    private static AVIMClient sAVIMClient;
    private static AVObject sAVObject;

    public static AVIMClient getAVIMClient() {
        return sAVIMClient;
    }

    public static void setAVIMClient(AVIMClient AVIMClient) {
        sAVIMClient = AVIMClient;
    }

    public static AVObject getAVObject() {
        return sAVObject;
    }

    public static void setAVObject(AVObject AVObject) {
        sAVObject = AVObject;
    }

    void initClient(String myName){
        sAVIMClient = AVIMClient.getInstance(myName);
    }

    void initMessage(String className){
        sAVObject = LeanCloudManager.getInstance().save(className);
    }

}
