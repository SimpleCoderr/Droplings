package me.nodemoteplesk.droplings;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.nodemoteplesk.droplings.commands.AutoSmelt;
import me.nodemoteplesk.droplings.commands.Drops;
import me.nodemoteplesk.droplings.commands.DroplingsCommand;
import me.nodemoteplesk.droplings.events.EventListener;
import me.nodemoteplesk.droplings.util.DebugManager;
import me.nodemoteplesk.droplings.util.HoloAPI;
import me.nodemoteplesk.droplings.util.MessageUtil;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Droplings
  extends JavaPlugin
{
  public static String fullInv;
  public static String world;
  private static Droplings plugin;
  public static List<String> allowedWorlds;
  public static List<UUID> playerSmelt = new ArrayList();
  public static List<UUID> playerDrops = new ArrayList();
  
  public void onEnable()
  {
    plugin = this;
    
    saveDefaultConfig();
    loadConfig();
    
    getCommand("autosmelt").setExecutor(new AutoSmelt(this));
    getCommand("drops").setExecutor(new Drops(this));
    getCommand("droplings").setExecutor(new DroplingsCommand(this));
    getServer().getPluginManager().registerEvents(new EventListener(), this);
    
    getServer().getScheduler().runTaskTimer(this, new Runnable()
    {
      public void run()
      {
        HoloAPI.clearOldHolograms();
      }
    }, 10L, 10L);
  }
  
  public void onDisable()
  {
    getServer().getScheduler().cancelTasks(this);
    HoloAPI.clearHolograms();
    MessageUtil.clearLastMessages();
    DebugManager.clearDebuggers();
    if (allowedWorlds != null) {
      allowedWorlds.clear();
    }
    playerSmelt.clear();
    playerDrops.clear();
    
    plugin = null;
  }
  
  public void loadConfig()
  {
    allowedWorlds = getConfig().getStringList("allowedWorlds");
    fullInv = getConfig().getString("messages.fullinv");
    world = getConfig().getString("world");
  }
  
  public static Droplings getInstance()
  {
    return plugin;
  }
}

