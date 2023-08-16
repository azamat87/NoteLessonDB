package kz.azamat.notelessondb.database

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

    @TypeConverter
    fun fromDate(date: Date) : Long = date.time

    @TypeConverter
    fun toDate(time: Long): Date = Date(time)

}