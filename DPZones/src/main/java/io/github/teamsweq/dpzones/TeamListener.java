package io.github.teamsweq.dpzones;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.teamsweq.dpzones.Teams;

public class TeamListener implements Listener {
	
	DPZones plugin;
	
	public TeamListener(DPZones instance)
	  {
	    this.plugin = instance;
	  }
	
	//Whenever the player logs on, they get put on a team
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onLogin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setScoreboard(Teams.board);
		if (!(Teams.redTeam.hasPlayer(player)) && !(Teams.blueTeam.hasPlayer(player))) {
			if (Teams.redTeam.getSize() >= Teams.blueTeam.getSize()) {
				Teams.blueTeam.addPlayer(player);
				player.sendMessage(ChatColor.BLUE + "You joined Blue Team!");
			} else {
				Teams.redTeam.addPlayer(player);
				player.sendMessage(ChatColor.RED + "You joined Red Team!");
			}
		}
	}
	
}