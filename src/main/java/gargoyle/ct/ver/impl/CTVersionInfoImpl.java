package gargoyle.ct.ver.impl;

import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.ver.CTVersionInfo;

public class CTVersionInfoImpl implements CTVersionInfo {
    private static final String KEY_ORGANIZATION_NAME = "organization_name";
    private static final String KEY_ORGANIZATION_URL = "organization_url";
    private static final String KEY_PROJECT_DESCRIPTION = "project_description";
    private static final String KEY_PROJECT_NAME = "project_name";
    private static final String KEY_PROJECT_VERSION = "project_version";
    private static final String LOC_VERSION = "messages/version";
    private final CTMessages version;

    public CTVersionInfoImpl() {
        version = new CTMessages(LOC_VERSION);
    }

    @Override
    public String getOrganizationName() {
        return version.getMessage(KEY_ORGANIZATION_NAME);
    }

    @Override
    public String getOrganizationUrl() {
        return version.getMessage(KEY_ORGANIZATION_URL);
    }

    @Override
    public String getProjectDescription() {
        return version.getMessage(KEY_PROJECT_DESCRIPTION);
    }

    @Override
    public String getProjectName() {
        return version.getMessage(KEY_PROJECT_NAME);
    }

    @Override
    public String getProjectVersion() {
        return version.getMessage(KEY_PROJECT_VERSION);
    }
}
