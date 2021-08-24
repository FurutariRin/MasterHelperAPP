package com.example.m1.fragments.mainPageSubFragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.dao.UserDao;
import com.cqupt.master_helper.entity.User;
import com.example.m1.adapter.rankVideoAdapter;
import com.example.m1.adapter.recommendVideoAdapter;
import com.example.m1.bean.recycleViewData;
import com.example.m1.fragments.fragmentUtilities.fragmentUtilities;
import com.example.m1.pages.videoPlayPageActivity;

import java.util.ArrayList;

public class rankFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recycleView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager layoutManager;

    private rankVideoAdapter rankVideoAdapter;

    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 20;

    private int fromIndex = 0;
    private int toIndex = PAGE_COUNT;

    private ArrayList<recycleViewData> LocalData = new ArrayList<>();

    private String uid;

    ArrayList<recycleViewData> newDatas;

    //----------------------------------------------------------------------------------------------
    //生命周期
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");

        //测试数据初始化
        rankVideoAdapter = new rankVideoAdapter(this.LocalData);
        updateRecyclerView();
        //组件绑定
        bindView();
        //recycleView初始化
        adapterInit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rank, container, false);
    }

    //----------------------------------------------------------------------------------------------
    //绑定视图、设定监听器
    private void bindView() {
        this.recycleView = this.getActivity().findViewById(R.id.recycleViewRank);
        this.swipeRefreshLayout = this.getActivity().findViewById((R.id.rankRefresher));

        //上拉刷新
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_red_light),
                getResources().getColor(android.R.color.holo_orange_light));
    }

    //列表
    //填充器初始化
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void adapterInit() {
        this.layoutManager = new LinearLayoutManager(this.getContext());
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(rankVideoAdapter);

        //监听返回
        rankVideoAdapter.setOnItemClickListener(new rankVideoAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent videoPlayPage = new Intent(getActivity(), videoPlayPageActivity.class);
                videoPlayPage.putExtras(fragmentUtilities.bundlePack(LocalData, position, uid));
                getActivity().startActivity(videoPlayPage);
            }
        });

        //滚动监听

        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                            @Override
                                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                                super.onScrollStateChanged(recyclerView, newState);
                                                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                                    // 如果没有隐藏footView，那么最后一个条目的位置就比我们的getItemCount少1，自己可以算一下
                                                    if (rankVideoAdapter.isFadeTips() == false && lastVisibleItem + 1 == rankVideoAdapter.getItemCount()) {
                                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                // 然后调用updateRecyclerview方法更新RecyclerView
                                                                updateRecyclerView();
                                                            }
                                                        }, 500);
                                                    }

                                                    // 如果隐藏了提示条，我们又上拉加载时，那么最后一个条目就要比getItemCount要少2
                                                    if (rankVideoAdapter.isFadeTips() == true && lastVisibleItem + 2 == rankVideoAdapter.getItemCount()) {
                                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                // 然后调用updateRecyclerview方法更新RecyclerView
                                                                updateRecyclerView();
                                                            }
                                                        }, 500);
                                                    }
                                                }
                                            }


                                            @Override
                                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                                super.onScrolled(recyclerView, dx, dy);
                                                // 在滑动完成后，拿到最后一个可见的item的位置
                                                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                                            }
                                        }
        );
    }

    //更新列表
    private void updateRecyclerView() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                // 获取从fromIndex到toIndex的数据
                newDatas = fragmentUtilities.getDatas(fromIndex, toIndex, 2, uid);

            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (newDatas.size() > 0) {
            // 然后传给Adapter，并设置hasMore为true
            rankVideoAdapter.updateList(newDatas, true);
            fromIndex += PAGE_COUNT;
            toIndex += PAGE_COUNT;
        } else {
            rankVideoAdapter.updateList(null, false);
        }
    }

    //刷新操作
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        rankVideoAdapter.resetDatas();
        updateRecyclerView();
        fromIndex += 0;
        toIndex = PAGE_COUNT;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
