package com.dhl.rcymvvm.data

import com.dhl.rcymvvm.net.ApiDataSource
import com.dhl.rcymvvm.net.SafeApiCall
import javax.inject.Inject

class NftRepository @Inject constructor(private val apiDataSource: ApiDataSource):SafeApiCall{

    suspend fun getTopNft() = safeApiCall {
        apiDataSource.getTopNft()
    }
    suspend fun getTrendingNft() = safeApiCall {
        apiDataSource.getTrendingNft()
    }

}