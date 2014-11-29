package org.teamsweq.dpzones.classes;

import org.teamsweq.dpzones.ClassAssign;
import org.teamsweq.dpzones.ClassUnAssign;
import org.teamsweq.dpzones.ZonesClass;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class Spectate implements ZonesClass {
	
	private Spectate(){}
	
	@ClassAssign
	public static void onAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		player.setGameMode(GameMode.CREATIVE);
	}
	
	@ClassUnAssign
	public static void onUnAssign(Player player){
		PlayerInventory inventory = player.getInventory();
		inventory.clear();
		player.setGameMode(GameMode.SURVIVAL);
	}
}