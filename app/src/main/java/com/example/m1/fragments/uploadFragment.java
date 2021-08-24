package com.example.m1.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.biz.dynamic.UploadVideoBiz;
import com.cqupt.master_helper.utils.FTPUtils;
import com.cqupt.master_helper.utils.FileUitl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_OK;

public class uploadFragment extends Fragment implements View.OnClickListener {

    private EditText edt_title;
    private EditText edt_introduction;
    private Spinner spn_courseType;
    private Button btn_selectVideo;
    private Button btn_upload;

    private String uid;
    private static final int REQUEST_FILE_CODE = 200;
    private String url = "";
    private boolean flag;
    private int retCode;
    private FileInputStream in;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");
        checkPermission();

        load();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.i("permission", "获取权限成功");
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 201);
        }
    }

    private void load() {
        edt_title = getActivity().findViewById(R.id.edt_title);
        edt_introduction = getActivity().findViewById(R.id.edt_introduction);
        spn_courseType = getActivity().findViewById(R.id.spn_courseType);
        btn_selectVideo = getActivity().findViewById(R.id.btn_selectVideo);
        btn_upload = getActivity().findViewById(R.id.btn_upload);

        btn_selectVideo.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_selectVideo:
//        指定选择的文件类型
                String[] mimeTypes = {"video/*"};
//        ACTION_GET_CONTENT：允许用户选择特殊种类的数据，并返回
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                StringBuilder mimeTypesStr = new StringBuilder();
                for (String mimeType : mimeTypes) {
                    mimeTypesStr.append(mimeType).append("|");
                }
                intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
                startActivityForResult(Intent.createChooser(intent, "选择文件"), REQUEST_FILE_CODE);
                break;
            case R.id.btn_upload:
                if (edt_title.getText().toString() == null) {
                    Toast.makeText(getActivity(), "请输入标题名", Toast.LENGTH_SHORT).show();
                    break;
                }
                try {
                    in = new FileInputStream(new File(url));
                    String title = edt_title.getText().toString().trim();
                    String inroduction = edt_introduction.getText().toString();
//                    Toast.makeText(getActivity(), in.toString(), Toast.LENGTH_SHORT).show();
                    Thread threadUpload = new Thread() {
                        @Override
                        public void run() {
                            flag = FTPUtils.uploadFile(FTPUtils.HOST, FTPUtils.PORT, FTPUtils.USERNAME, FTPUtils.PASSWORD,
                                    FTPUtils.BASE_PATH, FTPUtils.FILE_PATH, title + ".mp4", in);
                        }
                    };
                    threadUpload.start();
                    try {
                        threadUpload.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (flag) {
                        Thread threadUpdate = new Thread() {
                            @Override
                            public void run() {
                                retCode = new UploadVideoBiz().UploadVideoBiz(title, inroduction, uid, "http://47.108.152.107:8888/file/" + title + ".mp4", spn_courseType.getSelectedItem().toString(), "用不到呢");
                            }
                        };
                        threadUpdate.start();
                        try {
                            threadUpdate.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (retCode == 0) {
                            Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
                        } else if (retCode == 1) {
                            Toast.makeText(getActivity(), "连接失败", Toast.LENGTH_SHORT).show();
                        } else if (retCode == 2) {
                            Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FILE_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.d("uri", "uri:" + uri);
            url = FileUitl.getPath(getActivity(), uri);
            Log.d("upload_url", url);
            if (!TextUtils.isEmpty(url)) {
//                Toast.makeText(getActivity(), url, Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}

