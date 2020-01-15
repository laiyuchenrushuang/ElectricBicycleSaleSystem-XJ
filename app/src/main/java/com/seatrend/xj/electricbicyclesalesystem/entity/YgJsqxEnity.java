package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.List;

/**
 * Created by ly on 2019/11/5 11:46
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class YgJsqxEnity extends BaseEntity {

    private List<Data> data;
    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    public class Data {

        private int xh;
        private String zt;
        private String jsdh;
        private String jsmc;
        private String jssx;
        private String ssxt;
        private String jslx;
        private String bz;
        private long cjsj;
        private String listPro;
        public void setXh(int xh) {
            this.xh = xh;
        }
        public int getXh() {
            return xh;
        }

        public void setZt(String zt) {
            this.zt = zt;
        }
        public String getZt() {
            return zt;
        }

        public void setJsdh(String jsdh) {
            this.jsdh = jsdh;
        }
        public String getJsdh() {
            return jsdh;
        }

        public void setJsmc(String jsmc) {
            this.jsmc = jsmc;
        }
        public String getJsmc() {
            return jsmc;
        }

        public void setJssx(String jssx) {
            this.jssx = jssx;
        }
        public String getJssx() {
            return jssx;
        }

        public void setSsxt(String ssxt) {
            this.ssxt = ssxt;
        }
        public String getSsxt() {
            return ssxt;
        }

        public void setJslx(String jslx) {
            this.jslx = jslx;
        }
        public String getJslx() {
            return jslx;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }
        public String getBz() {
            return bz;
        }

        public void setCjsj(long cjsj) {
            this.cjsj = cjsj;
        }
        public long getCjsj() {
            return cjsj;
        }

        public void setListPro(String listPro) {
            this.listPro = listPro;
        }
        public String getListPro() {
            return listPro;
        }

    }
}
