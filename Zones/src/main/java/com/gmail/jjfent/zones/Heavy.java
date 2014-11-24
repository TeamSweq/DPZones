package com.gmail.jjfent.zones;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Heavy implements CommandExecutor {
	
	private final Zones plugin;
	
	public Heavy(Zones plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("heavy")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				PlayerInventory inventory = player.getInventory();
				ItemStack heavyHelm = new ItemStack(Material.DIAMOND_HELMET, 1);
				ItemStack heavyChest = new ItemStack (Material.DIAMOND_CHESTPLATE, 1);
				ItemStack heavyLegs = new ItemStack (Material.DIAMOND_LEGGINGS, 1);
				ItemStack heavyBoots = new ItemStack (Material.DIAMOND_BOOTS, 1);
				ItemStack heavySword = new ItemStack (Material.DIAMOND_SWORD, 1);
				ItemStack heavySteak = new ItemStack (Material.COOKED_BEEF, 3);
				player.setGameMode(GameMode.SURVIVAL);
				HungerSetup.hungerHealth(player);
				inventory.clear();
				inventory.setHelmet(heavyHelm);
				inventory.setChestplate(heavyChest);
				inventory.setLeggings(heavyLegs);
				inventory.setBoots(heavyBoots);
				inventory.addItem(heavySword, heavySteak);
				sender.sendMessage("You have chosen the Heavy class");
			} else {
				sender.sendMessage("Only players can choose classes!");
				return false;
			}
		}
		return false;
	}
	
}
