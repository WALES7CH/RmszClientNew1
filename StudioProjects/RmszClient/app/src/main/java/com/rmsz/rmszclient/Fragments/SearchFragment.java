package com.rmsz.rmszclient.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.rmsz.rmszclient.Activitys.PropertyActivity;
import com.rmsz.rmszclient.Activitys.VerifyActivity;
import com.rmsz.rmszclient.Base.BaseInfo;
import com.rmsz.rmszclient.Base.ReGetToken;
import com.rmsz.rmszclient.Beans.City;
import com.rmsz.rmszclient.Beans.Search;
import com.rmsz.rmszclient.Beans.SearchResult;
import com.rmsz.rmszclient.R;
import com.rmsz.rmszclient.Services.DevicePropertyService;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.utils.SPUtils;
import com.zhy.utils.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import zuo.biao.library.util.Json;

/**
 * Created by WALES7 on 2016/12/9.
 */

public class SearchFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    // Content View Elements
    private static final String TAG = "SearchFragment";
    private TextView mMain_tv_token;
    private Spinner mMain_sp_citys;
    private Button mMain_btn_getToken;
    private Button mMain_btn_getStatus;
    private ListView mMain_list_result;
    private ArrayAdapter<String> arr_adapter;
    private String token = "";
    private List<String> data_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View thisView = inflater.inflate(R.layout.fgmt_main_search, container, false);
//        bindViews();
//        return inflater.inflate(R.layout.fgmt_main_search, container, false);
        mMain_tv_token = (TextView) thisView.findViewById(R.id.main_tv_token);
        mMain_sp_citys = (Spinner) thisView.findViewById(R.id.main_sp_citys);
        mMain_btn_getToken = (Button) thisView.findViewById(R.id.main_btn_getToken);
        mMain_btn_getStatus = (Button) thisView.findViewById(R.id.main_btn_getStatus);
        mMain_list_result = (ListView) thisView.findViewById(R.id.main_list_result);
        initListeners();
        initData();
        return thisView;
    }

    private void initListeners() {
        mMain_btn_getToken.setOnClickListener(this);
        mMain_btn_getStatus.setOnClickListener(this);
    }

    private void initData() {
        token = (String) SPUtils.get(getActivity().getApplicationContext(), BaseInfo.SHARE_KEY_TOKEN, "");
        mMain_tv_token.setText(token);
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

                        //数据
                        data_list = new ArrayList<String>();
                        for (int i = 0; i < citys.getData().size(); i++) {
                            data_list.add(citys.getData().get(i).getName());
                        }

                        //适配器
                        arr_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data_list);
                        //设置样式
                        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //加载适配器
                        mMain_sp_citys.setAdapter(arr_adapter);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == mMain_btn_getToken) {
            ReGetToken.getToken(getActivity());
//            String token = (String) SPUtils.get(getApplicationContext(), BaseInfo.SHARE_KEY_TOKEN, "");
//            mTvToken.setText(token);
        }

        if (v == mMain_btn_getStatus) {
            getStatus();
        }
    }

    public void getStatus() {
        HashMap params = new HashMap();
        params.put("status","-3");
        params.put("keyword", this.mMain_sp_citys.getSelectedItem().toString());
        String url = BaseInfo.genApiUrl("Search/getStatus", token, params);//API_SERVER_URL + API方法名
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
//                        Log.e("respone", response.replace(" ", ""));
                        if (response.replace(" ", "").contains(":200")) {
                            SearchResult result = Json.parseObject(response, SearchResult.class);
                            onGetStatusResponse(result);
                        } else if (response.replace(" ", "").contains(":202") && response.replace(" ", "").contains("path")) {
                            Search result = Json.parseObject(response, Search.class);
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), VerifyActivity.class);
                            String path = result.getData().getPath();
                            intent.putExtra("path", path.replace("http://api.server.site.url" , BaseInfo.API_SERVER_SITE_URL));
                            startActivity(intent);
                        }
                    }
                });
    }

    private void onGetStatusResponse(SearchResult result) {
//        mMain_tv_token.setText(result.getData().toString());

        if(result.getData().size()<1){
            T.showShort(getActivity(),"未查询到任何结果");
            return;
        }
        mMain_list_result.setOnItemClickListener(this);


        String[] from = {"index","list_item_id","list_item_name", "list_item_code","list_item_lastdate"};              //这里是ListView显示内容每一列的列名
        int[] to = {R.id.list_item_index,R.id.list_item_id,R.id.list_item_name, R.id.list_item_code,R.id.list_item_lastdate};   //这里是ListView显示每一列对应的list_item中控件的id
        ArrayList<HashMap<String, String>> list = null;
        HashMap<String, String> map = null;

        //创建ArrayList对象；
        list = new ArrayList<HashMap<String, String>>();
        //将数据存放进ArrayList对象中，数据安排的结构是，ListView的一行数据对应一个HashMap对象，
        //HashMap对象，以列名作为键，以该列的值作为Value，将各列信息添加进map中，然后再把每一列对应
        //的map对象添加到ArrayList中

        for (int i = 0; i < result.getData().size(); i++) {
            map = new HashMap<String, String>();       //为避免产生空指针异常，有几列就创建几个map对象
            map.put("index",(i+1)+"");
            map.put("list_item_id",result.getData().get(i).getAgent_id());
            map.put("list_item_code", result.getData().get(i).getAgent_code());
            map.put("list_item_name", result.getData().get(i).getAgent_name());
            map.put("list_item_lastdate",result.getData().get(i).getConnect_time());
            list.add(map);
        }
        //调用ListActivity的setListAdapter方法，为ListView设置适配器
        mMain_list_result.setAdapter(new SimpleAdapter(getActivity(), list, R.layout.listview_item_search, from, to));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        HashMap<String,String> map = (HashMap<String,String>)mMain_list_result.getItemAtPosition(i);
        String agent_id = map.get("list_item_id");

        Intent intent = new Intent();
        intent.putExtra("agent_id",agent_id);
        intent.setClass(getActivity(),PropertyActivity.class);
        startActivity(intent);

//        Log.e("TAG ----> agent_id",agent_id);
//        T.showLong(getActivity(),"you taped the " + i + "th item");
    }
}
