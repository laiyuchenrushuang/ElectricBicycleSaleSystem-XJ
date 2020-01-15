package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.List;

/**
 * Created by ly on 2019/8/16 11:28
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class AllLizmBean {
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

    public class Data {

        private List<OwnerConfig> ownerConfig;
        private List<InsuranceConfig> insuranceConfig;
        private List<OriginConfig> originConfig;
        private List<AgentConfig> agentConfig;
        private List<Config> config;

        public void setOwnerConfig(List<OwnerConfig> ownerConfig) {
            this.ownerConfig = ownerConfig;
        }

        public List<OwnerConfig> getOwnerConfig() {
            return ownerConfig;
        }

        public void setInsuranceConfig(List<InsuranceConfig> insuranceConfig) {
            this.insuranceConfig = insuranceConfig;
        }

        public List<InsuranceConfig> getInsuranceConfig() {
            return insuranceConfig;
        }

        public void setOriginConfig(List<OriginConfig> originConfig) {
            this.originConfig = originConfig;
        }

        public List<OriginConfig> getOriginConfig() {
            return originConfig;
        }

        public void setAgentConfig(List<AgentConfig> agentConfig) {
            this.agentConfig = agentConfig;
        }

        public List<AgentConfig> getAgentConfig() {
            return agentConfig;
        }

        public void setConfig(List<Config> config) {
            this.config = config;
        }

        public List<Config> getConfig() {
            return config;
        }

        public class Config {

            private String ywxh;
            private String xmxh;
            private String sfxs;
            private String sfbt;
            private String zmmc;
            private String bz;
            private String zplx;
            public void setYwxh(String ywxh) {
                this.ywxh = ywxh;
            }
            public String getYwxh() {
                return ywxh;
            }

            public void setXmxh(String xmxh) {
                this.xmxh = xmxh;
            }
            public String getXmxh() {
                return xmxh;
            }

            public void setSfxs(String sfxs) {
                this.sfxs = sfxs;
            }
            public String getSfxs() {
                return sfxs;
            }

            public void setSfbt(String sfbt) {
                this.sfbt = sfbt;
            }
            public String getSfbt() {
                return sfbt;
            }

            public void setZmmc(String zmmc) {
                this.zmmc = zmmc;
            }
            public String getZmmc() {
                return zmmc;
            }

            public void setBz(String bz) {
                this.bz = bz;
            }
            public String getBz() {
                return bz;
            }

            public void setZplx(String zplx) {
                this.zplx = zplx;
            }
            public String getZplx() {
                return zplx;
            }

        }

        public class AgentConfig {

            private String ywxh;
            private String xmxh;
            private String pzlx;
            private String sfbt;
            private String zmmc;
            private String zplx;
            public void setYwxh(String ywxh) {
                this.ywxh = ywxh;
            }
            public String getYwxh() {
                return ywxh;
            }

            public void setXmxh(String xmxh) {
                this.xmxh = xmxh;
            }
            public String getXmxh() {
                return xmxh;
            }

            public void setPzlx(String pzlx) {
                this.pzlx = pzlx;
            }
            public String getPzlx() {
                return pzlx;
            }

            public void setSfbt(String sfbt) {
                this.sfbt = sfbt;
            }
            public String getSfbt() {
                return sfbt;
            }

            public void setZmmc(String zmmc) {
                this.zmmc = zmmc;
            }
            public String getZmmc() {
                return zmmc;
            }

            public void setZplx(String zplx) {
                this.zplx = zplx;
            }
            public String getZplx() {
                return zplx;
            }

        }

        public class OriginConfig {

            private String ywxh;
            private String xmxh;
            private String pzlx;
            private String sfbt;
            private String zmmc;
            private String zplx;
            public void setYwxh(String ywxh) {
                this.ywxh = ywxh;
            }
            public String getYwxh() {
                return ywxh;
            }

            public void setXmxh(String xmxh) {
                this.xmxh = xmxh;
            }
            public String getXmxh() {
                return xmxh;
            }

            public void setPzlx(String pzlx) {
                this.pzlx = pzlx;
            }
            public String getPzlx() {
                return pzlx;
            }

            public void setSfbt(String sfbt) {
                this.sfbt = sfbt;
            }
            public String getSfbt() {
                return sfbt;
            }

            public void setZmmc(String zmmc) {
                this.zmmc = zmmc;
            }
            public String getZmmc() {
                return zmmc;
            }

            public void setZplx(String zplx) {
                this.zplx = zplx;
            }
            public String getZplx() {
                return zplx;
            }

        }

        public class InsuranceConfig {

            private String ywxh;
            private String xmxh;
            private String pzlx;
            private String sfbt;
            private String zmmc;
            private String zplx;
            public void setYwxh(String ywxh) {
                this.ywxh = ywxh;
            }
            public String getYwxh() {
                return ywxh;
            }

            public void setXmxh(String xmxh) {
                this.xmxh = xmxh;
            }
            public String getXmxh() {
                return xmxh;
            }

            public void setPzlx(String pzlx) {
                this.pzlx = pzlx;
            }
            public String getPzlx() {
                return pzlx;
            }

            public void setSfbt(String sfbt) {
                this.sfbt = sfbt;
            }
            public String getSfbt() {
                return sfbt;
            }

            public void setZmmc(String zmmc) {
                this.zmmc = zmmc;
            }
            public String getZmmc() {
                return zmmc;
            }

            public void setZplx(String zplx) {
                this.zplx = zplx;
            }
            public String getZplx() {
                return zplx;
            }

        }

        public class OwnerConfig {

            private String ywxh;
            private String xmxh;
            private String pzlx;
            private String sfbt;
            private String zmmc;
            private String zplx;
            public void setYwxh(String ywxh) {
                this.ywxh = ywxh;
            }
            public String getYwxh() {
                return ywxh;
            }

            public void setXmxh(String xmxh) {
                this.xmxh = xmxh;
            }
            public String getXmxh() {
                return xmxh;
            }

            public void setPzlx(String pzlx) {
                this.pzlx = pzlx;
            }
            public String getPzlx() {
                return pzlx;
            }

            public void setSfbt(String sfbt) {
                this.sfbt = sfbt;
            }
            public String getSfbt() {
                return sfbt;
            }

            public void setZmmc(String zmmc) {
                this.zmmc = zmmc;
            }
            public String getZmmc() {
                return zmmc;
            }

            public void setZplx(String zplx) {
                this.zplx = zplx;
            }
            public String getZplx() {
                return zplx;
            }

        }
    }

}
