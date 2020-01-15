package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.List;

/**
 * "data": {
 * "pageNo": 1,
 * "pageSize": 1,
 * "count": 1,
 * "list": [
 * {
 * "lsh": "191102000023",
 * "xh": "9000000023",
 * "cllx": null,
 * "zcbm": "A1325731119198002",
 * "cph": null,
 * "cybm": "510100000000",
 * "fzjg": null,
 * "ywlx": null,
 * "ywyy": null,
 * "djbm": "510100000000",
 * "gdbm": null,
 * "kssj": 1572671159000,
 * "jssj": null,
 * "lczt": "2",
 * "jyw": null,
 * "shbj": "0",
 * "fhbj": "2",
 * "shbz": null,
 * "fhbz": "Dd",
 * "shsj": null,
 * "fhsj": 1572673485000,
 * "btgyy": "原因4,原因2",
 * "tbbz": null,
 * "fhr": "system",
 * "shr": null,
 * "tbr": null
 * }
 * ],
 * "orderDirection": ""
 * }
 */
public class TBEnity extends BaseEntity {

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
        private List<TBList> list;
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

        public void setList(List<TBList> list) {
            this.list = list;
        }

        public List<TBList> getList() {
            return list;
        }

        public void setOrderDirection(String orderDirection) {
            this.orderDirection = orderDirection;
        }

        public String getOrderDirection() {
            return orderDirection;
        }

        public class TBList {

            private String lsh;
            private String xh;
            private String cllx;
            private String zcbm;
            private String cph;
            private String cybm;
            private String fzjg;
            private String ywlx;
            private String ywyy;
            private String djbm;
            private String gdbm;
            private long kssj;
            private String jssj;
            private String lczt;
            private String jyw;
            private String shbj;
            private String fhbj;
            private String shbz;
            private String fhbz;
            private String shsj;
            private long fhsj;
            private String btgyy;
            private String tbbz;
            private String fhr;
            private String shr;
            private String tbr;
            private String syr;
            private String ywry;

            public String getSyr() {
                return syr;
            }

            public void setSyr(String syr) {
                this.syr = syr;
            }

            public String getYwry() {
                return ywry;
            }

            public void setYwry(String ywry) {
                this.ywry = ywry;
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

            public void setCllx(String cllx) {
                this.cllx = cllx;
            }

            public String getCllx() {
                return cllx;
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

            public void setFzjg(String fzjg) {
                this.fzjg = fzjg;
            }

            public String getFzjg() {
                return fzjg;
            }

            public void setYwlx(String ywlx) {
                this.ywlx = ywlx;
            }

            public String getYwlx() {
                return ywlx;
            }

            public void setYwyy(String ywyy) {
                this.ywyy = ywyy;
            }

            public String getYwyy() {
                return ywyy;
            }

            public void setDjbm(String djbm) {
                this.djbm = djbm;
            }

            public String getDjbm() {
                return djbm;
            }

            public void setGdbm(String gdbm) {
                this.gdbm = gdbm;
            }

            public String getGdbm() {
                return gdbm;
            }

            public void setKssj(long kssj) {
                this.kssj = kssj;
            }

            public long getKssj() {
                return kssj;
            }

            public void setJssj(String jssj) {
                this.jssj = jssj;
            }

            public String getJssj() {
                return jssj;
            }

            public void setLczt(String lczt) {
                this.lczt = lczt;
            }

            public String getLczt() {
                return lczt;
            }

            public void setJyw(String jyw) {
                this.jyw = jyw;
            }

            public String getJyw() {
                return jyw;
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

            public void setTbbz(String tbbz) {
                this.tbbz = tbbz;
            }

            public String getTbbz() {
                return tbbz;
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