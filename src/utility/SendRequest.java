package utility;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;

import static java.lang.System.out;
import static utility.Serialisation.convertToBytes;

public class SendRequest {
    public static void execute(Request request, int port){
        try {
            if (!Objects.isNull(request)) {
            String hostName = "localhost";
            SocketChannel socketChannel = null;
            InetSocketAddress inetSocketAddress = new InetSocketAddress(hostName, port);
            socketChannel = SocketChannel.open(inetSocketAddress);
            socketChannel.configureBlocking(false);
            System.out.println(String.format("Подключение к удаленному адресу %s по порту %d", hostName, port));

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);

            byte[] buf;
            try {
                buf = convertToBytes(request);
//            buf = serialize(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byteBuffer.put(buf);
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            }
        } catch (ConnectException e) {
            out.println("сервер недоступен");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
