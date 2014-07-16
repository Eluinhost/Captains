package com.publicuhc.captains.command;

import com.publicuhc.captains.DraftMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddPickCommand extends DraftModeCommand
{
    public AddPickCommand(DraftMode draftMode)
    {
        super(draftMode);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!sender.hasPermission("captains.draft.addpick")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }

        if(!draftMode.isInDraftMode()) {
            sender.sendMessage(ChatColor.RED + "Draft mode is not in progress!");
            return true;
        }

        if(args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Must provide a player name to add");
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if(null == player) {
            sender.sendMessage(ChatColor.RED + "Player is not online!");
            return true;
        }

        draftMode.addPick(player);
        sender.sendMessage(ChatColor.GOLD + "Player added to pick list");
        return true;
    }
}
