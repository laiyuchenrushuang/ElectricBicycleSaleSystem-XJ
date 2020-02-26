package com.seatrend.xj.electricbicyclesalesystem.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.CheckDataPhotoAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.persenter.CarPhotoPersenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.CarPhotoView
import kotlinx.android.synthetic.main.activity_collect_photo.*
import kotlinx.android.synthetic.main.recyclerview.*
import java.io.File
import java.text.DecimalFormat

/**
 * Created by ly on 2019/9/27 10:34
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class CollectPhotoActivity : BaseActivity(), CarPhotoView, CheckDataPhotoAdapter.itemOnClickListener, CheckDataPhotoAdapter.itemDeleteClickListener {
    companion object {
        var photoEntranceFlag: String? = "-1" // 0是VIN，1是查验，2注册，3变更 ，4转移， 5补换，6注销，7旧车换牌， 8临时号牌申请 9车辆归档，10 员工备案 ,11 退办 -1是defult
        var dzpzFlag: Boolean = true //true 是需要电子凭证 false 不需要
        var jtxsFlag: Boolean = true //true 合格  false 不合格 （不合格原因： 无脚踏行驶，整车质量>55，最高速度 >25）

        var mLsh: String? = null //业务流水号
        var mXh: String? = null //业务序号
        var ywlx: String? = null //业务类型
    }

    private var requested: Boolean = false
    private var deletePosition = 0
    private var imgFile: File? = null
    private val CAMERA_REQUEST_CODE = 20

    private var photoPosition = 0

    private var mCarPhotoPersenter: CarPhotoPersenter? = null

    private var uploadPhotoFileadNumber = 0//失败总数
    private var uploadPhotoSuccessNumber = 0//成功总数
    private val VIN_CODE = 30
    private var mCheckDataPhotoAdapter: CheckDataPhotoAdapter? = null
    var allPhoto = ArrayList<PhotoTypeEntity.DataBean.ConfigBean>() //所有照片类型信息（服务器）

    private var progressBar: ProgressBar? = null
    private var tvPro: TextView? = null
    private var progressdialog: Dialog? = null
    private val df = DecimalFormat("#.0")

    private var count = 0 //方便计数 zpdz为空，也可以传，但是总photolistsize -count  成功数-count
    private var isCommiting = false

    override fun initView() {
        setPageTitle(getString(R.string.carcy_next))
        bindEvent()

        mCarPhotoPersenter = CarPhotoPersenter(this)
        m_recycler_view.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        mCheckDataPhotoAdapter = CheckDataPhotoAdapter(this)
        mCheckDataPhotoAdapter!!.setItemOnClick(this)
        mCheckDataPhotoAdapter!!.setItemdeleteClick(this)
        m_recycler_view.adapter = mCheckDataPhotoAdapter
        initData()
    }

    private fun initData() {
        rb_cy_ok.isChecked = true
        //查验结论的逻辑 查验才有
        if (Constants.CAR_CY.equals(photoEntranceFlag)) {
            ll_cyjl.visibility = View.VISIBLE
            if (!jtxsFlag) {
                rb_cy_ok.isEnabled = false
                rb_cy_no.isClickable = false
                rb_cy_no.isChecked = true
            }
            val list = CodeTableSQLiteUtils.queryByDMLB(Constants.CYZP)
            allPhoto.clear()
            for (db in list) {
                var enity = PhotoTypeEntity.DataBean.ConfigBean()
                enity.zmmc = db.dmsm1
                enity.zplx = db.dmz
                allPhoto.add(enity)
            }
            mCheckDataPhotoAdapter!!.setPhotoType(allPhoto)
        } else if (Constants.YGBA.equals(photoEntranceFlag)) {
            //员工备案的图片收集
            ll_cyjl.visibility = View.GONE
            val list = CodeTableSQLiteUtils.queryByDMLB(Constants.YGZP)
            allPhoto.clear()
            for (db in list) {
                var enity = PhotoTypeEntity.DataBean.ConfigBean()
                enity.zmmc = db.dmsm1
                enity.zplx = db.dmz
                allPhoto.add(enity)
            }
            mCheckDataPhotoAdapter!!.setPhotoType(allPhoto)
        } else {
            ll_cyjl.visibility = View.GONE
            val list = CodeTableSQLiteUtils.queryByDMLB(Constants.DJZP)
            allPhoto.clear()
            for (db in list) {
                var enity = PhotoTypeEntity.DataBean.ConfigBean()
                enity.zmmc = db.dmsm1
                enity.zplx = db.dmz
                allPhoto.add(enity)
            }
            mCheckDataPhotoAdapter!!.setPhotoType(allPhoto)
        }
    }

    fun bindEvent() {

        CheckBoxUtils.setListener(rb_cy_ok, rb_cy_no)
        rb_cy_no.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                rb_cy_no.isChecked = true
                rb_cy_ok.isChecked = false
                ll_bhgyy.visibility = View.VISIBLE
            } else {
                rb_cy_no.isChecked = false
                rb_cy_ok.isChecked = true
                ll_bhgyy.visibility = View.GONE
            }
        }
        btn_commit.setOnClickListener {
            resetNumbers()
            if (checkPhoto(allPhoto)) {
                when (photoEntranceFlag) {
                    Constants.CAR_CY -> {
                        try {
                            if ("A" == intent.getStringExtra("ywlx")) {
                                showLoadingDialog()
                                val map = HashMap<String, String?>()
                                map["zcbm"] = intent.getStringExtra("zcbm")
                                map["lsh"] = CarInfoActivity.mDataZcbm!!.data.lsh
                                map["xh"] = CarInfoActivity.mDataZcbm!!.data.xh
                                map["cphgzbh"] = CarInfoActivity.mData3C!!.data.threeCertificates.data.qualificationCode
                                map["cccbh"] = CarInfoActivity.mData3C!!.data.threeCertificates.data.certcode
                                map["scqymc"] = CarInfoActivity.mData3C!!.data.threeCertificates.data.vehicleManufacturer
                                map["ywlx"] = intent.getStringExtra("ywlx")
                                map["cybm"] = UserInfo.GLBM
                                map["fzjg"] = if (TextUtils.isEmpty(UserInfo.FZJG)) "" else UserInfo.FZJG

                                map["cyr"] = UserInfo.XM
                                map["cyrsfzmhm"] = UserInfo.SFZMHM//查验人身份证明号码
                                map["czpt"] = Constants.CZPT //查验平台

                                map["clzwsb"] = CarInfoActivity.mData3C!!.data.threeCertificates.data.trademarkCn

                                map["cwkc"] = CarInfoActivity.mData3C!!.data.threeCertificates.data.length
                                map["cwck"] = CarInfoActivity.mData3C!!.data.threeCertificates.data.width
                                map["cwkg"] = CarInfoActivity.mData3C!!.data.threeCertificates.data.height

                                map["zbzl"] = CarInfoActivity.mData3C!!.data.threeCertificates.data.weight
                                map["xxlc"] = CarInfoActivity.mData3C!!.data.threeCertificates.data.continuousMileage
                                map["cpxh"] = CarInfoActivity.mData3C!!.data.threeCertificates.data.productModel
                                map["zzrq"] = CarInfoActivity.mData3C!!.data.threeCertificates.data.manufacturingDate
                                map["zgcs"] = CarInfoActivity.mData3C!!.data.threeCertificates.data.maxSpeed

                                map["ywyy"] = if (!TextUtils.isEmpty(intent.getStringExtra("ywyy"))) intent.getStringExtra("ywyy") else "" //为空传”“
                                if (rb_cy_no.isChecked) {
                                    if (!CheckEditTxetUtils.checkEditextValuable(ed_bhgyy)) {
                                        showToast("请填写不合格原因")
                                        return@setOnClickListener
                                    }
                                    map["cybz"] = ed_bhgyy.text.toString()
                                    map["cyjl"] = "0"
                                } else {
                                    map["cyjl"] = "1"
                                }

                                for (db in allPhoto) {
                                    if (db.zpPath != null && !TextUtils.isEmpty(db.zpPath)) {
                                        savePhotoDataToSql(db, Constants.CAR_CY)
                                    }
                                }
                                //为什么放这里，要保证数据库保存完毕
                                mCarPhotoPersenter!!.doNetworkTask(map, Constants.SAVE_CY_MSG)
                            } else {  //除开业务注册
                                showLoadingDialog()
                                val map = HashMap<String, String?>()
                                map["zcbm"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.zcbm
                                map["lsh"] = CarInfoActivity.mDataZcbm!!.data.lsh
                                map["xh"] = CarInfoActivity.mDataZcbm!!.data.xh
                                map["cphgzbh"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.cphgzbh
                                map["cccbh"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.cccbh
                                map["scqymc"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.clzzs
                                map["ywlx"] = intent.getStringExtra("ywlx")
                                map["cybm"] = UserInfo.GLBM
                                map["fzjg"] = if (TextUtils.isEmpty(UserInfo.FZJG)) "" else UserInfo.FZJG

                                map["cyr"] = UserInfo.XM
                                map["cyrsfzmhm"] = UserInfo.SFZMHM//查验人身份证明号码
                                map["czpt"] = Constants.CZPT //查验平台

                                map["cph"] = intent.getStringExtra("hphm")

                                map["clzwsb"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.clzwsb

                                map["cwkc"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.cwkc
                                map["cwck"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.cwkk
                                map["cwkg"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.cwkg

                                map["zbzl"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.zbzl
                                map["xxlc"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.xhlc
                                map["cpxh"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.cpxh
                                map["zzrq"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.zzrq
                                map["zgcs"] = CarInfoActivity.mDataZcbm!!.data.threeCertificates.zgcs

                                map["ywyy"] = if (!TextUtils.isEmpty(intent.getStringExtra("ywyy"))) intent.getStringExtra("ywyy") else "" //为空传”“
                                if (rb_cy_no.isChecked) {
                                    if (!CheckEditTxetUtils.checkEditextValuable(ed_bhgyy)) {
                                        showToast("请填写不合格原因")
                                        return@setOnClickListener
                                    }
                                    map["cybz"] = ed_bhgyy.text.toString()
                                    map["cyjl"] = "0"
                                } else {
                                    map["cyjl"] = "1"
                                }
                                for (db in allPhoto) {
                                    if (db.zpPath != null && !TextUtils.isEmpty(db.zpPath)) {
                                        savePhotoDataToSql(db, Constants.CAR_CY)
                                    }

                                }

                                //为什么放这里，要保证数据库保存完毕
                                mCarPhotoPersenter!!.doNetworkTask(map, Constants.SAVE_CY_MSG)

                            }

                        } catch (e: Exception) {
                            showToast(e.message.toString())
                        }
                    }
                    Constants.YGBA -> {
//                        showLoadingDialog()
                        try {
                            for (db in allPhoto) {
                                // ----
//                                val mapP = HashMap<String, String?>()
//                                mapP["zpzl"] = db.zplx
//                                mapP["zpdz"] = db.zplj
//                                mapP["sfzmhm"] = intent.getStringExtra("yg_sfz")
//
//                                mapP["cffs"] = UserInfo.GlobalParameter.CFBJ
//                                mapP["lrr"] = UserInfo.XM
//                                mapP["lrbm"] = UserInfo.GLBM
//                                mCarPhotoPersenter!!.doNetworkTask(mapP, Constants.YG_PHOTO_SAVE)
                                // ----
                                if (db.zpPath != null && !TextUtils.isEmpty(db.zpPath)) {
                                    savePhotoDataToSql(db, Constants.YGBA)
                                }
                            }
                            sendActivityEvent(EmployeeRemindActivity::class.java)
                        } catch (e: Exception) {
                            showToast(e.message.toString())
                        }
                    }
                    else -> {
                        savePhoto()
                    }
                }
            }
        }
        ed_bhgyy.filters = arrayOf(inputFilter)
    }

    //业务流水
    private fun savePhotoDataToSql(db: PhotoTypeEntity.DataBean.ConfigBean, flag: String) {

        when (flag) {
            Constants.CAR_CY -> {  //查验照片存储
                var enity = PhotoEntity()
                enity.lsh = CarInfoActivity.mDataZcbm!!.data.lsh
                enity.xh = CarInfoActivity.mDataZcbm!!.data.xh
                enity.zpzl = db.zplx
                enity.zpPath = db.zpPath
                enity.zpdz = db.zplj
                enity.zpsm = db.zmmc
                enity.cffs = UserInfo.GlobalParameter.CFBJ
                enity.lrr = UserInfo.XM
                enity.lrbm = UserInfo.GLBM
                CodeTableSQLiteUtils.addPhoto(enity)
            }
            Constants.CAR_YW -> { //业务照片存储
                var enity = PhotoEntity()
                enity.lsh = mLsh
                enity.xh = mXh
                enity.zpzl = db.zplx
                enity.zpPath = db.zpPath
                enity.zpdz = db.zplj
                enity.zpsm = db.zmmc
                enity.cffs = UserInfo.GlobalParameter.CFBJ
                enity.lrr = UserInfo.XM
                enity.lrbm = UserInfo.GLBM
                CodeTableSQLiteUtils.addPhoto(enity)
            }
            Constants.YGBA -> {//员工照片存储
                var enity = PhotoEntity()
                enity.zpzl = db.zplx
                enity.zpPath = db.zpPath
                enity.zpdz = db.zplj
                enity.zpsm = db.zmmc
                enity.sfz = intent.getStringExtra("yg_sfz")
                enity.cffs = UserInfo.GlobalParameter.CFBJ
                enity.lrr = UserInfo.XM
                enity.lrbm = UserInfo.GLBM
                CodeTableSQLiteUtils.addPhoto(enity)
            }
        }

    }

    private fun savePhoto() { //其他业务类
        if (Constants.CAR_ZC.equals(photoEntranceFlag) || Constants.CAR_BG.equals(photoEntranceFlag) || Constants.CAR_ZY.equals(photoEntranceFlag)) {
            mLsh = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.lsh
            mXh = CarInfoByCyActivity.mAllBikeMsgEnity!!.data.checkData.xh
        }

        //保存图片
        try {
            for (db in allPhoto) {
                if (db.zpPath != null && !TextUtils.isEmpty(db.zpPath)) {
                    savePhotoDataToSql(db, Constants.CAR_YW)
                }
            }

            if ("1" == UserInfo.GlobalParameter.BXBJ) {
                sendActivityEvent(InsuranceActivity::class.java)
            } else {
                sendActivityEventAddState()
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    private fun getPicFromCamera() {
        //用于保存调用相机拍照后所生成的文件
        val tempFile = File(Constants.IMAGE_PATH)//
        val imageUri: Uri
        if (!tempFile.exists()) {
            tempFile.mkdirs()
        }
        imgFile = File(tempFile, System.currentTimeMillis().toString() + ".jpg")
        if (Build.VERSION.SDK_INT >= 24) {//判断版本
            imageUri = FileProvider.getUriForFile(this, getString(R.string.authority), imgFile)
        } else {
            imageUri = Uri.fromFile(imgFile)
        }
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            showLoadingDialog()

            Thread(Runnable {

                var bitmap = BitmapUtils.getSmallBitmap(imgFile!!.path)
                bitmap = BitmapUtils.compressImage(bitmap)
                BitmapUtils.saveBitmap(bitmap, imgFile!!.name.replace(".jpg", ""))
                bitmap?.recycle()

                //为什么放进来，因为文件太大，服务器会报错 保证图片储存是过滤后的图片
                runOnUiThread {
                    allPhoto[photoPosition].zpPath = imgFile!!.path
                    mCheckDataPhotoAdapter!!.setPhoto(photoPosition, imgFile!!.path)
                    dismissLoadingDialog()
                }
            }).start()
        }
    }

    private fun checkPhoto(data: ArrayList<PhotoTypeEntity.DataBean.ConfigBean>): Boolean {
        for (cb in data) {
            if ((cb.zpPath == null || cb.zpPath.isEmpty()) && "B4" != cb.zplx) {
                showToast("【" + cb.zmmc + "】后台数据为空，请拍摄或再次拍摄")
                return false
            }
        }
        return true
    }


    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (!Constants.UPDATA_LS_ZT.equals(commonResponse.getUrl())) {
            //提交结论不合格的数据
            if (commonResponse.getUrl() == Constants.SAVE_CY_MSG) {
                sendActivityEvent(RemindCYActivity::class.java)
            }
        } else if (Constants.UPDATA_LS_ZT.equals(commonResponse.getUrl())) {
            //状态传成功才跳转
            LoadingDialog.getInstance().dismissLoadDialog()
            intent.setClass(this, RemindHPBFActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sendActivityEventAddState() {
        val map = HashMap<String, String?>()
        map["lsh"] = mLsh
        map["xh"] = mXh
        map["glbm "] = UserInfo.GLBM
        map["zt"] = "2" //流程状态 0-查验未通过，1-已查验，2-已登记，3-已制证，E-已归档，Q-已退办
        map["cyrsfzmhm"] = UserInfo.SFZMHM//查验人身份证明号码
        map["czpt"] = Constants.CZPT //查验平台
        mCarPhotoPersenter!!.doNetworkTask(map, Constants.UPDATA_LS_ZT)
    }

    override fun netWorkTaskfailed(commonResponse: CommonResponse) {
        LoadingDialog.getInstance().dismissLoadDialog()

        showErrorDialog(commonResponse.getResponseString())
    }

    override fun itemOnClick(position: Int) {
        photoPosition = position
        if (TextUtils.isEmpty(allPhoto[position].zpPath)) {
            getPicFromCamera()
        } else {
            val intent = Intent(this, ShowPhotoActivity::class.java)
            intent.putExtra(Constants.PATH, imgFile!!.path)
            intent.putExtra(Constants.ZPLX, allPhoto[position].zmmc)
            startActivity(intent)
            startRotateAlphaAcaleAnimation()
        }
    }

    //删除，连带本地数据库也删除
    private fun deletePhoto() {
        var file = File(imgFile!!.path)
        if (file.exists()) {
            file.delete()
        }
    }

    override fun itemdelete(position: Int) {
        deletePosition = position
        allPhoto[position].zpPath=null
        uploadPhotoSuccessNumber--
    }

    override fun onDestroy() {
        super.onDestroy()
        resetNumbers()
        requested = false
        allPhoto.clear()
    }

    // 成功数复原
    private fun resetNumbers() {
        uploadPhotoSuccessNumber = 0
        uploadPhotoFileadNumber = 0
        isCommiting = false
        count = 0
    }

    fun showProgressDialog() {
        val progressdialog1 = ProgressDialog.getProgressDialog(this)
        progressdialog = progressdialog1
        progressBar = findViewById<ProgressBar>(R.id.pb_pro)
        tvPro = findViewById<TextView>(R.id.tv_pro)
        progressdialog1.show()
    }

    private fun sendActivityEvent(activity: Class<*>) {
        if (rb_cy_no.isChecked) { //不合格，那么就不能点业务登记
            intent.putExtra("cyjlBj", "1") //１为不合格
        } else {
            intent.putExtra("cyjlBj", "0") //０为合格
        }
        intent.setClass(this, activity)
        startActivity(intent)
    }

    override fun getLayout(): Int {
        return R.layout.activity_collect_photo
    }
}