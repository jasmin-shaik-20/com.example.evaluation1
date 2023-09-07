package com.example.utils.helperFunctions

import com.example.database.table.Users
import com.example.model.User
import org.jetbrains.exposed.sql.ResultRow

fun resultRowToUser(row: ResultRow) = User(
    row[Users.id],
    row[Users.name],
    row[Users.email]
)