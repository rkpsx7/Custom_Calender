package com.example.frnd_assignment.calendar

interface OnCalendarCellClickListener {
    fun onCellClick(position: Int, day: String, month: String, year: String)
}