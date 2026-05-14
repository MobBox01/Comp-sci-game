package Elements;

import BossFight.BossFightSystem;
import BossFight.BossFightWindow;
import FightHandling.AdvancedFightingSystem;
import FightHandling.BasicFightingSystem;
import Stats.Layout;
import Stats.Player;
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

    //Booleans
    private boolean fightStatus = false;
    private boolean debounce = false; 
    private boolean alreadyChecked = false;
    private boolean playingFinalArea = true;
    private boolean[] finalRoomDialouge = {false, false, false, false, false};
    
    public PlayerInput(MainWindow windowPass,BossFightWindow bossFightingPass,BossFightSystem bossFightSystemPass, Layout layoutPass,AudioPlayer audioPlayerPass, BasicFightingSystem basic_FSPass, AdvancedFightingSystem advanced_FSPass,Player playerPass)
    {
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
        if(!layout.isBossRoom() && !debounce && !mainWindow.isDialougeActive())
        {
            debounceStart();
            double random = Math.random();

            //Room Movement
            if(!fightCheck())
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
                    fightSet(0);
                    mainWindow.updateStatus();
                    audioPlayer.setFightAudio(0);
                }
                else if(random < .06 && layout.isAdvancedRooms() && !(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) && !layout.isFinalRooms())
                {
                    fightSet(1);
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
            totalTime = 400;
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

    public void fightSet(int complexity)
    {
        switch(complexity)
        {
            case 0 -> 
            {
                basic_FS.enemyEncounter();
            }
            
            case 1 ->
            {
                advanced_FS.enemyEncounter();
            }
        }
    }
    /**
     * @return true if enemies are alive and fight is active<p>
     * Sets the room audio back to their dedicated room after defeat<p> 
     * <b>Contains final room dialouge</b>
     */
    public boolean fightCheck()
    {
        if(basic_FS.isEnemyAlive() || advanced_FS.isEnemyAlive())
        {
            alreadyChecked = false;
            fightStatus = true;
        }
        else if(!basic_FS.isEnemyAlive() && !layout.isAdvancedRooms() && !alreadyChecked && !layout.isFinalRooms())
        {
            alreadyChecked = true;
            fightStatus = false;
            audioPlayer.setRoomAudio(0);
        }
        else if(!advanced_FS.isEnemyAlive() && !layout.isBossRoom() && !alreadyChecked && !layout.isFinalRooms())
        {
            alreadyChecked = true;
            fightStatus = false;
            audioPlayer.setRoomAudio(1);
        }
        else if(layout.isFinalRooms())
        {
            if(playingFinalArea)
            {
                playingFinalArea = false;
                audioPlayer.setRoomAudio(2);
                mainWindow.dialouge("Its the final strech... Ferreto don't give up. Destroy that portal. T-10 minutes before demon appearance. You defeated a majority of enemies they are to afraid. Your to powerful for them all i have provided you the light of concealment the paths are now linear.");
                finalRoomDialouge[0] = true;
            }
            else if(layout.getRoomNumber() == 11 && !finalRoomDialouge[1])
            {
                mainWindow.dialouge("Ferreto.... the energy is to much. If you need to take a break do so, your journey was long (Even though gameplay is like 5 minutes long)... Stay focussed the world depends on you the other heros are soon to destroy their destined portals to!");
                finalRoomDialouge[1] = true;
            }
            else if(layout.getRoomNumber() == 12 && !finalRoomDialouge[2])
            {
                mainWindow.dialouge("So like anyway, why the hell didn't you do your computer science homework? Literally was befuddled throughout the entire boss encounter, i mean you got like 6666 XP so thats a win but still.............................................................");
                finalRoomDialouge[2] = true;
            }
            else if(layout.getRoomNumber() == 13 && !finalRoomDialouge[3])
            {
                mainWindow.dialouge("{{Ferreto}: Oh my god, STOP ASKING ME! I didn't feel like it. They're views have changed now so something positive came out of it, now lets stop this deomn from forming}\n{666}: Fine");
                finalRoomDialouge[3] = true;
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