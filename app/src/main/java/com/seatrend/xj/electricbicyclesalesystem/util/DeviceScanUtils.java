package com.seatrend.xj.electricbicyclesalesystem.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.device.scanner.configuration.PropertyID;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.widget.Toast;


import com.seatrend.xj.electricbicyclesalesystem.R;

import java.io.File;

/**
 * Created by seatrend on 2018/11/14.
 */

public class DeviceScanUtils {

    public boolean isReceiver = false;//"i6200 Series";//这个机型是有二维码扫描模块
    private ScanManager mScanManager;//"i6200 Series";//这个机型是有二维码扫描模块
    private SoundPool soundpool = null;//"i6200 Series";//这个机型是有二维码扫描模块
    private int soundid;//"i6200 Series";//这个机型是有二维码扫描模块
    private final static String SCAN_ACTION = ScanManager.ACTION_DECODE;//"i6200 Series";//这个机型是有二维码扫描模块
    private Vibrator mVibrator;//"i6200 Series";//这个机型是有二维码扫描模块
    //protected boolean isScaning = false;
    private String MODEL = "HC";
    private final String IDCARDIMAGEPATH = Environment.getExternalStorageDirectory().getPath() + "/scanManger";
    private String currentPhoneModel; //当前手机型号
    private final String ZCCCHGZ="ZCCCHGZ";//出厂合格证前缀
    private final String ZYYZH="企业注册号";//营业执照前缀

    private Context context;
    private Handler mHandler;

    public static final int HGZ_CODE=100;
    public static final int YYZZ_CODE=101;
    public static final int OTHER_CODE=0;

   public DeviceScanUtils(Context context, Handler handler){
        this.context=context;
        this.mHandler=handler;
        initDevice();
    }

    private void initDevice(){
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        currentPhoneModel= OtherUtils.getSystemProperty();
        if (currentPhoneModel != null && currentPhoneModel.equals(MODEL)) {
            initScan();
            IntentFilter filter = new IntentFilter();
            int[] idbuf = new int[]{PropertyID.WEDGE_INTENT_ACTION_NAME, PropertyID.WEDGE_INTENT_DATA_STRING_TAG};
            String[] value_buf = mScanManager.getParameterString(idbuf);
            if (value_buf != null && value_buf[0] != null && !value_buf[0].equals("")) {
                filter.addAction(value_buf[0]);
            } else {
                filter.addAction(SCAN_ACTION);
            }
            context.registerReceiver(mScanReceiver, filter);
            isReceiver = true;
        }
    }
    public void startScan(){
        if (currentPhoneModel != null && currentPhoneModel.equals(MODEL)) {
            // mScanManager.stopDecode();
           // isScaning = true;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mScanManager.startDecode();
            // mScanManager.stopDecode();
        }else {
            Toast.makeText(context,"您的设备不支持",Toast.LENGTH_LONG).show();
        }
      //  mHandler.sendEmptyMessageDelayed(1,5*1000);
    }

    private void initScan() {
        if (mScanManager == null) {
            mScanManager = new ScanManager();
            mScanManager.openScanner();
            mScanManager.switchOutputMode(0);
            soundpool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100); // MODE_RINGTONE
            soundid = soundpool.load("/etc/Scan_new.og", 1);
        }
    }

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {




        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            //isScaning = false;
            soundpool.play(soundid, 1, 1, 0, 0, 1);
            mVibrator.vibrate(100);
            byte[] barcode = intent.getByteArrayExtra(ScanManager.DECODE_DATA_TAG);
            int barcodelen = intent.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0);
            String barcodeStr;
            try {
                barcodeStr = new String(barcode, 0, barcodelen);
            }catch (Exception e){
                barcodeStr = new String(barcode);
            }
            if(TextUtils.isEmpty(barcodeStr)){
                Toast.makeText(context,context.getString(R.string.scan_null),Toast.LENGTH_LONG).show();
                return;
            }
            String[] split = barcodeStr.split("\\|");
            String arrString = split[0];
            Message message=Message.obtain();
            if(arrString.contains(ZCCCHGZ)){
                //合格证
                message.what=HGZ_CODE;
                message.obj=barcodeStr;
            }else if(arrString.contains(ZYYZH)){
                //营业执照
                message.what=YYZZ_CODE;
                message.obj=barcodeStr;
            }else {
                message.what=OTHER_CODE;
                message.obj=barcodeStr;
            }
            mHandler.sendMessage(message);
        }

    };

    public void releaseDeviceScan(){
        if (currentPhoneModel != null && currentPhoneModel.equals(MODEL)) {
            if (mScanManager != null) {
                mScanManager.stopDecode();
                //mScanManager.
               // isScaning = false;
            }
            if (mScanManager != null && isReceiver) {
                context.unregisterReceiver(mScanReceiver);
            }
        }
        File tempFile = new File(IDCARDIMAGEPATH);
        if(tempFile.exists()){
            File[] files = tempFile.listFiles();
            for (File f : files) {
                f.delete();
            }
        }

        mHandler.removeCallbacksAndMessages(null);
        mHandler=null;
        context=null;
    }
}
