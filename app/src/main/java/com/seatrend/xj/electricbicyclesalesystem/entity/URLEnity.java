package com.seatrend.xj.electricbicyclesalesystem.entity;


public class URLEnity extends BaseEntity {
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Data {

        private String addtime;
        private String appversion;
        private String downloadurl;
        private String id;
        private String updatemessage;

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAppversion(String appversion) {
            this.appversion = appversion;
        }

        public String getAppversion() {
            return appversion;
        }

        public void setDownloadurl(String downloadurl) {
            this.downloadurl = downloadurl;
        }

        public String getDownloadurl() {
            return downloadurl;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setUpdatemessage(String updatemessage) {
            this.updatemessage = updatemessage;
        }

        public String getUpdatemessage() {
            return updatemessage;
        }

    }
}
