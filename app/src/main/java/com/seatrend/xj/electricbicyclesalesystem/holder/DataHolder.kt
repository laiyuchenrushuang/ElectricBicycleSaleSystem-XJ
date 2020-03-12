package com.seatrend.xj.electricbicyclesalesystem.holder

import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.HashMap


/**
 * Created by ly on 2020/3/11 10:58
 *
 * 扩展组建之间大数据传递接口
 */
class DataHolder {



    //方便释放
    var data: MutableMap<String, WeakReference<Any>> = HashMap<String, WeakReference<Any>>()

    //单例模式
    companion object {
        val instance: DataHolder by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DataHolder()
        }
    }


    /**
     * 储存的save方法
     * id  储存的key
     * obj 储存的对象
     */

    fun save(id: String, obj: Any) {
        data[id] = WeakReference(obj)
    }

    /**
     * 返回储存的对象 Any
     * id 储存的key
     */
    fun retrieve(id: String): Any?{
        val objectWeakReference = data[id]
        return objectWeakReference?.get()
    }
}