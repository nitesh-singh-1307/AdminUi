package com.demo.newsapplication.ui.newslist

import android.view.View
import coil.load
import com.bumptech.glide.Glide
import com.demo.newsapplication.R
import com.demo.newsapplication.base.BaseAdapter
import com.demo.newsapplication.base.BaseFilterAdapter
import com.demo.newsapplication.databinding.NewsListItemsBinding
import com.demo.newsapplication.models.Article
import com.demo.newsapplication.models.NewsModuls
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class NewsListAdapter(val mcontect : NewsActivity) : BaseFilterAdapter<NewsListItemsBinding, Article>(R.layout.news_list_items) {
    override fun setClickableView(binding: NewsListItemsBinding): List<View?> {
        return when (binding) {is NewsListItemsBinding -> { listOf(binding.notificationConstraint) }
            else -> listOf(binding.root)
        }
    }

    override fun onBind(
        binding: NewsListItemsBinding,
        position: Int,
        item: Article,
        payloads: MutableList<Any>?
    ) {

        binding.run {
            Timber.d("Newsimage:::::: ${item.urlToImage}")
//                    Glide.with(mcontect).load(item.urlToImage).into(imgNews)
            // https://cleantechnica.com/files/2022/01/redwood-materials-intermediates.jpg
            // https://chorus.stimg.co/23261038/OPINION_SHESOL_BILLIONAIRES_SPACE_0.jpg?h=630&w=1200&fit=crop&bg=999&crop=faces
           // https://www.gannett-cdn.com/presto/2022/02/19/USAT/2c7a3f57-1e4b-4aab-9fa3-2e5390816b5a-AP_Rodents_Family_Dollar_Facility.jpg?auto=webp&crop=5936,3339,x0,y0&format=pjpg&width=1200
            if (!item.urlToImage.isNullOrEmpty()) {
                imgNews.load(item.urlToImage) {
                    placeholder(R.drawable.ic_placeholder)
                }
            }

            if (!item.title.isNullOrEmpty()){
                tvtNewsTitle.text = item.title
            }
            if (!item.description.isNullOrEmpty()){
                tvtNewsDiscription.text = item.description
            }

            if (!item.publishedAt.isNullOrEmpty()){
                val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                val output = SimpleDateFormat("dd/MM/yyyy")
                var date: Date? = null
                date = input.parse(item.publishedAt)
                val formatted = output.format(date)
                tvtNewsTime.text = formatted
            }
        }
    }

    override fun includeItem(query: CharSequence?, item: Article): Boolean {
        Timber.d("$query : ${item.title}")
        return item.title.contains(query.toString(), true)
    }

}