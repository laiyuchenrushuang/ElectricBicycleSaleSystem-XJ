package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.List;

/**
 * Created by seatrend on 2019/1/8.
 */

public class WarningMessageEntity extends BaseEntity {
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Data {
        private int pageNo;
        private int pageSize;
        private int count;
        private List<WList> list;
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

        public void setList(List<WList> list) {
            this.list = list;
        }

        public List<WList> getList() {
            return list;
        }

        public void setOrderDirection(String orderDirection) {
            this.orderDirection = orderDirection;
        }

        public String getOrderDirection() {
            return orderDirection;
        }

        public class WList {

            private String lsh;
            private String xh;
            private String cllx;
            private String hphm;
            private String yjlx;
            private String zcbm;
            private String yjyy;
            private long yjsj;
            private String qs;
            private String qsr;
            private String qssj;

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

            public void setCllx(String cllx) {
                this.cllx = cllx;
            }

            public String getCllx() {
                return cllx;
            }

            public void setHphm(String hphm) {
                this.hphm = hphm;
            }

            public String getHphm() {
                return hphm;
            }

            public void setYjlx(String yjlx) {
                this.yjlx = yjlx;
            }

            public String getYjlx() {
                return yjlx;
            }

            public void setZcbm(String zcbm) {
                this.zcbm = zcbm;
            }

            public String getZcbm() {
                return zcbm;
            }

            public void setYjyy(String yjyy) {
                this.yjyy = yjyy;
            }

            public String getYjyy() {
                return yjyy;
            }

            public void setYjsj(long yjsj) {
                this.yjsj = yjsj;
            }

            public long getYjsj() {
                return yjsj;
            }

            public void setQs(String qs) {
                this.qs = qs;
            }

            public String getQs() {
                return qs;
            }

            public void setQsr(String qsr) {
                this.qsr = qsr;
            }

            public String getQsr() {
                return qsr;
            }

            public void setQssj(String qssj) {
                this.qssj = qssj;
            }

            public String getQssj() {
                return qssj;
            }

        }
    }
}
