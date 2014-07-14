Captains
========

Commands
--------

### Start Draft

Starts the draft stage.

`/startdraft [-t=team_size] [-r] player1 player2 player3 player4 player5`

#### Parameters

`-t=teamsize` - optional, max team size to created. If ommitted attempts to make the largest teams available.

`-r` - optional, if set will remove random players from the pick list to make the pick list the correct size.
e.g. If there are 5 captains and a team size of 5 with 26 people online 1 player will be remove at random from the list.
If ommitted the command will fail and tell you that there are too many players online.

`player1 player2 player3 player4 player5` - List of captains names who will run the draft

#### Relevant Permissions

`captains.draft.start` - Allows use of the command, default OP

`captains.draft.spectate` - Player cannot be picked, chosen as captain and wont count towards player count when starting draft, default false

### Pick Player

Picks a player for your team. Restricted to Captains during their pick in the draft stage

`/pick [player_name]`

#### Parameters

`player_name` - The player to pick, must be in the unpicked list. If ommitted a random unpicked player will be picked.
You can pick players that have gone offline during the draft stage. If the player is still offline at the end of the pick you will be able to choose another pick

#### Relevant Permissions

`captains.draft.spectate` - Player cannot be picked and won't show in the pick list or count towards the pick count, default false

`captains.draft.pick` - Allows usage of the pick command in pick stage, default true

### Pick List

Lists all unpicked players

`/picklist`

#### Relevant Permissions

`captains.draft.picklist` - Allows usage of the command, default true