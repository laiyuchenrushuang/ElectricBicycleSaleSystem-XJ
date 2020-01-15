package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.List;

/**
 * Created by ly on 2019/8/9 10:32
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */

public class YwSearchByZcbmBean {

    private boolean status;
    private int code;
    private String message;
    private Data data;
    private int total;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

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

    public static class Data {

        private VehVehicle VehVehicle;
        private List<BusList> busList;

        public void setVehVehicle(VehVehicle VehVehicle) {
            this.VehVehicle = VehVehicle;
        }

        public VehVehicle getVehVehicle() {
            return VehVehicle;
        }

        public void setBusList(List<BusList> busList) {
            this.busList = busList;
        }

        public List<BusList> getBusList() {
            return busList;
        }

        public class VehVehicle {

            private String veid;
            private String zcbm;
            private String clpp;
            private String clxh;
            private String sccx;
            private String hpzl;
            private String sczbzl;
            private String cszt;
            private int hdzk;
            private String hdzz;
            private String zbzl;
            private String wkc;
            private String wkk;
            private String wkg;
            private String zgss;
            private String xhlc;
            private String cdfs;
            private String qhzj;
            private String qlzj;
            private String hlzj;
            private String ltkd;
            private String dcrl;
            private String bcdy;
            private String dclx;
            private String edzs;
            private String eddy;
            private String lxgl;
            private String edzj;
            private String djxs;
            private String qybh;
            private String glbh;
            private String csys1;
            private String csys2;
            private String qxgn;
            private String cshbsd;
            private String djh;
            private String sjss;
            private String lp;
            private String jydz;
            private String tmh;
            private String cphgzbh;
            private String cccbh;
            private String cllx;
            private String zgssbj;
            private String zczlbj;
            private String csysbj;
            private String ckgbj;
            private String jtxsbj;
            private String qhzxljbj;

            public void setVeid(String veid) {
                this.veid = veid;
            }

            public String getVeid() {
                return veid;
            }

            public void setZcbm(String zcbm) {
                this.zcbm = zcbm;
            }

            public String getZcbm() {
                return zcbm;
            }

            public void setClpp(String clpp) {
                this.clpp = clpp;
            }

            public String getClpp() {
                return clpp;
            }

            public void setClxh(String clxh) {
                this.clxh = clxh;
            }

            public String getClxh() {
                return clxh;
            }

            public void setSccx(String sccx) {
                this.sccx = sccx;
            }

            public String getSccx() {
                return sccx;
            }

            public void setHpzl(String hpzl) {
                this.hpzl = hpzl;
            }

            public String getHpzl() {
                return hpzl;
            }

            public void setSczbzl(String sczbzl) {
                this.sczbzl = sczbzl;
            }

            public String getSczbzl() {
                return sczbzl;
            }

            public void setCszt(String cszt) {
                this.cszt = cszt;
            }

            public String getCszt() {
                return cszt;
            }

            public void setHdzk(int hdzk) {
                this.hdzk = hdzk;
            }

            public int getHdzk() {
                return hdzk;
            }

            public void setHdzz(String hdzz) {
                this.hdzz = hdzz;
            }

            public String getHdzz() {
                return hdzz;
            }

            public void setZbzl(String zbzl) {
                this.zbzl = zbzl;
            }

            public String getZbzl() {
                return zbzl;
            }

            public void setWkc(String wkc) {
                this.wkc = wkc;
            }

            public String getWkc() {
                return wkc;
            }

            public void setWkk(String wkk) {
                this.wkk = wkk;
            }

            public String getWkk() {
                return wkk;
            }

            public void setWkg(String wkg) {
                this.wkg = wkg;
            }

            public String getWkg() {
                return wkg;
            }

            public void setZgss(String zgss) {
                this.zgss = zgss;
            }

            public String getZgss() {
                return zgss;
            }

            public void setXhlc(String xhlc) {
                this.xhlc = xhlc;
            }

            public String getXhlc() {
                return xhlc;
            }

            public void setCdfs(String cdfs) {
                this.cdfs = cdfs;
            }

            public String getCdfs() {
                return cdfs;
            }

            public void setQhzj(String qhzj) {
                this.qhzj = qhzj;
            }

            public String getQhzj() {
                return qhzj;
            }

            public void setQlzj(String qlzj) {
                this.qlzj = qlzj;
            }

