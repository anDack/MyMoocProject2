package com.andack.mymoocproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.andack.mymoocproject.R;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/15
 * 邮箱：    1160083806@qq.com
 * 描述：    管家助手的Fragment
 */

public class UserFragment extends Fragment implements View.OnClickListener{
    @Nullable
    private Button changeUserInfoBtn;
    private EditText userNameEt;
    private EditText userAgeEt;
    private EditText userDecsEt;
    private EditText userSexEt;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_layout,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        changeUserInfoBtn= (Button) view.findViewById(R.id.changeUserInfoBtn);
        userNameEt= (EditText) view.findViewById(R.id.userName_UserInfo);
        userAgeEt= (EditText) view.findViewById(R.id.userAge_UserInfo);
        userDecsEt= (EditText) view.findViewById(R.id.userDes_UserInfo);
        userSexEt= (EditText) view.findViewById(R.id.userSex_UserInfo);
        userNameEt.setEnabled(false);
        userAgeEt.setEnabled(false);
        userSexEt.setEnabled(false);
        userDecsEt.setEnabled(false);
        changeUserInfoBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeUserInfoBtn:
                userNameEt.setEnabled(true);
                userDecsEt.setEnabled(true);
                userAgeEt.setEnabled(true);
                userSexEt.setEnabled(true);
                break;
        }
    }
}
