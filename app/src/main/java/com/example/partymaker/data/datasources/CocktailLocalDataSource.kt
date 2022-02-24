package com.example.partymaker.data.datasources

import com.example.partymaker.data.common.CocktailEntityMapper
import com.example.partymaker.data.db.CocktailDao
import com.example.partymaker.data.db.entities.*
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.CocktailDomain
import javax.inject.Inject
import javax.inject.Singleton

interface ICocktailLocalDataSource {
    suspend fun updateCocktail(
        cocktailEntity: CocktailEntity,
        cocktailIngredientListEntity: List<CocktailIngredientEntity>)
    suspend fun insertCocktail(
        cocktailEntity: CocktailEntity,
        cocktailIngredientListEntity: List<CocktailIngredientEntity>,
        partyCocktailCrossRef: PartyCocktailCrossRef)
    suspend fun deleteCocktail(cocktailId: Long, partyId: Long)
    suspend fun getCocktailById(cocktailId: Long, partyId: Long): DataState<CocktailDomain>
}

private const val TAG = "MealLocalDataSource"

@Singleton
class CocktailLocalDataSource
@Inject constructor(
    private val cocktailDao: CocktailDao,
    private val cocktailEntityMapper: CocktailEntityMapper
) : ICocktailLocalDataSource {

    override suspend fun updateCocktail(
        cocktailEntity: CocktailEntity,
        cocktailIngredientListEntity: List<CocktailIngredientEntity>
    ) = cocktailDao.updateCocktail(cocktailEntity, cocktailIngredientListEntity)

    override suspend fun insertCocktail(
        cocktailEntity: CocktailEntity,
        cocktailIngredientListEntity: List<CocktailIngredientEntity>,
        partyCocktailCrossRef: PartyCocktailCrossRef
    ) = cocktailDao.insertCocktail(cocktailEntity, cocktailIngredientListEntity, partyCocktailCrossRef)

    override suspend fun deleteCocktail(cocktailId: Long, partyId: Long) {
        cocktailDao.deletePartyCocktailCrossRef(partyId = partyId, cocktailId = cocktailId)
        val cocktailRelationsCount = cocktailDao.cocktailRelationsCount(cocktailId)
        if (cocktailRelationsCount == 0L) {
            cocktailDao.delete(cocktailId)
        }
    }

    override suspend fun getCocktailById(cocktailId: Long, partyId: Long): DataState<CocktailDomain> {
        val cocktailFromLocal = cocktailDao.get(cocktailId)
        if (cocktailFromLocal != null) {
            val cocktailDomain = cocktailEntityMapper.mapToDomainModel(cocktailFromLocal)
            val partyCocktailRelationsCount = cocktailDao.partyCocktailRelationsCount(cocktailId, partyId)
            if (partyCocktailRelationsCount != 0L) {
                cocktailDomain.isInCurrentParty = true
            }
            return DataState.Data(cocktailDomain)
        }
        return DataState.Error("DB is empty")
    }

}
