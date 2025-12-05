package com.example.composercalculator.telegram

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

fun openTelegramChat(context: Context, showDialog: Boolean, setShowDialog: (Boolean) -> Unit) {
    val uri = "tg://resolve?domain=bugiman77".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)

    // Проверяем, установлен ли Telegram
    val isTelegramInstalled = try {
        context.packageManager.getPackageInfo("org.telegram.messenger", PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }

    if (isTelegramInstalled) {
        // Если Telegram установлен, открываем чат
        ContextCompat.startActivity(context, intent, null)
    } else {
        // Если Telegram не установлен, показываем модальное окно с выбором
        setShowDialog(true)
    }
}