package com.example.utils.helperFunctions

import com.example.database.table.Users
import com.example.model.User
import org.jetbrains.exposed.sql.ResultRow

fun resultRowToUser(row: ResultRow)=
    User(row[Users.id].value.toString(),
        row[Users.name],
        row[Users.email],
        row[Users.currentStage],
        row[Users.nextStage],
        row[Users.isVerified],
        row[Users.lastStageUpdate]
    )