package com.mydgnbot.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mydgnbot.domain.model.LogEntry


@Composable
fun ActivityLogCard(

    logs: List<LogEntry>

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()

    ) {

        Column(

            modifier = Modifier
                .padding(16.dp)

        ) {


            Text(

                text = "Activity Log",

                style =
                    MaterialTheme.typography.titleMedium

            )


            Spacer(

                modifier = Modifier
                    .height(8.dp)

            )


            if (logs.isEmpty()) {


                Text(

                    text = "No activity yet",

                    style =
                        MaterialTheme.typography.bodyMedium

                )


            } else {


                LazyColumn(

                    verticalArrangement =
                        Arrangement.spacedBy(6.dp),

                    modifier = Modifier
                        .height(180.dp)

                ) {


                    items(

                        items = logs,

                        key = {
                            it.id
                        }

                    ) { log ->


                        Row(

                            modifier = Modifier
                                .fillMaxWidth()

                        ) {


                            Text(

                                text =
                                    log.timestamp,

                                style =
                                    MaterialTheme.typography.bodySmall

                            )


                            Spacer(

                                modifier = Modifier
                                    .padding(
                                        horizontal = 6.dp
                                    )

                            )


                            Text(

                                text =
                                    log.message,

                                style =
                                    MaterialTheme.typography.bodyMedium

                            )

                        }

                    }

                }

            }

        }

    }

}
