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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rmsz.rmszclient.Activitys.PropertyActivity;
import com.rmsz.rmszclient.Activitys.VerifyActivity;
import com.rmsz.rmszclient.Base.BaseInfo;
import com.rmsz.rmszclient.Base.ReGetToken;
import com.rmsz.rmszclient.Beans.City;
import com.rmsz.rmszclient.Beans.Search;
import com.rmsz.rmszclient.Beans.SearchResult;
import com.rmsz.rmszclient.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.utils.SPUtils;
import com.zhy.utils.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import zuo.biao.library.util.Json;

/**
 * Created by WALES7 on 2016/12/9.
 */

public class ManageFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private String TAG = "ManageFragment";
    private EditText mManage_et_keyword;
    private Button mManage_btn_getStatus;
    private ListView mManage_list_result;
    private ArrayAdapter<String> arr_adapter;
    private String token = "";
    private List<String> data_list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View thisView = inflater.inflate(R.layout.fgmt_main_manage, container, false);


        mManage_et_keyword = (EditText) thisView.findViewById(R.id.manage_et_keyword);
        mManage_btn_getStatus = (Button) thisView.findViewById(R.id.manage_btn_getStatus);
        mManage_list_result = (ListView) thisView.findViewById(R.id.manage_list_result);
        token = (String) SPUtils.get(getActivity().getApplicationContext(), BaseInfo.SHARE_KEY_TOKEN, "");
        mManage_btn_getStatus.setOnClickListener(this);
        return  thisView;
    }

    @Override
    public void onClick(View v) {
        if (v == mManage_btn_getStatus) {
            getStatus();
        }
    }

    public void getStatus() {
        HashMap params = new HashMap();
        params.put("status","");
        params.put("keyword", mManage_et_keyword.getText().toString().trim().toLowerCase());
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
        if(result.getData().size()<1){
            T.showShort(getActivity(),"未查询到任何结果");
            return;
        }
        mManage_list_result.setOnItemClickListener(this);


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
        mManage_list_result.setAdapter(new SimpleAdapter(getActivity(), list, R.layout.listview_item_search, from, to));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        HashMap<String,String> map = (HashMap<String,String>)mManage_list_result.getItemAtPosition(i);
        String agent_id = map.get("list_item_id");

        Intent intent = new Intent();
        intent.putExtra("agent_id",agent_id);
        intent.setClass(getActivity(),PropertyActivity.class);
        startActivity(intent);
    }
}
