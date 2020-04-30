package com.seatrend.xj.electricbicyclesalesystem.common

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteOpenHelper
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.service.PhotoUploadService
import com.seatrend.xj.electricbicyclesalesystem.util.CrashHandler


/**
 * Created by seatrend on 2018/8/20.
 */

class MyApplication : Application(), Application.ActivityLifecycleCallbacks {

    override fun onCreate() {
        super.onCreate()
        myApplicationContext = this
//        CrashHandler.getInstance().init(this)
//        CodeTableSQLiteUtils.deleteAll(CodeTableSQLiteOpenHelper.PHOTO_TABLE_NAME)
        startService(Intent(this, PhotoUploadService::class.java))
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
        //currentActivity=activity;
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    companion object {
        var myApplicationContext: MyApplication? = null
    }
}
