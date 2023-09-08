package com.example.database.table

import com.example.utils.appConstants.GlobalConstants
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect

object Users : UUIDTable() {
    val name = varchar("name", GlobalConstants.MAX_LENGTH)
    val email = varchar("email", GlobalConstants.MAX_LENGTH)
    val currentStage = integer("currentStage").references(Stages.orderId, fkName = "fk_users_currentstage_orderid")
    val nextStage = integer("nextStage")
    val isVerified = bool("isVerified").default(false)
    val lastStageUpdate = long("last_stage_update").default(System.currentTimeMillis())
}


