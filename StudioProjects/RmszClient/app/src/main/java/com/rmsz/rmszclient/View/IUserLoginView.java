package com.rmsz.rmszclient.View;

import com.rmsz.rmszclient.Beans.User;

/**
 * Created by WALES7 on 2016/12/13.
 */

public interface IUserLoginView {
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError(String message);
}
