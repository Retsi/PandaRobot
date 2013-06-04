
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
            Scanner reader = new Scanner(connection.getInputStream());

            while (reader.hasNextLine()) {
                source += reader.nextLine();
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

                    return titleSearch(source, i);
                }
            }
        }

        return "";
    }

    public String titleSearch(String source, int tagIndex) {

        String title = "";

        for (int i = tagIndex; i < source.length(); i++) {
            if (source.charAt(i) == '>') {
                for (int j = i+1; j+6 < source.length(); j++) {
                    if (source.substring(j, j + 6).equalsIgnoreCase("</titl")) {
                        while(title.charAt(0) == ' ' && title.length()>1)
                            title = title.substring(1);
                        while(title.charAt(title.length()-1) == ' ' && title.length()>1)
                            title = title.substring(0, title.length()-1);
                        if(title.equals(" "))
                            title = "";
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
