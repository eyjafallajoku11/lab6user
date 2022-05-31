import utility.Request;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static utility.Serialisation2.serialize;

public class Client {

    private static int byteBufferSize = 1024;
    private static SocketChannel socketChannel;
    private final static String hostName = "localhost";
    private static SocketAddress inetSocketAddress;

    private static ByteBuffer[] bufferArray;
    public static void connect(int port) {
        try {
            inetSocketAddress = new InetSocketAddress(hostName, port);
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
            int[] requestData = split(buf);
            int size = requestData[1];
            socketChannel.write(ByteBuffer.wrap(serialize(requestData)));
            for (int i = 0; i < size; i++) {
                socketChannel.write(bufferArray[i]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int[] split(byte[] buffer) {
        int size = (int) Math.ceil((double) buffer.length / byteBufferSize);
//        System.out.println(size);
        int stop = byteBufferSize;
        bufferArray = new ByteBuffer[size];
        for (int i = 0; i < size; i++){
            if (i == size-1 && buffer.length % byteBufferSize!= 0) stop = (buffer.length % byteBufferSize);
            byte[] temp = new byte[stop];
            System.arraycopy(buffer, i * byteBufferSize, temp, 0, stop);
            bufferArray[i] = ByteBuffer.wrap(temp);
        }
//        System.out.println(size);
//        System.out.println(stop);
        return new int[] {byteBufferSize,size,stop};
    }
}