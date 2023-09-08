package com.example.database.table

import com.example.utils.appConstants.GlobalConstants
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table

object Stages : Table() {
    val orderId = integer("orderId").uniqueIndex()
    val stageName = varchar("stageName", GlobalConstants.MAX_LENGTH)
}