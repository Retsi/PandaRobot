
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
                        title = replaceHTMLEntities(title);
                        return title;
                            
                    } else {
                        title = title + source.charAt(j);
                    }
                }
            }
        }
        title = replaceHTMLEntities(title);

        return title;
    }
    
    public String replaceHTMLEntities(String t){
        String title = t;
        title = title.replace("&quot;", "\"");
        title = title.replace("&apos;", "'");
        title = title.replace("&amp;", "&");
        title = title.replace("&lt;", "<");
        title = title.replace("&gt;", ">");
        title = title.replace("&#34;", "\"");
        title = title.replace("&#39;", "'");
        title = title.replace("&#38;", "&");
        title = title.replace("&#60", "<");
        title = title.replace("&#62;", ">");
        title = title.replace("&auml;", "ä");
        title = title.replace("&ouml;", "ö");
        title = title.replace("&Auml;", "Ä");
        title = title.replace("&Ouml;", "Ö");
        title = title.replace("&#228;", "ä");
        title = title.replace("&#246;", "ö");
        title = title.replace("&#196;", "Ä");
        title = title.replace("&#214;", "Ö");
        title = title.replace("&ndash;", "–");
        title = title.replace("&#8211;", "–");
        title = title.replace("&mdash;", "—");
        title = title.replace("&#8212;", "—");
	title = title.replace("&#039;", "'");
	title = title.replace("&#8217;", "´");
        title = title.replace("&#064;", "@");
	title = title.replace("&euro;", "€");
	title = title.replace("&laquo;", "«");
	title = title.replace("&raquo;", "»");
	title = title.replace("&#034;", "\"");
	title = title.replace("&bull;", "•");
	title = title.replace("&#8230;", "…");
	title = title.replace("&nbsp;", " ");
	title = title.replace("&#160;", " ");

        return title;
    }
}
