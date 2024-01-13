package com.likander.newsy.features.core.data.mappers

interface Mapper<T : Any?, Model : Any> {
    fun toModel(value: T): Model
    fun fromModel(value: Model): T
}