package Elements;

import BossFight.BossFightGui;
import FightHandling.FightingGui;
import RoomHandling.RoomData;
import RoomHandling.RoomGui;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class PlayerInput extends JFrame implements KeyListener 
{
    RoomGui roomGui;
    FightingGui fightingGui;
    RoomData roomContainer;
    BossFightGui bossFight;

    
    public PlayerInput(RoomGui roomGuiPass, FightingGui fightingGuiPass,BossFightGui bossFightingPass, RoomData roomPass)
    {
        this.roomGui = roomGuiPass;
        this.fightingGui = fightingGuiPass;
        this.bossFight = bossFightingPass;
        roomContainer = roomPass;
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
        /*if(!fightingGui.fightCheck())
        {
            switch(keyEvent.getKeyCode())
            {
                case KeyEvent.VK_LEFT -> roomGui.movePlayer(-1, 0);
                case KeyEvent.VK_RIGHT -> roomGui.movePlayer(1,0);
                case KeyEvent.VK_UP -> roomGui.movePlayer(0, 1);
                case KeyEvent.VK_DOWN -> roomGui.movePlayer(0, -1);
            }

            if((int)(Math.random()*1000) <= 30 && roomContainer.isAdvancedRooms(roomGui.currentRoom()) == false && !(keyEvent.getKeyCode() == KeyEvent.VK_ENTER))
            {
                fightingGui.fightSet(true, "basic");
            }
            else if((int)(Math.random()*1000) <= 50 && roomContainer.isAdvancedRooms(roomGui.currentRoom()) == true&& !(keyEvent.getKeyCode() == KeyEvent.VK_ENTER))
            {
                fightingGui.fightSet(true, "advanced");
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
            */
        if(bossFight.isBossFight() && bossFight.dialougeStatus() == false)
        {
            switch(keyEvent.getKeyCode())
            {
                case KeyEvent.VK_LEFT -> bossFight.movePlayer(-1);
                case KeyEvent.VK_RIGHT -> bossFight.movePlayer(1);
                //case KeyEvent.VK_ENTER -> bossFight.movePlayer(90);
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyReleased(KeyEvent e){}


    
}