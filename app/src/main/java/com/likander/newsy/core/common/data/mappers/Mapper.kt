package com.likander.newsy.core.common.data.mappers

interface Mapper<Entity : Any?, Model : Any> {
    fun toModel(value: Entity): Model
    fun fromModelToEntity(value: Model): Entity
}