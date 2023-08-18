package com.neuraldischarge.gameclock.datastore.defaultdata

import com.neuraldischarge.gameclock.dataclasses.MultiTimerHandlerConfig
import com.neuraldischarge.gameclock.dataclasses.DisplayConfig
import com.neuraldischarge.gameclock.dataclasses.StageConfig
import com.neuraldischarge.gameclock.dataclasses.enums.IncrementTypes
import com.neuraldischarge.gameclock.dataclasses.enums.TimeRolloverStates
import com.neuraldischarge.gameclock.dataclasses.enums.TriggerTypes

object DefaultConfigs {
    private val clockConfig1 = DisplayConfig(
        icon = "1",
        title = "Classical Tournament",
        desc = "40 in 120, 20 in 60, G in 15|30",
        value = MultiTimerHandlerConfig(
            players = 2,
            clocks = List (2) {
                    listOf(
                        StageConfig(
                            time = 7200000L,
                            timeRollOver = TimeRolloverStates.CONTINUE,
                            inc = 0L,
                            incType = IncrementTypes.NO_INCREMENT,
                            trigVal = 40,
                            trigType = TriggerTypes.TIME)
                    )
            })
    )

    fun getConfigs() = Array (20) { clockConfig1 }
}