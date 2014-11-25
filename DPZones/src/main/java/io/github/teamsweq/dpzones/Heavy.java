package io.github.teamsweq.dpzones;

import static io.github.teamsweq.dpzones.Archer.plugin;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Heavy implements CommandExecutor{

	DPZones plugin;
	
	public Heavy(DPZones plugin) {
		this.plugin = plugin;
	}
	
	public static void heavyClass(Player player) {
		PlayerInventory inventory = player.getInventory();
		ItemStack heavyHelm = new ItemStack(Material.DIAMOND_HELMET, 1);
		ItemStack heavyChest = new ItemStack (Material.DIAMOND_CHESTPLATE, 1);
		ItemStack heavyLegs = new ItemStack (Material.DIAMOND_LEGGINGS, 1);
		ItemStack heavyBoots = new ItemStack (Material.DIAMOND_BOOTS, 1);
		ItemStack heavySword = new ItemStack (Material.DIAMOND_SWORD, 1);
		ItemStack heavySteak = new ItemStack (Material.COOKED_BEEF, 3);
		player.setGameMode(GameMode.ADVENTURE);
		FoodListener.hungerLevel(player, 15, 20.00D);
		inventory.clear();
		inventory.setHelmet(heavyHelm);
		inventory.setChestplate(heavyChest);
		inventory.setLeggings(heavyLegs);
		inventory.setBoots(heavyBoots);
		inventory.addItem(heavySword, heavySteak);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("heavy")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				heavyClass(player);
				sender.sendMessage(ChatColor.AQUA + "U r nao a hevy fagit!1!!!1!1");
                                
                                plugin.classListener.updatePlayerClass(player, ClassListener.HEAVY_ID);
			} else {
				sender.sendMessage("Only players can choose classes!");
				return false;
			}
		}
		return false;
	}

	
	
}
