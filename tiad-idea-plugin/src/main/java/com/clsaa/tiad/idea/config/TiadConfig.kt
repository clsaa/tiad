package com.clsaa.tiad.idea.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import java.util.*

@State(name = "com.clsaa.tiad.idea.config.TiadConfig", storages = [Storage(file = "smartfox/tiad.xml")])
class TiadConfig : PersistentStateComponent<TiadConfig> {
    var astCacheTime = 1000L
    var astCacheEnable = true

    var ruleCacheTime = 1000L
    var ruleCacheEnable = false

    var analysisBeforeCheckin = false

    var locale: String = ""
        get() {
            if (field.isEmpty()) {
                val lang = Locale.getDefault().language
                return if (lang != Locale.ENGLISH.language && lang != Locale.CHINESE.language) {
                    Locale.ENGLISH.language
                } else Locale.getDefault().language
            }

            return field
        }

    fun toggleLanguage() {
        locale = if (localeEn == locale) localeZh else localeEn
    }

    override fun getState(): TiadConfig {
        return this
    }

    override fun loadState(state: TiadConfig) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val localeEn = Locale.ENGLISH.language!!
        val localeZh = Locale.CHINESE.language!!
    }
}
