
/**
 *
 * @author retsi
 */
public class Parser {
    
    public Parser(){
        
    }
    
    public String protocolMsg(String msg){
        
        String ret = msg; // was String ret = "";
        
        for(int i = 1; i < msg.length(); i++){
            if(msg.charAt(i) == ':'){
                ret = msg.substring(0, i);
            }
        }
        return ret;
    }
    
    public String msg(String msg){
        
        String ret = msg;
        
        for(int i = 1; i < msg.length(); i++){
            if(msg.charAt(i) == ':'){
                ret = msg.substring(i+1);
                break;
            }
        }
        
        return ret;
    }
    
    public String nWordFromMsg(String msg, int n){
        
        int onword = 1;
        
        if(!msg.contains(" "))
            return msg;
        else
            for(int i = 0; i < msg.length(); i++){
                if (msg.charAt(i) == ' ')
                    onword++;
                if(onword == n){
                    for(int j = i+1; j<msg.length(); j++){
                        if(msg.charAt(j) == ' ')
                            return msg.substring(i, j).trim();
                    }
                    return msg.substring(i).trim();
                }
            }
        return "";
    }
    
    public int numberOfWordsOnMessage(String msg){
        if(msg.equals(""))
            return 0;
        else if(!msg.contains(" ")){
            return 1;
        }
        else{
            int numberOfWords = 1;
            for (int i = 0; i < msg.length(); i++){
                if (msg.charAt(i) == ' ')
                    numberOfWords++;
            }
            return numberOfWords;
        }
    }
    
    public String url(String msg){
        String url = "";
        
        for(int i = 1; i <= numberOfWordsOnMessage(msg); i++){
            if(nWordFromMsg(msg, i).contains("https://") || nWordFromMsg(msg, i).contains("http://"))
                url = nWordFromMsg(msg, i);
        }
        
        return url;
    }
    
    public String nickProtMsg(String msg){
        if(!msg.contains("!"))
            return "";
        else{
            for(int i = 0; i< msg.length(); i++){
                if(msg.charAt(i) == '!')
                    return msg.substring(1, i);
            }
        }return "";
    }
    
    public String channelProt(String msg){
        if(!msg.contains("#"))
            return "";
        else{
            for(int i = 0; i< msg.length(); i++){
                if(msg.charAt(i) == '#'){
                    for(int j = i+1; j<msg.length(); j++){
                        if (j == msg.length()-1)
                            return msg.substring(i, j+1).trim();
                        if(msg.charAt(j) == ' ')
                            return msg.substring(i, j).trim();
                    }
                }
            }
        }return "";
    }
    
    public String everythingElseExceptFirstWordFromMsg(String msg){
        
        String afterFirst = "";
        
        for(int i = 2; i <= numberOfWordsOnMessage(msg); i++){
            if ( i != numberOfWordsOnMessage(msg))
                afterFirst = afterFirst + nWordFromMsg(msg, i) + " ";
            else
                afterFirst = afterFirst + nWordFromMsg(msg, i);
        }
        return afterFirst;
    }
    
    public String getAddy(String msg){
        
        String addy = "";
        
        for(int i = 0; i<msg.length(); i++){
            if(msg.charAt(i) == '!')
                addy = msg.substring(i+1);
        }
        for(int i = 0; i< addy.length(); i++){
            if(addy.charAt(i) == ' ')
                addy = addy.substring(0, i);
        }
        return addy;
    }
    
}
