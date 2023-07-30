package com.dhl.rcymvvm.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.dhl.rcymvvm.R
import com.dhl.rcymvvm.databinding.ActivityMainBinding
import com.dhl.rcymvvm.net.Resource
import com.dhl.rcymvvm.ui.adapter.NftAdapter
import com.dhl.rcymvvm.ui.vm.NftViewModel
import com.dhl.rcymvvm.utils.Utility.changeVisibility
import com.dhl.rcymvvm.utils.Utility.setTransparentStatusBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    private val nftAdapter = NftAdapter()

    private val nftViewModel :NftViewModel by lazy {
        ViewModelProvider(this)[NftViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatusBar()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAdapterAndGetData()
    }

    private fun setUpAdapterAndGetData() {
        binding.rcvNft.apply {
            val gridLayoutManager = GridLayoutManager(this@MainActivity, 2)
            gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (nftAdapter.getItemViewType(position)) {
                        R.layout.rcv_lyt_title -> 2
                        R.layout.rcv_lyt_featured -> 2
                        R.layout.rcv_lyt_top_picks -> 1
                        R.layout.rcv_lyt_trending -> 2
                        else -> 1
                    }
                }

            }

            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = nftAdapter
        }



        lifecycleScope.launch {
            nftViewModel.nft.collect { result ->
                when (result) {
                    Resource.Loading -> binding.loading.changeVisibility(View.VISIBLE)
                    is Resource.Failure -> {
                        binding.loading.changeVisibility(View.GONE)
                    }

                    is Resource.Success -> {
                        binding.loading.changeVisibility(View.GONE)
                        nftAdapter.submitList(result.value)
                    }
                }

            }
        }
    }


}
