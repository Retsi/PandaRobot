
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author retsi
 */
public class UserModes {
    
    private HashMap<String, ArrayList<String>> oplist;
    private HashMap<String, ArrayList<String>> voicelist;
    
    public UserModes(){
        this.oplist = new HashMap<String, ArrayList<String>>();
        this.voicelist = new HashMap<String, ArrayList<String>>();
    }
    
    public void addUserToOpList(String addy, String channel){
        if (oplist.containsKey(addy)){
            if(oplist.get(addy).contains(channel))
                return;
            else
                oplist.get(addy).add(channel);
        }
        else{
            ArrayList<String> channelList = new ArrayList();
            channelList.add(channel);
            oplist.put(addy, channelList);
        }
    }
    
    public void addUserToVoiceList(String addy, String channel){
        if (voicelist.containsKey(addy)){
            if(voicelist.get(addy).contains(channel))
                return;
            else
                voicelist.get(addy).add(channel);
        }
        else{
            ArrayList<String> channelList = new ArrayList();
            channelList.add(channel);
            voicelist.put(addy, channelList);
        }
    }
    
    public void removeUserFromOpList(String addy, String channel){
        if (oplist.containsKey(addy)){
            if(oplist.get(addy).contains(channel)){
                oplist.get(addy).remove(channel);
            }
            else
                return;
        }
        else
            return;
    }
    
    public void removeUserFromVoiceList(String addy, String channel){
        if (voicelist.containsKey(addy)){
            if(voicelist.get(addy).contains(channel)){
                voicelist.get(addy).remove(channel);
            }
            else
                return;
        }
        else
            return;
    }
    
    public ArrayList getUsersOpChannels(String user){
        return oplist.get(user);
    }
    
    public ArrayList getUsersVoiceChannels(String user){
        return voicelist.get(user);
    }
    
    public HashMap getOpList(){
        return oplist;
    }
    
    public HashMap getVoiceList(){
        return voicelist;
    }
    
}
