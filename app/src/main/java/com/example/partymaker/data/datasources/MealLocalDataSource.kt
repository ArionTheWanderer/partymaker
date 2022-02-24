package com.example.partymaker.data.datasources

import com.example.partymaker.data.common.MealEntityMapper
import com.example.partymaker.data.db.MealDao
import com.example.partymaker.data.db.entities.MealEntity
import com.example.partymaker.data.db.entities.MealIngredientEntity
import com.example.partymaker.data.db.entities.PartyMealCrossRef
import com.example.partymaker.data.db.relations.MealWithIngredients
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealDomain
import javax.inject.Inject
import javax.inject.Singleton

interface IMealLocalDataSource {
//    suspend fun updateMeal(
//        mealEntity: MealEntity,
//        mealIngredientListEntity: List<MealIngredientEntity>)
    suspend fun insertMeal(
        mealEntity: MealEntity,
        mealIngredientListEntity: List<MealIngredientEntity>,
        partyMealCrossRef: PartyMealCrossRef
    )
    suspend fun deleteMeal(mealId: Long, partyId: Long)
    suspend fun getMealById(mealId: Long, partyId: Long): DataState<MealDomain>
}

private const val TAG = "MealLocalDataSource"

@Singleton
class MealLocalDataSource
@Inject constructor(
    private val mealDao: MealDao,
    private val mealEntityMapper: MealEntityMapper
) : IMealLocalDataSource {
//    override suspend fun updateMeal(
//        mealEntity: MealEntity,
//        mealIngredientListEntity: List<MealIngredientEntity>
//    ) = mealDao.updateMeal(mealEntity, mealIngredientListEntity)

    override suspend fun insertMeal(
        mealEntity: MealEntity,
        mealIngredientListEntity: List<MealIngredientEntity>,
        partyMealCrossRef: PartyMealCrossRef
    ) = mealDao.insertMeal(mealEntity, mealIngredientListEntity, partyMealCrossRef)

    override suspend fun deleteMeal(mealId: Long, partyId: Long) {
        mealDao.deletePartyMealCrossRef(partyId = partyId, mealId = mealId)
        val mealRelationsCount = mealDao.mealRelationsCount(mealId)
        if (mealRelationsCount == 0L) {
            mealDao.delete(mealId)
        }
    }

    override suspend fun getMealById(mealId: Long, partyId: Long): DataState<MealDomain> {
        val mealFromLocal = mealDao.get(mealId)
        if (mealFromLocal != null) {
            val mealDomain = mealEntityMapper.mapToDomainModel(mealFromLocal)
            val partyMealRelationsCount = mealDao.partyMealRelationsCount(mealId, partyId)
            if (partyMealRelationsCount != 0L) {
                mealDomain.isInCurrentParty = true
            }
            return DataState.Data(mealDomain)
        }
        return DataState.Error("DB is empty")

    }


}
