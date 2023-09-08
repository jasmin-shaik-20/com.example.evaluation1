package com.example.entities

import com.example.database.table.Stages
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class StageEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<StageEntity>(Stages)
    var stageName by Stages.stageName
    var userId by UserEntity optionalReferencedOn Stages.userId
}