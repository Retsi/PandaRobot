
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 *
 * @author retsi
 */
public class Weather {

    public Weather() {
    }

    public String getForecast(String city) throws MalformedURLException {
        
        String town = city;
        
        town = town.replace(" ", "%20");

        String forecast = "";

        String woeid = getWoeID(town);

        if (!woeid.equals("")) {
            String RSSsouce = getRSS(woeid);

            if (!RSSsouce.equals("")) {
                forecast = parseForecast(RSSsouce);
            }
        }

        return forecast;
    }

    public String getWoeID(String city) throws MalformedURLException {

        String pagesource = "";
        String woeid = "";

        URL url = new URL("http://woeid.rosselliot.co.nz/lookup/" + city);

        try {
            URLConnection connection = url.openConnection();
            connection.connect();
            Scanner reader = new Scanner(connection.getInputStream());
            while (reader.hasNextLine()) {
                pagesource += reader.nextLine();
            }
            if (pagesource.contains("data-woeid")) {
                woeid = parseWoeID(pagesource);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return woeid;
    }

    public String parseWoeID(String pageSource) {
        String woeid = "";

        for (int i = 0; i + 12 < pageSource.length(); i++) {
            if (pageSource.substring(i, i + 12).equals("data-woeid=\"")) {
                for (int j = i + 12; j < pageSource.length(); j++) {
                    if (pageSource.charAt(j) != '"') {
                        woeid += pageSource.charAt(j);
                    } else {
                        return woeid;
                    }
                }
            }
        }

        return woeid;
    }

    public String getRSS(String woeid) throws MalformedURLException {

        String source = "";
        URL urli = new URL("http://weather.yahooapis.com/forecastrss?w=" + woeid + "&u=c");

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

    public String parseForecast(String source) {
        String forecast = "";

        for (int i = 0; i + 32 < source.length(); i++) {
            if (source.substring(i, i + 32).equals("<b>Current Conditions:</b><br />")) {
                for (int j = i + 32; j < source.length(); j++) {
                    if (source.charAt(j) == '<') {
                        return "Current conditions: " + forecast;
                    }
                    forecast += source.charAt(j);
                }
            }
        }

        return forecast;
    }
}
