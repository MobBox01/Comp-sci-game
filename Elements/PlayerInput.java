package Elements;

import BossFight.BossFightSystem;
import BossFight.BossFightWindow;
import FightHandling.AdvancedFightingSystem;
import FightHandling.BasicFightingSystem;
import Stats.Layout;
import Stats.Player;
import TheEnd.EndingWindow;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

@SuppressWarnings("FieldMayBeFinal")
public class PlayerInput extends JFrame implements KeyListener 
{
    //Other
    private Layout layout;
    private BossFightWindow bossFightWindow;
    private BossFightSystem bossFightSystem;
    private MainWindow mainWindow;
    private AudioPlayer audioPlayer;
    private BasicFightingSystem basic_FS;
    private AdvancedFightingSystem advanced_FS;
    private Player player;
    private EndingWindow endingWindow;
    private DialougeMapping storedDialouge = new DialougeMapping();
    

    //Booleans
    private boolean fightStatus = false;
    private boolean debounce = false; 
    private boolean alreadyChecked = false;
    private boolean playingFinalArea = false;


    
    public PlayerInput(MainWindow windowPass,BossFightWindow bossFightingPass,BossFightSystem bossFightSystemPass, Layout layoutPass,AudioPlayer audioPlayerPass, BasicFightingSystem basic_FSPass, AdvancedFightingSystem advanced_FSPass,Player playerPass,EndingWindow endingWindowPass)
    {
        this.endingWindow = endingWindowPass;
        this.bossFightSystem = bossFightSystemPass;
        this.player = playerPass;
        this.basic_FS = basic_FSPass;
        this.advanced_FS = advanced_FSPass;
        this.mainWindow = windowPass;
        this.bossFightWindow = bossFightingPass;
        this.layout = layoutPass;
        this.audioPlayer = audioPlayerPass;
    }

