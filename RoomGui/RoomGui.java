package RoomGui;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import FightingGui.FightGui;

public class RoomGui extends JFrame 
{

    private ArrayList<JPanel> panel = new ArrayList<>();
    
    private Rooms rooms = new Rooms();
    private int[][] currentRoom;
    private int x = 15;
    private int y = 15;
    private int product = x*y;
    private int roomCounter = 0;
    private int tileUnderPlayer = 1;
    private FightGui fgui;
    /**
     * Sets up window and starting room
    */


    public RoomGui(FightGui e) 
    {
        fgui = e;
        setTitle("Void Game");
        setSize(750, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(new GridLayout(x, y, 0, 0));

        for (int i = 0; i < product; i++) 
        {
            JPanel p = new JPanel();
            p.setLayout(new BorderLayout());
            panel.add(p);
            add(p);
        }

        currentRoom = rooms.obtainRoom(0);
        buildRoom();

        setVisible(true);
    }


    /**
     * @param roomNumber
     * Obtain room, then build it
     */
    public void enteredRoom(int roomNumber)
    {
        System.out.println("Entered room number: {" + roomNumber + "}");
        currentRoom = rooms.obtainRoom(roomNumber);
        buildRoom();
    }


    /**
     * @param dx -> 1 tile left/right (Left == -1) (Right == 1)
     * @param dy -> 1 tile Up/Down  (Down == -1) (Up == 1)
     * [IF] User encounter square 10, move to next room
     * [IF] USer encounters square 11, move back a room
     */
    public void movePlayer(int dx,int dy)
    {
        int[] playerFound = new int[2];

        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                if(currentRoom[i][j] == 90)
                {
                    playerFound[0] = i;
                    playerFound[1] = j;
                }
            }    
        }

        int row = playerFound[0];
        int collumn = playerFound[1];

        switch(dx)
        {
            case 1 -> 
            {//Right
                int targetTile = currentRoom[row][collumn + 1];

                if (targetTile == 1 || targetTile == 2) 
                {
                    currentRoom[row][collumn] = tileUnderPlayer;
                    tileUnderPlayer = targetTile;
                    currentRoom[row][collumn + 1] = 90;
                }
                else if(targetTile == 10)
                {
                    roomCounter++;
                    enteredRoom(roomCounter);
                }
            }//Right
            case -1 -> 
            {//Left
                int targetTile = currentRoom[row][collumn - 1];

                if (targetTile == 1 || targetTile == 2) 
                {
                    currentRoom[row][collumn] = tileUnderPlayer;
                    tileUnderPlayer = targetTile;
                    currentRoom[row][collumn - 1] = 90;
                }
                else if(targetTile == 11)
                {
                    roomCounter--;
                    enteredRoom(roomCounter);
                }
            }//Left
        }

        switch(dy)
        {
            case 1 -> 
            {
                int targetTile = currentRoom[row -1 ][collumn];

                if (targetTile == 1 || targetTile == 2) 
                {
                    currentRoom[row][collumn] = tileUnderPlayer;
                    tileUnderPlayer = targetTile;
                    currentRoom[row-1][collumn] = 90;
                }

            }
            case -1 ->
            {
                int targetTile = currentRoom[row + 1][collumn];

                if (targetTile == 1 || targetTile == 2) 
                {
                    currentRoom[row][collumn] = tileUnderPlayer;
                    tileUnderPlayer = targetTile;
                    currentRoom[row + 1][collumn] = 90;
                }
            }
        }
        if((int)(Math.random()*1000) <= 10)
        {
            fgui.fightSet(true);
        }
        buildRoom();
    }

    /**
     * Index records as you loop preventing improper iteration 
     * <p>
     * Cleans up all tile panels
     * <p>
     * Loops until each tile is filled
     */
    private void buildRoom() 
    {
        int index = 0;
        for (int i = 0; i < x; i++) 
        {
            for (int j = 0; j < y; j++) 
            {
                panel.get(index).removeAll();
                
                switch (currentRoom[i][j]) 
                {
                    case 0 -> panel.get(index).setBackground(Color.BLACK);
                    case 1 -> setTile("Sprites/GrassPlain.png", index);
                    case 2 -> setTile("Sprites/GrassBlades.png", index);
                    case -1 -> setGifTile("Sprites/VoidHeart.gif", index);
                    case 90 -> setTile("Sprites/Logo.png", index);
                    case -10 -> setGifTile("Sprites/LightProducer.gif", index);
                    case 10 -> panel.get(index).setBackground(Color.BLUE);
                    case 11 -> panel.get(index).setBackground(Color.CYAN);
                    default -> panel.get(index).setBackground(Color.RED);
                }

                index++;
            }
        }
        revalidate();
        repaint();
    }

    /**
     * @param tilePath String for picture of tile
     * @param index Index of the [x]x[y] for loop
     * Set each tile and center them
     * [IF] image not found or error; set tile to magenta to spot it easily
     */
    private void setTile(String tilePath, int index)
    {
        ImageIcon icon = new ImageIcon(tilePath);
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel l = new JLabel(new ImageIcon(img));
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        panel.get(index).add(l, BorderLayout.CENTER);  
        panel.get(index).setBackground(Color.magenta);
    }

     /**
     * Set an animated GIF tile on the panel at index
     * @param gifPath Path to the animated GIF
     * @param index Index of the tile in the panel list
     */
    private void setGifTile(String gifPath, int index)
    {
        ImageIcon gifIcon = new ImageIcon(gifPath); 
        JLabel label = new JLabel(gifIcon);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        panel.get(index).removeAll();
        panel.get(index).add(label, BorderLayout.CENTER);
        panel.get(index).setBackground(Color.magenta);
    }

}
