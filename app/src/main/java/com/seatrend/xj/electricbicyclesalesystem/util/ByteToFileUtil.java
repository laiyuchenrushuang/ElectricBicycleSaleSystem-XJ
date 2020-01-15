package com.seatrend.xj.electricbicyclesalesystem.util;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by ly on 2019/9/25 13:59
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class ByteToFileUtil {

    private final static String LPHOTO_BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private final static String PHOTO_NAME = "sfz.jpg";

    public static void bytesToImageFile(byte[] bytes) {
        try {
            File file = new File(getLpginPhotoPath());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes, 0, bytes.length);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 正面照片的路径
     *
     * @return
     */

    public static String getLpginPhotoPath() {
        return LPHOTO_BASE_PATH + "/" + PHOTO_NAME;
    }

    /**
     * 删除数据
     */
    public static void clearFileData() {
        File file = new File(getLpginPhotoPath());
        if (file.exists()) {
            file.delete();
        }
    }
}
