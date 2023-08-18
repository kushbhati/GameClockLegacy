package com.neuraldischarge.gameclock.dataclasses

import java.io.Serializable

// class where actual data is stored
data class DisplayConfig(

    // configuration of the clock
    val value: MultiTimerHandlerConfig,

    // icon name
    val icon: String,

    // time-control category. eg. Blitz, Bullet, Tournament, etc
    val title: String,

    // user-desc
    val desc: String

) : Serializable
