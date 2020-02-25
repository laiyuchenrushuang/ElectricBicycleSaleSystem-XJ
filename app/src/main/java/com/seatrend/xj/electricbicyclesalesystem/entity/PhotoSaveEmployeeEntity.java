package com.seatrend.xj.electricbicyclesalesystem.entity;

/**
 * Created by ly on 2020/2/24 16:25
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class PhotoSaveEmployeeEntity extends BaseEntity {

    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }


    public class Data {

        private String lsh;
        private String zpzl;
        private String sfzmhm;

        public String getSfzhm() {
            return sfzmhm;
        }

        public void setSfzhm(String sfzhm) {
            this.sfzmhm = sfzhm;
        }

        public void setLsh(String lsh) {
            this.lsh = lsh;
        }

        public String getLsh() {
            return lsh;
        }

        public void setZplx(String zplx) {
            this.zpzl = zplx;
        }

        public String getZplx() {
            return zpzl;
        }

    }
}
