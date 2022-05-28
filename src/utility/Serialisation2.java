package utility;

import java.io.*;

public class Serialisation2 {
    public static byte[] serialize(Serializable value) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(ObjectOutputStream outputStream = new ObjectOutputStream(out)) {
            outputStream.writeObject(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return out.toByteArray();
    }

    public static <T extends Serializable> T deserialize(byte[] data) {
        try(ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
            return (T) new ObjectInputStream(bis).readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

