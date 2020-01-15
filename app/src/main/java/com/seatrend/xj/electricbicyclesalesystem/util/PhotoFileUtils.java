package com.seatrend.xj.electricbicyclesalesystem.util;

import com.seatrend.xj.electricbicyclesalesystem.common.Constants;
import com.seatrend.xj.electricbicyclesalesystem.common.Constants;

import java.io.File;

/**
 * Created by ly on 2019/11/12 15:56
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class PhotoFileUtils {
    /**
     * 删除拍照文件
     */
    public static void deleteCaptruePhotoFile() {
        File file = new File(Constants.Companion.getIMAGE_PATH());
        deleteDirectory(file);
    }

    public static void deleteFile(File file) {
        deleteDirectory(file);
    }

    private static void deleteDirectory(File file) {

        if (file.isFile()) {
            file.delete();//清理文件
        } else {
            File list[] = file.listFiles();
            if (list != null) {
                for (File f : list) {
                    deleteDirectory(f);
                }
                file.delete();//清理目录
            }
        }
    }
}
