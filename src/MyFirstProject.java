import java.util.*;
import java.util.function.BiConsumer;

class Game {
    // One shared Scanner/Random for the whole class (several Scanners on System.in can swallow input)
    private final Scanner sc = new Scanner(System.in);
    private final Random rd = new Random();

    int hp, maxHp, level, gold;
    long exp;
    public String name = " ", playerRole = " ";
    double magAtk, magDef, phyDef, phyAtk;
    String Mob = " ", currentTurn = " ";
    int currentMobLevel = 0;

    // Equipped gear bonuses (kept separate from base stats so replacing gear is easy)
    double robeMagDef, robePhyDef, bootMagDef, bootPhyDef, staffMagAtk, staffMagPen;
    String equippedRobe = "None", equippedBoots = "None", equippedStaff = "None";

    // Level 1 = 100exp, Level 2 = 119exp, Level 3 = 142exp ... (cumulative total exp)
    // long[] because the top levels are past Integer.MAX_VALUE
    private static final long[] LEVEL_REQ_EXP = new long[101];
    static {
        double base = 100;
        for (int lvl = 1; lvl <= 100; lvl++) {
            LEVEL_REQ_EXP[lvl] = (long) base;
            base *= 1.19209;
        }
    }

    public Game() {
        gameReset();
    }

    public void gameReset() {
        maxHp = 100;
        hp = maxHp;
        level = 1;
        exp = 100;
        gold = 0;
        robeMagDef = robePhyDef = bootMagDef = bootPhyDef = staffMagAtk = staffMagPen = 0;
        equippedRobe = equippedBoots = equippedStaff = "None";
        // Role stats are set again by playerRoleSelection()
    }

    private boolean yes(String s) {
        return s.startsWith("y") || s.startsWith("ok") || s.contains("sure");
    }

    private double totalMagDef() { return Math.min(0.9, magDef + robeMagDef + bootMagDef); }
    private double totalPhyDef() { return Math.min(0.9, phyDef + robePhyDef + bootPhyDef); }

    // ===================== DATA BASE FOR STORE =====================

    String[][] mageRobe = {
            {"Magic Imbued Old Rag", "Linen Vest", "Apprentice's Robe"},
            {"Wizard's Old Cloak( Worn Out)", "Wizard's Cloak(New)", "Magic Testament"},
            {"Netherweave Robe", "Ember-Soul Vestment", "Eclipse Robe"},
            {"Grandmaster's Archmage Vestment", "Crown of Stars Robe", "Veil of the Void"}
    };
    String[][] tierMageRobe = {
            {"Common", "Common", "Common"},
            {"Rare", "Rare", "Rare"},
            {"Epic", "Epic", "Epic"},
            {"Legendary", "Legendary", "Legendary"}
    };
    double[][] mageRobeMagDef = {
            {0.002, 0.004, 0.009},
            {0.01, 0.03, 0.05},
            {0.1, 0.12, 0.14},
            {0.17, 0.18, 0.2}
    };
    double[][] mageRobePhyDef = {
            {0, 0, 0},
            {0.001, 0.005, 0.006},
            {0.007, 0.0085, 0.0095},
            {0.015, 0.03, 0.05}
    };
    double[][] mageRobePrices = {
            {20, 340, 500},
            {630, 1000, 1320},
            {2250, 4300, 7600},
            {15000, 17500, 25000}
    };

    String[][] mageBoot = {
            {"Worn Leather Shoes", "Apprentice's Sandals", "Cloth Boots"},
            {"Scholar's Tread", "Enchanted Boots", "Gilded Silk Shoes"},
            {"Netherweave Striders", "Ember-Soul Boots", "Eclipse Treads"},
            {"Archmage's Walkers", "Crown of Stars Striders", "Void-Treader Boots"}
    };
    String[][] tierMageBoot = tierMageRobe;
    double[][] mageBootMagDef = {
            {0.001, 0.003, 0.006},
            {0.008, 0.02, 0.035},
            {0.06, 0.08, 0.095},
            {0.11, 0.13, 0.15}
    };
    double[][] mageBootPhyDef = {
            {0.001, 0.002, 0.004},
            {0.005, 0.008, 0.012},
            {0.015, 0.022, 0.03},
            {0.04, 0.055, 0.07}
    };
    double[][] mageBootPrices = {
            {15, 280, 420},
            {550, 880, 1150},
            {1900, 3800, 6800},
            {13000, 15500, 22000}
    };

