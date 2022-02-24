package com.example.partymaker.data.db

import androidx.room.*
import com.example.partymaker.data.db.entities.*
import com.example.partymaker.data.db.relations.CocktailWithIngredients

@Dao
interface CocktailDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCocktail(
        cocktailEntity: CocktailEntity,
        cocktailIngredientListEntity: List<CocktailIngredientEntity>,
        partyCocktailCrossRef: PartyCocktailCrossRef)

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCocktail(
        cocktailEntity: CocktailEntity,
        cocktailIngredientListEntity: List<CocktailIngredientEntity>)

    @Transaction
    @Query("SELECT * FROM cocktail WHERE cocktail_id = :id")
    suspend fun get(id: Long): CocktailWithIngredients?

    @Query("SELECT COUNT(*) AS count FROM party_cocktail WHERE cocktail_id = :cocktailId")
    suspend fun cocktailRelationsCount(cocktailId: Long): Long

    @Query("SELECT COUNT(*) AS count FROM party_cocktail WHERE party_id = :partyId AND cocktail_id = :cocktailId")
    suspend fun partyCocktailRelationsCount(cocktailId: Long, partyId: Long): Long

    @Query("DELETE FROM cocktail WHERE cocktail_id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM party_cocktail WHERE party_id = :partyId AND cocktail_id = :cocktailId")
    suspend fun deletePartyCocktailCrossRef(partyId: Long, cocktailId: Long)

}
