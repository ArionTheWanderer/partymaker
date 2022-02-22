package com.example.partymaker.data.common

import com.example.partymaker.data.db.entities.PartyEntity
import com.example.partymaker.domain.common.Mapper
import com.example.partymaker.domain.entities.Party
import com.example.partymaker.presentation.di.activity.ActivityScope
import javax.inject.Inject

@ActivityScope
class PartyEntityMapper
@Inject constructor(): Mapper<Party, PartyEntity> {
    override fun mapFromDomainModel(domainModel: Party): PartyEntity =
        PartyEntity(
            partyId = domainModel.id,
            name = domainModel.name
        )

    override fun mapToDomainModel(externalModel: PartyEntity): Party =
        Party(
            id = externalModel.partyId,
            name = externalModel.name
        )
}
