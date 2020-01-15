package com.seatrend.xj.electricbicyclesalesystem.entity;

/**
 * Created by seatrend on 2019/1/8.
 */

public class BusinessEntity extends BaseEntity{


    private Data data;
    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public class Data {

        private int dgd;
        private int ytb;
        private int dfh;
        private int ygd;
        private int ywzs;
        public void setDgd(int dgd) {
            this.dgd = dgd;
        }
        public int getDgd() {
            return dgd;
        }

        public void setYtb(int ytb) {
            this.ytb = ytb;
        }
        public int getYtb() {
            return ytb;
        }

        public void setDfh(int dfh) {
            this.dfh = dfh;
        }
        public int getDfh() {
            return dfh;
        }

        public void setYgd(int ygd) {
            this.ygd = ygd;
        }
        public int getYgd() {
            return ygd;
        }

        public void setYwzs(int ywzs) {
            this.ywzs = ywzs;
        }
        public int getYwzs() {
            return ywzs;
        }

    }
}
