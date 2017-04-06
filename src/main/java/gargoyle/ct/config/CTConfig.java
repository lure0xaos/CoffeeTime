package gargoyle.ct.config;

import gargoyle.ct.config.convert.impl.CTConfigDataConverter;
import gargoyle.ct.util.CTTimeUtil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;
import java.io.ObjectOutput;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public class CTConfig implements Externalizable, ObjectInputValidation {
    private static final String FORMAT_NAME = "{0,number,00}/{1,number,00}";
    private static final String MSG_NOT_VALID = "convert is not valid";
    private static final String STR_INVALID = "invalid";
    private static final long serialVersionUID = -898699928298432564L;
    private long block;
    private transient CTConfigDataConverter configDataConverter = CTConfigDataConverter.getInstance();
    private String name;
    private long warn;
    private long whole;

    public CTConfig() {
        init(0, 0, 0, STR_INVALID);
    }

    private void init(long whole, long block, long warn, String name) {
        if (isNotValid(whole, block, warn)) {
            throw new IllegalArgumentException(MSG_NOT_VALID);
        }
        this.whole = whole;
        this.block = block;
        this.warn = warn;
        this.name = name;
    }

    private boolean isNotValid(long wholeMillis, long blockMillis, long warnMillis) {
        return !isValid(wholeMillis, blockMillis, warnMillis);
    }

    private boolean isValid(long wholeMillis, long blockMillis, long warnMillis) {
        return wholeMillis > blockMillis && blockMillis > warnMillis;
    }

    private CTConfig(long whole, long block, long warn) {
        init(whole, block, warn, name(TimeUnit.MINUTES));
    }

    private CTConfig(String line) {
        long[] data = configDataConverter.parse(line);
        init(data[0], data[1], data[2], name(TimeUnit.MINUTES));
    }

    private String name(TimeUnit unit) {
        return MessageFormat.format(FORMAT_NAME, getWhole(unit), getBlock(unit));
    }

    private long getBlock(TimeUnit unit) {
        return CTTimeUtil.fromMillis(unit, block);
    }

    private long getWhole(TimeUnit unit) {
        return CTTimeUtil.fromMillis(unit, whole);
    }

    public CTConfig(TimeUnit unit, long whole, long block, long warn) {
        this(CTTimeUtil.toMillis(unit, whole), CTTimeUtil.toMillis(unit, block), CTTimeUtil.toMillis(unit, warn));
        name = name(unit);
    }

    public static CTConfig parse(String line) {
        return new CTConfig(line);
    }

    public long getBlock() {
        return block;
    }

    public void setBlock(long block) {
        this.block = block;
    }

    public String getName() {
        return name;
    }

    public long getWarn() {
        return warn;
    }

    public void setWarn(long warn) {
        this.warn = warn;
    }

    public long getWarn(TimeUnit unit) {
        return CTTimeUtil.fromMillis(unit, warn);
    }

    public long getWhole() {
        return whole;
    }

    public void setWhole(long whole) {
        this.whole = whole;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (int) (block ^ block >>> 32);
        result = prime * result + (int) (warn ^ warn >>> 32);
        result = prime * result + (int) (whole ^ whole >>> 32);
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
        CTConfig other = (CTConfig) obj;
        return block == other.block && warn == other.warn && whole == other.whole;
    }

    @Override
    public String toString() {
        return MessageFormat.format("CTConfig [name={0}, whole={1}, block={2}, warn={3}]", name, whole, block, warn);
    }

    public boolean isValid() {
        return isValid(whole, block, warn);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        configDataConverter = CTConfigDataConverter.getInstance();
    }

    public void setBlock(TimeUnit unit, long block) {
        this.block = CTTimeUtil.toMillis(unit, block);
    }

    public void setWarn(TimeUnit unit, long warn) {
        this.warn = CTTimeUtil.toMillis(unit, warn);
    }

    public void setWhole(TimeUnit unit, long whole) {
        this.whole = CTTimeUtil.toMillis(unit, whole);
    }

    @Override
    public void validateObject() throws InvalidObjectException {
        if (isNotValid()) {
            throw new InvalidObjectException("invalid configuration");
        }
    }

    public boolean isNotValid() {
        return isNotValid(whole, block, warn);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBytes(format());
    }

    public String format() {
        return configDataConverter.format(TimeUnit.MINUTES, whole, block, warn);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        long[] data = configDataConverter.parse(in.readLine());
        long whole1 = data[0];
        long block1 = data[1];
        long warn1 = data[2];
        if (isNotValid(whole1, block1, warn1)) {
            throw new IllegalArgumentException(MSG_NOT_VALID);
        }
        this.whole = whole1;
        this.block = block1;
        this.warn = warn1;
        name = name(TimeUnit.MINUTES);
    }
}
