package com.likander.newsy.features.core.data.remote.models

import com.google.gson.annotations.SerializedName

data class SourceDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)