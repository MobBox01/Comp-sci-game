import java.io.IOException;
import java.util.Scanner;

public class TestDriver
{

    public static void main(String[] args) throws IOException  
    {

        Scanner sc = new Scanner(System.in);
        ProgressSaving savingTest = new ProgressSaving();
        Player playerTest = new Player(savingTest.obtainSavePoint());
        Enemy enemyTest = new Enemy(8, .4, 100, "nuclear bomb", 900);
        RoomGUI gui = new RoomGUI();


   
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