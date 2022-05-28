import utility.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static utility.Serialisation.convertToBytes;
import static utility.Serialisation3.deserialize3;

public class MainUser{
    public static void main(String[] args) throws IOException {
        System.out.println("client online");
//        int port = 1567;
//        Socket sock;
//        OutputStream os;
//        InputStream is;
//        InetAddress host;
//        host = InetAddress.getByName("localhost");
//        sock = new Socket(host,port);
//        os = sock.getOutputStream();
//
//
//        String str = "1HelloWorld!2HelloWorld!3HelloWorld!4HelloWorld!5HelloWorld!6HelloWorld!7HelloWorld!8HelloWorld!9HelloWorld!10HelloWorld!11HelloWorld!12HelloWorld!13HelloWorld!14HelloWorld!15HelloWorld!1HelloWorld!2HelloWorld!3HelloWorld!4HelloWorld!5HelloWorld!6HelloWorld!7HelloWorld!8HelloWorld!9HelloWorld!10HelloWorld!11HelloWorld!12HelloWorld!13HelloWorld!14HelloWorld!15HelloWorld!";
//        byte[] buf = str.getBytes();
//        int len = buf.length;
//        int len = buf.length;
//        os.write(buf,0,len);
//        System.out.println(len);
        int port = 1567;
        String hostName = "localhost";

        SocketChannel socketChannel;
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(hostName, port);
            socketChannel = SocketChannel.open(inetSocketAddress);
            socketChannel.configureBlocking(false);

            System.out.println(String.format("Подключение к удаленному адресу %s по порту %d", hostName, port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
//        String str = "1HelloWorld!2HelloWorld!3HelloWorld!4HelloWorld!5HelloWorld!6HelloWorld!7HelloWorld!8HelloWorld!9HelloWorld!10HelloWorld!11HelloWorld!12HelloWorld!13HelloWorld!14HelloWorld!15HelloWorld!1HelloWorld!2HelloWorld!3HelloWorld!4HelloWorld!5HelloWorld!6HelloWorld!7HelloWorld!8HelloWorld!9HelloWorld!10HelloWorld!11HelloWorld!12HelloWorld!13HelloWorld!14HelloWorld!15HelloWorld!";
//        byte[] buf = str.getBytes();
        Request pac = new Request("add");
        String[] creator = new String[12];
        pac.setCreatorArgument(creator);
        System.out.println(pac);
        byte[] buf;
        try {
            buf = convertToBytes(pac);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byteBuffer.put(buf);
        byteBuffer.flip();
        oos.writeObject(buf);
        byte[] data = bos.toByteArray();


        socketChannel.write(byteBuffer);
        Request out;
        try {
            out = deserialize3(buf);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(out);

//        System.out.println(new String(buf));
    }
}
