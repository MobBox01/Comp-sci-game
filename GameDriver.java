import BossFight.BossFightGui;
import BossFight.BossFightSystem;
import Elements.AudioPlayer;
import Elements.MainWindow;
import Elements.PlayerInput;
import FightHandling.AdvancedFightingSystem;
import FightHandling.BasicFightingSystem;
import RoomHandling.RoomData;
import Saving.ProgressSaving;
import Stats.Player;
import java.io.IOException;

public class GameDriver
{
    public static void main(String[] args) throws IOException
    {
        //Elements
        AudioPlayer audio = new AudioPlayer();
        ProgressSaving saving = new ProgressSaving();
        Player player = new Player(saving.obtainSavePoint());
        RoomData roomContainer = new RoomData();
        BossFightSystem bossSystem = new BossFightSystem(player, audio);

        //Combat
        BasicFightingSystem basic_FS = new BasicFightingSystem(player, null, audio);
        AdvancedFightingSystem advanced_FS = new AdvancedFightingSystem(player, null, audio);
        

        MainWindow window = new MainWindow(saving, player, roomContainer, basic_FS, advanced_FS, audio);

        basic_FS.setWindow(window);
        advanced_FS.setWindow(window);


        BossFightGui bossFight = new BossFightGui(bossSystem);
        PlayerInput input = new PlayerInput(window, bossFight, roomContainer);

        window.addKeyListener(input);
        bossFight.addKeyListener(input);

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