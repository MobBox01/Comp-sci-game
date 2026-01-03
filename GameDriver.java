import java.io.IOException;

import FightingGui.Dialouge;
import FightingGui.FightGui;
import FightingGui.FightingSystem;
import RoomGui.PlayerMovement;
import RoomGui.RoomGui;
import Saving.ProgressSaving;
import Stats.Player;

public class GameDriver
{
    public static void main(String[] args) throws IOException
    {
        ProgressSaving saving = new ProgressSaving();

       //[1/3/2026 3:30 AM Log]: helllooooo me, working on adding objects here and parsing through
       //[1/3/2026 3:44 AM Log]: Hello again, managed to successfully connect progress saving with room gui!

       //[1/3/2026 5:27 PM Log]: todays goal [connect more objects together and refactor changes]
       //[1/3/2026 5:56 PM Log]: Took me a bit, zoned out but refactoring changes have been done.
       //[1/3/2026 6:02 PM Log]: new goal [get rid of objects not needed]
       //[1/3/2026 6:11 PM Log]:: Goal achieved for today, review code and make sure stuff looks good.
       
        Dialouge dialougeSystem = new Dialouge();
        Player player = new Player(saving.obtainSavePoint());
        FightingSystem fightSystem = new FightingSystem(player, dialougeSystem);
        FightGui fightGui = new FightGui(fightSystem,player, dialougeSystem);
        RoomGui roomGui = new RoomGui(fightGui,saving,player);
        PlayerMovement movement = new PlayerMovement(roomGui, fightGui);

        roomGui.addKeyListener(movement);
        fightGui.addKeyListener(movement);
    }
}



















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