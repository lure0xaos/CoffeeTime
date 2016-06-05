package gargoyle.ct;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public class CTConfig implements Serializable {
	private static final long serialVersionUID = -898699928298432564L;
	private long block;
	private String name;
	private long warn;
	private long whole;

	public CTConfig(final long whole, final long block, final long warn) {
		super();
		if (!this.isValid(whole, block, warn)) {
			throw new IllegalArgumentException();
		}
		this.whole = whole;
		this.block = block;
		this.warn = warn;
	}

	public CTConfig(final TimeUnit unit, final long whole, final long block, final long warn) {
		this(CTUtil.toMillis(unit, whole), CTUtil.toMillis(unit, block), CTUtil.toMillis(unit, warn));
		this.name = MessageFormat.format("{0,number,00}/{1,number,00}", Long.valueOf(this.getWhole(unit)),
				Long.valueOf(this.getBlock(unit)));
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
		return "CTConfig [whole=" + this.whole + ", block=" + this.block + ", warn=" + this.warn + "]";
	}
}
