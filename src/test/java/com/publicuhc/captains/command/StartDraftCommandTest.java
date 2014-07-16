package com.publicuhc.captains.command;

import com.publicuhc.captains.DraftMode;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Bukkit.class})
public class StartDraftCommandTest
{
    private StartDraftCommand command;

    private Player sender;
    private DraftMode draftMode;
    private Command pluginCommand;

    @SuppressWarnings("deprecation")
    @Before
    public void onStartup()
    {
        draftMode = mock(DraftMode.class);
        command = new StartDraftCommand(draftMode);

        pluginCommand = mock(Command.class);
        when(pluginCommand.getName()).thenReturn("startdraft");

        sender = mock(Player.class);
        when(sender.hasPermission("captains.draft.startdraft")).thenReturn(true);
    }

    //TODO test for -f

    /**
     * Makes x amount of player mocks and setups up Bukkit.getPlayer("playerx") and Bukkit.getOnlinePlayers() to return all.
     * Players are named "player" + index (e.g. player0 - player19 for amount 20)
     * @param amount the amount to create
     * @return the created player list
     */
    @SuppressWarnings("deprecation")
    private Player[] setupOnlinePlayers(int amount)
    {
        mockStatic(Bukkit.class);
        Player[] online = new Player[amount];
        for(int i = 0; i<amount; i++) {
            Player player = mock(Player.class);
            when(player.getName()).thenReturn("player"+i);
            when(Bukkit.getPlayer("player"+i)).thenReturn(player);
            online[i] = player;
        }
        when(Bukkit.getOnlinePlayers()).thenReturn(online);

        return online;
    }

    @Test
    public void testNotEnoughPicks()
    {
        //3 of 5 as captains, auto team size
        setupOnlinePlayers(5);
        command.onCommand(sender, pluginCommand, "", new String[]{"player0", "player1", "player2"});

        verify(sender, times(1)).sendMessage(contains("must be at least as many picks"));
    }

    @Test
    public void testTooLargeTeams()
    {
        //setup 14 players, 3 captains teams of 5
        setupOnlinePlayers(14);

        command.onCommand(sender, pluginCommand, "", new String[]{"player0", "player1", "player2", "-t=5"});

        verify(sender, times(1)).sendMessage(contains("enough people online"));
    }

    @Test
    public void testTeamSizeInvalid()
    {
        setupOnlinePlayers(1);

        command.onCommand(sender, pluginCommand, "", new String[]{"player0", "-t=asdjkh"});
        command.onCommand(sender, pluginCommand, "", new String[]{"player0", "-t=0"});
        command.onCommand(sender, pluginCommand, "", new String[]{"player0", "-t=-1"});

        verify(sender, times(3)).sendMessage(contains("provide a valid number"));
    }

    //TODO test for random player removal

    //TODO test valid command

    @Test
    public void testInvalidCaptainName()
    {
        setupOnlinePlayers(1);

        command.onCommand(sender, pluginCommand, "", new String[]{"player0", "player1"});
        command.onCommand(sender, pluginCommand, "", new String[]{"player1", "player0"});

        verify(sender, times(2)).sendMessage(contains("is not online"));
    }

    @Test
    public void testNotEnoughCaptains()
    {
        setupOnlinePlayers(1);

        command.onCommand(sender, pluginCommand, "", new String[]{});
        command.onCommand(sender, pluginCommand, "", new String[]{"player0"});

        verify(sender, times(2)).sendMessage(contains("provide at least 2 captains"));
    }

    @Test
    public void testDraftMode()
    {
        when(draftMode.isInDraftMode()).thenReturn(true);

        command.onCommand(sender, pluginCommand, "", new String[]{});

        verify(sender, times(1)).sendMessage(contains("already in progress"));
        verify(draftMode).isInDraftMode();
        verifyNoMoreInteractions(draftMode);
    }

    @Test
    public void testNoPermission()
    {
        when(sender.hasPermission("captains.draft.startdraft")).thenReturn(false);

        command.onCommand(sender, pluginCommand, "", new String[]{});

        verify(sender, times(1)).hasPermission("captains.draft.startdraft");
        verify(sender, times(1)).sendMessage(contains("do not have permission"));
        verifyNoMoreInteractions(sender);
        verifyNoMoreInteractions(draftMode);
    }

    @Test
    public void testAvailableForPick()
    {
        Player[] players = setupOnlinePlayers(6);
        for(int i = 0; i < 6; i++) {
            Player player = players[i];
            when(player.hasPermission("captains.draft.spectate")).thenReturn(i > 3);
        }

        List<Player> available = command.availableForPick();

        assertThat(available).containsExactly(players[0], players[1], players[2], players[3]);
        assertThat(available).hasSize(4);
        assertThat(available).doesNotContain(players[4], players[5]);

        for(Player player : players) {
            verify(player, times(1)).hasPermission("captains.draft.spectate");
            verifyNoMoreInteractions(player);
        }
    }


}
