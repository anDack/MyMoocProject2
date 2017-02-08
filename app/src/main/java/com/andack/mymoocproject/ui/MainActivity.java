package com.andack.mymoocproject.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.fragment.BluerFragment;
import com.andack.mymoocproject.fragment.GirlsFragment;
import com.andack.mymoocproject.fragment.UserFragment;
import com.andack.mymoocproject.fragment.WechatFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CheckPermissionsActivity implements OnClickListener{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton fab_setting;
    private List<String> mTitle;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        needPermissions= new String[]{
                Manifest.permission.RECEIVE_SMS,
        };
        //去掉阴影
        getSupportActionBar().setElevation(0);
        initData();
        initView();
        //测试Bugly的功能
//        CrashReport.testJavaCrash();

    }

    private void initView() {
        mTabLayout= (TabLayout) findViewById(R.id.myTabLayout);
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
        fab_setting= (FloatingActionButton) findViewById(R.id.fab_setting);
        fab_setting.setVisibility(View.GONE);
        //mButton.setImageResource(R.mipmap.ic_launcher);
        //预加载
        mViewPager.setOffscreenPageLimit(mFragments.size());//设置预读页数
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
            //设置标题

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }

        });//设置Adapter
        //绑定ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
        //ViewPager的滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                Log.i("TAG", "onPageSelected: 第 "+position+"页");
                if (position==0)
                {
                    fab_setting.setVisibility(View.GONE);
                }else {
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fab_setting.setOnClickListener(this);
    }

    private void initData() {
        mTitle=new ArrayList<>();
        mTitle.add(getString(R.string.bluer));
        mTitle.add(getString(R.string.wechat));
        mTitle.add(getString(R.string.girls));
        mTitle.add(getString(R.string.user));
        mFragments=new ArrayList<>();
        mFragments.add(new BluerFragment());
        mFragments.add(new WechatFragment());
        mFragments.add(new GirlsFragment());
        mFragments.add(new UserFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab_setting:
                Intent intent=new Intent(MainActivity.this,Setting.class);
                startActivity(intent);
                break;
        }

    }
}
