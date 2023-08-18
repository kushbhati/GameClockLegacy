package com.neuraldischarge.gameclock.clockmodules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.neuraldischarge.gameclock.methods.TimeConverter.toFormattedTime
import com.neuraldischarge.gameclock.ui.themes.DynamicTheme

// basic structure of a Clock Face
enum class ClockFace {

    DUAL_BUTTONED  {

        override var players = 2

        @Composable
        override fun CreateUI() {
            DynamicTheme(isFullScreen = false) {
                Surface {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)) {

                        val activeColor = colorSchemeExtended.additionalColorScheme.clockColor1
                        val passiveColor = MaterialTheme.colorScheme.surfaceVariant

                        PlayerButton(
                            id = 1,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .rotate(180f),
                            activeColor = activeColor,
                            passiveColor = passiveColor)

                        PauseButton(
                            modifier = Modifier.align(Alignment.Center)
                        )

                        PlayerButton(
                            id = 2,
                            modifier = Modifier
                                .align(Alignment.BottomCenter),
                            activeColor = activeColor,
                            passiveColor = passiveColor
                        )
                    }
                }
            }
        }

        @Composable
        private fun PlayerButton(
            id: Int,
            modifier: Modifier,
            activeColor: Color,
            passiveColor: Color
        ) {

            val turn = currentActive.value == id
            val state = turn && isActive.value
            val time = clockTime[id-1].value
            val moves = clockMoves[id-1].value

            Box(
                modifier = modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(.33f)
                    .background(if (state) activeColor else passiveColor)
                    .clickable { handler.nextMove(id) }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = time.toFormattedTime(),
                    style = MaterialTheme.typography.displayLarge
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(if (turn) activeColor else passiveColor)
                )
                if (moves > 0)
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        text = moves.toString(),
                        style = MaterialTheme.typography.titleLarge
                    )
            }
        }

        @Composable
        private fun PauseButton(modifier: Modifier) {
            if (isActive.value) {
                IconButton(onClick = { handler.toggle() }, modifier = modifier.size(72.dp)) {
                    Icon(imageVector = Icons.Default.Pause, contentDescription = null, modifier = Modifier.fillMaxSize())
                }
            }
        }

    };

    /*DUAL_IMMERSIVE {

        override var players = 2

        @Composable
        override fun CreateUI() {

            val animatedFloat by animateFloatAsState(
                if (clockState[0].value)
                    .67f
                else if (!isActive.value)
                    .5f
                else
                    .33f
            )

            DynamicTheme {
                Surface {
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {

                        // first button
                        ImmersiveButton(
                            1, animatedFloat,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .rotate(180f)
                        )


                        // second button
                        ImmersiveButton(
                            2, 1 - animatedFloat,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                        )

                        // pause button
                        PauseButton(modifier = Modifier
                            .align(Alignment.TopCenter)
                            .absoluteOffset(
                                y = maxHeight * animatedFloat - 36.dp
                            ))

                    }
                }
            }
        }

        @Composable
        private fun ImmersiveButton(id: Int, animatedFloat: Float, modifier: Modifier) {

            Box(
                modifier = modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(animatedFloat)
                    .background(
                        if (id == 1)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.tertiaryContainer
                    )
                    .clickable { handler.nextMove(id) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = clockTime[id - 1].value.toFormattedTime(),
                    style = MaterialTheme.typography.displayLarge
                )
            }

        }

        @Composable
        private fun PauseButton(modifier: Modifier) {
            if (isActive.value) {
                IconButton(onClick = { handler.toggle() }, modifier = modifier.size(72.dp)) {
                    Icon(imageVector = Icons.Default.Pause, contentDescription = null, modifier = Modifier.fillMaxSize())
                }
            }
        }

    };*/

    // reference to the clock handler to forward onclick events
    lateinit var handler: MultiTimerHandler

    // how many players are supported by the clock face
    abstract var players: Int

    // observable delegates to update ui elements
    val clockTime by lazy { MutableList(players) { mutableStateOf(0L) } }
    val clockMoves by lazy { MutableList(players) { mutableStateOf(0) } }
    val currentActive by lazy { mutableStateOf(0) }
    val isActive by lazy { mutableStateOf(false) }

    // main function to be called when ui is to be created
    @Composable
    abstract fun CreateUI()
}