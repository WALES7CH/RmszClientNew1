package com.rmsz.rmszclient.Base;

/**
 * 请求API时返回的状态信息对照表
 * Created by WALES7 on 2016/12/8.
 */

public class ApiReturnCodeInfo {

    /**
     * 状态：请求成功
     * 涉及API方法：
     *      Login/appLogin
     *      Login/reGenToekn
     *      Search/getCitys
     *      Search/getStatus
     *      Search/getLoginCookie
     *      Search/weblogin
     *      Search/getLastUpdate
     */

    public final static int API_REQUEST_SUCCESS = 200;
    /**
     *状态：验证码错误
     *涉及API方法：Search/weblogin
     */
    public final static int INVAILD_VERIFYCODE = 210;
    /**
     *状态：weblogin登陆失败
     *涉及API方法：Search/weblogin
     */
    public final static int WEB_LOGIN_FAIL = 10004;
    /**
     *状态：查询失败
     *涉及API方法：Search/weblogin
     */
    public final static int SEARCH_FAIL = 10004;
    /**
     *状态：登陆失败
     *涉及API方法：Search/weblogin
     */
    public final static int USER_LOGIN_FAIL = 400;
    /**
     *状态：用户不存在
     *涉及API方法：Search/weblogin
     */
    public final static int USER_NOT_EXISTS = 404;
    /**
     *状态：用户密码错误
     *涉及API方法：Search/weblogin
     */
    public final static int USER_PASSWORD_INCONRECCT = 403;
    /**
     *状态：用户被锁定
     *涉及API方法：Search/weblogin
     */
    public final static int USER_LOCKED = 402;
    /**
     *状态：需要验证码
     *涉及API方法：Search/weblogin
     */
    public final static int VERIFY_NEED = 202;
}
