package com.neuraldischarge.gameclock.ui.layout

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.neuraldischarge.gameclock.R
import com.neuraldischarge.gameclock.activities.ClockActivity
import com.neuraldischarge.gameclock.activities.NewConfigActivity
import com.neuraldischarge.gameclock.dataclasses.DisplayConfig
import com.neuraldischarge.gameclock.datastore.PreferencesManager
import com.neuraldischarge.gameclock.ui.layout.SharedLayouts.isScrollingUp
import com.neuraldischarge.gameclock.ui.themes.DynamicTheme

object ListerActivityLayout {


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateLayout(
        configArray: Array<DisplayConfig>?) {

        val context = LocalContext.current

        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        fun onBackPressed() {
            (context as? Activity)?.finish()
        }

        fun onClickFAB() {
            context.startActivity(Intent(context, NewConfigActivity::class.java))
        }

        fun onCLickConfig(index: Int) {
            val intent = Intent(context, ClockActivity::class.java)
                .putExtra("clock_face_name", PreferencesManager.getPreferences().clockFace)
                .putExtra("clock_config", configArray!![index].value)
            context.startActivity(intent)
        }

        val listState = rememberLazyListState()
        val expanded = listState.isScrollingUp()

        DynamicTheme {

            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = { ListerActivityTopAppBar(scrollBehavior = scrollBehavior, onNavIconClick = { onBackPressed() }) },
                floatingActionButton = { ListerActivityFAB(expanded = expanded, onClickFAB = { onClickFAB() }) },) {

                LazyColumn(
                    contentPadding = it,
                    state = listState
                ) {

                    item {
                        FilterChip(
                            selected = true,
                            onClick = { },
                            label = { Text(text = "All") },
                            Modifier.padding(start = 16.dp)
                        )

                        Spacer(Modifier.size(20.dp))
                    }


                    // time-control
                    if (configArray != null)
                        items(configArray.size) { index ->
                            TimeControlDisplay(
                                config = configArray[index],
                                onClick = { onCLickConfig(index = index) }
                            )
                            Divider(
                                Modifier.padding(start = 80.dp)
                            )
                        }
                }
            }
        }
    }





    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ListerActivityTopAppBar(
        scrollBehavior: TopAppBarScrollBehavior,
        onNavIconClick: () -> Unit) {

        LargeTopAppBar(
            title = {
                Text(text = stringResource(R.string.lister_app_bar_text))
            },
            navigationIcon = { IconButton(onClick = { onNavIconClick() }) {
                Icon(imageVector = Icons.Default.ArrowBack, null)
            } },
            scrollBehavior = scrollBehavior
        )
    }


    @Composable
    private fun ListerActivityFAB(expanded: Boolean, onClickFAB: () -> Unit) {

        ExtendedFloatingActionButton(
            text = { Text(text = stringResource(R.string.lister_new_config_prompt)) },
            icon = { Icon(imageVector = Icons.Default.Add, null) },
            expanded = expanded,
            onClick = onClickFAB
        )
    }


    @Composable
    private fun TimeControlDisplay(config: DisplayConfig, onClick: () -> Unit) {

        Box (modifier = Modifier
                .clickable { onClick() }
                .padding(vertical = 16.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Image(
                    imageVector = ImageVector.vectorResource(
                        id = (R.drawable::class.java)
                            .getField("ic_config_${config.icon}")
                            .getInt(null)
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp).clip(CircleShape)
                )

                Spacer(Modifier.size(16.dp))

                Column {
                    Text(text = config.title, style = MaterialTheme.typography.titleLarge)
                    Text(text = config.desc, style = MaterialTheme.typography.bodyMedium)
                }

            }

        }
    }

}