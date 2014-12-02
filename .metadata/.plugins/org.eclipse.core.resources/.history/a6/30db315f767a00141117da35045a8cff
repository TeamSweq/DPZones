package org.teamsweq.dpzones.classes;

import org.teamsweq.dpzones.ClassAssign;
import org.teamsweq.dpzones.ClassInit;
import org.teamsweq.dpzones.ClassUnAssign;
import org.teamsweq.dpzones.ZonesClass;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Medic implements ZonesClass {
	
	private Medic(){}
	
	@ClassInit
	public static void onInit(JavaPlugin plugin){
		plugin.getServer().getPluginManager().registerEvents(new Listener(){
			//TODO
		}, plugin);
	}
	
	@ClassAssign
	public static void onAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		inventory.addItem(new ItemStack(Material.GOLD_SWORD),
				new ItemStack(Material.COOKED_BEEF, 6),
				new ItemStack(Material.SNOW_BALL, 5));
		inventory.setHelmet(new ItemStack(Material.GOLD_HELMET));
		inventory.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
		inventory.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
		inventory.setBoots(new ItemStack(Material.GOLD_BOOTS));
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