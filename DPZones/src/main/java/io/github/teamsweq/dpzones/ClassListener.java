/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.github.teamsweq.dpzones;

import java.util.HashMap;

import org.bukkit.entity.Player;

/**
 *
 * @author Matt
 */
public class ClassListener implements org.bukkit.event.Listener {
    DPZones plugin;
    
    public static final int
            HEAVY_ID = 1,
            ARCHER_ID = 2,
            MEDIC_ID = 3,
            SOLDIER_ID = 4,
            ASSASSIN_ID = 5,
            CHEMIST_ID = 6,
            DWARF_ID = 7,
            ENGINEER_ID = 8,
            FASHIONISTA_ID = 9,
            MAGE_ID = 10,
            NECRO_ID = 11,
            NINJA_ID = 12,
            PYRO_ID = 13;                  
    
    private HashMap<Player, Integer> playerClasses;
	
	public ClassListener(DPZones instance) {
	    this.plugin = instance;
            playerClasses = new HashMap<>();
	  }
    
        public void updatePlayerClass(Player player, int classID) {
            playerClasses.put(player, classID);
        }
}
