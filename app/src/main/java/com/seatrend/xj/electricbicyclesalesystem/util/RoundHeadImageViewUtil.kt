package com.seatrend.xj.electricbicyclesalesystem.util

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.seatrend.xj.electricbicyclesalesystem.R
import kotlinx.android.synthetic.main.activity_main_other.*
import java.io.File

/**
 * Created by ly on 2019/10/21 14:18
 *
 * 圆形头像加载的工具类
 */
class RoundHeadImageViewUtil {
    companion object {
        var imagePath: File? = null
        /**
         *@param id 图片的资源id
         *@param imageId 图片控件id
         */
        fun loadImage(context: Context, imageId: ImageView) {
            Glide.with(context)
                    .load(imagePath)
                    .error(R.drawable.error_image)
                    .transform(GlideCircleTransform(context))
                    .into(imageId)
        }

        /**
         * path = url
         */
        fun loadImageByPath(context: Context, path:String,imageId: ImageView) {
            Glide.with(context)
                    .load(path)
                    .error(R.drawable.error_image)
                    .transform(GlideCircleTransform(context))
                    .into(imageId)
        }
    }

}