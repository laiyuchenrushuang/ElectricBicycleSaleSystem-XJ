package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.List;

/**
 * {
 * "bmmc": "string",
 * "createtime": "2019-11-04T10:07:29.811Z",
 * "gdip1": "string",
 * "gdip2": "string",
 * "gdip3": "string",
 * "glbm": "string",
 * "id": "string",
 * "jslx": "string",
 * "listRole": [
 * {
 * "jsdh": "string",
 * "yhdh": "string"
 * }
 * ],
 * "lxdh": "string",
 * "pwd": "string",
 * "qxms": "string",
 * "sfzmhm": "string",
 * "xm": "string",
 * "yhdh": "string",
 * "yxqz": "2019-11-04T10:07:29.812Z",
 * "zhzt": "string"
 * }
 */
public class YGPostEnity {


    private String bmmc;
    private String createtime;
    private String gdip1;
    private String gdip2;
    private String gdip3;
    private String glbm;
    private String id;
    private String jslx;
    private List<ListRole> listRole;
    private String lxdh;
    private String pwd;
    private String qxms;
    private String sfzmhm;
    private String xm;
    private String yhdh;
    private String yxqz;
    private String zhzt;

    public String getSfzmc() {
        return sfzmc;
    }

    public void setSfzmc(String sfzmc) {
        this.sfzmc = sfzmc;
    }

    private String sfzmc;
    private String cjr; //创建人

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }


    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreatetime() {
        return createtime;
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

    public void setGlbm(String glbm) {
        this.glbm = glbm;
    }

    public String getGlbm() {
        return glbm;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setJslx(String jslx) {
        this.jslx = jslx;
    }

    public String getJslx() {
        return jslx;
    }

    public void setListRole(List<ListRole> listRole) {
        this.listRole = listRole;
    }

    public List<ListRole> getListRole() {
        return listRole;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
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

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXm() {
        return xm;
    }

    public void setYhdh(String yhdh) {
        this.yhdh = yhdh;
    }

    public String getYhdh() {
        return yhdh;
    }

    public void setYxqz(String yxqz) {
        this.yxqz = yxqz;
    }

    public String getYxqz() {
        return yxqz;
    }

    public void setZhzt(String zhzt) {
        this.zhzt = zhzt;
    }

    public String getZhzt() {
        return zhzt;
    }

    public static class ListRole {

        private String jsdh;
        private String yhdh;

        public void setJsdh(String jsdh) {
            this.jsdh = jsdh;
        }

        public String getJsdh() {
            return jsdh;
        }

        public void setYhdh(String yhdh) {
            this.yhdh = yhdh;
        }

        public String getYhdh() {
            return yhdh;
        }

    }
}
