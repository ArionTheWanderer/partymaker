package com.example.partymaker.data.common

import com.example.partymaker.data.db.entities.PartyEntity
import com.example.partymaker.domain.common.Mapper
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.presentation.di.party.PartyScope
import javax.inject.Inject

@PartyScope
class PartyEntityMapper
@Inject constructor(): Mapper<Party, PartyEntity> {
    override fun mapFromDomainModel(domainModel: Party): PartyEntity =
        PartyEntity(
            id = domainModel.id,
            name = domainModel.name
        )

    override fun mapToDomainModel(externalModel: PartyEntity): Party =
        Party(
            id = externalModel.id,
            name = externalModel.name
        )
}
