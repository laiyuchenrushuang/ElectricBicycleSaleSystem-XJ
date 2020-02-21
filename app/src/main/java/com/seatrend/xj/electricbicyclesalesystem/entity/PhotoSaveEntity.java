package com.seatrend.xj.electricbicyclesalesystem.entity;

/**
 * Created by ly on 2020/2/20 12:11
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class PhotoSaveEntity extends BaseEntity {

    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }


    public class Data {

        private String lsh;
        private String zplx;
        private String sfzhm;

        public String getSfzhm() {
            return sfzhm;
        }

        public void setSfzhm(String sfzhm) {
            this.sfzhm = sfzhm;
        }

        public void setLsh(String lsh) {
            this.lsh = lsh;
        }

        public String getLsh() {
            return lsh;
        }

        public void setZplx(String zplx) {
            this.zplx = zplx;
        }

        public String getZplx() {
            return zplx;
        }

    }

}
