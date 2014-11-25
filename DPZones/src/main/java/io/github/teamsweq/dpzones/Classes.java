package io.github.teamsweq.dpzones;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Classes implements CommandExecutor {
	
	DPZones plugin;
	
	public Classes(DPZones plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ( (cmd.getName().equalsIgnoreCase("classes")) || (cmd.getName().equalsIgnoreCase("class")) ) {
			if (sender instanceof Player) {
				sender.sendMessage(ChatColor.GRAY + "Classes: /Heavy, /Archer");
			} else {
				sender.sendMessage("Only players can choose classes!");
				return false;
			}
		}
		return false;
	}
	
}
