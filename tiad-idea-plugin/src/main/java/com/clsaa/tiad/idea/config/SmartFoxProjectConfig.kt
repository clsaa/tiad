import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 *
 *
 * @author caikang
 * @date 2017/03/01
 */
@State(name = "SmartFoxProjectConfig", storages = [com.intellij.openapi.components.Storage(file = "smartfox_info.xml")])
class SmartFoxProjectConfig : PersistentStateComponent<SmartFoxProjectConfig> {
    var projectInspectionClosed = false

    override fun getState(): SmartFoxProjectConfig? {
        return this
    }

    override fun loadState(state: SmartFoxProjectConfig) {
        XmlSerializerUtil.copyBean(state, this)
    }
}
