package com.rmsz.rmszclient.Services;

import android.content.Context;

import com.rmsz.rmszclient.Base.BaseInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import zuo.biao.library.util.Json;

/**
 * Created by WALES7 on 2016/12/12.
 */

public class DevicePropertyService {
    //加载监听接口
    public static interface LoadingListener {
        public void onFinishedLoading(boolean success);
    }
    private LoadingListener mLoadingListener;
    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }
    private Object[] result;



    public Object[] getDatas(Context context, HashMap params){
        return null;
    }
}


