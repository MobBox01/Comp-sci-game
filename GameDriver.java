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
//1/21/2026 LOG: Added new sprites, improved quality of game. New ideas still trying to be cooked up
















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