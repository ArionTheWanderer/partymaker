package com.example.partymaker.data.common

import com.example.partymaker.data.db.entities.PartyEntity
import com.example.partymaker.domain.common.EntityMapper
import com.example.partymaker.domain.entities.PartyDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartyEntityMapper
@Inject constructor(): EntityMapper<PartyDomain, PartyEntity> {
    override fun mapFromDomainModel(domainModel: PartyDomain): PartyEntity {
        return PartyEntity(
            partyId = domainModel.id,
            name = domainModel.name
        )
    }

    override fun mapToDomainModel(externalModel: PartyEntity): PartyDomain {
        return PartyDomain(
            id = externalModel.partyId,
            name = externalModel.name,
            cocktails = listOf(),
            meals = listOf()
        )
    }
}