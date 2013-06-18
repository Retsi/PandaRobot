
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author retsi
 */
public class EnabledFunctions {
    
    HashMap<String, HashMap<String, Boolean>> functions;
    
    public EnabledFunctions(ArrayList<String> list){
        functions = new HashMap<String, HashMap<String, Boolean>>();
        for(String channel: list){
            functions.put(channel, new Functions().getFunctions());
        }
    }
    
    public void enableFunction(String channel, String function){
        if(functions.containsKey(channel)){
            if(functions.get(channel).containsKey(function)){
                functions.get(channel).put(function, true);
            }
        }
    }
    
    public void disableFunction(String channel, String function){
        if(functions.containsKey(channel)){
            if(functions.get(channel).containsKey(function)){
                functions.get(channel).put(function, false);
            }
        }
    }
    
    public Boolean getFunctionStatusOnChannel(String channel, String function){
        if(functions.containsKey(channel)){
            return functions.get(channel).get(function);
        }
        return true;
    }
}

class Functions{
    
    HashMap<String, Boolean> functions;
    
    public Functions(){
        functions = new HashMap<String, Boolean>();
        functions.put("weather", true);
        functions.put("usermodes", true);
        functions.put("reminder", true);
        functions.put("urltitle", true);
        functions.put("echo", true);
        functions.put("raw", true);
    }
    
    public HashMap<String, Boolean> getFunctions(){
        return functions;
    }
}