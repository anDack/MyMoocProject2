package com.andack.mymoocproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.entity.UserMode;
import com.andack.mymoocproject.ui.LoginActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/15
 * 邮箱：    1160083806@qq.com
 * 描述：    管家助手的用户信息Fragment
 */

public class UserFragment extends Fragment implements View.OnClickListener{
    @Nullable
    private Button changeUserInfoBtn;
    private Button changeUserInfoOkBtn;
    private Button exitLogin;
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
        changeUserInfoOkBtn= (Button) view.findViewById(R.id.changeUserInfoOkBtn);
        exitLogin= (Button) view.findViewById(R.id.exitLoginBtn);
        EtSetEnabled(false);
        setUserInfo();
        changeUserInfoBtn.setOnClickListener(this);
        changeUserInfoOkBtn.setOnClickListener(this);
        exitLogin.setOnClickListener(this);
    }
    //将读取的用户信息显示在相应的位置
    private void setUserInfo() {
        UserMode userMode=BmobUser.getCurrentUser(UserMode.class);
        if (userMode != null) {
            userNameEt.setText(userMode.getUsername());
            userAgeEt.setText(userMode.getAge()+"");
            userSexEt.setText(userMode.getSex()?"男":"女");
            userDecsEt.setText(userMode.getDesc());
        }else {
            Toast.makeText(getActivity(), "这是Bug，兄弟，截图发给我，我给你5毛红包", Toast.LENGTH_SHORT).show();
        }

    }

    private void EtSetEnabled(boolean b) {
        userNameEt.setEnabled(b);
        userAgeEt.setEnabled(b);
        userSexEt.setEnabled(b);
        userDecsEt.setEnabled(b);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeUserInfoBtn:
                EtSetEnabled(true);
                changeUserInfoOkBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.changeUserInfoOkBtn:
                EtSetEnabled(false);
                String name=userNameEt.getText().toString().trim();
                String desc=userDecsEt.getText().toString().trim();
                String sex=userSexEt.getText().toString().trim();
                String age=userAgeEt.getText().toString().trim();
                boolean isSex;
                if (!TextUtils.isEmpty(name) &&! TextUtils.isEmpty(sex) &&! TextUtils.isEmpty(age)) {
                    UserMode userMode=BmobUser.getCurrentUser(UserMode.class);
                    userMode.setUsername(name);
                    userMode.setDesc(desc);
                    userMode.setAge(Integer.parseInt(age));
                    if (sex.equals("男"))
                    {
                        isSex=true;
                    }else {
                        isSex=false;
                    }
                    userMode.setSex(isSex);
                    userMode.update(userMode.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null) {
                                Toast.makeText(getActivity(), "更新信息成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), "千年难遇的的错误,我们能这么办，我也很绝望啊!"
                                        +e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "有关键的信息为空,害怕", Toast.LENGTH_SHORT).show();
                }
                changeUserInfoOkBtn.setVisibility(View.GONE);
                break;
            case R.id.exitLoginBtn:
                BmobUser.logOut();
                BmobUser currentUser = BmobUser.getCurrentUser();
                if (currentUser==null) {
                    Toast.makeText(getActivity(), "退出成功!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                }

                break;
        }
    }
}
