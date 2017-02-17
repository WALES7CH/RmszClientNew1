package com.rmsz.rmszclient.Biz;

import com.rmsz.rmszclient.Beans.User;

/**
 * Created by WALES7 on 2016/12/13.
 */

public interface OnLoginListener {
    void loginSuccess(User user);

    void loginFailed(String message);
}
