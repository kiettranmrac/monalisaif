import java.util.ArrayList;
import java.util.Scanner;

public class IFGame {

    public Scanner in = new Scanner(System.in);
    public boolean echoOn = false;
    public Location location;
    // Index 0-6 are the locations mainEntrance, junction, guardsRoom, monaLisaRoom, securityCheck, exit respectively
    private ArrayList<Location> allLocations;
    // Items that further need added attributes
    private ArrayList<Item> neededItem;
    private Location inventory;

    public IFGame(boolean echoOn) {
        this.echoOn = echoOn;
        initialize();
        play();
    }

    private void initialize() {
        allLocations = new ArrayList<>();
        neededItem = new ArrayList<>();
        inventory = new Location("Inventory", "");
        Location mainEntrance = new Location("Main Entrance",
                "You are in the main entrance to the museum. There is a metallic door to enter the museum. On the door,"
                + " there is a sign that says 'There are 3 of me in December, one of me in June, and none of me in March. What am I?'"
                + " You see a keyboard and a screen on the door. Seems like you have to 'enter' something.");
        allLocations.add(mainEntrance);
        Location junction = new Location("Junction",
                "You reach the intersection. In front of you are a smoke mask, a smoke bomb, and a guard uniform."
                + " You vaguely see 2 security guards towards the West in the distance.");
        allLocations.add(junction);
        Location guardsRoom = new Location("Guards",
                "There are 2 guards in front of you. They are talking about the new security system that was installed."
                + " They're in their uniform, and one of them is carrying a gun.");
        allLocations.add(guardsRoom);
        Location monaLisaRoom = new Location("Mona Lisa",
                "You are in the Mona Lisa room. The painting is hanging on the wall. It's a lot smaller than you thought it would be.");
        allLocations.add(monaLisaRoom);
        Location securityCheck = new Location("Security Check",
                "You are in the security check room. There is a metal detector. Seems like if you want to go through, you have to drop all your metallic items. But the floor has a special property, if you drop the metallic items on the floor, the metal detector detects it. Seems like you have to 'use' the trash can.");
        allLocations.add(securityCheck);
        Location exit = new Location("Exit",
                "You are in the exit. You see a door that leads to the outside. However, the door is locked. On the wall,"
                + " there is a fire alarm.");
        allLocations.add(exit);
        
        junction.exits.put("w", guardsRoom);
        junction.exits.put("n", mainEntrance);
        guardsRoom.exits.put("e", junction);
        guardsRoom.exits.put("w", monaLisaRoom);
        monaLisaRoom.exits.put("e", guardsRoom);
        monaLisaRoom.exits.put("w", securityCheck);
        securityCheck.exits.put("e", monaLisaRoom);
        securityCheck.exits.put("n", exit);
        exit.exits.put("s", securityCheck);
        
        location = mainEntrance;

        Item keyboard = new Item("keyboard",
                "You see a keyboard. It's a normal keyboard, but the 'e' key is worn out.");
        Item door = new Item("door",
                "You see a metallic door. It's locked, but there is a keyboard next to it. On the door,"
                + " there is a sign that says 'There are 3 of me in December, one of me in June, and none of me in March. What am I?'");
        Item smokeMask = new Item("smoke mask",
                "You see a smoke mask. It's a normal smoke mask, but it's a little stinky, yuck.");
        Item smokeBomb = new Item("smoke bomb",
                "You see a smoke bomb. It's a smoke bomb, it has smoke, in the bomb.");
        Item guardUniform = new Item("guard uniform",
                "You see a guard uniform. It's a guard uniform, but it's a guard uniform.");
        Item guards = new Item("guards",
                "You see 2 guards. One of them is smoking a cigar, while the other is carrying a gun and talking about the new fire alarm system that was installed.");
        Item gun = new Item("gun",
                "You see a gun. It seems to be broken. You don't want to use it.");
        Item monaLisa = new Item("Mona Lisa",
                "You see the Mona Lisa. It's the Mona Lisa, and it's a lot smaller than you thought it would be.");
        Item paintBrush = new Item("paint brush",
                "You see a paint brush. You have no idea why there is a paint brush here.");
        Item metalDetector = new Item("metal detector",
                "You see a metal detector. It's a metal detector, and it's not beeping. Also, it's fixed to the wall. Seems like if you want to go through, you have to drop all your metallic items.");
        Item fireAlarm = new Item("fire alarm",
                "You see a fire alarm. It's a fire alarm, and it's fixed to the wall.");
        Item exitDoor = new Item("exit door",
                "You see an exit door. It's an exit door, and it's locked. You need to find a way to unlock it.");

        mainEntrance.items.add(keyboard);

        mainEntrance.items.add(door);
        door.attributes.add("closed");

        junction.items.add(smokeMask);
        smokeMask.attributes.add("wearable");
        smokeMask.attributes.add("gettable");
        smokeMask.attributes.add("metallic");

        junction.items.add(smokeBomb);
        smokeBomb.attributes.add("gettable");
        smokeMask.attributes.add("metallic");

        junction.items.add(guardUniform);
        guardUniform.attributes.add("wearable");
        guardUniform.attributes.add("gettable");

        guardsRoom.items.add(guards);
        neededItem.add(guards);

        neededItem.add(gun);
        gun.attributes.add("gettable");

        monaLisaRoom.items.add(monaLisa);
        monaLisa.attributes.add("gettable");

        monaLisaRoom.items.add(paintBrush);
        paintBrush.attributes.add("gettable");

        securityCheck.items.add(metalDetector);

        exit.items.add(fireAlarm);
        fireAlarm.attributes.add("unpressed");

        exit.items.add(exitDoor);
        exitDoor.attributes.add("closed");
    }

