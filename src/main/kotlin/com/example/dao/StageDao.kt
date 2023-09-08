package com.example.dao

import com.example.model.InputStage
import java.util.*

interface StageDao {

    suspend fun addStage(stageData: InputStage): UUID
}