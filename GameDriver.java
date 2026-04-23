import BossFight.BossFightGui;
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
        BossFightGui bossFight = new BossFightGui();
        RoomData roomContainer = new RoomData();

        //Combat
        BasicFightingSystem basic_FS = new BasicFightingSystem(player, dialougeSystem, audio);
        AdvancedFightingSystem advanced_FS = new AdvancedFightingSystem(player, dialougeSystem, audio);
        FightingGui fightGui = new FightingGui(basic_FS, advanced_FS, player, dialougeSystem,audio);
        
        //Rooms
        RoomGui roomGui = new RoomGui(saving, player, roomContainer);

        PlayerInput input = new PlayerInput(roomGui, fightGui,bossFight,roomContainer);

        roomGui.addKeyListener(input);
        fightGui.addKeyListener(input);

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