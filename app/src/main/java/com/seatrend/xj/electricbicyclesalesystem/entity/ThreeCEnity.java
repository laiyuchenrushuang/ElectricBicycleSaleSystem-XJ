package com.seatrend.xj.electricbicyclesalesystem.entity;

//{"status":true,"code":0,"message":"成功","data":{"threeCertificates":{"code":"0000","data":{"image":null,"productModel":"TDT309Z","certState":"有效","color":"中华红/亮黑","trademarkCn":"玫瑰之约","length":"1470","weight":"53.2","maxSpeed":"24.0","qualificationCode":"A11265319T3090100","codeOnFrame":"打刻于头管右侧","vehicleManufacturer":"成都当肯车业有限公司","certcode":"2019011119189128","fixedPositionName":"粘贴在鞍管上","certExpiryDate":"2024-05-27","continuousMileage":"35.0","width":"592","manufacturingDate":"2019-09-19","height":"1008"},"message":null}},"total":0}
public class ThreeCEnity extends BaseEntity {

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

        private ThreeCertificates threeCertificates;

        public void setThreeCertificates(ThreeCertificates threeCertificates) {
            this.threeCertificates = threeCertificates;
        }

        public ThreeCertificates getThreeCertificates() {
            return threeCertificates;
        }

        public class ThreeCertificates {

            private String code;
            private Data1 data;
            private String message;

            public void setCode(String code) {
                this.code = code;
            }

            public String getCode() {
                return code;
            }

            public void setData(Data1 data) {
                this.data = data;
            }

            public Data1 getData() {
                return data;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getMessage() {
                return message;
            }

            public class Data1 {

                private String image;
                private String productModel;
                private String certState;
                private String color;
                private String trademarkCn;
                private String length;
                private String weight;
                private String maxSpeed;
                private String qualificationCode;
                private String codeOnFrame;
                private String vehicleManufacturer;
                private String certcode;
                private String fixedPositionName;
                private String certExpiryDate;
                private String continuousMileage;
                private String width;
                private String manufacturingDate;
                private String height;

                public void setImage(String image) {
                    this.image = image;
                }

                public String getImage() {
                    return image;
                }

                public void setProductModel(String productModel) {
                    this.productModel = productModel;
                }

                public String getProductModel() {
                    return productModel;
                }

                public void setCertState(String certState) {
                    this.certState = certState;
                }

                public String getCertState() {
                    return certState;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public String getColor() {
                    return color;
                }

                public void setTrademarkCn(String trademarkCn) {
                    this.trademarkCn = trademarkCn;
                }

                public String getTrademarkCn() {
                    return trademarkCn;
                }

                public void setLength(String length) {
                    this.length = length;
                }

                public String getLength() {
                    return length;
                }

                public void setWeight(String weight) {
                    this.weight = weight;
                }

                public String getWeight() {
                    return weight;
                }

                public void setMaxSpeed(String maxSpeed) {
                    this.maxSpeed = maxSpeed;
                }

                public String getMaxSpeed() {
                    return maxSpeed;
                }

                public void setQualificationCode(String qualificationCode) {
                    this.qualificationCode = qualificationCode;
                }

                public String getQualificationCode() {
                    return qualificationCode;
                }

                public void setCodeOnFrame(String codeOnFrame) {
                    this.codeOnFrame = codeOnFrame;
                }

                public String getCodeOnFrame() {
                    return codeOnFrame;
                }

                public void setVehicleManufacturer(String vehicleManufacturer) {
                    this.vehicleManufacturer = vehicleManufacturer;
                }

                public String getVehicleManufacturer() {
                    return vehicleManufacturer;
                }

                public void setCertcode(String certcode) {
                    this.certcode = certcode;
                }

                public String getCertcode() {
                    return certcode;
                }

                public void setFixedPositionName(String fixedPositionName) {
                    this.fixedPositionName = fixedPositionName;
                }

                public String getFixedPositionName() {
                    return fixedPositionName;
                }

                public void setCertExpiryDate(String certExpiryDate) {
                    this.certExpiryDate = certExpiryDate;
                }

                public String getCertExpiryDate() {
                    return certExpiryDate;
                }

                public void setContinuousMileage(String continuousMileage) {
                    this.continuousMileage = continuousMileage;
                }

                public String getContinuousMileage() {
                    return continuousMileage;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getWidth() {
                    return width;
                }

                public void setManufacturingDate(String manufacturingDate) {
                    this.manufacturingDate = manufacturingDate;
                }

                public String getManufacturingDate() {
                    return manufacturingDate;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getHeight() {
                    return height;
                }

            }
        }
    }
}


