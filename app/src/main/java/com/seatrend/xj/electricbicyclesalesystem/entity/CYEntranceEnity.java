package com.seatrend.xj.electricbicyclesalesystem.entity;

/**
 "data": {
 "lsh": "191025000002",
 "xh": "9000000002",
 "fjdcJscu": {
 "cphgzbh": "A118688AT01TD0100",
 "cccbh": "2018011119129276",
 "ccczt": "01",
 "cccyxqz": "2018-11-08 00:00:00",
 "clzzs": "四川倍特电动车有限公司",
 "scqymc": "四川倍特电动车有限公司",
 "cpxh": "TD045DFZ01",
 "clzwsb": "倍特",
 "cwkc": "1475",
 "cwkk": "635",
 "cwkg": "1045",
 "xxlcc": "50",
 "zgcs": "24",
 "lj": "1035",
 "zczl": "52",
 "bgldh": "1.8",
 "zzl": null,
 "rl": "12",
 "bcdy": null,
 "ddjxs": "永磁",
 "bcgl": null,
 "edzs": "320",
 "eddy": "48",
 "qybhz": "42±0.5",
 "glbhz": "17±1",
 "cjszcbhwz": "打刻在车头管正前面中部",
 "mpgdwz": "铆接在车架后部右侧",
 "zcbm": "A1325731119198000",
 "hphm": null
 }
 }
 */
public class CYEntranceEnity extends BaseEntity {
    private Data data;
    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public class Data {

        private String lsh;
        private String xh;
        private FjdcJscu fjdcJscu;
        private ThreeCertificates threeCertificates;
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

        public void setFjdcJscu(FjdcJscu fjdcJscu) {
            this.fjdcJscu = fjdcJscu;
        }
        public FjdcJscu getFjdcJscu() {
            return fjdcJscu;
        }

        public void setThreeCertificates(ThreeCertificates threeCertificates) {
            this.threeCertificates = threeCertificates;
        }
        public ThreeCertificates getThreeCertificates() {
            return threeCertificates;
        }


        public class ThreeCertificates {

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

        public class FjdcJscu {

            private String cphgzbh;
            private String cccbh;
            private String ccczt;
            private long cccyxqz;
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
            private String bcdy;
            private String ddjxs;
            private String bcgl;
            private String edzs;
            private String eddy;
            private String qybhz;
            private String glbhz;
            private String cjszcbhwz;
            private String mpgdwz;
            private String zcbm;
            private String hphm;
            private String scqydz;
            private String clywsb;
            private String qhlzxj;//前后轮中心距
            private String edlxscgl; //额定输出功率
            private String cccbbh;

            public String getSqr() {
                return sqr;
            }

            public void setSqr(String sqr) {
                this.sqr = sqr;
            }

            private String sqr;

            public String getQlltgg() {
                return qlltgg;
            }

            public void setQlltgg(String qlltgg) {
                this.qlltgg = qlltgg;
            }

            public String getHlltgg() {
                return hlltgg;
            }

            public void setHlltgg(String hlltgg) {
                this.hlltgg = hlltgg;
            }

            private String qlltgg;
            private String hlltgg;

            public String getXdcscqy() {
                return xdcscqy;
            }

            public void setXdcscqy(String xdcscqy) {
                this.xdcscqy = xdcscqy;
            }

            private String xdcscqy;

            public String getXdcxh() {
                return xdcxh;
            }

            public void setXdcxh(String xdcxh) {
                this.xdcxh = xdcxh;
            }

            private String xdcxh;

            public String getXdcrl() {
                return xdcrl;
            }

            public void setXdcrl(String xdcrl) {
                this.xdcrl = xdcrl;
            }

            private String xdcrl;

            public String getKzqxh() {
                return kzqxh;
            }

            public void setKzqxh(String kzqxh) {
                this.kzqxh = kzqxh;
            }

            public String getKzqscqy() {
                return kzqscqy;
            }

            public void setKzqscqy(String kzqscqy) {
                this.kzqscqy = kzqscqy;
            }

            private String kzqxh;
            private String kzqscqy;

            public String getDdjscqy() {
                return ddjscqy;
            }

            public void setDdjscqy(String ddjscqy) {
                this.ddjscqy = ddjscqy;
            }

            private String ddjscqy;


            public String getEdlxscgl() {
                return edlxscgl;
            }

            public void setEdlxscgl(String edscgl) {
                this.edlxscgl = edscgl;
            }


            public String getDdjbm() {
                return ddjbm;
            }

            public void setDdjbm(String ddjbm) {
                this.ddjbm = ddjbm;
            }

            public String getDdjxh() {
                return ddjxh;
            }

            public void setDdjxh(String ddjxh) {
                this.ddjxh = ddjxh;
            }

            private String ddjbm;
            private String ddjxh;


            public String getCccbbh() {
                return cccbbh;
            }

            public void setCccbbh(String cccbbh) {
                this.cccbbh = cccbbh;
            }




            public String getQhlzxj() {
                return qhlzxj;
            }

            public void setQhlzxj(String qhlzxj) {
                this.qhlzxj = qhlzxj;
            }


            public String getQdfs() {
                return qdfs;
            }

            public void setQdfs(String qdfs) {
                this.qdfs = qdfs;
            }

            private String qdfs;

            public String getClywsb() {
                return clywsb;
            }

            public void setClywsb(String clywsb) {
                this.clywsb = clywsb;
            }




            public String getScqydz() {
                return scqydz;
            }

            public void setScqydz(String scqydz) {
                this.scqydz = scqydz;
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

            public void setCccyxqz(long cccyxqz) {
                this.cccyxqz = cccyxqz;
            }
            public long getCccyxqz() {
                return cccyxqz;
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

            public void setBcdy(String bcdy) {
                this.bcdy = bcdy;
            }
            public String getBcdy() {
                return bcdy;
            }

            public void setDdjxs(String ddjxs) {
                this.ddjxs = ddjxs;
            }
            public String getDdjxs() {
                return ddjxs;
            }

            public void setBcgl(String bcgl) {
                this.bcgl = bcgl;
            }
            public String getBcgl() {
                return bcgl;
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

            public void setZcbm(String zcbm) {
                this.zcbm = zcbm;
            }
            public String getZcbm() {
                return zcbm;
            }

            public void setHphm(String hphm) {
                this.hphm = hphm;
            }
            public String getHphm() {
                return hphm;
            }

        }
    }
}