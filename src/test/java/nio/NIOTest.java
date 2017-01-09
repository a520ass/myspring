package nio;

import org.junit.Test;
import org.springframework.util.StreamUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by 520 on 2017/1/1.
 */
public class NIOTest {

    @Test
    public void channelTest () throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("D:/configuration.yml", "rw");
        FileChannel inChannel = accessFile.getChannel();
        //create buffer with capacity of 48 bytes

        ByteBuffer buffer = ByteBuffer.allocate(StreamUtils.BUFFER_SIZE);
        int read = inChannel.read(buffer);
        while (read!=-1){
            System.out.println("Read " + read);
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
            read= inChannel.read(buffer);
        }
        accessFile.close();

    }

    @Test
    public void selectorTest() throws IOException {
        //通过调用Selector.open()方法创建一个Selector
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        /**
         * 某个channel成功连接到另一个服务器称为“连接就绪”。一个server socket channel准备好接收新进入的连接称为“接收就绪”。一个有数据可读的通道可以说是“读就绪”。等待写数据的通道可以说是“写就绪”。
         * 如果你对不止一种事件感兴趣，那么可以用“位或”操作符将常量连接起来，如下：
         *int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;在下面还会继续提到interest集合
         */
        socketChannel.register(selector, SelectionKey.OP_READ);


    }
}
