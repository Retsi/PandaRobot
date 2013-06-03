
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
    
    public Botti(String server, int port){
        this.portti = port;
        this.serveri = server;
        this.parser = new Parser();
    }
    
    public void connect() throws IOException{
        
        try{
            
            socket = new Socket(serveri, portti);
            lukija =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
            kirjoittaja = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            kirjoittaja.write("NICK PandaRobot\n");
            kirjoittaja.write("USER PandaRobot 0 * :PandaRobot\n");
            kirjoittaja.flush();
            
            System.out.println("Status ok");
            
            while(true){
                while((line = lukija.readLine()) != null){
                    if(parser.nWordFromMsg(parser.protocolMsg(line), 2).contains("001")){
                        kirjoittaja.write("JOIN #testi123\n");
                        System.out.println(line);
                        kirjoittaja.flush();
                    }
                    else if(parser.nWordFromMsg(parser.protocolMsg(line), 1).equalsIgnoreCase("PING")){
                        kirjoittaja.write("PONG :"+parser.msg(line)+"\n");
                        System.out.println(line);
                        System.out.print("Vastaus: PONG :"+parser.msg(line)+"\n");
                        kirjoittaja.flush();
                    }
                    else if(parser.nWordFromMsg(parser.msg(line), 1).equals("!huuda")){
                        kirjoittaja.write("PRIVMSG "+ parser.channelProt(parser.protocolMsg(line))+" :"+parser.everythingElseExceptFirstWordFromMsg(parser.msg(line))+"\n");
                        System.out.println(line);
                        System.out.print("Vastaus: PRIVMSG "+ parser.channelProt(parser.protocolMsg(line))+" :"+parser.everythingElseExceptFirstWordFromMsg(parser.msg(line))+"\n");
                        kirjoittaja.flush();
                    }
                    else
                        System.out.println(line);
                }
            }
            
        }
        catch (IOException e){
            System.out.println("error " + e);
            
        }
        
    }
    
}