    String[][] mageStaff = {
            {"Splintered Wooden Stick", "Apprentice Wand", "Carved Ash Rod"},
            {"Polished Elm Staff", "Crystal-Tipped Rod", "Runed Oak Staff"},
            {"Netherweave Catalyst", "Ember-Soul Scepter", "Eclipse Greatstaff"},
            {"Grandmaster's Aether Rod", "Crown of Stars Scepter", "Staff of the Void"}
    };
    String[][] tierMageStaff = tierMageRobe;
    double[][] mageStaffMagAtk = {
            {0.005, 0.01, 0.02},
            {0.035, 0.06, 0.09},
            {0.15, 0.18, 0.22},
            {0.28, 0.32, 0.40}
    };
    double[][] mageStaffMagPen = {
            {0.001, 0.002, 0.005},
            {0.008, 0.012, 0.018},
            {0.025, 0.035, 0.05},
            {0.065, 0.08, 0.10}
    };
    double[][] mageStaffPrices = {
            {30, 450, 700},
            {900, 1450, 1900},
            {3100, 5800, 9800},
            {18000, 22000, 32000}
    };

    // ===================== SHOP =====================

    public void Shop() {
        System.out.println("You have opened the shop!");
        while (true) {
            System.out.println("_-----X-----X-----X-----X-----X-----X-----X-----X-----X-----_ ");
            System.out.println("To buy gear for mages, click 1 or type mage");
            System.out.println("To buy gear for tanks, click 2 or type tank");
            System.out.println("To buy gear for swordsmen, click 3 or type swordsman");
            System.out.println("To go back, click 4 or type back");
            System.out.println("To exit the shop, click 101 or type exit");
            String nav = sc.nextLine().toLowerCase().trim();

            if (nav.equals("101") || nav.equals("exit")) {
                System.out.println("You have exited the shop!");
                return;
            } else if (nav.equals("4") || nav.equals("back")) {
                return;
            } else if (nav.equals("1") || nav.contains("mage")) {
                if (mageShop()) return;
            } else if (nav.equals("2") || nav.contains("tank") || nav.equals("3") || nav.contains("sword")) {
                System.out.println("That shop isn't stocked yet. Check back later!");
            } else {
                System.out.println("That's not an option!");
            }
        }
    }

    // returns true if the player wants to leave the whole shop
    private boolean mageShop() {
        System.out.println("You have entered the magic shop aka the shop for mages!");
        while (true) {
            System.out.println("What would you like to buy?");
            System.out.println("> Robes");
            System.out.println("> Boots");
            System.out.println("> Staffs");
            System.out.println("> Back");
            String shopChoice = sc.nextLine().toLowerCase().trim();

            if (shopChoice.equals("101") || shopChoice.equals("exit")) {
                System.out.println("You have exited the shop!");
                return true;
            } else if (shopChoice.contains("back")) {
                return false;
            } else if (shopChoice.contains("robe")) {
                System.out.println("Good choice! Which Robe fits your Fancy and Budget? (Pick by name or by serial number.)");
                boolean leave = browse(mageRobe, tierMageRobe, mageRobePrices,
                        (r, c) -> {
                            System.out.printf("Magic defence = %.2f%%%n", mageRobeMagDef[r][c] * 100);
                            System.out.printf("Physical defence = %.2f%%%n", mageRobePhyDef[r][c] * 100);
                        },
                        (r, c) -> {
                            equippedRobe = mageRobe[r][c];
                            robeMagDef = mageRobeMagDef[r][c];
                            robePhyDef = mageRobePhyDef[r][c];
                        });
                if (leave) return true;
            } else if (shopChoice.contains("boot")) {
                System.out.println("Nothing's better than not touching the ground with your bare feet after all!");
                boolean leave = browse(mageBoot, tierMageBoot, mageBootPrices,
                        (r, c) -> {
                            System.out.printf("Magic defence = %.2f%%%n", mageBootMagDef[r][c] * 100);
                            System.out.printf("Physical defence = %.2f%%%n", mageBootPhyDef[r][c] * 100);
                        },
                        (r, c) -> {
                            equippedBoots = mageBoot[r][c];
                            bootMagDef = mageBootMagDef[r][c];
                            bootPhyDef = mageBootPhyDef[r][c];
                        });
                if (leave) return true;
            } else if (shopChoice.contains("staff")) {
                System.out.println("A mage without a stick is just a person who mumbles.");
                boolean leave = browse(mageStaff, tierMageStaff, mageStaffPrices,
                        (r, c) -> {
                            System.out.printf("Magic attack boost = %.2f%%%n", mageStaffMagAtk[r][c] * 100);
                            System.out.printf("Magic penetration = %.2f%%%n", mageStaffMagPen[r][c] * 100);
                        },
                        (r, c) -> {
                            equippedStaff = mageStaff[r][c];
                            staffMagAtk = mageStaffMagAtk[r][c];
                            staffMagPen = mageStaffMagPen[r][c];
                        });
                if (leave) return true;
            } else {
                System.out.println("We don't sell that here!");
            }
        }
    }

