import java.io.IOException;

import FightingGui.Dialouge;
import FightingGui.FightingGui;
import FightingGui.FightingMusic;
import FightingGui.FightingSystem;
import RoomGui.PlayerMovement;
import RoomGui.RoomGui;
import Saving.ProgressSaving;
import Stats.Player;

public class GameDriver
{
    public static void main(String[] args) throws IOException
    {
        //These are in order!
        FightingMusic fightMusic = new FightingMusic();
        ProgressSaving saving = new ProgressSaving();
        Dialouge dialougeSystem = new Dialouge();
        Player player = new Player(saving.obtainSavePoint());

        FightingSystem fightSystem = new FightingSystem(player, dialougeSystem, fightMusic);
        FightingGui fightGui = new FightingGui(fightSystem,player, dialougeSystem);
        RoomGui roomGui = new RoomGui(fightGui,saving,player,dialougeSystem);

        PlayerMovement movement = new PlayerMovement(roomGui, fightGui);

        roomGui.addKeyListener(movement);
        fightGui.addKeyListener(movement);
    }
}

//[1/3/2026 3:30 AM Log]: Todays goal is to connect objects together, its a mess!
//[1/3/2026 3:44 AM Log]: RoomGUI is connected w/progress saving and player class *PUSHED*

//[1/3/2026 5:27 PM Log]: All classes can connect more easily now, this reduces versatility of a class making code
//easier to read
//[1/3/2026 5:56 PM Log]: Some refactoring changes done, i dont remember what i did lmfao
//[1/3/2026 6:02 PM Log]: Useless objects have been deleted.
//[1/3/2026 6:11 PM Log]: Finished up connecting classes. *PUSHED*

//1/4/2026 1:29 AM Log]: Every file in [ROOM GUI] file is now done.
//RoomGUI -> Merged GIF and Image tile into one, refactorization changes complete. Documentation is also cleaner
//RoomData -> More refactorization changes, documentation cleaner. Not much since its just a getter
//PlayerMovement -> MORE REFACTORIZATION CHANGES WOW. ALSO DOCUMENTATION IS CLEANER.
//[1/4/2026 1:36 AM Log]: Sprites are now seperated into folders for easier management

//[1/4/2026 4:04 PM Log]: Just code... 


















/**        while(playerTest.isAlive() && enemyTest.isAlive())
        {
            String input = sc.next();

            switch(input)
            {
                case "attack" -> System.out.println(enemyTest.damageRecieved(playerTest.attackAction()));
                case "heal" -> playerTest.amountHealed(20);
            }

            System.out.println(enemyTest.getName() + " is preparing their attack!");
            System.out.println(playerTest.damageRecieved(enemyTest.Attack()) + " damage has been delt!");

            System.out.println("Would you like to know player stat? type y/n");
            input = sc.next();
            
            switch (input) 
            {
                case "y" -> System.out.println(playerTest);
            }


            
        } */