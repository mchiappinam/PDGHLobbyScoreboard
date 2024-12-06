package me.mchiappinam.pdghlobbyscoreboard;

import me.mchiappinam.pdghlobbyscoreboard.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class Main extends JavaPlugin implements PluginMessageListener {
    
    public boolean go=false;
 
    public static Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    public static Objective ob = board.registerNewObjective("PlayerScoreBoard", "dummy");
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
	    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        startTimer();
		getServer().getConsoleSender().sendMessage("§3[PDGHLobbyScoreboard] §2ativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHLobbyScoreboard] §2Acesse: http://pdgh.com.br/");
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage("§3[PDGHLobbyScoreboard] §2desativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHLobbyScoreboard] §2Acesse: http://pdgh.com.br/");
	}
 
    public static void updateScoreboard() {
    }
    
	public void startTimer() {
		getServer().getScheduler().runTaskTimer(this, new Runnable() {
			public void run() {
		        for(Player p : Bukkit.getOnlinePlayers()) {
		            if(p.getScoreboard() != null && p.getScoreboard().equals(board)) {
		                ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		                ob.setDisplayName(ChatColor.GOLD + "Players online");
		 
		                Score score = ob.getScore(Bukkit.getOfflinePlayer("Online"));
		                score.setScore(Bukkit.getOnlinePlayers().length);
		            } else{
		                p.setScoreboard(board);
		            }
		        }
			}
		}, 0, 10*20);
	}
	
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
       
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
       
        if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF();
            int playerCount = in.readInt();
            /**if(server.contains("creativo")) {
            	scorecreativo.setScore(playerCount);
            }else if(server.contains("dayz2")) {
            	scoredayz2.setScore(playerCount);
            }else if(server.contains("fullpvp")) {
            	scorefullpvp.setScore(playerCount);
            }else if(server.contains("hg1")) {
            	scorehg1.setScore(playerCount);
            }else if(server.contains("hg2")) {
            	scorehg2.setScore(playerCount);
            }else if(server.contains("pvp")) {
            	scorepvp.setScore(playerCount);
            }*/
        }
       
    }
   
    public void getCount(String server) {
       
        if (server == null) {
            server = "ALL";
        }
       
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);

        getServer().sendPluginMessage(this, "BungeeCord", out.toByteArray());
        
    }
	
}