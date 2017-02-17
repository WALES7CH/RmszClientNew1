package com.rmsz.rmszclient.Beans;

/**
 * Created by WALES7 on 2016/12/8.
 */

public class Search {


    /**
     * status : 202
     * message : 请输入验证码
     * data : {"path":"http://192.168.2.20/wish/App/Modules/API/Tpl/temp/verify.png"}
     */

    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * path : http://192.168.2.20/wish/App/Modules/API/Tpl/temp/verify.png
         */

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
