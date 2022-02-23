package com.example.partymaker.domain.common

interface ResponseMapper <DomainModel, ExternalModel> {
    fun mapToDomainModel(externalModel: ExternalModel): DomainModel
}
