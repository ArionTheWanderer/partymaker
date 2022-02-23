package com.example.partymaker.domain.common

interface EntityMapper <DomainModel, ExternalModel> {

    fun mapFromDomainModel(domainModel: DomainModel): ExternalModel

    fun mapToDomainModel(externalModel: ExternalModel): DomainModel

}
