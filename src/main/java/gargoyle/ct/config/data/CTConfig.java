package gargoyle.ct.config.data;

import gargoyle.ct.util.CTTimeUtil;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public class CTConfig implements Serializable, ObjectInputValidation {
    private static final String FORMAT_NAME = "{0,number,00}/{1,number,00}";
    private static final String MSG_NOT_VALID = "convert is not valid";
    private static final String STR_INVALID = "invalid";
    private static final long serialVersionUID = -898699928298432564L;
    private long block;
    private String name;
    private long warn;
    private long whole;

    public CTConfig() {
        init(0, 0, 0, STR_INVALID);
    }

    private void init(long whole, long block, long warn, String name) {
        if (!isValid(whole, block, warn)) {
            throw new IllegalArgumentException(MSG_NOT_VALID);
        }
        this.whole = whole;
        this.block = block;
        this.warn = warn;
        this.name = name;
    }

    private boolean isValid(long wholeMillis, long blockMillis, long warnMillis) {
        return wholeMillis > blockMillis && blockMillis > warnMillis;
    }

    public CTConfig(TimeUnit unit, long whole, long block, long warn) {
        this(CTTimeUtil.toMillis(unit, whole), CTTimeUtil.toMillis(unit, block), CTTimeUtil.toMillis(unit, warn));
        name = name(unit, whole, block);
    }

    public CTConfig(long whole, long block, long warn) {
        init(whole, block, warn);
    }

    private void init(long whole, long block, long warn) {
        init(whole, block, warn, name(TimeUnit.MINUTES, whole, block));
    }

    private String name(TimeUnit unit, long whole, long block) {
        return MessageFormat.format(FORMAT_NAME, CTTimeUtil.fromMillis(unit, whole), CTTimeUtil.fromMillis(unit, block));
    }

    public long getBlock(TimeUnit unit) {
        return CTTimeUtil.fromMillis(unit, block);
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

    public long getWhole(TimeUnit unit) {
        return CTTimeUtil.fromMillis(unit, whole);
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

    public String name(TimeUnit unit) {
        return name(unit, whole, block);
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
        return !isValid(whole, block, warn);
    }
}
