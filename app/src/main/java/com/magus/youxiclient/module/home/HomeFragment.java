package com.magus.youxiclient.module.home;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.magus.youxiclient.R;
import com.magus.youxiclient.adapter.BinnerAdapter;
import com.magus.youxiclient.base.BaseFragment;
import com.magus.youxiclient.util.BinnerHelper;
import com.magus.youxiclient.util.DisplayUtil;
import com.magus.youxiclient.util.ImageLoadUtils;
import com.magus.youxiclient.view.refreshforheader.PtrDefaultHandler;
import com.magus.youxiclient.view.refreshforheader.PtrFrameLayout;
import com.magus.youxiclient.view.refreshforheader.PtrHandler;
import com.magus.youxiclient.view.refreshforheader.header.MaterialHeader;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {

    private PtrFrameLayout pullToRefresh; // 下拉控件
    private RelativeLayout homeContainerRly;
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
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, String message) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeContainerRly = (RelativeLayout)view.findViewById(R.id.home_container_rly);
        pullToRefresh = (PtrFrameLayout) view.findViewById(R.id.home_pull_to_refresh);
        initPullToRefresh();

        binnerVp = (ViewPager) view.findViewById(R.id.vp_banner);
        RadioGroup binnerIndicatorRg = (RadioGroup) view.findViewById(R.id.vp_indicator_rg);
        initBanner(binnerIndicatorRg);
        return view;
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
        pullToRefresh.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                /* 根据实际情况做改动 */
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
        });
        /* 测试用下拉监听 end */
    }

    /* 初始化Binner */
    private void initBanner(RadioGroup binnerIndicatorRg) {
        binnerViewArray = new ArrayList<>();
        String binnerPath = "https://avatars0.githubusercontent.com/u/7507927?v=3&s=460";
        ArrayList<View> startAndEndView = new ArrayList<>(); // 请务必存储 开始和最后的view
        for (int i = 0; i < 5; i++) {
            SimpleDraweeView draweeView = new SimpleDraweeView(getContext());
            ImageLoadUtils.getInstance(getContext()).display(binnerPath, draweeView);
            binnerViewArray.add(draweeView);
            if (i == 0) { // 开始的view
                SimpleDraweeView start = new SimpleDraweeView(getContext());
                ImageLoadUtils.getInstance(getContext()).display(binnerPath, start);
                startAndEndView.add(start);
            } else if (i == 4) { // 结束的view
                SimpleDraweeView end = new SimpleDraweeView(getContext());
                ImageLoadUtils.getInstance(getContext()).display(binnerPath, end);
                startAndEndView.add(end);
            }
        }

        BinnerHelper.initViewList(binnerViewArray, startAndEndView);
        binnerAdapter = new BinnerAdapter(binnerViewArray);
        binnerVp.setAdapter(binnerAdapter);
        BinnerHelper.getInstance().start(getContext(), binnerVp, binnerViewArray, binnerIndicatorRg);
    }


    @Override
    protected void onFragmentSeleted(boolean isFirst) {

    }

    @Override
    protected void onFragmentUnSeleted() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

}
