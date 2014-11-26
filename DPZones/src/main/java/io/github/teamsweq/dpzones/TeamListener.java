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
	
	//Puts a player on a team
	
	public static void joinTeam(Player player) {
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
	
	//checks if two players are on the same team
	
	public boolean areOnSameTeam(Player p1, Player p2) {
		if( (Teams.blueTeam.hasPlayer(p1) && Teams.blueTeam.hasPlayer(p2)) 
		   || (Teams.redTeam.hasPlayer(p1) && Teams.redTeam.hasPlayer(p2)) )
			return true;
		return false;
	}
	
	//Whenever the player logs on, they get put on a team
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onLogin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		joinTeam(player);
	}
	
}
