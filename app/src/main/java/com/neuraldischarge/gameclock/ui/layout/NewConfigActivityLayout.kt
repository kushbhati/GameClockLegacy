package com.neuraldischarge.gameclock.ui.layout

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.ShortText
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.neuraldischarge.gameclock.R
import com.neuraldischarge.gameclock.activities.NewConfigActivity
import com.neuraldischarge.gameclock.dataclasses.StageConfigUIVariant
import com.neuraldischarge.gameclock.dataclasses.enums.IncrementTypes
import com.neuraldischarge.gameclock.dataclasses.enums.TriggerTypes
import com.neuraldischarge.gameclock.ui.themes.DynamicTheme


object NewConfigActivityLayout {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateLayout(
        name: MutableState<String>,
        desc: MutableState<String>,
        icon: MutableState<String>,
        values: List<MutableList<StageConfigUIVariant>>
    ) {

        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        var advancedSettingsEnabled by remember { mutableStateOf(false) }

        DynamicTheme {

            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = { NewConfigActivityTopAppBar(scrollBehavior = scrollBehavior) }) { paddingValues ->

                var handicapEnabled by remember {
                    mutableStateOf(false)
                }

                LazyColumn(contentPadding = paddingValues) {

                    item {
                        TopSection(name = name, desc = desc, icon = icon)
                    }

                    item {
                        SharedLayouts.RadioSetting(
                            text = stringResource(R.string.advanced_prompt),
                            modifier = Modifier.padding(16.dp),
                            stateTextOff = stringResource(R.string.advanced_off_prompt),
                            stateTextOn = stringResource(R.string.advanced_on_prompt),
                            stateTextEnabled = true,
                            checked = advancedSettingsEnabled
                        ) {
                            advancedSettingsEnabled = it
                        }
                    }


                    if (!advancedSettingsEnabled)
                        item {
                            BasicStageSection(
                                stageConfig = values[0][0]
                            )
                        }
                    else {

                        item {
                            SharedLayouts.RadioSetting(
                                text = stringResource(id = R.string.handicap_prompt),
                                modifier = Modifier.padding(16.dp),
                                stateTextOff = stringResource(R.string.handicap_off_prompt),
                                stateTextOn = stringResource(R.string.handicap_on_prompt),
                                stateTextEnabled = true,
                                checked = handicapEnabled
                            ) {
                                handicapEnabled = it
                            }
                        }

                        if (handicapEnabled) {
                            items(values.count()) {
                                PlayerSection(player = it + 1, stageConfigs = values[it])
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        } else
                            item {
                                PlayerSection(player = 0, stageConfigs = values[0])
                            }

                    }

                    item { Spacer(modifier = Modifier.height(144.dp)) }

                }

            }

        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun NewConfigActivityTopAppBar(
        scrollBehavior: TopAppBarScrollBehavior
    ) {

        val context = LocalContext.current

        fun onBackPressed() {
            (context as? Activity)?.finish()
        }

        fun onSave() {
            (context as? NewConfigActivity)?.saveNewConfig()
        }

        LargeTopAppBar(
            title = {
                Text(text = "Create Time-Control")
            },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, null)
                }
            },
            actions = {
                      IconButton(onClick = { onSave() }) {
                          Icon(imageVector = Icons.Default.Save, contentDescription = null)
                      }
            },
            scrollBehavior = scrollBehavior
        )
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopSection(
        name: MutableState<String>,
        desc: MutableState<String>,
        icon: MutableState<String>
    ) {

        Row(modifier = Modifier.padding(16.dp)) {

            Image(
                imageVector = ImageVector.vectorResource(
                    id = (R.drawable::class.java).getField("ic_config_${icon.value}").getInt(null)
                ),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                TextField(value = name.value,
                    onValueChange = { name.value = it },
                    placeholder = { Text(text = "Time-Control Name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Title,
                            contentDescription = null
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = desc.value,
                    onValueChange = { desc.value = it },
                    placeholder = { Text(text = "Description") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.ShortText,
                            contentDescription = null
                        )
                    }
                )
            }

        }

    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DropDownMenu(
        value: String,
        options: List<String>,
        modifier: Modifier = Modifier,
        hint: String = "",
        onClick: (String) -> Unit,
    ) {

        var expanded by remember {
            mutableStateOf(false)
        }


        ExposedDropdownMenuBox(
            modifier = modifier,
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                value = value,
                onValueChange = {},
                label = { Text(text = hint) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            expanded = false
                            onClick(selectionOption)
                        }
                    )
                }
            }
        }
    }


    @Composable
    private fun BasicStageSection(
        stageConfig: StageConfigUIVariant
    ) {

        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
        ) {

            Column(modifier = Modifier.padding(8.dp)) {

                //val clockTypeList = stringArrayResource(id = R.array.clock_type_simple).toList()
                //val clockType = remember { mutableStateOf(clockTypeList[1]) }
                val clockTypeIndex = /* clockTypeList.indexOf(clockType.value) */ 1
                val isPerMoveEnabled = clockTypeIndex == 0

                /*DropDownMenu(
                    hint = "Clock Type",
                    options = clockTypeList,
                    value = clockType.value,
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        clockType.value = it
                    }
                )*/

                val timePrompt =
                    stringArrayResource(id = R.array.time_prompt).toList()[clockTypeIndex]
                val timeSubText =
                    stringArrayResource(id = R.array.time_sub_prompt).toList()[clockTypeIndex]

                SharedLayouts.SplitLayouts(
                    modifier = Modifier.padding(8.dp),
                    layout1 = {
                        SharedLayouts.Label(
                            labelText = timePrompt,
                            subLabelEnabled = true,
                            subLabelText = timeSubText,
                            modifier = it
                        )
                    },
                    layout2 = { modifier ->
                        SharedLayouts.TimeInputField(
                            endText = "min",
                            value = stageConfig.stageTime.value,
                            modifier = modifier
                        ) { newLabel ->
                            if (newLabel.isEmpty() || newLabel.all { it in "0123456789" })
                                stageConfig.stageTime.value = newLabel
                        }
                    }
                )

                if (!isPerMoveEnabled) {

                    val options =
                        listOf(
                            Pair(IncrementTypes.NO_INCREMENT, "No Increment"),
                            Pair(IncrementTypes.STANDARD_INCREMENT, "Standard or Fischer"),
                            Pair(IncrementTypes.SIMPLE_DELAY, "Simple Delay"),
                            Pair(IncrementTypes.BRONSTEIN_DELAY, "Bronstein Delay")
                        )

                    DropDownMenu(
                        hint = "Select Increment Type",
                        options = options.map { it.second },
                        value = stageConfig.stageIncType.value.second,
                        modifier = Modifier.padding(8.dp),
                        onClick = { it2 ->
                            stageConfig.stageIncType.value = options.first { it.second == it2 }
                        }
                    )


                    SharedLayouts.SplitLayouts(
                        modifier = Modifier.padding(8.dp),
                        layout1 = {
                            SharedLayouts.Label(
                                labelText = "Increment",
                                subLabelEnabled = true,
                                subLabelText = "Time added after each move",
                                modifier = it
                            )
                        },
                        layout2 = { modifier ->
                            SharedLayouts.TimeInputField(
                                endText = "sec",
                                value = stageConfig.stageInc.value,
                                modifier = modifier
                            ) { newLabel ->
                                if (newLabel.isEmpty() || newLabel.all { it in "0123456789" })
                                    stageConfig.stageInc.value = newLabel
                            }
                        }
                    )

                }

            }

        }

    }


    @Composable
    private fun AdvStageSection(
        stage: Int,
        stageConfig: StageConfigUIVariant,
        isExpanded: Boolean,
        isFirst: Boolean,
        isLast: Boolean,
        onClickExpand: () -> Unit,
        onClickDelete: () -> Unit
    ) {

        OutlinedCard(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
        ) {

            Column(modifier = Modifier.padding(8.dp)) {

                Row(modifier = Modifier.padding(start = 8.dp)) {
                    Text(text = "Stage $stage", modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .align(Alignment.CenterVertically))
                    IconButton(onClick = onClickDelete) {
                        Icon(
                            imageVector =
                                Icons.Default.Delete,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = onClickExpand) {
                        Icon(
                            imageVector =
                            if (!isExpanded)
                                Icons.Default.ExpandMore
                            else
                                Icons.Default.ExpandLess,
                            contentDescription = null
                        )
                    }
                }


                val clockTypeList = stringArrayResource(id = R.array.clock_type_simple).toList()
                val clockType = remember { mutableStateOf(clockTypeList[1]) }
                val clockTypeIndex = /*clockTypeList.indexOf(clockType.value)*/ 1
                val isPerMoveEnabled = clockTypeIndex == 0

                if (isExpanded) {

                    //DropDownMenu(
                    //    hint = "Clock Type",
                    //    options = clockTypeList,
                    //    value = clockType,
                    //    modifier = Modifier.padding(8.dp),
                    //)

                    val timePrompt =
                        stringArrayResource(id = R.array.time_prompt).toList()[clockTypeIndex]
                    val timeSubText =
                        stringArrayResource(id = R.array.time_sub_prompt).toList()[clockTypeIndex]

                    SharedLayouts.SplitLayouts(
                        modifier = Modifier.padding(8.dp),
                        layout1 = {
                            SharedLayouts.Label(
                                labelText = timePrompt,
                                subLabelEnabled = true,
                                subLabelText = timeSubText,
                                modifier = it
                            )
                        },
                        layout2 = { modifier ->
                            SharedLayouts.TimeInputField(
                                endText = "min",
                                value = stageConfig.stageTime.value,
                                modifier = modifier
                            ) { newLabel ->
                                if (newLabel.isEmpty() || newLabel.all { it in "0123456789" })
                                    stageConfig.stageTime.value = newLabel
                            }
                        }
                    )

                    if (!isPerMoveEnabled) {

                        val options =
                            listOf(
                                Pair(IncrementTypes.NO_INCREMENT, "No Increment"),
                                Pair(IncrementTypes.STANDARD_INCREMENT, "Standard or Fischer"),
                                Pair(IncrementTypes.SIMPLE_DELAY, "Simple Delay"),
                                Pair(IncrementTypes.BRONSTEIN_DELAY, "Bronstein Delay")
                            )

                        DropDownMenu(
                            hint = "Select Increment Type",
                            options = options.map { it.second },
                            value = stageConfig.stageIncType.value.second,
                            modifier = Modifier.padding(8.dp),
                            onClick = { it2 ->
                                stageConfig.stageIncType.value = options.first { it.second == it2 }
                            }
                        )


                        SharedLayouts.SplitLayouts(
                            modifier = Modifier.padding(8.dp),
                            layout1 = {
                                SharedLayouts.Label(
                                    labelText = "Increment",
                                    subLabelEnabled = true,
                                    subLabelText = "Time added after each move",
                                    modifier = it
                                )
                            },
                            layout2 = { modifier ->
                                SharedLayouts.TimeInputField(
                                    endText = "sec",
                                    value = stageConfig.stageInc.value,
                                    modifier = modifier
                                ) { newLabel ->
                                    if (newLabel.isEmpty() || newLabel.all { it in "0123456789" })
                                        stageConfig.stageInc.value = newLabel
                                }
                            }
                        )

                        if (!isLast) {

                            val stgEndOpts =
                                listOf(
                                    Pair(TriggerTypes.TIME, "When time runs out"),
                                    Pair(TriggerTypes.MOVES, "After certain moves")
                                )

                            DropDownMenu(
                                hint = "Select Increment Type",
                                options = stgEndOpts.map { it.second },
                                value = stageConfig.stageTrigType.value.second,
                                modifier = Modifier.padding(8.dp),
                                onClick = { it2 ->
                                    stageConfig.stageTrigType.value = stgEndOpts.first { it.second == it2 }
                                }
                            )


                            SharedLayouts.SplitLayouts(
                                modifier = Modifier.padding(8.dp),
                                layout1 = {
                                    SharedLayouts.Label(
                                        labelText = "Moves in Stage",
                                        subLabelEnabled = true,
                                        subLabelText = "No of moves in the stage after which it ends",
                                        modifier = it
                                    )
                                },
                                layout2 = { modifier ->
                                    SharedLayouts.TimeInputField(
                                        endText = "",
                                        value = stageConfig.stageTrig.value,
                                        modifier = modifier
                                    ) { newLabel ->
                                        if (newLabel.isEmpty() || newLabel.all { it in "0123456789" })
                                            stageConfig.stageTrig.value = newLabel
                                    }
                                }
                            )

                        }

                    }
                }

            }

        }

    }


    @Composable
    private fun PlayerSection(
        player: Int,
        stageConfigs: MutableList<StageConfigUIVariant>
    ) {

        var stages by remember { mutableStateOf(stageConfigs.size) }
        val hide by remember { derivedStateOf { player == 0 } }

        val areAllExpanded = remember { MutableList(stages) { mutableStateOf(false) } }

        OutlinedCard(
            modifier = Modifier
                .padding(horizontal = if (hide) 0.dp else 16.dp)
                .fillMaxWidth(),
            colors = if (hide) CardDefaults.cardColors(Color.Transparent) else
                CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
            border = if (hide) BorderStroke(0.dp, Color.Transparent) else
                CardDefaults.outlinedCardBorder()
        ) {

            if (!hide) {
                SharedLayouts.Label(
                    labelText = "Player $player",
                    modifier = Modifier.padding(16.dp)
                )
                Divider()
            }


            for (it in 1..stages) {

                AdvStageSection(
                    stage = it,
                    stageConfig = stageConfigs[it - 1],
                    isFirst = it == 1,
                    isLast = it == stages,
                    isExpanded = areAllExpanded[it - 1].value,
                    onClickExpand = {
                        if (!areAllExpanded[it - 1].value) {
                            areAllExpanded.forEach { if (it.value) it.value = false }
                            areAllExpanded[it - 1].value = true
                        } else {
                            areAllExpanded.forEach { if (it.value) it.value = false }
                        }
                    },
                    onClickDelete = {
                        areAllExpanded.removeAt(it-1)
                        stageConfigs.removeAt(it-1)
                        stages--
                    }
                )

            }
            
            IconButton(
                onClick = {
                    stages++
                    stageConfigs.add(StageConfigUIVariant())
                    areAllExpanded.add(mutableStateOf(false))
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
                        CircleShape
                    )
                    .border(1.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(48.dp))
            }

        }
    }
}