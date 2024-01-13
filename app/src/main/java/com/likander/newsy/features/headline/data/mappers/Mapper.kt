package com.likander.newsy.features.headline.data.mappers

interface Mapper<Entity : Any?, Model : Any> {
    fun toModel(value: Entity): Model
    fun fromModelToEntity(value: Model): Entity
}