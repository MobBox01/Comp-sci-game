package Elements;

import BossFight.BossFightGui;
import FightHandling.AdvancedFightingSystem;
import FightHandling.BasicFightingSystem;
import Stats.Layout;
import Stats.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class PlayerInput extends JFrame implements KeyListener 
{
    private Layout roomContainer;
    private BossFightGui bossFight;
    private boolean debounce = false; 
    private MainWindow window;
    private AudioPlayer audio;
    private BasicFightingSystem basic_FS;
    private AdvancedFightingSystem advanced_FS;
    private Player player;

    private boolean fightStatus = false;
    private boolean alreadyChecked = false;
    
    public PlayerInput(MainWindow windowPass,BossFightGui bossFightingPass, Layout roomPass,AudioPlayer audioPass, BasicFightingSystem basic_FSPass, AdvancedFightingSystem advanced_FSPass,Player playerPass)
    {
        this.player = playerPass;
        this.basic_FS = basic_FSPass;
        this.advanced_FS = advanced_FSPass;
        this.window = windowPass;
        this.bossFight = bossFightingPass;
        this.roomContainer = roomPass;
        this.audio = audioPass;
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
        if(!fightCheck() && !roomContainer.isBossRoom() && !debounce && !window.isDialougeActive())
        {
            debounceStart();
            switch(keyEvent.getKeyCode())
            {
                case KeyEvent.VK_LEFT -> window.movePlayer(-1, 0);
                case KeyEvent.VK_RIGHT -> window.movePlayer(1,0);
                case KeyEvent.VK_UP -> window.movePlayer(0, 1);
                case KeyEvent.VK_DOWN -> window.movePlayer(0, -1);
            }

            if((int)(Math.random()*1000) <= 30 && !roomContainer.isAdvancedRooms() && !(keyEvent.getKeyCode() == KeyEvent.VK_ENTER))
            {
                fightSet(0);
                audio.setFightAudio(0);
            }
            else if((int)(Math.random()*1000) <= 50 && roomContainer.isAdvancedRooms() && !(keyEvent.getKeyCode() == KeyEvent.VK_ENTER))
            {
                fightSet(1);
                audio.setFightAudio(1);
            }
        }
        else if(fightCheck() && !roomContainer.isBossRoom() && !debounce && !window.isDialougeActive())
        {
            debounceStart();
            switch (keyEvent.getKeyCode()) 
            {
                case KeyEvent.VK_LEFT -> window.moveSelector(-1);
                case KeyEvent.VK_RIGHT -> window.moveSelector(1);
                case KeyEvent.VK_ENTER -> window.moveSelector(90);
            }
        }
        else if(roomContainer.isBossRoom() && bossFight.dialougeStatus() == false)
        {
            switch(keyEvent.getKeyCode())
            {
                case KeyEvent.VK_LEFT -> bossFight.movePlayer(-1);
                case KeyEvent.VK_RIGHT -> bossFight.movePlayer(1);
                case KeyEvent.VK_ENTER -> bossFight.movePlayer(90);
            }
        }
        if(!bossFight.isVisible() && roomContainer.isBossRoom())
        {
            bossFight.setVisible(true);
            window.setVisible(false);
            audio.setFightAudio(2);
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
                window.setBasicEnemyFrame();
                window.buildFightContainer();
            }
            
            case 1 ->
            {
                advanced_FS.enemyEncounter();
                window.setAdvancedEnemyFrame();
                window.buildFightContainer();
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
        else if(!basic_FS.isEnemyAlive() && !roomContainer.isAdvancedRooms() && !alreadyChecked)
        {
            alreadyChecked = true;
            fightStatus = false;
            window.setfightRoomFrame(400);
            window.buildFightContainer();
            audio.setRoomAudio(0);
        }
        else if(!advanced_FS.isEnemyAlive() && !roomContainer.isBossRoom() && !alreadyChecked)
        {
            alreadyChecked = true;
            fightStatus = false;
            window.setfightRoomFrame(400);
            window.buildFightContainer();
            audio.setRoomAudio(1);
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