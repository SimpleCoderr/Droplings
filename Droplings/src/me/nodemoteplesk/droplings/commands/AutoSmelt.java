package me.nodemoteplesk.droplings.commands;

import java.util.List;
import me.nodemoteplesk.droplings.Droplings;
import me.nodemoteplesk.droplings.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class AutoSmelt
  implements CommandExecutor
{
  private Droplings plugin;
  
  public AutoSmelt(Droplings plugin)
  {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    if (commandLabel.equalsIgnoreCase("autosmelt"))
    {
      if (!(sender instanceof Player))
      {
        sender.sendMessage("Command isn't runnable by console.");
        return true;
      }
      Player p = (Player)sender;
      if (p.hasPermission("droplings.autosmelt"))
      {
        if (args.length == 1)
        {
          if (args[0].equalsIgnoreCase("on"))
          {
            if (!Droplings.playerSmelt.contains(p.getUniqueId()))
            {
              Droplings.playerSmelt.add(p.getUniqueId());
              p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.smelton")));
            }
            else
            {
              p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.smeltison")));
            }
          }
          else if (args[0].equalsIgnoreCase("off")) {
            if (Droplings.playerSmelt.contains(p.getUniqueId()))
            {
              Droplings.playerSmelt.remove(p.getUniqueId());
              p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.smeltoff")));
            }
            else
            {
              p.sendMessage(MessageUtil.replace(this.plugin.getConfig().getString("messages.smeltisoff")));
            }
          }
        }
        else
        {
          List<String> msgs = this.plugin.getConfig().getStringList("messages.help-smelt");
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

