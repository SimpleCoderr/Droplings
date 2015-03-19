package me.nodemoteplesk.droplings.commands;

import java.util.List;
import me.nodemoteplesk.droplings.Droplings;
import me.nodemoteplesk.droplings.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Drops
  implements CommandExecutor
{
  private Droplings plugin;
  
  public Drops(Droplings plugin)
  {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    if (commandLabel.equalsIgnoreCase("drops"))
    {
      if (!(sender instanceof Player))
      {
        sender.sendMessage("This command can only be run by a player!");
        return true;
      }
      Player p = (Player)sender;
      if (p.hasPermission("Droplings.drops"))
      {
        if (args.length == 1)
        {
          if (args[0].equalsIgnoreCase("on"))
          {
            if (!Droplings.playerDrops.contains(p.getUniqueId()))
            {
              Droplings.playerDrops.add(p.getUniqueId());
              p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.dropson")));
            }
            else
            {
              p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.dropsison")));
            }
          }
          else if (args[0].equalsIgnoreCase("off"))
          {
            if (Droplings.playerDrops.contains(p.getUniqueId()))
            {
              Droplings.playerDrops.remove(p.getUniqueId());
              p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.dropsoff")));
            }
            else
            {
              p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.dropsisoff")));
            }
          }
          else
          {
            List<String> msgs = this.plugin.getConfig().getStringList("messages.help-drops");
            for (String msg : msgs) {
              p.sendMessage(MessageUtil.replace(msg));
            }
          }
        }
        else
        {
          List<String> msgs = this.plugin.getConfig().getStringList("messages.help-drops");
          for (String msg : msgs) {
            p.sendMessage(MessageUtil.replace(msg));
          }
        }
      }
      else {
        p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.nopermission")));
      }
      return true;
    }
    return false;
  }
}

