package com.example.partymaker.domain.repositories

import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealDomain

interface IMealRepository {
    suspend fun getMealByName(name: String): DataState<List<MealDomain>>
}
