package gargoyle.ct;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CTConfigs implements Externalizable, ObjectInputValidation {
    public static CTConfigs parse(final String line) {
        return new CTConfigs(CTConfigs.read(line));
    }

    private static CTConfig[] read(final String line) {
        final String[] data = CTConfigsDataConverter.getInstance().parse(line);
        final List<CTConfig> configs = new LinkedList<CTConfig>();
        for (int i = 0; i < data.length; i++) {
            try {
                configs.add(new CTConfig(data[i]));
            } catch (final IllegalArgumentException ex) {
                Log.error("skip invalid config line: {0}", data[i]);
            }
        }
        return configs.toArray(new CTConfig[configs.size()]);
    }

    private final LinkedHashMap<String, CTConfig> configs;

    public CTConfigs() {
        this.configs = new LinkedHashMap<String, CTConfig>();
    }

    public CTConfigs(final CTConfig... configs) {
        this(Arrays.asList(configs));
    }

    public CTConfigs(final List<CTConfig> configs) {
        this();
        this.setConfigs(configs);
    }

    public void addConfig(final CTConfig config) {
        final String name = config.getName();
        if (!this.configs.containsKey(name)) {
            this.configs.put(name, config);
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final CTConfigs other = (CTConfigs) obj;
        if (this.configs == null) {
            if (other.configs != null) {
                return false;
            }
        } else if (!this.configs.equals(other.configs)) {
            return false;
        }
        return true;
    }

    public String format() {
        final List<String> formats = new LinkedList<String>();
        for (final CTConfig config : this.configs.values()) {
            formats.add(config.format());
        }
        return CTConfigsDataConverter.getInstance().format(TimeUnit.MINUTES,
                formats.toArray(new String[formats.size()]));
    }

    public CTConfig getConfig(final String name) {
        return this.configs.get(name);
    }

    public List<CTConfig> getConfigs() {
        return Collections.unmodifiableList(new LinkedList<CTConfig>(this.configs.values()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.configs == null) ? 0 : this.configs.hashCode());
        return result;
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        final List<CTConfig> cfgs = new LinkedList<CTConfig>();
        String line;
        while ((line = in.readLine()) != null) {
            cfgs.add(new CTConfig(line));
        }
        this.setConfigs(cfgs);
    }

    public void setConfigs(final List<CTConfig> configs) {
        this.configs.clear();
        for (final CTConfig config : configs) {
            this.addConfig(config);
        }
    }

    @Override
    public String toString() {
        return "CTConfigs [configs=" + this.configs + "]";
    }

    @Override
    public void validateObject() throws InvalidObjectException {
        for (final CTConfig config : this.configs.values()) {
            if ((config == null) || !config.isValid()) {
                throw new InvalidObjectException("not valid config: " + config);
            }
        }
    }

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeBytes(this.format());
    }
}
