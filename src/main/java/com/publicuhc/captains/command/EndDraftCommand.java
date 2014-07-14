package com.publicuhc.captains.command;

import com.publicuhc.captains.DraftMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class EndDraftCommand extends DraftModeCommand
{
    public EndDraftCommand(DraftMode draftMode)
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
