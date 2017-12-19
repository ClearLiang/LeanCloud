package com.example.clearliang.leancloud.presenter;

import com.example.clearliang.leancloud.base.BasePresenter;
import com.example.clearliang.leancloud.interfaces.IMTitleViewInterface;



public class IMTitlePresenter extends BasePresenter<IMTitleViewInterface> {
    private IMTitleViewInterface mIMTitleViewInterface;

    public IMTitlePresenter(IMTitleViewInterface IMTitleViewInterface) {
        mIMTitleViewInterface = IMTitleViewInterface;
    }

}
