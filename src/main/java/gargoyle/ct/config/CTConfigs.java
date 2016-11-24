package gargoyle.ct.config;

import gargoyle.ct.config.convert.CTConfigsDataConverter;
import gargoyle.ct.util.Log;

import java.io.InvalidObjectException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CTConfigs {

    private static final String MSG_NOT_VALID_CONVERT_0 = "not valid convert: {0}";

    private final Map<String, CTConfig> configs = new LinkedHashMap<>();

    protected CTConfigs(CTConfig... configs) {
        this(Arrays.asList(configs));
    }

    public CTConfigs(List<CTConfig> configs) {
        setConfigs(configs);
    }

    public static CTConfigs parse(String line) {
        return new CTConfigs(read(line));
    }

    private static CTConfig[] read(String line) {
        String[] data = CTConfigsDataConverter.getInstance().parse(line);
        List<CTConfig> configs = new LinkedList<>();
        for (String aData : data) {
            try {
                configs.add(CTConfig.parse(aData));
            } catch (IllegalArgumentException ex) {
                Log.error("skip invalid convert line: {0}", aData); //NON-NLS
            }
        }
        return configs.toArray(new CTConfig[configs.size()]);
    }

    private void addConfig(CTConfig config) {
        String name = config.getName();
        if (!configs.containsKey(name)) {
            configs.put(name, config);
        }
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
        } else if (!Objects.equals(configs, other.configs)) {
            return false;
        }
        return true;
    }

    public String format() {
        List<String> formats = new LinkedList<>();
        for (CTConfig config : configs.values()) {
            formats.add(config.format());
        }
        return CTConfigsDataConverter.getInstance()
            .format(TimeUnit.MINUTES, formats.toArray(new String[formats.size()]));
    }

    public CTConfig getConfig(String name) {
        return configs.get(name);
    }

    public List<CTConfig> getConfigs() {
        return Collections.unmodifiableList(new LinkedList<>(configs.values()));
    }

    @SuppressWarnings("TypeMayBeWeakened")
    private void setConfigs(List<CTConfig> configs) {
        this.configs.clear();
        for (CTConfig config : configs) {
            addConfig(config);
        }
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (configs == null ? 0 : configs.hashCode());
        return result;
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
