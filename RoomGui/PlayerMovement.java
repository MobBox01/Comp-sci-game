package RoomGui;

import FightingGui.FightingGui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class PlayerMovement extends JFrame implements KeyListener 
{
    RoomGui roomGui;
    FightingGui fightingGui;
    
    boolean battleStatus;

    public PlayerMovement(RoomGui roomGuiPass,FightingGui fightingGuiPass)
    {
        this.roomGui = roomGuiPass;
        this.fightingGui = fightingGuiPass;
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
        
        if(!fightingGui.fightCheck())
        {
            switch(keyEvent.getKeyCode())
            {
                case KeyEvent.VK_LEFT -> roomGui.movePlayer(-1, 0);
                case KeyEvent.VK_RIGHT -> roomGui.movePlayer(1,0);
                case KeyEvent.VK_UP -> roomGui.movePlayer(0, 1);
                case KeyEvent.VK_DOWN -> roomGui.movePlayer(0, -1);
            }
        }
        else if(fightingGui.fightCheck())
        {
            switch (keyEvent.getKeyCode()) 
            {
                case KeyEvent.VK_LEFT -> fightingGui.movePlayer(-1);
                case KeyEvent.VK_RIGHT -> fightingGui.movePlayer(1);
                case KeyEvent.VK_ENTER -> fightingGui.movePlayer(90);
            }
        }
        
    }


    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyReleased(KeyEvent e){}


    
}