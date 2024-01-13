package com.likander.newsy.features.headline.domain.model

import androidx.annotation.DrawableRes

data class Country(
    val code: String,
    val name: String,
    @DrawableRes val icResId: Int,
)