package com.example.timetracker.main.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timetracker.databinding.FragmentMainBinding
import com.example.timetracker.databinding.FragmentUsageBinding
import com.example.timetracker.main.Adapters.ViewPagerAdapter
import com.example.timetracker.main.Utils.Constants
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    lateinit var binding : FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // bind the viewPager with the TabLayout.
        // Initializing the ViewPagerAdapter
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)

        // Adding the Adapter to the ViewPager
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = Constants.dailyUsage
                1 -> tab.text = Constants.weekyUsage
                2 -> tab.text = Constants.monthlyUsage
            }
        }.attach()

        // bind the viewPager with the TabLayout.
        return  root
    }
}
