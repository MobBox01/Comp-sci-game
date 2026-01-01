import java.io.IOException;

import FightingGui.FightGui;
import RoomGui.PlayerMovement;
import RoomGui.RoomGui;

public class TestDriver
{
    public static void main(String[] args) throws IOException
    {

        FightGui fgui = new FightGui();
        RoomGui gui = new RoomGui(fgui);
        PlayerMovement movement = new PlayerMovement(gui);
        movement.fightTime(fgui);
        gui.addKeyListener(movement);
        fgui.addKeyListener(movement);
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