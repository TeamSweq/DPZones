package org.teamsweq.dpzones;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Zone {
	private List<Block> blocks; // Blocks that are possible to be in
	private List<Block> wools; //Wool that changes
	private List<UUID> players; //Players that are in this Zone
	public Zone(){
		blocks = new ArrayList<Block>();
		wools = new ArrayList<Block>();
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
		reset();
	}
	public void setRatio(DyeColor color, double ratio){
		reset();
		for(int i=0;i<=wools.size()*ratio;++i){
			wools.get(i).setType(Material.WOOL);
			wools.get(i).setData(color.getData());
		}
	}
	public void reset(){
		for(Block block: wools){
			block.setType(Material.WOOL);
			block.setData(DyeColor.WHITE.getData());
		}
	}
	public boolean inZone(Location location){
		for(Block block: blocks){
			if(block.equals(location.getBlock())){
				return true;
			}
		}
		return false;
	}
	public List<UUID> getPlayers(){
		return players;
	}
}
