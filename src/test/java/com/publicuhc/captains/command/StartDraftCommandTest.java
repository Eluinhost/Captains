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
    private Player ghowden;

    @SuppressWarnings("deprecation")
    @Before
    public void onStartup()
    {
        draftMode = mock(DraftMode.class);
        command = new StartDraftCommand(draftMode);

        pluginCommand = mock(Command.class);
        when(pluginCommand.getName()).thenReturn("startdraft");

        mockStatic(Bukkit.class);
        ghowden = mock(Player.class);
        when(Bukkit.getPlayer("ghowden")).thenReturn(ghowden);
        when(Bukkit.getPlayer("Eluinhost")).thenReturn(null);

        sender = mock(Player.class);
        when(sender.hasPermission("captains.draft.startdraft")).thenReturn(true);
    }

    //TODO test for -f and -t=team_size

    //TODO test for available players sizes

    //TODO test for random player removal

    //TODO test valid command

    @Test
    public void testInvalidCaptainName()
    {
        command.onCommand(sender, pluginCommand, "", new String[]{"ghowden", "Eluinhost"});
        command.onCommand(sender, pluginCommand, "", new String[]{"Eluinhost", "ghowden"});

        verify(sender, times(2)).sendMessage(contains("provide at least 2 captains"));
    }

    @Test
    public void testNotEnoughCaptains()
    {
        command.onCommand(sender, pluginCommand, "", new String[]{});
        command.onCommand(sender, pluginCommand, "", new String[]{"ghowden"});

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
        command.onCommand(sender, pluginCommand, "", new String[]{});

        verify(sender, times(1)).hasPermission("captains.draft.startdraft");

        verify(sender, times(1)).sendMessage(contains("do not have permission"));
        verifyNoMoreInteractions(sender);
        verifyNoMoreInteractions(draftMode);
    }

    @Test
    public void testAvailableForPick()
    {
        mockStatic(Bukkit.class);

        Player ok_player_1 = mock(Player.class);
        when(ok_player_1.hasPermission("captains.draft.spectate")).thenReturn(false);
        Player ok_player_2 = mock(Player.class);
        when(ok_player_2.hasPermission("captains.draft.spectate")).thenReturn(false);
        Player ok_player_3 = mock(Player.class);
        when(ok_player_3.hasPermission("captains.draft.spectate")).thenReturn(false);
        Player ok_player_4 = mock(Player.class);
        when(ok_player_4.hasPermission("captains.draft.spectate")).thenReturn(false);
        Player not_ok_player_5 = mock(Player.class);

        when(not_ok_player_5.hasPermission("captains.draft.spectate")).thenReturn(true);
        Player not_ok_player_6 = mock(Player.class);
        when(not_ok_player_6.hasPermission("captains.draft.spectate")).thenReturn(true);

        Player[] playerArray = new Player[]{ok_player_1, ok_player_2, ok_player_3, ok_player_4, not_ok_player_5, not_ok_player_6};

        when(Bukkit.getOnlinePlayers()).thenReturn(playerArray);

        List<Player> players = command.availableForPick();

        assertThat(players).containsExactly(ok_player_1, ok_player_2, ok_player_3, ok_player_4);
        assertThat(players).hasSize(4);
        assertThat(players).doesNotContain(not_ok_player_5, not_ok_player_6);

        for(Player player : playerArray) {
            verify(player, times(1)).hasPermission("captains.draft.spectate");
            verifyNoMoreInteractions(ok_player_1);
        }
    }


}
