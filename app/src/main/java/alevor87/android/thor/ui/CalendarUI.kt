package alevor87.android.thor.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowLeft
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun CalendarUI(calendarViewModel: CalendarViewModel = viewModel()) {

    val calendarUiState by calendarViewModel.uiState.collectAsState()

    Column {
        Spacer(modifier = Modifier.height(24.dp))
        YearField(
            year = calendarUiState.year,
            plusYear = { calendarViewModel.changeYear(true) },
            minusYear = { calendarViewModel.changeYear(false) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        MonthField(
            month = calendarUiState.month.second,
            plusMonth = { calendarViewModel.updateMonth(monthPlus = true) },
            minusMonth = { calendarViewModel.updateMonth(monthPlus = false) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            items(calendarUiState.listOfDaysForCalendar) {
                CalendarButton(
                    dayNumber = it,
                    listForIdentifyingColors = calendarUiState.listForIdentifyingColor
                )
            }
        }
    }
}

@Composable
fun CalendarButton(
    dayNumber: String,
    listForIdentifyingColors: List<String>,
) {
    TextButton(onClick = { /*TODO*/ }) {
        Text(
            text = dayNumber,
            color = if (dayNumber in listForIdentifyingColors) Color.Red
            else Color.Unspecified
        )
    }
}

//@Preview
//@Composable
//fun PreviewCalendarButton() {
//    CalendarButton(dayNumber = "1")
//}

//@Preview
//@Composable
//fun PreviewCalendarUI() {
//    CalendarUI(calendarButtons =
//    listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))
//}

@Composable
fun MonthField(month: Int, plusMonth: () -> Unit, minusMonth: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Sharp.KeyboardArrowLeft,
            contentDescription = null,
            modifier = Modifier.clickable(onClick = minusMonth)
        )
        Text(
            text = stringResource(month),
            textAlign = TextAlign.Center,
            modifier = Modifier.width(80.dp)
        )
        Icon(
            Icons.Sharp.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.clickable(onClick = plusMonth)
        )
    }
}

@Composable
fun YearField(year: Int, plusYear: () -> Unit, minusYear: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Sharp.KeyboardArrowLeft,
            contentDescription = null,
            modifier = Modifier.clickable(onClick = minusYear)
        )
        Text(text = year.toString())
        Icon(
            Icons.Sharp.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.clickable(onClick = plusYear)
        )
    }
}

//@Composable
//fun DaysOfWeek() {
//    Row {
//
//    }
//}