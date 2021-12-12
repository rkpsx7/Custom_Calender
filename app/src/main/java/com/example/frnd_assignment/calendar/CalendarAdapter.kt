package com.example.frnd_assignment.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.frnd_assignment.R
import kotlinx.android.synthetic.main.calender_cell.view.*
import java.time.LocalDate

class CalendarAdapter(
    private val daysOfMonth: ArrayList<String>,
    private val onCalendarCellClickListener: OnCalendarCellClickListener,
    private val selectedDate: LocalDate
) :
    RecyclerView.Adapter<CalendarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.calender_cell, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val day = daysOfMonth[position]
        holder.setDate(day, selectedDate)
        holder.calendarCellClick.setOnClickListener {
            onCalendarCellClickListener.onCellClick(
                position,
                day,
                selectedDate.month.value.toString(),
                selectedDate.year.toString()
            )
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }
}

class CalendarViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val calendarCellClick: LinearLayout = view.findViewById(R.id.calendarCellClick)
    private val currDate = LocalDate.now()

    fun setDate(day: String, selectedDate: LocalDate) {
        view.apply {
            tv_calender_cell.text = day
            if (day == selectedDate.dayOfMonth.toString() && currDate.month == selectedDate.month && currDate.year == selectedDate.year)
                tv_calender_cell.setBackgroundResource(R.drawable.selected_cell_bg)
        }
    }
}