package homeworkDay6;

import java.io.IOException;

/**
 * Created on 2020/9/14
 */
public class test2 {
    public static void main(String[] args) throws IOException {
        new Client(8080, "127.0.0.1").enterChatRoom();
//        new Client(8080,"47.105.84.63").enterChatRoom();
    }
}
