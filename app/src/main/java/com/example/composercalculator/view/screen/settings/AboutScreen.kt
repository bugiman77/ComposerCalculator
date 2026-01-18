package com.example.composercalculator.view.screen.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composercalculator.BuildConfig
import com.example.composercalculator.R
import com.example.composercalculator.model.AppInfo
import com.example.composercalculator.model.DeviceInfo
import com.example.composercalculator.viewmodel.AppListViewModel
import com.google.accompanist.drawablepainter.rememberDrawablePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    title: String,
    appListViewModel: AppListViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToPrivacy: () -> Unit
) {
    Scaffold(
        containerColor = Color(0xFF161616),
        topBar = {
            CustomTopBar(
                screenTitle = title,
                onNavigateBack = onNavigateBack,
                onScrollToTop = { },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppHeader(appListViewModel)

                Spacer(modifier = Modifier.height(32.dp))

                InfoLinksGroup(
                    onNavigateToPrivacy = onNavigateToPrivacy
                )

                Spacer(modifier = Modifier.height(32.dp))

                DeviceInfoApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppHeader(
    appListViewModel: AppListViewModel
) {

    val context = LocalContext.current
    val apps = appListViewModel.apps.collectAsState()

    // Состояние для управления модальным окном
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = "Иконка приложения",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .size(size = 120.dp)
                .clip(shape = RoundedCornerShape(size = 24.dp))
                .background(Color.DarkGray)
                .combinedClickable(
                    onClick = {},
                    onLongClick = {
                        appListViewModel.loadInstalledApps(context) // Загружаем список
                        showSheet = true // Показываем окно
                    }
                )
        )

        Spacer(modifier = Modifier.height(height = 24.dp))

        Text(
            text = "Версия ${BuildConfig.VERSION_NAME}",
            color = Color.Gray,
            fontSize = 16.sp
        )

        // Само модальное окно
        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
                dragHandle = { BottomSheetDefaults.DragHandle() } // Полоска сверху
            ) {
                AppListContent(apps.value)
            }
        }

    }
}

@Composable
private fun AppListContent(apps: List<AppInfo>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f) // Окно на 60% экрана
            .padding(horizontal = 16.dp)
    ) {
        item {
            Text(
                "Выберите приложение",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        if (apps.isEmpty()) {
            item {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        items(apps) { app ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberDrawablePainter(drawable = app.icon),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = app.name, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = app.packageName,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(height = 32.dp)) }
    }
}

@Composable
private fun InfoLinksGroup(
    onNavigateToPrivacy: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 32.dp))
            .background(Color(color = 0xFF2C2C2E))
    ) {
        InfoLinkRow(title = "Оценить в Google Play") { }
        HorizontalDivider(color = Color(color = 0xFF3A3A3C))
        InfoLinkRow(
            title = "Политика конфиденциальности",
            onClick = onNavigateToPrivacy
        )
        HorizontalDivider(color = Color(color = 0xFF3A3A3C))
        InfoLinkRow(
            title = "Связаться с автором",
        ) { }
    }
}

@Composable
private fun InfoLinkRow(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, color = Color.White, fontSize = 17.sp)
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(size = 20.dp)
        )
    }
}

@Composable
private fun DeviceInfoApp() {

    val infoDevice = DeviceInfo()

    Text(
        text = "${infoDevice.deviceName} ${infoDevice.androidVersion} (${infoDevice.apiLevel})",
        color = Color.Gray,
        fontSize = 16.sp
    )
}