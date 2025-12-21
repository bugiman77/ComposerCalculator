package com.example.composercalculator.view.screen.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composercalculator.BuildConfig
import com.example.composercalculator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPrivacy: () -> Unit
) {
    Scaffold(
        containerColor = Color(0xFF161616),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("О приложении", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    CustomBackButton(onClick = onNavigateBack)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
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
                AppHeader()

                Spacer(modifier = Modifier.height(32.dp))

                InfoLinksGroup(
                    onNavigateToPrivacy = onNavigateToPrivacy
                )

                Spacer(modifier = Modifier.height(32.dp))

                AuthorInfo()
            }
        }
    }
}

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
                .size(120.dp)
                .clip(RoundedCornerShape(size = 24.dp))
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
            .clip(shape = RoundedCornerShape(size = 12.dp))
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
private fun AuthorInfo() {
    Text(
        text = "",
        color = Color.Gray,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        lineHeight = 20.sp,
        modifier = Modifier.padding(vertical = 24.dp)
    )
}
