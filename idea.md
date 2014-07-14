# Draft Mode

Given the players: `[a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x, y, z] (26)`

We choose X number of captains from the list (can be randomized, cannot be greater than 50% of players): `[a, j, l, o, r] (5)`

That leaves the following playes as picks: `[b, c, d, e, f, g, h, i, k, m, n, p, q, s, t, u, v, x, y, z] (21)`

We need to know how many we want in a team (5) and we want even teams (5 teams of 5) so a player must be removed (either automatically or pre-command):
`[b, c, d, e, f, g, h, i, k, m, n, p, q, t, u, v, x, y, z] (20)`

The captains then choose 1 player from the pick list to be on their team in the following order: `a -> j -> l -> o -> r`

It then repeats in reverse order: `r -> o -> l -> j -> a`

It then continues in forward->backward picks until no players are left on the pick list or all teams are full.

End draft stage.

_must be able to handle/work out player disconnects (timeout player and pull in any spare players not in the pick list?)_

# Commands

## Enter Draft

Command to enter the 'draft' mode

### Requirements

- A list of captain names
- Team size (optional -> choose largest possible)
- Whether to remove random people if not even (optional -> fail command with message)

### Effects

Allows draft commands for captains and starts draft mode prompts.

## Pick player

Command to pick a player from the pick list

### Requirements

- Player to pick (must be in pick list, can be not online, if considered offline at the end of the pick gets a replacement pick)