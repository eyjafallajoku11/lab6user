package utility;

import utility.Request;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Objects;

import static utility.Serialisation2.deserialize;
import static utility.Serialisation2.serialize;

public class Client {
    private static SocketChannel channel;
    private final static String hostName = "localhost";
    private static SocketAddress inetSocketAddress;
    private static ByteBuffer[] bufferOut;
    private static ByteBuffer bufferIn;
    private static int port;
    public static void connect(int portNumber) {
        try {
            System.out.println("попытка подключения");
            port = portNumber;
            inetSocketAddress = new InetSocketAddress(hostName, port);
            channel = SocketChannel.open(inetSocketAddress);
            channel.configureBlocking(false);
            System.out.println(String.format("Подключено к удаленному адресу %s по порту %d", hostName, port));
        } catch (IOException e) {
            try {
                Thread.sleep(333);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            reconnect();
        }
    }

    public static void reconnect(){
        System.out.println("попытка подключения");
        try {
            channel = SocketChannel.open(inetSocketAddress);
            System.out.println(String.format("Подключено к удаленному адресу %s по порту %d", hostName, port));
        } catch (IOException e) {
            try {
                Thread.sleep(333);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            reconnect();
        }

    }
    public static void sendRequest(Request request) {
//        System.out.println("пытаемся");
        try {
            byte[] buf;
            buf = serialize(request);
            int[] requestData = split(buf);
            int size = requestData[1];
            channel.write(ByteBuffer.wrap(serialize(requestData)));
//            System.out.println("данные отправлены");
            for (int i = 0; i < size; i++) {
                Thread.sleep(5);
                channel.write(bufferOut[i]);
                bufferOut[i].clear();
//                System.out.println("пакет отправлен");
            }
        } catch (IOException e) {
            try {
                Thread.sleep(333);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            reconnect();
//            System.out.println("приконектились");
            sendRequest(request);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static int[] split(byte[] buffer) {
        int byteBufferSize = 1024;
        int size = (int) Math.ceil((double) buffer.length / byteBufferSize);
        int stop = byteBufferSize;
        bufferOut = new ByteBuffer[size];
        for (int i = 0; i < size; i++){
            if (i == size-1 && buffer.length % byteBufferSize != 0) stop = (buffer.length % byteBufferSize);
            byte[] temp = new byte[stop];
            System.arraycopy(buffer, i * byteBufferSize, temp, 0, stop);
            bufferOut[i] = ByteBuffer.wrap(temp);
        }
        return (new int[] {byteBufferSize,size});
    }
    public static void getAnswer(int[] bufferData){
//        System.out.println("answer");
        bufferIn = ByteBuffer.allocate(bufferData[0]);
        int size = bufferData[1];
        byte[] input = new byte[0];
        try {
            for (int i=0; i < size; i++) {
                bufferIn.clear();
                int length = 0;
                while (length == 0) {
                    length = channel.read(bufferIn);
                }
                input = combineArray(input, bufferIn.array(), length);
            }
            System.out.println(new String(input));
        } catch (Exception e) {
            try {
                Thread.sleep(333);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            reconnect();
            getAnswer(bufferData);
//            throw new RuntimeException(e);

        }
    }
    public static int[] getAnswerData(){
        System.out.println("хочу данные прочитать");
        int[] data = null;
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        try {
            byteBuffer.clear();
            int length = 0;
            while (length==0) {
                length = channel.read(byteBuffer);
            }
            data = deserialize(byteBuffer.array());
        }  catch (Exception e) {
            try {
                Thread.sleep(333);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            reconnect();
            getAnswerData();
//            throw new RuntimeException(e);
        }
        return data;
    }
    private static byte[] combineArray(byte[] arr1, byte[] arr2, int length){
        byte[] arr = new byte[arr1.length+arr2.length];
        System.arraycopy(arr1, 0, arr, 0, arr1.length);
        System.arraycopy(arr2, 0, arr, arr1.length, length);
        return arr;
    }

    public static void closeConnection(){
        try {
            channel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}