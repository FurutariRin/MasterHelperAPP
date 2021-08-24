package com.example.m1.pages;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.dao.UserDao;
import com.cqupt.master_helper.entity.User;
import com.example.m1.fragments.classifyFragment;
import com.example.m1.fragments.favoritesFragment;
import com.example.m1.fragments.mainPageFragment;
import com.example.m1.fragments.personalFragment;
import com.example.m1.fragments.uploadFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class mainPageActivity extends AppCompatActivity {

    private android.app.FragmentManager FragmentManager;

    private FragmentTransaction transaction;

    private BottomNavigationView navigationView;

    private personalFragment fragment_personalInfo;
    private mainPageFragment fragment_mainPage;
    private uploadFragment fragment_upload;
    private classifyFragment fragment_setting;
    private favoritesFragment fragment_favorites;

    private User user;
    private String uid;

    public static mainPageActivity activity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        activity = this;

        //初始化
        bindView();
        navInit();


    }

    private void bindView() {
        navigationView = this.findViewById(R.id.mainPageBottomNavigationView);

    }

    //-----------------------------------------------------------------------------------------------
    //导航栏初始化
    public void navInit() {
        FragmentManager = getFragmentManager();
        transaction = FragmentManager.beginTransaction();
        if (fragment_mainPage == null) {
            fragment_mainPage = new mainPageFragment();
            transaction.add(R.id.mainPageFragmentContainer, fragment_mainPage);
        } else {
            transaction.show(fragment_mainPage);
        }
        transaction.commit();

        //设置监听
        navigationView.setOnNavigationItemSelectedListener(
                item -> {
                    FragmentManager = getFragmentManager();
                    transaction = FragmentManager.beginTransaction();
                    hideAllFragment(transaction);
                    switch (item.getItemId()) {
                        case R.id.nvg_home:
                            if (fragment_mainPage == null) {
                                fragment_mainPage = new mainPageFragment();
                                transaction.add(R.id.mainPageFragmentContainer, fragment_mainPage);
                            } else {
                                transaction.show(fragment_mainPage);
                            }
                            break;
                        case R.id.nvg_personal:
                            if (fragment_personalInfo == null) {
                                fragment_personalInfo = new personalFragment();
                                transaction.add(R.id.mainPageFragmentContainer, fragment_personalInfo);
                            } else {
                                transaction.show(fragment_personalInfo);
                            }
                            break;
                        case R.id.nvg_upload:
                            if (fragment_upload == null) {
                                fragment_upload = new uploadFragment();
                                transaction.add(R.id.mainPageFragmentContainer, fragment_upload);
                            } else {
                                transaction.show(fragment_upload);
                            }
                            break;
                        case R.id.nvg_setting:
                            if (fragment_setting == null) {
                                fragment_setting = new classifyFragment();
                                transaction.add(R.id.mainPageFragmentContainer, fragment_setting);
                            } else {
                                transaction.show(fragment_setting);
                            }
                            break;
                        case R.id.nvg_favorite:
                            if (fragment_favorites == null) {
                                fragment_favorites = new favoritesFragment();
                                transaction.add(R.id.mainPageFragmentContainer, fragment_favorites);
                            } else {
                                transaction.show(fragment_favorites);
                            }
                            break;
                    }
                    transaction.commit();
                    return true;
                });
    }


    //-----------------------------------------------------------------------------------------------
    //隐藏所有的fragments
    public void hideAllFragment(FragmentTransaction transaction) {
        if (fragment_personalInfo != null) {
            transaction.hide(fragment_personalInfo);
        }
        if (fragment_mainPage != null) {
            transaction.hide(fragment_mainPage);
        }
        if (fragment_setting != null) {
            transaction.hide(fragment_setting);
        }
        if (fragment_upload != null) {
            transaction.hide(fragment_upload);
        }
        if (fragment_favorites != null) {
            transaction.hide(fragment_favorites);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}