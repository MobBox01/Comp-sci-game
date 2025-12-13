import Saving.ProgressSaving;
import Stats.Enemy;
import Stats.Player;
import java.io.IOException;

import FightingGui.FightGui;
import RoomGui.PlayerMovement;
import RoomGui.RoomGui;

public class TestDriver
{

    
    public static void main(String[] args) throws IOException  
    {

        RoomGui gui = new RoomGui();
        ProgressSaving saving = new ProgressSaving();
        PlayerMovement movement = new PlayerMovement(gui);
        @SuppressWarnings("unused")
        Enemy enemy1 = new Enemy(0, 1.0, 0, "Testing", 0);
        @SuppressWarnings("unused")
        Player player = new Player(saving.obtainSavePoint());
        FightGui test = new FightGui();

        movement.fightTime(test);
        gui.addKeyListener(movement);
        test.addKeyListener(movement);
        





     
   
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