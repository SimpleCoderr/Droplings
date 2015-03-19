package me.nodemoteplesk.droplings.commands;

import java.util.List;
import me.nodemoteplesk.droplings.Droplings;
import me.nodemoteplesk.droplings.util.DebugManager;
import me.nodemoteplesk.droplings.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class DroplingsCommand
  implements CommandExecutor
{
  private Droplings plugin;
  
  public DroplingsCommand(Droplings plugin)
  {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    if (commandLabel.equalsIgnoreCase("novadrops"))
    {
      if (!(sender instanceof Player))
      {
        sender.sendMessage("This command can only be run by a player!");
        return true;
      }
      Player p = (Player)sender;
      if (args.length == 1)
      {
        if (args[0].equalsIgnoreCase("reload"))
        {
          if (p.hasPermission("droplings.admin"))
          {
            this.plugin.reloadConfig();
            this.plugin.loadConfig();
            
            p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.reloadedconfig")));
          }
          else
          {
            p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.nopermission")));
          }
        }
        else if (args[0].equalsIgnoreCase("debug"))
        {
          if ((p.hasPermission("droplings.admin")) || (this.plugin.getDescription().getAuthors().contains(p.getName())))
          {
            if (DebugManager.toggleDebugger(p.getUniqueId())) {
              p.sendMessage(ChatColor.AQUA + "You are now debugging droplings.");
            } else {
              p.sendMessage(ChatColor.AQUA + "You are no longer debugging droplings.");
            }
          }
          else {
            p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.nopermission")));
          }
        }
        else if (p.hasPermission("droplings.admin"))
        {
          List<String> msgs = this.plugin.getConfig().getStringList("messages.help");
          for (String msg : msgs) {
            p.sendMessage(MessageUtil.replace(msg));
          }
        }
        else
        {
          p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.nopermission")));
        }
      }
      else if (p.hasPermission("droplings.admin"))
      {
        List<String> msgs = this.plugin.getConfig().getStringList("messages.help");
        for (String msg : msgs) {
          p.sendMessage(MessageUtil.replace(msg));
        }
      }
      else
      {
        p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.nopermission")));
      }
      return true;
    }
    return false;
  }
}


