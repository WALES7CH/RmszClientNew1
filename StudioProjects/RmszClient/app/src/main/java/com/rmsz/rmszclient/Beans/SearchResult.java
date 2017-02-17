package com.rmsz.rmszclient.Beans;

import java.util.List;

/**
 * Created by WALES7 on 2016/12/8.
 */

public class SearchResult {

    /**
     * status : 200
     * message : 查询成功
     * data : [{"monitor_id":"386553836","agent_id":"6237","connect_time":"2016-12-06 13:41:21","idx_file_name":"","file_id":"","file_title":"","template_name":"0118四川新框架","agent_name":"简阳市人民医院(住院部)","unit_id":"2","unit_name":"北京","agent_code":"W3TN8VQ7","agent_ip":"10.16.10.158","agent_monitor_times":"1","start_time":"08:00","end_time":"18:00","wait":"176335","disc":"123519","cs_no":"1.1","status":"red","net_type":"有线网","supplier":"创维","connection_rate":0,"shutdown_total":0,"city":"简阳市"},{"monitor_id":"292740514","agent_id":"6232","connect_time":"2016-07-05 08:38:02","idx_file_name":"","file_id":"","file_title":"","template_name":"0118四川新框架","agent_name":"简阳市人民政府政务服务中心(1L)","unit_id":"2","unit_name":"北京","agent_code":"W3TMX664","agent_ip":"10.16.18.234","agent_monitor_times":"1","start_time":"08:00","end_time":"17:30","wait":"13500134","disc":"13447318","cs_no":"1.1","status":"red","net_type":"有线网","supplier":"创维","connection_rate":0,"shutdown_total":0,"city":"简阳市"}]
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
         * monitor_id : 386553836
         * agent_id : 6237
         * connect_time : 2016-12-06 13:41:21
         * idx_file_name :
         * file_id :
         * file_title :
         * template_name : 0118四川新框架
         * agent_name : 简阳市人民医院(住院部)
         * unit_id : 2
         * unit_name : 北京
         * agent_code : W3TN8VQ7
         * agent_ip : 10.16.10.158
         * agent_monitor_times : 1
         * start_time : 08:00
         * end_time : 18:00
         * wait : 176335
         * disc : 123519
         * cs_no : 1.1
         * status : red
         * net_type : 有线网
         * supplier : 创维
         * connection_rate : 0
         * shutdown_total : 0
         * city : 简阳市
         */

        private String monitor_id;
        private String agent_id;
        private String connect_time;
        private String idx_file_name;
        private String file_id;
        private String file_title;
        private String template_name;
        private String agent_name;
        private String unit_id;
        private String unit_name;
        private String agent_code;
        private String agent_ip;
        private String agent_monitor_times;
        private String start_time;
        private String end_time;
        private String wait;
        private String disc;
        private String cs_no;
        private String status;
        private String net_type;
        private String supplier;
        private int connection_rate;
        private int shutdown_total;
        private String city;

        public String getMonitor_id() {
            return monitor_id;
        }

        public void setMonitor_id(String monitor_id) {
            this.monitor_id = monitor_id;
        }

        public String getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(String agent_id) {
            this.agent_id = agent_id;
        }

        public String getConnect_time() {
            return connect_time;
        }

        public void setConnect_time(String connect_time) {
            this.connect_time = connect_time;
        }

        public String getIdx_file_name() {
            return idx_file_name;
        }

        public void setIdx_file_name(String idx_file_name) {
            this.idx_file_name = idx_file_name;
        }

        public String getFile_id() {
            return file_id;
        }

        public void setFile_id(String file_id) {
            this.file_id = file_id;
        }

        public String getFile_title() {
            return file_title;
        }

        public void setFile_title(String file_title) {
            this.file_title = file_title;
        }

        public String getTemplate_name() {
            return template_name;
        }

        public void setTemplate_name(String template_name) {
            this.template_name = template_name;
        }

        public String getAgent_name() {
            return agent_name;
        }

        public void setAgent_name(String agent_name) {
            this.agent_name = agent_name;
        }

        public String getUnit_id() {
            return unit_id;
        }

        public void setUnit_id(String unit_id) {
            this.unit_id = unit_id;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getAgent_code() {
            return agent_code;
        }

        public void setAgent_code(String agent_code) {
            this.agent_code = agent_code;
        }

        public String getAgent_ip() {
            return agent_ip;
        }

        public void setAgent_ip(String agent_ip) {
            this.agent_ip = agent_ip;
        }

        public String getAgent_monitor_times() {
            return agent_monitor_times;
        }

        public void setAgent_monitor_times(String agent_monitor_times) {
            this.agent_monitor_times = agent_monitor_times;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getWait() {
            return wait;
        }

        public void setWait(String wait) {
            this.wait = wait;
        }

        public String getDisc() {
            return disc;
        }

        public void setDisc(String disc) {
            this.disc = disc;
        }

        public String getCs_no() {
            return cs_no;
        }

        public void setCs_no(String cs_no) {
            this.cs_no = cs_no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNet_type() {
            return net_type;
        }

        public void setNet_type(String net_type) {
            this.net_type = net_type;
        }

        public String getSupplier() {
            return supplier;
        }

        public void setSupplier(String supplier) {
            this.supplier = supplier;
        }

        public int getConnection_rate() {
            return connection_rate;
        }

        public void setConnection_rate(int connection_rate) {
            this.connection_rate = connection_rate;
        }

        public int getShutdown_total() {
            return shutdown_total;
        }

        public void setShutdown_total(int shutdown_total) {
            this.shutdown_total = shutdown_total;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
