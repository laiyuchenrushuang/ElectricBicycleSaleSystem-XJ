package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.List;

/**
 * Created by ly on 2019/11/25 17:05
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class GlobalEnity extends BaseEntity {
    private List<Data> data;

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data {

        private int xh;
        private String csmc;
        private String csz;
        private String xsxs;
        private String gjz;
        private String dmlb;
        private long gxsj;

        public void setXh(int xh) {
            this.xh = xh;
        }

        public int getXh() {
            return xh;
        }

        public void setCsmc(String csmc) {
            this.csmc = csmc;
        }

        public String getCsmc() {
            return csmc;
        }

        public void setCsz(String csz) {
            this.csz = csz;
        }

        public String getCsz() {
            return csz;
        }

        public void setXsxs(String xsxs) {
            this.xsxs = xsxs;
        }

        public String getXsxs() {
            return xsxs;
        }

        public void setGjz(String gjz) {
            this.gjz = gjz;
        }

        public String getGjz() {
            return gjz;
        }

        public void setDmlb(String dmlb) {
            this.dmlb = dmlb;
        }

        public String getDmlb() {
            return dmlb;
        }

        public void setGxsj(long gxsj) {
            this.gxsj = gxsj;
        }

        public long getGxsj() {
            return gxsj;
        }
    }
}