    // One generic browse/buy loop for every category. Returns true if the player left the shop.
    private boolean browse(String[][] names, String[][] tiers, double[][] prices,
                           BiConsumer<Integer, Integer> showStats,
                           BiConsumer<Integer, Integer> equip) {
        int serialNum = 0;
        for (int i = 0; i < names.length; i++) {
            for (int j = 0; j < names[i].length; j++) {
                ++serialNum;
                System.out.println(serialNum + ".  " + names[i][j] + "  ---->  " + prices[i][j] + " Gold.");
            }
        }

        while (true) {
            System.out.println("Your gold: " + gold + ". Pick an item, or type 'back' / 'exit'.");
            String choice = sc.nextLine().toLowerCase().trim();

            if (choice.equals("101") || choice.equals("exit")) {
                System.out.println("You have exited the shop!");
                return true;
            }
            if (choice.equals("back")) return false;

            int[] pick = findItem(choice, names);
            if (pick == null) {
                System.out.println("Not an item in the shop!");
                continue;
            }
            int r = pick[0], c = pick[1];
            System.out.println("Selected \"" + names[r][c] + "\"");
            System.out.println("Price = " + prices[r][c]);
            System.out.println("Tier = " + tiers[r][c]);
            showStats.accept(r, c);

            if (gold < prices[r][c]) {
                System.out.println("You can't afford that yet.");
                continue;
            }
            System.out.printf("Would you like to buy it? You will have %,.1f gold left over%n", gold - prices[r][c]);
            String buyOrNah = sc.nextLine().toLowerCase().trim();
            if (yes(buyOrNah) || buyOrNah.contains("buy")) {
                gold -= (int) prices[r][c];
                equip.accept(r, c);
                System.out.println("You have bought \"" + names[r][c] + "\" and equipped it!");
                System.out.println("You have " + gold + " gold left.");
            } else {
                System.out.println("Purchase cancelled.");
            }
        }
    }

    // Finds an item by serial number or name; returns {row, col} or null
    int[] findItem(String input, String[][] items) {
        if (input.isEmpty()) return null;
        int cols = items[0].length;
        try {
            int n = Integer.parseInt(input);
            if (n >= 1 && n <= items.length * cols) {
                return new int[]{(n - 1) / cols, (n - 1) % cols};
            }
        } catch (NumberFormatException e) {
            // not a number, fall through to name search
        }
        for (int i = 0; i < items.length; i++) {          // exact match first
            for (int j = 0; j < items[i].length; j++) {
                if (items[i][j].toLowerCase().equals(input)) return new int[]{i, j};
            }
        }
        for (int i = 0; i < items.length; i++) {          // then partial match
            for (int j = 0; j < items[i].length; j++) {
                if (items[i][j].toLowerCase().contains(input)) return new int[]{i, j};
            }
        }
        return null;
    }

    // ===================== DATA BASE FOR MOBS =====================

