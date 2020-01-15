//package com.seatrend.xj.electricbicyclesalesystem.entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
//
// {
// "data": {
// "checkData": {
// "lsh": "191025000003",
// "xh": "0",
// "cllx": "0",
// "ywlx": "0",
// "ywyy": "0",
// "cphgzbh": "0",
// "cccbh": "0",
// "clzzs": "0",
// "scqymc": "0",
// "cpxh": "0",
// "clzwsb": "0",
// "cwkc": "0",
// "cwck": "0",
// "cwkg": "0",
// "zbzl": "0",
// "zgcs": "0",
// "xxlc": "0",
// "zcbm": "A1325731119198000",
// "zzrq": 1571988251000,
// "cph": "12345",
// "csys": "A",
// "scc": "0",
// "sck": "0",
// "scg": "0",
// "sczbzl": "0",
// "sczgcs": "0",
// "scqhlzxj": "0",
// "jtgn": "0",
// "shdp": "0",
// "cyr": "0",
// "cybm": "0",
// "cyrq": 1571991336000,
// "cykssj": 1571973939000,
// "cyjssj": 1571988251000,
// "cyjl": "0",
// "cybz": "0"
// },
// "fjdcJscu": {
// "cphgzbh": "A118688AT01TD0100",
// "cccbh": "2018011119129276",
// "ccczt": "01",
// "cccyxqz": "2018-11-08 00:00:00",
// "clzzs": "四川倍特电动车有限公司",
// "scqymc": "四川倍特电动车有限公司",
// "cpxh": "TD045DFZ01",
// "clzwsb": "倍特",
// "cwkc": "1475",
// "cwkk": "635",
// "cwkg": "1045",
// "xxlcc": "50",
// "zgcs": "24",
// "lj": "1035",
// "zczl": "52",
// "bgldh": "1.8",
// "zzl": null,
// "rl": "12",
// "bcdy": null,
// "ddjxs": "永磁",
// "bcgl": null,
// "edzs": "320",
// "eddy": "48",
// "qybhz": "42±0.5",
// "glbhz": "17±1",
// "cjszcbhwz": "打刻在车头管正前面中部",
// "mpgdwz": "铆接在车架后部右侧",
// "zcbm": "A1325731119198000",
// "hphm": null
// }
// }
// }
// */
//public class YWEntranceEnity extends BaseEntity {
//    private Data data;
//    public void setData(Data data) {
//        this.data = data;
//    }
//    public Data getData() {
//        return data;
//    }
//
//    public static class Data {
//
//        private CheckData checkData;
//        private FjdcJscu fjdcJscu;
//        public void setCheckData(CheckData checkData) {
//            this.checkData = checkData;
//        }
//        public CheckData getCheckData() {
//            return checkData;
//        }
//        private ArrayList<PhotoList> photoList;
//        public void setPhotoList(ArrayList<PhotoList> photoList) {
//            this.photoList = photoList;
//        }
//        public ArrayList<PhotoList> getPhotoList() {
//            return photoList;
//        }
//        private CccData cccData;
//        public void setCccData(CccData cccData) {
//            this.cccData = cccData;
//        }
//        public CccData getCccData() {
//            return cccData;
//        }
//
//        public void setFjdcJscu(FjdcJscu fjdcJscu) {
//            this.fjdcJscu = fjdcJscu;
//        }
//        public FjdcJscu getFjdcJscu() {
//            return fjdcJscu;
//        }
//
//        public class CccData {
//
//            private String lsh;
//            private String cphgzbh;
//            private String cccbh;
//            private String ccczt;
//            private String cccyxq;
//            private String clzzs;
//            private String cpxh;
//            private String clzwsb;
//            private String cwkc;
//            private String cwkk;
//            private String cwkg;
//            private String xhlc;
//            private String zbzl;
//            private String zgcs;
//            private String cjszcbmwz;
//            private String mpgdwz;
//            private String csys;
//            private String zzrq;
//            public void setLsh(String lsh) {
//                this.lsh = lsh;
//            }
//            public String getLsh() {
//                return lsh;
//            }
//
//            public void setCphgzbh(String cphgzbh) {
//                this.cphgzbh = cphgzbh;
//            }
//            public String getCphgzbh() {
//                return cphgzbh;
//            }
//
//            public void setCccbh(String cccbh) {
//                this.cccbh = cccbh;
//            }
//            public String getCccbh() {
//                return cccbh;
//            }
//
//            public void setCcczt(String ccczt) {
//                this.ccczt = ccczt;
//            }
//            public String getCcczt() {
//                return ccczt;
//            }
//
//            public void setCccyxq(String cccyxq) {
//                this.cccyxq = cccyxq;
//            }
//            public String getCccyxq() {
//                return cccyxq;
//            }
//
//            public void setClzzs(String clzzs) {
//                this.clzzs = clzzs;
//            }
//            public String getClzzs() {
//                return clzzs;
//            }
//
//            public void setCpxh(String cpxh) {
//                this.cpxh = cpxh;
//            }
//            public String getCpxh() {
//                return cpxh;
//            }
//
//            public void setClzwsb(String clzwsb) {
//                this.clzwsb = clzwsb;
//            }
//            public String getClzwsb() {
//                return clzwsb;
//            }
//
//            public void setCwkc(String cwkc) {
//                this.cwkc = cwkc;
//            }
//            public String getCwkc() {
//                return cwkc;
//            }
//
//            public void setCwkk(String cwkk) {
//                this.cwkk = cwkk;
//            }
//            public String getCwkk() {
//                return cwkk;
//            }
//
//            public void setCwkg(String cwkg) {
//                this.cwkg = cwkg;
//            }
//            public String getCwkg() {
//                return cwkg;
//            }
//
//            public void setXhlc(String xhlc) {
//                this.xhlc = xhlc;
//            }
//            public String getXhlc() {
//                return xhlc;
//            }
//
//            public void setZbzl(String zbzl) {
//                this.zbzl = zbzl;
//            }
//            public String getZbzl() {
//                return zbzl;
//            }
//
//            public void setZgcs(String zgcs) {
//                this.zgcs = zgcs;
//            }
//            public String getZgcs() {
//                return zgcs;
//            }
//
//            public void setCjszcbmwz(String cjszcbmwz) {
//                this.cjszcbmwz = cjszcbmwz;
//            }
//            public String getCjszcbmwz() {
//                return cjszcbmwz;
//            }
//
//            public void setMpgdwz(String mpgdwz) {
//                this.mpgdwz = mpgdwz;
//            }
//            public String getMpgdwz() {
//                return mpgdwz;
//            }
//
//            public void setCsys(String csys) {
//                this.csys = csys;
//            }
//            public String getCsys() {
//                return csys;
//            }
//
//            public void setZzrq(String zzrq) {
//                this.zzrq = zzrq;
//            }
//            public String getZzrq() {
//                return zzrq;
//            }
//
//        }
//        public class CheckData {
//
//            private String lsh;
//            private String xh;
//            private String cllx;
//            private String ywlx;
//            private String ywyy;
//            private String cphgzbh;
//            private String cccbh;
//            private String clzzs;
//            private String scqymc;
//            private String cpxh;
//            private String clzwsb;
//            private String cwkc;
//            private String cwck;
//            private String cwkg;
//            private String zbzl;
//            private String zgcs;
//            private String xxlc;
//            private String zcbm;
//            private long zzrq;
//            private String cph;
//            private String csys;
//            private String scc;
//            private String sck;
//            private String scg;
//            private String sczbzl;
//            private String sczgcs;
//            private String scqhlzxj;
//            private String jtgn;
//            private String shdp;
//            private String cyr;
//            private String cybm;
//            private long cyrq;
//            private long cykssj;
//            private long cyjssj;
//            private String cyjl;
//            private String cybz;
//            public void setLsh(String lsh) {
//                this.lsh = lsh;
//            }
//            public String getLsh() {
//                return lsh;
//            }
//
//            public void setXh(String xh) {
//                this.xh = xh;
//            }
//            public String getXh() {
//                return xh;
//            }
//
//            public void setCllx(String cllx) {
//                this.cllx = cllx;
//            }
//            public String getCllx() {
//                return cllx;
//            }
//
//            public void setYwlx(String ywlx) {
//                this.ywlx = ywlx;
//            }
//            public String getYwlx() {
//                return ywlx;
//            }
//
//            public void setYwyy(String ywyy) {
//                this.ywyy = ywyy;
//            }
//            public String getYwyy() {
//                return ywyy;
//            }
//
//            public void setCphgzbh(String cphgzbh) {
//                this.cphgzbh = cphgzbh;
//            }
//            public String getCphgzbh() {
//                return cphgzbh;
//            }
//
//            public void setCccbh(String cccbh) {
//                this.cccbh = cccbh;
//            }
//            public String getCccbh() {
//                return cccbh;
//            }
//
//            public void setClzzs(String clzzs) {
//                this.clzzs = clzzs;
//            }
//            public String getClzzs() {
//                return clzzs;
//            }
//
//            public void setScqymc(String scqymc) {
//                this.scqymc = scqymc;
//            }
//            public String getScqymc() {
//                return scqymc;
//            }
//
//            public void setCpxh(String cpxh) {
//                this.cpxh = cpxh;
//            }
//            public String getCpxh() {
//                return cpxh;
//            }
//
//            public void setClzwsb(String clzwsb) {
//                this.clzwsb = clzwsb;
//            }
//            public String getClzwsb() {
//                return clzwsb;
//            }
//
//            public void setCwkc(String cwkc) {
//                this.cwkc = cwkc;
//            }
//            public String getCwkc() {
//                return cwkc;
//            }
//
//            public void setCwck(String cwck) {
//                this.cwck = cwck;
//            }
//            public String getCwck() {
//                return cwck;
//            }
//
//            public void setCwkg(String cwkg) {
//                this.cwkg = cwkg;
//            }
//            public String getCwkg() {
//                return cwkg;
//            }
//
//            public void setZbzl(String zbzl) {
//                this.zbzl = zbzl;
//            }
//            public String getZbzl() {
//                return zbzl;
//            }
//
//            public void setZgcs(String zgcs) {
//                this.zgcs = zgcs;
//            }
//            public String getZgcs() {
//                return zgcs;
//            }
//
//            public void setXxlc(String xxlc) {
//                this.xxlc = xxlc;
//            }
//            public String getXxlc() {
//                return xxlc;
//            }
//
//            public void setZcbm(String zcbm) {
//                this.zcbm = zcbm;
//            }
//            public String getZcbm() {
//                return zcbm;
//            }
//
//            public void setZzrq(long zzrq) {
//                this.zzrq = zzrq;
//            }
//            public long getZzrq() {
//                return zzrq;
//            }
//
//            public void setCph(String cph) {
//                this.cph = cph;
//            }
//            public String getCph() {
//                return cph;
//            }
//
//            public void setCsys(String csys) {
//                this.csys = csys;
//            }
//            public String getCsys() {
//                return csys;
//            }
//
//            public void setScc(String scc) {
//                this.scc = scc;
//            }
//            public String getScc() {
//                return scc;
//            }
//
//            public void setSck(String sck) {
//                this.sck = sck;
//            }
//            public String getSck() {
//                return sck;
//            }
//
//            public void setScg(String scg) {
//                this.scg = scg;
//            }
//            public String getScg() {
//                return scg;
//            }
//
//            public void setSczbzl(String sczbzl) {
//                this.sczbzl = sczbzl;
//            }
//            public String getSczbzl() {
//                return sczbzl;
//            }
//
//            public void setSczgcs(String sczgcs) {
//                this.sczgcs = sczgcs;
//            }
//            public String getSczgcs() {
//                return sczgcs;
//            }
//
//            public void setScqhlzxj(String scqhlzxj) {
//                this.scqhlzxj = scqhlzxj;
//            }
//            public String getScqhlzxj() {
//                return scqhlzxj;
//            }
//
//            public void setJtgn(String jtgn) {
//                this.jtgn = jtgn;
//            }
//            public String getJtgn() {
//                return jtgn;
//            }
//
//            public void setShdp(String shdp) {
//                this.shdp = shdp;
//            }
//            public String getShdp() {
//                return shdp;
//            }
//
//            public void setCyr(String cyr) {
//                this.cyr = cyr;
//            }
//            public String getCyr() {
//                return cyr;
//            }
//
//            public void setCybm(String cybm) {
//                this.cybm = cybm;
//            }
//            public String getCybm() {
//                return cybm;
//            }
//
//            public void setCyrq(long cyrq) {
//                this.cyrq = cyrq;
//            }
//            public long getCyrq() {
//                return cyrq;
//            }
//
//            public void setCykssj(long cykssj) {
//                this.cykssj = cykssj;
//            }
//            public long getCykssj() {
//                return cykssj;
//            }
//
//            public void setCyjssj(long cyjssj) {
//                this.cyjssj = cyjssj;
//            }
//            public long getCyjssj() {
//                return cyjssj;
//            }
//
//            public void setCyjl(String cyjl) {
//                this.cyjl = cyjl;
//            }
//            public String getCyjl() {
//                return cyjl;
//            }
//
//            public void setCybz(String cybz) {
//                this.cybz = cybz;
//            }
//            public String getCybz() {
//                return cybz;
//            }
//
//        }
//
//        public static class PhotoList {
//
//            private String lsh;
//            private String xh;
//            private String zpxh;
//            private String zpzl;
//            private String zpdz;
//            private String cffs;
//            private String lrr;
//            private String lrbm;
//            private String lrsj;
//            private String zpsm;
//            private String zpzt;
//            public void setLsh(String lsh) {
//                this.lsh = lsh;
//            }
//            public String getLsh() {
//                return lsh;
//            }
//
//            public void setXh(String xh) {
//                this.xh = xh;
//            }
//            public String getXh() {
//                return xh;
//            }
//
//            public void setZpxh(String zpxh) {
//                this.zpxh = zpxh;
//            }
//            public String getZpxh() {
//                return zpxh;
//            }
//
//            public void setZpzl(String zpzl) {
//                this.zpzl = zpzl;
//            }
//            public String getZpzl() {
//                return zpzl;
//            }
//
//            public void setZpdz(String zpdz) {
//                this.zpdz = zpdz;
//            }
//            public String getZpdz() {
//                return zpdz;
//            }
//
//            public void setCffs(String cffs) {
//                this.cffs = cffs;
//            }
//            public String getCffs() {
//                return cffs;
//            }
//
//            public void setLrr(String lrr) {
//                this.lrr = lrr;
//            }
//            public String getLrr() {
//                return lrr;
//            }
//
//            public void setLrbm(String lrbm) {
//                this.lrbm = lrbm;
//            }
//            public String getLrbm() {
//                return lrbm;
//            }
//
//            public void setLrsj(String lrsj) {
//                this.lrsj = lrsj;
//            }
//            public String getLrsj() {
//                return lrsj;
//            }
//
//            public void setZpsm(String zpsm) {
//                this.zpsm = zpsm;
//            }
//            public String getZpsm() {
//                return zpsm;
//            }
//
//            public void setZpzt(String zpzt) {
//                this.zpzt = zpzt;
//            }
//            public String getZpzt() {
//                return zpzt;
//            }
//
//        }
//        public class FjdcJscu {
//
//            private String cphgzbh;
//            private String cccbh;
//            private String ccczt;
//            private String cccyxqz;
//            private String clzzs;
//            private String scqymc;
//            private String cpxh;
//            private String clzwsb;
//            private String cwkc;
//            private String cwkk;
//            private String cwkg;
//            private String xxlcc;
//            private String zgcs;
//            private String lj;
//            private String zczl;
//            private String bgldh;
//            private String zzl;
//            private String rl;
//            private String bcdy;
//            private String ddjxs;
//            private String bcgl;
//            private String edzs;
//            private String eddy;
//            private String qybhz;
//            private String glbhz;
//            private String cjszcbhwz;
//            private String mpgdwz;
//            private String zcbm;
//            private String hphm;
//            public void setCphgzbh(String cphgzbh) {
//                this.cphgzbh = cphgzbh;
//            }
//            public String getCphgzbh() {
//                return cphgzbh;
//            }
//
//            public void setCccbh(String cccbh) {
//                this.cccbh = cccbh;
//            }
//            public String getCccbh() {
//                return cccbh;
//            }
//
//            public void setCcczt(String ccczt) {
//                this.ccczt = ccczt;
//            }
//            public String getCcczt() {
//                return ccczt;
//            }
//
//            public void setCccyxqz(String cccyxqz) {
//                this.cccyxqz = cccyxqz;
//            }
//            public String getCccyxqz() {
//                return cccyxqz;
//            }
//
//            public void setClzzs(String clzzs) {
//                this.clzzs = clzzs;
//            }
//            public String getClzzs() {
//                return clzzs;
//            }
//
//            public void setScqymc(String scqymc) {
//                this.scqymc = scqymc;
//            }
//            public String getScqymc() {
//                return scqymc;
//            }
//
//            public void setCpxh(String cpxh) {
//                this.cpxh = cpxh;
//            }
//            public String getCpxh() {
//                return cpxh;
//            }
//
//            public void setClzwsb(String clzwsb) {
//                this.clzwsb = clzwsb;
//            }
//            public String getClzwsb() {
//                return clzwsb;
//            }
//
//            public void setCwkc(String cwkc) {
//                this.cwkc = cwkc;
//            }
//            public String getCwkc() {
//                return cwkc;
//            }
//
//            public void setCwkk(String cwkk) {
//                this.cwkk = cwkk;
//            }
//            public String getCwkk() {
//                return cwkk;
//            }
//
//            public void setCwkg(String cwkg) {
//                this.cwkg = cwkg;
//            }
//            public String getCwkg() {
//                return cwkg;
//            }
//
//            public void setXxlcc(String xxlcc) {
//                this.xxlcc = xxlcc;
//            }
//            public String getXxlcc() {
//                return xxlcc;
//            }
//
//            public void setZgcs(String zgcs) {
//                this.zgcs = zgcs;
//            }
//            public String getZgcs() {
//                return zgcs;
//            }
//
//            public void setLj(String lj) {
//                this.lj = lj;
//            }
//            public String getLj() {
//                return lj;
//            }
//
//            public void setZczl(String zczl) {
//                this.zczl = zczl;
//            }
//            public String getZczl() {
//                return zczl;
//            }
//
//            public void setBgldh(String bgldh) {
//                this.bgldh = bgldh;
//            }
//            public String getBgldh() {
//                return bgldh;
//            }
//
//            public void setZzl(String zzl) {
//                this.zzl = zzl;
//            }
//            public String getZzl() {
//                return zzl;
//            }
//
//            public void setRl(String rl) {
//                this.rl = rl;
//            }
//            public String getRl() {
//                return rl;
//            }
//
//            public void setBcdy(String bcdy) {
//                this.bcdy = bcdy;
//            }
//            public String getBcdy() {
//                return bcdy;
//            }
//
//            public void setDdjxs(String ddjxs) {
//                this.ddjxs = ddjxs;
//            }
//            public String getDdjxs() {
//                return ddjxs;
//            }
//
//            public void setBcgl(String bcgl) {
//                this.bcgl = bcgl;
//            }
//            public String getBcgl() {
//                return bcgl;
//            }
//
//            public void setEdzs(String edzs) {
//                this.edzs = edzs;
//            }
//            public String getEdzs() {
//                return edzs;
//            }
//
//            public void setEddy(String eddy) {
//                this.eddy = eddy;
//            }
//            public String getEddy() {
//                return eddy;
//            }
//
//            public void setQybhz(String qybhz) {
//                this.qybhz = qybhz;
//            }
//            public String getQybhz() {
//                return qybhz;
//            }
//
//            public void setGlbhz(String glbhz) {
//                this.glbhz = glbhz;
//            }
//            public String getGlbhz() {
//                return glbhz;
//            }
//
//            public void setCjszcbhwz(String cjszcbhwz) {
//                this.cjszcbhwz = cjszcbhwz;
//            }
//            public String getCjszcbhwz() {
//                return cjszcbhwz;
//            }
//
//            public void setMpgdwz(String mpgdwz) {
//                this.mpgdwz = mpgdwz;
//            }
//            public String getMpgdwz() {
//                return mpgdwz;
//            }
//
//            public void setZcbm(String zcbm) {
//                this.zcbm = zcbm;
//            }
//            public String getZcbm() {
//                return zcbm;
//            }
//
//            public void setHphm(String hphm) {
//                this.hphm = hphm;
//            }
//            public String getHphm() {
//                return hphm;
//            }
//
//        }
//    }
//}
