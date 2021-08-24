package com.example.m1.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

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
import com.cqupt.master_helper.entity.User;
import com.example.m1.adapter.favoritesAdapter;
import com.example.m1.adapter.recommendVideoAdapter;
import com.example.m1.bean.recycleViewData;
import com.example.m1.fragments.fragmentUtilities.fragmentUtilities;
import com.example.m1.pages.videoPlayPageActivity;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

public class favoritesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    //测试用
    private int count = 0;

    private RecyclerView recycleView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager layoutManager;

    private favoritesAdapter favoritesAdapter;

    private int lastVisibleItem = 0;

    private final int PAGE_COUNT = 20;

    private ArrayList<recycleViewData> LocalData = new ArrayList<>();
    private ArrayList<recycleViewData> newDatas;


    private String uid;

    //--------------------------------------------------------------------------------------------------
    //生命周期
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindView();

        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");

        favoritesAdapter = new favoritesAdapter(this.LocalData, uid,getContext());
        this.favoritesAdapter = new favoritesAdapter(this.LocalData, uid,getContext());
        updateRecyclerView(0, PAGE_COUNT);
        adapterInit();
//        animInit();
    }

    private void animInit() {
        SlideInRightAnimationAdapter animationAdapter = new SlideInRightAnimationAdapter(favoritesAdapter);
        animationAdapter.setDuration(700);
        recycleView.setAdapter(animationAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void adapterInit() {


        this.layoutManager = new LinearLayoutManager(this.getContext());

        recycleView.setAdapter(favoritesAdapter);
        recycleView.setLayoutManager(layoutManager);

        favoritesAdapter.setOnItemClickListener(position -> {
            Intent videoPlayPage = new Intent(getActivity(), videoPlayPageActivity.class);
            videoPlayPage.putExtras(fragmentUtilities.bundlePack(LocalData, position, uid));
            getActivity().startActivity(videoPlayPage);
        });


        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 如果没有隐藏footView，那么最后一个条目的位置就比我们的getItemCount少1，自己可以算一下
                    if (!favoritesAdapter.isFadeTips() && lastVisibleItem + 1 == favoritesAdapter.getItemCount()) {
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // 然后调用updateRecyclerview方法更新RecyclerView
                                updateRecyclerView(favoritesAdapter.getRealLastPosition(), favoritesAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 0);
                    }

                    // 如果隐藏了提示条，我们又上拉加载时，那么最后一个条目就要比getItemCount要少2
                    if (favoritesAdapter.isFadeTips() && lastVisibleItem + 2 == favoritesAdapter.getItemCount()) {
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // 然后调用updateRecyclerview方法更新RecyclerView
                                updateRecyclerView(favoritesAdapter.getRealLastPosition(), favoritesAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 0);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 在滑动完成后，拿到最后一个可见的item的位置
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                // 获取从fromIndex到toIndex的数据
                newDatas = fragmentUtilities.getDatas(fromIndex, toIndex, 3, uid);
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
            favoritesAdapter.updateList(newDatas, true);
        } else {
            favoritesAdapter.updateList(null, false);
        }
    }


    private void bindView() {
        this.recycleView = this.getActivity().findViewById(R.id.recycleView_favorite);
        this.swipeRefreshLayout = this.getActivity().findViewById(R.id.swipeRefresh_favorite);
        //上拉刷新
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_red_light),
                getResources().getColor(android.R.color.holo_orange_light));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        favoritesAdapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 0);

    }
}