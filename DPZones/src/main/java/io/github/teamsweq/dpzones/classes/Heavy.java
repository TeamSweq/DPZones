package io.github.teamsweq.dpzones.classes;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.teamsweq.dpzones.ClassAssign;
import io.github.teamsweq.dpzones.ClassInit;
import io.github.teamsweq.dpzones.ClassUnAssign;
import io.github.teamsweq.dpzones.ZonesClass;

public class Heavy implements ZonesClass {
	
	private Heavy(){}
	
	@ClassInit
	public static void onInit(JavaPlugin plugin){
		
	}
	
	@ClassAssign
	public static void onAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		inventory.addItem(new ItemStack(Material.DIAMOND_SWORD),
				new ItemStack(Material.COOKED_BEEF, 3));
		inventory.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		inventory.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		inventory.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		inventory.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
		player.setGameMode(GameMode.ADVENTURE);
		player.setFoodLevel(15);
	}
	
	@ClassUnAssign
	public static void onUnAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		player.setGameMode(GameMode.SURVIVAL);
	}
}
