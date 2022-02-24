package com.example.partymaker.data.datasources

import com.example.partymaker.data.db.MealDao
import com.example.partymaker.data.db.entities.MealEntity
import com.example.partymaker.data.db.entities.MealIngredientEntity
import com.example.partymaker.data.db.entities.PartyMealCrossRef
import com.example.partymaker.data.db.relations.MealWithIngredients
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
    suspend fun getMeal(id: Long): MealWithIngredients?
}

private const val TAG = "MealLocalDataSource"

@Singleton
class MealLocalDataSource
@Inject constructor(
    private val mealDao: MealDao
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
        val mealRelationsCount = mealDao.mealRelationsCount(mealId)
        mealDao.deletePartyMealCrossRef(partyId = partyId, mealId = mealId)
        if (mealRelationsCount == 0L) {
            mealDao.delete(mealId)
        }
    }

    override suspend fun getMeal(id: Long): MealWithIngredients? =
        mealDao.get(id)

}
