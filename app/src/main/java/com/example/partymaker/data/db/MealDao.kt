package com.example.partymaker.data.db

import androidx.room.*
import com.example.partymaker.data.db.entities.MealEntity
import com.example.partymaker.data.db.entities.MealIngredientEntity
import com.example.partymaker.data.db.entities.PartyMealCrossRef
import com.example.partymaker.data.db.relations.MealWithIngredients

@Dao
interface MealDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeal(
        mealEntity: MealEntity,
        mealIngredientListEntity: List<MealIngredientEntity>,
        partyMealCrossRef: PartyMealCrossRef)

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMeal(
        mealEntity: MealEntity,
        mealIngredientListEntity: List<MealIngredientEntity>)

    @Transaction
    @Query("SELECT * FROM meal WHERE meal_id = :id")
    suspend fun get(id: Long): MealWithIngredients?

    @Query("SELECT COUNT(*) AS count FROM party_meal WHERE meal_id = :mealId")
    suspend fun mealRelationsCount(mealId: Long): Long

    @Query("SELECT COUNT(*) AS count FROM party_meal WHERE party_id = :partyId AND meal_id = :mealId")
    suspend fun partyMealRelationsCount(mealId: Long, partyId: Long): Long

    @Query("DELETE FROM meal WHERE meal_id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM party_meal WHERE party_id = :partyId AND meal_id = :mealId")
    suspend fun deletePartyMealCrossRef(partyId: Long, mealId: Long)

}
