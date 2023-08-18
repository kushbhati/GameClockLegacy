package com.neuraldischarge.gameclock.clockmodules

import android.os.CountDownTimer
import com.neuraldischarge.gameclock.dataclasses.StageConfig
import com.neuraldischarge.gameclock.dataclasses.enums.IncrementTypes
import com.neuraldischarge.gameclock.dataclasses.enums.TimeRolloverStates
import com.neuraldischarge.gameclock.dataclasses.enums.TriggerTypes

class SingleTimerHandler(

    private val handler: MultiTimerHandler,
    private val id: Int,
    private val config: List<StageConfig>
)

{

    inner class Unitrix(private val config: StageConfig, previousTime: Long) {

        // state variable:
        // -1 = not configured
        // 0 = initialised
        // 1 = paused or ready
        // 2 = running
        var state: Int = 0

        // count down timer object
        // will be configured at initialisation
        private var timer: CountDownTimer? = null

        // time control variables
        private var initialTime = config.time + if (config.timeRollOver == TimeRolloverStates.CONTINUE) previousTime else 0L
        var currentTime = initialTime

        // telemetry data
        val moveTimes: MutableList<Long> = mutableListOf()
        var currentMove: Int = 1

        init { tick(initialTime) }

        // resetting timer sets it back to null
        private fun resetTimer() {
            timer = null
        }


        // a ticker (called every time to update texts)
        private fun tick(p0: Long) {
            currentTime = p0

            // for delay
            if (config.incType != IncrementTypes.SIMPLE_DELAY || currentTime <= initialTime)
                this@SingleTimerHandler.handler.clockFace.clockTime[id].value = p0
        }

        private fun timeOut() {
            if (state == 1) {
                state = -1
                moveTimes += initialTime
                if (config.trigType == TriggerTypes.TIME) {
                    this@SingleTimerHandler.next()
                } else {
                    this@SingleTimerHandler.state = 0
                }
            }
        }

        // configure the timer to current time
        private fun setTimer() {
            timer = object: CountDownTimer(currentTime, 100L) {
                override fun onTick(p0: Long) {
                    tick(p0)
                }
                override fun onFinish() {
                    tick(0)
                    pause()
                    timeOut()
                }
            }
        }

        // start a new move
        fun startMove() {

            // can only configure when not configured
            if (state == 0) {

                tick(currentTime)

                // do a little delay
                if (config.incType == IncrementTypes.SIMPLE_DELAY) {
                    currentTime += config.inc
                }

                // state to ready
                state = 1
            }
        }


        // pausing and resuming functions
        fun resume() {

            // only do something if paused or ready
            if (state == 1) {

                // set timer
                setTimer()
                timer!!.start()

                // state is now running
                state = 2
            }
        }

        fun pause() {

            // can only pause while running
            if (state == 2) {

                // timer is cancelled and destroyed
                timer!!.cancel()
                resetTimer()

                // back to paused or ready state
                state = 1
            }
        }


        // ending the current move
        fun endMove() {
            if (state == 1) {

                // current move time is stored
                moveTimes += initialTime - currentTime

                // post move increment if I or B
                if (config.incType == IncrementTypes.STANDARD_INCREMENT || config.incType == IncrementTypes.BRONSTEIN_DELAY) {

                    // calculating the time to increment
                    val delta =
                        if (config.incType == IncrementTypes.BRONSTEIN_DELAY) // for bronstein increment
                            java.lang.Long.min(initialTime - currentTime, config.inc)
                        else // for standard increment
                            config.inc

                    // update the default time
                    tick(currentTime + delta)
                }

                // set previous time to current time iff its greater
                // (avoid messing with delay mechanics)
                if (initialTime > currentTime)
                    initialTime = currentTime

                // update move counter
                if (currentMove == config.trigVal && config.trigType == TriggerTypes.MOVES) {
                    state = -1
                    this@SingleTimerHandler.currentMove++
                    this@SingleTimerHandler.next()
                    return
                }

                currentMove++

                // updating move counter of parent
                this@SingleTimerHandler.currentMove++

                // set state back to 0
                state = 0
            }
        }
    }


    // Unitrix object
    var unix = Unitrix(config[0], 0)

    // control variables
    var state: Int = 1
    private var currentStg: Int = 1
    private var currentMove: Int = 1

    fun next() {
        if (unix.state == -1) {

            // stage is incremented
            currentStg++

            // a new unitrix is created
            try {
                unix = Unitrix(config[currentStg - 1], unix.currentTime)
                unix.startMove()
                unix.resume()
            } catch (e: IndexOutOfBoundsException) {
                state = 0
                handler.nextMove(id+1)
            }
        }
    }
}