package com.likander.newsy.features.headline.data.remote.model

import com.google.gson.annotations.SerializedName

data class SourceDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)