package GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerMovement implements KeyListener 
{
    RoomGui gui;

    public PlayerMovement(RoomGui r)
    {
        this.gui = r;
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_LEFT -> gui.enteredRoom(1);
            case KeyEvent.VK_RIGHT -> gui.enteredRoom(0);
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