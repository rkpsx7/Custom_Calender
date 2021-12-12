package com.example.frnd_assignment.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.frnd_assignment.R
import com.example.frnd_assignment.calendar.CalendarAdapter
import com.example.frnd_assignment.calendar.OnCalendarCellClickListener
import com.example.frnd_assignment.viewmodels.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.month_year_chooser.view.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import kotlin.math.abs


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnCalendarCellClickListener,
    AdapterView.OnItemSelectedListener {

    private val viewModel: TaskViewModel by viewModels()
    private lateinit var selectedDate: LocalDate
    private var dateForAddActivity = ""
    private var spiMonth = 0L
    private var spiYear = 0L
    private var currMonth = 0L
    private var NoOfTasks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_main)
        getTaskFromServer()
        initDate()
        setButtonActions()
        inflateMonthView()

    }

    private fun getTaskFromServer() {
        viewModel.getTasksFromServer()
    }

    private fun initDate() {
        selectedDate = LocalDate.now()
        dateForAddActivity =
            "${selectedDate.dayOfMonth}-${selectedDate.month.value}-${selectedDate.year}"
        tv_selected_date_show.text = "Selected Date: $dateForAddActivity"
        spiMonth = monthFromDateInDigit(selectedDate).toLong()
        currMonth = spiMonth
        spiYear = yearFromDate(selectedDate).toLong()
        getTotalTasks()
    }

    private fun inflateMonthView() {
        //tv_date_header.text = "${selectedDate.dayOfMonth} ${selectedDate.month} ${selectedDate.year}"
        tv_date_header.text =
            "${dayFromDate(selectedDate)} ${monthFromDate(selectedDate)} ${yearFromDate(selectedDate)}"
        val daysInMonth = daysInMonth(selectedDate)

        val calendarAdapter = CalendarAdapter(daysInMonth, this, selectedDate)
        calenderRecyclerView.layoutManager = GridLayoutManager(this, 7)
        calenderRecyclerView.adapter = calendarAdapter
        dateForAddActivity = "${selectedDate.dayOfMonth}-${selectedDate.month.value}-${selectedDate.year}"
        tv_selected_date_show.text = "Selected Date: $dateForAddActivity"
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

    private fun monthFromDateInDigit(date: LocalDate): String {
        val formatPattern = DateTimeFormatter.ofPattern("MM")
        return date.format(formatPattern)
    }

    private fun dayFromDate(date: LocalDate): String {
        val formatPattern = DateTimeFormatter.ofPattern("dd")
        return date.format(formatPattern)
    }

    private fun setButtonActions() {
        btn_nxt_month.setOnClickListener {
            nextMonth(1)
        }

        btn_pre_month.setOnClickListener {
            prevMonth(1)
        }

        btn_add_new_task.setOnClickListener {
            val intent = Intent(this, AddNewTaskActivity::class.java)
            intent.putExtra("date", dateForAddActivity)
            startActivity(intent)
        }

        tv_date_header.setOnClickListener {
            chooseDate()
        }

        btn_open_task_activity.setOnClickListener {
            startActivity(Intent(this, TaskListActivity::class.java))
        }
    }


    override fun onCellClick(position: Int, day: String, month: String, year: String) {
        if (day.isNotEmpty()) {
            dateForAddActivity = "${day}-${month}-${year}"
            tv_selected_date_show.text = "Selected Date: $dateForAddActivity"
            getTotalTasks()
        }
    }

    private fun getTotalTasks() {
        viewModel.getNoOfTasks(dateForAddActivity).observe(this, {
            NoOfTasks = it
            tv_total_tasks.text = "Total Tasks on selected day: $NoOfTasks"
        })
    }

    private fun chooseDate() {
        val view = LayoutInflater.from(this).inflate(R.layout.month_year_chooser, null)
        val monthYearDialog = AlertDialog.Builder(this)
            .setView(view)
            .create()

        monthYearDialog.show()

        val monthsArrayAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.Months,
            android.R.layout.simple_spinner_item
        )

        val yearsArrayAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.Years,
            android.R.layout.simple_spinner_item
        )

        monthsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.month_spinner.adapter = monthsArrayAdapter
        view.year_spinner.adapter = yearsArrayAdapter
        view.month_spinner.setSelection(monthFromDateInDigit(selectedDate).toInt() - 1)
        view.year_spinner.setSelection(yearFromDate(selectedDate).toInt() - 1990)
        view.month_spinner.onItemSelectedListener = this
        view.year_spinner.onItemSelectedListener = this


        view.alt_dig_select_btn.setOnClickListener {
            if (spiYear > 0)
                prevMonth(12 * spiYear)
            else
                nextMonth(12 * abs(spiYear))

            val preserveCurrMonthValue = spiMonth
            spiMonth = currMonth - spiMonth
            if (spiMonth > 0)
                prevMonth(spiMonth)
            else
                nextMonth(abs(spiMonth))
            currMonth = abs(preserveCurrMonthValue)

            monthYearDialog.cancel()
        }
        view.alt_dig_cancel_btn.setOnClickListener {
            monthYearDialog.cancel()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.month_spinner -> {
                val month = parent.selectedItemPosition + 1
                spiMonth = month.toLong()
            }
            R.id.year_spinner -> {
                val year =
                    yearFromDate(selectedDate).toInt() - parent.selectedItem.toString().toInt()
                spiYear = year.toLong()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun nextMonth(value: Long) {
        selectedDate = selectedDate.plusMonths(value)
        inflateMonthView()
    }

    private fun prevMonth(value: Long) {
        selectedDate = selectedDate.minusMonths(value)
        inflateMonthView()
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