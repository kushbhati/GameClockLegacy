package com.neuraldischarge.gameclock.dataclasses

import java.io.Serializable

data class MultiTimerHandlerConfig(
    val players: Int,
    val clocks: List<List<StageConfig>>
) : Serializable