            public String getQlzj() {
                return qlzj;
            }

            public void setHlzj(String hlzj) {
                this.hlzj = hlzj;
            }

            public String getHlzj() {
                return hlzj;
            }

            public void setLtkd(String ltkd) {
                this.ltkd = ltkd;
            }

            public String getLtkd() {
                return ltkd;
            }

            public void setDcrl(String dcrl) {
                this.dcrl = dcrl;
            }

            public String getDcrl() {
                return dcrl;
            }

            public void setBcdy(String bcdy) {
                this.bcdy = bcdy;
            }

            public String getBcdy() {
                return bcdy;
            }

            public void setDclx(String dclx) {
                this.dclx = dclx;
            }

            public String getDclx() {
                return dclx;
            }

            public void setEdzs(String edzs) {
                this.edzs = edzs;
            }

            public String getEdzs() {
                return edzs;
            }

            public void setEddy(String eddy) {
                this.eddy = eddy;
            }

            public String getEddy() {
                return eddy;
            }

            public void setLxgl(String lxgl) {
                this.lxgl = lxgl;
            }

            public String getLxgl() {
                return lxgl;
            }

            public void setEdzj(String edzj) {
                this.edzj = edzj;
            }

            public String getEdzj() {
                return edzj;
            }

            public void setDjxs(String djxs) {
                this.djxs = djxs;
            }

            public String getDjxs() {
                return djxs;
            }

            public void setQybh(String qybh) {
                this.qybh = qybh;
            }

            public String getQybh() {
                return qybh;
            }

            public void setGlbh(String glbh) {
                this.glbh = glbh;
            }

            public String getGlbh() {
                return glbh;
            }

            public void setCsys1(String csys1) {
                this.csys1 = csys1;
            }

            public String getCsys1() {
                return csys1;
            }

            public void setCsys2(String csys2) {
                this.csys2 = csys2;
            }

            public String getCsys2() {
                return csys2;
            }

            public void setQxgn(String qxgn) {
                this.qxgn = qxgn;
            }

            public String getQxgn() {
                return qxgn;
            }

            public void setCshbsd(String cshbsd) {
                this.cshbsd = cshbsd;
            }

            public String getCshbsd() {
                return cshbsd;
            }

            public void setDjh(String djh) {
                this.djh = djh;
            }

            public String getDjh() {
                return djh;
            }

            public void setSjss(String sjss) {
                this.sjss = sjss;
            }

            public String getSjss() {
                return sjss;
            }

            public void setLp(String lp) {
                this.lp = lp;
            }

            public String getLp() {
                return lp;
            }

            public void setJydz(String jydz) {
                this.jydz = jydz;
            }

            public String getJydz() {
                return jydz;
            }

            public void setTmh(String tmh) {
                this.tmh = tmh;
            }

            public String getTmh() {
                return tmh;
            }

            public void setCphgzbh(String cphgzbh) {
                this.cphgzbh = cphgzbh;
            }

            public String getCphgzbh() {
                return cphgzbh;
            }

            public void setCccbh(String cccbh) {
                this.cccbh = cccbh;
            }

            public String getCccbh() {
                return cccbh;
            }

            public void setCllx(String cllx) {
                this.cllx = cllx;
            }

            public String getCllx() {
                return cllx;
            }

            public void setZgssbj(String zgssbj) {
                this.zgssbj = zgssbj;
            }

            public String getZgssbj() {
                return zgssbj;
            }

            public void setZczlbj(String zczlbj) {
                this.zczlbj = zczlbj;
            }

            public String getZczlbj() {
                return zczlbj;
            }

            public void setCsysbj(String csysbj) {
                this.csysbj = csysbj;
            }

            public String getCsysbj() {
                return csysbj;
            }

            public void setCkgbj(String ckgbj) {
                this.ckgbj = ckgbj;
            }

            public String getCkgbj() {
                return ckgbj;
            }

            public void setJtxsbj(String jtxsbj) {
                this.jtxsbj = jtxsbj;
            }

            public String getJtxsbj() {
                return jtxsbj;
            }

            public void setQhzxljbj(String qhzxljbj) {
                this.qhzxljbj = qhzxljbj;
            }

            public String getQhzxljbj() {
                return qhzxljbj;
            }

        }

        public static class BusList {

