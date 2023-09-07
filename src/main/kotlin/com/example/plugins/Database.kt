package com.example.plugins

import com.example.config.DatabaseConfig.driver
import com.example.config.DatabaseConfig.password
import com.example.config.DatabaseConfig.url
import com.example.config.DatabaseConfig.user
import com.example.database.table.Stages
import com.example.database.table.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabase() {

    Database.connect(url = url, driver = driver, user = user, password = password)

    transaction {
        SchemaUtils.createMissingTablesAndColumns(Users, Stages)
    }
}
fun insertStages() {
    transaction {
        // Insert stages into the Stages table
        Stages.batchInsert(
            listOf(
                Pair(1, "Mobile verification"),
                Pair(2, "Email verification"),
                Pair(3, "Set MPIN"),
                Pair(4, "Add Aadhaar card"),
                Pair(5, "Add PAN card"),
                Pair(6, "Set 2FA")
            )
        ) { (orderId, stageName) ->
            this[Stages.orderId] = orderId
            this[Stages.stageName] = stageName
        }
    }
}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }