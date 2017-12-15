package com.example.clearliang.leancloud.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.clearliang.leancloud.activity.IMActivity;
import com.example.clearliang.leancloud.tools.LeanCloudManager;


public class BaseActivity extends AppCompatActivity {
    public static final String TAG = "信息：";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LeanCloudManager.getInstance().initIM(getApplicationContext(),"zNuggiKPXeQDfostTM7O8i7R-gzGzoHsz","LIrsg2Fic3YAMgCMd32BxPH6");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
