package com.hf.nio;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by 520 on 2017/1/9.
 */
public class FileChannelTest {
    
    @Test
    public void testTransferFrom() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("D:/configuration.yml","rw");
        FileChannel fromFileChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("D:/configuration.yml.target","rw");
        FileChannel toFileChannel = toFile.getChannel();

        long position = 0;
        long count = fromFileChannel.size();
        long l = toFileChannel.transferFrom(fromFileChannel,position,count);
    }
}
