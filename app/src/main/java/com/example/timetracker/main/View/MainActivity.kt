package com.example.timetracker.main.View

import android.app.AlertDialog
import android.app.AppOpsManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.timetracker.R
import com.example.timetracker.databinding.ActivityMainBinding
import com.example.timetracker.main.Adapters.ViewPagerAdapter
import com.example.timetracker.main.Fragments.PlanFragment
import com.example.timetracker.main.Fragments.ReminderFragment
import com.example.timetracker.main.Fragments.UsageFragment
import com.example.timetracker.main.Utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity: AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Find the NavController
        var navController = findNavController(R.id.navHostFragment)

        // Set up ActionBar with NavController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.mainFragment, R.id.planFragment,R.id.reminderFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavView.setupWithNavController(navController)
        // Set up BottomNavigationView

        binding.bottomNavView.setOnNavigationItemSelectedListener  { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item_time_consume -> navController.navigate(R.id.mainFragment)
                R.id.nav_item_plans -> navController.navigate(R.id.planFragment)
                R.id.nav_item_reminder -> navController.navigate(R.id.reminderFragment)
            }
            true
        }
        if ( checkUsageStatsPermission() ) {
            pinDialog()
        }
        else {
            // Navigate the user to the permission settings
            Intent( Settings.ACTION_USAGE_ACCESS_SETTINGS ).apply {
                startActivity( this )
            }
        }

    }



    private fun pinDialog()
    {
        val dialogBuilder = AlertDialog.Builder(this)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.pin_dialog, null)
        alertDialog.window?.setContentView(dialogView)
        alertDialog.window?.clearFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        )
        alertDialog.setCanceledOnTouchOutside(false)
        val buttonSubmit= dialogView.findViewById<View>(R.id.buttonSubmit) as Button
        val edtPin = dialogView.findViewById<View>(R.id.edtPin) as EditText
        buttonSubmit.setOnClickListener {
            if(edtPin.text.toString() == "9991")
            {
                alertDialog.dismiss()
            }
            else
            {
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkUsageStatsPermission() : Boolean {
        val appOpsManager = getSystemService(AppCompatActivity.APP_OPS_SERVICE) as AppOpsManager
        // `AppOpsManager.checkOpNoThrow` is deprecated from Android Q
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOpsManager.unsafeCheckOpNoThrow(
                "android:get_usage_stats",
                Process.myUid(), packageName
            )
        }
        else {
            appOpsManager.checkOpNoThrow(
                "android:get_usage_stats",
                Process.myUid(), packageName
            )
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

}