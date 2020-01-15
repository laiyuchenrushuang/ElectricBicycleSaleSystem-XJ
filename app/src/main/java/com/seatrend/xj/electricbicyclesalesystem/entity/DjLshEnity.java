package com.seatrend.xj.electricbicyclesalesystem.entity;

/**
 * Created by ly on 2019/11/13 10:02
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class DjLshEnity extends BaseEntity {
    private Data data;
    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public class Data {

        private String lsh;
        private String xh;
        private String error;
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

        public void setError(String error) {
            this.error = error;
        }
        public String getError() {
            return error;
        }

    }
}
