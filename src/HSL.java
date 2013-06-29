import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 *
 * @author lmsainio
 */
public class HSL {

    public HSL() {
    }

    public String getDirections(String locations) throws MalformedURLException {
        String directions = "";

        String start = parseStart(locations);
        start = start.replace(' ', '+');
        start = start.replace("ä", "%E4");
        start = start.replace("ö", "%F6");
        start = start.replace("å", "%E5");
        String finish = parseFinish(locations);
        finish = finish.replace(' ', '+');
        finish = finish.replace("ä", "%E4");
        finish = finish.replace("ö", "%F6");
        finish = finish.replace("å", "%E5");

        if (finish == "" || start == "") {
            return "";
        }
        String source = getSource(start, finish);
        if(source.equals(""))
            return "";
        if (source.contains("Ei tuloksia")) {
            return "Ei tuloksia";
        }
        if (source.contains("L&ouml;ytyi useita")) {
            return "Löytyi useita";
        }
        if (source.contains("Anna osoitenumero")) {
            return "Anna osoitenumero";
        }
        if (source.contains("Hakemaasi osoitenumeroa ei ")) {
            return "Hakemaasi osoitenumeroa ei ole";
        }


        String secondLastLineFromSource = parseSecondLastLineFromSource(source);
        if (secondLastLineFromSource.equals("")) {
            return "";
        }
        directions = parseDirections(secondLastLineFromSource);

        return directions;
    }

    public String parseStart(String locations) {
        String start = "";
        for (int i = 0; i < locations.length(); i++) {
            if (locations.charAt(i) != '.') {
                start += locations.charAt(i);
            } else {
                return start;
            }
        }
        return "";
    }

    public String parseFinish(String locations) {
        String finish = "";
        for (int i = 0; i < locations.length(); i++) {
            if (locations.charAt(i) == '.') {
                for (int j = i + 1; j < locations.length(); j++) {
                    finish += locations.charAt(j);
                }
                while (finish.charAt(0) == ' ') {
                    finish = finish.substring(1);
                }
                return finish;
            }
        }
        return "";
    }

    public String getSource(String start, String finish) throws MalformedURLException {
        String source = "";
        URL url = new URL("http://aikataulut.hsl.fi/reittiopas-pda/fi/?test=1&keya=" + start + "&keyb=" + finish);

        try {
            URLConnection connection = url.openConnection();
            connection.connect();
            Scanner reader = new Scanner(connection.getInputStream());

            while (reader.hasNextLine()) {
                source += reader.nextLine() + "\n";
            }

        } catch (Exception e) {
            System.out.println(e);

        }
        return source;
    }

    public String parseSecondLastLineFromSource(String pageSource) {
        String source = pageSource;
        int numberoflines = 0;
        Scanner scanner = new Scanner(pageSource);
        String secondLastLine = "";
        String lastLine = "";

        while (scanner.hasNextLine()) {
            if (!scanner.nextLine().equals(" ")) {
                numberoflines++;
                lastLine = scanner.nextLine();
                if (scanner.hasNextLine()) {
                    secondLastLine = scanner.nextLine();
                    numberoflines++;
                }
            }
        }
        
        if (numberoflines % 2 == 0) {
            return lastLine;
        } else {
            return secondLastLine;
        }
    }

    public String parseDirections(String secondLastLine) {
        String directions = "";
        Boolean copy = true;

        for (int i = 0; i < secondLastLine.length(); i++) {
            if (secondLastLine.charAt(i) == '<') {
                copy = false;
            }
            if (copy) {
                directions += secondLastLine.charAt(i);
            }
            if (secondLastLine.charAt(i) == '>') {
                copy = true;
                directions += "\n";
            }
        }
        directions = directions.replace("&auml;", "ä");
        directions = directions.replace("&ouml;", "ö");


        String first = getLines(shorter(directions, " 1. "));
        String second = getLines(shorter(directions, " 2. "));
        String third = getLines(shorter(directions, " 3. "));

        return "1. " + first + " // 2. " + second + " // 3. " + third;
    }

    public String shorter(String msg, String what) {
        String result = "";
        for (int i = 0; i + 4 < msg.length(); i++) {
            if (msg.substring(i, i + 4).equalsIgnoreCase(what)) {
                result = msg.substring(i);
            }
        }

        return result;
    }

    public String getLines(String directions) {
        String what = "";
        String where = "";
        String when = "";

        Scanner reader = new Scanner(directions);

        int i = 0;
            while (reader.hasNextLine()) {
                if (i == 22) {
                    what = reader.nextLine();
                }
                if (i == 23) {
                    where = reader.nextLine();
                }
                if (i == 24) {
                    when = reader.nextLine();
                }
                reader.nextLine();
                i++;
            }
            if(what.equals("") || where.equals(""))
                return "walk";

        return what + ", " + where + ", " + when;
    }
}