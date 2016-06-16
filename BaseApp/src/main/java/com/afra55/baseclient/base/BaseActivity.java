package com.afra55.baseclient.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.afra55.baseclient.util.ImageLoadUtils;
import com.afra55.commontutils.log.LogUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;

public class BaseActivity extends AppCompatActivity implements BaseActivityUI{

    private boolean destroyed = false;

    private static Handler handler;

    private BaseActivityPresenter mBaseActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {


                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);
                // 全局搜索 View 替换 View
//                if (view != null && view instanceof TextView) {
//                    (TextView)view;
//                }
                return view;
            }
        });

        super.onCreate(savedInstanceState);
        Fresco.initialize(this, ImageLoadUtils.CusstomConfig(this));

        LogUtil.ui("activity: " + getClass().getSimpleName() + " onCreate()");

        mBaseActivityPresenter = new BaseActivityPresenter(this);
    }

    @Override
    protected void onDestroy() {

        mBaseActivityPresenter.onDestroy();

        super.onDestroy();
    }

    @Override
    public final Handler getHandler() {
        return mBaseActivityPresenter.getHandler();
    }

    /**
     * 判断页面是否已经被销毁（异步回调时使用）
     */
    @Override
    public boolean isDestroyedCompatible() {
        return mBaseActivityPresenter.isDestroyedCompatible();
    }

    @Override
    public boolean isDestroyedCompatible17() {
        return mBaseActivityPresenter.isDestroyedCompatible17();
    }

    @Override
    public void invokeFragmentManagerNoteStateNotSaved() {
        mBaseActivityPresenter.invokeFragmentManagerNoteStateNotSaved();
    }


    @Override
    public void onBackPressed() {
        invokeFragmentManagerNoteStateNotSaved();
        mBaseActivityPresenter.onBackPressed();
    }

    @Override
    public void showToast(String message) {
        mBaseActivityPresenter.showToast(message);
    }

    @Override
    public void showKeyboard(boolean isShow) {
        mBaseActivityPresenter.showKeyboard(isShow);
    }

    /**
     * 延时弹出键盘
     *
     * @param focus ：键盘的焦点项
     */
    @Override
    public void showKeyboardDelayed(View focus) {
        mBaseActivityPresenter.showKeyboardDelayed(focus);
    }

    @Override
    public <T extends View> T findView(int resId) {
        return mBaseActivityPresenter.findView(resId);
    }

    // fragment 相关
    @Override
    public BaseFragment addFragment(BaseFragment fragment) {

        return mBaseActivityPresenter.addFragment(fragment);
    }

    @Override
    public List<BaseFragment> addFragments(List<BaseFragment> fragments) {
        return mBaseActivityPresenter.addFragments(fragments);
    }

    /**
     * fragment 只使用一次就被替换掉，使用 replace
     * @param fragment
     * @return
     */
    @Override
    public BaseFragment replaceFragmentContent(BaseFragment fragment) {
        return mBaseActivityPresenter.replaceFragmentContent(fragment);
    }

    @Override
    public BaseFragment replaceFragmentContent(BaseFragment fragment, boolean needAddToBackStack) {
        return mBaseActivityPresenter.replaceFragmentContent(fragment, needAddToBackStack);
    }

    /**
     * 如果使用 fragment 切换动画或常驻界面的话，最好使用 hide 和 show。
     * @param from
     * @param to
     */
    @Override
    public void switchFragment(BaseFragment from, BaseFragment to) {
        mBaseActivityPresenter.switchFragment(from, to);
    }

    /**
     * 判断 sdk_int 是否小于等于系统版本号
     * @param sdk_int
     * @return
     */
    @Override
    public boolean isCompatible(int sdk_int) {
        return mBaseActivityPresenter.isCompatible(sdk_int);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }
}
