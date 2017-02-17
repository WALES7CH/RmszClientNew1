package com.rmsz.rmszclient.Biz;

import com.google.gson.Gson;
import com.rmsz.rmszclient.Base.BaseInfo;
import com.rmsz.rmszclient.Beans.LoginResult;
import com.rmsz.rmszclient.Beans.User;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;
import zuo.biao.library.util.Json;

/**
 * Created by WALES7 on 2016/12/13.
 */

public class UserBiz implements IUserBiz {
    @Override
    public void login(final String username, final String password, final OnLoginListener loginListener) {
        //模拟子线程耗时操作
        new Thread() {
            @Override
            public void run() {
                String url = BaseInfo.genApiUrl("Login/appLogin", "", null);//API_SERVER_URL + API方法名
                OkHttpUtils
                        .postString()
                        .url(url)
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .content(new Gson().toJson(new User(username, password)))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                loginListener.loginFailed(e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LoginResult result = Json.parseObject(response, LoginResult.class);

                                switch (result.getStatus()) {
                                    case 200:
                                        String token_str = result.getData().getToken();
                                        User user = new User();
                                        user.setToken(token_str);
                                        user.setUsername(username);
                                        user.setPassword(password);
                                        loginListener.loginSuccess(user);
                                        break;
                                    case 404:
                                        loginListener.loginFailed(result.getMessage());
                                        break;
                                    case 403:
                                        loginListener.loginFailed(result.getMessage());
                                        break;
                                    default:
                                        loginListener.loginFailed("未知错误");
                                        break;
                                }
                            }
                        });
            }
        }.start();
    }
}
