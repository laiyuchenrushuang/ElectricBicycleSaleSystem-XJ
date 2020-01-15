package com.seatrend.xj.electricbicyclesalesystem.adpater

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import java.util.ArrayList

/**
 * Created by ly on 2019/7/2 17:43
 *
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class CarInfoPagerAdapter(fragmetnmanager: FragmentManager  //创建FragmentManager
                          , listf: ArrayList<Fragment>) : FragmentPagerAdapter(fragmetnmanager) {
    private val listfragment: List<Fragment> //创建一个List<Fragment>

    init {
        this.listfragment = listf
    }

    override fun getItem(position: Int): Fragment {
        return listfragment[position]
    }

    override fun getCount(): Int {
        return listfragment.size
    }
}
