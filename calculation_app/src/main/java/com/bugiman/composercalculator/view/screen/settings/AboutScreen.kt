package com.bugiman.composercalculator.view.screen.settings

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
import com.bugiman.composercalculator.BuildConfig
import com.bugiman.composercalculator.R
//import com.bugiman.composercalculator.model.DeviceInfo
import com.bugiman.composercalculator.view.components.general.settings.CustomTopBar
import com.bugiman.domain.models.DeviceInfo
import com.google.accompanist.drawablepainter.rememberDrawablePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    title: String,
    onNavigateBack: () -> Unit,
    onNavigateToPrivacy: () -> Unit
) {
    Scaffold(
        containerColor = Color(color = 0xFF000000),
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
                .padding(paddingValues = innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppHeader()

                Spacer(modifier = Modifier.height(height = 32.dp))

                InfoLinksGroup(
                    onNavigateToPrivacy = onNavigateToPrivacy
                )

                Spacer(modifier = Modifier.height(height = 32.dp))

                DeviceInfoApp()

                Spacer(modifier = Modifier.height(height = 60.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        text = "Powered by Android Studio",
                        color = Color.White
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppHeader() {

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
        )

        Spacer(modifier = Modifier.height(height = 24.dp))

        Text(
            text = "Версия ${BuildConfig.VERSION_NAME}",
            color = Color.Gray,
            fontSize = 16.sp
        )

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

    val infoDevice = DeviceInfo(
        deviceName = "",
        manufacturer = "",
        androidVersion = "",
        apiLevel = 0
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = infoDevice.deviceName,
            color = Color.Gray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        Text(
            text = "Android Version ${infoDevice.androidVersion}",
            color = Color.Gray,
            fontSize = 16.sp
        )
    }

}