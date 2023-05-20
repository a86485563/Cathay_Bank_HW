package com.example.cathay_bank_hw.util

import android.content.Context
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.module.AppGlideModule
import com.example.cathay_bank_hw.network.ApiProvider.ignoreAllSSLErrors
import okhttp3.OkHttpClient
import java.io.InputStream

@GlideModule
class AppGlideModule : AppGlideModule(){
    override fun registerComponents(@NonNull context: Context, @NonNull glide: Glide, @NonNull registry: Registry) {
        val okHttpClient = OkHttpClient.Builder().apply {
            //使用略過ssl認證
            ignoreAllSSLErrors()
        }.build()
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }


}