package com.apx5.apx5

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import androidx.collection.LruCache
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.apx5.apx5.di.apiModule
import com.apx5.apx5.di.repositoryModule
import com.apx5.apx5.di.retrofitModule
import com.apx5.apx5.di.viewModelModule
import com.apx5.apx5.ui.login.kakao.KakaoSDKAdapter
import com.kakao.auth.KakaoSDK
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * ProoyaClient
 */

class ProoyaClient : Application() {
    /**
     * 이미지 로더를 반환한다.
     * @return 이미지 로더
     */
    var imageLoader: ImageLoader? = null
        private set

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        instance = this
        KakaoSDK.init(KakaoSDKAdapter())

        val requestQueue = Volley.newRequestQueue(this)

        val imageCache = object : ImageLoader.ImageCache {
            val imageCache = LruCache<String, Bitmap>(30)

            override fun putBitmap(key: String, value: Bitmap) {
                imageCache.put(key, value)
            }

            override fun getBitmap(key: String): Bitmap? {
                return imageCache.get(key)
            }
        }

        imageLoader = ImageLoader(requestQueue, imageCache)

        startKoin {
            androidContext(this@ProoyaClient)
            modules(listOf(
                apiModule,
                repositoryModule,
                retrofitModule,
                viewModelModule
            ))
        }

    }

    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    companion object {
        @Volatile
        private var instance: ProoyaClient? = null

        lateinit var appContext: Context
    }
}
