package com.publicuhc.captains.command;

import com.publicuhc.captains.DraftMode;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class RemovePickCommandTest
{
    private RemovePickCommand command;

    private Player sender;
    private DraftMode draftMode;
    private Command pluginCommand;

    @SuppressWarnings("deprecation")
    @Before
    public void onStartup()
    {
        draftMode = mock(DraftMode.class);
        command = new RemovePickCommand(draftMode);
        when(draftMode.isInDraftMode()).thenReturn(true);

        pluginCommand = mock(Command.class);
        when(pluginCommand.getName()).thenReturn("removepick");

        sender = mock(Player.class);
        when(sender.hasPermission("captains.draft.removepick")).thenReturn(true);
    }

    @Test
    public void testNoPermission()
    {
        when(sender.hasPermission("captains.draft.removepick")).thenReturn(false);

        command.onCommand(sender, pluginCommand, "", new String[]{});

        verify(sender).sendMessage(contains("do not have permission"));
    }

    @Test
    public void testNotInProgress()
    {
        when(draftMode.isInDraftMode()).thenReturn(false);

        command.onCommand(sender, pluginCommand, "", new String[]{});

        verify(sender).sendMessage(contains("not in progress"));
    }

    @Test
    public void testNoNameGiven()
    {
        command.onCommand(sender, pluginCommand, "", new String[]{});
        verify(sender).sendMessage(contains("provide a player name"));
    }

    @Test
    public void testInvalidNameGiven()
    {
        when(draftMode.removePick(anyString())).thenReturn(false);

        command.onCommand(sender, pluginCommand, "", new String[]{"player"});
        verify(sender).sendMessage(contains("not found"));
    }

    @Test
    public void testValid()
    {
        when(draftMode.removePick(anyString())).thenReturn(true);

        command.onCommand(sender, pluginCommand, "", new String[]{"player"});

        verify(sender).sendMessage(contains("added to pick list"));
        verify(draftMode).removePick("player");
    }
}