    // Goblin
    double gobHP = 200, gobMagDef = 0.2, gobPhyAtk = 25, gobPhyDef = 0.2, gobMagAtk = 0, gobExp = 50, gobGold = 20;
    String gobAbility1 = "Effortless Swing ", gobUlt = "Desparate All-In", gobBasic = "Basic Slash";
    double gobAbility1DmgMag = 0, gobAbility1DmgPhy = 35, gobUltDmgMag = 0, gobUltDmgPhy = 50, gobBasicDmgMag = 0, gobBasicDmgPhy = 25;

    // Spectre
    double speHP = 225, speMagDef = 0.15, spePhyAtk = 15, spePhyDef = 0.2, speMagAtk = 20, speExp = 100, speGold = 20;
    String speAbility1 = "Spectre's Haunt ", speUlt = "Eye Of The Abyss", speBasic = "Basic Magic Claw";
    double speAbility1DmgMag = 30, speAbility1DmgPhy = 5, speUltDmgMag = 25, speUltDmgPhy = 25, speBasicDmgMag = 20, speBasicDmgPhy = 10;

    // Golem
    double golHP = 350, golMagDef = 0.3, golPhyAtk = 25, golPhyDef = 0.35, golMagAtk = 15, golExp = 75, golGold = 20;
    String golAbility1 = "Heavy Punch ", golUlt = "Heavy Leap Smash", golBasic = "Basic Melee";
    double golAbility1DmgMag = 5, golAbility1DmgPhy = 35, golUltDmgMag = 0, golUltDmgPhy = 50, golBasicDmgMag = 5, golBasicDmgPhy = 25;

    // Current mob
    double mobHP = 0, mobMagDef = 0, mobPhyAtk = 0, mobPhyDef = 0, mobMagAtk = 0, mobExp = 0, mobGold = 0;
    String mobAbility1 = " ", mobUlt = " ", mobBasic = " ";
    double mobAbility1DmgMag = 0, mobAbility1DmgPhy = 0, mobUltDmgMag = 0, mobUltDmgPhy = 0, mobBasicDmgPhy = 0, mobBasicDmgMag = 0;

    // ===================== PLAYER SETUP =====================

