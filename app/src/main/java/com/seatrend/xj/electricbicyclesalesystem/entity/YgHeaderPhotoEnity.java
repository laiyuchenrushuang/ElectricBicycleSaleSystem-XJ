package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.List;

/**
 * "data": [
 *     {
 *       "yhdh": "555444",
 *       "zpdz": "M00/00/03/wKgACF3BZtqASfZKAAPsj8RiDmU242.jpg",
 *       "gxsj": "2019-11-05 20:10:21",
 *       "zpzl": "C1"
 *     },
 *     {
 *       "yhdh": "555444",
 *       "zpdz": "M00/00/03/wKgACF3BZt6AeBASAAPIUZuMqaU625.jpg",
 *       "gxsj": "2019-11-05 20:10:21",
 *       "zpzl": "C2"
 *     },
 *     {
 *       "yhdh": "555444",
 *       "zpdz": "M00/00/03/wKgACF3BZuOADaAuAAP2nja34eI433.jpg",
 *       "gxsj": "2019-11-05 20:10:21",
 *       "zpzl": "C3"
 *     }
 *   ]
 */
public class YgHeaderPhotoEnity extends BaseEntity {
    private List<Data> data;
    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    public class Data {

        private String yhdh;
        private String zpdz;
        private String gxsj;
        private String zpzl;
        public void setYhdh(String yhdh) {
            this.yhdh = yhdh;
        }
        public String getYhdh() {
            return yhdh;
        }

        public void setZpdz(String zpdz) {
            this.zpdz = zpdz;
        }
        public String getZpdz() {
            return zpdz;
        }

        public void setGxsj(String gxsj) {
            this.gxsj = gxsj;
        }
        public String getGxsj() {
            return gxsj;
        }

        public void setZpzl(String zpzl) {
            this.zpzl = zpzl;
        }
        public String getZpzl() {
            return zpzl;
        }

    }
}
