package Elements;

import RoomHandling.RoomGui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import BossFight.TBD;
import FightHandling.FightingGui;

public class PlayerInput extends JFrame implements KeyListener 
{
    RoomGui roomGui;
    FightingGui fightingGui;
    TBD bossFight;
    
    boolean battleStatus;

    public PlayerInput(RoomGui roomGuiPass,FightingGui fightingGuiPass,TBD bossFightingPass)
    {
        this.roomGui = roomGuiPass;
        this.fightingGui = fightingGuiPass;
        this.bossFight = bossFightingPass;
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
        
        if(!fightingGui.fightCheck() && !bossFight.isBossFight())
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
        else if(bossFight.isBossFight())
        {//Dead code, as this is always false
            switch(keyEvent.getKeyCode())
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