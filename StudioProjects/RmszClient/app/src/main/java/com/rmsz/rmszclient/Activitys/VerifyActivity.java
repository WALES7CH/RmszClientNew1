package com.rmsz.rmszclient.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rmsz.rmszclient.Base.ApiReturnCodeInfo;
import com.rmsz.rmszclient.Base.BaseInfo;
import com.rmsz.rmszclient.Beans.Search;
import com.rmsz.rmszclient.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.utils.L;
import com.zhy.utils.SPUtils;
import com.zhy.utils.T;

import java.util.HashMap;

import okhttp3.Call;
import zuo.biao.library.util.Json;

public class VerifyActivity extends Activity implements View.OnClickListener {
    // Content View Elements

    private LinearLayout mActivity_verify;
    private ImageView mVerify_img_view;
    private TextView mVerify_tv_tips;
    private EditText mVerify_et_input;
    private Button mVerify_btn_weblogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        bindViews();

        //取得启动该Activity的Intent对象
        Intent intent = getIntent();
        /*取出Intent中附加的数据*/
        String path = intent.getStringExtra("path");
        Log.e("Verify-----" , path);
        initImageViewBitmap(path);
        initListeners();
    }


    // End Of Content View Elements

    private void bindViews() {

        mActivity_verify = (LinearLayout) findViewById(R.id.activity_verify);
        mVerify_img_view = (ImageView) findViewById(R.id.verify_img_view);
        mVerify_tv_tips = (TextView) findViewById(R.id.verify_tv_tips);
        mVerify_et_input = (EditText) findViewById(R.id.verify_et_input);
        mVerify_btn_weblogin = (Button) findViewById(R.id.verify_btn_weblogin);
    }

    private void initListeners() {
        mVerify_btn_weblogin.setOnClickListener(this);
    }

    private void initImageViewBitmap(String path) {
        OkHttpUtils
                .get()//
                .url(path)//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mVerify_tv_tips.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        Log.e("TAG", "onResponse：complete");
                        mVerify_img_view.setImageBitmap(bitmap);
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if (view == mVerify_btn_weblogin) {
            String token = SPUtils.get(this, BaseInfo.SHARE_KEY_TOKEN, "").toString();
            String check = mVerify_et_input.getText().toString().trim();
            HashMap params = new HashMap();
            params.put("check",check);
            String url = BaseInfo.genApiUrl("Search/weblogin", token, params);
            OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    L.e("verify -error",e.getMessage());
                }

                @Override
                public void onResponse(String response, int id) {
                    L.e("verify -success",response);
                    if(response.contains(":" + ApiReturnCodeInfo.API_REQUEST_SUCCESS + "")){
                        T.showShort(VerifyActivity.this,"验证成功，请点击查询按钮进行操作");
                        finish();
                    }else{//验证码错误或者登录失败
                        T.showShort(VerifyActivity.this,"验证码错误");
                        mVerify_et_input.setText("");//置空
                        refreshVerifyImage();
                    }

                }
            });
        }
    }

    //刷新验证码图片
    private void refreshVerifyImage(){
        String url = BaseInfo.genApiUrl("Search/getLoginCookie",SPUtils.get(this,BaseInfo.SHARE_KEY_TOKEN,"").toString(),null);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Search result = Json.parseObject(response,Search.class);
                initImageViewBitmap(result.getData().getPath().replace("http://api.server.site.url" , BaseInfo.API_SERVER_SITE_URL));
            }
        });

    }
}
