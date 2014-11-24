package com.gmail.jjfent.zones;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Classes implements CommandExecutor {
	
private final Zones plugin;
	
	public Classes(Zones plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("classes")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				sender.sendMessage(ChatColor.GRAY + "Classes: /Heavy, /Archer");
			} else {
				sender.sendMessage("Only players can choose classes!");
				return false;
			}
		}
		return false;
	}
	
}
