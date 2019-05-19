package hr.ferit.brunozoric.taskie.db

import androidx.room.TypeConverter
import hr.ferit.brunozoric.taskie.model.Priority

class PriorityConverter {

    companion object{
        @TypeConverter
        @JvmStatic
        fun fromPriority(priority: Priority): Int{
            return priority.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toPriority(priority: Int): Priority{
            return when(priority)
            {
                0-> Priority.LOW
                1-> Priority.MEDIUM
                2->Priority.HIGH
                else -> Priority.LOW
            }
        }
    }
}