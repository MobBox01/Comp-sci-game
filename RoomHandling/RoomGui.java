package RoomHandling;
import FightingGui.FightingGui;
import Saving.ProgressSaving;
import Stats.Player;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import Elements.AudioPlayer;
import Elements.Dialouge;

public class RoomGui extends JFrame 
{
    
    //Classes & Arrays
    private RoomData roomContainer = new RoomData();
    private FightingGui fightingGui;
    private ProgressSaving progressSaving;
    private Player player;

    //Arrays
    private int[][] currentRoom;
    private ArrayList<JPanel> panelArray = new ArrayList<>();

    //Room Size - Constants
    private final int roomX = 10;
    private final int roomY = 10;
    private final int roomTotalTiles = roomX*roomY;

    //Rooms
    private int roomCounter = 0;
    private int tileUnderPlayer = 27;
    private int playerRow;
    private int playerCollumn;

    //Constants
    private static final int VOID = 0;
    private static final int CONCRETE = 1;
    private static final int PLAYER = 90;
    private static final int NEXT_ROOM = 10;
    private static final int LAST_ROOM = 11;
    private static final int LIGHT_PRODUCER = -10;
    private static final int CITY_1 = 100;
    private static final int DESTROYEDCITY_1 = -100;

    /**
     * Sets up window and starting room
    */
    public RoomGui(FightingGui fightGuiPass,ProgressSaving progressPass,Player playerPass) 
    {
        fightingGui = fightGuiPass;
        progressSaving = progressPass;
        player = playerPass;

        setTitle("Room Number: [0] VOID GAME");
        setSize(750, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(roomX, roomY, 0, 0));

        for (int i = 0; i < roomTotalTiles; i++) 
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panelArray.add(panel);
            add(panel);
        }

        currentRoom = roomContainer.obtainRoom(progressSaving.obtainSavePoint()[2]);
        roomCounter = progressSaving.obtainSavePoint()[2];
        findPlayer();
        buildRoom();

        setVisible(true);
    }

    /**
     * @param roomNumber Obtain the new room that player entered, then build it.
     */
    public void enteredRoom(int roomNumber)
    {
        System.out.println("Entered room number: {" + roomNumber + "}");
        setTitle("Room Number: [" + roomNumber + "] VOID GAME");

        currentRoom = roomContainer.obtainRoom(roomNumber);
        findPlayer();
        progressSaving.setSavePoint(player.getLevel(),player.getXP(),roomNumber);

        buildRoom();
    }

    /**
     * @param dx -> 1 tile left/right (Left == -1) (Right == 1)
     * @param dy -> 1 tile Up/Down  (Down == -1) (Up == 1)
     * <p>
     * [IF] User encounter square 10, move to next room
     * <p>
     * [IF] USer encounters square 11, move back a room
     */
    public void movePlayer(int dx,int dy)
    {
        if (dx != 0) movePlayerX(dx);
        if (dy != 0) movePlayerY(dy);

        if((int)(Math.random()*1000) <= 30 && roomContainer.isAdvancedRooms(roomCounter) == false)
        {
            fightingGui.fightSet(true, "basic");
        }
        else if(((int) Math.random()*1000) <= 50 && roomContainer.isAdvancedRooms(roomCounter) == true)
        {
            fightingGui.fightSet(true, "advanced");
        }
        buildRoom();
    }

    /**
     * Finds the players current (Row,Collumn) position in the 2D-Array.
     */
    private void findPlayer()
    {
        for(int i = 0; i < roomX; i++)
        {
            for(int j = 0; j < roomY; j++)
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
     * @param dx Move player to the right or left 
     * <p>
     * [1] -> Right
     * <p>
     * [-1] -> Left
     */
    private void movePlayerX(int dx)
    {
        int targetTile = currentRoom[playerRow][playerCollumn + dx];

        if (targetTile >= CONCRETE && targetTile <= 10) 
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
     * @param dy -> Move player up or down
     * <p> 
     * [1] -> Up
     * <p>
     * [-1] -> Down
     */
    private void movePlayerY(int dy)
    {
        int targetTile = currentRoom[playerRow - dy][playerCollumn];

        if (targetTile >= CONCRETE && targetTile <= 10) 
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
        for (int r = 0; r < roomX; r++) 
        {
            for (int c = 0; c < roomY; c++) 
            {
                panelArray.get(index).removeAll();
                switch (currentRoom[r][c]) 
                {
                    case VOID -> panelArray.get(index).setBackground(Color.BLACK);
                    case CONCRETE -> setTileImage("Sprites/Walkable/Concrete.png", index);
                    case PLAYER -> setTileImage("Sprites/Walkable/PlayerSpot.gif", index);
                    case LIGHT_PRODUCER -> setTileImage("Sprites/Barriers/LightProducer.gif", index);
                    case DESTROYEDCITY_1 -> setTileImage("Sprites/Barriers/CityDestroyed_1.gif", index);
                    case CITY_1 -> setTileImage("Sprites/Barriers/City_1.png", index);
                    case NEXT_ROOM -> panelArray.get(index).setBackground(Color.BLUE);
                    case LAST_ROOM -> panelArray.get(index).setBackground(Color.CYAN);

                    default -> panelArray.get(index).setBackground(Color.RED);
                }
                index++;
            }
        }
        revalidate();
        repaint();
    }

    /**
     * @param spritePath String for picture of tile
     * @param index Index of the [x]x[y] for loop 
     * <p>
     * Set each tile and center them
     * <p>
     * [IF] Image/Gif not found; tile becomes RED.
     * <p>
     * [ALTERNATIVELY] Image/Gif found BUT doesnt match pixel size
    */
    private void setTileImage(String spritePath, int index)
    {
        ImageIcon imageComponent = new ImageIcon(spritePath); 
        JLabel labelComponent = new JLabel(imageComponent);
        

        labelComponent.setHorizontalAlignment(JLabel.CENTER);
        labelComponent.setVerticalAlignment(JLabel.CENTER);

        panelArray.get(index).removeAll();
        panelArray.get(index).add(labelComponent, BorderLayout.CENTER);
        panelArray.get(index).setBackground(Color.RED);
    }
}
