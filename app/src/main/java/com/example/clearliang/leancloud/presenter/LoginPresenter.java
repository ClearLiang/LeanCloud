package com.example.clearliang.leancloud.presenter;


import com.example.clearliang.leancloud.base.BasePresenter;
import com.example.clearliang.leancloud.interfaces.LoginViewInterface;
import com.example.clearliang.leancloud.tools.LeanCloudManager;

public class LoginPresenter extends BasePresenter<LoginViewInterface> {
    private LoginViewInterface mLoginViewInterface;

    public LoginPresenter(LoginViewInterface loginViewInterface) {
        mLoginViewInterface = loginViewInterface;
    }

    public String CheckUser(String user,String password){
        if(user .equals("123") ){
            if(password.equals("123")){
                return "成功";
            }else {
                return "密码错误";
            }
        }else{
            return "用户不存在";
        }
    }
}
