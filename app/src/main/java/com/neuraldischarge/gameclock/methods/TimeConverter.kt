package com.neuraldischarge.gameclock.methods

object TimeConverter {
    fun Long.toFormattedTime(): String {
        val hours = floorDiv(3600000)
        val mins = (this % 3600000).floorDiv(60000)
        val secs = (this % 60000).floorDiv(1000)
        val deci = (this % 1000).floorDiv(100)

        return (if (hours != 0L) "$hours:" else "") +
                mins.toString().padStart(2, '0') + ":" +
                secs.toString().padStart(2, '0') + if (secs > 9 || mins != 0L || hours != 0L) "" else ".$deci"
    }
}