package com.example.m1.pages.classifyPages;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cqupt.master_helper.R;
import com.example.m1.adapter.classifyClassAdapter;
import com.example.m1.adapter.searchAdapter;
import com.example.m1.bean.recycleViewData;
import com.example.m1.pages.activityUtilities.activityUtilities;
import com.example.m1.pages.videoPlayPageActivity;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

public class classifySubPages extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recycleView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager layoutManager;

    private classifyClassAdapter classifyClassAdapter;

    private TextView title;

    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 20;

    private int fromIndex = 0;
    private int toIndex = PAGE_COUNT;

    private int Type = 5;

    private String classType;
    private String uid;

    private ArrayList<recycleViewData> newDatas;

    private ArrayList<recycleViewData> LocalData = new ArrayList<>();
    private final String[] videoUri = {
            "http://vjs.zencdn.net/v/oceans.mp4",
            "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
            "https://media.w3.org/2010/05/sintel/trailer.mp4",
            "http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4",
            "https://v-cdn.zjol.com.cn/280443.mp4",
            "https://v-cdn.zjol.com.cn/276982.mp4",
            "https://v-cdn.zjol.com.cn/276985.mp4",
            "https://v-cdn.zjol.com.cn/276984.mp4"
    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classofy_sub_pages);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        classType = bundle.getString("classType");
        uid = bundle.getString("uid");
        classifyClassAdapter = new classifyClassAdapter(this.LocalData);
        updateRecyclerView();
        bindView();
        adapterInit();
    }

    //----------------------------------------------------------------------------------------------
    //??????
    //??????????????????????????????
    private void bindView() {
        this.recycleView = this.findViewById(R.id.recycleViewRecommnend);
        this.swipeRefreshLayout = this.findViewById((R.id.recommendRefresher));

        this.title = findViewById(R.id.title);
        title.setText(classType);

        //????????????
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_red_light),
                getResources().getColor(android.R.color.holo_orange_light));
    }

    //??????????????????
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void adapterInit() {
        this.layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(classifyClassAdapter);
        //????????????
        classifyClassAdapter.setOnItemClickListener(position -> {

            Intent videoPlayPage = new Intent(this, videoPlayPageActivity.class);
            videoPlayPage.putExtras(activityUtilities.bundlePack(LocalData, position, uid));
            this.startActivity(videoPlayPage);
        });
        //????????????
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                            @Override
                                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                                super.onScrollStateChanged(recyclerView, newState);
                                                // ???newState??????????????????
                                                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                                    // ??????????????????footView???????????????????????????????????????????????????getItemCount???1????????????????????????
                                                    if (classifyClassAdapter.isFadeTips() == false && lastVisibleItem + 1 == classifyClassAdapter.getItemCount()) {
                                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                // ????????????updateRecyclerview????????????RecyclerView
                                                                updateRecyclerView();
                                                            }
                                                        }, 0);
                                                    }

                                                    // ???????????????????????????????????????????????????????????????????????????????????????getItemCount??????2
                                                    if (classifyClassAdapter.isFadeTips() == true && lastVisibleItem + 2 == classifyClassAdapter.getItemCount()) {
                                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                // ????????????updateRecyclerview????????????RecyclerView
                                                                updateRecyclerView();
                                                            }
                                                        }, 0);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                                super.onScrolled(recyclerView, dx, dy);
                                                // ????????????????????????????????????????????????item?????????
                                                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                                            }
                                        }
        );
    }

    //????????????
    private void updateRecyclerView() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                // ?????????fromIndex???toIndex?????????
                newDatas = activityUtilities.getDatas(fromIndex, toIndex, Type, classType);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (newDatas.size() > 0) {
            // ????????????Adapter????????????hasMore???true
            classifyClassAdapter.updateList(newDatas, true);
            fromIndex += PAGE_COUNT;
            toIndex += PAGE_COUNT;
        } else {
            classifyClassAdapter.updateList(null, false);
        }
    }


    //????????????
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        classifyClassAdapter.resetDatas();
        updateRecyclerView();
        fromIndex = 0;
        toIndex = PAGE_COUNT;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
    }

    //??????????????????
    public void animInit() {
        SlideInRightAnimationAdapter animationAdapter = new SlideInRightAnimationAdapter(classifyClassAdapter);
        animationAdapter.setDuration(700);
        recycleView.setAdapter(animationAdapter);
    }


}
