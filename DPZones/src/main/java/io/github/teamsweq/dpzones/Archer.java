package io.github.teamsweq.dpzones;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Archer implements CommandExecutor {
	
	DPZones plugin;
	
	public Archer(DPZones plugin) {
		this.plugin = plugin;
	}
	
	public static void archerClass(Player player) {
		PlayerInventory inventory = player.getInventory();
		ItemStack archerHelm = new ItemStack(Material.LEATHER_HELMET, 1);
		ItemStack archerChest = new ItemStack (Material.CHAINMAIL_CHESTPLATE, 1);
		ItemStack archerLegs = new ItemStack (Material.CHAINMAIL_LEGGINGS, 1);
		ItemStack archerBoots = new ItemStack (Material.CHAINMAIL_BOOTS, 1);
		ItemStack archerSword = new ItemStack (Material.STONE_SWORD, 1);
		ItemStack archerSteak = new ItemStack (Material.COOKED_BEEF, 4);
		ItemStack archerBow = new ItemStack (Material.BOW, 1);
		ItemStack archerArrows = new ItemStack (Material.ARROW, 64);
		player.setGameMode(GameMode.SURVIVAL);
		FoodListener.hungerLevel(player, 15, 20.00D);
		inventory.clear();
		inventory.setHelmet(archerHelm);
		inventory.setChestplate(archerChest);
		inventory.setLeggings(archerLegs);
		inventory.setBoots(archerBoots);
		inventory.addItem(archerSword, archerBow, archerSteak, archerArrows, archerArrows);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("archer")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				archerClass(player);
				sender.sendMessage(ChatColor.MAGIC + "You are now an Archer!");
			}
		}
		return false;
	}
	
}