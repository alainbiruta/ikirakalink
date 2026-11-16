package com.microcareer.match.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import java.util.Locale;

/**
 * Apply a locale to a Context and persist the choice to SharedPreferences.
 *
 * Usage in Activity.attachBaseContext():
 *  override fun attachBaseContext(base: Context) {
 *      super.attachBaseContext(LocaleHelper.applyLocale(base))
 *  }
 *
 * Usage when user picks a language in ProfileViewModel:
 *  LocaleHelper.setLocale(context, "rw")   // Kinyarwanda
 *  LocaleHelper.setLocale(context, "en")   // English
 *  LocaleHelper.setLocale(context, "fr")   // French
 *  // Then recreate the Activity so the new locale takes effect.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u000e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\fJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0004H\u0002J\u0016\u0010\u0012\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0004J\u0018\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/microcareer/match/utils/LocaleHelper;", "", "()V", "DEFAULT_LOCALE", "", "KEY_LOCALE", "PREFS_NAME", "supportedLanguages", "Ljava/util/LinkedHashMap;", "getSupportedLanguages", "()Ljava/util/LinkedHashMap;", "applyLocale", "Landroid/content/Context;", "context", "getSavedLocale", "persist", "", "languageTag", "setLocale", "updateConfig", "app_debug"})
public final class LocaleHelper {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "microcareer_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LOCALE = "selected_locale";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_LOCALE = "rw";
    @org.jetbrains.annotations.NotNull()
    private static final java.util.LinkedHashMap<java.lang.String, java.lang.String> supportedLanguages = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.utils.LocaleHelper INSTANCE = null;
    
    private LocaleHelper() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.LinkedHashMap<java.lang.String, java.lang.String> getSupportedLanguages() {
        return null;
    }
    
    /**
     * Persist + apply locale. Call this when the user picks a language.
     */
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context setLocale(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String languageTag) {
        return null;
    }
    
    /**
     * Re-apply the persisted locale. Call this from Activity.attachBaseContext()
     * so every screen respects the saved choice.
     */
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context applyLocale(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSavedLocale(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    private final void persist(android.content.Context context, java.lang.String languageTag) {
    }
    
    private final android.content.Context updateConfig(android.content.Context context, java.lang.String languageTag) {
        return null;
    }
}