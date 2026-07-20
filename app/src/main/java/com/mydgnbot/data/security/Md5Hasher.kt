package com.mydgnbot.data.security

import java.security.MessageDigest


object Md5Hasher {


    fun generate(
        value: String
    ): String {

        val bytes = MessageDigest
            .getInstance("MD5")
            .digest(
                value.toByteArray()
            )


        return bytes.joinToString("") {

            "%02x".format(it)

        }

    }

}
