package io.github.teamsweq.dpzones;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Teams implements CommandExecutor {
	
	private final DPZones plugin;
	
	public Teams(DPZones plugin) {
		this.plugin = plugin;
	}
	
	public static ScoreboardManager manager = Bukkit.getScoreboardManager();
	public static Scoreboard board = manager.getNewScoreboard();
	
	public static Team redTeam = board.registerNewTeam("Red");
	public static Team blueTeam = board.registerNewTeam("Blue");
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("switch")) {
			Player player = (Player) sender;
			if (sender instanceof Player) {
				if (redTeam.hasPlayer(player)) {
					if (redTeam.getSize() >= blueTeam.getSize()) {
						redTeam.removePlayer(player);
						blueTeam.addPlayer(player);
					} else {
						sender.sendMessage(ChatColor.GRAY + "You cannot switch teams!");
					}
				}
				else if (blueTeam.hasPlayer(player)) {
					if (blueTeam.getSize() >= redTeam.getSize()) {
						blueTeam.removePlayer(player);
						redTeam.addPlayer(player);
					} else {
						sender.sendMessage(ChatColor.GRAY + "You cannot switch teams!");
					}
				} else sender.sendMessage(ChatColor.GRAY + "You aren't on a team!");
			} else {
				sender.sendMessage("Only players can switch teams!");
				return false;
			}
		}
		
		//Displays back the team the player is on
		
		if (cmd.getName().equalsIgnoreCase("team")) {
			Player player = (Player) sender;
			if (sender instanceof Player) {
				if (redTeam.hasPlayer(player)) {
					sender.sendMessage(ChatColor.RED + "You are on Red Team!");
				}
				if (blueTeam.hasPlayer(player)) {
					sender.sendMessage(ChatColor.BLUE + "You are on Blue Team!");
				}
				if ( (!(blueTeam.hasPlayer(player)) && (!(redTeam.hasPlayer(player))) )){
					sender.sendMessage(ChatColor.GOLD + "You are spectating!");
				}
			} else {
				sender.sendMessage("Only players can be on teams!");
				return false;
			}
		}
		
		//Allows mods to make players spectate, in 1.8 they'll be put in gm 3
		
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
	
}