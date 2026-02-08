import java.io.IOException;

import Elements.Dialouge;
import Elements.AudioPlayer;
import Elements.PlayerInput;

import FightingGui.FightingGui;
import FightingGui.AdvancedFightingSystem;
import FightingGui.BasicFightingSystem;

import RoomHandling.RoomGui;

import Saving.ProgressSaving;
import Stats.Player;

public class GameDriver
{
    public static void main(String[] args) throws IOException
    {
        //Elements
        AudioPlayer audio = new AudioPlayer();
        ProgressSaving saving = new ProgressSaving();
        Dialouge dialougeSystem = new Dialouge();
        Player player = new Player(saving.obtainSavePoint());

        //Combat
        BasicFightingSystem basic_FS = new BasicFightingSystem(player, dialougeSystem, audio);
        AdvancedFightingSystem advanced_FS = new AdvancedFightingSystem(player, dialougeSystem, audio);
        FightingGui fightGui = new FightingGui(basic_FS, advanced_FS, player, dialougeSystem,audio);

        //Rooms
        RoomGui roomGui = new RoomGui(fightGui, saving, player);

        PlayerInput input = new PlayerInput(roomGui, fightGui);

        roomGui.addKeyListener(input);
        fightGui.addKeyListener(input);
    }
}

//[2/7/2026]: Factoring changes, added in advanced and basic fighting systems and some object memory addressess
//BUG: Advanced rooms have a enemy EACH tile you go on!














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