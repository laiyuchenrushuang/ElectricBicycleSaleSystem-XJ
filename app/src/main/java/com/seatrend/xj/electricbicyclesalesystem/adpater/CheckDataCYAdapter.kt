package com.seatrend.xj.electricbicyclesalesystem.adpater

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.seatrend.xj.electricbicyclesalesystem.R
import com.seatrend.xj.electricbicyclesalesystem.activity.CYProjectJudgeActivity
import com.seatrend.xj.electricbicyclesalesystem.common.Constants
import com.seatrend.xj.electricbicyclesalesystem.database.CodeTableSQLiteUtils
import com.seatrend.xj.electricbicyclesalesystem.entity.CodeEntity
import com.seatrend.xj.electricbicyclesalesystem.entity.JudgeProjectEnity
import com.seatrend.xj.electricbicyclesalesystem.util.CheckBoxUtils
import com.seatrend.xj.electricbicyclesalesystem.util.CsysUtils
import com.seatrend.xj.electricbicyclesalesystem.util.OtherUtils
import com.seatrend.xj.electricbicyclesalesystem.util.ViewShowUtils

class CheckDataCYAdapter(private var mContext: Context? = null, private var list: ArrayList<JudgeProjectEnity>? = null) : RecyclerView.Adapter<CheckDataCYAdapter.MyViewHolder>() {

    private var listener: DataChange? = null
    private var bgCsysBj: Boolean = false //变更车身颜色标记 false
    private var csysData: List<CodeEntity.DataBean>? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.pan_ding_item, parent, false)
        return MyViewHolder(view)
    }

    fun setListener(ls: DataChange, flag: Boolean) {
        this.listener = ls
        this.bgCsysBj = flag
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.initItemView(position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var space: View? = null


        var sp_csys_a: Spinner? = null
        var sp_csys_b: Spinner? = null
        var sp_csys_c: Spinner? = null

        var text_content: TextView? = null
        var text_csys: TextView? = null
        var ll_check_box: LinearLayout? = null
        var cb_ok: CheckBox? = null
        var cb_no: CheckBox? = null

        var ll_sp: LinearLayout? = null

        fun initItemView(position: Int) {
            text_content = itemView.findViewById(R.id.pan_ding_content)
            ll_check_box = itemView.findViewById(R.id.ll_check_box)
            text_csys = itemView.findViewById(R.id.tv_csys)
            cb_ok = itemView.findViewById(R.id.cb_ok)
            cb_no = itemView.findViewById(R.id.cb_no)

            ll_sp = itemView.findViewById(R.id.ll_sp)
            space = itemView.findViewById(R.id.space)
            text_content!!.text = list!![position].content
            if ("0" == list!![position].order) {
                cb_no!!.isChecked = true
            } else {
                cb_ok!!.isChecked = true
            }

            if (bgCsysBj) {

                csysData = CodeTableSQLiteUtils.queryByDMLB(Constants.CSYS)
                //c 是车身颜色
                sp_csys_a = itemView.findViewById(R.id.sp_csys_a)
                sp_csys_c = itemView.findViewById(R.id.sp_csys_c)
                sp_csys_b = itemView.findViewById(R.id.sp_csys_b)

                var ai = ""
                var bi = ""
                var ci = ""

                if (Constants.PD_CSYS == list!![position].content.split(":")[0]) {
                    ViewShowUtils.showVisibleView(ll_sp)
                    ViewShowUtils.showGoneView(ll_check_box, space)
                    setSpinnerAdapter(sp_csys_a!!)
                    setSpinnerAdapter(sp_csys_b!!)
                    setSpinnerAdapter(sp_csys_c!!)
                    initCsysData()
                }

                sp_csys_a!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (sp_csys_a!!.selectedItem.toString() != ai) {
                            setAdapterThreeTreeModel(sp_csys_a!!, sp_csys_b, sp_csys_c)
                            ai = sp_csys_a!!.selectedItem.toString()
                            listener!!.getCsysData(sp_csys_a!!, sp_csys_b!!, sp_csys_c!!)
                        }
                    }
                }
                sp_csys_b!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (sp_csys_b!!.selectedItem.toString() != bi) {
                            setAdapterThreeTreeModel(sp_csys_a!!, sp_csys_b, sp_csys_c)
                            bi = sp_csys_b!!.selectedItem.toString()
                            listener!!.getCsysData(sp_csys_a!!, sp_csys_b!!, sp_csys_c!!)
                        }
                    }
                }
                sp_csys_c!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        if (sp_csys_c!!.selectedItem.toString() != ci) {
                            setAdapterThreeTreeModel(sp_csys_a!!, sp_csys_b, sp_csys_c)
                            ci = sp_csys_c!!.selectedItem.toString()
                            listener!!.getCsysData(sp_csys_a!!, sp_csys_b!!, sp_csys_c!!)
                        }
                    }
                }
            }

