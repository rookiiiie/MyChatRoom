package homeworkDay6;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 2020/9/14
 * message entity
 */
public class Message {
    private String msg;
    private String dateStr;
    private String ip;

    public Message(String msg, Date date, String ip) {
        this.msg = msg;
        this.ip = ip;
        this.dateStr = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(date);
    }

    @Override
    public String toString() {
        return String.format("%s at %s : %s", ip.substring(1, ip.length()), dateStr, msg);
    }
}
