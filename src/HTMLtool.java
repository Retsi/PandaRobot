
import java.net.*;
import java.util.*;

/**
 *
 * @author retsi
 */
public class HTMLtool {

    public HTMLtool() {
    }

    public String getSource(String URL) throws MalformedURLException {

        String source = "";
        URL urli = new URL(URL);

        try {
            URLConnection connection = urli.openConnection();
            connection.connect();
            Scanner lukija = new Scanner(connection.getInputStream());

            while (lukija.hasNextLine()) {
                source += lukija.nextLine();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return source;
    }

    public String getPageTitle(String source) {

        if (!source.contains("<title") && !source.contains("<TITLE")) {
            return "";
        } else {
            for (int i = 0; i + 6 < source.length(); i++) {
                int j = i + 6;
                if (source.substring(i, j).equalsIgnoreCase("<title")) {

                    return titleHaku(source, i);
                }
            }
        }

        return "";
    }

    public String titleHaku(String source, int taginAlku) {

        String title = "";

        for (int i = taginAlku; i < source.length(); i++) {
            if (source.charAt(i) == '>') {
                for (int j = i+1; j+6 < source.length(); j++) {
                    if (source.substring(j, j + 6).equalsIgnoreCase("</titl")) {
                        if(title.charAt(0) == ' ')
                            return title.substring(1);
                        else
                            return title;
                    } else {
                        title = title + source.charAt(j);
                    }
                }
            }
        }

        return title;
    }
}
