package me.skylands.skypvp.util

import java.util.*
import java.util.concurrent.TimeUnit

object Formatter {

    /**
     * Converts the given amount of millis into a formatted String (days, hours, minutes, seconds)
     */
    fun formatMillis(millis: Long): String {
        var millisTmp = millis
        val days = TimeUnit.MILLISECONDS.toDays(millisTmp)
        millisTmp -= TimeUnit.DAYS.toMillis(days)
        val hours = TimeUnit.MILLISECONDS.toHours(millisTmp)
        millisTmp -= TimeUnit.HOURS.toMicros(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisTmp)
        millisTmp -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisTmp)
        val results: MutableList<String> = LinkedList()
        if (days != 0L) {
            results.add(days.toString() + "d")
        }
        if (hours != 0L) {
            results.add(hours.toString() + "h")
        }
        if (minutes != 0L) {
            results.add(minutes.toString() + "m")
        }
        if (seconds != 0L) {
            results.add(seconds.toString() + "s")
        }
        return if (results.isEmpty()) {
            "1ms"
        } else java.lang.String.join(", ", results)
    }

}