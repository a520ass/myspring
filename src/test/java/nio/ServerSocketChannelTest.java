package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by 520 on 2017/1/9.
 */
public class ServerSocketChannelTest {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(true);//true 阻塞模式 false 非阻塞模式

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            /**
             * ServerSocketChannel可以设置成非阻塞模式。在非阻塞模式下，accept() 方法会立刻返回，如果还没有新进来的连接,返回的将是null。 因此，需要检查返回的SocketChannel是否是null
             */
            if(socketChannel != null){
                //do something with socketChannel...
                ByteBuffer buffer = ByteBuffer.allocate(48);
                int read = socketChannel.read(buffer);
                while (read!=-1){
                    System.out.println("读取的字节数： " + read);
                    //flip方法将Buffer从写模式切换到读模式
                    buffer.flip();
                    while (buffer.hasRemaining()){
                        System.out.print((char) buffer.get());
                    }
                    /**
                     * 有两种方式能清空缓冲区：调用clear()或compact()方法。
                     * clear()方法会清空整个缓冲区。
                     * compact()方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面
                     */
                    buffer.clear();
                    read= socketChannel.read(buffer);
                }
            }

        }
    }
}