    /**
     * @param keyEvent Move the player
    */
    @Override
    public void keyPressed(KeyEvent keyEvent) 
    {
        //MainWindow
        if(!layout.isBossRoom() && !debounce && !mainWindow.isDialougeActive() && !layout.isEndingRooms())
        {
            debounceStart();
            double random = Math.random();

            //Room Movement
            if(!checkRoom())
            {
                switch(keyEvent.getKeyCode())
                {
                    case KeyEvent.VK_LEFT -> mainWindow.movePlayer(-1, 0);
                    case KeyEvent.VK_RIGHT -> mainWindow.movePlayer(1,0);
                    case KeyEvent.VK_UP -> mainWindow.movePlayer(0, 1);
                    case KeyEvent.VK_DOWN -> mainWindow.movePlayer(0, -1);
                }

                if(random < .03 && !layout.isAdvancedRooms() && !(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) && !layout.isFinalRooms())
                {
                    basic_FS.enemyEncounter();
                    mainWindow.updateStatus();
                    audioPlayer.setFightAudio(0);
                }
                else if(random < .06 && layout.isAdvancedRooms() && !(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) && !layout.isFinalRooms())
                {
                    advanced_FS.enemyEncounter();
                    mainWindow.updateStatus();
                    audioPlayer.setFightAudio(1);
                }
            }
            //Fighting movement
            else
            {
                switch (keyEvent.getKeyCode()) 
                {
                    case KeyEvent.VK_LEFT -> mainWindow.moveSelector(-1);
                    case KeyEvent.VK_RIGHT -> mainWindow.moveSelector(1);
                    case KeyEvent.VK_ENTER -> mainWindow.moveSelector(90);
                }
            }
        }
        //Boss Fight Window
        else if(layout.isBossRoom() && bossFightWindow.isVisible() && !bossFightWindow.dialougeStatus())
        {
            if(!bossFightSystem.isBossFightOver())
            {
                switch(keyEvent.getKeyCode())
                {
                    case KeyEvent.VK_LEFT -> bossFightWindow.movePlayer(-1);
                    case KeyEvent.VK_RIGHT -> bossFightWindow.movePlayer(1);
                    case KeyEvent.VK_ENTER -> bossFightWindow.movePlayer(90);
                }
            }
            else if(!bossFightWindow.moveOn() && keyEvent.getKeyCode() == KeyEvent.VK_ENTER && bossFightSystem.isBossFightOver())
            {
                bossFightWindow.defeatedSequence();
            }
        }
        //Ending Window
        else if(layout.isEndingRooms())
        {
            //Move to next room
            //Dialouge will work as normal, animations will play at specified rooms
            if(!endingWindow.isVisible())
            {
                mainWindow.setVisible(false);
                endingWindow.setVisible(true);
            }
            //ENTER, CHECK FOR DIALOUGE, SEE STATUS OF DIALOUGE
            else if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER && storedDialouge.isDialougeInRoom(layout.getRoomNumber()) && !storedDialouge.dialougeStatus() && !endingWindow.isDialougeActive() && !endingWindow.isAnimationActive())
            {
                endingWindow.endingDialouge(storedDialouge.getDialougeText());
                layout.nextRoom(1);

            }
            //ENTER, CHECK FOR ANIMATION OR OTHER, SEE STATUS OF ANIMATION OR OTHER
            else if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER && !storedDialouge.animationStatus() && storedDialouge.isAnimationInRoom(layout.getRoomNumber()))
            {
                endingWindow.playAnimation();
                layout.nextRoom(1);
            }             
        }
    
        //Visibility of boss room
        if(!bossFightWindow.isVisible() && layout.isBossRoom())
        {
            bossFightWindow.setVisible(true);
            mainWindow.setVisible(false);
            audioPlayer.setFightAudio(2);
        }
    }

    /**
     * Debounce prevents you from spamming your character movement
     */
    public void debounceStart()
    {
        int totalTime = 100;
        if(layout.isFinalRooms())
        {
            totalTime = 200;
        }
        debounce = true;
        Timer timer = new Timer(totalTime, time ->
            {
                ((Timer)time.getSource()).setRepeats(false);
                debounce = false;
            }
        );

        timer.start();
    }

    /**
     * @return true if enemies are alive and fight is active<p>
     * Sets the room audio back to their dedicated room after defeat<p> 
     * <b>Contains final room dialouge</b>
     */
    public boolean checkRoom()
    {
        //Are enemies alive
        if(basic_FS.isEnemyAlive() || advanced_FS.isEnemyAlive())
        {
            alreadyChecked = false;
            fightStatus = true;
        }
        //If basic enemy is dead
        else if(!basic_FS.isEnemyAlive() && !layout.isAdvancedRooms() && !alreadyChecked && !layout.isFinalRooms())
        {
            alreadyChecked = true;
            fightStatus = false;
            audioPlayer.setRoomAudio(0);
        }
        //If advanced enemy is dead
        else if(!advanced_FS.isEnemyAlive() && !layout.isBossRoom() && !alreadyChecked && !layout.isFinalRooms())
        {
            alreadyChecked = true;
            fightStatus = false;
            audioPlayer.setRoomAudio(1);
        }
        //If its the final rooms
        else if(layout.isFinalRooms())
        {
            //Check if sound is playing for this area, only exception!
            if(!playingFinalArea) 
            {
                playingFinalArea = true;
                audioPlayer.setRoomAudio(2);
                mainWindow.dialouge(storedDialouge.getDialougeText());
            }
            //If the room has a dialouge option, play it
            else if(storedDialouge.isDialougeInRoom(layout.getRoomNumber()) && !storedDialouge.dialougeStatus()) 
            {
                mainWindow.dialouge(storedDialouge.getDialougeText());
            }
        }

        if(!player.isAlive() && !mainWindow.isDialougeActive())
        {
            JOptionPane.showMessageDialog(null,"=={YOU DIED}==","GAME OVER",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return fightStatus;
    }


    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyReleased(KeyEvent e){}
}