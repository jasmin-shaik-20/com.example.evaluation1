package com.example.plugins

import com.example.config.DatabaseConfig.driver
import com.example.config.DatabaseConfig.password
import com.example.config.DatabaseConfig.url
import com.example.config.DatabaseConfig.user
import com.example.database.table.Stages
import com.example.database.table.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
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
        // Define the stages with unique order IDs
        val stagesData = listOf(
            Pair(1, "Mobile verification"),
            Pair(2, "Email verification"),
            Pair(3, "Set MPIN"),
            Pair(4, "Add Aadhaar card"),
            Pair(5, "Add PAN card"),
            Pair(6, "Set 2FA")
        )

        // Check if stages already exist
        val existingStages = Stages.selectAll().map { it[Stages.orderId] }

        // Insert stages that do not already exist
        for ((orderId, stageName) in stagesData) {
            if (orderId !in existingStages) {
                Stages.insert {
                    it[Stages.orderId] = orderId
                    it[Stages.stageName] = stageName
                }
            }
        }
    }
}


suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }