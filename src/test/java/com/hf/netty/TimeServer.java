package com.hf.netty;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by krt on 2017/8/5.
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8099;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println ("The time server is start in port : " + port);
            Socket socket = null;
            while(true){
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                System.out.print("");
                serverSocket.close();
                serverSocket=null;
            }
        }

    }


}
class TimeServerHandler implements Runnable {

    public TimeServerHandler(Socket socket) {

    }

    @Override
    public void run() {

    }
}
