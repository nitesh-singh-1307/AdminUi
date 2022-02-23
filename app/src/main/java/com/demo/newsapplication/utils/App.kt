package com.demo.newsapplication.utils
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.databinding.library.BuildConfig
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import coil.ImageLoader
import com.demo.newsapplication.data.database.AppDatabase
import com.demo.newsapplication.data.database.repository.ChatRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import okhttp3.OkHttpClient
import timber.log.Timber
import timber.log.Timber.*
import java.util.*


/**
 * Created by pragya on 25/05/21
 */

@DelicateCoroutinesApi
@ObsoleteCoroutinesApi
class App : Application(), LifecycleObserver  {
//    private lateinit var dao: AppDao
    val database by lazy { AppDatabase.getInstance(this.applicationContext) }
    val chatRepository by lazy { ChatRepository(database.chatDao()) }
//    private lateinit var payPalConfiguration: PayPalConfiguration

    companion object {
        private lateinit var mInstance: App
        @Synchronized
        fun getInstance(): App = mInstance
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        Timber.plant(DebugTree())

//        dao = database.getDao()
//        startSocketService()
    }


//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(if (base != null) LocaleManager.setLocale(base) else base)
//    }
//
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        LocaleManager.setLocale(this)
//    }


//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(localeAppDelegate.attachBaseContext(base))
//    }
//
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        localeAppDelegate.onConfigurationChanged(this)
//    }

//    override fun getApplicationContext(): Context =
//        onAttach(super.getApplicationContext())


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onMoveToForeground() {
        Timber.d("ON_RESUME")
        try {
//            startSocketService()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onMoveToBackground() {
        Timber.d("ON_PAUSE")
//        stopSocketService()
    }



//    fun getPref() = pref
//    fun getDao() = dao
//    fun getPayPal() = payPalConfiguration
}