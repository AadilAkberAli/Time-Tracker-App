package com.example.timetracker.main.Fragments

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timetracker.R
import com.example.timetracker.databinding.FragmentUsageBinding
import com.example.timetracker.main.Adapters.ViewPagerAdapter
import com.example.timetracker.main.Adapters.appDataAdapter
import com.example.timetracker.main.Model.AppData
import com.example.timetracker.main.Utils.Constants
import com.example.timetracker.main.ViewModel.MainActivityViewModel
import com.example.timetracker.main.ViewModel.UsageViewModel
import com.example.timetracker.main.ViewModel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UsageFragment : Fragment() {

    private lateinit var usageViewModel: UsageViewModel
    lateinit var binding: FragmentUsageBinding
    lateinit var appDataAdapter: appDataAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        usageViewModel =
            ViewModelProvider(this, ViewModelFactory(requireContext()))[UsageViewModel::class.java]
        val linearLayoutManager =  LinearLayoutManager(requireContext())

        appDataAdapter = appDataAdapter(ArrayList())
        binding.recyclerViewAppData.adapter = appDataAdapter
        binding.recyclerViewAppData.layoutManager = linearLayoutManager

        usageViewModel.appLists.observe(viewLifecycleOwner){
            if(it.isNotEmpty())
            {
                binding.progressBar.visibility = View.GONE
                calculateTime(it)
            }
        }
        return  root
    }

    override fun onResume() {
        super.onResume()
        binding.progressBar.visibility = View.VISIBLE
        binding.heading.visibility = View.GONE
        appDataAdapter.clear()
        if(arguments?.getString(Constants.usage) == Constants.dailyUsage)
        {
            binding.dailyTitle.text = "Apps used in last 24 hours"
            usageViewModel.getForegroundActivity(Constants.dailyUsage,requireContext())
        }
        else if(arguments?.getString(Constants.usage) == Constants.weekyUsage)
        {
            binding.dailyTitle.text = "Apps used in a week"
            usageViewModel.getForegroundActivity(Constants.weekyUsage,requireContext())
        }
        else
        {
            binding.dailyTitle.text = "Apps used in a month"
            usageViewModel.getForegroundActivity(Constants.monthlyUsage,requireContext())
        }
    }


    private fun calculateTime(it: List<AppData>) {
        val formatter: Format = SimpleDateFormat("dd/MM/yyyy")
        val today: String = formatter.format(Date())
        var totalTime = 0L
        val sortedAppsList = ArrayList(it.sortedBy { it.packageName })
        if(arguments?.getString(Constants.usage) == Constants.dailyUsage)
        {
            sortedAppsList.removeAll{ it.lastUsed?.let { it1 -> convertLongToTime(it1) } != today }
        }
        sortedAppsList.forEach {
            if(arguments?.getString(Constants.usage) == Constants.dailyUsage)
            {
                if(it.lastUsed?.let { it1 -> convertLongToTime(it1) } == today)
                {
                    totalTime += it.time!!
                }
            }
            else
            {
                totalTime += it.time!!
            }
        }
        appDataAdapter.addAll(ArrayList(sortedAppsList) as ArrayList<AppData>)
        val finalTotalTime = DateUtils.formatElapsedTime(totalTime?.div(1000) ?:0 )
        val time = finalTotalTime.split(":")
        if(time.size == 3)
        {
            binding.heading.text = time[0] + " hr " + time[1] + " min " + time[2] + " sec"
        }
        else if (time.size == 2 && time[0] != "00")
        {
            binding.heading.text =  time[0] + " min " + time[1] + " sec "
        }
        else
        {
            binding.heading.text = time[0] + " sec"
        }
        binding.heading.visibility = View.VISIBLE
    }


    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(date)
    }

}