            private String lsh;
            private String veid;
            private String drid;
            private String ywlx;
            private String lczt;
            private String zcbm;
            private String sfzmhm;
            private String cllx;
            private String hdfs;
            private String llzm;
            private String syxz;
            private String clyt;
            private String xsqy;
            private String hqrq;
            private String cyry;
            private String cyrq;
            private String cybm;
            private String cybj;
            private String fhry;
            private long fhrq;
            private String fhbj;
            private String fhbz;
            private String djry;
            private String djrq;
            private String djbm;
            private String bfry;
            private String bfrq;
            private String bfbm;
            private String jgry;
            private long jgrq;
            private String jgbm;
            private String jgbj;
            private String jgbz;
            private String bgnr;
            private String zxyy;
            private String bfyy;
            private String fzjg;
            private String gdry;
            private String gdrq;
            private String gdbm;
            private long slrq;
            private String hphm;
            private String hpzl;
            private String fhbm;
            private String dlrid;
            private String yjrid;
            private String xsyrid;
            private String bxd;
            private String vehVehicle;
            private String vehInsurance;
            private String zlList;
            private String syr;
            private String xsyr;
            private String dlr;
            private String yjr;

            public void setLsh(String lsh) {
                this.lsh = lsh;
            }

            public String getLsh() {
                return lsh;
            }

            public void setVeid(String veid) {
                this.veid = veid;
            }

            public String getVeid() {
                return veid;
            }

            public void setDrid(String drid) {
                this.drid = drid;
            }

            public String getDrid() {
                return drid;
            }

            public void setYwlx(String ywlx) {
                this.ywlx = ywlx;
            }

            public String getYwlx() {
                return ywlx;
            }

            public void setLczt(String lczt) {
                this.lczt = lczt;
            }

            public String getLczt() {
                return lczt;
            }

            public void setZcbm(String zcbm) {
                this.zcbm = zcbm;
            }

            public String getZcbm() {
                return zcbm;
            }

            public void setSfzmhm(String sfzmhm) {
                this.sfzmhm = sfzmhm;
            }

            public String getSfzmhm() {
                return sfzmhm;
            }

            public void setCllx(String cllx) {
                this.cllx = cllx;
            }

            public String getCllx() {
                return cllx;
            }

            public void setHdfs(String hdfs) {
                this.hdfs = hdfs;
            }

            public String getHdfs() {
                return hdfs;
            }

            public void setLlzm(String llzm) {
                this.llzm = llzm;
            }

            public String getLlzm() {
                return llzm;
            }

            public void setSyxz(String syxz) {
                this.syxz = syxz;
            }

            public String getSyxz() {
                return syxz;
            }

            public void setClyt(String clyt) {
                this.clyt = clyt;
            }

            public String getClyt() {
                return clyt;
            }

            public void setXsqy(String xsqy) {
                this.xsqy = xsqy;
            }

            public String getXsqy() {
                return xsqy;
            }

            public void setHqrq(String hqrq) {
                this.hqrq = hqrq;
            }

            public String getHqrq() {
                return hqrq;
            }

            public void setCyry(String cyry) {
                this.cyry = cyry;
            }

            public String getCyry() {
                return cyry;
            }

            public void setCyrq(String cyrq) {
                this.cyrq = cyrq;
            }

            public String getCyrq() {
                return cyrq;
            }

            public void setCybm(String cybm) {
                this.cybm = cybm;
            }

            public String getCybm() {
                return cybm;
            }

            public void setCybj(String cybj) {
                this.cybj = cybj;
            }

            public String getCybj() {
                return cybj;
            }

            public void setFhry(String fhry) {
                this.fhry = fhry;
            }

            public String getFhry() {
                return fhry;
            }

            public void setFhrq(long fhrq) {
                this.fhrq = fhrq;
            }

            public long getFhrq() {
                return fhrq;
            }

            public void setFhbj(String fhbj) {
                this.fhbj = fhbj;
            }

            public String getFhbj() {
                return fhbj;
            }

            public void setFhbz(String fhbz) {
                this.fhbz = fhbz;
            }

            public String getFhbz() {
                return fhbz;
            }

            public void setDjry(String djry) {
                this.djry = djry;
            }

            public String getDjry() {
                return djry;
            }

            public void setDjrq(String djrq) {
                this.djrq = djrq;
            }

            public String getDjrq() {
                return djrq;
            }

