import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
public class PlayerMovement implements KeyListener 
{
    RoomGUI gui;

    public PlayerMovement(RoomGUI r)
    {
        this.gui = r;
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_LEFT -> gui.movePlayer(-1, 0);
            case KeyEvent.VK_RIGHT -> gui.movePlayer(1,0);
            case KeyEvent.VK_UP -> gui.movePlayer(0, 1);
            case KeyEvent.VK_DOWN -> gui.movePlayer(0, -1);
        }
    }

































    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }


    
}