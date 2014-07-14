package com.publicuhc.captains.command;

import com.publicuhc.captains.DraftMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Bukkit.class})
public class StartDraftCommandTest
{
    private StartDraftCommand command;

    private DraftMode draftMode;
    private Command pluginCommand;

    @Before
    public void onStartup()
    {
        draftMode = mock(DraftMode.class);
        command = new StartDraftCommand(draftMode);

        pluginCommand = mock(Command.class);
        when(pluginCommand.getName()).thenReturn("startdraft");
    }

    @Test
    public void testPermission()
    {
        Player player = mock(Player.class);
        when(player.hasPermission("captains.draft.startdraft")).thenReturn(true);

        //hacky little thing to exit after perm check
        when(draftMode.isInDraftMode()).thenThrow(new IllegalStateException());
        try {
            command.onCommand(player, pluginCommand, "", new String[]{});
        }catch(IllegalStateException ignored) {}

        verify(player, times(1)).hasPermission("captains.draft.startdraft");

        verify(player, never()).sendMessage(startsWith(ChatColor.RED.toString()));
    }

    @Test
    public void testNoPermission()
    {
        Player player = mock(Player.class);
        when(player.hasPermission("captains.draft.startdraft")).thenReturn(false);

        command.onCommand(player, pluginCommand, "", new String[]{});

        verify(player, times(1)).hasPermission("captains.draft.startdraft");

        verify(player, times(1)).sendMessage(startsWith(ChatColor.RED.toString()));
        verifyNoMoreInteractions(player);
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
