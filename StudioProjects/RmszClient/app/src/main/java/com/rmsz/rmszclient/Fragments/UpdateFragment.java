package com.rmsz.rmszclient.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class UpdateFragment extends Fragment {
    private String update_url = "";
    private LastUpdateBean updateData;
    private ListView mProUpdateList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View thisView  = inflater.inflate(R.layout.fgmt_pro_update, container, false);
        mProUpdateList = (ListView)thisView.findViewById(R.id.pro_update_list);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        Map params = new HashMap();
        Log.e("agent_id", bundle.getString("agent_id"));
        if(bundle!=null){
            params.put("agent_id",bundle.getString("agent_id"));
        }
        update_url = BaseInfo.genApiUrl("Search/getLastUpdate", SPUtils.get(getActivity().getApplicationContext(),BaseInfo.SHARE_KEY_TOKEN,"").toString(),params);
        initDatas();
        return thisView;

    }

    private void  initDatas(){
        Log.e("url" , update_url);
        OkHttpUtils.get().url(update_url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                updateData = Json.parseObject(response,LastUpdateBean.class);
                if(updateData.getStatus() == ApiReturnCodeInfo.SEARCH_FAIL){
                    T.showShort(getActivity(),updateData.getMessage());
                }
                String[] from = {"pro_update_type","pro_update_file_name","pro_update_status","pro_update_finish_time"};              //这里是ListView显示内容每一列的列名
                int[] to = {R.id.pro_update_type,R.id.pro_update_file_name,R.id.pro_update_status,R.id.pro_update_finish_time};   //这里是ListView显示每一列对应的list_item中控件的id
                ArrayList<HashMap<String, String>> list = null;
                HashMap<String, String> map = null;

                //创建ArrayList对象；
                list = new ArrayList<HashMap<String, String>>();
                //将数据存放进ArrayList对象中，数据安排的结构是，ListView的一行数据对应一个HashMap对象，
                //HashMap对象，以列名作为键，以该列的值作为Value，将各列信息添加进map中，然后再把每一列对应
                //的map对象添加到ArrayList中

                for (int i = 0; i < updateData.getData().size(); i++) {
                    map = new HashMap<String, String>();       //为避免产生空指针异常，有几列就创建几个map对象
                    map.put("pro_update_type",updateData.getData().get(i).getFile_title());
                    map.put("pro_update_file_name", updateData.getData().get(i).getIdx_file_name());
                    map.put("pro_update_status", (updateData.getData().get(i).getFinish_flag().equals("1") ?"已完成":"未完成") );
                    map.put("pro_update_finish_time", updateData.getData().get(i).getFinish_date());
                    list.add(map);
                }
                //调用ListActivity的setListAdapter方法，为ListView设置适配器
                mProUpdateList.setAdapter(new SimpleAdapter(getActivity(), list, R.layout.pro_update_list_item, from, to));
            }
        });
    }
}


class LastUpdateBean{

    /**
     * status : 200
     * message : 查询成功
     * data : [{"agent_name":"四川终端JZD-医院-简阳市-简阳市人民医院(住院部)-创维","agent_code":"W3TN8VQ7","file_title":"rmrb20161212a","idx_file_name":"rmrb.swf","file_kind_id":"3","file_kind_name":"人民日报","file_type_id":"1","user_name":"2center","agent_file_id":"12513701","agent_id":"6237","file_id":"42622","publish_user":"189","publish_date":"2016-12-12 06:20:21.0","status":"1","template_column_id":"10162","finish_flag":"0","finish_date":"","publish_bat_no":"b7621b4109404e5c97747c2760ee9432","wait":"6017032.0","receive_time":"2016-12-12 06:21:55.0","agent_time":"","agent_ip":"10.16.10.158"},{"agent_name":"四川终端JZD-医院-简阳市-简阳市人民医院(住院部)-创维","agent_code":"W3TN8VQ7","file_title":"1212证券时报","idx_file_name":"zqsb.swf","file_kind_id":"5","file_kind_name":"本地栏目","file_type_id":"1","user_name":"四川-江韵","agent_file_id":"12531151","agent_id":"6237","file_id":"42631","publish_user":"328","publish_date":"2016-12-12 12:11:46.0","status":"1","template_column_id":"10163","finish_flag":"0","finish_date":"","publish_bat_no":"b5b2f2d200634d438fee6188b9f40412","wait":"6017032.0","receive_time":"2016-12-12 12:11:56.0","agent_time":"","agent_ip":"10.16.10.158"},{"agent_name":"四川终端JZD-医院-简阳市-简阳市人民医院(住院部)-创维","agent_code":"W3TN8VQ7","file_title":"20161212四川数字联播","idx_file_name":"2016.12.12数字联播.avi","file_kind_id":"6","file_kind_name":"本地广告","file_type_id":"2","user_name":"四川-江韵","agent_file_id":"12532997","agent_id":"6237","file_id":"42645","publish_user":"328","publish_date":"2016-12-12 12:48:35.0","status":"1","template_column_id":"0","finish_flag":"0","finish_date":"","publish_bat_no":"c7b9629bc54c4945b326199161e1ab12","wait":"6017032.0","receive_time":"2016-12-12 12:48:54.0","agent_time":"","agent_ip":"10.16.10.158"},{"agent_name":"四川终端JZD-医院-简阳市-简阳市人民医院(住院部)-创维","agent_code":"W3TN8VQ7","file_title":"京之道1212","idx_file_name":"20161209115216722498.txt","file_kind_id":"7","file_kind_name":"广告排片","file_type_id":"6","user_name":"四川-江韵","agent_file_id":"12536018","agent_id":"6237","file_id":"42581","publish_user":"328","publish_date":"2016-12-12 13:42:54.0","status":"1","template_column_id":"0","finish_flag":"0","finish_date":"","publish_bat_no":"2686113035c5421981b9bca4f3d3ec09","wait":"6017032.0","receive_time":"2016-12-12 13:43:50.0","agent_time":"","agent_ip":"10.16.10.158"},{"agent_name":"四川终端JZD-医院-简阳市-简阳市人民医院(住院部)-创维","agent_code":"W3TN8VQ7","file_title":"1212京之道led","idx_file_name":"20161212094108571874.txt","file_kind_id":"8","file_kind_name":"LED","file_type_id":"4","user_name":"四川-江韵","agent_file_id":"12519592","agent_id":"6237","file_id":"42628","publish_user":"328","publish_date":"2016-12-12 10:14:59.0","status":"1","template_column_id":"0","finish_flag":"0","finish_date":"","publish_bat_no":"3c9654ee059c41aab9050a7c65a2cabd","wait":"6017032.0","receive_time":"2016-12-12 10:15:02.0","agent_time":"","agent_ip":"10.16.10.158"}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

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

    public static class DataBean {
        /**
         * agent_name : 四川终端JZD-医院-简阳市-简阳市人民医院(住院部)-创维
         * agent_code : W3TN8VQ7
         * file_title : rmrb20161212a
         * idx_file_name : rmrb.swf
         * file_kind_id : 3
         * file_kind_name : 人民日报
         * file_type_id : 1
         * user_name : 2center
         * agent_file_id : 12513701
         * agent_id : 6237
         * file_id : 42622
         * publish_user : 189
         * publish_date : 2016-12-12 06:20:21.0
         * status : 1
         * template_column_id : 10162
         * finish_flag : 0
         * finish_date :
         * publish_bat_no : b7621b4109404e5c97747c2760ee9432
         * wait : 6017032.0
         * receive_time : 2016-12-12 06:21:55.0
         * agent_time :
         * agent_ip : 10.16.10.158
         */

