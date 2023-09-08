package com.example.repository

import com.example.dao.StageDao
import com.example.entities.StageEntity
import com.example.model.InputStage
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class StageRepository {

    private val numbersMap = mapOf(1 to "MobileNumber", 2 to "Gmail", 3 to "MPIN", 4 to "Aadhar",5 to "Pan Card",6 to "Set 2FA")

}