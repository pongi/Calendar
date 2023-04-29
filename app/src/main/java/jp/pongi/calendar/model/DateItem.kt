package jp.pongi.calendar.model

import java.io.Serializable
import java.util.*

data class DateItem(val calendar: Calendar, val today : Boolean = false): Serializable