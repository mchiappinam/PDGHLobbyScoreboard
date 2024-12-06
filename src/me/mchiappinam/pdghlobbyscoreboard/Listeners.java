package me.mchiappinam.pdghlobbyscoreboard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {
	
	private Main plugin;
	public Listeners(Main main) {
		plugin=main;
	}
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().setScoreboard(Main.board);
    }
}