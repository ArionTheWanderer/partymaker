package com.example.partymaker.data.datasources

import com.example.partymaker.data.db.PartyDao
import com.example.partymaker.data.db.entities.PartyEntity

interface ILocalDataSource {
    fun addParty(partyEntity: PartyEntity)
    fun insertParty(partyEntity: PartyEntity)
    fun getParty(id: Long)
    fun getAllParties()
}

class LocalDataSource(private val partyDao: PartyDao) : ILocalDataSource {
    override fun addParty(partyEntity: PartyEntity) {
        TODO("Not yet implemented")
    }

    override fun insertParty(partyEntity: PartyEntity) {
        TODO("Not yet implemented")
    }

    override fun getParty(id: Long) {
        TODO("Not yet implemented")
    }

    override fun getAllParties() {
        TODO("Not yet implemented")
    }

}
