package com.dhl.rcymvvm.ui.vh

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.dhl.rcymvvm.data.NftData
import com.dhl.rcymvvm.databinding.RcvLytFeaturedBinding
import com.dhl.rcymvvm.databinding.RcvLytTitleBinding
import com.dhl.rcymvvm.databinding.RcvLytTopPicksBinding
import com.dhl.rcymvvm.databinding.RcvLytTrendingBinding
import com.dhl.rcymvvm.utils.Constants

sealed class NftViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root){

    var itemClick :((view: View, item:NftData,position:Int)->Unit)? = null


    class TitleViewHolder(private val binding: RcvLytTitleBinding):NftViewHolder(binding){
        fun bind(title:NftData.Title){
            binding.txtFeatured.text = title.title
            binding.txtViewAll.text = title.viewAll
            binding.txtViewAll.setOnClickListener {
                itemClick?.invoke(it,title,adapterPosition)
            }
        }
    }

    class FeaturedViewHolder(private val binding:RcvLytFeaturedBinding):NftViewHolder(binding){
        fun bind(featured: NftData.Featured){
            binding.imgFeatured.load(Constants.FEATURED_IMAGE){
                crossfade(true)
                transformations(RoundedCornersTransformation(20F))
            }
            binding.imgFeatured.setOnClickListener {
                itemClick?.invoke(it,featured,adapterPosition)
            }
            binding.txtFeaturedTitle.text = Constants.FEATURED_IMAGE_TITLE
        }
    }

    class TopPicksViewHolder(private val  binding:RcvLytTopPicksBinding):NftViewHolder(binding){
        fun bind(topPicks:NftData.Top){
            binding.imgTopPicks.load(topPicks.image){
                crossfade(true)
                transformations(RoundedCornersTransformation(20F))
            }
            binding.imgTopPicks.setOnClickListener {
                itemClick?.invoke(it,topPicks,adapterPosition)
            }
        }
    }

    class TrendingViewHolder(private val binding:RcvLytTrendingBinding):NftViewHolder(binding){

        fun bind(trending:NftData.Trending){
            binding.imgTrending.load(trending.image){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            binding.topNftContainer.setOnClickListener {
                itemClick?.invoke(it,trending,adapterPosition)
            }
            binding.txtNftTitle.text = trending.name
            binding.txtCategory.text = trending.category
        }


    }

}