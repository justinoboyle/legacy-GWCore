package depricated.com.lightcraftmc.guildwars.java.core.types;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;

import com.lightcraftmc.guildwars.java.core.main.GWCore;

@Deprecated
public class PDGuild {
    
    @Deprecated
    private HashMap<String, String> members = new HashMap<String, String>();
    @Deprecated
    private String name;
    @Deprecated
    private String description;
    @Deprecated
    private String owner;

    @Deprecated
    public PDGuild(String name, String description, String owner) {
        super();
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    @Deprecated
    public HashMap<String, String> getMembers() {
        return members;
    }

    @Deprecated
    public void setMembers(HashMap<String, String> members) {
        this.members = members;
    }

    @Deprecated
    public String getName() {
        return name;
    }

    @Deprecated
    public void setName(String name) {
        this.name = name;
    }

    @Deprecated
    public String getDescription() {
        return description;
    }

    @Deprecated
    public void setDescription(String description) {
        this.description = description;
    }

    @Deprecated
    public String getOwner() {
        return owner;
    }

    @Deprecated
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @SuppressWarnings("deprecation")
    public void saveToDB() {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(GWCore.getInstance(), new Runnable() {
            public void run() {
                save();
            }
        }, 1);
    }

    @Deprecated
    private synchronized void save() {
        GWCore.getInstance().getDBManager().query("insert guilds/" + name + " description " + description.replace(" ", "_"));
        GWCore.getInstance().getDBManager().query("insert guilds/" + name + " owner " + owner.toString());
        GWCore.getInstance().getDBManager().query("insert guilds/" + name + " members " + getMemberString().replace(" ", "_"));
        GWCore.getInstance().getDBManager().query("insert guilds/" + name.toLowerCase().replace("/", "").replace(" ", "") + " name " + getName().replace(" ", "_"));
    }

    @Deprecated
    private synchronized String getMemberString() {
        String s = "";
        for (String u : members.keySet()) {
            if (!u.equals(getDescription()) && !u.equals(getDescription().replace("_", " "))) {
                s = s + u + ",";
            }
        }

        return s;
    }

    @Deprecated
    public synchronized static PDGuild fromName(String name) {
        String desc = GWCore.getInstance().getDBManager().query("retrieve guilds/" + name + " description");
        String owner = GWCore.getInstance().getDBManager().query("retrieve guilds/" + name + " owner");
        String workingMembers = GWCore.getInstance().getDBManager().query("retrieve guilds/" + name + " members");
        String dispName = GWCore.getInstance().getDBManager().query("retrieve guilds/" + name + " name");

        if (desc.startsWith("ERROR") || owner.startsWith("ERROR") || workingMembers.startsWith("ERROR") || name.startsWith("ERROR")) {
            return null;
        }

        PDGuild working = new PDGuild(dispName, desc, owner);

        for (String s : workingMembers.split(",")) {
            s = s.replace("_", " ");
            try {
                working.members.put(s, "");
            } catch (Exception ex) {

            }
        }

        return working;

    }

    @Deprecated
    public static boolean disbandGuild(PDGuild g) {
        String response = GWCore.getInstance().getDBManager().query("deletecategory guilds/" + g.getName());
        return response.contains("SUCCESS");
    }

    @Deprecated
    public static ArrayList<PDGuild> getGuilds() {
        String guildList = GWCore.getInstance().getDBManager().query("list guilds".replace("_", " "));
        ArrayList<PDGuild> guilds = new ArrayList<PDGuild>();
        try {
            guildList = guildList.split("::")[1];
        } catch (Exception ex) {
            return guilds;
        }

        for (String s : guildList.split(",")) {
            try {
                PDGuild g = PDGuild.fromName(s);
                if (g != null) {
                    guilds.add(g);
                }
            } catch (Exception ex) {

            }
        }

        return guilds;

    }

}
