package kz.xbase.mstroy.fragments.analytics

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import com.kizitonwose.calendarview.utils.yearMonth
import kotlinx.android.synthetic.main.calendar_day_legend.view.*
import kotlinx.android.synthetic.main.example_5_calendar_day.view.*
import kotlinx.android.synthetic.main.example_5_calendar_header.view.*
import kotlinx.android.synthetic.main.example_5_calendar_header.view.legendLayout
import kotlinx.android.synthetic.main.fragment_personal_item.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.daysOfWeekFromLocale
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class PersonalItemFragment : Fragment() {
    private var selectedDate: LocalDate? = null
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal_item,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        exFiveCalendar.setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
        exFiveCalendar.scrollToMonth(currentMonth)
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        if (selectedDate != day.date) {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            exFiveCalendar.notifyDateChanged(day.date)
                            oldDate?.let { exFiveCalendar.notifyDateChanged(it) }
                        }
                    }
                }
            }
        }
        exFiveCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.view.exFiveDayText
                textView.text = day.date.dayOfMonth.toString()
                val cardView : MaterialCardView = container.view as MaterialCardView
                if (day.date == selectedDate){
                    textView.setTextColor(resources.getColor(R.color.black))
                    cardView.strokeColor = resources.getColor(R.color.white)
                    cardView.setCardBackgroundColor(resources.getColor(R.color.white))
                }
                if(day.owner != DayOwner.THIS_MONTH && day.date != selectedDate){
                    textView.setTextColor(resources.getColor(R.color.grey))
                    cardView.strokeColor = resources.getColor(R.color.grey)
                    cardView.setCardBackgroundColor(resources.getColor(R.color.black))
                }else if(day.date != selectedDate){
                    textView.setTextColor(resources.getColor(R.color.white))
                    cardView.strokeColor = resources.getColor(R.color.white)
                    cardView.setCardBackgroundColor(resources.getColor(R.color.black))
                }
            }
        }
        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = view.legendLayout.rootView
        }
        exFiveCalendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    month.yearMonth
                }
            }
        }
        exFiveCalendar.monthScrollListener = { month ->
            val title = "${monthTitleFormatter.format(month.yearMonth)} ${month.yearMonth.year}"
            exFiveMonthYearText.text = title
        }
        exFiveNextMonthImage.setOnClickListener {
            exFiveCalendar.findFirstVisibleMonth()?.let {
                exFiveCalendar.smoothScrollToMonth(it.yearMonth.next)
            }
        }
        exFivePreviousMonthImage.setOnClickListener {
            exFiveCalendar.findFirstVisibleMonth()?.let {
                exFiveCalendar.smoothScrollToMonth(it.yearMonth.previous)
            }
        }
        selectedDate = LocalDate.now()
        exFiveCalendar.notifyDateChanged(LocalDate.now())
    }
    companion object{
        @JvmStatic
        fun newInstance() = PersonalItemFragment()
    }
}