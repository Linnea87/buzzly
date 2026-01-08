package com.example.buzzly.utils

object ChatIdUtil {

    // the same two UIDs will always produce the same ID(used as chatID)
    fun uniqueChatId(uid1: String, uid2: String): String =
        if (uid1 < uid2) "${uid1}_${uid2}" else "${uid2}_${uid1}"
}