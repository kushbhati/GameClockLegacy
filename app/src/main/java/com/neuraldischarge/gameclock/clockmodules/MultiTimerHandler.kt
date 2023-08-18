package com.neuraldischarge.gameclock.clockmodules

import com.neuraldischarge.gameclock.dataclasses.MultiTimerHandlerConfig

class MultiTimerHandler(
    private val config: MultiTimerHandlerConfig,
    val clockFace: ClockFace
) {

    // keeping track of current player
    private var currentPlayer = 0
    private var state: Int

    // list of clocks
    private var omnix: List<SingleTimerHandler>


    init {
        val list = mutableListOf<SingleTimerHandler>()
        with(config.clocks) {
            for (i in indices)
                list.add(SingleTimerHandler(this@MultiTimerHandler, i, this[i]))
        }
        omnix = list
        state = 0
    }

    fun nextMove(id: Int) {
        if (currentPlayer == id) {
            pause()
            if (state == 1) {
                omnix[currentPlayer-1].unix.endMove()
                state =
                    if (omnix.count { it.state == 1 } < 2)
                        -1
                    else
                        0
                clockFace.clockMoves[currentPlayer-1].value = omnix[currentPlayer-1].unix.currentMove - 1
                start()
            }
        } else if (state == 0) {
            currentPlayer = id
            start()
        } else if (state == 1) {
            resume()
        }
    }

    private fun start() {
        if (state == 0) {

            while (true) {
                currentPlayer++
                if (currentPlayer > config.players) currentPlayer = 1
                if (omnix[currentPlayer-1].state != 0)
                    break
            }

            state = 1
            omnix[currentPlayer-1].unix.startMove()
            resume()
        }
    }

    private fun resume() {
        if (state == 1) {
            omnix[currentPlayer-1].unix.resume()
            state = 2

            clockFace.isActive.value = true
            clockFace.currentActive.value = currentPlayer
        }
    }

    private fun pause() {
        if (state == 2) {
            omnix[currentPlayer-1].unix.pause()
            state = 1

            clockFace.isActive.value = false
            //clockFace.clockState.forEach { it.value = false }
        }
    }

    fun toggle() {
        when (state) {
            1 -> resume()
            2 -> pause()
        }
    }
}