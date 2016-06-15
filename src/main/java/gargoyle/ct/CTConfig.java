package gargoyle.ct;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputValidation;
import java.io.ObjectOutput;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public class CTConfig implements Externalizable, ObjectInputValidation {
	private static final long serialVersionUID = -898699928298432564L;

	public static CTConfig parse(final String line) {
		return new CTConfig(line);
	}

	private long block;
	private String name;
	private long warn;
	private long whole;

	public CTConfig() {
		this(0, 0, 0);
	}

	public CTConfig(final long whole, final long block, final long warn) {
		super();
		if (!this.isValid(whole, block, warn)) {
			throw new IllegalArgumentException();
		}
		this.whole = whole;
		this.block = block;
		this.warn = warn;
		this.name(TimeUnit.MINUTES);
	}

	public CTConfig(final String line) {
		this.read(line);
	}

	public CTConfig(final TimeUnit unit, final long whole, final long block, final long warn) {
		this(CTUtil.toMillis(unit, whole), CTUtil.toMillis(unit, block), CTUtil.toMillis(unit, warn));
		this.name = this.name(unit);
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
		final CTConfig other = (CTConfig) obj;
		if (this.block != other.block) {
			return false;
		}
		if (this.warn != other.warn) {
			return false;
		}
		if (this.whole != other.whole) {
			return false;
		}
		return true;
	}

	public String format() {
		return CTConfigDataConverter.getInstance().format(TimeUnit.MINUTES,
				new long[] { this.whole, this.block, this.warn });
	}

	public long getBlock() {
		return this.block;
	}

	public long getBlock(final TimeUnit unit) {
		return CTUtil.fromMillis(unit, this.block);
	}

	public String getName() {
		return this.name;
	}

	public long getWarn() {
		return this.warn;
	}

	public long getWarn(final TimeUnit unit) {
		return CTUtil.fromMillis(unit, this.warn);
	}

	public long getWhole() {
		return this.whole;
	}

	public long getWhole(final TimeUnit unit) {
		return CTUtil.fromMillis(unit, this.whole);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (this.block ^ (this.block >>> 32));
		result = (prime * result) + (int) (this.warn ^ (this.warn >>> 32));
		result = (prime * result) + (int) (this.whole ^ (this.whole >>> 32));
		return result;
	}

	public boolean isValid() {
		return this.isValid(this.whole, this.block, this.warn);
	}

	protected boolean isValid(final long wholeMillis, final long blockMillis, final long warnMillis) {
		return (wholeMillis > blockMillis) && (blockMillis > warnMillis);
	}

	private String name(final TimeUnit unit) {
		return MessageFormat.format("{0,number,00}/{1,number,00}", Long.valueOf(this.getWhole(unit)),
				Long.valueOf(this.getBlock(unit)));
	}

	private void read(final String line) {
		final long[] data = CTConfigDataConverter.getInstance().parse(line);
		this.whole = data[0];
		this.block = data[1];
		this.warn = data[2];
		this.name = this.name(TimeUnit.MINUTES);
	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		this.read(in.readLine());
	}

	public void setBlock(final long block) {
		this.block = block;
	}

	public void setBlock(final TimeUnit unit, final long block) {
		this.block = CTUtil.toMillis(unit, block);
	}

	public void setWarn(final long warn) {
		this.warn = warn;
	}

	public void setWarn(final TimeUnit unit, final long warn) {
		this.warn = CTUtil.toMillis(unit, warn);
	}

	public void setWhole(final long whole) {
		this.whole = whole;
	}

	public void setWhole(final TimeUnit unit, final long whole) {
		this.whole = CTUtil.toMillis(unit, whole);
	}

	@Override
	public String toString() {
		return MessageFormat.format("CTConfig [name={0}, whole={1}, block={2}, warn={3}]", this.name,
				Long.valueOf(this.whole), Long.valueOf(this.block), Long.valueOf(this.warn));
	}

	@Override
	public void validateObject() throws InvalidObjectException {
		if (!this.isValid()) {
			throw new InvalidObjectException("invalid configuration");
		}
	}

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeBytes(this.format());
	}
}
