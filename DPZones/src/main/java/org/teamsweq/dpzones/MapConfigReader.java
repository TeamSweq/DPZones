
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.teamsweq.dpzones.DPZones;

public class MapConfigReader {

    private File configFile;
    private DPZones main;

    private ArrayList<Location> teamSpawns;
    private ArrayList<Location> zones;
    private int timeLimit;
    private int scoreLimit;

    public MapConfigReader(DPZones instance) {
        main = instance;
    }

    public boolean readFile(String filePath) {
//    verifies that the given file exists
        configFile = new File(main.getDataFolder(), filePath);
        if (!configFile.exists()) {
            System.out.println("The file " + main.getDataFolder().getPath() + "\\" + filePath + " doesn't exist!");
            return false;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

//      verifies that the given file has all the needed values
        if (!(config.isConfigurationSection("Team Spawns") && config.isInt("Time Limit") && config.isInt("Score Limit") && config.isInt("Number of Zones") && config.isConfigurationSection("Zone Locations"))) {
            System.out.println("The file " + configFile.getPath() + " is missing one or more of the following: Time Limit, Score Limit, Team Spawns, Number of Zones, Zone Locations.");
            return false;
        }

        timeLimit = config.getInt("Time Limit");
        scoreLimit = config.getInt("Score Limit");

//      loads the team spawn locations into the ArrayList in order
//      NOTE: team spawns should go into the arraylist in the same order as their colors are in the main class
        int numberOfTeams = DPZones.getTeamColors().length;
        teamSpawns = new ArrayList<>(numberOfTeams);
        ConfigurationSection teamSpawnLocations = config.getConfigurationSection("Team Spawn Locations");
        for (int team = 0;
                team < numberOfTeams; team++) {
            teamSpawns.add(teamSpawnLocations.getVector(team + "").toLocation(main.getServer().getWorlds().get(0)));
        }

        int numberOfZones = config.getInt("Number Of Zones");
        zones = new ArrayList<>(numberOfZones);
        ConfigurationSection zoneLocations = config.getConfigurationSection("Zone Locations");
        for (int zone = 0; zone < numberOfZones; zone++) {
            zones.add(zoneLocations.getVector(zone + "").toLocation(main.getServer().getWorlds().get(0)));
        }
        return true;
    }

    public Location getTeamLocation(int teamID) {
        return teamSpawns.get(teamID);
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public int getScoreLimit() {
        return scoreLimit;
    }
}
