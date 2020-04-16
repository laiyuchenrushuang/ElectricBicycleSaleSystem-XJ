package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.List;

/**
 * Created by ly on 2020/4/15 16:10
 */
public class ReasonEntity extends BaseEntity {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * lx : 1
         * ly : 电动车信息与合格证信息不符
         */

        private String id;
        private String lx;
        private String ly;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLx() {
            return lx;
        }

        public void setLx(String lx) {
            this.lx = lx;
        }

        public String getLy() {
            return ly;
        }

        public void setLy(String ly) {
            this.ly = ly;
        }
    }
}
