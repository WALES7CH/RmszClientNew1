package com.rmsz.rmszclient.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.rmsz.rmszclient.Base.BaseInfo;
import com.rmsz.rmszclient.Beans.User;
import com.rmsz.rmszclient.Presenter.UserLoginPresenter;
import com.rmsz.rmszclient.R;
import com.rmsz.rmszclient.View.IUserLoginView;
import com.zhy.utils.SPUtils;
import com.zhy.utils.T;

import zuo.biao.library.util.MD5Util;

/**
 * Created by WALES7 on 2016/12/13.
 */

public class UserLoginActivity extends Activity implements IUserLoginView {
    private EditText mEtUsername, mEtPassword;
    private Button mBtnLogin, mBtnClear;
    private ProgressBar mPbLoading;
    private CheckBox mCb_mima;
    private CheckBox mCb_auto;

    private UserLoginPresenter mUserLoginPresenter = new UserLoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        setRemerberMe();
//        setAutoLogin();
        initViews();
        autoLogin();
    }

    private void autoLogin() {
        Boolean autoLogin = (Boolean) SPUtils.get(this, BaseInfo.SHARE_KEY_AUTOLOGIN, false);
        Boolean rememberMe = (Boolean) SPUtils.get(this,BaseInfo.SHARE_KEY_REMEMBER_ME,false);
        if(rememberMe){
            //设置默认是记录密码状态
            mCb_mima.setChecked(true);
            String useranme = (String)SPUtils.get(this,BaseInfo.SHARE_KEY_USERNAME,"");
            String password = (String)SPUtils.get(this, BaseInfo.SHARE_KEY_UNPASSWORD,"");
            mEtUsername.setText(useranme);
            mEtPassword.setText(password);
            if(autoLogin){
                Intent intent = new Intent();
                intent.setClass(this,AutoLoginActivity.class);
                startActivity(intent);
            }
        }
    }

    private void initViews() {
        mEtUsername = (EditText) findViewById(R.id.login_et_username);
        mEtPassword = (EditText) findViewById(R.id.login_et_password);
        mCb_mima = (CheckBox) findViewById(R.id.cb_mima);
        mCb_auto = (CheckBox) findViewById(R.id.cb_auto);

//        mBtnClear = (Button) findViewById(R.id.id_btn_clear);
        mBtnLogin = (Button) findViewById(R.id.login_btn_signin);

        mPbLoading = (ProgressBar) findViewById(R.id.login_pb_progress);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserLoginPresenter.login();
            }
        });
        mCb_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCb_auto.isChecked()) {
                    System.out.println("autoLogin已选中");
                    SPUtils.put(UserLoginActivity.this, BaseInfo.SHARE_KEY_AUTOLOGIN, true);
                } else {
                    System.out.println("autoLogin没有选中");
                    SPUtils.put(UserLoginActivity.this, BaseInfo.SHARE_KEY_AUTOLOGIN, false);

                }
            }
        });
        mCb_mima.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCb_mima.isChecked()) {
                    System.out.println("记住密码已选中");
                    SPUtils.put(UserLoginActivity.this, BaseInfo.SHARE_KEY_REMEMBER_ME, true);

                } else {
                    System.out.println("记住密码没有选中");
                    SPUtils.put(UserLoginActivity.this, BaseInfo.SHARE_KEY_REMEMBER_ME, false);

                }
            }
        });

//        mBtnClear.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                mUserLoginPresenter.clear();
//            }
//        });
    }

    @Override
    public String getUserName() {
        return mEtUsername.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString().trim();
    }

    @Override
    public void clearUserName() {
        mEtUsername.setText("");
    }

    @Override
    public void clearPassword() {
        mEtPassword.setText("");
    }

    @Override
    public void showLoading() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity(User user) {

        SPUtils.put(this, BaseInfo.SHARE_KEY_TOKEN, user.getToken());
        SPUtils.put(this, BaseInfo.SHARE_KEY_USERNAME, user.getUsername());
        SPUtils.put(this,BaseInfo.SHARE_KEY_UNPASSWORD,user.getPassword());
        SPUtils.put(this, BaseInfo.SHARE_KEY_PASSWORD, MD5Util.MD5(user.getPassword()));
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFailedError(String message) {
        T.showShort(this, message);
    }
}
