package me.skylands.skypvp.delay

data class DelayConfig(
    /**
     * May contain a <code>%time</code> placeholder which gets replaced with a formatted version of the remaining time.
     */
    val message: String?,
    /**
     * Delay time in millis.
     */
    val time: Long
)