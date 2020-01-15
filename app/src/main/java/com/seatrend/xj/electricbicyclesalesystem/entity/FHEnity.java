package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2019/11/1 15:35
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class FHEnity extends BaseEntity {
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public static class Data {

        private int pageNo;
        private int pageSize;
        private int count;
        private ArrayList<FHList> list;
        private String orderDirection;

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public void setList(ArrayList<FHList> list) {
            this.list = list;
        }

        public ArrayList<FHList> getList() {
            return list;
        }

        public void setOrderDirection(String orderDirection) {
            this.orderDirection = orderDirection;
        }

        public String getOrderDirection() {
            return orderDirection;
        }

        public static class FHList {

            private String lsh;
            private String xh;
            private String syr;
            private String zcbm;
            private String cph;
            private String cybm;
            private String ywlx;
            private String djbm;
            private String sjsj;
            private String lczt;
            private String shbj;
            private String fhbj; //复核标记 9-无需复核，0-待复核，1-复核通过，2-复核未通过
            private String shbz;
            private String fhbz;
            private String shsj;
            private long fhsj;
            private String btgyy;
            private String ywry;
            private String fhr;
            private String shr;
            private String tbr;
            private long slsj;

            public long getSlsj() {
                return slsj;
            }

            public void setSlsj(long slsj) {
                this.slsj = slsj;
            }

            public void setLsh(String lsh) {
                this.lsh = lsh;
            }

            public String getLsh() {
                return lsh;
            }

            public void setXh(String xh) {
                this.xh = xh;
            }

            public String getXh() {
                return xh;
            }

            public void setSyr(String syr) {
                this.syr = syr;
            }

            public String getSyr() {
                return syr;
            }

            public void setZcbm(String zcbm) {
                this.zcbm = zcbm;
            }

            public String getZcbm() {
                return zcbm;
            }

            public void setCph(String cph) {
                this.cph = cph;
            }

            public String getCph() {
                return cph;
            }

            public void setCybm(String cybm) {
                this.cybm = cybm;
            }

            public String getCybm() {
                return cybm;
            }

            public void setYwlx(String ywlx) {
                this.ywlx = ywlx;
            }

            public String getYwlx() {
                return ywlx;
            }

            public void setDjbm(String djbm) {
                this.djbm = djbm;
            }

            public String getDjbm() {
                return djbm;
            }

            public void setSjsj(String sjsj) {
                this.sjsj = sjsj;
            }

            public String getSjsj() {
                return sjsj;
            }

            public void setLczt(String lczt) {
                this.lczt = lczt;
            }

            public String getLczt() {
                return lczt;
            }

            public void setShbj(String shbj) {
                this.shbj = shbj;
            }

            public String getShbj() {
                return shbj;
            }

            public void setFhbj(String fhbj) {
                this.fhbj = fhbj;
            }

            public String getFhbj() {
                return fhbj;
            }

            public void setShbz(String shbz) {
                this.shbz = shbz;
            }

            public String getShbz() {
                return shbz;
            }

            public void setFhbz(String fhbz) {
                this.fhbz = fhbz;
            }

            public String getFhbz() {
                return fhbz;
            }

            public void setShsj(String shsj) {
                this.shsj = shsj;
            }

            public String getShsj() {
                return shsj;
            }

            public void setFhsj(long fhsj) {
                this.fhsj = fhsj;
            }

            public long getFhsj() {
                return fhsj;
            }

            public void setBtgyy(String btgyy) {
                this.btgyy = btgyy;
            }

            public String getBtgyy() {
                return btgyy;
            }

            public void setYwry(String ywry) {
                this.ywry = ywry;
            }

            public String getYwry() {
                return ywry;
            }

            public void setFhr(String fhr) {
                this.fhr = fhr;
            }

            public String getFhr() {
                return fhr;
            }

            public void setShr(String shr) {
                this.shr = shr;
            }

            public String getShr() {
                return shr;
            }

            public void setTbr(String tbr) {
                this.tbr = tbr;
            }

            public String getTbr() {
                return tbr;
            }
        }
    }
}
