package com.publicuhc.captains.command;

import com.publicuhc.captains.DraftMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemovePickCommand extends DraftModeCommand
{
    public RemovePickCommand(DraftMode draftMode)
    {
        super(draftMode);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!sender.hasPermission("captains.draft.removepick")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }

        if(!draftMode.isInDraftMode()) {
            sender.sendMessage(ChatColor.RED + "Draft mode is not in progress!");
            return true;
        }

        if(args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Must provide a player name to remove");
            return true;
        }

        boolean removed = draftMode.removePick(args[0]);

        if(removed) {
            sender.sendMessage(ChatColor.GOLD + "Player added to pick list");
        } else {
            sender.sendMessage(ChatColor.RED + "Player not found in pick list");
        }
        return true;
    }
}
