package com.example.timetracker.main.Dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.timetracker.R

class TimeInputDialogFragment : DialogFragment() {

    interface TimeInputListener {
        fun onTimeInputProvided(hours: Int, minutes: Int, seconds: Int)
    }

    private lateinit var listener: TimeInputListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_custom_time_picker, null)

        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Reminder")
            .setView(view)
            .setPositiveButton("OK") { _, _ ->
                val hours = view.findViewById<NumberPicker>(R.id.hourPicker).value
                val minutes = view.findViewById<NumberPicker>(R.id.minutePicker).value
                val seconds = view.findViewById<NumberPicker>(R.id.secondPicker).value

                listener.onTimeInputProvided(hours, minutes, seconds)
            }
            .setNegativeButton("Cancel", null)
        // Set up NumberPicker minimum and maximum values
        view.findViewById<NumberPicker>(R.id.hourPicker).minValue = 0
        view.findViewById<NumberPicker>(R.id.hourPicker).maxValue = 23
        view.findViewById<NumberPicker>(R.id.minutePicker).minValue = 0
        view.findViewById<NumberPicker>(R.id.minutePicker).maxValue = 59
        view.findViewById<NumberPicker>(R.id.secondPicker).minValue = 0
        view.findViewById<NumberPicker>(R.id.secondPicker).maxValue = 59
        return builder.create()
    }

    fun setListener(listener: TimeInputListener) {
        this.listener = listener
    }
}
