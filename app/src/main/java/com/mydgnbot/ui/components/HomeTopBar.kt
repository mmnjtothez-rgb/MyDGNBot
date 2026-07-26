package com.mydgnbot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mydgnbot.R

@Composable
fun HomeTopBar(

    onSettingsClick: () -> Unit

) {

    Row(

        modifier = Modifier.fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically

    ) {

        Column(

            modifier = Modifier.weight(1f)

        ) {

            Text(

                text = "MyDGNBot",

                fontSize = 16.sp,

                fontWeight = FontWeight.Bold,

                color = MaterialTheme.colorScheme.onBackground

            )

          

        }

        Surface(

            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .clickable {

                    onSettingsClick()

                },

            color = Color(0xFF1A1D22)

        ) {

            Box(

                contentAlignment = Alignment.Center

            ) {

                Image(

                    painter = painterResource(R.drawable.ic_settings),

                    contentDescription = null,

                    modifier = Modifier.size(22.dp)

                )

            }

        }

    }

}