package com.gmail.jjfent.zones;

import org.bukkit.entity.Player;

public class HungerSetup {
	
public static void hungerHealth (Player player) {
		
		player.setFoodLevel(15);
		player.setHealth(20);
		player.setExhaustion(-1);
		player.setSaturation(100);
		
	}
	
}
