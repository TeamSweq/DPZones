package io.github.teamsweq.dpzones.classes;

import io.github.teamsweq.dpzones.ClassAssign;
import io.github.teamsweq.dpzones.ClassUnAssign;
import io.github.teamsweq.dpzones.ZonesClass;

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
