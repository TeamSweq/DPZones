package io.github.teamsweq.dpzonesv2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

public class Teams {
	private static Map<DyeColor, List<UUID>> players;
	static{
		players = new HashMap<DyeColor, List<UUID>>();
	}
	private Teams(){}
	
	/**
	 * @param team Color of Team
	 * @return Old Team - Null if none
	 */
	public static DyeColor assignTeam(DyeColor team, Player player){
		DyeColor oldTeam = getTeam(player.getUniqueId());
		if(oldTeam!=null){
			removePlayerFromTeam(oldTeam, player);
		}
		List<UUID> players = Teams.players.get(team);
		if(players==null){
			players = new ArrayList<UUID>();
		}
		players.add(player.getUniqueId());
		Teams.players.put(team, players);
		return oldTeam;
	}
	private static void removePlayerFromTeam(DyeColor team, Player player){
		List<UUID> players = Teams.players.get(team);
		players.remove(player.getUniqueId());
		Teams.players.put(team, players);
	}
	/**
	 * 
	 * @param uuid Player
	 * @return The Team the Player is on - Null if not on a team
	 */
	public static DyeColor getTeam(UUID uuid){
		if(uuid==null){
			return null;
		}
		for(DyeColor team: players.keySet()){
			for(UUID player: players.get(team)){
				if(player.equals(uuid)){
					return team;
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * @param team Color of Team
	 * @return Team Size - -1 If Not Exist
	 */
	public static int getTeamSize(DyeColor team){
		List<UUID> players = Teams.players.get(team);
		if(players!=null){
			return players.size();
		}
		return -1;
	}
	/**
	 * 
	 * @param uuid Player
	 * @return Whether the Player is in a Team
	 */
	public static boolean inTeam(UUID uuid){
		return getTeam(uuid)!=null;
	}
}
