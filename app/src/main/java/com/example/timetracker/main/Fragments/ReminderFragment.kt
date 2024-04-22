package com.example.timetracker.main.Fragments

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startForegroundService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timetracker.databinding.FragmentReminderBinding
import com.example.timetracker.main.Adapters.appReminderDataAdapter
import com.example.timetracker.main.Service.NotificationService
import com.example.timetracker.main.Utils.Constants
import com.example.timetracker.main.ViewModel.ReminderViewModel
import com.example.timetracker.main.ViewModel.ViewModelFactory
import java.util.*
import kotlin.collections.ArrayList

class ReminderFragment : Fragment(){

    lateinit var binding : FragmentReminderBinding
    lateinit var reminderViewModel: ReminderViewModel
    lateinit var appReminderAdapter:appReminderDataAdapter
    private var isBound = false
    private var notificationService: NotificationService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as NotificationService.LocalBinder
            notificationService = binder.getService()
            val notification = notificationService!!.createNotification("Reminder", "Don't forget your task!")
            notificationService!!.showNotification(1, notification)
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            notificationService = null
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            requireContext().unbindService(connection)
        }
    }

    private fun startService()
    {

        val serviceIntent = Intent(requireContext(), NotificationService::class.java)
        requireContext().bindService(serviceIntent, connection, BIND_AUTO_CREATE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(requireContext(),serviceIntent)
        } else {
            requireContext().startService(serviceIntent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReminderBinding.inflate(inflater, container, false)
        reminderViewModel = ViewModelProvider(this, ViewModelFactory(requireContext()))[ReminderViewModel::class.java]
        val root: View = binding.root
        val linearLayoutManager =  LinearLayoutManager(requireContext())

        appReminderAdapter = appReminderDataAdapter(ArrayList(),requireContext(),reminderViewModel, parentFragmentManager)
        binding.recyclerViewAppData.adapter = appReminderAdapter
        binding.recyclerViewAppData.layoutManager = linearLayoutManager
        binding.cardViewRecyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        reminderViewModel.getApps(requireContext())
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.POST_NOTIFICATIONS), Constants.POST_PERMISSION)
            }
        }
        else
        {
            startService()
        }
        reminderViewModel.appLists.observe(viewLifecycleOwner){
            if(it.isNotEmpty())
            {
                appReminderAdapter.clear()
                binding.noItem.visibility = View.GONE
                binding.cardViewRecyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                appReminderAdapter.addAll(it)
                reminderViewModel.insertAppData(it)
            }
            else
            {
                binding.cardViewRecyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.noItem.visibility = View.VISIBLE
            }
        }
        return  root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.POST_PERMISSION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               startService()
            } else {
                Log.e("value", "Permission Denied, Now you can not use Notification .")
            }
        }
    }


}