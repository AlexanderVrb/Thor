package alevor87.android.thor.data

import alevor87.android.thor.R

object CalendarDatasource {

    val leapYears = listOf(2024, 2028, 2032, 2036, 2040, 2044, 2048, 2052)

    val daysOfWeek = listOf("П", "В", "С", "Ч", "П", "С", "В")

    val months = listOf(
        Triple("January", R.string.January, (1..31).toList()),
        Triple("February", R.string.February, (1..28).toList()),
        Triple("March", R.string.March, (1..31).toList()),
        Triple("April", R.string.April, (1..30).toList()),
        Triple("May", R.string.May, (1..31).toList()),
        Triple("June", R.string.June, (1..30).toList()),
        Triple("July", R.string.July, (1..31).toList()),
        Triple("August", R.string.August, (1..31).toList()),
        Triple("September", R.string.September, (1..30).toList()),
        Triple("October", R.string.October, (1..31).toList()),
        Triple("November", R.string.November, (1..30).toList()),
        Triple("December", R.string.December, (1..31).toList()),
        Triple("February", R.string.February, (1..29).toList())
    )
}



