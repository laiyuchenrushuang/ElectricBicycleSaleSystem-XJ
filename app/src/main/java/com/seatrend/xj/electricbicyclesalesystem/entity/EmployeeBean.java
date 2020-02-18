package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2019/10/31 18:18
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class EmployeeBean extends BaseEntity {
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
        private ArrayList<EmployList> list;
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

        public void setList(ArrayList<EmployList> list) {
            this.list = list;
        }

        public ArrayList<EmployList> getList() {
            return list;
        }

        public void setOrderDirection(String orderDirection) {
            this.orderDirection = orderDirection;
        }

        public String getOrderDirection() {
            return orderDirection;
        }

        public class EmployList {
            private String id;
            private String yhdh;
            private String xm;
            private String glbm;
            private String bmmc;
            private String qxms;
            private String sfzmhm;
            private String pwd;
            private String zhzt;
            private String yxqz;
            private long createtime;
            private String jslx;
            private String lxdh;
            private String gdip1;
            private String gdip2;
            private String gdip3;
            private List<ListRole> listRole;
            private List<Photos> photos;
            private String zb;

            public String getSfzmc() {
                return sfzmc;
            }

            public void setSfzmc(String sfzmc) {
                this.sfzmc = sfzmc;
            }

            private String sfzmc;

            public String getSjbm() {
                return sjbm;
            }

            public void setSjbm(String sjbm) {
                this.sjbm = sjbm;
            }

            public String getSjbmmc() {
                return sjbmmc;
            }

            public void setSjbmmc(String sjbmmc) {
                this.sjbmmc = sjbmmc;
            }

            private String sjbm;
            private String sjbmmc;

            public void setId(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }

            public void setYhdh(String yhdh) {
                this.yhdh = yhdh;
            }

            public String getYhdh() {
                return yhdh;
            }

            public void setXm(String xm) {
                this.xm = xm;
            }

            public String getXm() {
                return xm;
            }

            public void setGlbm(String glbm) {
                this.glbm = glbm;
            }

            public String getGlbm() {
                return glbm;
            }

            public void setBmmc(String bmmc) {
                this.bmmc = bmmc;
            }

            public String getBmmc() {
                return bmmc;
            }

            public void setQxms(String qxms) {
                this.qxms = qxms;
            }

            public String getQxms() {
                return qxms;
            }

            public void setSfzmhm(String sfzmhm) {
                this.sfzmhm = sfzmhm;
            }

            public String getSfzmhm() {
                return sfzmhm;
            }

            public void setPwd(String pwd) {
                this.pwd = pwd;
            }

            public String getPwd() {
                return pwd;
            }

            public void setZhzt(String zhzt) {
                this.zhzt = zhzt;
            }

            public String getZhzt() {
                return zhzt;
            }

            public void setYxqz(String yxqz) {
                this.yxqz = yxqz;
            }

            public String getYxqz() {
                return yxqz;
            }

            public void setCreatetime(long createtime) {
                this.createtime = createtime;
            }

            public long getCreatetime() {
                return createtime;
            }

            public void setJslx(String jslx) {
                this.jslx = jslx;
            }

            public String getJslx() {
                return jslx;
            }

            public void setLxdh(String lxdh) {
                this.lxdh = lxdh;
            }

            public String getLxdh() {
                return lxdh;
            }

            public void setGdip1(String gdip1) {
                this.gdip1 = gdip1;
            }

            public String getGdip1() {
                return gdip1;
            }

            public void setGdip2(String gdip2) {
                this.gdip2 = gdip2;
            }

            public String getGdip2() {
                return gdip2;
            }

            public void setGdip3(String gdip3) {
                this.gdip3 = gdip3;
            }

            public String getGdip3() {
                return gdip3;
            }

            public void setListRole(List<ListRole> listRole) {
                this.listRole = listRole;
            }

            public List<ListRole> getListRole() {
                return listRole;
            }

            public void setPhotos(List<Photos> photos) {
                this.photos = photos;
            }

            public List<Photos> getPhotos() {
                return photos;
            }

            public void setZb(String zb) {
                this.zb = zb;
            }

            public String getZb() {
                return zb;
            }

            public class Photos {

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

            public class ListRole {

                private String jsdh;
                private String jsmc;
                private String yhdh;
                public void setJsdh(String jsdh) {
                    this.jsdh = jsdh;
                }
                public String getJsdh() {
                    return jsdh;
                }

                public void setjsmc(String jsdhmc) {
                    this.jsmc = jsdhmc;
                }
                public String getjsmc() {
                    return jsmc;
                }

                public void setYhdh(String yhdh) {
                    this.yhdh = yhdh;
                }
                public String getYhdh() {
                    return yhdh;
                }

            }
        }
    }
}
