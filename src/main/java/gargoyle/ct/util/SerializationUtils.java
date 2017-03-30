package gargoyle.ct.util;

import java.io.*;

public final class SerializationUtils {

    private SerializationUtils() {
    }

    @SuppressWarnings({"unchecked", "MethodCanBeVariableArityMethod"})
    public static <T> T deserialize(byte[] bytes) throws ClassNotFoundException, IOException {
        try (final ObjectInput ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (T) ois.readObject();
        }
    }

    public static <T> T pipe(T orig) throws ClassNotFoundException, IOException {
        byte[] serialized = serialize(orig);
        // System.out.println(new String(serialized));
        return deserialize(serialized);
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
