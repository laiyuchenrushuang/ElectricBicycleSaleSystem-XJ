package com.seatrend.xj.electricbicyclesalesystem.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2019/11/13 10:02
 */
public class DjLshEnity extends BaseEntity implements Parcelable {

    /**
     * data : {"lsh":"200506000220","xh":"9000000220","photo":[{"dmz":"A","dmsm":"申请表"},{"dmz":"BA","dmsm":"所有人身份证明（正面）"},{"dmz":"BB","dmsm":"所有人身份证明（反面）"},{"dmz":"BD","dmsm":"所有人现场照片"},{"dmz":"C","dmsm":null},{"dmz":"D","dmsm":"合格证"},{"dmz":"E","dmsm":"查验记录表"},{"dmz":"KA","dmsm":"号牌号码"},{"dmz":"99","dmsm":"其他"}],"error":"1"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * lsh : 200506000220
         * xh : 9000000220
         * photo : [{"dmz":"A","dmsm":"申请表"},{"dmz":"BA","dmsm":"所有人身份证明（正面）"},{"dmz":"BB","dmsm":"所有人身份证明（反面）"},{"dmz":"BD","dmsm":"所有人现场照片"},{"dmz":"C","dmsm":null},{"dmz":"D","dmsm":"合格证"},{"dmz":"E","dmsm":"查验记录表"},{"dmz":"KA","dmsm":"号牌号码"},{"dmz":"99","dmsm":"其他"}]
         * error : 1
         */

        private String lsh;
        private String xh;
        private String error;
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

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public List<PhotoBean> getPhoto() {
            return photo;
        }

        public void setPhoto(List<PhotoBean> photo) {
            this.photo = photo;
        }

        public static class PhotoBean implements Parcelable {
            /**
             * dmz : A
             * dmsm : 申请表
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
            dest.writeString(this.error);
            dest.writeList(this.photo);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.lsh = in.readString();
            this.xh = in.readString();
            this.error = in.readString();
            this.photo = new ArrayList<PhotoBean>();
            in.readList(this.photo, PhotoBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
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
    }

    public DjLshEnity() {
    }

    protected DjLshEnity(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<DjLshEnity> CREATOR = new Parcelable.Creator<DjLshEnity>() {
        @Override
        public DjLshEnity createFromParcel(Parcel source) {
            return new DjLshEnity(source);
        }

        @Override
        public DjLshEnity[] newArray(int size) {
            return new DjLshEnity[size];
        }
    };
}
