package com.publicuhc.captains.command;

import com.publicuhc.captains.DraftMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StartDraftCommand extends DraftModeCommand
{
    public StartDraftCommand(DraftMode draftMode)
    {
        super(draftMode);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!sender.hasPermission("captains.draft.startdraft")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }

        if(draftMode.isInDraftMode()) {
            sender.sendMessage(ChatColor.RED + "Draft mode is already in progress.");
            return true;
        }

        int teamSize = -1;
        boolean addAll = false;
        List<Player> captainList = new ArrayList<Player>();

        for(String arg : args) {

            //check for `-f`
            if(arg.equalsIgnoreCase("-f")) {
                addAll = true;
                continue;
            }

            //check for `-t=team_size`
            if(arg.startsWith("-t=")) {
                //remove the start stuffs
                arg = arg.substring(3);

                //read in the team size
                try {
                    teamSize = Integer.parseInt(arg);

                    //check for positive > 0 value
                    if(teamSize < 1)
                        throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    sender.sendMessage(ChatColor.RED + "Must provide a valid number for the team size");
                    return true;
                }
            }

            //must be a player (captain) name
            @SuppressWarnings("deprecation") Player player = Bukkit.getPlayer(arg);

            if(null == player) {
                sender.sendMessage(ChatColor.RED + "Player "+arg+" is not online!");
                return true;
            }
        }

        if(captainList.size() < 2) {
            sender.sendMessage(ChatColor.RED + "Must provide at least 2 captains");
            return true;
        }

        List<Player> availablePlayers = availableForPick();

        //TODO remove captains from available players

        //TODO check if team size is -1 and auto set it

        //TODO start draft mode with the provided captains and player list

        draftMode.startDraftMode();
        sender.sendMessage(ChatColor.GOLD + "Draft mode started");
        return true;
    }

    /**
     * @return List of all online players that are available for pick (without permission captains.draft.spectate)
     */
    protected List<Player> availableForPick()
    {
        List<Player> playerList = new ArrayList();

        Player[] allPlayers = Bukkit.getOnlinePlayers();

        for(Player player : allPlayers) {
            if(!player.hasPermission("captains.draft.spectate")) {
                playerList.add(player);
            }
        }

        return playerList;
    }
}
