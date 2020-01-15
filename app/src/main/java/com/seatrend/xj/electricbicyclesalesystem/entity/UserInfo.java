package com.seatrend.xj.electricbicyclesalesystem.entity;

/**
 * Created by seatrend on 2018/12/28.
 */

public class UserInfo {

    public static String XM;//姓名
    public static String XSDDM;//销售点代码(glbm)
    public static String SFZMHM;//身份证明号码(user sfzmhm)
    public static String GLBM;//管理部门 (user)
    public static String TOKEN="";//token
//    public static String YHLX="0";//0是 厂商   1是销售商
    public static String YHDH;//用户代号(user sfzmhm)
    public static String BMQC;//部门QC(bmqc)
    public static String BMMC;//部门名称(bmqc)
    public static String JSLX;//角色类型   1 车管部门 2 服务站 3 经销商
    public static String FZJG;//发证机关

    //上级部门
    public static class NewUserInfo{
        public static String GLBM;//管理部门 (user)
        public static String BMQC;//部门QC(bmqc)
        public static String BMMC;//部门MC(bmmc)
    }

    //全局参数配置
    public static class GlobalParameter{
        public static String BXBJ;//保险(0:不启用,1:启用)
        public static String DPBJ;//带牌销售(0:否,1:是)
        public static String SFBJ;//收费验证(0:不启用,1:启用)
        public static String CFBJ;//存放方式(0-oracle,1-fastdfs,2-MongoDB )
        public static String LQBJ;//领取方式(0:不领取,1:现场领取,2:邮寄领取)
        public static String SCBJ;//是否开起查验实时检测数据录入(0:不启用,1:启用)
    }
}

