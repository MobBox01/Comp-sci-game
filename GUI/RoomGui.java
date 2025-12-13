package GUI;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class RoomGui extends JFrame 
{

    private ArrayList<JPanel> panel = new ArrayList<>();
    
    private Rooms room = new Rooms();
    private int[][] currentRoom;
    private int x = 10;
    private int y = 10;
    private int product = x*y;
    private int roomCounter = 0;
    private int tileUnderPlayer = 1;

    /**
     * Sets up window and starting room
    */
    public RoomGui() 
    {
        
        setTitle("Void Game");
        setSize(750, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(x, y, 0, 0));

        for(int i = 0; i < product; i++)
        {
            panel.add(new JPanel());
            panel.get(i).setLayout(new BorderLayout());

        }

        currentRoom = room.obtainRoom(0);
        setVisible(true);
    }

    /**
     * @param roomNumber
     * Obtain room, then build it
     */
    public void enteredRoom(int roomNumber)
    {
        currentRoom = room.obtainRoom(roomNumber);
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

        for(int i = 0; i < product; i++)
        {
            panel.get(i).removeAll();
        }

        for (int i = 0; i < x; i++) 
        {
            for (int j = 0; j < y; j++) 
            {
                switch (currentRoom[i][j]) 
                {
                    case 0 -> panel.get(index).setBackground(Color.BLACK);

                    case 1 ->   setTile("Sprites/GreenPlain.png", index);

                    case 2 -> setTile("Sprites/GrassBlades.png", index);

                    case -1 -> setTile("Sprites/VoidEye.jpg", index);
                    
                    case 90 -> setTile("Sprites/Logo.png",index);

                    default -> panel.get(index).setBackground(Color.GRAY);

                }

                add(panel.get(index)); 
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
        Image img = icon.getImage().getScaledInstance(107, 107, Image.SCALE_SMOOTH);
        JLabel l = new JLabel(new ImageIcon(img));
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        panel.get(index).add(l, BorderLayout.CENTER);  
        panel.get(index).setBackground(Color.magenta);
    }
}
