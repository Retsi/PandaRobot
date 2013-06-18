
import java.io.IOException;

/**
 *
 * @author retsi
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Botti bot = new Botti("fi.quakenet.org", 6667);
        
        bot.connect();
        
    }
}
