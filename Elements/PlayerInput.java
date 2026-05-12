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
    private Layout layout;
    private BossFightWindow bossFightWindow;
    private BossFightSystem bossFightSystem;
    private boolean debounce = false; 
    private MainWindow mainWindow;
    private AudioPlayer audioPlayer;
    private BasicFightingSystem basic_FS;
    private AdvancedFightingSystem advanced_FS;
    private Player player;

    private boolean fightStatus = false;
    private boolean alreadyChecked = false;
    
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
     * @param keyEvent 
     * <p>
     * Checks if player is in a fight.
     * <p>
     * [IF] Player is in a fight state, any events passed in will connect with the top right second biggest window
     * <p>
     * [IF] Player isnt in a fight state, any events passed in will connect with the left window screen.
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) 
    {
        if(!fightCheck() && !layout.isBossRoom() && !debounce && !mainWindow.isDialougeActive())
        {
            debounceStart();
            switch(keyEvent.getKeyCode())
            {
                case KeyEvent.VK_LEFT -> mainWindow.movePlayer(-1, 0);
                case KeyEvent.VK_RIGHT -> mainWindow.movePlayer(1,0);
                case KeyEvent.VK_UP -> mainWindow.movePlayer(0, 1);
                case KeyEvent.VK_DOWN -> mainWindow.movePlayer(0, -1);
            }

            if((int)(Math.random()*4000) <= 30 && !layout.isAdvancedRooms() && !(keyEvent.getKeyCode() == KeyEvent.VK_ENTER))
            {
                fightSet(0);
                audioPlayer.setFightAudio(0);
            }
            else if((int)(Math.random()*2000) <= 50 && layout.isAdvancedRooms() && !(keyEvent.getKeyCode() == KeyEvent.VK_ENTER))
            {
                fightSet(1);
                audioPlayer.setFightAudio(1);
            }
        }
        else if(fightCheck() && !layout.isBossRoom() && !debounce && !mainWindow.isDialougeActive())
        {
            debounceStart();
            switch (keyEvent.getKeyCode()) 
            {
                case KeyEvent.VK_LEFT -> mainWindow.moveSelector(-1);
                case KeyEvent.VK_RIGHT -> mainWindow.moveSelector(1);
                case KeyEvent.VK_ENTER -> mainWindow.moveSelector(90);
            }
        }
        else if(layout.isBossRoom() && bossFightWindow.dialougeStatus() == false && !bossFightSystem.isBossFightOver())
        {
            switch(keyEvent.getKeyCode())
            {
                case KeyEvent.VK_LEFT -> bossFightWindow.movePlayer(-1);
                case KeyEvent.VK_RIGHT -> bossFightWindow.movePlayer(1);
                case KeyEvent.VK_ENTER -> bossFightWindow.movePlayer(90);
            }
        }
        else if(bossFightSystem.isBossFightOver() && !bossFightWindow.dialougeStatus() && !bossFightWindow.moveOn())
        {
            bossFightWindow.defeatedSequence();
        }
        else if(bossFightSystem.isBossFightOver() && bossFightWindow.moveOn())
        {
            bossFightWindow.setVisible(false);
            mainWindow.setVisible(true);
            mainWindow.enteredRoom();
            audioPlayer.setRoomAudio(1);
        }
        if(!bossFightWindow.isVisible() && layout.isBossRoom())
        {
            bossFightWindow.setVisible(true);
            mainWindow.setVisible(false);
            audioPlayer.setFightAudio(2);
        }
    }

    public void debounceStart()
    {
        debounce = true;
        Timer timer = new Timer(100, time ->
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
                mainWindow.setBasicEnemyFrame();
                mainWindow.buildFightContainer();
            }
            
            case 1 ->
            {
                advanced_FS.enemyEncounter();
                mainWindow.setAdvancedEnemyFrame();
                mainWindow.buildFightContainer();
            }
        }
    }
    /**
     * @return check if a fight is ongoing
     * [IF] Enemy is alive -> True
     * [ELSE-IF] Enemy isnt alive -> False
     * [IF] Player dies -> Show a error message, kill the program once X is or "OK" is pressed
     */
    public boolean fightCheck()
    {
        if(basic_FS.isEnemyAlive() || advanced_FS.isEnemyAlive())
        {
            alreadyChecked = false;
            fightStatus = true;
        }
        else if(!basic_FS.isEnemyAlive() && !layout.isAdvancedRooms() && !alreadyChecked)
        {
            alreadyChecked = true;
            fightStatus = false;
            mainWindow.setfightRoomFrame(400);
            mainWindow.buildFightContainer();
            audioPlayer.setRoomAudio(0);
        }
        else if(!advanced_FS.isEnemyAlive() && !layout.isBossRoom() && !alreadyChecked)
        {
            alreadyChecked = true;
            fightStatus = false;
            mainWindow.setfightRoomFrame(400);
            mainWindow.buildFightContainer();
            audioPlayer.setRoomAudio(1);
        }

        if(!player.isAlive())
        {
            JOptionPane.showMessageDialog(null,"YOU DIED","GAME OVER",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return fightStatus;
    }


    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyReleased(KeyEvent e){}
}