package gargoyle.ct.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public final class CTSerializationUtil {
    private CTSerializationUtil() {
    }

    public static <T> T pipe(T orig) throws IOException {
        byte[] serialized = serialize(orig);
        // System.out.println(new String(serialized));
        return deserialize(serialized);
    }

    @SuppressWarnings({"unchecked", "MethodCanBeVariableArityMethod"})
    public static <T> T deserialize(byte[] bytes) throws IOException {
        try (final ObjectInput ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            try {
                return (T) ois.readObject();
            } catch (ClassNotFoundException ex) {
                throw new IOException(ex);
            }
        }
    }

    public static <T> byte[] serialize(T o) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             final ObjectOutput oos = new ObjectOutputStream(out)) {
            oos.writeObject(o);
            oos.flush();
            return out.toByteArray();
        }
    }
}
