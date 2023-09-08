package com.example.entities

import com.example.database.table.Users
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(Users)

    var name by Users.name
    var email by Users.email
    var currentStage by Users.currentStage
    var nextStage by Users.nextStage
    var isVerified by Users.isVerified
}