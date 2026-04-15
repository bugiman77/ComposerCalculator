package com.bugiman.domain.models

interface IconResource

data class AppInfo(
    val name: String,
    val packageName: String,
    val icon: IconResource
)