    private void play() {
        System.out.println("Welcome to the Mona Lisa Heist! Your goal is to steal "
            + "the Mona Lisa from the museum without getting caught. Do you want to play? (y/n)");
        while (true) {
            // Basic start up screen
            String command = in.nextLine().replaceAll("\\s+$", "");
            
            if (echoOn) 
                System.out.println(command);
            
            if (command.matches("(n|no|No|N)")) {
                System.out.println("See you next time!");
                System.exit(0);
            }
            else if (command.matches("(y|Y|Yes|yes)")) {
                break;
            }
            else {
                System.out.println("I don't understand what you're saying.");
            }
        }
        System.out.println("Basic commands: go, look, examine, take, drop, help, inventory, restart, quit");
        while (true) {
            System.out.println(location);
            System.out.print("? ");
            String command = in.nextLine().replaceAll("\\s+$", "");
            // Echo the command if echoOn is true
            if (echoOn) {
                System.out.println(command);
            }
            // Below are the commands that are specific to the game
            // Quit the game
            if (command.equals("quit")) {
                System.exit(0);
            }
            // Restart the game
            else if (command.equals("restart")) {
                while (true) {
                    System.out.println("Reset the game? (y/n)");
                    String answer = in.nextLine();
                    if (echoOn)
                        System.out.println(answer);
                    if (answer.matches("(y|Y|Yes|yes)")) {
                        System.out.println("Restarting...");
                        initialize();
                        break;
                    }
                    else if (answer.matches("(n|no|No|N)")) {
                        System.out.println("Back to stealing the Mona Lisa!");
                        break;
                    }
                    System.out.println("I don't understand what you're saying.");
                }
            }
            // Help for basic commands
            else if (command.equals("help")) {
                System.out.println("Basic commands: go, look, examine, take, drop, help, inventory, restart, quit");
            }
            // Look around
            else if (command.matches("(look|look around|look at the surroundings)")) {
                location.isVisited = false;
            }
            // Show inventory
            else if (command.matches("i|inventory")) {
                System.out.println(inventory);
            }
            // Go to a location
            else if (command.matches("(go )?(?i)(n|w|e|s|north|west|east|south)") || command.matches("(n|w|e|s|north|west|east|south)")) {
                if (command.startsWith("go "))
                    command = command.substring(3);
                if (command.length() > 1)
                    command = command.substring(0, 1);
                
                Location nextLocation = location.exits.get(command);
                boolean isBadEnding = false;
                // Situation: you try to get past the guards and go to the room with the Mona Lisa
                if (location.name.equals("Guards") && command.equals("w")) {
                    boolean isSmokeUsed = false;
                    boolean hasUniformOn = false;
                    for (Item item : location.items) {
                        if (item.name.equals("guards") && item.attributes.contains("unconscious")) {
                            isSmokeUsed = true;
                            break;
                        }
                    }
                    for (Item item : inventory.items) {
                        if (item.name.equals("guard uniform")) {
                            hasUniformOn = true;
                            break;
                        }
                    }
                    if (hasUniformOn && !isSmokeUsed) {
                        System.out.println("You have the guard uniform on. You tried to get past the guards, but they shot you because there is only 2 guards in the museum.");
                        System.out.println("You lost (Bad Ending 1/10).");
                        isBadEnding = true;
                        System.out.println("Restarting...");
                        initialize();
                    }
                    else if (!isSmokeUsed) {
                        System.out.println("You tried to get past the guards, but they caught you.");
                        System.out.println("You lost (Bad Ending 2/10).");
                        isBadEnding = true;
                        System.out.println("Restarting...");
                        initialize();
                    }
                }
                else if (location.name.equals("Security Check") && command.equals("w")) {
                    for (Item item : inventory.items) {
                        if (item.attributes.contains("metallic")) {
                            System.out.println("You tried to go through the metal detector, but it detected your metallic items and the door slam shut. The police came later and arrested you.");
                            System.out.println("You lost (Bad Ending 7/10).");
                            isBadEnding = true;
                            System.out.println("Restarting...");
                            initialize();
                        }
                    }
                }
                else if (location.name.equals("Exit") && command.equals("n")) {
                    boolean hasMonaLisa = false;
                    boolean isWearingUniform = false;
                    for (Item item : inventory.items) {
                        if (item.name.equals("Mona Lisa"))
                            hasMonaLisa = true;
                        if (item.name.equals("guard uniform"))
                            isWearingUniform = true;
                    }

                    if (hasMonaLisa && isWearingUniform) {
                        System.out.println("You were able to escape with the Mona Lisa. The police was"
                                + " already outside but you were wearing a guard uniform so they didn't catch you. You went ahead and sold it"
                                + " on Ebay and got $1 because people thought that it was fake. Hooray!");
                        System.out.println("You won (Good Ending).");
                        while (true) {
                            System.out.println("Reset the game? (y/n)");
                            String answer = in.nextLine();
                            if (echoOn)
                                System.out.println(answer);
                            if (answer.matches("(y|Y|Yes|yes)")) {
                                System.out.println("Restarting...");
                                initialize();
                                isBadEnding = true;
                                break;
                            }
                            else if (answer.matches("(n|no|No|N)")) {
                                System.out.println("See you later!");
                                System.exit(0);
                            }
                            System.out.println("I don't understand what you're saying.");
                        }
                    }
                    else if (!hasMonaLisa) {
                        System.out.println("You were able to escape but you don't have the Mona Lisa!");
                        System.out.println("You lost (Bad Ending 9/10).");
                        isBadEnding = true;
                        System.out.println("Restarting...");
                        initialize();
                    }
                    else if (!isWearingUniform) {
                        System.out.println("You were able to escape. You don't have your uniform on and the police was already outside and they caught you.");
                        System.out.println("You lost (Bad Ending 10/10).");
                        isBadEnding = true;
                        System.out.println("Restarting...");
                        initialize();
                    }
                }
                // Go to location like normal
                if (!isBadEnding) {
                    if (nextLocation == null)
                        System.out.println("You can't go that way.");
                    else
                        location = nextLocation;
                }
            }
            // Examine an item
            else if (command.matches("(examine|x|Examine|X) (\\w*|\\w+\\s\\w+)")) {
                if (command.startsWith("examine ") || command.startsWith("Examine "))
                    command = command.substring(8);
                else
                    command = command.substring(2);

                boolean isItemFound = false;

                for (Item c : location.items) {
                    if (command.equals(c.name)) {
                        System.out.println(c.description);
                        isItemFound = true;
                        break;
                    }
                }
                
                if (!isItemFound) {
                    for (Item c : inventory.items) {
                        if (command.equals(c.name)) {
                            System.out.println(c.description);
                            isItemFound = true;
                            break;
                        }
                    }
                }

                if (!isItemFound){
                    System.out.println("You don't see that.");
                }
            }
            // Take an item
            else if (command.matches("(get|pick up|take) (\\w*|\\w+\\s\\w+)")) {
                if (command.startsWith("get "))
                    command = command.substring(4);
                else if (command.startsWith("pick up "))
                    command = command.substring(7);
                else
                    command = command.substring(5);

                boolean isItemTaken = false;

                for (Item c : location.items) {
                    if (command.equals(c.name)) {
                        isItemTaken = true;
                        if (c.attributes.contains("gettable")) {
                            inventory.items.add(c);
                            location.items.remove(c);
                            System.out.println("You took " + c.name + ".");
                            break;
                        }
                        else {
                            System.out.println("You can't take that.");
                            break;
                        }
                    }
                }

                // Situation: you have the Mona Lisa and the paint brush
                boolean hasBrush = false;
                boolean hasMonaLisa = false;
                for (Item c : inventory.items) {
                    if (c.name.equals("paint brush")) {
                        hasBrush = true;
                    }
                    if (c.name.equals("Mona Lisa")) {
                        hasMonaLisa = true;
                    }
                }
                if (hasBrush && hasMonaLisa) {
                    System.out.println("The paint brush has paint on the tip, and when you went to pick it up, it destroyed the Mona Lisa.");
                    System.out.println("You lost (Bad Ending 5/10).");
                    System.out.println("Restarting...");
                    initialize();
                }
                if (!isItemTaken)
                    System.out.println("You don't see that.");                
            }
            // Drop an item
            else if (command.matches("(drop|release|put down) (\\w*|\\w+\\s\\w+)")) {
                if (command.startsWith("drop "))
                    command = command.substring(5);
                else if (command.startsWith("put down "))
                    command = command.substring(9);
                else
                    command = command.substring(7);

                boolean isItemDropped = false;
                boolean isMetallic = false;

                for (Item c : inventory.items) {
                    if (command.equals(c.name)) {
                        location.items.add(c);
                        inventory.items.remove(c);
                        isItemDropped = true;
                        System.out.println("You dropped " + c.name + ".");
                        if (command.matches("((guard )?uniform|(smoke )?mask)")){
                            if (c.attributes.contains("worn"))
                                c.attributes.remove("worn");
                        }
                        if (c.attributes.contains("metallic"))
                            isMetallic = true;
                        break;
                    }
                }

                if (location.name.equals("Security Check") && isMetallic) {
                    System.out.println("The floor detected metallic item was dropped and the door slam shut. The police came later and arrested you.");
                    System.out.println("You lost (Bad Ending 8/10).");
                    System.out.println("Restarting...");
                    initialize();
                }

                if (!isItemDropped)
                    System.out.println("You don't have that.");     
            }
            // Below are the commands that are specific to the game
            // When input e, the door opens
            else if (command.matches("(type|enter|input) (\\w*|\\w+\\s\\w+)")) {
                if (command.startsWith("type "))
                    command = command.substring(5);
                else
                    command = command.substring(6);

                if (location.name.equals("Main Entrance") && command.equals("e")) {
                    for (Item item : location.items) {
                        if (item.name.equals("door") && item.attributes.contains("closed")) {
                                System.out.println("You entered 'e'. The door opened.");
                                item.attributes.add("opened");
                                item.description = "You see an opened metallic door.";
                                item.attributes.remove("closed");
                                location.exits.put("s", allLocations.get(1));
                        }
                        else if (item.name.equals("door"))
                            System.out.println("The door is already opened.");
                    }
                }
                else if (!location.name.equals("Main Entrance")) {
                    System.out.println("There is nothing to type on.");
                }
                else if (!command.equals("e")) {
                    System.out.println("You heard a loud rejecting noise coming from the door, and the door was still locked. Seems like you have to try again.");
                }
            }
            // When you use the smoke bomb, it goes off, and
            //  - If you are not wearing a mask, you sufficate to dead
            //  - If you are wearing a mask, you are protected
            //      - If you are in the guards room, the guards sufficate to dead
            //      - If you are not in the guards room, you lose
            else if (command.matches("(use|throw|activate|break|touch|activate|push|punch) (smoke )?bomb")) {
                boolean hasSmokeBomb = false;
                boolean hasSmokeMask = false;
                boolean isBadEnding = false;
                for (Item item1 : inventory.items) {
                    if (item1.name.equals("smoke bomb")) {
                        hasSmokeBomb = true;
                        for (Item item2 : inventory.items) {
                            if (item2.name.equals("smoke mask")) {
                                hasSmokeMask = true;
                                if (item2.attributes.contains("worn"))
                                    System.out.println("You used the smoke bomb. The smoke bomb went off, and you were protected by the smoke mask.");
                                else {
                                    System.out.println("You used the smoke bomb. The smoke bomb went off, but you don't have protection so you sufficated to dead.");
                                    System.out.println("You lost (Bad Ending 3/10).");
                                    isBadEnding = true;
                                    System.out.println("Restarting...");
                                    initialize();
                                }
                            }
                        }
                        if (!hasSmokeMask) {
                            System.out.println("You used the smoke bomb. The smoke bomb went off, but you don't have protection so you sufficated to dead.");
                            System.out.println("You lost (Bad Ending 3/10).");
                            isBadEnding = true;
                            System.out.println("Restarting...");
                            initialize();
                        }
                        else if (!isBadEnding) {
                            if (location.name.equals("Guards")) {
                                System.out.println("Additionally, the guards were sufficated by the smoke. They dropped a gun");
                                neededItem.get(0).attributes.add("unconscious");
                                neededItem.get(0).description = "The guards are now unconscious.";
                                location.items.add(neededItem.get(1));
                                for (Item c : inventory.items) {
                                    if (c.name.equals("guards")) {
                                        c.attributes.add("unconscious");
                                        break;
                                    }
                                }     
                            }
                            else {
                                System.out.println("It went off in a different location and didn't affect the guards. You have no idea how to get pass the guards now so you weren't able to steal the Mona Lisa this time.");
                                System.out.println("You lost (Bad Ending 4/10).");
                                System.out.println("Restarting...");
                                initialize();
                            }
                        }
                    }
                }
                if (!hasSmokeBomb)
                    System.out.println("You don't have a smoke bomb.");
            }
            // Use gun and explodes
            else if (command.matches("(use|throw|trigger|break|activate|push|punch) gun")) {
                boolean hasGun = false;
                for (Item item : inventory.items) {
                    if (item.name.equals("gun")) {
                        hasGun = true;
                        System.out.println("You used the gun. The gun malfuntioned, then it exploded and you died.");
                        System.out.println("You lost (Bad Ending 6/10).");
                        System.out.println("Restarting...");
                        initialize();
                    }
                }
                if (!hasGun)
                    System.out.println("You don't have a gun.");
            }
            // Throw all metallic items into trash can
            // else if (command.matches("(use|put into|place into|dump into|throw all into|throw all) (trash )?can")) {
            //     boolean hasMetallic = false;
            //     for (Item item : inventory.items) {
            //         if (item.attributes.contains("metallic")) {
            //             // inventory.items.remove(item);
            //             hasMetallic = true;
            //             System.out.println("You drop all your metallic items into the trash can.");
            //         }
            //     }
            //     if (!hasMetallic)
            //         System.out.println("You don't have any metallic item.");
            // }
            // Put on smoke mask or uniform
            else if (command.matches("(wear|put on) ((guard )?uniform|(smoke )?mask)")) {
                if (command.startsWith("wear "))
                    command = command.substring(5);
                else
                    command = command.substring(7);
                
                boolean maskWorn = false;
                boolean uniformWorn = false;

                for (Item item : inventory.items) {
                    if (item.name.equals("guard uniform") && command.matches("(guard )?uniform")) {
                        if (item.attributes.contains("worn")) {
                            System.out.println("You already have that on.");
                            uniformWorn = true;
                            break;
                        }
                        else { 
                            System.out.println("You put on the guard uniform. You look like a guard now.");
                            item.attributes.add("worn");
                            uniformWorn = true;
                            break;
                        }
                    }
                    else if (item.name.equals("smoke mask") && command.matches("(smoke )?mask")) {
                        if (item.attributes.contains("worn")) {
                            System.out.println("You already have that on.");
                            maskWorn = true;
                            break;
                        }
                        else { 
                            System.out.println("You put on the smoke mask. You are protected from the smoke now.");
                            item.attributes.add("worn");
                            maskWorn = true;
                            break;
                        }
                    }
                }
                if (!command.matches("(guard )?uniform") && !command.matches("(smoke )?mask")) {
                    System.out.println("You can't wear that.");
                }
                if (command.matches("(guard )?uniform")) {
                    if (!uniformWorn)
                        System.out.println("You don't have a guard uniform.");
                }
                else {
                    if (!maskWorn)
                        System.out.println("You don't have a smoke mask.");
                }
            }
            // Press the fire alarm and you win!
            else if (command.matches("(start|press|break|touch|activate|push|punch) (fire )?alarm")) {
                if (location.name.equals("Exit")) {
                    System.out.println("The alarm was activated. With that, the door opened.");
                    location.exits.put("n", allLocations.get(5)); // doesnt matter where it goes
                    for (Item item : location.items) {
                        if (item.name.equals("exit door")) {
                            if (item.attributes.contains("closed")) {
                                item.attributes.add("opened");
                                item.description = "You see an opened exit door.";
                                item.attributes.remove("closed");
                            }
                            else
                                System.out.println("The door is already opened.");
                        }
                        else if (item.name.equals("fire alarm")) {
                            if (item.attributes.contains("unpressed")) {
                                item.attributes.add("pressed");
                                item.description = "The alarm was pressed.";
                                item.attributes.remove("unpressed");
                            }
                            else
                                System.out.println("The alarm is already pressed.");
                        }
                    }
                }
                else 
                    System.out.println("There is no fire alarm.");
            }
            // Do nothing when command is jibberirsh
            else {
                System.out.println("I don't understand what you're saying.");
            }
        }
    }

    public static void main(String[] args) {
        new IFGame(args.length > 0 && args[0].equals("-e"));
    }

}