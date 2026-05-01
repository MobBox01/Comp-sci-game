import BossFight.BossFightGui;
import BossFight.BossFightSystem;
import Elements.AudioPlayer;
import Elements.Dialouge;
import Elements.PlayerInput;
import FightHandling.AdvancedFightingSystem;
import FightHandling.BasicFightingSystem;
import FightHandling.FightingGui;
import RoomHandling.RoomData;
import RoomHandling.RoomGui;
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
        Dialouge dialougeSystem = new Dialouge();
        Player player = new Player(saving.obtainSavePoint());
        RoomData roomContainer = new RoomData();
        BossFightSystem bossSystem = new BossFightSystem(player, dialougeSystem, audio);

        //Combat
        BasicFightingSystem basic_FS = new BasicFightingSystem(player, dialougeSystem, audio);
        AdvancedFightingSystem advanced_FS = new AdvancedFightingSystem(player, dialougeSystem, audio);

        FightingGui fightGui = new FightingGui(basic_FS, advanced_FS, player, dialougeSystem,audio);
        BossFightGui bossFight = new BossFightGui(bossSystem);

        //Rooms
        RoomGui roomGui = new RoomGui(saving, player, roomContainer);

        //Input
        PlayerInput input = new PlayerInput(roomGui, fightGui,bossFight,roomContainer,dialougeSystem);

        roomGui.addKeyListener(input);        
        fightGui.addKeyListener(input);
        bossFight.addKeyListener(input);
    }
}
/**
 * 5/1/2026 Log: 
 * Made the dialouge text box utilize a timer to display text at a 10-20 milisecond interval this should be fast enough that 
 * you can see it.
 */













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