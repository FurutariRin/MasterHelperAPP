package com.example.m1.pages.manageSubPage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.biz.report.VideoReportBiz;
import com.cqupt.master_helper.biz.video.VideoRecommendBiz;
import com.cqupt.master_helper.biz.video.VideoSearchBiz;
import com.cqupt.master_helper.dao.UserReportDao;
import com.cqupt.master_helper.dao.VideoDao;
import com.cqupt.master_helper.entity.UserReport;
import com.cqupt.master_helper.entity.Video;
import com.example.m1.adapter.reportAdapter;
import com.example.m1.bean.recycleViewData;
import com.example.m1.bean.reportData;
import com.example.m1.pages.videoPlayPageActivity;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

public class reportManageActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    //测试用
    private int count = 0;

    private RecyclerView recycleView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager layoutManager;

    private reportAdapter reportAdapter;

    private ArrayList<reportData> LocalData = new ArrayList<>();

    private List<UserReport> userReportList;


    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 20;

    private int fromIndex = 0;
    private int toIndex = PAGE_COUNT;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_manage);
        this.reportAdapter = new reportAdapter(this.LocalData);
        updateRecyclerView();
        bindView();
        adapterInit();
    }

    private void animInit() {
        SlideInRightAnimationAdapter animationAdapter = new SlideInRightAnimationAdapter(reportAdapter);
        animationAdapter.setDuration(700);
        recycleView.setAdapter(animationAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void adapterInit() {


        this.layoutManager = new LinearLayoutManager(this);

        recycleView.setAdapter(reportAdapter);
        recycleView.setLayoutManager(layoutManager);


        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 如果没有隐藏footView，那么最后一个条目的位置就比我们的getItemCount少1，自己可以算一下
                    if (reportAdapter.isFadeTips() == false && lastVisibleItem + 1 == reportAdapter.getItemCount()) {
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // 然后调用updateRecyclerview方法更新RecyclerView
                                updateRecyclerView();
                            }
                        }, 0);
                    }

                    // 如果隐藏了提示条，我们又上拉加载时，那么最后一个条目就要比getItemCount要少2
                    if (reportAdapter.isFadeTips() == true && lastVisibleItem + 2 == reportAdapter.getItemCount()) {
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // 然后调用updateRecyclerview方法更新RecyclerView
                                updateRecyclerView();
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

    private void updateRecyclerView() {
        // 获取从fromIndex到toIndex的数据
        ArrayList<reportData> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            // 然后传给Adapter，并设置hasMore为true
            reportAdapter.updateList(newDatas, true);
            fromIndex += PAGE_COUNT;
            toIndex += PAGE_COUNT;
        } else {
            reportAdapter.updateList(null, false);
        }
    }


    private void bindView() {
        this.recycleView = this.findViewById(R.id.reportManageRecycleView);
        this.swipeRefreshLayout = this.findViewById(R.id.swipeRefresh_report);
        //上拉刷新
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_red_light),
                getResources().getColor(android.R.color.holo_orange_light));
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        reportAdapter.resetDatas();
        updateRecyclerView();
        fromIndex += 0;
        toIndex = PAGE_COUNT;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 0);

    }

    //----------------------------------------------------------------------------------------------
    // 数据获取
    private ArrayList<reportData> getDatas(int fromIndex, int toIndex) {
        ArrayList<reportData> newDataTestSet = new ArrayList<>();
        Thread thread = new Thread() {
            @Override
            public void run() {
                userReportList = new UserReportDao().adminFoundReport();
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (userReportList.size() > toIndex) {
            for (int i = fromIndex; i < toIndex; i++) {
                reportData reportData = new reportData(userReportList.get(i));
                int finalI = i;
                Thread thread1 = new Thread() {
                    @Override
                    public void run() {
                        reportData.setVideoName(new VideoDao().videoInfo(userReportList.get(finalI).getVid()).getVname());
                    }
                };
                thread1.start();
                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                newDataTestSet.add(reportData);
            }
        } else {
            for (int i = 0; i < userReportList.size(); i++) {
                reportData reportData = new reportData(userReportList.get(i));
                int finalI = i;
                Thread thread2 = new Thread() {
                    @Override
                    public void run() {
                        reportData.setVideoName(new VideoDao().videoInfo(userReportList.get(finalI).getSomething_id()).getVname());
                    }
                };
                thread2.start();
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                newDataTestSet.add(reportData);
            }
        }

//           videoName
//           Rid
//           Vid
//           reportNum
        return newDataTestSet;
    }

}