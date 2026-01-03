package RoomGui;
import FightingGui.FightGui;
import Saving.ProgressSaving;
import Stats.Player;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class RoomGui extends JFrame 
{

    //Classes & Arrays
    private int[][] currentRoom;
    private ArrayList<JPanel> panel = new ArrayList<>();
    private Rooms rooms = new Rooms();
    private FightGui fightingGui;
    private ProgressSaving progressSaving;
    private Player player;

    //Room Size - Constants
    private final int x = 10;
    private final int y = 10;
    private final int product = x*y;

    //Rooms
    private int roomCounter = 0;
    private int tileUnderPlayer = 1;
    private int playerRow;
    private int playerCollumn;

    //Constants
    private static final int VOID = 0;
    private static final int GRASS = 1;
    private static final int GRASS_BLADES = 2;
    private static final int PLAYER = 90;
    private static final int NEXT_ROOM = 10;
    private static final int LAST_ROOM = 11;
    private static final int VOID_HEART = -1;
    private static final int LIGHT_PRODUCER = -10;

    /**
     * Sets up window and starting room
    */
    public RoomGui(FightGui fightGuiPass,ProgressSaving progressPass,Player playerPass) 
    {
        fightingGui = fightGuiPass;
        progressSaving = progressPass;
        player = playerPass;

        setTitle("Room Number: [0] VOID GAME");
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

        currentRoom = rooms.obtainRoom(progressSaving.obtainSavePoint()[2]);
        roomCounter = progressSaving.obtainSavePoint()[2];
        findPlayer();
        buildRoom();

        setVisible(true);
    }


    /**
     * @param roomNumber
     * Obtain new room, then build it
     */
    public void enteredRoom(int roomNumber)
    {
        System.out.println("Entered room number: {" + roomNumber + "}");
        setTitle("Room Number: [" + roomNumber + "] VOID GAME");
        currentRoom = rooms.obtainRoom(roomNumber);
        findPlayer();
        progressSaving.setSavePoint(player.getLevel(),player.getXP(),roomNumber);

        
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
        if (dx != 0) movePlayerX(dx);
        if (dy != 0) movePlayerY(dy);

        if((int)(Math.random()*1000) <= 3)
        {
            fightingGui.fightSet(true);
        }
        buildRoom();
    }

    /**
     * Find the players current X, Y position when entering a room.
     */
    private void findPlayer()
    {
        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                if(currentRoom[i][j] == PLAYER)
                {
                    playerRow = i;
                    playerCollumn = j;
                }
            }    
        }
    }

    /**
     * @param dx
     * Move player to the right or left 
     * [1] -> Right
     * [-1] -> Left
     */
    private void movePlayerX(int dx)
    {
        int targetTile = currentRoom[playerRow][playerCollumn + dx];

        if (targetTile == 1 || targetTile == 2) 
        {
            currentRoom[playerRow][playerCollumn] = tileUnderPlayer; 
            tileUnderPlayer = targetTile;
            playerCollumn += dx;
            currentRoom[playerRow][playerCollumn] = PLAYER;
        }
        else if(targetTile == NEXT_ROOM)
        {
            roomCounter++;
            enteredRoom(roomCounter);
        }
        else if(targetTile == LAST_ROOM)
        {
            roomCounter--;
            enteredRoom(roomCounter);
        }
    }

    /**
     * @param dx
     * Move player to the right or left 
     * [1] -> Up
     * [-1] -> Down
     */
    private void movePlayerY(int dy)
    {
        int targetTile = currentRoom[playerRow - dy][playerCollumn];

        if (targetTile == 1 || targetTile == 2) 
        {
            currentRoom[playerRow][playerCollumn] = tileUnderPlayer;
            tileUnderPlayer = targetTile;
            playerRow -= dy;
            currentRoom[playerRow][playerCollumn] = PLAYER;
        }
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
                    case VOID -> panel.get(index).setBackground(Color.BLACK);
                    case GRASS -> setTile("Sprites/GrassPlain.png", index);
                    case GRASS_BLADES -> setTile("Sprites/GrassBlades.png", index);
                    case VOID_HEART -> setGifTile("Sprites/VoidHeart.gif", index);
                    case PLAYER -> setTile("Sprites/Logo.png", index);
                    case LIGHT_PRODUCER -> setGifTile("Sprites/LightProducer.gif", index);
                    case NEXT_ROOM -> panel.get(index).setBackground(Color.BLUE);
                    case LAST_ROOM -> panel.get(index).setBackground(Color.CYAN);
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
     * [IF] image not found or error; set tile to RED to spot it easily
     * 
     * TODO: Merge setGif and setTile into one method, this way itd be cleaner and more easier to work with
     */
    private void setTile(String tilePath, int index)
    {
        ImageIcon icon = new ImageIcon(tilePath);
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel l = new JLabel(new ImageIcon(img));

        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);

        panel.get(index).add(l, BorderLayout.CENTER);  
        panel.get(index).setBackground(Color.RED);
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
        panel.get(index).setBackground(Color.RED);
    }
}