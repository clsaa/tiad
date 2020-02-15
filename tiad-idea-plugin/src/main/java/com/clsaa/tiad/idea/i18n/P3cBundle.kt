import com.clsaa.tiad.idea.util.getService
import com.clsaa.tiad.pmd.I18nResources
import com.intellij.CommonBundle
import java.util.*

object P3cBundle {
    private val p3cConfig = P3cConfig::class.java.getService()
    private val resourceBundle = ResourceBundle.getBundle("messages.P3cBundle",
            Locale(p3cConfig.locale), I18nResources.XmlControl())

    fun getMessage(key: String): String {
        return resourceBundle.getString(key).trim()
    }

    fun message(key: String, vararg params: Any): String {
        return CommonBundle.message(resourceBundle, key, *params).trim()
    }
}
