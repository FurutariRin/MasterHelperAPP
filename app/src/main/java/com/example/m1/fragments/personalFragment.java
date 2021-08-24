package com.example.m1.fragments;

import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.entity.User;
import com.example.m1.pages.basicManageActivity;
import com.example.m1.pages.personalSubPage.personalInfoActivity;

public class personalFragment extends Fragment implements View.OnClickListener {

    private String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");

        bindView();
    }

    private void bindView() {
        LinearLayout download = this.getActivity().findViewById(R.id.download);
        LinearLayout history = this.getActivity().findViewById(R.id.history);
        LinearLayout favorite = this.getActivity().findViewById(R.id.favorite);
        LinearLayout playLater = this.getActivity().findViewById(R.id.playLater);
        LinearLayout classSetting = this.getActivity().findViewById(R.id.classSetting);
        LinearLayout studyPlan = this.getActivity().findViewById(R.id.studyPlan);
        LinearLayout feedback = this.getActivity().findViewById(R.id.feedback);
        LinearLayout personInfoSetting = this.getActivity().findViewById(R.id.personInfoSetting);

        download.setOnClickListener(this);
        history.setOnClickListener(this);
        favorite.setOnClickListener(this);
        playLater.setOnClickListener(this);
        classSetting.setOnClickListener(this);
        studyPlan.setOnClickListener(this);
        feedback.setOnClickListener(this);
        personInfoSetting.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personInfoSetting:
                Intent personalInfo = new Intent();
                personalInfo.setClass(this.getActivity(), personalInfoActivity.class);
                personalInfo.putExtra("uid",uid);
                this.getActivity().startActivity(personalInfo);
                break;
            default:
                Toast.makeText(this.getActivity(), "此功能尚未开放", Toast.LENGTH_SHORT).show();
        }

    }
}