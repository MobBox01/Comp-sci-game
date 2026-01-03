package RoomGui;

import FightingGui.FightGui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class PlayerMovement extends JFrame implements KeyListener 
{
    //Classes
    RoomGui gui;
    FightGui fgui;
    
    //Booleans
    boolean battleStatus;

    public PlayerMovement(RoomGui r,FightGui f)
    {
        this.gui = r;
        this.fgui = f;
    }


    @Override
    public void keyPressed(KeyEvent e) 
    {
        
        if(!fgui.fightCheck())
        {
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_LEFT -> gui.movePlayer(-1, 0);
                case KeyEvent.VK_RIGHT -> gui.movePlayer(1,0);
                case KeyEvent.VK_UP -> gui.movePlayer(0, 1);
                case KeyEvent.VK_DOWN -> gui.movePlayer(0, -1);
            }
        }
        else if(fgui.fightCheck())
        {
            switch (e.getKeyCode()) 
            {
                case KeyEvent.VK_LEFT -> fgui.movePlayer(-1);
                case KeyEvent.VK_RIGHT -> fgui.movePlayer(1);
                case KeyEvent.VK_ENTER -> fgui.movePlayer(90);
            }
        }
    }

































    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }


    
}