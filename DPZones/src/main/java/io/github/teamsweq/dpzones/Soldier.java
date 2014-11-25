package io.github.teamsweq.dpzones;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Soldier implements CommandExecutor{
	
	DPZones plugin;
	
	public Soldier(DPZones plugin) {
		this.plugin = plugin;
	}
	
	public static void soldierClass(Player player) {
		PlayerInventory inventory = player.getInventory();
		ItemStack soldierHelm = new ItemStack(Material.GOLD_HELMET, 1);
		ItemStack soldierChest = new ItemStack (Material.GOLD_CHESTPLATE, 1);
		ItemStack soldierLegs = new ItemStack (Material.GOLD_LEGGINGS, 1);
		ItemStack soldierBoots = new ItemStack (Material.GOLD_BOOTS, 1);
		ItemStack soldierSword = new ItemStack (Material.GOLD_SWORD, 1);
		ItemStack soldierSteak = new ItemStack (Material.COOKED_BEEF, 4);
		player.setGameMode(GameMode.ADVENTURE);
		FoodListener.hungerLevel(player, 15, 20.00D);
		inventory.clear();
		inventory.setHelmet(soldierHelm);
		inventory.setChestplate(soldierChest);
		inventory.setLeggings(soldierLegs);
		inventory.setBoots(soldierBoots);
		inventory.addItem(soldierSword, soldierSteak);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("soldier")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				soldierClass(player);
				sender.sendMessage(ChatColor.AQUA + "You are now a soldier!");

				plugin.classListener.updatePlayerClass(player, ClassListener.SOLDIER_ID);
			} else {
				sender.sendMessage("Only players can choose classes!");
				return false;
			}
		}
		return false;
	}
	
}
