package com.publicuhc.captains;

import org.bukkit.entity.Player;

import java.util.List;

public class DraftMode
{
    private boolean isInDraftMode = false;

    /**
     * @return whether or not we are in draft mode or not
     */
    public boolean isInDraftMode()
    {
        return isInDraftMode;
    }

    /**
     * Start the draft mode
     *
     * @throws java.lang.IllegalStateException if already in draft mode
     */
    public void startDraftMode(List<Player> captains, List<Player> picks, int teamSize)
    {
        if(isInDraftMode()) {
            throw new IllegalStateException();
        }
        isInDraftMode = true;

        //TODO anything needed to start draft mode
    }

    public void addPick(Player player)
    {
        //TODO
    }

    /**
     * Ends draft mode
     */
    public void endDraftMode()
    {
        //TODO anything we need to force stop draft mode
        isInDraftMode = false;
    }

    public boolean removePick(String name)
    {
        return false;
    }
}
