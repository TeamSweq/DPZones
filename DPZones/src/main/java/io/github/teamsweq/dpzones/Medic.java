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

public class Medic implements CommandExecutor {
	
	DPZones plugin;
	
	public Medic(DPZones plugin) {
		this.plugin = plugin;
	}
	
	public static void medicClass(Player player) {
		PlayerInventory inventory = player.getInventory();
		ItemStack medicHelm = new ItemStack(Material.GOLD_HELMET, 1);
		ItemStack medicChest = new ItemStack (Material.GOLD_CHESTPLATE, 1);
		ItemStack medicLegs = new ItemStack (Material.GOLD_LEGGINGS, 1);
		ItemStack medicBoots = new ItemStack (Material.GOLD_BOOTS, 1);
		ItemStack medicSword = new ItemStack (Material.GOLD_SWORD, 1);
		ItemStack medicSteak = new ItemStack (Material.COOKED_BEEF, 6);
		ItemStack medicSnowballs = new ItemStack (Material.SNOW_BALL, 12);
		player.setGameMode(GameMode.ADVENTURE);
		FoodListener.hungerLevel(player, 15, 40.00D);
		inventory.clear();
		inventory.setHelmet(medicHelm);
		inventory.setChestplate(medicChest);
		inventory.setLeggings(medicLegs);
		inventory.setBoots(medicBoots);
		inventory.addItem(medicSword, medicSteak, medicSnowballs);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("medic")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				medicClass(player);
				sender.sendMessage(ChatColor.AQUA + "You are now a medic!");
			}
		}
		return false;
	}
	
}