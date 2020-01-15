package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.List;

/**
 * Created by seatrend on 2018/12/28.
 */

public class LoginEntity extends BaseEntity{
    private Data data;
    private int total;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public class Data {
        private List<SeaPrograms> seaPrograms;
        private List<SeaCodeListOne> seaCodeListOne;
        private List<SeaCodeListTwo> seaCodeListTwo;
        private SysUser sysUser;
        private String jwtToken;
        private SeaDepartment seaDepartment;
        private String username;
        private String updatePwd;

        public String getUpdatePwd() {
            return updatePwd;
        }

        public void setUpdatePwd(String updatePwd) {
            this.updatePwd = updatePwd;
        }
        public List<SeaPrograms> getSeaPrograms() {
            return seaPrograms;
        }

        public void setSeaPrograms(List<SeaPrograms> seaPrograms) {
            this.seaPrograms = seaPrograms;
        }

        public List<SeaCodeListOne> getSeaCodeListOne() {
            return seaCodeListOne;
        }

        public void setSeaCodeListOne(List<SeaCodeListOne> seaCodeListOne) {
            this.seaCodeListOne = seaCodeListOne;
        }

        public List<SeaCodeListTwo> getSeaCodeListTwo() {
            return seaCodeListTwo;
        }

        public void setSeaCodeListTwo(List<SeaCodeListTwo> seaCodeListTwo) {
            this.seaCodeListTwo = seaCodeListTwo;
        }

        public SysUser getSysUser() {
            return sysUser;
        }

        public void setSysUser(SysUser sysUser) {
            this.sysUser = sysUser;
        }

        public String getJwtToken() {
            return jwtToken;
        }

        public void setJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
        }

        public SeaDepartment getSeaDepartment() {
            return seaDepartment;
        }

        public void setSeaDepartment(SeaDepartment seaDepartment) {
            this.seaDepartment = seaDepartment;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }


        public class SysUser {

            private String id;
            private String xm;
            private String mm;
            private String lxr;
            private String lxdh;
            private String glbm;
            private String zhzt;
            private String role;
            private String jslx;
            private String timestamp;
            private String judge;
            private long mmyxqz;
            private long cjsj;
            private String sfzmhm;
            private String xsdmc;
            private String yhdh;
            private String bmmc;

            public String getBmmc() {
                return bmmc;
            }

            public void setBmmc(String bmmc) {
                this.bmmc = bmmc;
            }

            public String getYhdh() {
                return yhdh;
            }

            public void setYhdh(String yhdh) {
                this.yhdh = yhdh;
            }

            public String getXsdmc() {
                return xsdmc;
            }

            public void setXsdmc(String xsdmc) {
                this.xsdmc = xsdmc;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }

            public void setXm(String xm) {
                this.xm = xm;
            }

            public String getXm() {
                return xm;
            }

            public void setMm(String mm) {
                this.mm = mm;
            }

            public String getMm() {
                return mm;
            }

            public void setLxr(String lxr) {
                this.lxr = lxr;
            }

            public String getLxr() {
                return lxr;
            }

            public void setLxdh(String lxdh) {
                this.lxdh = lxdh;
            }

            public String getLxdh() {
                return lxdh;
            }

            public void setGlbm(String glbm) {
                this.glbm = glbm;
            }

            public String getGlbm() {
                return glbm;
            }

            public void setZhzt(String zhzt) {
                this.zhzt = zhzt;
            }

