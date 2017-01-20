package com.andack.mymoocproject.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andack.mymoocproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/19
 * 邮箱：    1160083806@qq.com
 * 描述：    导引页面
 */
public class GuideActivity extends AppCompatActivity{
    private ViewPager viewPager;
    private List<View> views=new ArrayList<>();
    private View view1,view2,view3;
    private ImageView point1,point2,point3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);

        initView();
    }

    private void initView() {
        viewPager= (ViewPager) findViewById(R.id.myViewPager);
        //引导页的小圆点
        point1= (ImageView) findViewById(R.id.firstPointGuide);
        point2= (ImageView) findViewById(R.id.secondPointGuide);
        point3= (ImageView) findViewById(R.id.thredPointGuide);
        pointChange(true,false,false);
        view1=View.inflate(this,R.layout.guide_one,null);
        view2=View.inflate(this,R.layout.guide_two,null);
        view3=View.inflate(this,R.layout.guide_three,null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        viewPager.setAdapter(new myViewAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        pointChange(true,false,false);
                        break;
                    case 1:
                        pointChange(false,true,false);
                        break;
                    case 2:
                        pointChange(false,false,true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private void pointChange(boolean point1S,boolean point2S,boolean point3S)
    {
        if (point1S) {
            point1.setImageResource(R.drawable.point_on);
        }else {
            point1.setImageResource(R.drawable.point_off);
        }
        if (point2S) {
            point2.setImageResource(R.drawable.point_on);
        }else {
            point2.setImageResource(R.drawable.point_off);
        }
        if (point3S) {
            point3.setImageResource(R.drawable.point_on);
        }else {
            point3.setImageResource(R.drawable.point_off);
        }
    }
    class myViewAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(views.get(position));

        }
    }
}
