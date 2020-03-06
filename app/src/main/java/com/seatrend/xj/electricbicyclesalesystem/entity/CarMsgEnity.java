package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ly on 2020/1/15 16:53
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class CarMsgEnity extends BaseEntity implements Serializable {

    private Data data;
    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public class Data implements Serializable{

        private Jscs jscs;
        private CheckData checkData;
        private Syrjbxx syrjbxx;
        private List<Syrzpxx> syrzpxx;
        private CccData cccdata;
        public void setJscs(Jscs jscs) {
            this.jscs = jscs;
        }
        public Jscs getJscs() {
            return jscs;
        }

        public void setCheckData(CheckData checkData) {
            this.checkData = checkData;
        }
        public CheckData getCheckData() {
            return checkData;
        }

        public void setSyrjbxx(Syrjbxx syrjbxx) {
            this.syrjbxx = syrjbxx;
        }
        public Syrjbxx getSyrjbxx() {
            return syrjbxx;
        }

        public void setSyrzpxx(List<Syrzpxx> syrzpxx) {
            this.syrzpxx = syrzpxx;
        }
        public List<Syrzpxx> getSyrzpxx() {
            return syrzpxx;
        }

        public void setCccdata(CccData cccdata) {
            this.cccdata = cccdata;
        }
        public CccData getCccdata() {
            return cccdata;
        }

        public class Syrzpxx implements Serializable{

            private String lsh;
            private String xh;
            private String zpxh;
            private String zpzl;
            private String zpdz;
            private String cffs;
            private String lrr;
            private String lrbm;
            private long lrsj;
            private String zpsm;
            private String zpzt;
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

            public void setZpxh(String zpxh) {
                this.zpxh = zpxh;
            }
            public String getZpxh() {
                return zpxh;
            }

            public void setZpzl(String zpzl) {
                this.zpzl = zpzl;
            }
            public String getZpzl() {
                return zpzl;
            }

            public void setZpdz(String zpdz) {
                this.zpdz = zpdz;
            }
            public String getZpdz() {
                return zpdz;
            }

            public void setCffs(String cffs) {
                this.cffs = cffs;
            }
            public String getCffs() {
                return cffs;
            }

            public void setLrr(String lrr) {
                this.lrr = lrr;
            }
            public String getLrr() {
                return lrr;
            }

            public void setLrbm(String lrbm) {
                this.lrbm = lrbm;
            }
            public String getLrbm() {
                return lrbm;
            }

            public void setLrsj(long lrsj) {
                this.lrsj = lrsj;
            }
            public long getLrsj() {
                return lrsj;
            }

            public void setZpsm(String zpsm) {
                this.zpsm = zpsm;
            }
            public String getZpsm() {
                return zpsm;
            }

            public void setZpzt(String zpzt) {
                this.zpzt = zpzt;
            }
            public String getZpzt() {
                return zpzt;
            }

        }

        public class Syrjbxx implements Serializable{

            private String lsh;
            private String xh;
            private String cllx;
            private String glbm;
            private String ywlx;
            private String ywyy;
            private String bpyy;
            private String bzyy;
            private String zxyy;
            private String cphgzbh;
            private String cccbh;
            private String clzzs;
            private String scqymc;
            private String cpxh;
            private String clzwsb;
            private String cwkc;
            private String cwkk;
            private String cwkg;
            private String xhlc;
            private String zbzl;
            private String zgcs;
            private String zcbm;
            private String csys;
            private long zzrq;
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
            private String djxs;
            private String qybh;
            private String glbh;
            private String cph;
            private String xsqymc;
            private String syrmc;
            private String sfzmmc;
            private String sfzmhm;
            private String djxzqh;
            private String djxxdz;
            private String lxdh;
            private String lxdzxzqh;
            private String lxxxdz;
            private String yzbm;
            private long djrq;
            private String djsf;
            private String fzjg;
            private String clyt;
            private String clzt;
            private String hdfs;
            private String llzm;
            private String syxz;
            private long hqrq;
            private String zrd;
            private String sqfs;
            private String dlrsfzmlx;
            private String dlrsfzmhm;
            private String dlrxm;
            private String dlrlxdh;
            private String rlbdjg;
            private String sfgmbx;
            private String bdh;
            private String zzxsz;
            private String lqfs;
            private String sjrsfzmlx;
            private String sjrsfzmhm;
            private String sjrxm;
            private String sjrlxdh;
            private String sjryjxzqh;
            private String sjryjxxdz;
            private String sjryzbm;
            private String blr;
            private String blrq;
            private String blkssj;
            private String bljssj;
            private String blbm;
            private String blbj;
            private String ywzt;
            private String dzyx;
            private long gxsj;
            private String jyw;
            private String zt;
            private String cyrsfzmhm;
            private String czpt;
            private String sfkyghhp;
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

            public void setCllx(String cllx) {
                this.cllx = cllx;
            }
            public String getCllx() {
                return cllx;
            }

            public void setGlbm(String glbm) {
                this.glbm = glbm;
            }
            public String getGlbm() {
                return glbm;
            }

            public void setYwlx(String ywlx) {
                this.ywlx = ywlx;
            }
            public String getYwlx() {
                return ywlx;
            }

            public void setYwyy(String ywyy) {
                this.ywyy = ywyy;
            }
            public String getYwyy() {
                return ywyy;
            }

            public void setBpyy(String bpyy) {
                this.bpyy = bpyy;
            }
            public String getBpyy() {
                return bpyy;
            }

            public void setBzyy(String bzyy) {
                this.bzyy = bzyy;
            }
            public String getBzyy() {
                return bzyy;
            }

            public void setZxyy(String zxyy) {
                this.zxyy = zxyy;
            }
            public String getZxyy() {
                return zxyy;
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

            public void setClzzs(String clzzs) {
                this.clzzs = clzzs;
            }
            public String getClzzs() {
                return clzzs;
            }

            public void setScqymc(String scqymc) {
                this.scqymc = scqymc;
            }
            public String getScqymc() {
                return scqymc;
            }

            public void setCpxh(String cpxh) {
                this.cpxh = cpxh;
            }
            public String getCpxh() {
                return cpxh;
            }

            public void setClzwsb(String clzwsb) {
                this.clzwsb = clzwsb;
            }
            public String getClzwsb() {
                return clzwsb;
            }

            public void setCwkc(String cwkc) {
                this.cwkc = cwkc;
            }
            public String getCwkc() {
                return cwkc;
            }

            public void setCwkk(String cwkk) {
                this.cwkk = cwkk;
            }
            public String getCwkk() {
                return cwkk;
            }

            public void setCwkg(String cwkg) {
                this.cwkg = cwkg;
            }
            public String getCwkg() {
                return cwkg;
            }

            public void setXhlc(String xhlc) {
                this.xhlc = xhlc;
            }
            public String getXhlc() {
                return xhlc;
            }

            public void setZbzl(String zbzl) {
                this.zbzl = zbzl;
            }
            public String getZbzl() {
                return zbzl;
            }

            public void setZgcs(String zgcs) {
                this.zgcs = zgcs;
            }
            public String getZgcs() {
                return zgcs;
            }

            public void setZcbm(String zcbm) {
                this.zcbm = zcbm;
            }
            public String getZcbm() {
                return zcbm;
            }

            public void setCsys(String csys) {
                this.csys = csys;
            }
            public String getCsys() {
                return csys;
            }

            public void setZzrq(long zzrq) {
                this.zzrq = zzrq;
            }
            public long getZzrq() {
                return zzrq;
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

            public void setCph(String cph) {
                this.cph = cph;
            }
            public String getCph() {
                return cph;
            }

            public void setXsqymc(String xsqymc) {
                this.xsqymc = xsqymc;
            }
            public String getXsqymc() {
                return xsqymc;
            }

            public void setSyrmc(String syrmc) {
                this.syrmc = syrmc;
            }
            public String getSyrmc() {
                return syrmc;
            }

            public void setSfzmmc(String sfzmmc) {
                this.sfzmmc = sfzmmc;
            }
            public String getSfzmmc() {
                return sfzmmc;
            }

            public void setSfzmhm(String sfzmhm) {
                this.sfzmhm = sfzmhm;
            }
            public String getSfzmhm() {
                return sfzmhm;
            }

            public void setDjxzqh(String djxzqh) {
                this.djxzqh = djxzqh;
            }
            public String getDjxzqh() {
                return djxzqh;
            }

            public void setDjxxdz(String djxxdz) {
                this.djxxdz = djxxdz;
            }
            public String getDjxxdz() {
                return djxxdz;
            }

            public void setLxdh(String lxdh) {
                this.lxdh = lxdh;
            }
            public String getLxdh() {
                return lxdh;
            }

            public void setLxdzxzqh(String lxdzxzqh) {
                this.lxdzxzqh = lxdzxzqh;
            }
            public String getLxdzxzqh() {
                return lxdzxzqh;
            }

            public void setLxxxdz(String lxxxdz) {
                this.lxxxdz = lxxxdz;
            }
            public String getLxxxdz() {
                return lxxxdz;
            }

            public void setYzbm(String yzbm) {
                this.yzbm = yzbm;
            }
            public String getYzbm() {
                return yzbm;
            }

            public void setDjrq(long djrq) {
                this.djrq = djrq;
            }
            public long getDjrq() {
                return djrq;
            }

            public void setDjsf(String djsf) {
                this.djsf = djsf;
            }
            public String getDjsf() {
                return djsf;
            }

            public void setFzjg(String fzjg) {
                this.fzjg = fzjg;
            }
            public String getFzjg() {
                return fzjg;
            }

            public void setClyt(String clyt) {
                this.clyt = clyt;
            }
            public String getClyt() {
                return clyt;
            }

            public void setClzt(String clzt) {
                this.clzt = clzt;
            }
            public String getClzt() {
                return clzt;
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

            public void setHqrq(long hqrq) {
                this.hqrq = hqrq;
            }
            public long getHqrq() {
                return hqrq;
            }

            public void setZrd(String zrd) {
                this.zrd = zrd;
            }
            public String getZrd() {
                return zrd;
            }

            public void setSqfs(String sqfs) {
                this.sqfs = sqfs;
            }
            public String getSqfs() {
                return sqfs;
            }

            public void setDlrsfzmlx(String dlrsfzmlx) {
                this.dlrsfzmlx = dlrsfzmlx;
            }
            public String getDlrsfzmlx() {
                return dlrsfzmlx;
            }

            public void setDlrsfzmhm(String dlrsfzmhm) {
                this.dlrsfzmhm = dlrsfzmhm;
            }
            public String getDlrsfzmhm() {
                return dlrsfzmhm;
            }

            public void setDlrxm(String dlrxm) {
                this.dlrxm = dlrxm;
            }
            public String getDlrxm() {
                return dlrxm;
            }

            public void setDlrlxdh(String dlrlxdh) {
                this.dlrlxdh = dlrlxdh;
            }
            public String getDlrlxdh() {
                return dlrlxdh;
            }

            public void setRlbdjg(String rlbdjg) {
                this.rlbdjg = rlbdjg;
            }
            public String getRlbdjg() {
                return rlbdjg;
            }

            public void setSfgmbx(String sfgmbx) {
                this.sfgmbx = sfgmbx;
            }
            public String getSfgmbx() {
                return sfgmbx;
            }

            public void setBdh(String bdh) {
                this.bdh = bdh;
            }
            public String getBdh() {
                return bdh;
            }

            public void setZzxsz(String zzxsz) {
                this.zzxsz = zzxsz;
            }
            public String getZzxsz() {
                return zzxsz;
            }

            public void setLqfs(String lqfs) {
                this.lqfs = lqfs;
            }
            public String getLqfs() {
                return lqfs;
            }

            public void setSjrsfzmlx(String sjrsfzmlx) {
                this.sjrsfzmlx = sjrsfzmlx;
            }
            public String getSjrsfzmlx() {
                return sjrsfzmlx;
            }

            public void setSjrsfzmhm(String sjrsfzmhm) {
                this.sjrsfzmhm = sjrsfzmhm;
            }
            public String getSjrsfzmhm() {
                return sjrsfzmhm;
            }

            public void setSjrxm(String sjrxm) {
                this.sjrxm = sjrxm;
            }
            public String getSjrxm() {
                return sjrxm;
            }

            public void setSjrlxdh(String sjrlxdh) {
                this.sjrlxdh = sjrlxdh;
            }
            public String getSjrlxdh() {
                return sjrlxdh;
            }

            public void setSjryjxzqh(String sjryjxzqh) {
                this.sjryjxzqh = sjryjxzqh;
            }
            public String getSjryjxzqh() {
                return sjryjxzqh;
            }

            public void setSjryjxxdz(String sjryjxxdz) {
                this.sjryjxxdz = sjryjxxdz;
            }
            public String getSjryjxxdz() {
                return sjryjxxdz;
            }

            public void setSjryzbm(String sjryzbm) {
                this.sjryzbm = sjryzbm;
            }
            public String getSjryzbm() {
                return sjryzbm;
            }

            public void setBlr(String blr) {
                this.blr = blr;
            }
            public String getBlr() {
                return blr;
            }

            public void setBlrq(String blrq) {
                this.blrq = blrq;
            }
            public String getBlrq() {
                return blrq;
            }

            public void setBlkssj(String blkssj) {
                this.blkssj = blkssj;
            }
            public String getBlkssj() {
                return blkssj;
            }

            public void setBljssj(String bljssj) {
                this.bljssj = bljssj;
            }
            public String getBljssj() {
                return bljssj;
            }

            public void setBlbm(String blbm) {
                this.blbm = blbm;
            }
            public String getBlbm() {
                return blbm;
            }

            public void setBlbj(String blbj) {
                this.blbj = blbj;
            }
            public String getBlbj() {
                return blbj;
            }

            public void setYwzt(String ywzt) {
                this.ywzt = ywzt;
            }
            public String getYwzt() {
                return ywzt;
            }

            public void setDzyx(String dzyx) {
                this.dzyx = dzyx;
            }
            public String getDzyx() {
                return dzyx;
            }

            public void setGxsj(long gxsj) {
                this.gxsj = gxsj;
            }
            public long getGxsj() {
                return gxsj;
            }

            public void setJyw(String jyw) {
                this.jyw = jyw;
            }
            public String getJyw() {
                return jyw;
            }

            public void setZt(String zt) {
                this.zt = zt;
            }
            public String getZt() {
                return zt;
            }

            public void setCyrsfzmhm(String cyrsfzmhm) {
                this.cyrsfzmhm = cyrsfzmhm;
            }
            public String getCyrsfzmhm() {
                return cyrsfzmhm;
            }

            public void setCzpt(String czpt) {
                this.czpt = czpt;
            }
            public String getCzpt() {
                return czpt;
            }

            public void setSfkyghhp(String sfkyghhp) {
                this.sfkyghhp = sfkyghhp;
            }
            public String getSfkyghhp() {
                return sfkyghhp;
            }

        }

        public class CheckData implements Serializable{

            private String lsh;
            private String xh;
            private String cllx;
            private String ywlx;
            private String ywyy;
            private String cphgzbh;
            private String cccbh;
            private String clzzs;
            private String scqymc;
            private String cpxh;
            private String clzwsb;
            private String cwkc;
            private String cwck;
            private String cwkg;
            private String zbzl;
            private String zgcs;
            private String xxlc;
            private String zcbm;
            private long zzrq;
            private String cph;
            private String csys;
            private String scc;
            private String sck;
            private String scg;
            private String sczbzl;
            private String sczgcs;
            private String scqhlzxj;
            private String jtgn;
            private String shdp;
            private String cyr;
            private String cybm;
            private long cyrq;
            private long cykssj;
            private long cyjssj;
            private String cyjl;
            private String cybz;
            private String fzjg;
            private String cyrsfzmhm;
            private String czpt;
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

            public void setCllx(String cllx) {
                this.cllx = cllx;
            }
            public String getCllx() {
                return cllx;
            }

            public void setYwlx(String ywlx) {
                this.ywlx = ywlx;
            }
            public String getYwlx() {
                return ywlx;
            }

            public void setYwyy(String ywyy) {
                this.ywyy = ywyy;
            }
            public String getYwyy() {
                return ywyy;
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

            public void setClzzs(String clzzs) {
                this.clzzs = clzzs;
            }
            public String getClzzs() {
                return clzzs;
            }

            public void setScqymc(String scqymc) {
                this.scqymc = scqymc;
            }
            public String getScqymc() {
                return scqymc;
            }

            public void setCpxh(String cpxh) {
                this.cpxh = cpxh;
            }
            public String getCpxh() {
                return cpxh;
            }

            public void setClzwsb(String clzwsb) {
                this.clzwsb = clzwsb;
            }
            public String getClzwsb() {
                return clzwsb;
            }

            public void setCwkc(String cwkc) {
                this.cwkc = cwkc;
            }
            public String getCwkc() {
                return cwkc;
            }

            public void setCwck(String cwck) {
                this.cwck = cwck;
            }
            public String getCwck() {
                return cwck;
            }

            public void setCwkg(String cwkg) {
                this.cwkg = cwkg;
            }
            public String getCwkg() {
                return cwkg;
            }

            public void setZbzl(String zbzl) {
                this.zbzl = zbzl;
            }
            public String getZbzl() {
                return zbzl;
            }

            public void setZgcs(String zgcs) {
                this.zgcs = zgcs;
            }
            public String getZgcs() {
                return zgcs;
            }

            public void setXxlc(String xxlc) {
                this.xxlc = xxlc;
            }
            public String getXxlc() {
                return xxlc;
            }

            public void setZcbm(String zcbm) {
                this.zcbm = zcbm;
            }
            public String getZcbm() {
                return zcbm;
            }

            public void setZzrq(long zzrq) {
                this.zzrq = zzrq;
            }
            public long getZzrq() {
                return zzrq;
            }

            public void setCph(String cph) {
                this.cph = cph;
            }
            public String getCph() {
                return cph;
            }

            public void setCsys(String csys) {
                this.csys = csys;
            }
            public String getCsys() {
                return csys;
            }

            public void setScc(String scc) {
                this.scc = scc;
            }
            public String getScc() {
                return scc;
            }

            public void setSck(String sck) {
                this.sck = sck;
            }
            public String getSck() {
                return sck;
            }

            public void setScg(String scg) {
                this.scg = scg;
            }
            public String getScg() {
                return scg;
            }

            public void setSczbzl(String sczbzl) {
                this.sczbzl = sczbzl;
            }
            public String getSczbzl() {
                return sczbzl;
            }

            public void setSczgcs(String sczgcs) {
                this.sczgcs = sczgcs;
            }
            public String getSczgcs() {
                return sczgcs;
            }

            public void setScqhlzxj(String scqhlzxj) {
                this.scqhlzxj = scqhlzxj;
            }
            public String getScqhlzxj() {
                return scqhlzxj;
            }

            public void setJtgn(String jtgn) {
                this.jtgn = jtgn;
            }
            public String getJtgn() {
                return jtgn;
            }

            public void setShdp(String shdp) {
                this.shdp = shdp;
            }
            public String getShdp() {
                return shdp;
            }

            public void setCyr(String cyr) {
                this.cyr = cyr;
            }
            public String getCyr() {
                return cyr;
            }

            public void setCybm(String cybm) {
                this.cybm = cybm;
            }
            public String getCybm() {
                return cybm;
            }

            public void setCyrq(long cyrq) {
                this.cyrq = cyrq;
            }
            public long getCyrq() {
                return cyrq;
            }

            public void setCykssj(long cykssj) {
                this.cykssj = cykssj;
            }
            public long getCykssj() {
                return cykssj;
            }

            public void setCyjssj(long cyjssj) {
                this.cyjssj = cyjssj;
            }
            public long getCyjssj() {
                return cyjssj;
            }

            public void setCyjl(String cyjl) {
                this.cyjl = cyjl;
            }
            public String getCyjl() {
                return cyjl;
            }

            public void setCybz(String cybz) {
                this.cybz = cybz;
            }
            public String getCybz() {
                return cybz;
            }

            public void setFzjg(String fzjg) {
                this.fzjg = fzjg;
            }
            public String getFzjg() {
                return fzjg;
            }

            public void setCyrsfzmhm(String cyrsfzmhm) {
                this.cyrsfzmhm = cyrsfzmhm;
            }
            public String getCyrsfzmhm() {
                return cyrsfzmhm;
            }

            public void setCzpt(String czpt) {
                this.czpt = czpt;
            }
            public String getCzpt() {
                return czpt;
            }

        }


        public class Jscs implements Serializable{

            private String cphgzbh;
            private String cccbh;
            private int ccczt;
            private String clzzs;
            private String scqymc;
            private String cpxh;
            private String clzwsb;
            private String cwkc;
            private String cwkk;
            private String cwkg;
            private String xxlcc;
            private String zgcs;
            private String lj;
            private String zczl;
            private String bgldh;
            private String zzl;
            private String xdclx;
            private String rl;
            private String ddjxs;
            private String edzs;
            private String eddy;
            private String qybhz;
            private String glbhz;
            private String cjszcbhwz;
            private String mpgdwz;
            private String cccbbh;
            private long cccfzrq;
            private String qdfs;
            private String ddjxh;
            private String ddjbm;
            private int edlxscgl;
            private String ddjscqy;
            private String kzqxh;
            private String xdcxh;
            private String kzqscqy;
            private String xdcscqy;
            private String qlltgg;
            private String hlltgg;
            private String sqr;
            private String zzs;
            private String scqydz;
            private String clywsb;
            private long zzrq;
            private String bz;
            private String djr;
            private long djsj;
            private String glbm;
            private long id;
            private int hmd;
            private String sqrsfzmhm;
            private String sqrlxdh;
            private String cyrsfzmhm;
            private String czpt;
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

            public void setCcczt(int ccczt) {
                this.ccczt = ccczt;
            }
            public int getCcczt() {
                return ccczt;
            }

            public void setClzzs(String clzzs) {
                this.clzzs = clzzs;
            }
            public String getClzzs() {
                return clzzs;
            }

            public void setScqymc(String scqymc) {
                this.scqymc = scqymc;
            }
            public String getScqymc() {
                return scqymc;
            }

            public void setCpxh(String cpxh) {
                this.cpxh = cpxh;
            }
            public String getCpxh() {
                return cpxh;
            }

            public void setClzwsb(String clzwsb) {
                this.clzwsb = clzwsb;
            }
            public String getClzwsb() {
                return clzwsb;
            }

            public void setCwkc(String cwkc) {
                this.cwkc = cwkc;
            }
            public String getCwkc() {
                return cwkc;
            }

            public void setCwkk(String cwkk) {
                this.cwkk = cwkk;
            }
            public String getCwkk() {
                return cwkk;
            }

            public void setCwkg(String cwkg) {
                this.cwkg = cwkg;
            }
            public String getCwkg() {
                return cwkg;
            }

            public void setXxlcc(String xxlcc) {
                this.xxlcc = xxlcc;
            }
            public String getXxlcc() {
                return xxlcc;
            }

            public void setZgcs(String zgcs) {
                this.zgcs = zgcs;
            }
            public String getZgcs() {
                return zgcs;
            }

            public void setLj(String lj) {
                this.lj = lj;
            }
            public String getLj() {
                return lj;
            }

            public void setZczl(String zczl) {
                this.zczl = zczl;
            }
            public String getZczl() {
                return zczl;
            }

            public void setBgldh(String bgldh) {
                this.bgldh = bgldh;
            }
            public String getBgldh() {
                return bgldh;
            }

            public void setZzl(String zzl) {
                this.zzl = zzl;
            }
            public String getZzl() {
                return zzl;
            }

            public void setXdclx(String xdclx) {
                this.xdclx = xdclx;
            }
            public String getXdclx() {
                return xdclx;
            }

            public void setRl(String rl) {
                this.rl = rl;
            }
            public String getRl() {
                return rl;
            }

            public void setDdjxs(String ddjxs) {
                this.ddjxs = ddjxs;
            }
            public String getDdjxs() {
                return ddjxs;
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

            public void setQybhz(String qybhz) {
                this.qybhz = qybhz;
            }
            public String getQybhz() {
                return qybhz;
            }

            public void setGlbhz(String glbhz) {
                this.glbhz = glbhz;
            }
            public String getGlbhz() {
                return glbhz;
            }

            public void setCjszcbhwz(String cjszcbhwz) {
                this.cjszcbhwz = cjszcbhwz;
            }
            public String getCjszcbhwz() {
                return cjszcbhwz;
            }

            public void setMpgdwz(String mpgdwz) {
                this.mpgdwz = mpgdwz;
            }
            public String getMpgdwz() {
                return mpgdwz;
            }

            public void setCccbbh(String cccbbh) {
                this.cccbbh = cccbbh;
            }
            public String getCccbbh() {
                return cccbbh;
            }

            public void setCccfzrq(long cccfzrq) {
                this.cccfzrq = cccfzrq;
            }
            public long getCccfzrq() {
                return cccfzrq;
            }

            public void setQdfs(String qdfs) {
                this.qdfs = qdfs;
            }
            public String getQdfs() {
                return qdfs;
            }

            public void setDdjxh(String ddjxh) {
                this.ddjxh = ddjxh;
            }
            public String getDdjxh() {
                return ddjxh;
            }

            public void setDdjbm(String ddjbm) {
                this.ddjbm = ddjbm;
            }
            public String getDdjbm() {
                return ddjbm;
            }

            public void setEdlxscgl(int edlxscgl) {
                this.edlxscgl = edlxscgl;
            }
            public int getEdlxscgl() {
                return edlxscgl;
            }

            public void setDdjscqy(String ddjscqy) {
                this.ddjscqy = ddjscqy;
            }
            public String getDdjscqy() {
                return ddjscqy;
            }

            public void setKzqxh(String kzqxh) {
                this.kzqxh = kzqxh;
            }
            public String getKzqxh() {
                return kzqxh;
            }

            public void setXdcxh(String xdcxh) {
                this.xdcxh = xdcxh;
            }
            public String getXdcxh() {
                return xdcxh;
            }

            public void setKzqscqy(String kzqscqy) {
                this.kzqscqy = kzqscqy;
            }
            public String getKzqscqy() {
                return kzqscqy;
            }

            public void setXdcscqy(String xdcscqy) {
                this.xdcscqy = xdcscqy;
            }
            public String getXdcscqy() {
                return xdcscqy;
            }

            public void setQlltgg(String qlltgg) {
                this.qlltgg = qlltgg;
            }
            public String getQlltgg() {
                return qlltgg;
            }

            public void setHlltgg(String hlltgg) {
                this.hlltgg = hlltgg;
            }
            public String getHlltgg() {
                return hlltgg;
            }

            public void setSqr(String sqr) {
                this.sqr = sqr;
            }
            public String getSqr() {
                return sqr;
            }

            public void setZzs(String zzs) {
                this.zzs = zzs;
            }
            public String getZzs() {
                return zzs;
            }

            public void setScqydz(String scqydz) {
                this.scqydz = scqydz;
            }
            public String getScqydz() {
                return scqydz;
            }

            public void setClywsb(String clywsb) {
                this.clywsb = clywsb;
            }
            public String getClywsb() {
                return clywsb;
            }

            public void setZzrq(long zzrq) {
                this.zzrq = zzrq;
            }
            public long getZzrq() {
                return zzrq;
            }

            public void setBz(String bz) {
                this.bz = bz;
            }
            public String getBz() {
                return bz;
            }

            public void setDjr(String djr) {
                this.djr = djr;
            }
            public String getDjr() {
                return djr;
            }

            public void setDjsj(long djsj) {
                this.djsj = djsj;
            }
            public long getDjsj() {
                return djsj;
            }

            public void setGlbm(String glbm) {
                this.glbm = glbm;
            }
            public String getGlbm() {
                return glbm;
            }

            public void setId(long id) {
                this.id = id;
            }
            public long getId() {
                return id;
            }

            public void setHmd(int hmd) {
                this.hmd = hmd;
            }
            public int getHmd() {
                return hmd;
            }

            public void setSqrsfzmhm(String sqrsfzmhm) {
                this.sqrsfzmhm = sqrsfzmhm;
            }
            public String getSqrsfzmhm() {
                return sqrsfzmhm;
            }

            public void setSqrlxdh(String sqrlxdh) {
                this.sqrlxdh = sqrlxdh;
            }
            public String getSqrlxdh() {
                return sqrlxdh;
            }

            public void setCyrsfzmhm(String cyrsfzmhm) {
                this.cyrsfzmhm = cyrsfzmhm;
            }
            public String getCyrsfzmhm() {
                return cyrsfzmhm;
            }

            public void setCzpt(String czpt) {
                this.czpt = czpt;
            }
            public String getCzpt() {
                return czpt;
            }

        }
        public class CccData implements Serializable{

            private String lsh;
            private String cphgzbh;
            private String cccbh;
            private String ccczt;
            private String cccyxq;
            private String clzzs;
            private String cpxh;
            private String clzwsb;
            private String cwkc;
            private String cwkk;
            private String cwkg;
            private String xhlc;
            private String zbzl;
            private String zgcs;
            private String cjszcbmwz;
            private String mpgdwz;
            private String csys;
            private String zzrq;
            private String zcbm;
            public void setLsh(String lsh) {
                this.lsh = lsh;
            }
            public String getLsh() {
                return lsh;
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

            public void setCcczt(String ccczt) {
                this.ccczt = ccczt;
            }
            public String getCcczt() {
                return ccczt;
            }

            public void setCccyxq(String cccyxq) {
                this.cccyxq = cccyxq;
            }
            public String getCccyxq() {
                return cccyxq;
            }

            public void setClzzs(String clzzs) {
                this.clzzs = clzzs;
            }
            public String getClzzs() {
                return clzzs;
            }

            public void setCpxh(String cpxh) {
                this.cpxh = cpxh;
            }
            public String getCpxh() {
                return cpxh;
            }

            public void setClzwsb(String clzwsb) {
                this.clzwsb = clzwsb;
            }
            public String getClzwsb() {
                return clzwsb;
            }

            public void setCwkc(String cwkc) {
                this.cwkc = cwkc;
            }
            public String getCwkc() {
                return cwkc;
            }

            public void setCwkk(String cwkk) {
                this.cwkk = cwkk;
            }
            public String getCwkk() {
                return cwkk;
            }

            public void setCwkg(String cwkg) {
                this.cwkg = cwkg;
            }
            public String getCwkg() {
                return cwkg;
            }

            public void setXhlc(String xhlc) {
                this.xhlc = xhlc;
            }
            public String getXhlc() {
                return xhlc;
            }

            public void setZbzl(String zbzl) {
                this.zbzl = zbzl;
            }
            public String getZbzl() {
                return zbzl;
            }

            public void setZgcs(String zgcs) {
                this.zgcs = zgcs;
            }
            public String getZgcs() {
                return zgcs;
            }

            public void setCjszcbmwz(String cjszcbmwz) {
                this.cjszcbmwz = cjszcbmwz;
            }
            public String getCjszcbmwz() {
                return cjszcbmwz;
            }

            public void setMpgdwz(String mpgdwz) {
                this.mpgdwz = mpgdwz;
            }
            public String getMpgdwz() {
                return mpgdwz;
            }

            public void setCsys(String csys) {
                this.csys = csys;
            }
            public String getCsys() {
                return csys;
            }

            public void setZzrq(String zzrq) {
                this.zzrq = zzrq;
            }
            public String getZzrq() {
                return zzrq;
            }

            public void setZcbm(String zcbm) {
                this.zcbm = zcbm;
            }
            public String getZcbm() {
                return zcbm;
            }

        }
    }

}
