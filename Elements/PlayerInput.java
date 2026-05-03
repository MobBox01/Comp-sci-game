package Elements;

import BossFight.BossFightGui;
import Stats.Layout;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class PlayerInput extends JFrame implements KeyListener 
{
    private Layout roomContainer;
    private BossFightGui bossFight;
    private boolean debounce = false; 
    private MainWindow window;

    
    public PlayerInput(MainWindow windowPass,BossFightGui bossFightingPass, Layout roomPass)
    {
        window = windowPass;
        this.bossFight = bossFightingPass;
        this.roomContainer = roomPass;
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
        if(!window.fightCheck() && !roomContainer.isBossRoom() && !debounce && !window.isDialougeActive())
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
                window.fightSet(true, "basic");
            }
            else if((int)(Math.random()*1000) <= 50 && roomContainer.isAdvancedRooms() && !(keyEvent.getKeyCode() == KeyEvent.VK_ENTER))
            {
                window.fightSet(true, "advanced");
            }
        }
        else if(window.fightCheck() && !roomContainer.isBossRoom() && !debounce && !window.isDialougeActive())
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


    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyReleased(KeyEvent e){}


    
}