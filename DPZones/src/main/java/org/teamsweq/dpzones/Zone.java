package org.teamsweq.dpzones;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Zone {
	private List<Block> blocks;
	private List<UUID> players; //Players that are in this Zone
	public Zone(){
		blocks = new ArrayList<Block>();
		players = new ArrayList<UUID>();
	}
	public void init(JavaPlugin plugin){
		plugin.getServer().getPluginManager().registerEvents(new Listener(){
			@EventHandler
			public void onMove(PlayerMoveEvent event){
				if(inZone(event.getPlayer().getLocation())){
					players.add(event.getPlayer().getUniqueId());
				}else{
					players.remove(event.getPlayer().getUniqueId());
				}
			}
			@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
			public void onDeath(PlayerDeathEvent event){
				players.remove(event.getEntity().getUniqueId());
			}
		}, plugin);
		new BukkitRunnable(){
			@Override
			public void run() {
				//TODO
			}
		}.runTaskTimerAsynchronously(plugin, 0, 20);
	}
	public boolean inZone(Location location){
		for(Block block: blocks){
			if(block.equals(location.getBlock())){
				return true;
			}
		}
		return false;
	}
}
