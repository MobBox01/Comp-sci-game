package RoomGui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import FightingGui.FightGui;
public class PlayerMovement extends JFrame implements KeyListener 
{
    RoomGui gui;
    FightGui fgui;
    boolean battleStatus;

    public PlayerMovement(RoomGui r)
    {
        this.gui = r;
    }

    public void fightTime(FightGui r)
    {
        this.fgui = r;
    }


    @Override
    public void keyPressed(KeyEvent e) 
    {
        if(!fgui.fight())
        {
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_LEFT -> gui.movePlayer(-1, 0);
                case KeyEvent.VK_RIGHT -> gui.movePlayer(1,0);
                case KeyEvent.VK_UP -> gui.movePlayer(0, 1);
                case KeyEvent.VK_DOWN -> gui.movePlayer(0, -1);
            }
        }
        else if(fgui.fight())
        {
            switch (e.getKeyCode()) 
            {
                case KeyEvent.VK_LEFT -> fgui.movePlayer(-1, 0);
                case KeyEvent.VK_RIGHT -> fgui.movePlayer(1, 0);
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