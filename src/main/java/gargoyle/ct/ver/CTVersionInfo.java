package gargoyle.ct.ver;

import org.jetbrains.annotations.Nullable;

public interface CTVersionInfo {
    @Nullable String getOrganizationName();

    @Nullable String getOrganizationUrl();

    @Nullable String getProjectDescription();

    @Nullable String getProjectName();

    @Nullable String getProjectVersion();
}
