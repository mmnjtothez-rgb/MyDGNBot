package com.mydgnbot.domain.manager

import com.mydgnbot.domain.model.LogEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class LogManager {


    private var nextId = 0L


    private val maxLogs = 20



    fun add(

        currentLogs: List<LogEntry>,

        message: String

    ): List<LogEntry> {


        val lastMessage =

            currentLogs.lastOrNull()
                ?.message



        if (lastMessage == message) {

            return currentLogs

        }



        val entry = LogEntry(

            id = ++nextId,

            message = message,

            timestamp =
                SimpleDateFormat(

                    "HH:mm:ss",

                    Locale.getDefault()

                ).format(Date())

        )



        return (

            currentLogs + entry

        ).takeLast(maxLogs)

    }

}
