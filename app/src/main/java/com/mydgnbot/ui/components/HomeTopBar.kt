package com.mydgnbot.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mydgnbot.R

@Composable
fun HomeTopBar(

    title: String,

    onSettingsClick: () -> Unit

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),

        horizontalArrangement = Arrangement.SpaceBetween,

        verticalAlignment = Alignment.CenterVertically

    ) {

        Text(

            text = title,

            fontSize = 26.sp,

            fontWeight = FontWeight.Bold,

            color = MaterialTheme.colorScheme.onBackground

        )

        Image(

            painter = painterResource(R.drawable.ic_settings),

            contentDescription = "Settings",

            modifier = Modifier
                .size(24.dp)
                .clickable {

                    onSettingsClick()

                }

        )

    }

}