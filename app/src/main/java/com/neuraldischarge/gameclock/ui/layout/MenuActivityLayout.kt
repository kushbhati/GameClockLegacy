package com.neuraldischarge.gameclock.ui.layout

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.neuraldischarge.gameclock.activities.ListerActivity
import com.neuraldischarge.gameclock.ui.themes.DynamicTheme

object MenuActivityLayout {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateLayout(context: Context) {
        DynamicTheme {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(title = { Text(text = "GameClock") })
                }
            ) { innerPadding ->

                LazyColumn(
                    contentPadding = innerPadding
                ) {

                    item {
                        Spacer(Modifier.size(16.dp))
                        ContinueCard()
                        Spacer(Modifier.size(16.dp))
                        Header(text = "Start New", color = MaterialTheme.colorScheme.tertiary)
                        Spacer(Modifier.size(16.dp))
                        NewStartCards(context)
                        Spacer(Modifier.size(16.dp))
                        Header(text = "Customize", color = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.size(16.dp))
                        ContinueCard()
                        Spacer(Modifier.size(16.dp))
                        Header(
                            text = "About",
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        Spacer(Modifier.size(16.dp))
                        ContinueCard()
                        Spacer(Modifier.size(16.dp))
                        Header(text = "More Apps", color = MaterialTheme.colorScheme.secondary)
                        Spacer(Modifier.size(16.dp))
                        ContinueCard()
                        Spacer(Modifier.size(16.dp))
                    }

                }

            }

        }

    }

    @Composable
    private fun ContinueCard(modifier: Modifier = Modifier) {

        Box(modifier = modifier
            .padding(horizontal = 16.dp)) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(144.dp)
            ) {}

        }
    }


    @Composable
    private fun Header(text: String, color: Color, modifier: Modifier = Modifier) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            color = color,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NewStartCards(context: Context) {
        Box(
            modifier = Modifier
                .height(144.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(onClick = {val intent = Intent(context, ListerActivity::class.java)
                    context.startActivity(intent)},
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .weight(0.66f, true)
                ) {

                }

                Box(Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.33f, true)
                        .fillMaxHeight()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(0.5f, true)
                    ) {

                    }

                    Box(Modifier.height(16.dp))


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(0.5f, true)

                    ) {

                    }
                }
            }

        }
    }



}