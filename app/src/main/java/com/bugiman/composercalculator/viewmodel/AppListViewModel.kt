package com.bugiman.composercalculator.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import com.bugiman.composercalculator.model.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppListViewModel : ViewModel() {

    private val _apps = MutableStateFlow<List<AppInfo>>(value = emptyList())
    val apps: StateFlow<List<AppInfo>> = _apps

    fun loadInstalledApps(context: Context) {
        viewModelScope.launch {
            val list = withContext(context = Dispatchers.IO) {
                val packageManager = context.packageManager

                val allApps = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    packageManager.getInstalledApplications(PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong()))
                } else {
                    @Suppress("DEPRECATION")
                    packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                }

                allApps.filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }
                    .map { info ->
                        AppInfo(
                            name = info.loadLabel(packageManager).toString(),
                            packageName = info.packageName,
                            icon = info.loadIcon(packageManager)
                        )
                    }
                    .sortedBy { it.name.lowercase() }
            }
            _apps.value = list
        }
    }
}