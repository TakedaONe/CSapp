package com.example.fcapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TimePicker
import android.widget.Toast
import com.example.fcapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class calendarFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var calendarView: CalendarView
    private lateinit var timePicker: TimePicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        database = FirebaseDatabase.getInstance().reference

        calendarView = view.findViewById(R.id.calendarView)
        timePicker = view.findViewById(R.id.timePicker)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            val selectedTime = "${timePicker.hour}:${timePicker.minute}"
            saveCleaningDate(selectedDate, selectedTime)
        }

        return view
    }

    private fun saveCleaningDate(date: String, time: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "default_user"
        val cleaningDetails = mapOf("date" to date, "time" to time)

        database.child("cleanings").child(userId).setValue(cleaningDetails)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Cleaning date saved", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error saving cleaning date", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
