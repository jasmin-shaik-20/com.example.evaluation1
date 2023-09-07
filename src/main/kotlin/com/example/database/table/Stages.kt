package com.example.database.table

import com.example.utils.appConstants.GlobalConstants
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table

object Stages : UUIDTable() {
    val stageName = varchar("stage_name", GlobalConstants.MAX_LENGTH)
}