package com.gmail.jjfent.zones;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Teams implements Listener, CommandExecutor {
	
	private final Zones plugin;
	
	public Teams(Zones plugin) {
		this.plugin = plugin;
	}
	
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	Scoreboard board = manager.getNewScoreboard();
	
	public Team redTeam = board.registerNewTeam("Red");
	public Team blueTeam = board.registerNewTeam("Blue");
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onLogin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		// Random rand = new Random();
		// int team = rand.nextInt(2) + 1;
		player.setScoreboard(board);
		if (!(redTeam.hasPlayer(player)) && !(blueTeam.hasPlayer(player))) {
			if (redTeam.getSize() >= blueTeam.getSize()) {
				blueTeam.addPlayer(player);
				player.sendMessage(ChatColor.BLUE + "You joined Blue Team!");
			} else {
				redTeam.addPlayer(player);
				player.sendMessage(ChatColor.RED + "You joined Red Team!");
			}
			// if (((team == 1) && (redTeam.getSize() + 2 > blueTeam.getSize()) || (team == 1) && (redTeam.getSize() + 1 > blueTeam.getSize()) || (team == 1) && (redTeam.getSize() == blueTeam.getSize()) && (redTeam.getSize() > blueTeam.getSize()))) {
			// 	blueTeam.addPlayer(player);
			// 	player.sendMessage(ChatColor.BLUE + "You joined Blue Team!");
			// } 
			// if (((team == 2) && (redTeam.getSize() < blueTeam.getSize() + 2)) || ((team == 2) && (redTeam.getSize() < blueTeam.getSize() + 1)) || ((team == 2) && (redTeam.getSize() == blueTeam.getSize()) && (redTeam.getSize() < blueTeam.getSize()))){
			// 	redTeam.addPlayer(player);
			// 	player.sendMessage(ChatColor.RED + "You joined Red Team!");
			// }
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("switch")) {
			Player player = (Player) sender;
			if (sender instanceof Player) {
				if (redTeam.hasPlayer(player)) {
					if ((blueTeam.getSize() + 2 > redTeam.getSize()) || (blueTeam.getSize() + 1 > redTeam.getSize()) || (blueTeam.getSize() == redTeam.getSize()) || (blueTeam.getSize() < redTeam.getSize())) {
						redTeam.removePlayer(player);
						blueTeam.addPlayer(player);
						sender.sendMessage(ChatColor.BLUE + "You joined Blue Team!");
					} else {
						sender.sendMessage(ChatColor.GRAY + "You cannot switch teams!");
					}
				}
				else if (blueTeam.hasPlayer(player)) {
					if ((blueTeam.getSize() < redTeam.getSize() + 2) || (blueTeam.getSize() < redTeam.getSize() + 1) || (blueTeam.getSize() == redTeam.getSize()) || (blueTeam.getSize() > redTeam.getSize())) {
						blueTeam.removePlayer(player);
						redTeam.addPlayer(player);
						sender.sendMessage(ChatColor.RED + "You joined Red Team!");
					} else {
						sender.sendMessage(ChatColor.GRAY + "You cannot switch teams!");
					}
				} else sender.sendMessage(ChatColor.GRAY + "You aren't on a team!");
			} else {
				sender.sendMessage("Only players can switch teams!");
				return false;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("team")) {
			Player player = (Player) sender;
			if (sender instanceof Player) {
				if (redTeam.hasPlayer(player)) {
					sender.sendMessage(ChatColor.RED + "You are on Red Team!");
				}
				if (blueTeam.hasPlayer(player)) {
					sender.sendMessage(ChatColor.BLUE + "You are on Blue Team!");
				}
				if (!(blueTeam.hasPlayer(player) || redTeam.hasPlayer(player))){
					sender.sendMessage(ChatColor.GOLD + "You are spectating!");
				}
			} else {
				sender.sendMessage("Only players can be on teams!");
				return false;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("spectate")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				PlayerInventory inventory = player.getInventory();
				ItemStack empty = new ItemStack(Material.AIR, 64);
				if (redTeam.hasPlayer(player))
					redTeam.removePlayer(player);
				else blueTeam.removePlayer(player);
				inventory.clear();
				inventory.setBoots(empty);
				inventory.setChestplate(empty);
				inventory.setHelmet(empty);
				inventory.setLeggings(empty);
				player.setGameMode(GameMode.CREATIVE);
				sender.sendMessage(ChatColor.GOLD + "You are now spectating!");
			} else {
				sender.sendMessage("Only players can spectate!");
				return false;
			}
		}
		return false;
	}
		
//	public void joinTeam() {
//		Player player = event.getPlayer();
//		Random rand = new Random();
//		int team = rand.nextInt(2) + 1;
//		if (!(redTeam.hasPlayer(player) && blueTeam.hasPlayer(player))) {
//			
//		}
//	}
	
}
