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
import com.seatrend.xj.electricbicyclesalesystem.entity.CommonResponse;
import com.seatrend.xj.electricbicyclesalesystem.entity.PhotoEntity;
import com.seatrend.xj.electricbicyclesalesystem.entity.PhotoIdEnity;
import com.seatrend.xj.electricbicyclesalesystem.http.HttpService;
import com.seatrend.xj.electricbicyclesalesystem.util.GsonUtils;
import com.seatrend.xj.electricbicyclesalesystem.util.ObjectNullUtil;

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


    private void checkUpload() {
        List<PhotoEntity> list = CodeTableSQLiteUtils.queryAll();
        if (list.size() == 0) {
            Log.i(PUS_TAG, "数据库无照片");
            return;
        }
        Log.i(PUS_TAG, "数据库有照片 长度 =" + list.size());
        Log.i(PUS_TAG, "数据库有照片 内容 =" + GsonUtils.toJson(list));

        for (PhotoEntity entity : list) {

            Map<String, String> map = new HashMap();

            File file = new File(entity.getZpPath());
            String zpdz = entity.getZpdz();

            if (!file.exists()) {
                //如果改照片 不存在了，如 人为去本地删除，则不需要上传了 ，
                // 如果不删除，这条数据始终无法删除，就会累积，虽然发生几率很小，但还是做了一下判定
                if (!TextUtils.isEmpty(entity.getLsh()) && !TextUtils.isEmpty(entity.getZpzl())) {
                    CodeTableSQLiteUtils.deleteByLshAndDmz(entity.getLsh(), entity.getZpzl());
                }
                continue;
            }

            if (!TextUtils.isEmpty(entity.getZpPath())) {
                if (TextUtils.isEmpty(zpdz) || zpdz == "") {
                    map.put("type", entity.getLsh() + ":" + entity.getZpzl());
                    mUploadModule.uploadPhoto(map, file, Constants.Companion.getPHOTO_INSERT());
                } else { //不为空 就是参数完全
                    map.put(Constants.Companion.getS_LSH(), entity.getLsh());
                    map.put(Constants.Companion.getS_XH(), entity.getXh());
                    map.put(Constants.Companion.getS_ZPZL(), entity.getZpzl());
                    map.put(Constants.Companion.getS_ZPDZ(), entity.getZpdz());
                    map.put(Constants.Companion.getS_LRR(), entity.getLrr());
                    map.put(Constants.Companion.getS_LRBM(), entity.getLrbm());
                    map.put(Constants.Companion.getS_ZPSM(), entity.getZpsm());
                    map.put(Constants.Companion.getS_CFFS(), entity.getCffs());
                    mUploadModule.uploadPhoto(map, Constants.Companion.getPHOTO_MSG_SAVE());
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

    public class UploadModule extends BaseModule {
        @Override
        public void doWork(Map<String, String> map, String url) {

        }

        @Override
        public void doWorkResults(CommonResponse commonResponse, boolean isSuccess) {
            Log.i(PUS_TAG, "uploadResult " + isSuccess + "    " + commonResponse.getResponseString());
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
                        if (enity.getData().getType() != null && enity.getData().getType().split(":").length == 2) {
                            CodeTableSQLiteUtils.updateByLshAndDmz(enity.getData().getType().split(":")[0], enity.getData().getType().split(":")[1], id);
                        }
                    }

                    if (Constants.Companion.getPHOTO_MSG_SAVE().equals(commonResponse.getUrl())) {
                        //上传图片保存信息成功 要删除本地的zpPath
                        Log.d("lylog", "[Service]  result = " + commonResponse.getResponseString());

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
        try {
            timer.cancel();
            timerTask.cancel();
        } catch (Exception e) {
            e.getMessage();
        }


    }
}
