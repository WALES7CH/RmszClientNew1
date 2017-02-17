package com.rmsz.rmszclient.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rmsz.rmszclient.Base.ApiReturnCodeInfo;
import com.rmsz.rmszclient.Base.BaseInfo;
import com.rmsz.rmszclient.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.utils.SPUtils;
import com.zhy.utils.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import zuo.biao.library.util.Json;
import zuo.biao.library.util.Log;

/**
 * Created by WALES7 on 2016/12/26.
 */

public class PropertyFragment extends Fragment {
    private DevicePropertyBean property;
    private String pro_url;
    private ListView mProPropertyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View thisView = inflater.inflate(R.layout.fgmt_pro_property, container, false);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        mProPropertyList = (ListView) thisView.findViewById(R.id.pro_property_list);
        Map params = new HashMap();
        Log.e("agent_id", bundle.getString("agent_id"));
        if (bundle != null) {

            params.put("agent_id", bundle.getString("agent_id"));
        }

        pro_url = BaseInfo.genApiUrl("Search/getDeviceProperty", SPUtils.get(getActivity().getApplicationContext(), BaseInfo.SHARE_KEY_TOKEN, "").toString(), params);
        initDatas();

        return thisView;

    }

    private void initDatas() {
        Log.e("url", pro_url);
        OkHttpUtils.get().url(pro_url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
//                Log.e("pro_list_main",response);
                property = Json.parseObject(response, DevicePropertyBean.class);
                if (property.getStatus() == ApiReturnCodeInfo.SEARCH_FAIL) {
                    T.showShort(getActivity(), property.getMessage());
                }
                String[] from = {"pro_key", "pro_value"};              //这里是ListView显示内容每一列的列名
                int[] to = {R.id.pro_list_key, R.id.pro_list_value};   //这里是ListView显示每一列对应的list_item中控件的id
                ArrayList<HashMap<String, String>> list = null;
                HashMap<String, String> map = null;

                //创建ArrayList对象；
                list = new ArrayList<HashMap<String, String>>();
                //将数据存放进ArrayList对象中，数据安排的结构是，ListView的一行数据对应一个HashMap对象，
                //HashMap对象，以列名作为键，以该列的值作为Value，将各列信息添加进map中，然后再把每一列对应
                //的map对象添加到ArrayList中

                for (int i = 0; i < property.getData().size(); i++) {
                    map = new HashMap<String, String>();       //为避免产生空指针异常，有几列就创建几个map对象
                    map.put("pro_key", property.getData().get(i).getProperty_name());
                    map.put("pro_value", property.getData().get(i).getProperty_value());
                    list.add(map);
                }
                //调用ListActivity的setListAdapter方法，为ListView设置适配器
                mProPropertyList.setAdapter(new SimpleAdapter(getActivity(), list, R.layout.pro_list_item, from, to));
                mProPropertyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, String> map = (HashMap<String, String>) mProPropertyList.getItemAtPosition(position);
//                        Iterator<Map.Entry<String,String>> it = map.entrySet().iterator();
//                        while (it.hasNext()){
//                            Map.Entry<String, String> entry = it.next();
//                            Log.e("TAG========>", "-->" +"key= " + entry.getKey() + " and value= " + entry.getValue()+ "<---");
//                        }
                        String proK = map.get("pro_key").trim();
                        if (proK.equals("业主联系电话")) {
                            Log.e("TAG------------------->", map.get("pro_value"));
                            String phoneNumber =
                                    map.get("pro_value").trim();
                            //意图：想干什么事
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_CALL);
                            //url:统一资源定位符
                            //uri:统一资源标示符（更广）
                            intent.setData(Uri.parse("tel:" + phoneNumber));
                            //开启系统拨号器
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}

class DevicePropertyBean {

    /**
     * status : 200
     * message : 查询成功
     * data : [{"property_value":"RMSZ-CW-001-151100600","property_name":"唯一编码"},{"property_value":"住院楼大厅","property_name":"摆放位置"},{"property_value":"1","property_name":"运行状态"},{"property_value":"1","property_name":"终端序列号"},{"property_value":"1","property_name":"终端ID标签"},{"property_value":"3g卡号","property_name":"上网账号"},{"property_value":"有线网","property_name":"联网形式"},{"property_value":"2016-06-15","property_name":"安装日期"},{"property_value":"1","property_name":"SIM卡号"},{"property_value":"1","property_name":"安装人"},{"property_value":"13882913644","property_name":"业主联系电话"},{"property_value":"黄晓川","property_name":"业主联系人"},{"property_value":"42寸落地式终端","property_name":"终端机型"},{"property_value":"创维","property_name":"生产厂商"},{"property_value":"简阳市人民医院(住院部)","property_name":"安装场所"},{"property_value":"是","property_name":"周末是否开关机"},{"property_value":"简阳市","property_name":"行政区划"},{"property_value":"医院","property_name":"渠道"},{"property_value":"四川省资阳市简阳市医院路180号","property_name":"详细地址"}]
     */

    private int status;
    private String message;
    private List data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {
        /**
         * property_value : RMSZ-CW-001-151100600
         * property_name : 唯一编码
         */

        private String property_value;
        private String property_name;

        public String getProperty_value() {
            return property_value;
        }

        public void setProperty_value(String property_value) {
            this.property_value = property_value;
        }

        public String getProperty_name() {
            return property_name;
        }

        public void setProperty_name(String property_name) {
            this.property_name = property_name;
        }
    }
}
