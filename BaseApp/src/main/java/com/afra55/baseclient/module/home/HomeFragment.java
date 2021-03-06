package com.afra55.baseclient.module.home;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.afra55.baseclient.R;
import com.afra55.baseclient.adapter.BinnerAdapter;
import com.afra55.baseclient.module.home.presenter.HomeFragmentPresenter;
import com.afra55.baseclient.module.home.ui.HomeFragmentUI;
import com.afra55.baseclient.util.DisplayUtil;
import com.afra55.baseclient.view.refreshforheader.PtrDefaultHandler;
import com.afra55.baseclient.view.refreshforheader.PtrFrameLayout;
import com.afra55.baseclient.view.refreshforheader.PtrHandler;
import com.afra55.baseclient.view.refreshforheader.header.MaterialHeader;
import com.afra55.commontutils.base.BaseFragment;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements PtrHandler, HomeFragmentUI {

    private HomeFragmentPresenter mHomeFragmentPresenter;

    private PtrFrameLayout pullToRefresh; // 下拉控件
    private ViewPager binnerVp; // 广告Binner
    private ArrayList<View> binnerViewArray; // 存储Binner view的容器
    private BinnerAdapter binnerAdapter;

    public static HomeFragment newInstance(String param1, String param2) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        setContainerId(R.id.main_fragment_content);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mHomeFragmentPresenter = new HomeFragmentPresenter(this);

        findView();

        initPullToRefresh();
        initBanner((RadioGroup) findView(R.id.vp_indicator_rg));

    }

    private void findView() {
        pullToRefresh = findView(R.id.home_pull_to_refresh);
        binnerVp = findView(R.id.vp_banner);
    }

    /* 初始化下拉控件 */
    private void initPullToRefresh() {

        pullToRefresh.setResistance(1.7f); // 阻尼系数, 越大，感觉下拉时越吃力
        pullToRefresh.setRatioOfHeaderHeightToRefresh(1.2f); // 触发刷新时移动的位置比例, 移动达到头部高度1.2倍时可触发刷新操作
        pullToRefresh.setDurationToClose(200); // 回弹延时, 回弹到刷新高度所用时间
        pullToRefresh.setDurationToCloseHeader(1000); // 头部回弹时间
        pullToRefresh.setPullToRefresh(false); // 下拉刷新 / 释放刷新, 默认为释放刷新
        pullToRefresh.setKeepHeaderWhenRefresh(true); // 刷新时保持头部
        pullToRefresh.setPinContent(true); // 刷新时，保持内容不动，仅头部下移, Material 风格时使用
        pullToRefresh.disableWhenHorizontalMove(true); // ViewPager滑动冲突

        /* 设置下拉刷新的 view */
        final MaterialHeader header = new MaterialHeader(getContext());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DisplayUtil.dp2px(getContext(), 15), 0, DisplayUtil.dp2px(getContext(), 10));
        header.setPtrFrameLayout(pullToRefresh);
        // pullToRefresh.setLoadingMinTime(1000);
        // pullToRefresh.setDurationToCloseHeader(1500);
        pullToRefresh.setHeaderView(header);
        pullToRefresh.addPtrUIHandler(header);

        /* 测试用下拉监听 start */
        pullToRefresh.setPtrHandler(this);
    }

    /* 初始化Binner */
    private void initBanner(RadioGroup binnerIndicatorRg) {
        mHomeFragmentPresenter.initBinner(getContext(), binnerVp, binnerIndicatorRg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onFragmentSelected(boolean isFirst) {

    }

    @Override
    public void onFragmentUnSelected() {

    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefresh.refreshComplete();
                    }
                }, 1800);
    }
}
