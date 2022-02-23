package com.demo.newsapplication.ui.savelist

import android.view.View
import coil.load
import com.demo.newsapplication.R
import com.demo.newsapplication.base.BaseAdapter
import com.demo.newsapplication.databinding.NewsSaveListItemsBinding
import com.demo.newsapplication.models.Article
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class SaveNewsListAdapter(val saveListActivty: SaveNewsListActivity) :
    BaseAdapter<NewsSaveListItemsBinding, Article>(R.layout.news_save_list_items) {
    override fun setClickableView(binding: NewsSaveListItemsBinding): List<View?> {
        return when (binding) {
            is NewsSaveListItemsBinding -> {
                listOf(binding.notificationConstraint)
            }
            else -> listOf(binding.root)
        }
    }

    override fun setLongClickableView(binding: NewsSaveListItemsBinding): List<View?> =
        listOf()

    override fun onBind(
        binding: NewsSaveListItemsBinding,
        position: Int,
        item: Article,
        payloads: MutableList<Any>?
    ) {
        binding.run {
            Timber.d("CheackImage :::: ${item.urlToImage}")
            if (item.urlToImage.isNullOrEmpty()) {
                imageProfile.load(item.urlToImage) {
                    placeholder(R.drawable.ic_placeholder)
                }
            }
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val output = SimpleDateFormat("dd/MM/yyyy")
            var date: Date? = null
                date = input.parse(item.publishedAt)
            val formatted = output.format(date)
            Timber.d("DATE::::::", "" + formatted)
            tvtNewsDateTime.text = saveListActivty.getString(R.string.date_time ,formatted, item.author)
            tvtSaveDiscription.text =  item.title
//            tvtNewsDateTime.text = item.content
            tvtLink.text = item.url

        }
    }
}