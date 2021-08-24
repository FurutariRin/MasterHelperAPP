package com.example.m1.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;


import android.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.entity.User;
import com.example.m1.fragments.mainPageSubFragment.rankFragment;
import com.example.m1.fragments.mainPageSubFragment.recommendFragment;
import com.example.m1.pages.searchPageActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class mainPageFragment extends Fragment {

    private FragmentManager fragmentManagerSub;

    private FragmentTransaction transaction;

    private recommendFragment recommendFragment;
    private rankFragment rank_Fragment;

    private BottomNavigationView navigationViewSub;

    private LinearLayout searchLinearLayout;

    private String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManagerSub = this.getActivity().getFragmentManager();
        transaction = fragmentManagerSub.beginTransaction();
        return inflater.inflate(R.layout.fragment_mainpage, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");

        bindView();
        navInit();


    }

    private void bindView() {
        this.navigationViewSub = this.getActivity().findViewById(R.id.mainPageBottomNavigationViewFragment);
        this.searchLinearLayout =this.getActivity().findViewById(R.id.linearLayout_searchVideoBar);

        searchLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchPage = new Intent(getActivity(), searchPageActivity.class);
                searchPage.putExtra("uid",uid);
                getActivity().startActivity(searchPage);
            }
        });

    }

    private void navInit() {
        navigationViewSub.setOnNavigationItemSelectedListener(item -> {
            this.fragmentManagerSub = getFragmentManager();
            transaction = fragmentManagerSub.beginTransaction();
            hideAllFragments();
            switch (item.getItemId()) {
                case R.id.recommend_btn:
                    transaction.show(recommendFragment);
                    break;
                case R.id.rank_btn:
                    if (rank_Fragment == null) {
                        rank_Fragment = new rankFragment();
                        transaction.add(R.id.subMainpageFragmentContainer, rank_Fragment);
                    } else {
                        transaction.show(rank_Fragment);
                    }
                    break;
            }
            transaction.commit();
            return true;
        });
        if (recommendFragment == null) {
            recommendFragment = new recommendFragment();
            transaction.add(R.id.subMainpageFragmentContainer, recommendFragment);
            transaction.commit();
        }
    }

    private void hideAllFragments() {
        if (recommendFragment != null)
            transaction.hide(recommendFragment);
        if (rank_Fragment != null)
            transaction.hide(rank_Fragment);
    }


}