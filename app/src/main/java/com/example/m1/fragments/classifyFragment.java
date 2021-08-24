package com.example.m1.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.entity.User;
import com.example.m1.pages.classifyPages.classifySubPages;

public class classifyFragment extends Fragment implements View.OnClickListener {

    private LinearLayout math1;
    private LinearLayout math2;
    private LinearLayout math3;
    private LinearLayout math4;
    private LinearLayout computer1;
    private LinearLayout computer2;
    private LinearLayout computer3;
    private LinearLayout computer4;
    private LinearLayout computer5;
    private LinearLayout computer6;
    private LinearLayout english1;
    private LinearLayout politic1;

    private User user;
    private String uid;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");

        bindView();
    }

    private void bindView() {
        math1 = getActivity().findViewById(R.id.block_math1);
        math2 = getActivity().findViewById(R.id.block_math2);
        math3 = getActivity().findViewById(R.id.block_math3);
        math4 = getActivity().findViewById(R.id.block_math4);

        computer1 = getActivity().findViewById(R.id.block_Computer1);
        computer2 = getActivity().findViewById(R.id.block_Computer2);
        computer3 = getActivity().findViewById(R.id.block_Computer3);
        computer4 = getActivity().findViewById(R.id.block_Computer4);
        computer5 = getActivity().findViewById(R.id.block_Computer5);
        computer6 = getActivity().findViewById(R.id.block_Computer6);

        english1 = getActivity().findViewById(R.id.block_English1);

        politic1 = getActivity().findViewById(R.id.block_Politic1);

        math1.setOnClickListener(this);
        math2.setOnClickListener(this);
        math3.setOnClickListener(this);
        math4.setOnClickListener(this);

        computer1.setOnClickListener(this);
        computer2.setOnClickListener(this);
        computer3.setOnClickListener(this);
        computer4.setOnClickListener(this);
        computer5.setOnClickListener(this);
        computer6.setOnClickListener(this);

        english1.setOnClickListener(this);

        politic1.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classify, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        Intent classifySubPages = new Intent();
        switch (v.getId()) {
            case R.id.block_math1:
                bundle.putString("classType", "高等数学");
                bundle.putString("uid", uid);
                classifySubPages.setClass(this.getContext(), classifySubPages.class);
                break;
            case R.id.block_math2:
                bundle.putString("classType", "线性代数");
                bundle.putString("uid", uid);
                classifySubPages.setClass(this.getContext(), classifySubPages.class);
                break;
            case R.id.block_math3:
                bundle.putString("classType", "离散数学");
                bundle.putString("uid", uid);
                classifySubPages.setClass(this.getContext(), classifySubPages.class);
                break;
            case R.id.block_math4:
                bundle.putString("classType", "概率论");
                bundle.putString("uid", uid);
                classifySubPages.setClass(this.getContext(), classifySubPages.class);
                break;
            case R.id.block_Computer1:
                bundle.putString("classType", "计算机组成");
                bundle.putString("uid", uid);
                classifySubPages.setClass(this.getContext(), classifySubPages.class);
                break;
            case R.id.block_Computer2:
                bundle.putString("classType", "计算机网络");
                bundle.putString("uid", uid);
                classifySubPages.setClass(this.getContext(), classifySubPages.class);
                break;
            case R.id.block_Computer3:
                bundle.putString("classType", "编译原理");
                bundle.putString("uid", uid);
                classifySubPages.setClass(this.getContext(), classifySubPages.class);
                break;
            case R.id.block_Computer4:
                bundle.putString("classType", "数据结构");
                bundle.putString("uid", uid);
                classifySubPages.setClass(this.getContext(), classifySubPages.class);
                break;
            case R.id.block_Computer5:
                bundle.putString("classType", "操作系统");
                bundle.putString("uid", uid);
                classifySubPages.setClass(this.getContext(), classifySubPages.class);
                break;
            case R.id.block_Computer6:
                bundle.putString("classType", "汇编语言");
                bundle.putString("uid", uid);
                classifySubPages.setClass(this.getContext(), classifySubPages.class);
                break;
            case R.id.block_English1:
                bundle.putString("classType", "英语");
                bundle.putString("uid", uid);
                classifySubPages.setClass(this.getContext(), classifySubPages.class);
                break;
            case R.id.block_Politic1:
                bundle.putString("classType", "政治");
                bundle.putString("uid", uid);
                classifySubPages.setClass(this.getContext(), classifySubPages.class);
                break;
        }
        classifySubPages.putExtras(bundle);
        this.getActivity().startActivity(classifySubPages);


    }
}