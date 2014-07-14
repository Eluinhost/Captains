package com.publicuhc.captains.command;

import com.publicuhc.captains.DraftMode;
import org.bukkit.command.CommandExecutor;

public abstract class DraftModeCommand implements CommandExecutor
{
    protected DraftMode draftMode;

    public DraftModeCommand(DraftMode draftMode)
    {
        this.draftMode = draftMode;
    }
}
