package gargoyle.ct.ver.impl;

import gargoyle.ct.messages.impl.CTMessages;
import gargoyle.ct.ver.CTVersionInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CTVersionInfoImpl implements CTVersionInfo {
    private static final String KEY_ORGANIZATION_NAME = "organization_name";
    private static final String KEY_ORGANIZATION_URL = "organization_url";
    private static final String KEY_PROJECT_DESCRIPTION = "project_description";
    private static final String KEY_PROJECT_NAME = "project_name";
    private static final String KEY_PROJECT_VERSION = "project_version";
    private static final String LOC_VERSION = "messages/version";
    @NotNull
    private final CTMessages version;

    public CTVersionInfoImpl() {
        version = new CTMessages(LOC_VERSION);
    }

    @Nullable
    @Override
    public String getOrganizationName() {
        return version.getMessage(KEY_ORGANIZATION_NAME);
    }

    @Nullable
    @Override
    public String getOrganizationUrl() {
        return version.getMessage(KEY_ORGANIZATION_URL);
    }

    @Nullable
    @Override
    public String getProjectDescription() {
        return version.getMessage(KEY_PROJECT_DESCRIPTION);
    }

    @Nullable
    @Override
    public String getProjectName() {
        return version.getMessage(KEY_PROJECT_NAME);
    }

    @Nullable
    @Override
    public String getProjectVersion() {
        return version.getMessage(KEY_PROJECT_VERSION);
    }
}
