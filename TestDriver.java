import GUI.PlayerMovement;
import GUI.RoomGui;
import Saving.ProgressSaving;
import Stats.Enemy;
import Stats.Player;
import java.io.IOException;

public class TestDriver
{

    public static void main(String[] args) throws IOException  
    {

        RoomGui gui = new RoomGui();
        ProgressSaving saving = new ProgressSaving();
        PlayerMovement e = new PlayerMovement(gui);
        Enemy enemy1 = new Enemy(0, 1.0, 0, "Balls", 0);
        Player player = new Player(saving.obtainSavePoint());


        gui.addKeyListener(e);


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