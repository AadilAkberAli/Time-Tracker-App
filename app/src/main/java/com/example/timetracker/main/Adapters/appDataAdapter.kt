package com.example.timetracker.main.Adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.format.DateUtils
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timetracker.R
import com.example.timetracker.main.Model.AppData
import com.example.timetracker.main.Utils.Utility
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class appDataAdapter(private val mList: ArrayList<AppData>) : RecyclerView.Adapter<appDataAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appdata_items, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageBitmap(Utility.decodeImage(ItemsViewModel.imageView.toString()))

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.packageName

        // sets the text to the textview from our itemHolder class
        holder.textView1.text = "Time Consume: " + DateUtils.formatElapsedTime(ItemsViewModel.time?.div(1000) ?:0 )

        // sets the text to the textview from our itemHolder class
        holder.textView2.text = "Last time used: " + convertLongToTime(
            ItemsViewModel.lastUsed
            ?: 0)

    }

    fun clear()
    {
        mList.clear()
        notifyDataSetChanged()
    }

    fun addAll(items: ArrayList<AppData>)
    {
        mList.addAll(items)
        notifyDataSetChanged()
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd/MM/yyyy hh:mm a")
        return format.format(date)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView_AppData)
        val textView: TextView = itemView.findViewById(R.id.heading)
        val textView1: TextView = itemView.findViewById(R.id.subHeading)
        val textView2: TextView = itemView.findViewById(R.id.subHeadingLastUsed)
    }
}
