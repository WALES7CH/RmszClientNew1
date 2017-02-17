package com.rmsz.rmszclient.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.rmsz.rmszclient.Base.BaseInfo;
import com.rmsz.rmszclient.Beans.User;
import com.rmsz.rmszclient.Presenter.UserLoginPresenter;
import com.rmsz.rmszclient.R;
import com.rmsz.rmszclient.View.IUserLoginView;
import com.zhy.utils.SPUtils;
import com.zhy.utils.T;

public class AutoLoginActivity extends Activity implements IUserLoginView {
    // Content View Elements

    private LinearLayout mActivity_auto_login;
    private ProgressBar mPgBar;
    private TextView mTv1;
    private Button mBtn_back;

    // End Of Content View Elements
    private UserLoginPresenter mUserLoginPresenter = new UserLoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_auto_login);
        bindViews();
        mBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mUserLoginPresenter.login();

    }


    private void bindViews() {

        mActivity_auto_login = (LinearLayout) findViewById(R.id.activity_auto_login);
        mPgBar = (ProgressBar) findViewById(R.id.pgBar);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mBtn_back = (Button) findViewById(R.id.btn_back);
    }
    @Override
    public String getUserName() {
        return (String)SPUtils.get(this,BaseInfo.SHARE_KEY_USERNAME,"");
    }

    @Override
    public String getPassword() {
        return (String)SPUtils.get(this,BaseInfo.SHARE_KEY_UNPASSWORD,"");
    }

    @Override
    public void clearUserName() {

    }

    @Override
    public void clearPassword() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void toMainActivity(User user) {
        SPUtils.put(this, BaseInfo.SHARE_KEY_TOKEN, user.getToken());
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFailedError(String message) {
        T.showShort(this, message);
        finish();
    }
}
