package com.example.timetracker.main.Adapters

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.timetracker.main.Fragments.UsageFragment
import com.example.timetracker.main.Utils.Constants

class ViewPagerAdapter(fm: FragmentManager, lifeCycle: Lifecycle) :
    FragmentStateAdapter(fm, lifeCycle) {

    override fun getItemCount(): Int {
        return  3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val bundle = Bundle()
        if (position == 0) {
            fragment = UsageFragment()
            bundle.putString(Constants.usage, Constants.dailyUsage)
            fragment?.arguments = bundle
        } else if (position == 1) {
            fragment = UsageFragment()
            bundle.putString(Constants.usage, Constants.weekyUsage)
            fragment?.arguments = bundle
        } else if (position == 2) {
            fragment = UsageFragment()
            bundle.putString(Constants.usage, Constants.monthlyUsage)
            fragment?.arguments = bundle
        }
        return fragment!!
    }
}