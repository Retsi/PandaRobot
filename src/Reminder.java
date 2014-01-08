
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author retsi
 */
public class Reminder extends Thread {

    long milliseconds;
    String msg = "";
    BufferedWriter writer;
    String channel = "";
    String nick = "";

    public Reminder(BufferedWriter writer, int mins, String msg, String channel, String nick) {
        this.writer = writer;
        this.milliseconds = mins * 60000;
        this.msg = msg;
        this.channel = channel;
        this.nick = nick;
    }

    @Override
    public void run() {
        try {
            launch();
        } catch (IOException ex) {
            Logger.getLogger(Reminder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void launch() throws IOException {
        long currentTime = System.currentTimeMillis();
        long passedTime = 0;
        while (passedTime < milliseconds) {
            passedTime = System.currentTimeMillis() - currentTime;
        }
        writer.write("PRIVMSG " + channel + " :" + nick + ": Remember " + msg + "\n");
        writer.flush();
    }
}
