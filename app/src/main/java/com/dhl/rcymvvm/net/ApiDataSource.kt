package com.dhl.rcymvvm.net

import javax.inject.Inject

class ApiDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getTopNft() = apiService.getTopNft()
    suspend fun getTrendingNft() = apiService.getTrendingNft()
}