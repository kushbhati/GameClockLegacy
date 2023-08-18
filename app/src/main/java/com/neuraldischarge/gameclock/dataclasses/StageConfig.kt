package com.neuraldischarge.gameclock.dataclasses

import com.neuraldischarge.gameclock.dataclasses.enums.IncrementTypes
import com.neuraldischarge.gameclock.dataclasses.enums.TimeRolloverStates
import com.neuraldischarge.gameclock.dataclasses.enums.TriggerTypes
import java.io.Serializable


// 'UniCon' data class for class 'Unitrix'
data class StageConfig(

    // time to set timer to
    val time: Long,

    // what to do with previous time
    val timeRollOver: TimeRolloverStates,

    // value of increment
    val inc: Long,

    // type of increment
    val incType: IncrementTypes,

    // value of trigger if moves
    val trigVal: Int,

    // type of end condition
    val trigType: TriggerTypes
) : Serializable
