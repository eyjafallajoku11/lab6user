import utility.Request;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static utility.Serialisation2.serialize;

public class Client {

    private static SocketChannel socketChannel;
    private final static String hostName = "localhost";
    private static SocketAddress inetSocketAddress;
    public static void connect(int port) {
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(hostName, port);
            socketChannel = SocketChannel.open(inetSocketAddress);
            System.out.println(String.format("Подключение к удаленному адресу %s по порту %d", hostName, port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendRequest(Request request) {
        try {
            byte[] buf;
            buf = serialize(request);
//            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
//            byteBuffer.clear();
            ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
//            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }
            System.out.println(byteBuffer);
//            System.out.println((Request)deserialize(byteBuffer.array()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}