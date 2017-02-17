package com.rmsz.rmszclient.Presenter;

import android.os.Handler;

import com.rmsz.rmszclient.Beans.User;
import com.rmsz.rmszclient.Biz.IUserBiz;
import com.rmsz.rmszclient.Biz.OnLoginListener;
import com.rmsz.rmszclient.Biz.UserBiz;
import com.rmsz.rmszclient.View.IUserLoginView;

/**
 * Created by WALES7 on 2016/12/13.
 */

public class UserLoginPresenter {
    private IUserBiz userBiz;
    private IUserLoginView userLoginView;
    private Handler mHandler = new Handler();
    public UserLoginPresenter(IUserLoginView userLoginView)
    {
        this.userLoginView = userLoginView;
        this.userBiz = new UserBiz();
    }


    public void login()
    {
        userLoginView.showLoading();
        userBiz.login(userLoginView.getUserName(), userLoginView.getPassword(), new OnLoginListener()
        {
            @Override
            public void loginSuccess(final User user)
            {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userLoginView.toMainActivity(user);
                        userLoginView.hideLoading();
                    }
                });

            }

            @Override
            public void loginFailed(final String message)
            {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userLoginView.showFailedError(message);
                        userLoginView.hideLoading();
                    }
                });

            }
        });
    }
    public void clear()
    {
        userLoginView.clearUserName();
        userLoginView.clearPassword();
    }
}
