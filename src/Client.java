import utility.Request;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static utility.Serialisation2.deserialize;
import static utility.Serialisation2.serialize;

public class Client {

    private static SocketChannel channel;
    private final static String hostName = "localhost";
    private static SocketAddress inetSocketAddress;

    private static ByteBuffer[] bufferOut;
    private static ByteBuffer bufferIn;
    public static void connect(int port) {
        try {
            inetSocketAddress = new InetSocketAddress(hostName, port);
            channel = SocketChannel.open(inetSocketAddress);
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
            channel.write(ByteBuffer.wrap(serialize(requestData)));
            for (int i = 0; i < size; i++) {
                channel.write(bufferOut[i]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int[] split(byte[] buffer) {
        int byteBufferSize = 1024;
        int size = (int) Math.ceil((double) buffer.length / byteBufferSize);
//        System.out.println(size);
        int stop = byteBufferSize;
        bufferOut = new ByteBuffer[size];
        for (int i = 0; i < size; i++){
            if (i == size-1 && buffer.length % byteBufferSize != 0) stop = (buffer.length % byteBufferSize);
            byte[] temp = new byte[stop];
            System.arraycopy(buffer, i * byteBufferSize, temp, 0, stop);
            bufferOut[i] = ByteBuffer.wrap(temp);
        }
        return new int[] {byteBufferSize,size};
    }

    public static void getAnswer(int[] bufferData){
        bufferIn = ByteBuffer.allocate(bufferData[0]);
        int size = bufferData[1];
        byte[] input = new byte[0];
        System.out.println(size);
        try {
            for (int i=0; i < size; i++) {
                channel.read(bufferIn);
//                System.out.println("прочитали канал");
                input = combineArray(input, bufferIn.array());
                System.out.println(input.length);
                bufferIn.clear();
            }
                System.out.println(new String(input));
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int[] getAnswerData(){
        int[] data;
        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
        try {
            channel.read(byteBuffer);
            data = deserialize(byteBuffer.array());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
    private static byte[] combineArray(byte[] arr1, byte[] arr2){
        byte[] arr = new byte[arr1.length+arr2.length];
        System.arraycopy(arr1, 0, arr, 0, arr1.length);
        System.arraycopy(arr2, 0, arr, arr1.length, arr2.length);
        return arr;
    }

}