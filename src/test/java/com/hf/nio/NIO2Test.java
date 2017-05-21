package com.hf.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by 520 on 2017/1/2.
 */
public class NIO2Test {

    @Test
    public void test1() throws IOException {
        Path path = Paths.get("D:/configuration.yml");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        fileChannel.read(byteBuffer, 0, "private", new CompletionHandler<Integer, String>() {
            @Override
            public void completed(Integer result, String attachment) {
                System.out.print(result);
            }

            @Override
            public void failed(Throwable exc, String attachment) {
                System.out.print(attachment);
            }
        });
    }
}
