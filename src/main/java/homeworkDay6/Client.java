package homeworkDay6;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created on 2020/9/14
 * Client
 * when confront the situation:
 * wanna run simultaneously two program segments,such as 1: while(xx){xxx} and 2: while(xx){xxx},
 * you can utilize thread to run them at the same time ~
 * here, continuously read and write content from / to server need to use threads to wrestle with
 */
public class Client {
    private Socket socket;
    private int port;
    private String destinationIp;

    public Client(int port, String destinationIp) throws IOException {
        this.port = port;
        this.destinationIp = destinationIp;
        this.socket = new Socket(destinationIp, port);
    }

    public Client() throws IOException {
        System.out.println("Please input your destinationIp and port split with a whitespace:");
        String[] str = new Scanner(System.in).nextLine().split(" ");
        this.socket = new Socket(this.destinationIp = str[0], this.port = Integer.valueOf(str[1]));
    }

    public void enterChatRoom() throws IOException {
        //获取客户端中的流
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        //将字节流转换为字符流
        PrintWriter pw = new PrintWriter(out);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        new readThread(br).start();
        new writeThread(pw).start();
    }

    class readThread extends Thread {
        private BufferedReader br;

        public readThread(BufferedReader br) {
            this.br = br;
        }

        @Override
        public void run() {
            String msg = null;
            try {
                while ((msg = this.br.readLine()) != null) {
                    System.out.println(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class writeThread extends Thread {
        private PrintWriter pw;

        public writeThread(PrintWriter pw) {
            this.pw = pw;
        }

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            String str = null;
            while ((str = scanner.nextLine()) != null) {
                this.pw.println(str);
                this.pw.flush();
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        new Client(8080,"127.0.0.1").enterChatRoom();
//        new Client(8080,"47.105.84.63").enterChatRoom();
        new Client().enterChatRoom();
    }
}
