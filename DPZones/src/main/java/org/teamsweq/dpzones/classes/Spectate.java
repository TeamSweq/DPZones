package org.teamsweq.dpzones.classes;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.teamsweq.dpzones.ClassAssign;
import org.teamsweq.dpzones.ClassUnAssign;
import org.teamsweq.dpzones.ZonesClass;

public class Spectate implements ZonesClass {
	
	private Spectate(){}
	
	@ClassAssign
	public static void onAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		player.setGameMode(GameMode.CREATIVE);
		for (Player players : Bukkit.getOnlinePlayers()) {
			//Going to make it so people with certain permissions or other spectators can see eachother
			players.hidePlayer(player);
		}
	}
	
	@ClassUnAssign
	public static void onUnAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		inventory.setHelmet(new ItemStack(Material.AIR));
		inventory.setChestplate(new ItemStack(Material.AIR));
		inventory.setLeggings(new ItemStack(Material.AIR));
		inventory.setBoots(new ItemStack(Material.AIR));
		for (Player players : Bukkit.getOnlinePlayers()) {
			//Going to make it so people with certain permissions or other spectators can see eachother
			players.showPlayer(player);
		}
	}
}