package alevor87.android.thor.data

import alevor87.android.thor.data.CalendarDatasource.daysOfWeek
import alevor87.android.thor.data.CalendarDatasource.months

data class CalendarUiState(
    val year: Int = 2024,
    val numberOfMonth: Int = 0,
    val month: Triple<String, Int, List<Int>> = months[numberOfMonth],
    val uiDaysOfWeek: List<String> = daysOfWeek,
    val listOfDaysForCalendar: List<String> = daysOfWeek + month.third.map { it.toString() },
    val listForIdentifyingColor: List<String> = daysOfWeek + " "
)