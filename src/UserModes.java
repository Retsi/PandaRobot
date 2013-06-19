
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author retsi
 */
public class UserModes implements Serializable {

    private HashMap<String, ArrayList<String>> oplist;
    private HashMap<String, ArrayList<String>> voicelist;
    private String network = "";

    public UserModes(String network) {
        this.oplist = new HashMap<String, ArrayList<String>>();
        this.voicelist = new HashMap<String, ArrayList<String>>();

        this.network = network;

        File channelmodes = new File(network + "usermodes.data");

        if (channelmodes.exists()) {
            loadUserModes();
        }
    }

    public void addUserToOpList(String addy, String channel) {
        if (oplist.containsKey(addy)) {
            if (oplist.get(addy).contains(channel)) {
                return;
            } else {
                oplist.get(addy).add(channel);
                saveUsermodes();
            }
        } else {
            ArrayList<String> channelList = new ArrayList();
            channelList.add(channel);
            oplist.put(addy, channelList);
            saveUsermodes();
        }
    }

    public void addUserToVoiceList(String addy, String channel) {
        if (voicelist.containsKey(addy)) {
            if (voicelist.get(addy).contains(channel)) {
                return;
            } else {
                voicelist.get(addy).add(channel);
                saveUsermodes();
            }
        } else {
            ArrayList<String> channelList = new ArrayList();
            channelList.add(channel);
            voicelist.put(addy, channelList);
            saveUsermodes();
        }
    }

    public void removeUserFromOpList(String addy, String channel) {
        if (oplist.containsKey(addy)) {
            if (oplist.get(addy).contains(channel)) {
                oplist.get(addy).remove(channel);
                saveUsermodes();
            } else {
                return;
            }
        } else {
            return;
        }
    }

    public void removeUserFromVoiceList(String addy, String channel) {
        if (voicelist.containsKey(addy)) {
            if (voicelist.get(addy).contains(channel)) {
                voicelist.get(addy).remove(channel);
                saveUsermodes();
            } else {
                return;
            }
        } else {
            return;
        }
    }

    public ArrayList getUsersOpChannels(String user) {
        return oplist.get(user);
    }

    public ArrayList getUsersVoiceChannels(String user) {
        return voicelist.get(user);
    }

    public HashMap getOpList() {
        return oplist;
    }

    public HashMap getVoiceList() {
        return voicelist;
    }

    public void saveUsermodes() {

        try {
            FileOutputStream fileOut = new FileOutputStream(network + "usermodes.data");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(oplist);
            out.writeObject(voicelist);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    public void loadUserModes() {

        try {
            FileInputStream fileIn = new FileInputStream(network + "usermodes.data");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            oplist = (HashMap<String, ArrayList<String>>) in.readObject();
            voicelist = (HashMap<String, ArrayList<String>>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }

    }
}
