package me.skylands.skypvp.util

import java.util.*
import java.util.concurrent.TimeUnit

object Formatter {

    /**
     * Converts the given amount of millis into a formatted String (days, hours, minutes, seconds)
     */
    fun formatMillis(mMillis: Long): String {
        var millis = mMillis

        val days = TimeUnit.MILLISECONDS.toDays(millis)
        millis -= TimeUnit.DAYS.toMillis(days)
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        millis -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        millis -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)

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