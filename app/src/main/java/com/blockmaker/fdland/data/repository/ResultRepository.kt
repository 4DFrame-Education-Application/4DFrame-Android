package com.blockmaker.fdland.data.repository

import com.blockmaker.fdland.data.source.remote.result.ResultDataSourceImpl

class ResultRepository(private val resultDataSource: ResultDataSourceImpl) {

    suspend fun getResult(
        name: String?,
        accuracy: String?,
        rate: String?,
        imageUrl: String?
    ) {
        resultDataSource.getResult(name, accuracy, rate, imageUrl)
    }
}
