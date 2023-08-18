package com.neuraldischarge.gameclock.methods

import androidx.compose.runtime.mutableStateOf
import com.neuraldischarge.gameclock.dataclasses.StageConfig
import com.neuraldischarge.gameclock.dataclasses.StageConfigUIVariant
import com.neuraldischarge.gameclock.dataclasses.enums.IncrementTypes
import com.neuraldischarge.gameclock.dataclasses.enums.TimeRolloverStates
import com.neuraldischarge.gameclock.dataclasses.enums.TriggerTypes

object VariantConverter {
    fun StageConfig.toUIVariant(): StageConfigUIVariant {
        return StageConfigUIVariant(
            stageTime = mutableStateOf(""),
            stageTimeRollover = mutableStateOf(Pair(TimeRolloverStates.CONTINUE, "")),
            stageInc = mutableStateOf(""),
            stageIncType = mutableStateOf(Pair(IncrementTypes.NO_INCREMENT,"")),
            stageTrig = mutableStateOf("0"),
            stageTrigType = mutableStateOf(Pair(TriggerTypes.TIME,"")),
        )
    }

    fun StageConfigUIVariant.toNormalVariant(): StageConfig {
        return StageConfig(
            time = stageTime.value.toLong() * 60000 ,
            inc = stageInc.value.toLong() * 1000,
            trigVal = stageTrig.value.toInt(),
            timeRollOver = stageTimeRollover.value.first,
            incType = stageIncType.value.first,
            trigType = stageTrigType.value.first
        )
    }
}