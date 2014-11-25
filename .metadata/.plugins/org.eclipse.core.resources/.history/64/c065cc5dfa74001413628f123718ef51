/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.github.teamsweq.dpzones;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 *
 * @author Matt
 */
public class ClassListener implements Listener {
	
    DPZones plugin;
    
    public static final int
            HEAVY_ID = 1,
            ARCHER_ID = 2,
            MEDIC_ID = 3,
            SOLDIER_ID = 4;                  
    
    private static HashMap<String, Integer> playerClasses;
	
	public ClassListener(DPZones instance) {
	    this.plugin = instance;
            playerClasses = new HashMap<>();
	  }
    
        public void updatePlayerClass(Player player, int classID) {
        	String playerName = player.getName();
            playerClasses.put(playerName, classID);
        }
        
//        public static void playerReset(Player player, int classID) {
//        	if () {
//        		
//        	}
//        }
	public int getClassID(Player player) {
        	return playerClasses.get(player);
        }
}