            public void setDjbm(String djbm) {
                this.djbm = djbm;
            }

            public String getDjbm() {
                return djbm;
            }

            public void setBfry(String bfry) {
                this.bfry = bfry;
            }

            public String getBfry() {
                return bfry;
            }

            public void setBfrq(String bfrq) {
                this.bfrq = bfrq;
            }

            public String getBfrq() {
                return bfrq;
            }

            public void setBfbm(String bfbm) {
                this.bfbm = bfbm;
            }

            public String getBfbm() {
                return bfbm;
            }

            public void setJgry(String jgry) {
                this.jgry = jgry;
            }

            public String getJgry() {
                return jgry;
            }

            public void setJgrq(long jgrq) {
                this.jgrq = jgrq;
            }

            public long getJgrq() {
                return jgrq;
            }

            public void setJgbm(String jgbm) {
                this.jgbm = jgbm;
            }

            public String getJgbm() {
                return jgbm;
            }

            public void setJgbj(String jgbj) {
                this.jgbj = jgbj;
            }

            public String getJgbj() {
                return jgbj;
            }

            public void setJgbz(String jgbz) {
                this.jgbz = jgbz;
            }

            public String getJgbz() {
                return jgbz;
            }

            public void setBgnr(String bgnr) {
                this.bgnr = bgnr;
            }

            public String getBgnr() {
                return bgnr;
            }

            public void setZxyy(String zxyy) {
                this.zxyy = zxyy;
            }

            public String getZxyy() {
                return zxyy;
            }

            public void setBfyy(String bfyy) {
                this.bfyy = bfyy;
            }

            public String getBfyy() {
                return bfyy;
            }

            public void setFzjg(String fzjg) {
                this.fzjg = fzjg;
            }

            public String getFzjg() {
                return fzjg;
            }

            public void setGdry(String gdry) {
                this.gdry = gdry;
            }

            public String getGdry() {
                return gdry;
            }

            public void setGdrq(String gdrq) {
                this.gdrq = gdrq;
            }

            public String getGdrq() {
                return gdrq;
            }

            public void setGdbm(String gdbm) {
                this.gdbm = gdbm;
            }

            public String getGdbm() {
                return gdbm;
            }

            public void setSlrq(long slrq) {
                this.slrq = slrq;
            }

            public long getSlrq() {
                return slrq;
            }

            public void setHphm(String hphm) {
                this.hphm = hphm;
            }

            public String getHphm() {
                return hphm;
            }

            public void setHpzl(String hpzl) {
                this.hpzl = hpzl;
            }

            public String getHpzl() {
                return hpzl;
            }

            public void setFhbm(String fhbm) {
                this.fhbm = fhbm;
            }

            public String getFhbm() {
                return fhbm;
            }

            public void setDlrid(String dlrid) {
                this.dlrid = dlrid;
            }

            public String getDlrid() {
                return dlrid;
            }

            public void setYjrid(String yjrid) {
                this.yjrid = yjrid;
            }

            public String getYjrid() {
                return yjrid;
            }

            public void setXsyrid(String xsyrid) {
                this.xsyrid = xsyrid;
            }

            public String getXsyrid() {
                return xsyrid;
            }

            public void setBxd(String bxd) {
                this.bxd = bxd;
            }

            public String getBxd() {
                return bxd;
            }

            public void setVehVehicle(String vehVehicle) {
                this.vehVehicle = vehVehicle;
            }

            public String getVehVehicle() {
                return vehVehicle;
            }

            public void setVehInsurance(String vehInsurance) {
                this.vehInsurance = vehInsurance;
            }

            public String getVehInsurance() {
                return vehInsurance;
            }

            public void setZlList(String zlList) {
                this.zlList = zlList;
            }

            public String getZlList() {
                return zlList;
            }

            public void setSyr(String syr) {
                this.syr = syr;
            }

            public String getSyr() {
                return syr;
            }

            public void setXsyr(String xsyr) {
                this.xsyr = xsyr;
            }

            public String getXsyr() {
                return xsyr;
            }

            public void setDlr(String dlr) {
                this.dlr = dlr;
            }

            public String getDlr() {
                return dlr;
            }

            public void setYjr(String yjr) {
                this.yjr = yjr;
            }

            public String getYjr() {
                return yjr;
            }

        }
    }

}
