
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author retsi
 */
public class Main {

    public static void main(String[] args) throws IOException {
        
        ArrayList quakenet = new ArrayList<String>();
        quakenet.add("#testi123");
        Botti bot = new Botti("fi.quakenet.org", 6667, "PandaRobot123", "@cs27003181.pp.htv.fi", quakenet);
        
        bot.connect();
        
    }
}
