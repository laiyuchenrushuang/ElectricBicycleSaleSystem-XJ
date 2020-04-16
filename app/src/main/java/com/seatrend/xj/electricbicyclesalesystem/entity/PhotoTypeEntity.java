package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.List;

/**
 * Created by seatrend on 2019/5/24.
 */

public class PhotoTypeEntity extends BaseEntity {


    /**
     * data : {"ownerConfig":[{"ywxh":"2","xmxh":"1","pzlx":"A","sfbt":"1","zmmc":"身份证反面（人像面）","zplx":"21A3"},{"ywxh":"2","xmxh":"1","pzlx":"A","sfbt":"1","zmmc":"居住证明","zplx":"21A2"},{"ywxh":"2","xmxh":"1","pzlx":"A","sfbt":"1","zmmc":"身份证正面（国徽面）","zplx":"21A1"}],"insuranceConfig":[{"ywxh":"2","xmxh":"6","pzlx":"1","sfbt":"1","zmmc":"人保财险","zplx":"2611"},{"ywxh":"2","xmxh":"6","pzlx":"1","sfbt":"1","zmmc":"天安保险","zplx":"2612"}],"originConfig":[{"ywxh":"2","xmxh":"2","pzlx":"A","sfbt":"1","zmmc":"销售发票","zplx":"22A3"},{"ywxh":"2","xmxh":"2","pzlx":"A","sfbt":"1","zmmc":"国外发票","zplx":"22A2"},{"ywxh":"2","xmxh":"2","pzlx":"A","sfbt":"1","zmmc":"国外发票翻译文本","zplx":"22A1"}],"agentConfig":[{"ywxh":"2","xmxh":"3","pzlx":"C","sfbt":"1","zmmc":"军官证","zplx":"23C1"},{"ywxh":"2","xmxh":"3","pzlx":"C","sfbt":"1","zmmc":"文职干部证","zplx":"23C3"},{"ywxh":"2","xmxh":"3","pzlx":"C","sfbt":"1","zmmc":"住所证明","zplx":"23C2"}],"config":[{"ywxh":"2","xmxh":"4","sfxs":"1","sfbt":"1","zmmc":"合格证","bz":null,"zplx":"24"},{"ywxh":"2","xmxh":"5","sfxs":"1","sfbt":"1","zmmc":"行驶证","bz":null,"zplx":"25"},{"ywxh":"2","xmxh":"7","sfxs":"1","sfbt":"1","zmmc":"寄递信息","bz":null,"zplx":"27"}]}
     * total : 0
     */

    private DataBean data;
    private int total;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class DataBean {
        private List<ConfigBean> ownerConfig;
        private List<ConfigBean> insuranceConfig;
        private List<ConfigBean> originConfig;
        private List<ConfigBean> agentConfig;
        private List<ConfigBean> config;

        public List<ConfigBean> getOwnerConfig() {
            return ownerConfig;
        }

        public void setOwnerConfig(List<ConfigBean> ownerConfig) {
            this.ownerConfig = ownerConfig;
        }

        public List<ConfigBean> getInsuranceConfig() {
            return insuranceConfig;
        }

        public void setInsuranceConfig(List<ConfigBean> insuranceConfig) {
            this.insuranceConfig = insuranceConfig;
        }

        public List<ConfigBean> getOriginConfig() {
            return originConfig;
        }

        public void setOriginConfig(List<ConfigBean> originConfig) {
            this.originConfig = originConfig;
        }

        public List<ConfigBean> getAgentConfig() {
            return agentConfig;
        }

        public void setAgentConfig(List<ConfigBean> agentConfig) {
            this.agentConfig = agentConfig;
        }

        public List<ConfigBean> getConfig() {
            return config;
        }

        public void setConfig(List<ConfigBean> config) {
            this.config = config;
        }


        public static class ConfigBean {
            /**
             * ywxh : 2
             * xmxh : 4
             * sfxs : 1
             * sfbt : 1
             * zmmc : 合格证
             * bz : null
             * zplx : 24
             */

            private String ywxh;
            private String xmxh;
            private String sfxs;
            private String sfbt;
            private String zmmc;
            private Object bz;
            private String zplx;
            private String zplj; //照片路径
            private String zpPath; //照片加载路径
            private String clipped = "0"; //照片需要裁剪的是否被裁剪（ZCBM 类型的图片）  默认的所有没被裁剪 (0 不需要  1 需要)
            private String takeMode = "0"; //照片是否多拍 (0 不是  1 是)   0 必拍      1 多拍

            public String getTakeMode() {
                return takeMode;
            }

            public void setTakeMode(String takeMode) {
                this.takeMode = takeMode;
            }

            public String isClipped() {
                return clipped;
            }

            public void setClipped(String clipped) {
                this.clipped = clipped;
            }

            public void setZpPath(String zpPath) {
                this.zpPath = zpPath;
            }

            public String getZpPath() {
                return zpPath;
            }

            public String getZplj() {
                return zplj;
            }

            public void setZplj(String zplj) {
                this.zplj = zplj;
            }

            public String getYwxh() {
                return ywxh;
            }

            public void setYwxh(String ywxh) {
                this.ywxh = ywxh;
            }

            public String getXmxh() {
                return xmxh;
            }

            public void setXmxh(String xmxh) {
                this.xmxh = xmxh;
            }

            public String getSfxs() {
                return sfxs;
            }

            public void setSfxs(String sfxs) {
                this.sfxs = sfxs;
            }

            public String getSfbt() {
                return sfbt;
            }

            public void setSfbt(String sfbt) {
                this.sfbt = sfbt;
            }

            public String getZmmc() {
                return zmmc;
            }

            public void setZmmc(String zmmc) {
                this.zmmc = zmmc;
            }

            public Object getBz() {
                return bz;
            }

            public void setBz(Object bz) {
                this.bz = bz;
            }

            public String getZplx() {
                return zplx;
            }

            public void setZplx(String zplx) {
                this.zplx = zplx;
            }
        }
    }
}
