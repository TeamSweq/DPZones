import stuff (I'm writing this in notepad because I'm at a school computer cut me some slack);

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
      try {
        configFile = new File(main.getDataFolder(), filePath);
      } catch (FileNotFoundException ex) {
        System.out.println("The file " + main.getDataFolder().getPath() + "\\" + filePath + " doesn't exist!");
        ex.printStackTrace();
        return false;
      }
      
      YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
      
//      verifies that the given file has all the needed values
      
      if (!(config.hasConfigurationSection("Team Spawns") && config.hasInt("Time Limit") && config.hasInt("Score Limit") && config.hasInt("Number of Zones") && config.hasConfigurationSection("Zone Locations"))) {
        System.out.println("The file " + configFile.getPath() + " is missing one or more of the following: Time Limit, Score Limit, Team Spawns, Number of Zones, Zone Locations.");
        return false;
      }
      
      timeLimit = config.getInt("Time Limit");
      scoreLimit = config.getInt("Score Limit");
      
//      loads the team spawn locations into the ArrayList in order
//      NOTE: team spawns should go into the arraylist in the same order as their colors are in the main class
      int numberOfTeams = main.teamColors.length;
      teamSpawns = new ArrayList<>(numberOfTeams);
      ConfigurationSection teamSpawnLocations = config.getConfigurationSection("Team Spawn Locations");
      for (int team = 0; team < numberOfTeams; team++) {
        teamSpawns.add(teamSpawnLocations.getLocation(team+""));
      }
      
      int numberOfZones = config.getInt("Number Of Zones");
      zones = new ArrayList<>(numberOfZones);
      ConfigurationSection zoneLocations = config.getConfigurationSection("Zone Locations");
      for (int zone = 0; zone < numberOfZones; zone++) {
        zones.add(zoneLocations.getLocation(zone+""));
      }
    }
    
    public Location getTeamLocation(int teamID) {
      return teamSpawns.get(teamID);
    }
    
    public int getTimeLimit() {
      return timeLimit;
    }
    
    public int getScoreLimit() {
      getScoreLimit();
    }
}
