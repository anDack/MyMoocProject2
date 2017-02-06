package com.andack.mymoocproject.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.util.L;
import com.andack.mymoocproject.util.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/6
 * 邮箱：    1160083806@qq.com
 * 描述：    归属地查询UI
 */

public class PhoneAreaFind extends BaseActivity implements View.OnClickListener {
    private EditText phoneEt;
    private ImageView companyIv;
    private TextView phoneArea;
    private Button oneBtn;
    private Button zreoBtn;
    private Button twoBtn;
    private Button threeBtn;
    private Button fourBtn;
    private Button fiveBtn;
    private Button sixBtn;
    private Button sevenBtn;
    private Button eightBtn;
    private Button nineBtn;
    private Button delBtn;
    private Button findBtn;

    private boolean flag=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_area_layout);
        initView();

    }

    private void initView() {
        phoneEt= (EditText) findViewById(R.id.PhoneNumEt);
        companyIv= (ImageView) findViewById(R.id.companyIv);
        phoneArea= (TextView) findViewById(R.id.areaTv);
        zreoBtn= (Button) findViewById(R.id.ZeroBtn);
        oneBtn= (Button) findViewById(R.id.OneBtn);
        twoBtn= (Button) findViewById(R.id.TwoBtn);
        threeBtn= (Button) findViewById(R.id.ThreeBtn);
        fourBtn= (Button) findViewById(R.id.FourBtn);
        fiveBtn= (Button) findViewById(R.id.FiveBtn);
        sixBtn= (Button) findViewById(R.id.SixBtn);
        sevenBtn= (Button) findViewById(R.id.SevenBtn);
        eightBtn= (Button) findViewById(R.id.EightBtn);
        nineBtn= (Button) findViewById(R.id.NineBtn);
        delBtn= (Button) findViewById(R.id.DelBtn);
        findBtn= (Button) findViewById(R.id.FindBtn);
        zreoBtn.setOnClickListener(this);
        oneBtn.setOnClickListener(this);
        twoBtn.setOnClickListener(this);
        threeBtn.setOnClickListener(this);
        fourBtn.setOnClickListener(this);
        fiveBtn.setOnClickListener(this);
        sixBtn.setOnClickListener(this);
        sevenBtn.setOnClickListener(this);
        eightBtn.setOnClickListener(this);
        nineBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);
        delBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                phoneEt.setText("");
                return false;
            }
        });
        findBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str=phoneEt.getText().toString();
        switch (v.getId()) {
            case R.id.ZeroBtn:
            case R.id.OneBtn:
            case R.id.TwoBtn:
            case R.id.ThreeBtn:
            case R.id.FourBtn:
            case R.id.FiveBtn:
            case R.id.SixBtn:
            case R.id.SevenBtn:
            case R.id.EightBtn:
            case R.id.NineBtn:
                if (flag) {
                    flag=false;
                    str="";
                    phoneEt.setText("");
                }
                phoneEt.setText(str+((Button)v).getText());
                //移动光标
                phoneEt.setSelection(str.length()+1);
                break;
            case R.id.DelBtn:
                if (!TextUtils.isEmpty(str) && str.length()>0)
                {
                    phoneEt.setText(str.substring(0,str.length()-1));
                    phoneEt.setSelection(str.length()-1);
                }
                break;
            case R.id.FindBtn:
                if (!TextUtils.isEmpty(str) && str.length()>0)
                    findNumArea(str);
                break;
        }
    }
    //解析并且获取归属地
    private void findNumArea(String str) {
        String URL="http://apis.juhe.cn/mobile/get?phone="+str+"&key="+ StaticClass.PHONEQCEllCORE_KEY;
        RxVolley.get(URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
                L.i(t);
            }



            @Override
            public void onFailure(VolleyError error) {
                Toast.makeText(PhoneAreaFind.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parsingJson(String t) {

        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonRes=jsonObject.getJSONObject("result");
           String resultCode=jsonObject.getString("resultcode");
            switch (resultCode)
            {
                case "203":
                    Toast.makeText(this, "没有比这个手机号码", Toast.LENGTH_SHORT).show();
                    break;
            }
            String res="归属地："+jsonRes.getString("province")+" "+jsonRes.get("city")+" "+jsonRes.get("company")+" "+jsonRes.get("card");
            switch (jsonRes.getString("company"))
            {
                case "移动":
                    companyIv.setImageResource(R.drawable.china_moblie);
                    break;
                case "联通":
                    companyIv.setImageResource(R.drawable.china_ubicom);
                    break;
                case "电信":
                    companyIv.setImageResource(R.drawable.china_telecom);
                    break;
            }

            phoneArea.setText(res);
            flag=true;
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
