package com.demo.newsapplication.ui.newslist

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.newsapplication.R
import com.demo.newsapplication.base.BaseActivity
import com.demo.newsapplication.databinding.ActivityMainBinding
import com.demo.newsapplication.models.Article
import com.demo.newsapplication.models.NewsModuls
import com.demo.newsapplication.utils.App
import com.demo.newsapplication.utils.BUS_EVENT_PROFILE_UPDATED
import com.demo.newsapplication.utils.EventBus
import timber.log.Timber


class NewsActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var notificationAdapter: NewsListAdapter
    private var newsModuls: NewsModuls? = null
    private var articleList: List<Article> = listOf()
    private var newsModulsList: List<Article> = listOf()
    val app by lazy { App.getInstance() }
    val chatRepository by lazy { app.chatRepository }
    private var isconnectboolean: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initS()
        setUpObserver()
        initListeners()
    }

    private fun initListeners() {
        notificationAdapter.setItemClickListener { _view, _position, _getCountryData ->
            when (_view.id) {
                R.id.notificationConstraint -> {
                    val intent = Intent(this, NewsDetailsActivity::class.java)
                    intent.putExtra("AnyNameOrKey", _getCountryData)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }

    private fun setUpObserver() {
        viewModel.run {
            apiErrors.observe(this@NewsActivity) { handleError(it) }
            appLoader.observe(this@NewsActivity) { updateLoaderUI(it) }
            getNationalityOption.observe(this@NewsActivity) {
                Timber.d("GetNewsData:::::: ${it}")
                articleList = it.articles
                chatRepository.getAllNews(it)
//                Timber.d("CheckNewsModuls :::::  ${newsModuls!!.articles}")
                if (it.articles.isEmpty()) {
                } else {
                    notificationAdapter.addAll(it.articles)
                    binding.rvNotificationDetail.adapter = notificationAdapter
                }
            }
        }
        isconnectboolean = isOnline()
        Timber.d("CheckInternetConnectOrNot :::::  ${isconnectboolean}")

        if (!isconnectboolean) {
            newsModuls = chatRepository.getNewsModelData()
            if(newsModulsList != null) {
                newsModulsList = newsModuls!!.articles
                Timber.d("CheckInternetConnectOrNot :::::  ${newsModulsList}")
                notificationAdapter.addAll(newsModulsList)
                binding.rvNotificationDetail.adapter = notificationAdapter
            }
        }else{
            val bundle = Bundle()
            bundle.putString(BUS_EVENT_PROFILE_UPDATED, "")
            EventBus.publish(bundle)
        }
    }

    private fun initS() {
        windowsStatusTrensparent()
        binding.toolbar.ivBack.visibility = View.INVISIBLE
        notificationAdapter = NewsListAdapter(this@NewsActivity)
        binding.rvNotificationDetail.layoutManager =
            LinearLayoutManager(this@NewsActivity)
        binding.rvNotificationDetail.isNestedScrollingEnabled = false
        EventBus.subscribe<Bundle>(listSubscription) {
            Timber.d("ProfilePersonalInfoActivit: EditProfielEvent")
            if (it.containsKey(BUS_EVENT_PROFILE_UPDATED)) {
                viewModel.getNationalityOption()
                recreate()
            }

        }
        viewModel.getNationalityOption()
        binding.etSearchCountry.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        binding.etSearchCountry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(ediSearchCountry: Editable?) {
                notificationAdapter.filter.filter(ediSearchCountry.toString())
            }
        })
    }

    fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}