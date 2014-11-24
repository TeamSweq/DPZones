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

public class Archer extends Teams implements CommandExecutor {
	
private final Zones plugin;
	
	public Archer(Zones plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("archer")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
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
				HungerSetup.hungerHealth(player);
				inventory.clear();
				inventory.setHelmet(archerHelm);
				inventory.setChestplate(archerChest);
				inventory.setLeggings(archerLegs);
				inventory.setBoots(archerBoots);
				inventory.addItem(archerSword, archerBow, archerSteak, archerArrows, archerArrows);
				sender.sendMessage("You have chosen the Archer class");
				Random rand = new Random();
				int team = rand.nextInt(2) + 1;
				if (!(redTeam.hasPlayer(player) && blueTeam.hasPlayer(player))) {
					if (((team == 1) && (redTeam.getSize() + 2 > blueTeam.getSize()) || (team == 1) && (redTeam.getSize() + 1 > blueTeam.getSize()) || (team == 1) && (redTeam.getSize() == blueTeam.getSize()))) {
						blueTeam.addPlayer(player);
						player.sendMessage(ChatColor.BLUE + "You joined Blue Team!");
					} else if (((team == 2) && (redTeam.getSize() < blueTeam.getSize() + 2)) || ((team == 2) && (redTeam.getSize() < blueTeam.getSize() + 1)) || ((team == 2) && (redTeam.getSize() == blueTeam.getSize()))){
						redTeam.addPlayer(player);
						player.sendMessage(ChatColor.RED + "You joined Red Team!");
					}
				}
			} else {
				sender.sendMessage("Only players can choose classes!");
				return false;
			}
		}
		return false;
	}
	
}
