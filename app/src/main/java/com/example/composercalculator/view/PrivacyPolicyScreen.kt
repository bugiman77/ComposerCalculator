package com.example.composercalculator.view

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView

/**
 * Экран для отображения локального HTML-файла из папки assets.
 *
 * @param htmlFileName Имя файла в папке assets (например, "privacy_policy.html").
 * @param title Заголовок, который будет отображаться в TopAppBar.
 * @param onNavigateBack Лямбда для возврата на предыдущий экран.
 */
@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    htmlFileName: String,
    title: String,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xFF1C1C1E), // Фон, подходящий под HTML
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    CustomBackButton(onClick = onNavigateBack)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        // AndroidView позволяет "встроить" классический View (в нашем случае WebView) в Compose
        AndroidView(
            // FACTORY: Здесь мы только создаём и базово настраиваем WebView.
            // Никакой загрузки данных здесь быть не должно.
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    // Делаем фон WebView прозрачным, чтобы был виден фон Scaffold
                    setBackgroundColor(0x00000000)
                }
            },
            // UPDATE: Этот блок отвечает за загрузку и обновление данных.
            // Он вызывается гарантированно, когда View создан.
            update = { webView ->
                try {
                    // Читаем HTML-файл из папки assets
                    val htmlContent =
                        webView.context.assets.open(htmlFileName).bufferedReader().use { reader ->
                            reader.readText()
                        }
                    // Загружаем HTML-строку в WebView
                    webView.loadDataWithBaseURL(
                        "file:///android_asset/",
                        htmlContent,
                        "text/html",
                        "utf-8",
                        null
                    )
                } catch (e: Exception) {
                    // Обработка ошибки, если файл не найден
                    e.printStackTrace()
                    webView.loadData(
                        "<html><body><h1>Ошибка загрузки</h1><p>Не удалось найти или прочитать файл: <b>$htmlFileName</b>. " +
                                "Убедитесь, что он находится по пути: <b>app/src/main/assets/$htmlFileName</b></p></body></html>",
                        "text/html",
                        "utf-8"
                    )
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
