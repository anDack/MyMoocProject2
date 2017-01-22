package com.andack.mymoocproject.entity;

import cn.bmob.v3.BmobUser;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/22
 * 邮箱：    1160083806@qq.com
 * 描述：    用户属性扩展自BmobUser
 */

public class UserMode extends BmobUser{
    private int age;
    private boolean sex;
    private String desc;
    //迅速拿到get/set方法的快捷键,ALT+INS

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
