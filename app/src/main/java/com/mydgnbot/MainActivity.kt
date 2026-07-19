package com.mydgnbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MaterialTheme {

                Surface {

                    Text(
                        text = "Hello MyDGN 👋\n\nVersion 0.1\n\nPipeline Working ✔"
                    )

                }

            }

        }
    }
}
