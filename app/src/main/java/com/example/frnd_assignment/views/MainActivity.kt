package com.example.frnd_assignment.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.frnd_assignment.R
import com.example.frnd_assignment.viewmodels.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnCalendarCellListener {

    private val viewModel: TaskViewModel by viewModels()
    private lateinit var selectedDate: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_main)
        viewModel.getTasksFromServer()
        initDate()
        setButtonActions()
        inflateMonthView()
    }


    private fun initDate() {
        selectedDate = LocalDate.now()
    }

    private fun inflateMonthView() {
        //tv_date_header.text = "${selectedDate.dayOfMonth} ${selectedDate.month} ${selectedDate.year}"
        tv_date_header.text =
            "${dayFromDate(selectedDate)} ${monthFromDate(selectedDate)} ${yearFromDate(selectedDate)}"
        val daysInMonth = daysInMonth(selectedDate)

        val calendarAdapter = CalendarAdapter(daysInMonth, this, selectedDate)
        calenderRecyclerView.layoutManager = GridLayoutManager(this, 7)
        calenderRecyclerView.adapter = calendarAdapter

    }

    private fun daysInMonth(date: LocalDate): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()

        val firstDayOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstDayOfMonth.dayOfWeek.value

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }

        return daysInMonthArray
    }



    private fun yearFromDate(date: LocalDate): String {
        val formatPattern = DateTimeFormatter.ofPattern("yyyy")
        return date.format(formatPattern)
    }

    private fun monthFromDate(date: LocalDate): String {
        val formatPattern = DateTimeFormatter.ofPattern("MMMM")
        return date.format(formatPattern)
    }

    private fun dayFromDate(date: LocalDate): String {
        val formatPattern = DateTimeFormatter.ofPattern("dd")
        return date.format(formatPattern)
    }

    private fun setButtonActions() {
        btn_nxt_month.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            inflateMonthView()
        }

        btn_pre_month.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            inflateMonthView()
        }

        tv_date_header.setOnClickListener {

        }

        btn_open_task_activity.setOnClickListener {
            startActivity(Intent(this, TaskListActivity::class.java))
        }
    }

    override fun onCellClick(position: Int, day: String, month: String, year: String) {
        tv_selected_date_show.text = "Selected Date: ${day}-${month}-${year}"
    }


}
//        val deleteReq = hashMapOf<String, Int>()
//        deleteReq["user_id"] = userId
//        deleteReq["task_id"] = 21
//
//        CoroutineScope(Main).launch {
//            val res = viewModel.deleteTaskFromServer(deleteReq)
//        }


//        val x = TaskDetail("07-07-2021", "desc_app_dummy", "Interview_07")
//       val tsk = StoreTaskRequest(x,userId)
//        CoroutineScope(Main).launch {
//            viewModel.storeTaskOnServer(tsk)
//        }


//        val x = hashMapOf<String,Int>()
//        x["user_id"] = 1012
//        CoroutineScope(Main).launch {