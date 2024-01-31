package alevor87.android.thor.ui

import alevor87.android.thor.data.CalendarDatasource.leapYears
import alevor87.android.thor.data.CalendarDatasource.months
import alevor87.android.thor.data.CalendarUiState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalendarViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    // Finding a month code for finding the day of the week
    private fun findMonthCode(
        monthName: String = _uiState.value.month.first,
        year: Int = _uiState.value.year,
    ): Int {
        val monthCode = when (monthName) {
            "January", "October" -> 1
            "February", "March", "November" -> 4
            "April", "July" -> 0
            "May" -> 2
            "August" -> 3
            "September", "December" -> 6
            else -> 5
        }
        if (monthName == "January" && year in leapYears) return 0
        if (monthName == "February" && year in leapYears) return 3
        return monthCode
    }

    private fun findDayOfWeek(
        year: Int = _uiState.value.year,
        monthCode: Int = findMonthCode(),
    ): Int =
        (((year - 2000) + ((year - 2000) / 4) + 1 + monthCode) - 1) % 7

    private fun findDayOfWeekSpaces(dayOfWeek: Int = findDayOfWeek()): Int {
        return when (dayOfWeek) {
            2 -> 0
            3 -> 1
            4 -> 2
            5 -> 3
            6 -> 4
            0 -> 5
            else -> 6
        }
    }

    private fun addEmptySpaces(
        dayOfWeekSpaces: Int = findDayOfWeekSpaces(),
    ): List<String> {
        val listWithEmptySpaces = mutableListOf<String>()
        while (listWithEmptySpaces.size < dayOfWeekSpaces) {
            listWithEmptySpaces += " "
        }
        return listWithEmptySpaces.toList()
    }

    private fun concatenateLists(
        listOfDaysOfWeek: List<String> = _uiState.value.uiDaysOfWeek,
        listWithEmptySpaces: List<String> = addEmptySpaces(),
        listOfDays: List<Int> = _uiState.value.month.third,
    ): List<String> {
        return (listOfDaysOfWeek + listWithEmptySpaces + listOfDays)
            .map<Any, String> { it.toString() }
    }

    private val baseUpdateUi: (Int) -> (Int) -> (Triple<String, Int, List<Int>>) -> (List<String>) -> Unit =
        { year ->
            { number ->
                { month ->
                    { list ->
                        _uiState.update { calendarState ->
                            calendarState.copy(
                                year = year,
                                numberOfMonth = number,
                                listOfDaysForCalendar = list,
                                month = month
                            )
                        }
                    }
                }
            }
        }

    private fun updateYear(year: Int) =
        baseUpdateUi(year)(_uiState.value.numberOfMonth)(_uiState.value.month)(_uiState.value.listOfDaysForCalendar)

    private fun updateNumber(number: Int) =
        baseUpdateUi(_uiState.value.year)(number)(_uiState.value.month)(_uiState.value.listOfDaysForCalendar)

    private fun updateMonth(month: Triple<String, Int, List<Int>>) =
        baseUpdateUi(_uiState.value.year)(_uiState.value.numberOfMonth)(month)(_uiState.value.listOfDaysForCalendar)

    private fun updateListOfDaysForCalendar() =
        baseUpdateUi(_uiState.value.year)(_uiState.value.numberOfMonth)(_uiState.value.month)(
            concatenateLists()
        )

    fun changeYear(yearPlus: Boolean) {

        val year = if (yearPlus) {
            _uiState.value.year.plus(1)
        } else {
            _uiState.value.year.minus(1)
        }

        when (year) {
            2023 -> updateYear(2054)
            2055 -> updateYear(2024)
            else -> updateYear(year)
        }

        if (_uiState.value.year in leapYears && _uiState.value.numberOfMonth == 1) {
            updateMonth(months[12])
        } else updateMonth(months[_uiState.value.numberOfMonth])

        updateListOfDaysForCalendar()
    }

    fun updateMonth(monthPlus: Boolean) {
        if (monthPlus) {
            updateNumber(_uiState.value.numberOfMonth.plus(1))
        }

        if (_uiState.value.numberOfMonth == 12) {
            updateNumber(0)
        }

        if (!monthPlus) {
            updateNumber(_uiState.value.numberOfMonth.minus(1))
        }

        if (_uiState.value.numberOfMonth == -1) {
            updateNumber(11)
        }

        if (_uiState.value.year in leapYears
            && _uiState.value.numberOfMonth == 1
        ) {
            updateMonth(months[12])
        } else {
            updateMonth(months[_uiState.value.numberOfMonth])
        }

        updateListOfDaysForCalendar()
    }
}