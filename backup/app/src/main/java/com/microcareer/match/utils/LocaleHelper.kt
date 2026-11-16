package com.microcareer.match.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

/**
 * Apply a locale to a Context and persist the choice to SharedPreferences.
 *
 * Usage in Activity.attachBaseContext():
 *   override fun attachBaseContext(base: Context) {
 *       super.attachBaseContext(LocaleHelper.applyLocale(base))
 *   }
 *
 * Usage when user picks a language in ProfileViewModel:
 *   LocaleHelper.setLocale(context, "rw")   // Kinyarwanda
 *   LocaleHelper.setLocale(context, "en")   // English
 *   LocaleHelper.setLocale(context, "fr")   // French
 *   // Then recreate the Activity so the new locale takes effect.
 */
object LocaleHelper {

    private const val PREFS_NAME = "microcareer_prefs"
    private const val KEY_LOCALE = "selected_locale"
    const val DEFAULT_LOCALE     = "rw"   // ← Kinyarwanda is the app default

    // Map of display name → BCP-47 language tag shown in the Language picker
    val supportedLanguages = linkedMapOf(
        "Ikinyarwanda" to "rw",
        "English"      to "en",
        "Français"     to "fr",
        "Kiswahili"    to "sw"
    )

    /** Persist + apply locale. Call this when the user picks a language. */
    fun setLocale(context: Context, languageTag: String): Context {
        persist(context, languageTag)
        return updateConfig(context, languageTag)
    }

    /**
     * Re-apply the persisted locale. Call this from Activity.attachBaseContext()
     * so every screen respects the saved choice.
     */
    fun applyLocale(context: Context): Context {
        val tag = getSavedLocale(context)
        return updateConfig(context, tag)
    }

    fun getSavedLocale(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LOCALE, DEFAULT_LOCALE) ?: DEFAULT_LOCALE
    }

    private fun persist(context: Context, languageTag: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LOCALE, languageTag)
            .apply()
    }

    private fun updateConfig(context: Context, languageTag: String): Context {
        val locale = Locale.forLanguageTag(languageTag)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}