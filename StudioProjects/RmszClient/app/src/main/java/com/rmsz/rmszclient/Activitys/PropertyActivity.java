package com.rmsz.rmszclient.Activitys;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rmsz.rmszclient.Fragments.PropertyFragment;
import com.rmsz.rmszclient.Fragments.UpdateFragment;
import com.rmsz.rmszclient.R;
import com.rmsz.rmszclient.Services.DevicePropertyService;

import java.util.ArrayList;
import java.util.HashMap;

public class PropertyActivity extends Activity implements View.OnClickListener {
    private static final String TAG  = "PropertyActivity";    // Content View Elements

    private RelativeLayout mActivity_property;
    private LinearLayout mLl_pro_main_title;
    private TextView mTv_pro_main_title;
    private LinearLayout mLl_pro_main_bottom;
    private LinearLayout mTab_pro_property;
    private LinearLayout mTab_pro_update;
    private FrameLayout mPro_main_content;
    private PropertyFragment mPropertyFragment;
    private UpdateFragment mUpdateFragment;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    private String agentId = "";

    // End Of Content View Elements
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_property);
        Intent intent = getIntent();
        this.agentId = intent.getStringExtra("agent_id");
        bindViews();

        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);

    }


    private void bindViews() {
        mActivity_property = (RelativeLayout) findViewById(R.id.activity_property);
        mLl_pro_main_title = (LinearLayout) findViewById(R.id.ll_pro_main_title);
        mTv_pro_main_title = (TextView) findViewById(R.id.tv_pro_main_title);
        mLl_pro_main_bottom = (LinearLayout) findViewById(R.id.ll_pro_main_bottom);
        mTab_pro_property = (LinearLayout) findViewById(R.id.tab_pro_property);
        mTab_pro_update = (LinearLayout) findViewById(R.id.tab_pro_update);
        mPro_main_content = (FrameLayout) findViewById(R.id.pro_main_content);

        mTab_pro_property.setOnClickListener(this);
        mTab_pro_update.setOnClickListener(this);
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
     */
    private void setTabSelection(int index) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        Log.e("setTabSelection", index + "==========>");
        switch (index) {
            case 0:
                if (mPropertyFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mPropertyFragment = new PropertyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("agent_id", agentId);
                    mPropertyFragment.setArguments(bundle);
                    transaction.add(R.id.pro_main_content, mPropertyFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mPropertyFragment);
                }
                break;
            case 1:
            default:
                if (mUpdateFragment == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    mUpdateFragment = new UpdateFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("agent_id", agentId);
                    mUpdateFragment.setArguments(bundle);
                    transaction.add(R.id.pro_main_content, mUpdateFragment);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(mUpdateFragment);
                }
                break;
        }
        transaction.commit();
    }


    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mPropertyFragment != null) {
            transaction.hide(mPropertyFragment);
        }
        if (mUpdateFragment != null) {
            transaction.hide(mUpdateFragment);
        }
    }

    @Override
    public void onClick(View v) {
        Log.e("click",v.getId()+ "");
        switch (v.getId()){
            case R.id.tab_pro_property:
                Log.e("click",v.getId()+ "");
                setTabSelection(0);
                break;
            case R.id.tab_pro_update:
                Log.e("click",v.getId()+ "");
                setTabSelection(1);
                break;
            default:
                break;
        }
    }
}
