package com.seatrend.xj.electricbicyclesalesystem.activity

import android.annotation.SuppressLint
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
import android.widget.*
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.adpater.CheckDataPhotoAdapter
import com.seatrend.xj.electricbicyclesalesystem.common.BaseActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.*
import com.seatrend.xj.electricbicyclesalesystem.http.thread.ThreadPoolManager
import com.seatrend.xj.electricbicyclesalesystem.persenter.CarPhotoPersenter
import com.seatrend.xj.electricbicyclesalesystem.util.*
import com.seatrend.xj.electricbicyclesalesystem.view.CarPhotoView
import kotlinx.android.synthetic.main.activity_collect_photo.*
import kotlinx.android.synthetic.main.common_title.*
import kotlinx.android.synthetic.main.recyclerview.*
import java.io.File
import java.text.DecimalFormat
import android.os.Parcelable


/**
 * Created by ly on 2019/9/27 10:34
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class CollectPhotoActivity : BaseActivity(), CarPhotoView, CheckDataPhotoAdapter.itemOnClickListener, CheckDataPhotoAdapter.itemDeleteClickListener {
    companion object {
        var photoEntranceFlag: String? = "-1" // 0是VIN，1是查验，2注册，3变更 ，4转移， 5补换，6注销，7旧车换牌， 8临时号牌申请 9车辆归档，10 员工备案 ,11 退办 ,12 补拍 -1是defult
        var dzpzFlag: Boolean = true //true 是需要电子凭证 false 不需要
        var jtxsFlag: Boolean = true //true 合格  false 不合格 （不合格原因： 无脚踏行驶，整车质量>55，最高速度 >25）

        var mLsh: String? = null //业务流水号
        var mXh: String? = null //业务序号
        var ywlx: String? = null //业务类型

        var allPhoto = ArrayList<PhotoTypeEntity.DataBean.ConfigBean>() //所有照片类型信息（服务器）  初次进来的所有照片类型

        var addPhoto = ArrayList<PhotoTypeEntity.DataBean.ConfigBean>() //多拍模式 添加的照片类型（缓存初始zplx）
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


    private var progressBar: ProgressBar? = null
    private var tvPro: TextView? = null
    private var progressdialog: Dialog? = null
    private val df = DecimalFormat("#.0")

    private var count = 0 //方便计数 zpdz为空，也可以传，但是总photolistsize -count  成功数-count
    private var isCommiting = false


    var mDataZcbm: CYEntranceEnity? = null  //查验专属信息
    var mData3C: ThreeCEnity? = null//查验专属信息

    override fun initView() {
        setPageTitle(getString(R.string.carcy_next))
        bindEvent()
        mCarPhotoPersenter = CarPhotoPersenter(this)
        m_recycler_view.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        mCheckDataPhotoAdapter = CheckDataPhotoAdapter(this)
        mCheckDataPhotoAdapter!!.setItemOnClick(this)
        mCheckDataPhotoAdapter!!.setItemdeleteClick(this)
        m_recycler_view.adapter = mCheckDataPhotoAdapter

        tv_right.text = resources.getString(R.string.take_many_patterns)
        initData()
        showLog("图片数据h -----" + GsonUtils.toJson(addPhoto))
    }

    private fun initData() {
        try {
            rb_cy_ok.isChecked = true
            //查验结论的逻辑 查验才有
            if (Constants.CAR_CY == photoEntranceFlag) {
                mDataZcbm = intent.getSerializableExtra("all_data") as CYEntranceEnity
                ll_cyjl.visibility = View.VISIBLE
                if (!jtxsFlag) {
                    rb_cy_ok.isEnabled = false
                    rb_cy_no.isClickable = false
                    rb_cy_no.isChecked = true
                }
                var enity = intent.getParcelableExtra<Parcelable>("photo_list") as ServicePhotoCamebackEnity

                allPhoto.clear()
                addPhoto.clear()
                if (enity.data != null && enity.data.size > 0) {
                    for (db in enity.data) {
                        var photoEnity = PhotoTypeEntity.DataBean.ConfigBean()
                        photoEnity.zmmc = db.dmsm
                        photoEnity.zplx = db.dmz
                        addPhoto.add(photoEnity)
                        allPhoto.add(photoEnity)
                    }
                }

                mCheckDataPhotoAdapter!!.setPhotoType(allPhoto)
            } else if (Constants.YGBA == photoEntranceFlag) {

                //员工备案的图片收集
                ll_cyjl.visibility = View.GONE
                val list = CodeTableSQLiteUtils.queryByDMLB(Constants.YGZP)
                allPhoto.clear()
                addPhoto.clear()
                for (db in list) {
                    var enity = PhotoTypeEntity.DataBean.ConfigBean()
                    enity.zmmc = db.dmsm1
                    enity.zplx = db.dmz
                    allPhoto.add(enity)
                    addPhoto.add(enity)
                }
                mCheckDataPhotoAdapter!!.setPhotoType(allPhoto)
            } else if (Constants.YWBP == photoEntranceFlag) {
                //照片补拍
                ll_cyjl.visibility = View.GONE
                var enity = intent.getParcelableExtra<Parcelable>("photo_list") as ZpbpEntity

                allPhoto.clear()
                addPhoto.clear()
                if (enity.data != null && enity.data.photo.size > 0) {
                    for (db in enity.data.photo) {
                        var photoEnity = PhotoTypeEntity.DataBean.ConfigBean()
                        photoEnity.zmmc = db.dmsm
                        photoEnity.zplx = db.dmz
                        addPhoto.add(photoEnity)
                        allPhoto.add(photoEnity)
                    }
                }

                mCheckDataPhotoAdapter!!.setPhotoType(allPhoto)
            } else {
                //登记方面
                ll_cyjl.visibility = View.GONE
                var enity = intent.getParcelableExtra<Parcelable>("photo_list") as DjLshEnity
                allPhoto.clear()
                addPhoto.clear()
                if (enity.data.photo != null && enity.data.photo.size > 0) {

                    for (db in enity.data.photo) {
                        var photoEnity = PhotoTypeEntity.DataBean.ConfigBean()
                        photoEnity.zmmc = db.dmsm
                        photoEnity.zplx = db.dmz
                        addPhoto.add(photoEnity)
                        allPhoto.add(photoEnity)
                    }
                }
                mCheckDataPhotoAdapter!!.setPhotoType(allPhoto)
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
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
            //            showLog("  result list = " + GsonUtils.toJson(allPhoto))
//            return@setOnClickListener
            resetNumbers()
            if (checkPhoto(allPhoto)) {
                when (photoEntranceFlag) {
                    Constants.CAR_CY -> {
                        try {
                            if (Constants.A == intent.getStringExtra("ywlx")) {
                                mData3C = intent.getSerializableExtra("3c_data") as ThreeCEnity
                                val map = HashMap<String, String?>()
                                map["zcbm"] = intent.getStringExtra("zcbm")
                                map["lsh"] = mDataZcbm!!.data.lsh
                                map["xh"] = mDataZcbm!!.data.xh
                                map["cphgzbh"] = mData3C!!.data.threeCertificates.data.qualificationCode
                                map["cccbh"] = mData3C!!.data.threeCertificates.data.certcode
                                map["scqymc"] = mData3C!!.data.threeCertificates.data.vehicleManufacturer
                                map["ywlx"] = intent.getStringExtra("ywlx")
                                map["cybm"] = UserInfo.GLBM
                                map["fzjg"] = if (TextUtils.isEmpty(UserInfo.FZJG)) "" else UserInfo.FZJG

                                map["cyr"] = UserInfo.XM
                                map["cyrsfzmhm"] = UserInfo.SFZMHM//查验人身份证明号码
                                map["czpt"] = Constants.CZPT //查验平台

                                map["clzwsb"] = mData3C!!.data.threeCertificates.data.trademarkCn

                                map["cwkc"] = mData3C!!.data.threeCertificates.data.length
                                map["cwck"] = mData3C!!.data.threeCertificates.data.width
                                map["cwkg"] = mData3C!!.data.threeCertificates.data.height

                                map["zbzl"] = mData3C!!.data.threeCertificates.data.weight
                                map["xxlc"] = mData3C!!.data.threeCertificates.data.continuousMileage
                                map["cpxh"] = mData3C!!.data.threeCertificates.data.productModel
                                map["zzrq"] = mData3C!!.data.threeCertificates.data.manufacturingDate
                                map["zgcs"] = mData3C!!.data.threeCertificates.data.maxSpeed

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
                                showLoadingDialog()
                                mCarPhotoPersenter!!.doNetworkTask(map, Constants.SAVE_CY_MSG)
                            } else {  //除开业务注册
                                showLoadingDialog()
                                val map = HashMap<String, String?>()
                                map["zcbm"] = mDataZcbm!!.data.threeCertificates.zcbm
                                map["lsh"] = mDataZcbm!!.data.lsh
                                map["xh"] = mDataZcbm!!.data.xh
                                map["cphgzbh"] = mDataZcbm!!.data.threeCertificates.cphgzbh
                                map["cccbh"] = mDataZcbm!!.data.threeCertificates.cccbh
                                map["scqymc"] = mDataZcbm!!.data.threeCertificates.clzzs
                                map["ywlx"] = intent.getStringExtra("ywlx")
                                map["cybm"] = UserInfo.GLBM
                                map["fzjg"] = if (TextUtils.isEmpty(UserInfo.FZJG)) "" else UserInfo.FZJG

                                map["cyr"] = UserInfo.XM
                                map["cyrsfzmhm"] = UserInfo.SFZMHM//查验人身份证明号码
                                map["czpt"] = Constants.CZPT //查验平台

                                map["cph"] = intent.getStringExtra("hphm")

                                map["clzwsb"] = mDataZcbm!!.data.threeCertificates.clzwsb

                                map["cwkc"] = mDataZcbm!!.data.threeCertificates.cwkc
                                map["cwck"] = mDataZcbm!!.data.threeCertificates.cwkk
                                map["cwkg"] = mDataZcbm!!.data.threeCertificates.cwkg

                                map["zbzl"] = mDataZcbm!!.data.threeCertificates.zbzl
                                map["xxlc"] = mDataZcbm!!.data.threeCertificates.xhlc
                                map["cpxh"] = mDataZcbm!!.data.threeCertificates.cpxh
                                map["zzrq"] = mDataZcbm!!.data.threeCertificates.zzrq
                                map["zgcs"] = mDataZcbm!!.data.threeCertificates.zgcs

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
                                showLoadingDialog()
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

                    Constants.YWBP -> {
                        var bpCount = 0
                        for (db in allPhoto) {
                            if (db.zpPath != null && !TextUtils.isEmpty(db.zpPath)) {
                                savePhotoDataToSql(db, Constants.YWBP)
                                bpCount++
                            }
                        }
                        showToast("" + bpCount + "张照片加入上传队列成功")
                        sendToTopActivity(MainOtherActivity::class.java)
                        finish()
                    }
                    else -> {
                        savePhoto()//注册 变更 转移 补换 注销 转入 号牌回收
                    }
                }
            }

        }
        ed_bhgyy.filters = arrayOf(inputFilter)

        tv_right.setOnClickListener {
            showDialog()
        }
    }

    var countType = 0 //多拍模式下 拍照类型 要不一样 才能保证定时任务上传

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_take_many_photo)
        dialog.setCanceledOnTouchOutside(false)

        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val btnOk = dialog.findViewById<Button>(R.id.btn_ok)
        val sp = dialog.findViewById<Spinner>(R.id.sp_photo_type)

        // 设置sp
        setSpinnerAdapter(sp)
        dialog.show()
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnOk.setOnClickListener {
            dialog.dismiss()

            val zmmc = sp.selectedItem.toString()  //照片名称
            var zpzl = ""                                    //照片类型
            for (db in addPhoto) {  //获得母zplx++
                if (zmmc == db.zmmc) {
                    zpzl = db.zplx + countType++
                    break
                }
            }

            var enity = PhotoTypeEntity.DataBean.ConfigBean()
            enity.zmmc = zmmc
            enity.zplx = zpzl
            enity.takeMode = "1"//多拍添加状态
            if (allPhoto.size - 1 >= 0) {
                allPhoto.add(allPhoto.size, enity)
            } else {
                allPhoto.add(0, enity)
            }

            mCheckDataPhotoAdapter!!.setPhotoType(allPhoto)
        }

    }

    private fun setSpinnerAdapter(spinner: Spinner) {
        val adapter = ArrayAdapter<String>(this, R.layout.my_simple_spinner_item)
        adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
        when (spinner.id) {
            R.id.sp_photo_type -> {
                adapter.clear()
                for (db in addPhoto) {
                    val dmsm1 = db.zmmc
                    adapter.add("$dmsm1")
                }
                spinner.adapter = adapter
            }
        }
    }

    //业务流水
    private fun savePhotoDataToSql(db: PhotoTypeEntity.DataBean.ConfigBean, flag: String) {

        when (flag) {
            Constants.CAR_CY -> {  //查验照片存储
                var enity = PhotoEntity()
                enity.lsh = mDataZcbm!!.data.lsh
                enity.xh = mDataZcbm!!.data.xh
                enity.zpzl = db.zplx
                enity.zpPath = db.zpPath
                enity.zpdz = db.zplj
                enity.zpsm = db.zmmc
                enity.cffs = UserInfo.GlobalParameter.CFBJ
                enity.lrr = UserInfo.XM
                enity.lrbm = UserInfo.GLBM
                enity.zplx = "1"////照片类型(1查验照片，2登记照片)
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
                enity.zplx = "2"////照片类型(1查验照片，2登记照片)
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

            Constants.YWBP -> {//照片补拍
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
                enity.zplx = intent.getStringExtra("zplx")
                CodeTableSQLiteUtils.addPhoto(enity)
            }
        }

    }

    private fun savePhoto() { //其他业务类
        if (Constants.CAR_ZC == photoEntranceFlag || Constants.CAR_BG == photoEntranceFlag || Constants.CAR_ZY == photoEntranceFlag) {
            val enity = intent.getSerializableExtra("all_data") as AllBikeMsgEnity
            if (!TextUtils.isEmpty(enity.data.checkData.lsh) && !TextUtils.isEmpty(enity.data.checkData.xh)) {
                mLsh = enity.data.checkData.lsh
                mXh = enity.data.checkData.xh
            }
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

    @SuppressLint("StringFormatMatches")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            showLoadingDialog()
            ThreadPoolManager.instance.execute(Runnable {
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
            })


            if (Constants.TYPE_ZCBM == allPhoto[photoPosition].zplx) {
                showToast(resources.getString(R.string.clippview_tips, allPhoto[photoPosition].zmmc))
            }
            if (Constants.TYPE_TYH == allPhoto[photoPosition].zplx) {
                showToast(resources.getString(R.string.clippview_tips, allPhoto[photoPosition].zmmc))
            }

        }

        if (requestCode == Constants.REQUEST_ZCBM && resultCode == Activity.RESULT_OK) {
            try {
                allPhoto[photoPosition].isClipped = data!!.getStringExtra(Constants.CLIPP)
                allPhoto[photoPosition].zpPath = data.getStringExtra(Constants.CLIPP_PICTURE_PATH)
                // 因為照片路徑沒變，不用換zpPath 更新下adapter試試
                mCheckDataPhotoAdapter!!.setPhoto(photoPosition, data.getStringExtra(Constants.CLIPP_PICTURE_PATH))
                showToast(resources.getString(R.string.clipped_success))
            } catch (e: Exception) {

            }


        }
    }

    private fun checkPhoto(data: ArrayList<PhotoTypeEntity.DataBean.ConfigBean>): Boolean {

        //如果是补拍照片就不用check数据了 直接下一步
        if (Constants.YWBP == photoEntranceFlag) {
            return true
        }
        for (cb in data) {
            if ("0" == cb.takeMode) {  // 只关注必拍模式的图片类型（0 是必拍照片类型）
                if ((cb.zpPath == null || cb.zpPath.isEmpty()) && Constants.TYPE_QT != cb.zplx) {
                    showToast("【" + cb.zmmc + "】后台数据为空，请拍摄或再次拍摄")
                    return false
                }
            }
        }
        return true
    }


    override fun netWorkTaskSuccess(commonResponse: CommonResponse) {
        dismissLoadingDialog()
        if (!Constants.UPDATA_LS_ZT.equals(commonResponse.getUrl())) {
            //提交结论不合格的数据
            if (commonResponse.getUrl() == Constants.SAVE_CY_MSG) {
                sendActivityEvent(AutographActivity::class.java) //查验还没改
            }
        } else if (Constants.UPDATA_LS_ZT.equals(commonResponse.getUrl())) {
            //状态传成功才跳转
            LoadingDialog.getInstance().dismissLoadDialog()
            intent.setClass(this, AutographActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sendActivityEventAddState() {
        try {
            showLoadingDialog()
            val map = HashMap<String, String?>()
            map["lsh"] = mLsh
            map["xh"] = mXh
            map["glbm "] = UserInfo.GLBM
            map["zt"] = "2" //流程状态 0-查验未通过，1-已查验，2-已登记，3-已制证，E-已归档，Q-已退办
            map["cyrsfzmhm"] = UserInfo.SFZMHM//查验人身份证明号码
            map["czpt"] = Constants.CZPT //查验平台
            mCarPhotoPersenter!!.doNetworkTask(map, Constants.UPDATA_LS_ZT)
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
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

            showLog(" itemOnClick  " + allPhoto[position].isClipped)
            if (getClipperView(position) && "0" == allPhoto[position].isClipped) { // 整车编码的类型且 没裁剪
                val intent = Intent(this, ShowPhotoActivity::class.java)
                intent.putExtra(Constants.PATH, allPhoto[photoPosition].zpPath)
                intent.putExtra(Constants.ZPMC, allPhoto[position].zmmc)
                intent.putExtra(Constants.ZPLX, allPhoto[position].zplx.substring(0, 2))
                intent.putExtra(Constants.CLIPP, "1")  // 需要裁剪没被裁剪
                startActivityForResult(intent, Constants.REQUEST_ZCBM)
                startRotateAlphaAcaleAnimation()
            } else {
                val intent = Intent(this, ShowPhotoActivity::class.java)
                intent.putExtra(Constants.CLIPP, "0") // 不需要裁剪
                intent.putExtra(Constants.PATH, allPhoto[photoPosition].zpPath)
                intent.putExtra(Constants.ZPMC, allPhoto[position].zmmc)
                intent.putExtra(Constants.ZPLX, allPhoto[position].zplx)
                startActivity(intent)
                startRotateAlphaAcaleAnimation()
            }
        }
    }

    private fun getClipperView(position: Int): Boolean {
        showLog("" + allPhoto[position].zplx)
        //适配整车编码类型的图片
        if (Constants.TYPE_ZCBM == allPhoto[position].zplx) {
            return true
        }

        //适配拓印摸类型的图片
        if (Constants.TYPE_TYH == allPhoto[position].zplx) {
            return true
        }

        //多拍整车编码
        if (allPhoto[position].zplx.length > 2 && Constants.TYPE_ZCBM == allPhoto[position].zplx.substring(0, 2)) {
            return true
        }

        //多拍拓印摸
        if (allPhoto[position].zplx.length > 2 && Constants.TYPE_TYH == allPhoto[position].zplx.substring(0, 2)) {
            return true
        }

        return false
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
        allPhoto[position].zpPath = null
        allPhoto[position].isClipped = "0"
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