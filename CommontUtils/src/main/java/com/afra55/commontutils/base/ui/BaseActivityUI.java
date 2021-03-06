package com.afra55.commontutils.base.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.afra55.commontutils.base.BaseActivity;
import com.afra55.commontutils.base.BaseFragment;

import java.util.List;

/**
 * Created by Victor Yang on 2016/6/16.
 * MVP- V
 */
public interface BaseActivityUI {

    Handler getHandler();

    /**
     * 判断页面是否已经被销毁（异步回调时使用）
     */
    boolean isDestroyedCompatible();

    @TargetApi(17)
    boolean isDestroyedCompatible17();

    void showToast(String message);

    /**
     * 显示隐藏 keyboard
     * @param isShow
     */
    void showKeyboard(boolean isShow);

    /**
     * 延时弹出键盘
     *
     * @param focus ：键盘的焦点项
     */
    void showKeyboardDelayed(View focus);

    Context getContext();

    BaseActivity getActivity();
}
