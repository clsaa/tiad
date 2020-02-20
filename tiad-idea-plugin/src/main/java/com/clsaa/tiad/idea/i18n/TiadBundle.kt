package com.clsaa.tiad.idea.i18n

import com.clsaa.tiad.idea.config.TiadConfig
import com.clsaa.tiad.pmd.I18nResources
import com.intellij.CommonBundle
import com.intellij.openapi.components.ServiceManager
import java.util.*

object TiadBundle {
    private val tiadConfig = ServiceManager.getService(TiadConfig::class.java)
    private val resourceBundle = ResourceBundle.getBundle("messages.TiadBundle",
            Locale(tiadConfig.locale), I18nResources.XmlControl())

    fun getMessage(key: String): String {
        return resourceBundle.getString(key).trim()
    }

    fun message(key: String, vararg params: Any): String {
        return CommonBundle.message(resourceBundle, key, *params).trim()
    }
}
