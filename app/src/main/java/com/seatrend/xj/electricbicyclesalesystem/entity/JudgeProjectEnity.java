package com.seatrend.xj.electricbicyclesalesystem.entity;

/**
 * Created by ly on 2020/4/20 17:17
 */
public class JudgeProjectEnity {


    private String content; //内容
    private String order ="0"; // 决定  0不合格  1合格
//    private String csys ="红/绿"; // 车身颜色
//
//    public String getCsys() {
//        return csys;
//    }
//
//    public void setCsys(String csys) {
//        this.csys = csys;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
