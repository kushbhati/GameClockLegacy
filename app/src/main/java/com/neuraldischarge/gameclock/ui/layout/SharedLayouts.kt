package com.neuraldischarge.gameclock.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object SharedLayouts {

    @Composable
    fun LazyListState.isScrollingUp(): Boolean {
        var lastSolved by remember(this) { mutableStateOf(true) }
        var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
        var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
        return remember(this) {
            derivedStateOf {
                if (previousIndex != firstVisibleItemIndex) {
                    previousIndex > firstVisibleItemIndex
                } else {
                    if (lastSolved)
                        previousScrollOffset + 20 >= firstVisibleItemScrollOffset
                    else
                        previousScrollOffset >= firstVisibleItemScrollOffset + 20
                }.also {
                    lastSolved = it
                    previousIndex = firstVisibleItemIndex
                    previousScrollOffset = firstVisibleItemScrollOffset
                }
            }
        }.value
    }

    @Composable
    fun Label(
        labelText: String,
        modifier: Modifier = Modifier,
        subLabelEnabled: Boolean = false,
        subLabelText: String = ""
    ) {
        Column(modifier = modifier) {
            Text(
                text = labelText,
                style = MaterialTheme.typography.titleMedium
            )

            if (subLabelEnabled)
                Text(
                    text = subLabelText,
                    style = MaterialTheme.typography.bodySmall
                )

        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TimeInputField(
        modifier: Modifier = Modifier,
        endText: String = "",
        value: String = "",
        onValueChange: (String) -> Unit
        ) {
        Column(
            modifier = modifier
        ) {
            TextField(
                value = value,
                textStyle = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.End),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = onValueChange,
                trailingIcon = { Text(text = endText) }
            )
        }
    }


    @Composable
    fun RadioSetting(
        text: String,
        modifier: Modifier = Modifier,
        stateTextEnabled: Boolean = false,
        stateTextOn: String = "",
        stateTextOff: String = "",
        checked: Boolean = false,
        onCheckedChange: ((Boolean) -> Unit)?
    ) {

        Row(modifier = modifier
            .fillMaxWidth()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true)
                    .align(Alignment.CenterVertically)) {

                Text(
                    text = text,
                    style = MaterialTheme.typography.titleLarge
                )

                if (stateTextEnabled)
                    Text(
                        text = if (checked) stateTextOn else stateTextOff,
                        style = MaterialTheme.typography.bodySmall
                    )

            }

            Switch(
                modifier = Modifier
                    .defaultMinSize(minWidth = 64.dp)
                    .align(Alignment.CenterVertically),
                checked = checked,
                onCheckedChange = onCheckedChange
            )

        }
    }


    @Composable
    fun SplitLayouts(
        layout1: @Composable (RowScope.(Modifier) -> Unit),
        layout2: @Composable (RowScope.(Modifier) -> Unit),
        modifier: Modifier = Modifier,
        sep: Dp = 16.dp
    ) {

        Box(modifier = modifier.fillMaxWidth()) {
            Row {

                val mod = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .align(Alignment.CenterVertically)

                layout1(mod)
                Spacer(Modifier.size(sep))
                layout2(mod)

            }
        }

    }

}