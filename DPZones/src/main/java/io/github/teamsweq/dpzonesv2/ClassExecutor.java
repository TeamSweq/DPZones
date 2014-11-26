package io.github.teamsweq.dpzonesv2;

import io.github.teamsweq.dpzones.ClassListener;
import io.github.teamsweq.dpzones.classes.Heavy;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("heavy")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				Heavy.onAssign(player);
				sender.sendMessage(ChatColor.AQUA + "U r nao a hevy fagit!1!!!1!1");
                                
			} else {
				sender.sendMessage("Only players can choose classes!");
				return false;
			}
		}
		return false;
	}
	
	
}
