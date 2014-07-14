package com.publicuhc.captains;

import com.publicuhc.captains.command.*;
import org.bukkit.plugin.java.JavaPlugin;

public class CaptainsPlugin extends JavaPlugin
{
    public void onEnable()
    {
        DraftMode draft = new DraftMode();

        getCommand("startdraft").setExecutor(new StartDraftCommand(draft));
        getCommand("enddraft").setExecutor(new EndDraftCommand(draft));
        getCommand("addpick").setExecutor(new AddPickCommand(draft));
        getCommand("removepick").setExecutor(new RemovePickCommand(draft));
        getCommand("picklist").setExecutor(new PickListCommand(draft));
        getCommand("pick").setExecutor(new PickCommand(draft));
    }
}
