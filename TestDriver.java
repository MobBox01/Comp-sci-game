import java.io.IOException;
import java.util.Scanner;

import Unused.Enemy;
import Unused.Player;
import Unused.ProgressSaving;

public class TestDriver
{

    public static void main(String[] args) throws IOException  
    {

        RoomGUI gui = new RoomGUI();
        PlayerMovement e = new PlayerMovement(gui);
        gui.addKeyListener(e);


        gui.enteredRoom(1);  
        gui.enteredRoom(0);    




     
   
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