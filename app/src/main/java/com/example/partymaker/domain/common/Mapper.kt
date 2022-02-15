package com.example.partymaker.domain.common

interface Mapper <DomainModel, ExternalModel> {

    fun mapFromDomainModel(domainModel: DomainModel): ExternalModel

    fun mapToDomainModel(externalModel: ExternalModel): DomainModel

}
