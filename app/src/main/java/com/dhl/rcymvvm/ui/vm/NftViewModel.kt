package com.dhl.rcymvvm.ui.vm

import android.content.pm.FeatureInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhl.rcymvvm.data.NftData
import com.dhl.rcymvvm.data.NftRepository
import com.dhl.rcymvvm.net.Resource
import com.dhl.rcymvvm.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NftViewModel @Inject constructor(private val nftRepository: NftRepository):ViewModel(){

    private val _nft = MutableStateFlow<Resource<List<NftData>>>(Resource.Loading)
    val nft : StateFlow<Resource<List<NftData>>> get() = _nft

    init {
        getNft()
    }

    private fun getNft() = viewModelScope.launch{

        _nft.emit(Resource.Loading)
        val topNftDeferred = async {
            nftRepository.getTopNft()
        }

        val trendingNdtDeferred = async {
            nftRepository.getTrendingNft()
        }

        val nftList = mutableListOf<NftData>()

        val topNft = topNftDeferred.await()
        val trendingNft = trendingNdtDeferred.await()

        if(topNft is Resource.Success && trendingNft is Resource.Success){
            nftList.add(NftData.Title(1,"Featured",""))
            nftList.add(NftData.Featured(Constants.FEATURED_IMAGE,Constants.FEATURED_IMAGE_TITLE))
            nftList.add(NftData.Title(2,"Top Pick","View all"))
            nftList.addAll(topNft.value)
            nftList.addAll(trendingNft.value)
            _nft.emit(Resource.Success(nftList))
        }else{
            Resource.Failure(false,null,null)
        }
    }

}