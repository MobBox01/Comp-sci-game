import BossFight.BossFightSystem;
import BossFight.BossFightWindow;
import Elements.AudioPlayer;
import Elements.MainWindow;
import Elements.PlayerInput;
import FightHandling.AdvancedFightingSystem;
import FightHandling.BasicFightingSystem;
import Saving.ProgressSaving;
import Stats.Layout;
import Stats.Player;
import TheEnd.EndingWindow;
import java.io.IOException;

public class GameDriver
{
    public static void main(String[] args) throws IOException
    {
        //Elements
        AudioPlayer audioPlayer = new AudioPlayer();
        ProgressSaving progressSaving = new ProgressSaving();
        Player player = new Player(progressSaving.obtainSavePoint());
        Layout layout = new Layout();

        //Combat
        BasicFightingSystem basic_FS = new BasicFightingSystem(player, null);
        AdvancedFightingSystem advanced_FS = new AdvancedFightingSystem(player, null);
        
        //Main Window
        MainWindow window = new MainWindow(progressSaving, player, layout, basic_FS, advanced_FS);
        basic_FS.setWindow(window);
        advanced_FS.setMainWindow(window);

        //Boss Fight
        BossFightSystem bossFightSystem = new BossFightSystem(player);
        BossFightWindow bossFightWindow = new BossFightWindow(bossFightSystem, layout, audioPlayer, window);
        bossFightSystem.setGuiConnection(bossFightWindow);

        //Other
        EndingWindow endingWindow = new EndingWindow();

        //Inputs
        PlayerInput input = new PlayerInput(window, bossFightWindow, bossFightSystem, layout, audioPlayer, basic_FS, advanced_FS, player, endingWindow);
        window.addKeyListener(input);
        bossFightWindow.addKeyListener(input);
        endingWindow.addKeyListener(input);
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