package com.seatrend.xj.electricbicyclesalesystem.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2020/5/6 17:16
 */
public class ServicePhotoCamebackEnity extends BaseEntity implements Parcelable {

    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean implements Parcelable {
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

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.dmz = in.readString();
            this.dmsm = in.readString();
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
        dest.writeList(this.data);
    }

    public ServicePhotoCamebackEnity() {
    }

    protected ServicePhotoCamebackEnity(Parcel in) {
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ServicePhotoCamebackEnity> CREATOR = new Parcelable.Creator<ServicePhotoCamebackEnity>() {
        @Override
        public ServicePhotoCamebackEnity createFromParcel(Parcel source) {
            return new ServicePhotoCamebackEnity(source);
        }

        @Override
        public ServicePhotoCamebackEnity[] newArray(int size) {
            return new ServicePhotoCamebackEnity[size];
        }
    };
}
