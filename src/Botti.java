
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author retsi
 */
public class Botti {

    private Socket socket;
    private int portti;
    private String serveri;
    private BufferedReader lukija;
    private BufferedWriter kirjoittaja;
    private String line;
    
    private Parser parser;
    private HTMLtool htmltool;
    private UserModes usermodes;
    private Weather weather;
    
    private String masteraddy = "";

    public Botti(String server, int port) {
        this.portti = port;
        this.serveri = server;
        this.parser = new Parser();
        this.htmltool = new HTMLtool();
        this.usermodes = new UserModes();
        this.weather = new Weather();
        
    }

    public void connect() throws IOException {

        try {

            socket = new Socket(serveri, portti);
            lukija = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            kirjoittaja = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            masteraddy = "@cs27003181.pp.htv.fi";

            kirjoittaja.write("NICK PandaRobot\n");
            kirjoittaja.write("USER PandaRobot 0 * :PandaRobot\n");
            kirjoittaja.flush();

            System.out.println("Status ok");

            while (true) {
                while ((line = lukija.readLine()) != null) {
                    if (parser.nWordFromMsg(parser.protocolMsg(line), 2).contains("001")) {
                        kirjoittaja.write("JOIN #supadeltat\n");
                        System.out.println(line);
                        kirjoittaja.flush();
                    } 
                    
                    else if (parser.nWordFromMsg(parser.protocolMsg(line), 1).equalsIgnoreCase("PING")) {
                        kirjoittaja.write("PONG :" + parser.msg(line) + "\n");
                        System.out.println(line);
                        System.out.print("Vastaus: PONG :" + parser.msg(line) + "\n");
                        kirjoittaja.flush();
                    } 
                    
                    else if (parser.nWordFromMsg(parser.msg(line), 1).equals("!huuda")) {
                        kirjoittaja.write("PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :" + parser.everythingElseExceptFirstWordFromMsg(parser.msg(line)) + "\n");
                        System.out.println(line);
                        System.out.print("Vastaus: PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :" + parser.everythingElseExceptFirstWordFromMsg(parser.msg(line)) + "\n");
                        kirjoittaja.flush();
                    }
                    
                    else if (parser.nWordFromMsg(parser.msg(line), 1).equals("!raw") && parser.protocolMsg(line).contains(masteraddy)) {
                        kirjoittaja.write(parser.everythingElseExceptFirstWordFromMsg(parser.msg(line))+"\n");
                        System.out.println(line);
                        System.out.print(parser.everythingElseExceptFirstWordFromMsg(parser.msg(line))+"\n");
                        kirjoittaja.flush();
                    }
                    
                    else if (parser.nWordFromMsg(parser.msg(line), 1).equals("!addop") && parser.protocolMsg(line).contains(masteraddy)) {
                        usermodes.addUserToOpList(parser.nWordFromMsg(parser.msg(line), 2), parser.channelProt(parser.protocolMsg(line)));
                        kirjoittaja.write("PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :added\n");
                        System.out.println(line);
                        System.out.print("Vastaus: PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :added\n");
                        kirjoittaja.flush();
                    }
                    
                    else if (parser.nWordFromMsg(parser.msg(line), 1).equals("!addvoice") && parser.protocolMsg(line).contains(masteraddy)) {
                        usermodes.addUserToVoiceList(parser.nWordFromMsg(parser.msg(line), 2), parser.channelProt(parser.protocolMsg(line)));
                        kirjoittaja.write("PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :added\n");
                        System.out.println(line);
                        System.out.print("Vastaus: PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :added\n");
                        kirjoittaja.flush();
                    }
                    
                    else if (parser.nWordFromMsg(parser.msg(line), 1).equals("!removeop") && parser.protocolMsg(line).contains(masteraddy)) {
                        usermodes.removeUserFromOpList(parser.nWordFromMsg(parser.msg(line), 2), parser.channelProt(parser.protocolMsg(line)));
                        kirjoittaja.write("PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :removed\n");
                        System.out.println(line);
                        System.out.print("Vastaus: PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :removed\n");
                        kirjoittaja.flush();
                    }
                    
                    else if (parser.nWordFromMsg(parser.msg(line), 1).equals("!removevoice") && parser.protocolMsg(line).contains(masteraddy)) {
                        usermodes.removeUserFromVoiceList(parser.nWordFromMsg(parser.msg(line), 2), parser.channelProt(parser.protocolMsg(line)));
                        kirjoittaja.write("PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :removed\n");
                        System.out.println(line);
                        System.out.print("Vastaus: PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :removed\n");
                        kirjoittaja.flush();
                    }
                    else if (parser.nWordFromMsg(parser.msg(line), 1).equals("!w")){
                        kirjoittaja.write("PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :" + weather.getForecast(parser.everythingElseExceptFirstWordFromMsg(parser.msg(line))) + "\n");
                        System.out.println(line);
                        System.out.print("Vastaus: PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :" + weather.getForecast(parser.nWordFromMsg(parser.msg(line), 2)) + "\n");
                        kirjoittaja.flush();
                    }
                    
                    else if (parser.msg(line).contains("https://") || parser.msg(line).contains("http://")) {
                        String title = htmltool.getPageTitle(htmltool.getSource(parser.url(parser.msg(line))));
                        if (!title.equals("")) {
                            kirjoittaja.write("PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :Page title: [ " + title + " ]\n");
                            System.out.println(line);
                            System.out.print("Vastaus: PRIVMSG " + parser.channelProt(parser.protocolMsg(line)) + " :" + title + "\n");
                            kirjoittaja.flush();
                        }
                    }
                    
                    else if(parser.protocolMsg(line).contains("JOIN") && usermodes.getOpList().containsKey(parser.getAddy(parser.protocolMsg(line)))){
                        System.out.println(line);
                        if(usermodes.getUsersOpChannels(parser.getAddy(parser.protocolMsg(line))).contains(parser.channelProt(parser.channelProt(line)))){
                            kirjoittaja.write("MODE " + parser.channelProt(parser.protocolMsg(line))+ " +o "+ parser.nickProtMsg(parser.protocolMsg(line)) + "\n");
                            System.out.print("Vastaus: MODE " + parser.channelProt(parser.protocolMsg(line))+ " +o "+ parser.nickProtMsg(parser.protocolMsg(line)) + "\n");
                            kirjoittaja.flush();
                        }
                    }
                    
                    else if(parser.protocolMsg(line).contains("JOIN") && usermodes.getVoiceList().containsKey(parser.getAddy(parser.protocolMsg(line)))){
                        System.out.println(line);
                        if(usermodes.getUsersVoiceChannels(parser.getAddy(parser.protocolMsg(line))).contains(parser.channelProt(parser.channelProt(line)))){
                            kirjoittaja.write("MODE " + parser.channelProt(parser.protocolMsg(line))+ " +v "+ parser.nickProtMsg(parser.protocolMsg(line)) + "\n");
                            System.out.print("Vastaus: MODE " + parser.channelProt(parser.protocolMsg(line))+ " +v "+ parser.nickProtMsg(parser.protocolMsg(line)) + "\n");
                            kirjoittaja.flush();
                        }
                    }
                    
                    else {
                        System.out.println(line);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("error " + e);

        }

    }
}
