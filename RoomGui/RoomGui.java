package RoomGui;
import FightingGui.FightGui;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class RoomGui extends JFrame 
{

    private ArrayList<JPanel> panel = new ArrayList<>();
    
    private Rooms rooms = new Rooms();
    private FightGui fgui;

    private int[][] currentRoom;
    
    private int x = 10;
    private int y = 10;
    private int product = x*y;
    private int roomCounter = 0;
    private int tileUnderPlayer = 1;

    //CONSTANTS
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
    public RoomGui(FightGui e) 
    {
        fgui = e;
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
        setTitle("Room Number: [" + roomNumber + "] VOID GAME");
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
                if(currentRoom[i][j] == PLAYER)
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
                    currentRoom[row][collumn + 1] = PLAYER;
                }
                else if(targetTile == NEXT_ROOM)
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
                    currentRoom[row][collumn - 1] = PLAYER;
                }
                else if(targetTile == LAST_ROOM)
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
                    currentRoom[row-1][collumn] = PLAYER;
                }

            }
            case -1 ->
            {
                int targetTile = currentRoom[row + 1][collumn];

                if (targetTile == 1 || targetTile == 2) 
                {
                    currentRoom[row][collumn] = tileUnderPlayer;
                    tileUnderPlayer = targetTile;
                    currentRoom[row + 1][collumn] = PLAYER;
                }
            }
        }
        if((int)(Math.random()*1000) <= 20)//~2% chance for encounter will be later readjusted
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
        panel.get(index).setBackground(Color.red);
    }

}
