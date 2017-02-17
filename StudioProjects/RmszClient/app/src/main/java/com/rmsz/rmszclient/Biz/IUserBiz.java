package com.rmsz.rmszclient.Biz;

/**
 * Created by WALES7 on 2016/12/13.
 */

public interface IUserBiz {
    public void login(String username, String password, OnLoginListener loginListener);
}
