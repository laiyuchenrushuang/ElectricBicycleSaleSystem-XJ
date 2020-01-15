package com.seatrend.xj.electricbicyclesalesystem.util;

import com.seatrend.xj.electricbicyclesalesystem.entity.BaseEntity;

import java.util.List;

/**
 * Created by ly on 2019/11/22 15:32
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class PermissionEnity extends BaseEntity {

    private List<Data> data;

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data {

        private String xtlb;
        private String mldh;
        private String qxdm;
        private String qxmc;
        private String hbbj;
        private String ssxt;
        private String ssks;
        private String sfxf;
        private String zt;
        private String url;
        private int sxh;
        private String qxsx;
        private String ckms;
        private String xtmc;
        private String ctrlUrl;
        private boolean xzbj;
        private String normal;
        private String sfmj;
        private String jyw;
        private String core;
        private String jyzt;
        private String qxmcTemp;

        public void setXtlb(String xtlb) {
            this.xtlb = xtlb;
        }

        public String getXtlb() {
            return xtlb;
        }

        public void setMldh(String mldh) {
            this.mldh = mldh;
        }

        public String getMldh() {
            return mldh;
        }

        public void setQxdm(String qxdm) {
            this.qxdm = qxdm;
        }

        public String getQxdm() {
            return qxdm;
        }

        public void setQxmc(String qxmc) {
            this.qxmc = qxmc;
        }

        public String getQxmc() {
            return qxmc;
        }

        public void setHbbj(String hbbj) {
            this.hbbj = hbbj;
        }

        public String getHbbj() {
            return hbbj;
        }

        public void setSsxt(String ssxt) {
            this.ssxt = ssxt;
        }

        public String getSsxt() {
            return ssxt;
        }

        public void setSsks(String ssks) {
            this.ssks = ssks;
        }

        public String getSsks() {
            return ssks;
        }

        public void setSfxf(String sfxf) {
            this.sfxf = sfxf;
        }

        public String getSfxf() {
            return sfxf;
        }

        public void setZt(String zt) {
            this.zt = zt;
        }

        public String getZt() {
            return zt;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setSxh(int sxh) {
            this.sxh = sxh;
        }

        public int getSxh() {
            return sxh;
        }

        public void setQxsx(String qxsx) {
            this.qxsx = qxsx;
        }

        public String getQxsx() {
            return qxsx;
        }

        public void setCkms(String ckms) {
            this.ckms = ckms;
        }

        public String getCkms() {
            return ckms;
        }

        public void setXtmc(String xtmc) {
            this.xtmc = xtmc;
        }

        public String getXtmc() {
            return xtmc;
        }

        public void setCtrlUrl(String ctrlUrl) {
            this.ctrlUrl = ctrlUrl;
        }

        public String getCtrlUrl() {
            return ctrlUrl;
        }

        public void setXzbj(boolean xzbj) {
            this.xzbj = xzbj;
        }

        public boolean getXzbj() {
            return xzbj;
        }

        public void setNormal(String normal) {
            this.normal = normal;
        }

        public String getNormal() {
            return normal;
        }

        public void setSfmj(String sfmj) {
            this.sfmj = sfmj;
        }

        public String getSfmj() {
            return sfmj;
        }

        public void setJyw(String jyw) {
            this.jyw = jyw;
        }

        public String getJyw() {
            return jyw;
        }

        public void setCore(String core) {
            this.core = core;
        }

        public String getCore() {
            return core;
        }

        public void setJyzt(String jyzt) {
            this.jyzt = jyzt;
        }

        public String getJyzt() {
            return jyzt;
        }

        public void setQxmcTemp(String qxmcTemp) {
            this.qxmcTemp = qxmcTemp;
        }

        public String getQxmcTemp() {
            return qxmcTemp;
        }

    }
}
