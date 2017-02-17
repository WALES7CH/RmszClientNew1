package com.rmsz.rmszclient.Activitys;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.rmsz.rmszclient.Base.BaseInfo;
import com.rmsz.rmszclient.Base.ReGetToken;
import com.rmsz.rmszclient.Beans.City;
import com.rmsz.rmszclient.Beans.Search;
import com.rmsz.rmszclient.Beans.SearchResult;
import com.rmsz.rmszclient.Fragments.ManageFragment;
import com.rmsz.rmszclient.Fragments.SearchFragment;
import com.rmsz.rmszclient.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import zuo.biao.library.util.Json;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private TextView mTvToken;
    private Spinner spinner;
    private List<String> data_list;
    private Button mBtnGetToken , mBtnGetStatus;
    private ArrayAdapter<String> arr_adapter;
    private String token = "";

    private LinearLayout mTabSearch,mTabManage;

    private ArrayList<Fragment> fragments;
    private SearchFragment mSearchFragment;
    private ManageFragment mManageFragment;
    private FragmentManager fragmentManager = getFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mTvToken = (TextView) findViewById(R.id.main_tv_token);
        mBtnGetToken = (Button) findViewById(R.id.main_btn_getToken);
        mBtnGetStatus = (Button) findViewById(R.id.main_btn_getStatus);
        //initData();
        //initListeners();



        // 初始化控件和声明事件
        mTabSearch= (LinearLayout) findViewById(R.id.main_tab_search);
        mTabManage = (LinearLayout) findViewById(R.id.main_tab_manage);
        mTabSearch.setOnClickListener(this);
        mTabManage.setOnClickListener(this);

        setDefaultFragment();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tab_search:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.main_tab_manage:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            default:
                break;
        }
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
        switch (index) {
            case 0:
                if (mSearchFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mSearchFragment = new SearchFragment();
                    transaction.add(R.id.main_frlayout_content, mSearchFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mSearchFragment);
                }
                break;
            case 1:
                if (mManageFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    mManageFragment = new ManageFragment();
                    transaction.add(R.id.main_frlayout_content, mManageFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(mManageFragment);
                }
                break;
            default:
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
        if (mSearchFragment != null) {
            transaction.hide(mSearchFragment);
        }
        if (mManageFragment != null) {
            transaction.hide(mManageFragment);
        }
    }

    public void onClick_bak(View v)
    {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        switch (v.getId())
        {
            case R.id.main_tab_search:
                if (mSearchFragment == null)
                {
                    mSearchFragment = new SearchFragment();
                    // 使用当前Fragment的布局替代id_content的控件
                    fragments.add(mSearchFragment);
                    transaction.add(R.id.main_frlayout_content, mSearchFragment);
                }else{
                    showFragment(transaction,mSearchFragment);
                }

                break;
            case R.id.main_tab_manage:
                if (mManageFragment == null)
                {
                    mManageFragment = new ManageFragment();
                    fragments.add(mManageFragment);
                    transaction.add(R.id.main_frlayout_content, mManageFragment);
                }else{
                    showFragment(transaction,mManageFragment);
                }

                break;
        }
        // transaction.addToBackStack();
        // 事务提交
        transaction.commit();
    }

    private void  showFragment(FragmentTransaction transaction,Fragment fragment){
        for (int i =0 ;i<fragments.size();i++){
            if(fragments.get(i) == fragment){
                transaction.show(fragment);
            }else{
                transaction.hide(fragments.get(i));
            }
        }
    }

    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mSearchFragment = new SearchFragment();
        transaction.replace(R.id.main_frlayout_content, mSearchFragment);
        transaction.commit();
    }

    private void initListeners() {
        mBtnGetToken.setOnClickListener(this);
        mBtnGetStatus.setOnClickListener(this);
    }

    private void initData() {
        token = (String) SPUtils.get(getApplicationContext(), BaseInfo.SHARE_KEY_TOKEN, "");
        mTvToken.setText(token);
        //返回API地址
        String url = BaseInfo.genApiUrl("Search/getCitys", token, null);//API_SERVER_URL + API方法名

        Log.e(TAG, url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, response);
//                mTvToken.setText(response);
                        City citys = Json.parseObject(response, City.class);

                        spinner = (Spinner) findViewById(R.id.main_sp_citys);

                        //数据
                        data_list = new ArrayList<String>();
                        for (int i = 0; i < citys.getData().size(); i++) {
                            data_list.add(citys.getData().get(i).getName());
                        }

                        //适配器
                        arr_adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, data_list);
                        //设置样式
                        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //加载适配器
                        spinner.setAdapter(arr_adapter);
                    }
                });
    }

    public void getStatus(){
        HashMap params = new HashMap();
        params.put("keyword",this.spinner.getSelectedItem().toString());
        String url =BaseInfo.genApiUrl("Search/getStatus", token, params);//API_SERVER_URL + API方法名
        Log.e(TAG, url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("respone",response.replace(" ",""));
                        if(response.replace(" ","").contains(":200") ){
                            SearchResult result = Json.parseObject(response, SearchResult.class);
                            onGetStatusResponse(result);
                        }else if (response.replace(" ","").contains(":202") && response.replace(" ","").contains("path")){
                            Search result = Json.parseObject(response,Search.class);
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this,VerifyActivity.class);
                            intent.putExtra("path",result.getData().getPath());
                            startActivity(intent);
                        }
                    }
                });
    }

    private void onGetStatusResponse(SearchResult result){
        mTvToken.setText(result.getData().toString());

        ListView listView=(ListView)findViewById(R.id.main_list_result);
        listView.setOnClickListener(this);
        String[] from={"agent_name","agent_code"};              //这里是ListView显示内容每一列的列名
        int[] to={R.id.agent_name,R.id.agent_code};   //这里是ListView显示每一列对应的list_item中控件的id
        ArrayList<HashMap<String,String>> list=null;
        HashMap<String,String> map=null;

        //创建ArrayList对象；
        list=new ArrayList<HashMap<String,String>>();
        //将数据存放进ArrayList对象中，数据安排的结构是，ListView的一行数据对应一个HashMap对象，
        //HashMap对象，以列名作为键，以该列的值作为Value，将各列信息添加进map中，然后再把每一列对应
        //的map对象添加到ArrayList中

        for(int i=0; i<result.getData().size(); i++){
            map=new HashMap<String,String>();       //为避免产生空指针异常，有几列就创建几个map对象
            map.put("agent_code", result.getData().get(i).getAgent_code());
            map.put("agent_name", result.getData().get(i).getAgent_name());
            list.add(map);
        }
        //调用ListActivity的setListAdapter方法，为ListView设置适配器
        listView.setAdapter(new SimpleAdapter(this,list,R.layout.layout_list_item_search,from,to));
    }
}
