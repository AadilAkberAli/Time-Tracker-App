package com.example.timetracker.main.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timetracker.R
import com.example.timetracker.main.Dialog.TimeInputDialogFragment
import com.example.timetracker.main.Model.AppDataReminder
import com.example.timetracker.main.Utils.Utility
import com.example.timetracker.main.ViewModel.ReminderViewModel
import java.util.*
import kotlin.collections.ArrayList


class appReminderDataAdapter(
    private val mList: ArrayList<AppDataReminder>,
    private val context: Context,
    private val reminderViewModel: ReminderViewModel,
    private val parentFragmentManager: FragmentManager
) : RecyclerView.Adapter<appReminderDataAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appreminderdata_items, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageBitmap(Utility.decodeImage(ItemsViewModel.imageView.toString()))

        // sets the text to the textview from our itemHolder class
        holder.textView.text = Utility.getAppLabel(ItemsViewModel.packageName.toString(),context)

        holder.toggleButton.isChecked = ItemsViewModel.isReminder == true
        // sets the text to the textview from our itemHolder class

        if(ItemsViewModel.reminderTime != null)
        {
            holder.textView1.text = Utility.convertLongToTime(ItemsViewModel.reminderTime!!, ItemsViewModel.currentTime!!)
        }
        else
        {
            holder.textView1.text = "No Reminder"
        }

        holder.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val dialogFragment = TimeInputDialogFragment()
                dialogFragment.setListener(object : TimeInputDialogFragment.TimeInputListener {
                    override fun onTimeInputProvided(hours: Int, minutes: Int, seconds: Int) {
                        // Use the provided hours, minutes, and seconds here
                        // For example, you can convert them to milliseconds or perform any other action
                        val currentTime = Calendar.getInstance()
                        holder.textView1.text =  Utility.getTimeConversion(currentTime,Utility.getTimeDifference(hours, minutes, seconds))
                        reminderViewModel.updateReminder(ItemsViewModel.packageName.toString(), isChecked,currentTime.timeInMillis, Utility.getTimeDifference(hours, minutes, seconds).timeInMillis)
                    }
                })
                dialogFragment.show(parentFragmentManager, "timeInputDialog")
            } else {
                // Toggle button is unchecked/off
                // Do something else
                reminderViewModel.updateReminder(ItemsViewModel.packageName.toString(), isChecked, null, null)
                holder.textView1.text = "No Reminder"
            }
        }

    }

    fun clear()
    {
        mList.clear()
        notifyDataSetChanged()
    }

    fun addAll(items: ArrayList<AppDataReminder>)
    {
        mList.addAll(items)
        notifyDataSetChanged()
    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView_AppData)
        val toggleButton: ToggleButton = itemView.findViewById(R.id.toggleButton)
        val textView: TextView = itemView.findViewById(R.id.heading)
        val textView1: TextView = itemView.findViewById(R.id.subHeading)
    }
}
