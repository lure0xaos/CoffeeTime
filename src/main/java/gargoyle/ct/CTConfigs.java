package gargoyle.ct;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputValidation;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CTConfigs implements Externalizable, ObjectInputValidation {

    private static final long serialVersionUID = 2320942613378643025L;

    private final Map<String, CTConfig> configs;

    public CTConfigs() {
        configs = new LinkedHashMap<>();
    }

    protected CTConfigs(CTConfig... configs) {
        this(Arrays.asList(configs));
    }

    public CTConfigs(List<CTConfig> configs) {
        this();
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
                configs.add(new CTConfig(aData));
            } catch (IllegalArgumentException ex) {
                Log.error("skip invalid config line: {0}", aData);
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
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        List<CTConfig> cfgs = new LinkedList<>();
        String line;
        while ((line = in.readLine()) != null) {
            cfgs.add(new CTConfig(line));
        }
        setConfigs(cfgs);
    }

    @Override
    public String toString() {
        return "CTConfigs [configs=" + configs + "]";
    }

    @Override
    public void validateObject() throws InvalidObjectException {
        for (CTConfig config : configs.values()) {
            if (config == null || config.isNotValid()) {
                throw new InvalidObjectException("not valid config: " + config);
            }
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBytes(format());
    }
}
