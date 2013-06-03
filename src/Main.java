
import java.io.IOException;
/**
 *
 * @author retsi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Botti bot = new Botti("fi.quakenet.org", 6667);
        
        bot.connect();
        
    }
}
