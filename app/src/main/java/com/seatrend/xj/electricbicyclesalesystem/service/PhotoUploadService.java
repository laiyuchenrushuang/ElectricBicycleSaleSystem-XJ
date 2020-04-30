package com.seatrend.xj.electricbicyclesalesystem.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.seatrend.xj.electricbicyclesalesystem.common.BaseModule;
import com.seatrend.xj.electricbicyclesalesystem.common.Constants;
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils;
import com.seatrend.xj.electricbicyclesalesystem.entity.CodeEntity;
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse;
import com.seatrend.xj.electricbicyclesalesystem.entity.PhotoEntity;
import com.seatrend.xj.electricbicyclesalesystem.entity.PhotoIdEnity;
import com.seatrend.xj.electricbicyclesalesystem.entity.PhotoSaveEmployeeEntity;
import com.seatrend.xj.electricbicyclesalesystem.entity.PhotoSaveEntity;
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService;
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils;
import com.seatrend.xj.electricbicyclesalesystem.util.ObjectNullUtil;
import com.seatrend.xj.electricbicyclesalesystem.util.PhotoFileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PhotoUploadService extends Service {


    private Timer timer = new Timer();
    private static final int PERIOD = 10 * 1000;
    private static final String PUS_TAG = "PhotoUploadService";
    private UploadModule mUploadModule = new UploadModule();
    private static final int CHECK_UPLOAD = 1009;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer.schedule(timerTask, 0, PERIOD);
        Log.i(PUS_TAG, "上传照片服务已创建");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(PUS_TAG, "上传照片服务已启动");
        return super.onStartCommand(intent, flags, startId);
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHECK_UPLOAD:
                    checkUpload();

                    break;
            }
        }
    };
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(CHECK_UPLOAD);
            // 定时 发送 照片 轮循 广播，也就是上传完一次，发送一次，当然也存在无照片上传的情况，但不影响
            sendPhotoUploadBroadcast();
        }
    };

    /**
     * Why kill the current service here "stopSelf()", because the program design does not want the service to be open all the time,
     * which is not friendly to the phone performance of the customer, when the program detects that there is no picture
     * upload business, then kill the service process, then when to start? Each Activity has a parent, when open each
     * interface, go to monitor whether the service is running, if the current service in running, so will not open
     * service, if the current service closed and will open services, to upload photos, interface must be changed,
     * the service is open, when the service open database to detect whether there is need to upload photos, if you
     * have a photo to upload, if no photo, close the current service, have a task, upload service open, task,
     * service shut down.
     */

    private void checkUpload() {
        List<PhotoEntity> list = CodeTableSQLiteUtils.queryAll();
        if (list.size() == 0) {
            Log.i(PUS_TAG, "数据库无照片");
            stopSelf();
            return;
        }
        Log.i(PUS_TAG, "数据库有照片 长度 =" + list.size());
        Log.i(PUS_TAG, "数据库有照片 内容 =" + GsonUtils.toJson(list));

        for (PhotoEntity entity : list) {

            String path = entity.getZpPath();
            File file = new File(path);
            String zpdz = entity.getZpdz();

            if (!file.exists()) {

                if(checkIsYgPicture(entity.getZpzl())){
                    if (!TextUtils.isEmpty(entity.getSfz()) && !TextUtils.isEmpty(entity.getZpzl())) {
                        CodeTableSQLiteUtils.deleteBySfzAndDmz(entity.getSfz(), entity.getZpzl());
                    }
                }else {
                    if (!TextUtils.isEmpty(entity.getLsh()) && !TextUtils.isEmpty(entity.getZpzl())) {
                        CodeTableSQLiteUtils.deleteByLshAndDmz(entity.getLsh(), entity.getZpzl());
                    }
                }
                continue;
            }

            if (!TextUtils.isEmpty(entity.getZpPath())) {
                if (TextUtils.isEmpty(zpdz) || zpdz.equals("")) {

                    if (checkIsYgPicture(entity.getZpzl())) {

                        // 员工备案的照片
                        Map<String, String> map = new HashMap();

                        map.put("type", entity.getSfz() + ":" + entity.getZpzl() +":"+entity.getZpPath());  //返回身份证和照片种类 + 照片path 方便删除
                        mUploadModule.uploadPhoto(map, file, Constants.Companion.getPHOTO_INSERT());
                    } else {

                        // 查验业务类照片
                        Map<String, String> map = new HashMap();

                        map.put("type", entity.getLsh() + ":" + entity.getZpzl()+":"+entity.getZpPath());  //返回流水号和照片种类 + 照片path 方便删除
                        mUploadModule.uploadPhoto(map, file, Constants.Companion.getPHOTO_INSERT());
                    }

                } else { //不为空 就是参数完全 上传保存

                    if (checkIsYgPicture(entity.getZpzl())) {

                        // 存储员工备案的照片
                        Map<String, String> map = new HashMap();
                        map.put(Constants.Companion.getS_SFZ(), entity.getSfz());
                        map.put(Constants.Companion.getS_ZPZL(), entity.getZpzl());
                        map.put(Constants.Companion.getS_ZPDZ(), entity.getZpdz());
                        map.put(Constants.Companion.getS_LRR(), entity.getLrr());
                        map.put(Constants.Companion.getS_LRBM(), entity.getLrbm());
                        map.put(Constants.Companion.getS_ZPSM(), entity.getZpsm());
                        map.put(Constants.Companion.getS_CFFS(), entity.getCffs());
//                        map.put(Constants.Companion.getS_ZPPATH(), entity.getZpPath());
                        mUploadModule.uploadPhoto(map, Constants.Companion.getYG_PHOTO_SAVE()); // YG
                    } else {
                        // 存储查验业务类照片
                        Map<String, String> map = new HashMap();
                        map.put(Constants.Companion.getS_LSH(), entity.getLsh());
                        map.put(Constants.Companion.getS_XH(), entity.getXh());
                        map.put(Constants.Companion.getS_ZPZL(), entity.getZpzl());
                        map.put(Constants.Companion.getS_ZPDZ(), entity.getZpdz());
                        map.put(Constants.Companion.getS_LRR(), entity.getLrr());
                        map.put(Constants.Companion.getS_LRBM(), entity.getLrbm());
                        map.put(Constants.Companion.getS_ZPSM(), entity.getZpsm());
                        map.put(Constants.Companion.getS_CFFS(), entity.getCffs());
//                        map.put(Constants.Companion.getS_ZPPATH(), entity.getZpPath());
                        Log.i(PUS_TAG, "  map  post = "+GsonUtils.toJson(map));
                        mUploadModule.uploadPhoto(map, Constants.Companion.getPHOTO_MSG_SAVE()); // YW &  CY
                    }
                }
            }
        }
        list.clear();
    }


    private void sendPhotoUploadBroadcast() {
        //每上传完一次，发送一次照片删上传完成广播，有需要可监听这个广播，做一些逻辑处理，比如说 刷新界面等
        Intent intent = new Intent();
        intent.setAction(Constants.Companion.getPTOTO_UPLOAD_ACTION());
        intent.putExtra(Constants.Companion.getDATA(), "upload photo ok,please update you need UI");
        sendBroadcast(intent);
    }

    //根据照片类型判断是否是员工照片
    private Boolean checkIsYgPicture(String zplx) {
        List<CodeEntity.DataBean> list = CodeTableSQLiteUtils.queryByDMLB(Constants.Companion.getYGZP());
        for (CodeEntity.DataBean db : list) {
            if (zplx != null && zplx.equals(db.getDmz())) {
                return true;
            }
        }
        return false;
    }

    public class UploadModule extends BaseModule {
        @Override
        public void doWork(Map<String, String> map, String url) {

        }

        @Override
        public void doWorkResults(CommonResponse commonResponse, boolean isSuccess) {
            Log.i(PUS_TAG, "uploadResult " + isSuccess + "    "+" URL = "+commonResponse.getUrl()+ "    result = " + commonResponse.getResponseString());
            if (isSuccess)
                try {

                    if (Constants.Companion.getPHOTO_INSERT().equals(commonResponse.getUrl())) {
                        PhotoIdEnity enity = GsonUtils.gson(commonResponse.getResponseString(), PhotoIdEnity.class);
                        // 证明已经获取到id

                        if (!ObjectNullUtil.checknull(enity.getData(), enity.getData().getType(), enity.getData().getId())) {
                            Log.d("lylog", "[Service]  获取照片id异常");
                            return;
                        }
                        String id = enity.getData().getId();

                        if (enity.getData().getType() != null && enity.getData().getType().split(":").length == 3) {
                            String s1 = enity.getData().getType().split(":")[0]; // ( sfz or lsh)
                            String s2 = enity.getData().getType().split(":")[1]; // 照片种类
                            String s3 = enity.getData().getType().split(":")[2]; // 照片path
                            if(checkIsYgPicture(s2)){ // 员工照片存储
                                CodeTableSQLiteUtils.updateBySfzAndDmz(s1, s2,id);
                            }else {  // 查验和业务
                                CodeTableSQLiteUtils.updateByLshAndDmz(s1, s2,id);
                            }

                            //服务器的id已经获取，删除本地照片 防止oom
//                            PhotoFileUtils.deleteFile(new File(s3));

                        }


                    }

                    if (Constants.Companion.getPHOTO_MSG_SAVE().equals(commonResponse.getUrl())) {
                        //查验和注册保存成功返回
                        PhotoSaveEntity entity = GsonUtils.gson(commonResponse.getResponseString(),PhotoSaveEntity.class);//业务查验实体类


                        //删除本地文件
                        PhotoFileUtils.deleteFile(CodeTableSQLiteUtils.queryLshAndDmz(entity.getData().getLsh(),entity.getData().getZplx()));

                        //删除数据库
                        CodeTableSQLiteUtils.deleteByLshAndDmz(entity.getData().getLsh(), entity.getData().getZplx());

                    }
                    if (Constants.Companion.getYG_PHOTO_SAVE().equals(commonResponse.getUrl())) {
                        // 员工备案保存成功返回
                        PhotoSaveEmployeeEntity entity = GsonUtils.gson(commonResponse.getResponseString(), PhotoSaveEmployeeEntity.class); //员工实体类

                        Log.d(PUS_TAG," sfzhm = "+entity.getData().getSfzhm());
                        Log.d(PUS_TAG," zplx = "+entity.getData().getZplx());
                        //删除本地文件
                        PhotoFileUtils.deleteFile(CodeTableSQLiteUtils.querySfzAndDmz(entity.getData().getSfzhm(),entity.getData().getZplx()));

                        //删除数据库
                        CodeTableSQLiteUtils.deleteBySfzAndDmz(entity.getData().getSfzhm(), entity.getData().getZplx());
                    }

                } catch (Exception e) {
                    Log.i(PUS_TAG, "Exception " + e.getMessage());
                }

        }

        public void uploadPhoto(Map<String, String> map, File file, String url) {
            HttpService.getInstance().uploadFileToServer(url, file, map, this);
        }


        public void uploadPhoto(Map<String, String> map, String url) {
            HttpService.getInstance().getDataFromServer(map, url, Constants.Companion.getPOST(), this);
            //HttpService.getInstance().uploadFileByURL(map,file,url,Constants.GET,this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
        Log.i(PUS_TAG, "SERVICE　OFF");
        try {
            timer.cancel();
            timerTask.cancel();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