    public void playerName() {
        System.out.println("Enter your name. ");
        while (true) {
            System.out.print("> ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Your name can't be empty.");
                continue;
            }
            System.out.println("Do you want to lock this name?");
            String nameLock = sc.nextLine().toLowerCase().trim();
            if (yes(nameLock)) {
                String[][] nameLine = {
                        {name + "? Nice name!", "Hi " + name + "!"},
                        {name + " huh..What a lovely name!", "So your name is \"" + name + "\"? Did I pronounce that right? Nice name btw"},
                        {"I got it! " + name + " Right?", "Fancy name eh?, " + name}
                };
                System.out.println(nameLine[rd.nextInt(3)][rd.nextInt(2)]);
                break;
            } else {
                System.out.println("Okay, choose again.");
            }
        }
    }

    double ability1DmgMag, ability1DmgPhy, ultDmgMag, ultDmgPhy, basicDmgMag, basicDmgPhy;
    String ability1 = " ", ult = " ", Basic = " ";

    public void playerRoleSelection() {
        System.out.println("Pick your role: ");
        System.out.println("Mage:  Magic all around and can hits and chants with a stick");
        System.out.println("Tank: 0 magic but built like a TANK!.. Get it?");
        System.out.println("Swordsman: best of both worlds from tank and mage..");
        while (true) {
            System.out.print("> ");
            String rolePick = sc.nextLine().toLowerCase().trim();

            if (rolePick.contains("mage") || rolePick.contains("magic")) {
                playerRole = "Mage";
                magAtk = 45; phyAtk = 5; magDef = 0.15; phyDef = 0.1;
                ability1 = "Fire Ball";
                ability1DmgMag = 65 + magAtk;  ability1DmgPhy = 0 + phyAtk;
                ult = "Porfyró megaleío";
                ultDmgMag = 80 + magAtk;       ultDmgPhy = 0 + phyAtk;
                Basic = "Basic Attack";
                basicDmgMag = 45 + magAtk;     basicDmgPhy = 5 + phyAtk;
                break;
            } else if (rolePick.contains("tank")) {
                playerRole = "Tank";
                magAtk = 0; phyAtk = 25; magDef = 0.3; phyDef = 0.35;
                ability1 = "Brute Smash";
                ability1DmgPhy = 40 + phyAtk;  ability1DmgMag = 0 + magAtk;
                ult = "The Hand Of Buddha";
                ultDmgPhy = 70 + phyAtk;       ultDmgMag = 0 + magAtk;
                Basic = "Basic Attack";
                basicDmgPhy = 25 + phyAtk;     basicDmgMag = 0 + magAtk;
                break;
            } else if (rolePick.contains("sword")) {
                playerRole = "Swordsman";
                magAtk = 40; phyAtk = 50; magDef = 0.2; phyDef = 0.25;
                ability1 = "Magic Slash";
                ability1DmgMag = 30 + magAtk;  ability1DmgPhy = 35 + phyAtk;
                ult = "Gladius Magicus Geminus";
                ultDmgMag = 45 + magAtk;       ultDmgPhy = 35 + phyAtk;
                Basic = "Basic Attack";
                basicDmgMag = 25 + magAtk;     basicDmgPhy = 25 + phyAtk;
                break;
            } else {
                System.out.println("That's not an available role! Pick again.");
            }
        }
    }

    public void playerStats() {
        System.out.println("x----x---x---x---x---x---x---x---x");
        System.out.println("General Attributes: ");
        System.out.println("HP: " + hp + "/" + maxHp);
        System.out.println("Level: " + level);
        System.out.println("Exp: " + exp);
        System.out.println("Gold: " + gold);
        System.out.println("Role chosen: " + playerRole);
        System.out.println("----------Role Attributes----------");
        System.out.println("Magic Attack: " + magAtk);
        System.out.println("Physical Attack: " + phyAtk);
        System.out.printf("Magic Defense: %.3f%n", totalMagDef());
        System.out.printf("Physical Defense: %.3f%n", totalPhyDef());
        System.out.println("Ability 1: " + ability1);
        System.out.println("Ultimate: " + ult);
        System.out.println("Robe: " + equippedRobe + " | Boots: " + equippedBoots + " | Staff: " + equippedStaff);
    }

    // ===================== LEVELING =====================

    public void checkLevelUp() {
        int newLevel = level;
        while (newLevel < 100 && exp >= LEVEL_REQ_EXP[newLevel + 1]) {
            newLevel++;
        }
        if (newLevel > level) {
            int oldLevel = level;
            level = newLevel;
            System.out.println("LEVEL UP! You are now Level " + newLevel + "!");
            playerStatLevelUp(oldLevel, newLevel);
        }
    }

    public void playerLevel() {
        exp += (long) mobExp;
        System.out.println("You have gained " + (int) mobExp + "xp from the level " + currentMobLevel + " " + Mob + "! Total EXP: " + exp + "xp.");
    }

    // Applies one round of growth for each level actually gained
    public void playerStatLevelUp(int fromLevel, int toLevel) {
        for (int lv = fromLevel + 1; lv <= toLevel; lv++) {
            if (playerRole.equals("Mage")) {
                maxHp += 200;
                magAtk *= 1.5;
                magDef = Math.min(0.7, magDef * 1.02);
                phyAtk *= 1.001;
                phyDef = Math.min(0.7, phyDef * 1.015);
                ability1DmgMag *= 1.5;
                ability1DmgPhy *= 1.002;
                ultDmgMag *= 2;
                ultDmgPhy *= 1.002;
                basicDmgMag *= 1.8;
                basicDmgPhy *= 1.002;
            } else if (playerRole.equals("Tank")) {
                maxHp += 250;
                magAtk *= 1.002;
                magDef = Math.min(0.7, magDef * 1.5);
                phyAtk *= 1.5;
                phyDef = Math.min(0.7, phyDef * 1.5);
                if (lv % 10 == 0) {
                    ability1DmgMag *= 1.002;
                    ability1DmgPhy *= 1.02;
                    ultDmgMag *= 1.002;
                    ultDmgPhy *= 1.2;
                    basicDmgMag *= 1.002;
                    basicDmgPhy *= 1.1;
                }
            } else if (playerRole.equals("Swordsman")) {
                maxHp += 250;
                magAtk *= 1.2;
                magDef = Math.min(0.7, magDef * 1.25);
                phyAtk *= 1.2;
                phyDef = Math.min(0.7, phyDef * 1.5);
                if (lv % 10 == 0) {
                    ability1DmgMag *= 1.25;
                    ability1DmgPhy *= 1.35;
                    ultDmgMag *= 1.5;
                    ultDmgPhy *= 1.5;
                    basicDmgMag *= 1.1;
                    basicDmgPhy *= 1.1;
                }
            }
        }
        hp = maxHp;
        playerStats();
    }

    // ===================== MOBS =====================

    public void randomMob() {
        currentMobLevel = mobLevel();
        int mobNum = 1 + rd.nextInt(3);

        if (mobNum == 1) {
            Mob = "Goblin";
            mobHP = gobHP; mobMagDef = gobMagDef; mobPhyDef = gobPhyDef;
            mobPhyAtk = gobPhyAtk; mobMagAtk = gobMagAtk;
            mobAbility1 = gobAbility1; mobAbility1DmgMag = gobAbility1DmgMag; mobAbility1DmgPhy = gobAbility1DmgPhy;
            mobUlt = gobUlt; mobUltDmgMag = gobUltDmgMag; mobUltDmgPhy = gobUltDmgPhy;
            mobBasic = gobBasic; mobBasicDmgMag = gobBasicDmgMag; mobBasicDmgPhy = gobBasicDmgPhy;
            mobExp = gobExp; mobGold = gobGold;
        } else if (mobNum == 2) {
            Mob = "Spectre";
            mobHP = speHP; mobMagDef = speMagDef; mobPhyDef = spePhyDef;
            mobPhyAtk = spePhyAtk; mobMagAtk = speMagAtk;
            mobAbility1 = speAbility1; mobAbility1DmgMag = speAbility1DmgMag; mobAbility1DmgPhy = speAbility1DmgPhy;
            mobUlt = speUlt; mobUltDmgMag = speUltDmgMag; mobUltDmgPhy = speUltDmgPhy;
            mobBasic = speBasic; mobBasicDmgMag = speBasicDmgMag; mobBasicDmgPhy = speBasicDmgPhy;
            mobExp = speExp; mobGold = speGold;
        } else {
            Mob = "Golem";
            mobHP = golHP; mobMagDef = golMagDef; mobPhyDef = golPhyDef;
            mobPhyAtk = golPhyAtk; mobMagAtk = golMagAtk;
            mobAbility1 = golAbility1; mobAbility1DmgMag = golAbility1DmgMag; mobAbility1DmgPhy = golAbility1DmgPhy;
            mobUlt = golUlt; mobUltDmgMag = golUltDmgMag; mobUltDmgPhy = golUltDmgPhy;
            mobBasic = golBasic; mobBasicDmgMag = golBasicDmgMag; mobBasicDmgPhy = golBasicDmgPhy;
            mobExp = golExp; mobGold = golGold;
        }
    }

    public int mobLevel() {
        if (level >= 1 && level <= 20) return 1 + rd.nextInt(30);
        else if (level > 20 && level <= 40) return 1 + rd.nextInt(50);
        else if (level > 40 && level <= 60) return 1 + rd.nextInt(70);
        else if (level > 60 && level <= 80) return 1 + rd.nextInt(90);
        else if (level > 80 && level <= 100) return 1 + rd.nextInt(100);
        else {
            System.out.println("Error! Leveling mistake!");
            throw new IllegalStateException();
        }
    }

    public void mobStatLevelUp() {
        for (int i = 1; i <= currentMobLevel; i++) {
            mobHP += 15;
            mobMagDef = Math.min(0.95, mobMagDef * 1.0093);
            mobPhyAtk += 0.2;
            mobPhyDef = Math.min(0.95, mobPhyDef * 1.0093);
            mobMagAtk += 0.2;
            mobExp += 100;
            mobGold += 25;
            if (i % 10 == 0) {
                mobAbility1DmgMag *= 1.02;
                mobAbility1DmgPhy *= 1.02;
                mobUltDmgMag *= 1.02;
                mobUltDmgPhy *= 1.02;
                mobBasicDmgMag *= 1.002;
                mobBasicDmgPhy *= 1.002;
            }
        }
    }

    public int randomMobTimer() {
        return (5 + rd.nextInt(5)) * 1000; // 5-9 seconds
    }

    public int Attacker() {
        return 1 + rd.nextInt(2); // 1 = player first, 2 = mob first
    }

    // ===================== COMBAT =====================

    String[][] playerVL = {
            {"You hav' the first hit.", "It doesn't see us. Attack now."},
            {"C'mon! Now Attack!", "Better late than never. Hit it."},
            {"I told you creeping up would work, jeopardize the opportunity! ", "My advice never fails! Kill it in one blow if you can!."}
    };
    String[][] mobVL = {
            {"It saw us, get ready to take a hit", "This is awkward.. it's coming!"},
            {"Why are they so sharp?", "Twats always notice."},
            {"Woo hoo, we have gotten spotted! Dodge!", "Crazy how creeping up never works."}
    };

    int rowsVl = 0, columnsVL = 0;

    public void randomVLVal() {
        rowsVl = rd.nextInt(3);
        columnsVL = rd.nextInt(2);
    }

    public void attackAndFight() {
        boolean attackInSession = true;
        while (attackInSession) {
            randomMob();
            mobStatLevelUp();   // was never called before, so mob levels did nothing
            int mobWait = randomMobTimer();

            System.out.println("You will randomly find the mob..or it will find you..");
            System.out.println("Searching for mob...");
            try {
                Thread.sleep(mobWait);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupted! Skipping the wait.");
            }

            System.out.println("A " + Mob + " of level " + currentMobLevel + " has appeared!");
            System.out.println("Would you like to fight it?");
            String fightOrNot;
            while (true) {
                fightOrNot = sc.nextLine().toLowerCase().trim();
                if (yes(fightOrNot) || fightOrNot.contains("fight")
                        || fightOrNot.startsWith("n") || fightOrNot.contains("run")) break;
                System.out.println("Please answer yes or no.");
            }

            if (yes(fightOrNot) || fightOrNot.contains("fight")) {
                boolean survived = fightMob();
                if (!survived) {
                    System.out.println("Game over. Would you like to start over?");
                    String again = sc.nextLine().toLowerCase().trim();
                    if (yes(again)) {
                        gameReset();
                        playerRoleSelection();
                        playerStats();
                    } else {
                        attackInSession = false;
                    }
                    continue;
                }
                System.out.println("Would you like to exit the game?");
                String exitGame = sc.nextLine().toLowerCase().trim();
                if (yes(exitGame) || exitGame.contains("exit")) {
                    attackInSession = false;
                }
            } else {
                System.out.println("You have chosen to run away from the " + Mob);
                System.out.println("Would you like to:\n>  exit the game? or, \n>  Open the shop?");
                String choice = sc.nextLine().toLowerCase().trim();
                if (choice.contains("exit") || yes(choice)) {
                    attackInSession = false;
                } else if (choice.contains("shop") || choice.contains("open")) {
                    Shop();
                } else {
                    System.out.println("You have decided to neither exit or open the shop, hence the game will continue.");
                }
            }
        }
    }

    public int mobRandomAttack() {
        return 1 + rd.nextInt(3);
    }

    // returns true if the player survived
    public boolean fightMob() {
        System.out.println("You have chosen to fight the " + Mob + "!");
        currentTurn = (Attacker() == 1) ? "Player" : "Mob";   // reset every fight (was stale before)
        randomVLVal();
        System.out.println(currentTurn.equals("Player") ? playerVL[rowsVl][columnsVL] : mobVL[rowsVl][columnsVL]);

        while (true) {
            if (currentTurn.equals("Player")) {
                playerAttack();
                int dmg = playerDamageCalc();
                mobHP = Math.max(0, mobHP - dmg);
                System.out.println("You have dealt " + dmg + "HP damage to the " + Mob + ".");
                if (mobHP <= 0) {
                    System.out.println("You have defeated the " + Mob + "!");
                    playerLevel();
                    checkLevelUp();
                    hp = maxHp;
                    goldObtained();
                    return true;
                }
                System.out.println("The " + Mob + " has " + mobHP + "HP left.");
                currentTurn = "Mob";
            } else {
                mobAttack();
                int dmg = mobDamageCalc();
                hp = Math.max(0, hp - dmg);
                System.out.println("The " + Mob + " has dealt " + dmg + "HP damage to you.");
                if (hp <= 0) {
                    String[][] playerDeathVL = {
                            {"You..Have died.", "Not all souls survive to reach a peaceful death...Thank you for your efforts."},
                            {"You, " + name + " will be remembered as a courageous soul. Now rest", "Your efforts have led you to a gruesome death, but a peaceful afterlife."},
                            {"You will go down as the one with violent light who fought until it couldn't shine. Now embrace the peaceful dark.", "You showed no vanity until the end. An inspiration indeed. Thank you and goodbye."},
                    };
                    System.out.println(playerDeathVL[rd.nextInt(3)][rd.nextInt(2)]);
                    return false;
                }
                System.out.println("You have " + hp + "HP left.");
                currentTurn = "Player";
            }
        }
    }

    String attackUsed = " ";
    double magDmgOfAttack = 0;
    double phyDmgOfAttack = 0;

    public void playerAttack() {
        while (true) {
            System.out.println("What would you like to use?");
            System.out.println(Basic + "(Basic attack)    " + ability1 + "(ability)    or    " + ult + "(ultimate)");
            String used = sc.nextLine().toLowerCase().trim();

            if (used.contains("basic")) {
                magDmgOfAttack = basicDmgMag;
                phyDmgOfAttack = basicDmgPhy;
                attackUsed = Basic;
            } else if (used.contains("ability") || used.contains(ability1.toLowerCase())) {
                magDmgOfAttack = ability1DmgMag;
                phyDmgOfAttack = ability1DmgPhy;
                attackUsed = ability1;
            } else if (used.contains("ult") || used.contains(ult.toLowerCase())) {
                magDmgOfAttack = ultDmgMag;
                phyDmgOfAttack = ultDmgPhy;
                attackUsed = ult;
            } else {
                System.out.println("That's not one of your attacks! Pick again.");
                continue;
            }
            System.out.println("You used " + attackUsed);
            return;
        }
    }

    public int playerDamageCalc() {
        double effMobMagDef = Math.max(0, mobMagDef - staffMagPen);
        return (int) (magDmgOfAttack * (1 + staffMagAtk) * (1 - effMobMagDef)
                + phyDmgOfAttack * (1 - mobPhyDef));
    }

    String mobAttackUsed = " ";
    double mobMagDmg = 0;
    double mobPhyDmg = 0;

    public void mobAttack() {
        int attackChoice = mobRandomAttack();
        if (attackChoice == 1) {
            mobAttackUsed = mobBasic;
            mobMagDmg = mobBasicDmgMag;
            mobPhyDmg = mobBasicDmgPhy;
        } else if (attackChoice == 2) {
            mobAttackUsed = mobAbility1;
            mobMagDmg = mobAbility1DmgMag;
            mobPhyDmg = mobAbility1DmgPhy;
        } else {
            mobAttackUsed = mobUlt;
            mobMagDmg = mobUltDmgMag;
            mobPhyDmg = mobUltDmgPhy;
        }
        System.out.println("The " + Mob + " has used " + mobAttackUsed.trim() + "!");
    }

    public int mobDamageCalc() {
        return (int) (mobMagDmg * (1 - totalMagDef()) + mobPhyDmg * (1 - totalPhyDef()));
    }

    public void goldObtained() {
        gold += (int) mobGold;
        System.out.println("You have obtained +" + (int) mobGold + " gold from the " + Mob);
        System.out.println("Total gold: " + gold);
        System.out.println("Would you like to open the shop? ");
        String openShop = sc.nextLine().toLowerCase().trim();
        if (openShop.contains("open") || yes(openShop)) {
            System.out.println("You have chosen to open the shop!");
            Shop();
        } else {
            System.out.println("You have chosen to not open the shop");
        }
    }

    public static void main(String[] args) {
        Game obj = new Game();
        obj.playerName();
        obj.playerRoleSelection();
        obj.playerStats();
        obj.attackAndFight();
    }
}
// HAS NOT BEEN FINISHED YET (tank/swordsman shops still to do)