package com.demo.newsapplication.ui.newslist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import coil.load
import com.demo.newsapplication.R
import com.demo.newsapplication.base.BaseActivity
import com.demo.newsapplication.databinding.ActivityNewsDetailsBinding
import com.demo.newsapplication.models.Article
import com.demo.newsapplication.ui.savelist.SaveNewsListActivity
import com.demo.newsapplication.utils.App
import com.google.gson.Gson
import org.jetbrains.anko.startActivity
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class NewsDetailsActivity :
    BaseActivity<ActivityNewsDetailsBinding>(R.layout.activity_news_details), View.OnClickListener {
    val gson = Gson()
    lateinit var articles: Article
    //    val product = null
    val app by lazy { App.getInstance() }
    val chatRepository by lazy { app.chatRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        iniTi()
        clickListner()
    }

    private fun clickListner() {
        binding.tvSave.setOnClickListener(this)
        binding.toolbar.ivBack.setOnClickListener(this)
        binding.toolbar.imgSave.setOnClickListener(this)
    }

    private fun iniTi() {
        // Make sure to use the same key as in MainActivity
        val product = intent.getParcelableExtra<Article>("AnyNameOrKey")
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val output = SimpleDateFormat("dd/MM/yyyy")
        var date: Date? = null
        try {

            date = input.parse(product?.publishedAt)
            val formatted = output.format(date)
            Timber.d("DATE::::::", "" + formatted)
            binding.tvtDatetime.text = formatted

        }catch (E:Exception){}
        binding.tvtTitle.text = product?.title
        binding.tvtJobJd.text = product?.author
        binding.tvtJobJd2.text = product?.content
        binding.imageProfile.load(product?.urlToImage)
        binding.imgFront.load(product?.urlToImage)
    }

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivBack -> {
                startActivity<NewsActivity>()
            }
            R.id.imgSave ->{
                val intent = Intent(this, SaveNewsListActivity::class.java)
                startActivity(intent)
            }
            R.id.tvSave -> {
                val product = intent.getParcelableExtra<Article>("AnyNameOrKey")
                articles = product!!
                Timber.d("Articles ::::::::${articles}")
                chatRepository.insertMessage(articles)

            }
        }
    }
}
