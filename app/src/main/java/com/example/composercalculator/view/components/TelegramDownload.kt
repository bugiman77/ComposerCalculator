package com.example.composercalculator.view.components

import android.content.Intent
import android.net.Uri
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat

@Composable
fun TelegramDownloadDialog(onDismiss: (Boolean) -> Unit) {

    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss(false) },
        title = { Text("Telegram не установлен") },
        text = {
            Text("Вы можете установить Telegram с официального сайта или из Google Play. Рекомендуемый способ — с официального сайта.")
        },
        confirmButton = {
            TextButton(onClick = {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.org"))
                ContextCompat.startActivity(context, browserIntent, null)
                onDismiss(false)
            }) {
                Text("Скачать с сайта (Рекомендуется)")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=org.telegram.messenger"))
                ContextCompat.startActivity(context, playStoreIntent, null)
                onDismiss(false)
            }) {
                Text("Скачать с Google Play")
            }
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    )
}