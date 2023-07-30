package com.dhl.rcymvvm.net

import com.dhl.rcymvvm.data.NftData
import com.dhl.rcymvvm.utils.EndPoints
import retrofit2.http.GET

interface ApiService {

    @GET(EndPoints.TOP_NFT)
    suspend fun getTopNft():List<NftData.Top>

    @GET(EndPoints.TRENDING_NFT)
    suspend fun getTrendingNft():List<NftData.Trending>

}