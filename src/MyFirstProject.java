import java.util.*;



   class Game {
       int hp, level, gold, exp;
       public String name, Mage, Tank, Swordsman, playerRole;
       double magAtk, magDef, phyDef, phyAtk;
       String Mob = " ", currentTurn = " ";
       String openShop = " ";
       int currentMobLevel = 0;
       String selectedRobe = " ",buyOrNah = " ";
       double robePrice = 0,leftGold = 0;
       double magBootPrice = 0;
//**Level 1 = 100exp
// Level 2 = 119exp
// Level 3 = 142exp
// Level 4 = 170exp
// Level 100 = 4,294,967,296exp**//


       public Game() {
           hp = 100;
           level = 1;
           name = " ";
           magAtk = 0;
           magDef = 0;
           phyDef = 0;
           phyAtk = 0;
           gold = 0;
           exp = 100;

       }


       public void gameReset() {
           hp = 100;
           level = 1;
           magAtk = 0;
           magDef = 0;
           phyDef = 0;
           phyAtk = 0;
           gold = 0;
           exp = 100;
           System.out.println("Game has been reset.");
       }


       //DATA BASE FOR STORE


       //ARMOUR

       //CHESTPLATES

       //Mage focused
       String[][] mageRobe = {
               {"Magic Imbued Old Rag", "Linen Vest", "Apprentice's Robe"},//Common
               {"Wizard's Old Cloak( Worn Out)", "Wizard's Cloak(New)", "Magic Testament"},//Rare
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
               {"Worn Leather Shoes", "Apprentice's Sandals", "Cloth Boots"}, // Common
               {"Scholar's Tread", "Enchanted Boots", "Gilded Silk Shoes"}, // Rare
               {"Netherweave Striders", "Ember-Soul Boots", "Eclipse Treads"}, // Epic
               {"Archmage's Walkers", "Crown of Stars Striders", "Void-Treader Boots"} // Legendary
       };

       String[][] tierMageBoot = {
               {"Common", "Common", "Common"},
               {"Rare", "Rare", "Rare"},
               {"Epic", "Epic", "Epic"},
               {"Legendary", "Legendary", "Legendary"}
       };

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
               {"Splintered Wooden Stick", "Apprentice Wand", "Carved Ash Rod"}, // Common
               {"Polished Elm Staff", "Crystal-Tipped Rod", "Runed Oak Staff"}, // Rare
               {"Netherweave Catalyst", "Ember-Soul Scepter", "Eclipse Greatstaff"}, // Epic
               {"Grandmaster's Aether Rod", "Crown of Stars Scepter", "Staff of the Void"} // Legendary
       };

       String[][] tierMageStaff = {
               {"Common", "Common", "Common"},
               {"Rare", "Rare", "Rare"},
               {"Epic", "Epic", "Epic"},
               {"Legendary", "Legendary", "Legendary"}
       };

       // Offense primary: Magic Attack % boost
       double[][] mageStaffMagAtk = {
               {0.005, 0.01, 0.02},
               {0.035, 0.06, 0.09},
               {0.15, 0.18, 0.22},
               {0.28, 0.32, 0.40}
       };

       // Offense secondary: Magic Penetration %
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


       //Tank Focused
       String[] tankChestPlates = {"Iron Chestplate", "Heavy Steel Plate", "Titan's Bulwark", ""};


       public void Shop() {
           Scanner sc = new Scanner(System.in);
           System.out.println("You have opened the shop!");
           System.out.println("_-----X-----X-----X-----X-----X-----X-----X-----X-----X-----_ ");

           System.out.println("To buy gear for mages, click 1 or type mage");
           System.out.println("To buy gear for tanks, click 2 or type tank");
           System.out.println("To buy gear for swordsmen, click 3 or type swordsman");
           System.out.println("To go back, click 4 or type back");
           System.out.println("To exit the shop, click 101 or type exit");
           String shopNavigate = sc.nextLine().toLowerCase().trim();
           if (shopNavigate.contains("1") || shopNavigate.contains("mage")) {
               System.out.println("You have entered the magic shop aka the shop for mages!");
               System.out.println("What would you like to buy?");
               System.out.println("> Robes");
               System.out.println("> Boots");
               System.out.println("> Staffs");
               String mageShopNavigate = sc.nextLine().toLowerCase().trim();
               if (mageShopNavigate.contains("robes")) {
                   System.out.println("Good choice! Which Robe fits your Fancy and Budget?(Can pick by either typing the name or typing the Serial number corresponding to the equipment.");
                   shopMageRobe();
               }
               else if(mageShopNavigate.contains("boot"))
               {
                   System.out.println("Nothing's better than not touching the ground with your bare feet after all!");
                   shopMageBoots();
               }
           }
       }

       String exitChoice;

       public void shopMageRobe() {
           Scanner sc = new Scanner(System.in);

           int serialNum = 0;
           for (int i = 0; i < mageRobe.length; i++) {
               for (int j = 0; j < mageRobe[i].length; j++) {
                   ++serialNum;
                   System.out.println(serialNum + ".  " + mageRobe[i][j] + "  ---->  " + mageRobePrices[i][j] + " Gold.");
               }
           }

           shopWhile:
           while (true) {
               String robeChoice = sc.nextLine().toLowerCase().trim();
               if (robeChoice.contains("1") || robeChoice.contains("magic imbued")) {
                   selectedRobe = mageRobe[0][0];
                   robePrice = mageRobePrices[0][0];
                   System.out.println("Selected \"" + mageRobe[0][0] + "\" ");
                   System.out.println("Price = " + mageRobePrices[0][0]);
                   System.out.println("Tier = " + tierMageRobe[0][0]);
                   System.out.println("Magic defence = " + mageRobeMagDef[0][0] * 100 + "%");
                   System.out.println("Physical defence = " + mageRobePhyDef[0][0] * 100 + "%");
                   continue;
               } else if (robeChoice.contains("2") || robeChoice.contains("linen")) {
                   selectedRobe = mageRobe[0][1];
                   robePrice = mageRobePrices[0][1];
                   System.out.println("Selected \"" + mageRobe[0][1] + "\" ");
                   System.out.println("Price = " + mageRobePrices[0][1]);
                   System.out.println("Tier = " + tierMageRobe[0][1]);
                   System.out.println("Magic defence = " + mageRobeMagDef[0][1] * 100 + "%");
                   System.out.println("Physical defence = " + mageRobePhyDef[0][1] * 100 + "%");
                   continue;
               } else if (robeChoice.contains("3") || robeChoice.contains("apprentice")) {
                   selectedRobe = mageRobe[0][2];
                   robePrice = mageRobePrices[0][2];
                   System.out.println("Selected \"" + mageRobe[0][2] + "\" ");
                   System.out.println("Price = " + mageRobePrices[0][2]);
                   System.out.println("Tier = " + tierMageRobe[0][2]);
                   System.out.println("Magic defence = " + mageRobeMagDef[0][2] * 100 + "%");
                   System.out.println("Physical defence = " + mageRobePhyDef[0][2] * 100 + "%");
                   continue;
               } else if (robeChoice.contains("4") || robeChoice.contains("old")) {
                   selectedRobe = mageRobe[1][0];
                   robePrice = mageRobePrices[1][0];
                   System.out.println("Selected \"" + mageRobe[1][0] + "\" ");
                   System.out.println("Price = " + mageRobePrices[1][0]);
                   System.out.println("Tier = " + tierMageRobe[1][0]);
                   System.out.println("Magic defence = " + mageRobeMagDef[1][0] * 100 + "%");
                   System.out.println("Physical defence = " + mageRobePhyDef[1][0] * 100 + "%");
                   continue;
               } else if (robeChoice.contains("5") || robeChoice.contains("new")) {
                   selectedRobe = mageRobe[1][1];
                   robePrice = mageRobePrices[1][1];
                   System.out.println("Selected \"" + mageRobe[1][1] + "\" ");
                   System.out.println("Price = " + mageRobePrices[1][1]);
                   System.out.println("Tier = " + tierMageRobe[1][1]);
                   System.out.println("Magic defence = " + mageRobeMagDef[1][1] * 100 + "%");
                   System.out.println("Physical defence = " + mageRobePhyDef[1][1] * 100 + "%");
                   continue;
               } else if (robeChoice.contains("6") || robeChoice.contains("testament")) {
                   selectedRobe = mageRobe[1][2];
                   robePrice = mageRobePrices[1][2];
                   System.out.println("Selected \"" + mageRobe[1][2] + "\" ");
                   System.out.println("Price = " + mageRobePrices[1][2]);
                   System.out.println("Tier = " + tierMageRobe[1][2]);
                   System.out.println("Magic defence = " + mageRobeMagDef[1][2] * 100 + "%");
                   System.out.println("Physical defence = " + mageRobePhyDef[1][2] * 100 + "%");
                   continue;
               } else if (robeChoice.contains("7") || robeChoice.contains("netherweave")) {
                   selectedRobe = mageRobe[2][0];
                   robePrice = mageRobePrices[2][0];
                   System.out.println("Selected \"" + mageRobe[2][0] + "\" ");
                   System.out.println("Price = " + mageRobePrices[2][0]);
                   System.out.println("Tier = " + tierMageRobe[2][0]);
                   System.out.println("Magic defence = " + mageRobeMagDef[2][0] * 100 + "%");
                   System.out.println("Physical defence = " + mageRobePhyDef[2][0] * 100 + "%");
                   continue;
               } else if (robeChoice.contains("8") || robeChoice.contains("ember")) {
                   selectedRobe = mageRobe[2][1];
                   robePrice = mageRobePrices[2][1];
                   System.out.println("Selected \"" + mageRobe[2][1] + "\" ");
                   System.out.println("Price = " + mageRobePrices[2][1]);
                   System.out.println("Tier = " + tierMageRobe[2][1]);
                   System.out.println("Magic defence = " + mageRobeMagDef[2][1] * 100 + "%");
                   System.out.println("Physical defence = " + mageRobePhyDef[2][1] * 100 + "%");
                   continue;
               } else if (robeChoice.contains("9") || robeChoice.contains("eclipse")) {
                   selectedRobe = mageRobe[2][2];
                   robePrice = mageRobePrices[2][2];
                   System.out.println("Selected \"" + mageRobe[2][2] + "\" ");
                   System.out.println("Price = " + mageRobePrices[2][2]);
                   System.out.println("Tier = " + tierMageRobe[2][2]);
                   System.out.println("Magic defence = " + mageRobeMagDef[2][2] * 100 + "%");
                   System.out.println("Physical defence = " + mageRobePhyDef[2][2] * 100 + "%");
                   continue;
               } else if (robeChoice.contains("10") || robeChoice.contains("grandmaster")) {
                   selectedRobe = mageRobe[3][0];
                   robePrice = mageRobePrices[3][0];
                   System.out.println("Selected \"" + mageRobe[3][0] + "\" ");
                   System.out.println("Price = " + mageRobePrices[3][0]);
                   System.out.println("Tier = " + tierMageRobe[3][0]);
                   System.out.println("Magic defence = " + mageRobeMagDef[3][0] * 100 + "%");
                   System.out.println("Physical defence = " + mageRobePhyDef[3][0] * 100 + "%");
                   continue;
               } else if (robeChoice.contains("11") || robeChoice.contains("crown")) {
                   selectedRobe = mageRobe[3][1];
                   robePrice = mageRobePrices[3][1];
                   System.out.println("Selected \"" + mageRobe[3][1] + "\" ");
                   System.out.println("Price = " + mageRobePrices[3][1]);
                   System.out.println("Tier = " + tierMageRobe[3][1]);
                   System.out.println("Magic defence = " + mageRobeMagDef[3][1] * 100 + "%");
                   System.out.println("Physical defence = " + mageRobePhyDef[3][1] * 100 + "%");
                   continue;
               } else if (robeChoice.contains("12") || robeChoice.contains("veil")) {
                   selectedRobe = mageRobe[3][2];
                   robePrice = mageRobePrices[3][2];
                   System.out.println("Selected \"" + mageRobe[3][2] + "\" ");
                   System.out.println("Price = " + mageRobePrices[3][2]);
                   System.out.println("Tier = " + tierMageRobe[3][2]);
                   System.out.println("Magic defence = " + mageRobeMagDef[3][2] * 100 + "%");
                   System.out.println("Physical defence = " + mageRobePhyDef[3][2] * 100 + "%");
                   continue;
               } else if (robeChoice.contains("101") || robeChoice.contains("exit")) {
                   selectedRobe = "Not Selected.";
                   robePrice = 0;
                   System.out.println("You have exited the shop!");
                   break shopWhile;
               } else {
                   selectedRobe = "Not Selected.";
                   robePrice = 0;
                   System.out.println("Not an item in the shop!");
                   System.out.println("Do you wish to exit?");
                   exitChoice = sc.nextLine().toLowerCase().trim();
                   if (exitChoice.contains("exit")) {
                       exitChoice = "Exited.";
                       System.out.println("You have exited.");
                   } else {
                       System.out.println("You have chosen to not exit.");
                   }
               }
           }

           if (selectedRobe.equals("Not Selected.") || exitChoice.equals("Exited.")) {
               System.out.println("You did not choose anything..? Okay then.");
           } else {
           if(gold  >= robePrice){
               leftGold = gold - robePrice;
               System.out.println("Would you like to buy \"" + selectedRobe + "\"?");
               System.out.printf("You will have: %,.1f left over\n", leftGold);
               buyOrNah = sc.nextLine().toLowerCase().trim();
               if(buyOrNah.contains("buy")||buyOrNah.contains("yes"))
               {
                   leftGold = gold;
                   System.out.println("You have bought \"" +selectedRobe+ "\"!");
                   System.out.println("You have " + gold + "left.");
               }

           }
           }


       }


        String selectedBoot = " ";
        public void shopMageBoots()
        {
       Scanner sc = new Scanner(System.in);

       int serialNum = 0;
           for (int i = 0; i < mageBoot.length; i++) {
           for (int j = 0; j < mageBoot[i].length; j++) {
               ++serialNum;
               System.out.println(serialNum + ".  " + mageBoot[i][j] + "  ---->  " + mageBootPrices[i][j] + " Gold.");
           }
       }

       shopWhile:
               while (true) {
           String bootChoice = sc.nextLine().toLowerCase().trim();
           if (bootChoice.contains("1") || bootChoice.contains("magic imbued")) {
               selectedBoot = mageBoot[0][0];
               magBootPrice = mageBootPrices[0][0];
               System.out.println("Selected \"" + mageBoot[0][0] + "\" ");
               System.out.println("Price = " + mageBootPrices[0][0]);
               System.out.println("Tier = " + tierMageBoot[0][0]);
               System.out.println("Magic defence = " + mageBootMagDef[0][0] * 100 + "%");
               System.out.println("Physical defence = " + mageBootPhyDef[0][0] * 100 + "%");
               continue;
           } else if (bootChoice.contains("2") || bootChoice.contains("linen")) {
               selectedBoot = mageBoot[0][1];
               magBootPrice = mageBootPrices[0][1];
               System.out.println("Selected \"" + mageBoot[0][1] + "\" ");
               System.out.println("Price = " + mageBootPrices[0][1]);
               System.out.println("Tier = " + tierMageBoot[0][1]);
               System.out.println("Magic defence = " + mageBootMagDef[0][1] * 100 + "%");
               System.out.println("Physical defence = " + mageBootPhyDef[0][1] * 100 + "%");
               continue;
           } else if (bootChoice.contains("3") || bootChoice.contains("apprentice")) {
               selectedBoot = mageBoot[0][2];
               magBootPrice = mageBootPrices[0][2];
               System.out.println("Selected \"" + mageBoot[0][2] + "\" ");
               System.out.println("Price = " + mageBootPrices[0][2]);
               System.out.println("Tier = " + tierMageBoot[0][2]);
               System.out.println("Magic defence = " + mageBootMagDef[0][2] * 100 + "%");
               System.out.println("Physical defence = " + mageBootPhyDef[0][2] * 100 + "%");
               continue;
           } else if (bootChoice.contains("4") || bootChoice.contains("old")) {
               selectedBoot = mageBoot[1][0];
               magBootPrice = mageBootPrices[1][0];
               System.out.println("Selected \"" + mageBoot[1][0] + "\" ");
               System.out.println("Price = " + mageBootPrices[1][0]);
               System.out.println("Tier = " + tierMageBoot[1][0]);
               System.out.println("Magic defence = " + mageBootMagDef[1][0] * 100 + "%");
               System.out.println("Physical defence = " + mageBootPhyDef[1][0] * 100 + "%");
               continue;
           } else if (bootChoice.contains("5") || bootChoice.contains("new")) {
               selectedBoot = mageBoot[1][1];
               magBootPrice = mageBootPrices[1][1];
               System.out.println("Selected \"" + mageBoot[1][1] + "\" ");
               System.out.println("Price = " + mageBootPrices[1][1]);
               System.out.println("Tier = " + tierMageBoot[1][1]);
               System.out.println("Magic defence = " + mageBootMagDef[1][1] * 100 + "%");
               System.out.println("Physical defence = " + mageBootPhyDef[1][1] * 100 + "%");
               continue;
           } else if (bootChoice.contains("6") || bootChoice.contains("testament")) {
               selectedBoot = mageBoot[1][2];
               magBootPrice = mageBootPrices[1][2];
               System.out.println("Selected \"" + mageBoot[1][2] + "\" ");
               System.out.println("Price = " + mageBootPrices[1][2]);
               System.out.println("Tier = " + tierMageBoot[1][2]);
               System.out.println("Magic defence = " + mageBootMagDef[1][2] * 100 + "%");
               System.out.println("Physical defence = " + mageBootPhyDef[1][2] * 100 + "%");
               continue;
           } else if (bootChoice.contains("7") || bootChoice.contains("netherweave")) {
               selectedBoot = mageBoot[2][0];
               magBootPrice = mageBootPrices[2][0];
               System.out.println("Selected \"" + mageBoot[2][0] + "\" ");
               System.out.println("Price = " + mageBootPrices[2][0]);
               System.out.println("Tier = " + tierMageBoot[2][0]);
               System.out.println("Magic defence = " + mageBootMagDef[2][0] * 100 + "%");
               System.out.println("Physical defence = " + mageBootPhyDef[2][0] * 100 + "%");
               continue;
           } else if (bootChoice.contains("8") || bootChoice.contains("ember")) {
               selectedBoot = mageBoot[2][1];
               magBootPrice = mageBootPrices[2][1];
               System.out.println("Selected \"" + mageBoot[2][1] + "\" ");
               System.out.println("Price = " + mageBootPrices[2][1]);
               System.out.println("Tier = " + tierMageBoot[2][1]);
               System.out.println("Magic defence = " + mageBootMagDef[2][1] * 100 + "%");
               System.out.println("Physical defence = " + mageBootPhyDef[2][1] * 100 + "%");
               continue;
           } else if (bootChoice.contains("9") || bootChoice.contains("eclipse")) {
               selectedBoot = mageBoot[2][2];
               magBootPrice = mageBootPrices[2][2];
               System.out.println("Selected \"" + mageBoot[2][2] + "\" ");
               System.out.println("Price = " + mageBootPrices[2][2]);
               System.out.println("Tier = " + tierMageBoot[2][2]);
               System.out.println("Magic defence = " + mageBootMagDef[2][2] * 100 + "%");
               System.out.println("Physical defence = " + mageBootPhyDef[2][2] * 100 + "%");
               continue;
           } else if (bootChoice.contains("10") || bootChoice.contains("grandmaster")) {
               selectedBoot = mageBoot[3][0];
               magBootPrice = mageBootPrices[3][0];
               System.out.println("Selected \"" + mageBoot[3][0] + "\" ");
               System.out.println("Price = " + mageBootPrices[3][0]);
               System.out.println("Tier = " + tierMageBoot[3][0]);
               System.out.println("Magic defence = " + mageBootMagDef[3][0] * 100 + "%");
               System.out.println("Physical defence = " + mageBootPhyDef[3][0] * 100 + "%");
               continue;
           } else if (bootChoice.contains("11") || bootChoice.contains("crown")) {
               selectedBoot = mageBoot[3][1];
               magBootPrice = mageBootPrices[3][1];
               System.out.println("Selected \"" + mageBoot[3][1] + "\" ");
               System.out.println("Price = " + mageBootPrices[3][1]);
               System.out.println("Tier = " + tierMageBoot[3][1]);
               System.out.println("Magic defence = " + mageBootMagDef[3][1] * 100 + "%");
               System.out.println("Physical defence = " + mageBootPhyDef[3][1] * 100 + "%");
               continue;
           } else if (bootChoice.contains("12") || bootChoice.contains("veil")) {
               selectedBoot = mageBoot[3][2];
               magBootPrice = mageBootPrices[3][2];
               System.out.println("Selected \"" + mageBoot[3][2] + "\" ");
               System.out.println("Price = " + mageBootPrices[3][2]);
               System.out.println("Tier = " + tierMageBoot[3][2]);
               System.out.println("Magic defence = " + mageBootMagDef[3][2] * 100 + "%");
               System.out.println("Physical defence = " + mageBootPhyDef[3][2] * 100 + "%");
               continue;
           } else if (bootChoice.contains("101") || bootChoice.contains("exit")) {
               selectedBoot = "Not Selected.";
               magBootPrice = 0;
               System.out.println("You have exited the shop!");
               break shopWhile;
           } else {
               selectedBoot = "Not Selected.";
               magBootPrice = 0;
               System.out.println("Not an item in the shop!");
               System.out.println("Do you wish to exit?");
               exitChoice = sc.nextLine().toLowerCase().trim();
               if (exitChoice.contains("exit")) {
                   exitChoice = "Exited.";
                   System.out.println("You have exited.");
               } else {
                   System.out.println("You have chosen to not exit.");
               }
           }
       }

           if (selectedBoot.equals("Not Selected.") || exitChoice.equals("Exited.")) {
           System.out.println("You did not choose anything..? Okay then.");
       } else {
           if(gold  >= magBootPrice){
               leftGold = gold - magBootPrice;
               System.out.println("Would you like to buy \"" + selectedBoot + "\"?");
               System.out.printf("You will have: %,.1f left over\n", leftGold);
               buyOrNah = sc.nextLine().toLowerCase().trim();
               if(buyOrNah.contains("buy")||buyOrNah.contains("yes"))
               {
                   leftGold = gold;
                   System.out.println("You have bought \"" +selectedBoot+ "\"!");
                   System.out.println("You have " + gold + "left.");
               }

           }
       }


   }







       //DATA BASE FOR MOBS


       String Goblin, spectre, Golem;


       //goblin attribute data base
       double gobHP = 200, gobMagDef = 0.2, gobPhyAtk = 25, gobPhyDef = 0.2, gobMagAtk = 0, gobExp = 50,gobGold = 20;

       //goblin ability
       String gobAbility1 = "Effortless Swing ", gobUlt = "Desparate All-In", gobBasic = "Basic Slash";
       double gobAbility1DmgMag = 0, gobAbility1DmgPhy = 35, gobUltDmgMag = 0, gobUltDmgPhy = 50, gobBasicDmgMag = 0, gobBasicDmgPhy = 25;


       //Spectre attribute data base
       double speHP = 225, speMagDef = 0.15, spePhyAtk = 15, spePhyDef = 0.2, speMagAtk = 20, speExp = 100,speGold = 20;

       //Spectre ability
       String speAbility1 = "Spectre's Haunt ", speUlt = "Eye Of The Abyss", speBasic = "Basic Magic Claw";
       double speAbility1DmgMag = 30, speAbility1DmgPhy = 5, speUltDmgMag = 25, speUltDmgPhy = 25, speBasicDmgMag = 20, speBasicDmgPhy = 10;


       //Golem attribute data base
       double golHP = 350, golMagDef = 0.3, golPhyAtk = 25, golPhyDef = 0.35, golMagAtk = 15, golExp = 75,golGold = 20;

       //Golem attack
       String golAbility1 = "Heavy Punch ", golUlt = "Heavy Leap Smash", golBasic = "Basic Melee";
       double golAbility1DmgMag = 5, golAbility1DmgPhy = 35, golUltDmgMag = 0, golUltDmgPhy = 50, golBasicDmgMag = 5, golBasicDmgPhy = 25;


       //END OF DATA BASE FOR MOBS


       //Common Attributes
       double mobHP = 0, mobMagDef = 0, mobPhyAtk = 0, mobPhyDef = 0, mobMagAtk = 0, mobExp = 0,mobGold = 0;

       String mobAbility1 = " ", mobUlt = " ", mobBasic = " ";
       double mobAbility1DmgMag = 0, mobAbility1DmgPhy = 0, mobUltDmgMag = 0, mobUltDmgPhy = 0, mobBasicDmgPhy = 0, mobBasicDmgMag = 0;



       public void playerName() {
           Scanner sc = new Scanner(System.in);
           Random rd = new Random();
           System.out.println("Enter your name. ");
           while (true) {
               System.out.print("> ");
               name = sc.nextLine();
               System.out.println("Do you want to lock this name?");
               String nameLock = sc.nextLine().toLowerCase().trim();
               if (nameLock.contains("yes") || nameLock.contains("ok")) {
                   int randomNumRow = rd.nextInt(0, 3);
                   int randomNumCol = rd.nextInt(0, 2);
                   String[][] nameLine = {
                           {name + "? Nice name!", "Hi " + name + "!"},
                           {name + " huh..What a lovely name!", "So your name is \"" + name + "\"? Did I pronounce that right? Nice name btw"},
                           {"I got it! " + name + " Right?", "Fancy name eh?, " + name}
                   };
                   System.out.println(nameLine[randomNumRow][randomNumCol]);
                   break;
               } else {
                   System.out.println("Okay, choose again.");
               }
           }
       }

       double ability1DmgMag, ability1DmgPhy, ultDmgMag, ultDmgPhy, basicDmgMag, basicDmgPhy;
       String ability1, ult, Basic;


       public void playerRoleSelection() {
           Scanner sc = new Scanner(System.in);
           System.out.println("Pick your role: ");
           System.out.println("Mage:  Magic all around and can hits and chants with a stick");
           System.out.println("Tank: 0 magic but built like a TANK!.. Get it?");
           System.out.println("Swordsman: best of both worlds from tank and mage..");
           while (true) {
               System.out.print("> ");

               String rolePick = sc.nextLine().toLowerCase().trim();

               if (rolePick.contains("mage") || rolePick.contains("magic")) {
                   //Player Role (Redundant but good practice
                   playerRole = "Mage";
                   //Attributes
                   magAtk = 45;
                   phyAtk = 5;
                   magDef = 0.15;
                   phyDef = 0.1;
                   //Ability
                   ability1 = "Fire Ball";
                   ability1DmgMag = 65+magAtk;
                   ability1DmgPhy = 0+phyAtk;

                   //Ultimate
                   ult = "Porfyró megaleío";
                   ultDmgMag = 80+magAtk;
                   ultDmgPhy = 0+phyAtk;

                   //Basic Attack
                   Basic = "Basic Attack";
                   basicDmgMag = 45+magAtk;
                   basicDmgPhy = 5+phyAtk;

                   break;
               } else if (rolePick.contains("tank")) {
                   playerRole = "Tank";
                   //Attributes

                   magAtk = 0;
                   phyAtk = 25;
                   magDef = 0.3;
                   phyDef = 0.35;
                   //Ability
                   ability1 = "Brute Smash";
                   ability1DmgPhy = 40+phyAtk;
                   ability1DmgMag = 0+magAtk;

                   //Ultimate
                   ult = "The Hand Of Buddha";
                   ultDmgPhy = 70+phyAtk;
                   ultDmgMag = 0+magAtk;
                   //Basic Attack
                   Basic = "Basic Attack";
                   basicDmgPhy = 25+phyAtk;
                   basicDmgMag = 0+magAtk;
                   break;
               } else if (rolePick.contains("sword")) {
                   playerRole = "Swordsman";
                   //Attributes
                   magAtk = 40;
                   phyAtk = 50;
                   magDef = 0.2;
                   phyDef = 0.25;
                   //Ability
                   ability1 = "Magic Slash";
                   ability1DmgMag = 30+magAtk;
                   ability1DmgPhy = 35+phyAtk;
                   //Ultimate
                   ult = "Gladius Magicus Geminus";
                   ultDmgMag = 45+magAtk;
                   ultDmgPhy = 35+phyAtk;
                   //Basic Attack
                   Basic = "Basic Attack";
                   basicDmgMag = 25+magAtk;
                   basicDmgPhy = 25+phyAtk;
                   break;
               } else {
                   System.out.println("That's not an available role! Pick again.");
               }

           }

       }


       public void playerStats() {
           double totalAbility1Dmg = ability1DmgMag + ability1DmgPhy;
           double totalUltDmg = ultDmgMag + ultDmgPhy;
           double totalBasicDmg = basicDmgMag + basicDmgPhy;
           System.out.println("x----x---x---x---x---x---x---x---x");
           System.out.println("General Attributes: ");
           System.out.println("HP: " + hp);
           System.out.println("Level: " + level);
           System.out.println("Exp: " + exp);
           System.out.println("Gold: " + gold);
           String ability1ForIf = ability1.toLowerCase().trim();
           String ultForIf = ult.toLowerCase().trim();
           String basicForIf = Basic.toLowerCase().trim();
           System.out.println("Role chosen:" + playerRole);
           System.out.println("----------Role Attributes----------");
           System.out.println("Magic Attack: " + magAtk);
           System.out.println("Physical Attack: " + phyAtk);
           System.out.println("Magic Defense: " + magDef);
           System.out.println("Physical Defense: " + phyDef);
           System.out.println("Ability 1: " + ability1);
           System.out.println("Ultimate: " + ult);
       }


       public void checkLevelUp()
       {

           int[] levelReqExp = new int[101];

           double baseExp = 100;
           double growthRate = 1.19209;


           for (int lvl = 1; lvl <= 100; lvl++) {
               levelReqExp[lvl] = (int) baseExp;
               baseExp *= growthRate;
           }


           int newLevel = 1;
           for (int lvl = 1; lvl <= 100; lvl++) {
               if (exp >= levelReqExp[lvl]) {
                   newLevel = lvl;
               } else {
                   break;
               }
           }


           if (newLevel > level) {
               System.out.println("LEVEL UP! You are now Level " + newLevel + "!");
               level = newLevel;


           }
       }



       public void playerLevel()
       {
           exp += (int)mobExp;
           System.out.println("You have gained " + (int)mobExp + "xp from the level " + currentMobLevel + " " +Mob + "! Total EXP: " + exp + "xp.");
        }

        public void playerStatLevelUp()
        {
            if(playerRole.equals ("Mage")) {
                for (int statUp = 1; statUp <= level; statUp++) {
                    hp += 200;
                    magAtk *= 1.5;
                    magDef = Math.min(0.7,magDef *1.02);
                    phyAtk *= 1.001;
                    phyDef = Math.min(0.7,magDef*1.015);
                    ability1DmgMag *= 1.5;
                    ability1DmgPhy *= 1.002;
                    ultDmgMag *= 2;
                    ultDmgPhy *= 1.002;
                    basicDmgMag *= 1.8;
                    basicDmgPhy *= 1.002;
                }
            }
            else if(playerRole.equals("Tank"))
            {
                for (int statUp = 1; statUp <= level; statUp++) {
                    hp += 250;
                    magAtk *= 1.002;
                    magDef = Math.min( 0.7,magDef *1.5);
                    phyAtk *= 1.5;
                    phyDef = Math.min(0.7,phyDef * 1.5);
                    if(level%10==0) {
                        ability1DmgMag *= 1.002;
                        ability1DmgPhy *= 1.02;
                        ultDmgMag *= 1.002;
                        ultDmgPhy *= 1.2;
                        basicDmgMag *= 1.002;
                        basicDmgPhy *= 1.1;
                    }
                }
            }
            else if(playerRole.equals("Swordsman"))
            {
                for (int statUp = 1; statUp <= level; statUp++) {
                    hp += 250;
                    magAtk *= 1.2;
                    magDef = Math.min(0.7, magDef * 1.25);
                    phyAtk *= 1.2;
                    phyDef = Math.min(0.7,phyDef*1.5);
                    if(level%10==0) {
                        ability1DmgMag *= 1.25;
                        ability1DmgPhy *= 1.35;
                        ultDmgMag *= 1.5;
                        ultDmgPhy *= 1.5;
                        basicDmgMag *= 1.1;
                        basicDmgPhy *= 1.1;
                    }
                }
            }
            playerStats();
        }



       public void randomMob() {
           Random rd = new Random();
            currentMobLevel = mobLevel();
           int mobNum = rd.nextInt(1, 4);

           if (mobNum == 1) {
               Mob = "Goblin";
               mobHP = gobHP;
               mobMagDef = gobMagDef;
               mobPhyDef = gobPhyDef;
               mobPhyAtk = gobPhyAtk;
               mobMagAtk = gobMagAtk;
               mobAbility1 = gobAbility1;
               mobAbility1DmgMag = gobAbility1DmgMag;
               mobAbility1DmgPhy = gobAbility1DmgPhy;
               mobUlt = gobUlt;
               mobUltDmgMag = gobUltDmgMag;
               mobUltDmgPhy = gobUltDmgPhy;
               mobBasic = gobBasic;
               mobBasicDmgMag = gobBasicDmgMag;
               mobBasicDmgPhy = gobBasicDmgPhy;
               mobExp = gobExp;
               mobGold = gobGold;
           } else if (mobNum == 2) {
               Mob = "Spectre";
               mobHP = speHP;
               mobMagDef = speMagDef;
               mobPhyDef = spePhyDef;
               mobPhyAtk = spePhyAtk;
               mobMagAtk = speMagAtk;
               mobAbility1 = speAbility1;
               mobAbility1DmgMag = speAbility1DmgMag;
               mobAbility1DmgPhy = speAbility1DmgPhy;
               mobUlt = speUlt;
               mobUltDmgMag = speUltDmgMag;
               mobUltDmgPhy = speUltDmgPhy;
               mobBasic = speBasic;
               mobBasicDmgMag = speBasicDmgMag;
               mobBasicDmgPhy = speBasicDmgPhy;
               mobExp = speExp;
               mobGold = speGold;
           } else if (mobNum == 3) {
               Mob = "Golem";
               mobHP = golHP;
               mobMagDef = golMagDef;
               mobPhyDef = golPhyDef;
               mobPhyAtk = golPhyAtk;
               mobMagAtk = golMagAtk;
               mobAbility1 = golAbility1;
               mobAbility1DmgMag = golAbility1DmgMag;
               mobAbility1DmgPhy = golAbility1DmgPhy;
               mobUlt = golUlt;
               mobUltDmgMag = golUltDmgMag;
               mobUltDmgPhy = golUltDmgPhy;
               mobBasic = golBasic;
               mobBasicDmgMag = golBasicDmgMag;
               mobBasicDmgPhy = golBasicDmgPhy;
               mobExp = golExp;
               mobGold = golGold;
           } else {
               System.out.println("Unprecedented Error! Check public void randomMob");
           }
       }


       public int mobLevel() {
           Random rd = new Random();

           if(level >=1 && level <=20)
           {
               return rd.nextInt(1,31);
           }
           else if(level >20 && level <=40)
           {
               return rd.nextInt(1,51);
           }
           else if(level >40 && level <=60)
           {
               return rd.nextInt(1,71);
           }
           else if(level > 60 && level <=80)
           {
               return rd.nextInt(1,91);
           }
           else if(level > 80 && level <=100)
           {
               return rd.nextInt(1,101);
           }
           else
           {
               System.out.println("Error! Leveling mistake!");
               throw new IllegalStateException();
           }
       }


       public void mobStatLevelUp() {
           for (int i = 1; i <= currentMobLevel; i++) {

               mobHP += 15;
               mobMagDef *= 1.0093;
               mobPhyAtk += 0.2;
               mobPhyDef *= 1.0093;
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
           Random rd = new Random();
           return (rd.nextInt(5, 10)) * 1000;

       }


       public int Attacker() {
           Random rd = new Random();
           return rd.nextInt(1, 3);
       }

       //PLAYER ATTACKS FIRST LINES
       String[][] playerVL = {
               {"You hav' the first hit.", "It doesn't see us. Attack now."},
               {"C'mon! Now Attack!", "Better late than never. Hit it."},
               {"I told you creeping up would work, jeopardize the opportunity! ", "My advice never fails! Kill it in one blow if you can!."}

       };

       //MOB ATTACKS FIRST LINES
       String[][] mobVL = {
               {"It saw us, get ready to take a hit", "This is awkward.. it's coming!"},
               {"Why are they so sharp?", "Twats always notice."},
               {"Woo hoo, we have gotten spotted! Dodge!", "Crazy how creeping up never works."}
       };


       int rowsVl = 0, columnsVL = 0;

       public void randomVLVal() {
           Random rd = new Random();
           rowsVl = rd.nextInt(0, 3);
           columnsVL = rd.nextInt(0, 2);
       }

       public void attackAndFight() {
           Scanner sc = new Scanner(System.in);

           boolean attackInSession = true;
           while (attackInSession) {
               randomMob();
               int mobWait = randomMobTimer();

               try {
                   System.out.println("You will randomly find the mob..or it will find you..");
                   System.out.println("Searching for mob...");
                   Thread.sleep(mobWait);

                   System.out.println("A " + Mob + " of level " + currentMobLevel + " has appeared!");
                   System.out.println("Would you like to fight it?");
                   String fightOrNot = sc.nextLine().toLowerCase().trim();

                   if (fightOrNot.contains("yes") || fightOrNot.contains("fight") || fightOrNot.contains("ok")) {
                       fightMob();
                       System.out.println("Would you like to exit the game?");
                       String exitGame = sc.nextLine().toLowerCase().trim();
                       gameReset();
                       if (exitGame.contains("yes") || exitGame.contains("exit")) {
                           attackInSession = false;
                       } else if (exitGame.contains("no") || exitGame.contains("don't") || exitGame.contains("dont")) {
                           currentTurn = "Mob";
                       }
                   } else if (fightOrNot.contains("no") || fightOrNot.contains("run")) {
                       System.out.println("You have chosen to run away from the " + Mob);
                       System.out.println("Would you like to:\n>  exit the game? or, \n>  Open the shop?");
                       String exitGame = sc.nextLine().toLowerCase().trim();
                       if (exitGame.contains("yes") || exitGame.contains("exit")) {
                           attackInSession = false;
                       } else if (exitGame.contains("no") || exitGame.contains("don't") || exitGame.contains("dont")) {
                           System.out.println("You have decided to neither exit or open the shop, hence the game will continue.");
                           continue;
                       }

                   }

               } catch (InterruptedException e) {
                   System.out.println("Interrupted! Error, re-initializing the timer.");
               }
           }
       }


       public int mobRandomAttack() {
           Random rd = new Random();
           return rd.nextInt(1, 4);
       }






       public void fightMob() {

           Attacker();
           int AttackerStorage = Attacker();
           PlayerTurn:
           while (true) {
               System.out.println("You have chosen to fight the " + Mob + "!");
               randomVLVal();
               if (AttackerStorage == 1||currentTurn.equals( "Player")) {
                   AttackerStorage = 0;
                   currentTurn = "Player";
                   System.out.println(playerVL[rowsVl][columnsVL]);
                   playerAttack();

                   mobHP = Math.max(0, mobHP - playerDamageCalc());
                   if (mobHP > 0) {
                       System.out.println("You have dealt " + playerDamageCalc() + "HP damage to the " + Mob + ".");
                       System.out.println("The " + Mob + " has " + mobHP + "HP left.");
                       currentTurn = "Mob";


                   } else if (mobHP == 0 ) {
                       System.out.println("You have defeated the " + Mob + "!");
                       playerLevel();
                       checkLevelUp();
                       goldObtained();

                       //PlayerLevel(); calling here
                       break PlayerTurn;

                   }
               } if (AttackerStorage == 2|| currentTurn.equals( "Mob")) {
                   AttackerStorage = 0;
                   currentTurn = "Mob";
                   System.out.println(mobVL[rowsVl][columnsVL]);
                   mobAttack();

                   hp = Math.max(0,hp - mobDamageCalc());
                   if(hp > 0 )
                   {
                       System.out.println("The " + Mob + " has dealt " + mobDamageCalc() + "HP damage to you.");
                       System.out.println("You have "+hp+"HP left.");
                       currentTurn = "Player";


                   }
                   else if(hp == 0)
                   {
                       String[][] playerDeathVL = {
                               {"You..Have died.","Not all souls survive to reach a peaceful death...Thank you for your efforts."},
                               {"You, " +name+ " will be remembered as a courageous soul. Now rest", "Your efforts have led you to a gruesome death, but a peaceful afterlife."},
                               {"You will go down as the one with violent light who fought until it couldn't shine. Now embrace the peaceful dark.", "You showed no vanity until the end. An inspiration indeed. Thank you and goodbye."},
                       };
                       Random rd = new Random();
                       int deathRowsVL = rd.nextInt(0,3);
                       int deathColumnsVL = rd.nextInt(0,2);
                       System.out.println(playerDeathVL[deathRowsVL][deathColumnsVL]);
                       break PlayerTurn;
                   }


               }
           }
       }



       String attackUsed = " ";
       double  magDmgOfAttack =  0;
       double phyDmgOfAttack = 0;
       public void playerAttack()
       {
           Scanner sc = new Scanner(System.in);
           System.out.println("What would you like to use?");
           System.out.println(Basic+"(Basic attack)    "+ability1 + "(ability)    or    "+ult+"(ultimate)");
           String playerAttackUsed = sc.nextLine().toLowerCase().trim();
           if(playerAttackUsed.contains("basic"))
           {
                magDmgOfAttack =  basicDmgMag;
                phyDmgOfAttack = basicDmgPhy;
                attackUsed = Basic;

           }
           else if(playerAttackUsed.contains("ability") || playerAttackUsed.contains(ability1))
           {
                magDmgOfAttack =  ability1DmgMag;
                phyDmgOfAttack = ability1DmgPhy;
                attackUsed = ability1;
           }
           else if(playerAttackUsed.contains("ult")||playerAttackUsed.contains(ult))
           {
                magDmgOfAttack =  ultDmgMag;
                phyDmgOfAttack = ultDmgPhy;
                attackUsed = ult;
           }
           System.out.println("You used "+ attackUsed);
       }


       public int playerDamageCalc()
       {
           return (int)(magDmgOfAttack * (1-mobMagDef) + (phyDmgOfAttack * (1-mobPhyDef)));
       }


       String mobAttackUsed = " ";
       double mobMagDmg = 0;
       double mobPhyDmg = 0;
       public void mobAttack()
       {

           int attackChoice  = mobRandomAttack();
           if (attackChoice == 1 )
           {
               mobAttackUsed = mobBasic;
               mobMagDmg = mobBasicDmgMag;
               mobPhyDmg = mobBasicDmgPhy;

           }
           else if(attackChoice == 2)
           {
               mobAttackUsed = mobAbility1;
               mobMagDmg = mobAbility1DmgMag;
               mobPhyDmg = mobAbility1DmgPhy;
           }
           else if(attackChoice == 3)
           {
               mobAttackUsed = mobUlt;
               mobMagDmg = mobUltDmgMag;
               mobPhyDmg = mobUltDmgPhy;
           }
           System.out.println("The " + Mob + "has used " + mobAttackUsed +"!");

       }



       public int mobDamageCalc()
       {
           return (int)(mobMagDmg * (1-magDef) + (mobPhyDmg *(1-phyDef)));
       }



       public void goldObtained() {
           Scanner sc = new Scanner(System.in);
           gold = (int) (gold + mobGold);
           System.out.println("You have obtained +" +mobGold+" from the " + Mob);
           System.out.println("Total gold: " + gold);
           System.out.println("Would you like to open the shop? ");
           openShop = sc.nextLine().toLowerCase().trim();
           if(openShop.contains("open")||openShop.contains("yes"))
           {
               System.out.println("You have chosen to open the shop!");
               Shop();
           }
           else {
               System.out.println("You have chosen to not open the shop");

           }C
       }

       public static void main(String[] args)
       {
           Game obj = new Game();


           obj.playerName();
           obj.playerRoleSelection();
           obj.playerStats();

           obj.attackAndFight();
       }
   }
   // HAS NOT BEEN FINISHED YETTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT