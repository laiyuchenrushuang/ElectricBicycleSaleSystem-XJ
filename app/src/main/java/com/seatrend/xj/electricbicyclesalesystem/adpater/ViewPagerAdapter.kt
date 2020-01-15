package com.seatrend.xj.electricbicyclesalesystem.adpater

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Administrator on 2017/12/22.
 */

class ViewPagerAdapter(fm: FragmentManager, private val list: List<Fragment>, private val title: List<String>?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (title == null) null else title[position]
    }
}
