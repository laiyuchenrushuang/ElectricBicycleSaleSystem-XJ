package com.seatrend.xj.electricbicyclesalesystem.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2020/5/27 16:39
 */
public class ZpbpEntity extends BaseEntity implements Parcelable {

    /**
     * data : {"lsh":"200527000250","xh":"9000000250","photo":[{"dmz":"D","dmsm":"合格证"},{"dmz":"HA","dmsm":"车辆识别代号拓印膜"},{"dmz":"HB","dmsm":"整车编码照片"},{"dmz":"I","dmsm":"车辆标准照片"},{"dmz":"J","dmsm":"前后中心轮距"}]}
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

    public static class DataBean implements Parcelable {
        /**
         * lsh : 200527000250
         * xh : 9000000250
         * photo : [{"dmz":"D","dmsm":"合格证"},{"dmz":"HA","dmsm":"车辆识别代号拓印膜"},{"dmz":"HB","dmsm":"整车编码照片"},{"dmz":"I","dmsm":"车辆标准照片"},{"dmz":"J","dmsm":"前后中心轮距"}]
         */

        private String lsh;
        private String xh;
        private List<PhotoBean> photo;

        public String getLsh() {
            return lsh;
        }

        public void setLsh(String lsh) {
            this.lsh = lsh;
        }

        public String getXh() {
            return xh;
        }

        public void setXh(String xh) {
            this.xh = xh;
        }

        public List<PhotoBean> getPhoto() {
            return photo;
        }

        public void setPhoto(List<PhotoBean> photo) {
            this.photo = photo;
        }

        public static class PhotoBean implements Parcelable {
            /**
             * dmz : D
             * dmsm : 合格证
             */

            private String dmz;
            private String dmsm;

            public String getDmz() {
                return dmz;
            }

            public void setDmz(String dmz) {
                this.dmz = dmz;
            }

            public String getDmsm() {
                return dmsm;
            }

            public void setDmsm(String dmsm) {
                this.dmsm = dmsm;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.dmz);
                dest.writeString(this.dmsm);
            }

            public PhotoBean() {
            }

            protected PhotoBean(Parcel in) {
                this.dmz = in.readString();
                this.dmsm = in.readString();
            }

            public static final Creator<PhotoBean> CREATOR = new Creator<PhotoBean>() {
                @Override
                public PhotoBean createFromParcel(Parcel source) {
                    return new PhotoBean(source);
                }

                @Override
                public PhotoBean[] newArray(int size) {
                    return new PhotoBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.lsh);
            dest.writeString(this.xh);
            dest.writeList(this.photo);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.lsh = in.readString();
            this.xh = in.readString();
            this.photo = new ArrayList<PhotoBean>();
            in.readList(this.photo, PhotoBean.class.getClassLoader());
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
        dest.writeInt(this.total);
    }

    public ZpbpEntity() {
    }

    protected ZpbpEntity(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.total = in.readInt();
    }

    public static final Parcelable.Creator<ZpbpEntity> CREATOR = new Parcelable.Creator<ZpbpEntity>() {
        @Override
        public ZpbpEntity createFromParcel(Parcel source) {
            return new ZpbpEntity(source);
        }

        @Override
        public ZpbpEntity[] newArray(int size) {
            return new ZpbpEntity[size];
        }
    };
}
