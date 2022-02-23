package com.demo.newsapplication.ui.savelist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.newsapplication.R
import com.demo.newsapplication.base.BaseActivity
import com.demo.newsapplication.databinding.ActivitySaveNewsListBinding
import com.demo.newsapplication.models.Article
import com.demo.newsapplication.ui.newslist.NewsActivity
import com.demo.newsapplication.ui.newslist.NewsDetailsActivity
import com.demo.newsapplication.utils.App
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.jetbrains.anko.startActivity
import timber.log.Timber

class SaveNewsListActivity : BaseActivity<ActivitySaveNewsListBinding>(R.layout.activity_save_news_list) , View.OnClickListener {
    private lateinit var saveNewsListAdapter: SaveNewsListAdapter
    var articleList : List<Article>? = null
    val app by lazy { App.getInstance() }
    val chatRepository by lazy { app.chatRepository }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_save_news_list)
        init()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.imgFilter ->{
                articleList =  chatRepository.getAscAllNewsData()
                Timber.d("CheckAscArticles :::: ${articleList?.size}")
                saveNewsListAdapter.addAll(articleList!!)
                binding.rvNotificationDetail.adapter = saveNewsListAdapter
            }
            R.id.ivBack ->{
                startActivity<NewsActivity>()
            }
        }
    }

    @ObsoleteCoroutinesApi
    @DelicateCoroutinesApi
    private fun init() {
        windowsStatusTrensparent()
        binding.imgFilter.setOnClickListener(this)
        binding.toolbar.ivBack.setOnClickListener(this)
        binding.toolbar.tvTitle.text = getString(R.string.str_save)
        saveNewsListAdapter = SaveNewsListAdapter(this@SaveNewsListActivity)
        binding.rvNotificationDetail.layoutManager = LinearLayoutManager(this@SaveNewsListActivity)
        binding.rvNotificationDetail.isNestedScrollingEnabled = false
        articleList =  chatRepository.getAllNewsData()
        Timber.d("CheckArticles :::: ${articleList?.size}")
        saveNewsListAdapter.addAll(articleList!!)
        binding.rvNotificationDetail.adapter = saveNewsListAdapter
//        getNotification()
    }
}