package com.neuraldischarge.gameclock.dataclasses

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.neuraldischarge.gameclock.dataclasses.enums.IncrementTypes
import com.neuraldischarge.gameclock.dataclasses.enums.TimeRolloverStates
import com.neuraldischarge.gameclock.dataclasses.enums.TriggerTypes

data class StageConfigUIVariant(
    val stageTime: MutableState<String> = mutableStateOf(""),
    val stageTimeRollover: MutableState<Pair<TimeRolloverStates, String>> = mutableStateOf(Pair(TimeRolloverStates.CONTINUE, "")),
    val stageInc: MutableState<String> = mutableStateOf(""),
    val stageIncType: MutableState<Pair<IncrementTypes, String>> = mutableStateOf(Pair(IncrementTypes.NO_INCREMENT,"")),
    val stageTrig: MutableState<String> = mutableStateOf("0"),
    val stageTrigType: MutableState<Pair<TriggerTypes, String>> = mutableStateOf(Pair(TriggerTypes.TIME,"")),
)