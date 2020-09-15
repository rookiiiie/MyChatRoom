package homeworkDay6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Created on 2020/9/14
 * server
 */
public class Server {
    private ServerSocket serverSocket;
    private int port;
    private ArrayList<Socket> clients = new ArrayList<>();  //store currently connected clients

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    public Server() throws IOException {
        System.out.println("Please input the port you wanna open");
        this.serverSocket = new ServerSocket(Integer.parseInt(new Scanner(System.in).nextLine().trim()));
    }

    public void OpenServer() throws IOException {
        Socket client = null;
        while ((client = serverSocket.accept()) != null) {
            clients.add(client);
//            System.out.println("client["+client.getInetAddress()+"] add ok");
            new HandlerThread(client).start();
        }
    }

    class HandlerThread extends Thread {
        private Socket socket;

        public HandlerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("in Server receive " + socket.getInetAddress());
            OutputStream out = null;
            try {
                out = this.socket.getOutputStream();
                //将字节流转换为过滤流
                PrintWriter pw = new PrintWriter(out);
                pw.println("welcome to the ChatRoom! your Address is " + this.socket.getInetAddress().toString().substring(1));
                pw.flush();
                while (!this.socket.isClosed()) {
                    //通过流来获取客户端发送的信息
                    BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                    String msg = null;
                    try {
                        msg = br.readLine();
                    } catch (Exception e) {
                        this.socket.close();
                    }
                    if (msg != null) {
                        for (Socket socket : clients) {
                            //返回信息给每一个客户端
                            pw = new PrintWriter(socket.getOutputStream());
                            String msg2 = new Message(msg, new Date(), this.socket.getInetAddress().toString()).toString();
                            pw.println(msg2);
                            System.out.println(msg2);
                            pw.flush();
                        }
                    }
                }
                System.out.println("client[" + socket.getInetAddress() + "] go out!");
                clients.remove(this.socket);
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }

    //    public Boolean isServerClose(Socket socket){
//        try{
//            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
//            return false;
//        }catch(Exception se){
//            return true;
//        }
//    }
    public static void main(String[] args) throws IOException {
//        new Server(8080).OpenServer();
        new Server().OpenServer();
    }
}