            public String getZhzt() {
                return zhzt;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getRole() {
                return role;
            }

            public void setJslx(String jslx) {
                this.jslx = jslx;
            }

            public String getJslx() {
                return jslx;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setJudge(String judge) {
                this.judge = judge;
            }

            public String getJudge() {
                return judge;
            }

            public void setMmyxqz(long mmyxqz) {
                this.mmyxqz = mmyxqz;
            }

            public long getMmyxqz() {
                return mmyxqz;
            }

            public void setCjsj(long cjsj) {
                this.cjsj = cjsj;
            }

            public long getCjsj() {
                return cjsj;
            }

            public void setSfzmhm(String sfzmhm) {
                this.sfzmhm = sfzmhm;
            }

            public String getSfzmhm() {
                return sfzmhm;
            }

        }

        public class SeaDepartment {

            private String glbm;
            private String bmmc;
            private String bmqc;
            private String yzmc;
            private String fzjg;
            private String bmjb;
            private String kclyw;
            private String ywlb;
            private String fzr;
            private String lxr;
            private String lxdh;
            private String czhm;
            private String lxdz;
            private String sjbm;
            private String bz;
            private String sjcjzdbm;
            private String sjwfzdbm;
            private String sjsgzdbm;
            private String jzjb;
            private String gltz;
            private String jfly;
            private String yfly;
            private String jlzt;
            private String jrgaw;
            private String lsgx;
            private String jzptglbm;
            private String csbj;
            private String gxsj;
            private List<String> childList;

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

            public void setBmqc(String bmqc) {
                this.bmqc = bmqc;
            }

            public String getBmqc() {
                return bmqc;
            }

            public void setYzmc(String yzmc) {
                this.yzmc = yzmc;
            }

            public String getYzmc() {
                return yzmc;
            }

            public void setFzjg(String fzjg) {
                this.fzjg = fzjg;
            }

            public String getFzjg() {
                return fzjg;
            }

            public void setBmjb(String bmjb) {
                this.bmjb = bmjb;
            }

            public String getBmjb() {
                return bmjb;
            }

            public void setKclyw(String kclyw) {
                this.kclyw = kclyw;
            }

            public String getKclyw() {
                return kclyw;
            }

            public void setYwlb(String ywlb) {
                this.ywlb = ywlb;
            }

            public String getYwlb() {
                return ywlb;
            }

            public void setFzr(String fzr) {
                this.fzr = fzr;
            }

            public String getFzr() {
                return fzr;
            }

            public void setLxr(String lxr) {
                this.lxr = lxr;
            }

            public String getLxr() {
                return lxr;
            }

            public void setLxdh(String lxdh) {
                this.lxdh = lxdh;
            }

            public String getLxdh() {
                return lxdh;
            }

            public void setCzhm(String czhm) {
                this.czhm = czhm;
            }

            public String getCzhm() {
                return czhm;
            }

            public void setLxdz(String lxdz) {
                this.lxdz = lxdz;
            }

            public String getLxdz() {
                return lxdz;
            }

            public void setSjbm(String sjbm) {
                this.sjbm = sjbm;
            }

            public String getSjbm() {
                return sjbm;
            }

            public void setBz(String bz) {
                this.bz = bz;
            }

            public String getBz() {
                return bz;
            }

            public void setSjcjzdbm(String sjcjzdbm) {
                this.sjcjzdbm = sjcjzdbm;
            }

            public String getSjcjzdbm() {
                return sjcjzdbm;
            }

            public void setSjwfzdbm(String sjwfzdbm) {
                this.sjwfzdbm = sjwfzdbm;
            }

            public String getSjwfzdbm() {
                return sjwfzdbm;
            }

            public void setSjsgzdbm(String sjsgzdbm) {
                this.sjsgzdbm = sjsgzdbm;
            }

            public String getSjsgzdbm() {
                return sjsgzdbm;
            }

            public void setJzjb(String jzjb) {
                this.jzjb = jzjb;
            }

            public String getJzjb() {
                return jzjb;
            }

            public void setGltz(String gltz) {
                this.gltz = gltz;
            }

            public String getGltz() {
                return gltz;
            }

            public void setJfly(String jfly) {
                this.jfly = jfly;
            }

            public String getJfly() {
                return jfly;
            }

            public void setYfly(String yfly) {
                this.yfly = yfly;
            }

            public String getYfly() {
                return yfly;
            }

            public void setJlzt(String jlzt) {
                this.jlzt = jlzt;
            }

            public String getJlzt() {
                return jlzt;
            }

            public void setJrgaw(String jrgaw) {
                this.jrgaw = jrgaw;
            }

            public String getJrgaw() {
                return jrgaw;
            }

            public void setLsgx(String lsgx) {
                this.lsgx = lsgx;
            }

            public String getLsgx() {
                return lsgx;
            }

            public void setJzptglbm(String jzptglbm) {
                this.jzptglbm = jzptglbm;
            }

            public String getJzptglbm() {
                return jzptglbm;
            }

            public void setCsbj(String csbj) {
                this.csbj = csbj;
            }

            public String getCsbj() {
                return csbj;
            }

            public void setGxsj(String gxsj) {
                this.gxsj = gxsj;
            }

            public String getGxsj() {
                return gxsj;
            }

            public void setChildList(List<String> childList) {
                this.childList = childList;
            }

            public List<String> getChildList() {
                return childList;
            }

        }

        public class SeaCodeListTwo {

            private String xtlb;
            private String dmlb;
            private String dmz;
            private String mldh;
            private String dmsm1;
            private String mlmc;
            private String dmsm2;
            private String dmsm3;
            private String zt;
            private String dmsm4;
            public void setXtlb(String xtlb) {
                this.xtlb = xtlb;
            }
            public String getXtlb() {
                return xtlb;
            }

            public void setDmlb(String dmlb) {
                this.dmlb = dmlb;
            }
            public String getDmlb() {
                return dmlb;
            }

            public void setDmz(String dmz) {
                this.dmz = dmz;
            }
            public String getDmz() {
                return dmz;
            }

            public void setMldh(String mldh) {
                this.mldh = mldh;
            }
            public String getMldh() {
                return mldh;
            }

            public void setDmsm1(String dmsm1) {
                this.dmsm1 = dmsm1;
            }
            public String getDmsm1() {
                return dmsm1;
            }

            public void setMlmc(String mlmc) {
                this.mlmc = mlmc;
            }
            public String getMlmc() {
                return mlmc;
            }

            public void setDmsm2(String dmsm2) {
                this.dmsm2 = dmsm2;
            }
            public String getDmsm2() {
                return dmsm2;
            }

            public void setDmsm3(String dmsm3) {
                this.dmsm3 = dmsm3;
            }
            public String getDmsm3() {
                return dmsm3;
            }

            public void setZt(String zt) {
                this.zt = zt;
            }
            public String getZt() {
                return zt;
            }

            public void setDmsm4(String dmsm4) {
                this.dmsm4 = dmsm4;
            }
            public String getDmsm4() {
                return dmsm4;
            }

        }

        public class SeaCodeListOne {

            private String xtlb;
            private String dmlb;
            private String dmz;
            private String mldh;
            private String dmsm1;
            private String mlmc;
            private String dmsm2;
            private String dmsm3;
            private String zt;
            private String dmsm4;
            public void setXtlb(String xtlb) {
                this.xtlb = xtlb;
            }
            public String getXtlb() {
                return xtlb;
            }

            public void setDmlb(String dmlb) {
                this.dmlb = dmlb;
            }
            public String getDmlb() {
                return dmlb;
            }

            public void setDmz(String dmz) {
                this.dmz = dmz;
            }
            public String getDmz() {
                return dmz;
            }

            public void setMldh(String mldh) {
                this.mldh = mldh;
            }
            public String getMldh() {
                return mldh;
            }

            public void setDmsm1(String dmsm1) {
                this.dmsm1 = dmsm1;
            }
            public String getDmsm1() {
                return dmsm1;
            }

            public void setMlmc(String mlmc) {
                this.mlmc = mlmc;
            }
            public String getMlmc() {
                return mlmc;
            }

            public void setDmsm2(String dmsm2) {
                this.dmsm2 = dmsm2;
            }
            public String getDmsm2() {
                return dmsm2;
            }

            public void setDmsm3(String dmsm3) {
                this.dmsm3 = dmsm3;
            }
            public String getDmsm3() {
                return dmsm3;
            }

            public void setZt(String zt) {
                this.zt = zt;
            }
            public String getZt() {
                return zt;
            }

            public void setDmsm4(String dmsm4) {
                this.dmsm4 = dmsm4;
            }
            public String getDmsm4() {
                return dmsm4;
            }

        }

        public class SeaPrograms {

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
            private String sxh;
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

            public void setSxh(String sxh) {
                this.sxh = sxh;
            }
            public String getSxh() {
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
}
