package com.example.m1.pages;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.dao.UserDao;
import com.cqupt.master_helper.entity.User;
import com.example.m1.adapter.recommendVideoAdapter;
import com.example.m1.adapter.searchAdapter;
import com.example.m1.bean.recycleViewData;
import com.example.m1.pages.activityUtilities.activityUtilities;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

public class searchPageActivity extends AppCompatActivity {

    private RecyclerView recycleView;
    private EditText searchBar;
    private TextView defaultContent;
    private String searchContent;
    private LinearLayoutManager layoutManager;
    private searchAdapter searchAdapter;

    //    private User user;
    private String uid;

    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 20;

    private int fromIndex = 0;
    private int toIndex = PAGE_COUNT;

    private int Type = 4;

    private ArrayList<recycleViewData> LocalData = new ArrayList<>();
    private ArrayList<recycleViewData> newDatas;
//    private final String[] videoUri = {
//            "http://vjs.zencdn.net/v/oceans.mp4",
//            "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
//            "https://media.w3.org/2010/05/sintel/trailer.mp4",
//            "http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4",
//            "https://v-cdn.zjol.com.cn/280443.mp4",
//            "https://v-cdn.zjol.com.cn/276982.mp4",
//            "https://v-cdn.zjol.com.cn/276985.mp4",
//            "https://v-cdn.zjol.com.cn/276984.mp4"
//    };

    //----------------------------------------------------------------------------------------------
    //生命周期
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                user = new UserDao().userInfo(uid);
//            }
//        };
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        searchAdapter = new searchAdapter(this.LocalData);
        bindView();
        adapterInit();
        //animInit();
    }

    //----------------------------------------------------------------------------------------------
    //列表
    //绑定视图、设定监听器
    private void bindView() {

        this.recycleView = this.findViewById(R.id.recycleView_search);
        this.searchBar = this.findViewById(R.id.editText_search);
        this.defaultContent = findViewById(R.id.text_DefaultSearch);

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (defaultContent.getVisibility() != View.GONE) {
                    defaultContent.setVisibility(View.GONE);
                }
                searchAdapter.resetDatas();
                searchContent = v.getText().toString();
                updateRecyclerView();
                return false;
            }
        });
    }

    //填充器初始化
    private void adapterInit() {
//        this.searchAdapter = new searchAdapter(this.LocalData);
        this.layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(searchAdapter);
        //点击监听
        searchAdapter.setOnItemClickListener(position -> {
            LocalData.addAll(newDatas);
            Intent videoPlayPage = new Intent(this, videoPlayPageActivity.class);
            videoPlayPage.putExtras(bundlePack(position));
            this.startActivity(videoPlayPage);
        });
        //滑动监听
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                            @Override
                                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                                super.onScrollStateChanged(recyclerView, newState);
                                                // 在newState为滑到底部时
                                                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                                    // 如果没有隐藏footView，那么最后一个条目的位置就比我们的getItemCount少1，自己可以算一下
                                                    if (searchAdapter.isFadeTips() == false && lastVisibleItem + 1 == searchAdapter.getItemCount()) {
                                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                // 然后调用updateRecyclerview方法更新RecyclerView
                                                                updateRecyclerView();
                                                            }
                                                        }, 0);
                                                    }

                                                    // 如果隐藏了提示条，我们又上拉加载时，那么最后一个条目就要比getItemCount要少2
                                                    if (searchAdapter.isFadeTips() == true && lastVisibleItem + 2 == searchAdapter.getItemCount()) {
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
                                        }
        );
    }

    //更新列表
    private void updateRecyclerView() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                // 获取从fromIndex到toIndex的数据
                newDatas = activityUtilities.getDatas(fromIndex, toIndex, Type, searchContent);
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
            searchAdapter.updateList(newDatas, true);
            fromIndex += PAGE_COUNT;
            toIndex += PAGE_COUNT;
        } else {
            searchAdapter.updateList(null, false);
        }
    }


    //列表动画设置
    public void animInit() {
        SlideInRightAnimationAdapter animationAdapter = new SlideInRightAnimationAdapter(searchAdapter);
        animationAdapter.setDuration(700);
        recycleView.setAdapter(animationAdapter);
    }


    //传递参数
    public Bundle bundlePack(int position) {
        Bundle tempBundle = new Bundle();
        tempBundle.putString("videoName", LocalData.get(position).videoName);
        tempBundle.putString("uploaderName", LocalData.get(position).uploaderName);
        tempBundle.putString("videoUri", LocalData.get(position).videoUri);
        tempBundle.putString("introduction", LocalData.get(position).videoDesc);
        tempBundle.putString("authorUid", LocalData.get(position).authorUid);
        tempBundle.putString("uid", uid);
        tempBundle.putString("vid", LocalData.get(position).vid);
        return tempBundle;
    }
}