//            if (Constants.PD_CSYS == list!![position].content.split(":")[0]) {  //车身颜色的选项
//                text_csys!!.visibility = View.VISIBLE
//                text_csys!!.text = list!![position].csys
//                ll_check_box!!.visibility = View.GONE
//            } else {
//                ll_check_box!!.visibility = View.VISIBLE
//                text_csys!!.visibility = View.GONE
//            }
            bindEvent()
        }

        private fun setAdapterThreeTreeModel(sp_csys_a: Spinner, sp_csys_b: Spinner?, sp_csys_c: Spinner?) {

            val listB = getListString(csysData!!)
            val listC = getListString(csysData!!)
            val listA = getListString(csysData!!)


            if (sp_csys_a.selectedItem != "") {
                listB.remove(sp_csys_a.selectedItem)
                listC.remove(sp_csys_a.selectedItem)
            }
            if (sp_csys_b!!.selectedItem != "") {
                listC.remove(sp_csys_b.selectedItem)
                listA.remove(sp_csys_b.selectedItem)
            }

            if (sp_csys_c!!.selectedItem != "") {
                listB.remove(sp_csys_c.selectedItem)
                listA.remove(sp_csys_c.selectedItem)
            }

            setSpinnerAdapter(sp_csys_a, listA, sp_csys_a.selectedItem.toString())
            setSpinnerAdapter(sp_csys_b, listB, sp_csys_b.selectedItem.toString())
            setSpinnerAdapter(sp_csys_c, listC, sp_csys_c.selectedItem.toString())
        }

        private fun getListString(csysData: List<CodeEntity.DataBean>): ArrayList<String> {

            var list = ArrayList<String>()
            for (db in csysData) {
                if (db.dmz != "" && db.dmz != null) {
                    list.add(db.dmz + ":" + db.dmsm1)
                }
            }
            return list
        }

        //缓存之前状态值
        private fun setSpinnerAdapter(spinner: Spinner, list: ArrayList<String>, selectStr: String) {
            val adapter = ArrayAdapter<String>(mContext, R.layout.my_simple_spinner_item)
            adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
            synchronized(list) {
                when (spinner.id) {
                    R.id.sp_csys_a, R.id.sp_csys_b, R.id.sp_csys_c -> {
                        adapter.clear()
                        adapter.add("")
                        for (db in list) {
                            adapter.add(db)
                        }
                        spinner.adapter = adapter
                        for (index in 0 until adapter.count) {
                            var str = adapter.getItem(index)
                            if (selectStr == str) {
                                spinner.setSelection(index)
                                break
                            }
                        }
                    }
                }
            }

        }

        private fun initCsysData() {
            val activity = mContext as CYProjectJudgeActivity

            //车身颜色
            if (activity.data1!!.data.threeCertificates.csys != null && activity.data1!!.data.threeCertificates.csys.contains("/")) {
                val str = activity.data1!!.data.threeCertificates.csys.split("/")
                if (str.size == 2) {
                    OtherUtils.setSpinnerToDmsm(str[0], sp_csys_a)
                    OtherUtils.setSpinnerToDmsm(str[1], sp_csys_b)
                } else if (str.size == 3) {
                    OtherUtils.setSpinnerToDmsm(str[0], sp_csys_a)
                    OtherUtils.setSpinnerToDmsm(str[1], sp_csys_b)
                    OtherUtils.setSpinnerToDmsm(str[2], sp_csys_c)
                }
            } else { //只有一个数据
                OtherUtils.setSpinnerToDmsm(activity.data1!!.data.threeCertificates.csys, sp_csys_a)
            }
        }

        //初始化状态
        private fun setSpinnerAdapter(spinner: Spinner) {
            val adapter = ArrayAdapter<String>(mContext, R.layout.my_simple_spinner_item)
            adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
            when (spinner.id) {
                R.id.sp_csys_a, R.id.sp_csys_b, R.id.sp_csys_c -> {
                    adapter.clear()
                    for (db in csysData!!) {
                        val dmz = db.dmz
                        val dmsm1 = db.dmsm1
                        adapter.add("$dmz:$dmsm1")
                    }
                    spinner.adapter = adapter
                }
            }
        }

        private fun bindEvent() {
            CheckBoxUtils.setListener(cb_ok, cb_no)
            cb_ok!!.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    cb_ok!!.isChecked = true
                    cb_no!!.isChecked = false
                    list!![adapterPosition].order = "1"
                } else {
                    cb_ok!!.isChecked = false
                    cb_no!!.isChecked = true
                    list!![adapterPosition].order = "0"
                }
                listener!!.DataChangeListener(list!!)

            }
            cb_no!!.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    cb_no!!.isChecked = true
                    cb_ok!!.isChecked = false
                    list!![adapterPosition].order = "0"
                    listener!!.DataChangeListener(list!!)
                } else {
                    cb_no!!.isChecked = false
                    cb_ok!!.isChecked = true
                    list!![adapterPosition].order = "1"
                }
                listener!!.DataChangeListener(list!!)
            }
        }
    }

    interface DataChange {
        fun DataChangeListener(data: ArrayList<JudgeProjectEnity>)

        fun getCsysData(sp_csys_a: Spinner, sp_csys_b: Spinner, sp_csys_c: Spinner)
    }

}
