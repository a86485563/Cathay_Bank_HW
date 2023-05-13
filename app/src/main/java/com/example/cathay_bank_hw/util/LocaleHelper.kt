package com.example.cathay_bank_hw.util

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.preference.PreferenceManager
import java.util.*

object LocaleHelper {
    val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

    fun setLocale(context: Context,lang: String) : Context {
        persist(context, lang)
        // updating the language for devices above android nougat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, lang)
        }
        // for devices having lower version of android os
        return updateResourcesLegacy(context, lang)
    }

    /*private static void persist(Context context, String language) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(SELECTED_LANGUAGE, language);
            editor.apply();
        }
    * */

    private fun persist(context: Context,lang: String){
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE,lang)
        editor.apply()
    }

    private fun updateResources (context: Context,lang:String): Context{
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return context.createConfigurationContext(config)
    }

    private fun updateResourcesLegacy(context: Context,lang: String) : Context {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val resources= context.resources
        val config = resources.configuration
        config.setLocale(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLayoutDirection(locale);
        }
        resources.updateConfiguration(config, resources.displayMetrics);
        return context;
    }

    fun getDefaultRes(context: Context) : Resources{
        return setLocale(context ,"zh-tw" ).resources
    }
}