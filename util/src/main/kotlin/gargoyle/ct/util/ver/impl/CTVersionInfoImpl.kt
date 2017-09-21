package gargoyle.ct.util.ver.impl

import gargoyle.ct.util.messages.impl.CTMessages
import gargoyle.ct.util.ver.CTVersionInfo
import java.util.*


class CTVersionInfoImpl(resourceBundleLoader: (Locale) -> ResourceBundle) : CTVersionInfo {
    private val version: CTMessages = CTMessages(resourceBundleLoader)

    override val organizationName: String
        get() = version.getMessage(KEY_ORGANIZATION_NAME)
    override val organizationUrl: String
        get() = version.getMessage(KEY_ORGANIZATION_URL)
    override val projectDescription: String
        get() = version.getMessage(KEY_PROJECT_DESCRIPTION)
    override val projectName: String
        get() = version.getMessage(KEY_PROJECT_NAME)
    override val projectVersion: String
        get() = version.getMessage(KEY_PROJECT_VERSION)

    companion object {
        private const val KEY_ORGANIZATION_NAME = "organization_name"
        private const val KEY_ORGANIZATION_URL = "organization_url"
        private const val KEY_PROJECT_DESCRIPTION = "project_description"
        private const val KEY_PROJECT_NAME = "project_name"
        private const val KEY_PROJECT_VERSION = "project_version"
    }
}
