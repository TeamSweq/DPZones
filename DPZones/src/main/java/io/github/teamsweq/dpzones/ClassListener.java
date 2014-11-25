/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.github.teamsweq.dpzones;

import io.github.teamsweq.dpzones.DPZones;
import java.util.HashMap;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

/**
 *
 * @author Matt
 */
public class ClassListener implements org.bukkit.event.Listener {
    DPZones plugin;
    
    public static final int
            ARCHER_ID = 0,
            ASSASSIN_ID = 1,
            CHEMIST_ID = 2,
            DWARF_ID = 3,
            ENGINEER_ID = 4,
            FASHIONISTA_ID = 5,
            HEAVY_ID = 6,
            MAGE_ID = 7,
            MEDIC_ID = 8,
            NECRO_ID = 9,
            NINJA_ID = 10,
            PYRO_ID = 11,
            SOLDIER_ID = 12;
                  
    
    private HashMap<Player, Integer> playerClasses;
	
	public ClassListener(DPZones instance) {
	    this.plugin = instance;
            playerClasses = new HashMap<>();
	  }
    
        public void updatePlayerClass(Player player, int classID) {
            playerClasses.put(player, classID);
        }
}