        private String agent_name;
        private String agent_code;
        private String file_title;
        private String idx_file_name;
        private String file_kind_id;
        private String file_kind_name;
        private String file_type_id;
        private String user_name;
        private String agent_file_id;
        private String agent_id;
        private String file_id;
        private String publish_user;
        private String publish_date;
        private String status;
        private String template_column_id;
        private String finish_flag;
        private String finish_date;
        private String publish_bat_no;
        private String wait;
        private String receive_time;
        private String agent_time;
        private String agent_ip;

        public String getAgent_name() {
            return agent_name;
        }

        public void setAgent_name(String agent_name) {
            this.agent_name = agent_name;
        }

        public String getAgent_code() {
            return agent_code;
        }

        public void setAgent_code(String agent_code) {
            this.agent_code = agent_code;
        }

        public String getFile_title() {
            return file_title;
        }

        public void setFile_title(String file_title) {
            this.file_title = file_title;
        }

        public String getIdx_file_name() {
            return idx_file_name;
        }

        public void setIdx_file_name(String idx_file_name) {
            this.idx_file_name = idx_file_name;
        }

        public String getFile_kind_id() {
            return file_kind_id;
        }

        public void setFile_kind_id(String file_kind_id) {
            this.file_kind_id = file_kind_id;
        }

        public String getFile_kind_name() {
            return file_kind_name;
        }

        public void setFile_kind_name(String file_kind_name) {
            this.file_kind_name = file_kind_name;
        }

        public String getFile_type_id() {
            return file_type_id;
        }

        public void setFile_type_id(String file_type_id) {
            this.file_type_id = file_type_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getAgent_file_id() {
            return agent_file_id;
        }

        public void setAgent_file_id(String agent_file_id) {
            this.agent_file_id = agent_file_id;
        }

        public String getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(String agent_id) {
            this.agent_id = agent_id;
        }

        public String getFile_id() {
            return file_id;
        }

        public void setFile_id(String file_id) {
            this.file_id = file_id;
        }

        public String getPublish_user() {
            return publish_user;
        }

        public void setPublish_user(String publish_user) {
            this.publish_user = publish_user;
        }

        public String getPublish_date() {
            return publish_date;
        }

        public void setPublish_date(String publish_date) {
            this.publish_date = publish_date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTemplate_column_id() {
            return template_column_id;
        }

        public void setTemplate_column_id(String template_column_id) {
            this.template_column_id = template_column_id;
        }

        public String getFinish_flag() {
            return finish_flag;
        }

        public void setFinish_flag(String finish_flag) {
            this.finish_flag = finish_flag;
        }

        public String getFinish_date() {
            return finish_date;
        }

        public void setFinish_date(String finish_date) {
            this.finish_date = finish_date;
        }

        public String getPublish_bat_no() {
            return publish_bat_no;
        }

        public void setPublish_bat_no(String publish_bat_no) {
            this.publish_bat_no = publish_bat_no;
        }

        public String getWait() {
            return wait;
        }

        public void setWait(String wait) {
            this.wait = wait;
        }

        public String getReceive_time() {
            return receive_time;
        }

        public void setReceive_time(String receive_time) {
            this.receive_time = receive_time;
        }

        public String getAgent_time() {
            return agent_time;
        }

        public void setAgent_time(String agent_time) {
            this.agent_time = agent_time;
        }

        public String getAgent_ip() {
            return agent_ip;
        }

        public void setAgent_ip(String agent_ip) {
            this.agent_ip = agent_ip;
        }
    }
}
