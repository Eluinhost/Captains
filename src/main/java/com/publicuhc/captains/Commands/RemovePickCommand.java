package com.publicuhc.captains.Commands;

import com.publicuhc.captains.DraftMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RemovePickCommand extends DraftModeCommand
{
    public RemovePickCommand(DraftMode draftMode)
    {
        super(draftMode);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings)
    {
        //TODO
        return false;
    }
}
