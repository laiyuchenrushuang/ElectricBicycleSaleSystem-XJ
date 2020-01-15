package com.seatrend.xj.electricbicyclesalesystem.entity;

import java.util.List;

/**
 * Created by ly on 2019/11/7 20:42
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class PayPostEnity {

    private int amount;
    private String orderNo;
    private String desc;
    private String certNo;
    private List<FeeDatas> feeDatas;
    private int money;
    private String remark1;
    private String enterCode;
    private String remark3;
    private String payerName;
    private String remark2;

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setFeeDatas(List<FeeDatas> feeDatas) {
        this.feeDatas = feeDatas;
    }

    public List<FeeDatas> getFeeDatas() {
        return feeDatas;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setEnterCode(String enterCode) {
        this.enterCode = enterCode;
    }

    public String getEnterCode() {
        return enterCode;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark2() {
        return remark2;
    }

    public static class FeeDatas {

        private int amount;
        private int price;
        private String busCode;

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }

        public void setBusCode(String busCode) {
            this.busCode = busCode;
        }

        public String getBusCode() {
            return busCode;
        }

    }
}
