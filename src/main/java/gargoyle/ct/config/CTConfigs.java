package gargoyle.ct.config;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CTConfigs implements Serializable {

    private static final String                MSG_NOT_VALID_CONVERT_0 = "not valid convert: {0}";
    private static final long                  serialVersionUID        = 2024075953874239351L;
    private final        Map<String, CTConfig> configs                 = new LinkedHashMap<>();

    public CTConfigs(CTConfig... configs) {
        setConfigs(configs);
    }

    private void setConfigs(CTConfig[] configs) {
        this.configs.clear();
        for (CTConfig config : configs) {
            addConfig(config);
        }
    }

    public void addConfig(CTConfig config) {
        String name = config.getName();
        if (!configs.containsKey(name)) {
            configs.put(name, config);
        }
    }

    @SuppressWarnings("TypeMayBeWeakened")
    public CTConfigs(List<CTConfig> configs) {
        setConfigs(configs);
    }

    public CTConfig getConfig(String name) {
        return configs.get(name);
    }

    public List<CTConfig> getConfigs() {
        return Collections.unmodifiableList(new LinkedList<>(configs.values()));
    }

    private void setConfigs(Iterable<CTConfig> configs) {
        this.configs.clear();
        for (CTConfig config : configs) {
            addConfig(config);
        }
    }

    public boolean hasConfig(CTConfig config) {
        return configs.containsKey(config.getName());
    }

    @Override
    public int hashCode() {
        int prime  = 31;
        int result = 1;
        result = prime * result + (configs == null ? 0 : configs.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CTConfigs other = (CTConfigs) obj;
        if (configs == null) {
            if (other.configs != null) {
                return false;
            }
        }
        else if (!Objects.equals(configs, other.configs)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("CTConfigs [configs={0}]", configs);
    }

    public void validate() throws InvalidObjectException {
        for (CTConfig config : configs.values()) {
            if (config == null || config.isNotValid()) {
                throw new InvalidObjectException(MessageFormat.format(MSG_NOT_VALID_CONVERT_0, config));
            }
        }
    }
}
