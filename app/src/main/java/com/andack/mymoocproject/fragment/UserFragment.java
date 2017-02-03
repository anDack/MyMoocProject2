package com.andack.mymoocproject.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.entity.UserMode;
import com.andack.mymoocproject.ui.ExpressFind;
import com.andack.mymoocproject.ui.LoginActivity;
import com.andack.mymoocproject.util.L;
import com.andack.mymoocproject.util.UtilTools;
import com.andack.mymoocproject.view.CustomDialog;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

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
    private Button chooseCancel;
    private Button chooseFromCamera;
    private Button chooseFormAlbum;
    private Button expressFind;
    private EditText userNameEt;
    private EditText userAgeEt;
    private EditText userDecsEt;
    private EditText userSexEt;
    private CustomDialog dialog;
    private CircleImageView userHeadCircle;

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
        expressFind= (Button) view.findViewById(R.id.expressFindBtn);
        expressFind.setOnClickListener(this);
        changeUserInfoOkBtn= (Button) view.findViewById(R.id.changeUserInfoOkBtn);
        exitLogin= (Button) view.findViewById(R.id.exitLoginBtn);
        userHeadCircle= (CircleImageView) view.findViewById(R.id.profile_image);
        userHeadCircle.setOnClickListener(this);
        UtilTools.getImg4Share(getActivity(),userHeadCircle);
        WindowManager wm = getActivity().getWindowManager();
        int width=wm.getDefaultDisplay().getWidth();
//        int height=wm.getDefaultDisplay().getHeight();
        dialog=new CustomDialog(getActivity(),width,700,R.layout.dialog_choosehead,R.style.Theme_Dialog, Gravity.BOTTOM,
                R.style.pop_anim_style);
        chooseCancel= (Button) dialog.findViewById(R.id.cancelChoose);
        chooseCancel.setOnClickListener(this);
        chooseFormAlbum= (Button) dialog.findViewById(R.id.chooseFromAlbum);
        chooseFormAlbum.setOnClickListener(this);
        chooseFromCamera= (Button) dialog.findViewById(R.id.chooseFromCamera);
        chooseFromCamera.setOnClickListener(this);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=getActivity().RESULT_CANCELED)
        {
            switch (requestCode) {
                //相册的数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //.相机的数据
                case CAMERA_REQUEST_CODE:
                    tempFile=new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RES_REQUEST_CODE:
                    //有可能选择取消
                   if (data!=null)
                   {
                       //拿到图片设置
                       setImageView(data);
                       //原先的删掉
                       if (tempFile!=null)
                       {
                           tempFile.delete();
                       }
                   }
                    break;
            }
        }else{

        }
    }
    private void setImageView(Intent data)
    {
        Bundle bundle=data.getExtras();
        //设置图片
        if (bundle!=null) {
            Bitmap bitmap=bundle.getParcelable("data");
            userHeadCircle.setImageBitmap(bitmap);
        }

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
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.cancelChoose:
                dialog.dismiss();
                break;
            case R.id.chooseFromAlbum:
                toAlbum();
                break;
            case R.id.chooseFromCamera:
                toCamera();
                break;
            case R.id.expressFindBtn:
                Intent intent=new Intent(getActivity(), ExpressFind.class);
                startActivity(intent);
                break;
        }
    }
    public static final String PHOTO_IMAGE_FILE_NAME="fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE=100;
    public static final int IMAGE_REQUEST_CODE=101;
    public static final int RES_REQUEST_CODE=102;
    File tempFile;
    private void startPhotoZoom(Uri uri){
        if (uri==null) {
            L.e("uri==null");
            return;
        }
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //设置裁剪
        intent.putExtra("crop",true);
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //设置裁剪图片的质量
        intent.putExtra("outputX",320);
        intent.putExtra("outputY",320);
        //发送数据
        intent.putExtra("return-data",true);
        startActivityForResult(intent,RES_REQUEST_CODE);
    }
    //跳转相机

    /**
     * 这里涉及到知识点为调用系统相机的方法
     */
    private void toCamera() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可以的话就进行存储
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                ,PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }
    //跳转相册

    /**
     * 这里涉及到调用系统相册
     */
    private void toAlbum() {

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UtilTools.putImg2Share(getActivity(),userHeadCircle);
    }
}
