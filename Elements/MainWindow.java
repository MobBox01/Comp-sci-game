package Elements;
import Saving.ProgressSaving;
import Stats.Player;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import FightHandling.AdvancedFightingSystem;
import FightHandling.BasicFightingSystem;
import FightHandling.Layout;
import RoomHandling.RoomData;

public class MainWindow extends JFrame 
{
    
    //Classes & Arrays
    private RoomData roomData;
    private ProgressSaving progressSaving;
    private Player player;
    private AdvancedFightingSystem advanced_FS;
    private BasicFightingSystem basic_FS;
    private AudioPlayer audio;

    //Arrays
    private int[][] roomLayout;
    private ArrayList<JPanel> roomArray = new ArrayList<>();
    private ArrayList<JPanel> fightArray = new ArrayList<>();

    //Room Size - Constants
    private JTextArea textBox = new JTextArea();
    private boolean isDialougeBusy = false;
    private final int roomSizeX = 10;
    private final int roomSizeY = 10;
    private final int roomTotalTileCount = roomSizeX*roomSizeY;
    JPanel roomContainer = new JPanel(new GridLayout(roomSizeX,roomSizeY,0,0));
    JPanel dialougeContainer = new JPanel(new BorderLayout());

    //Fighting Container
    private final int fightRoomX = 5;
    private final int fightRoomY = 5;
    private final int fightTotalTileCount = fightRoomX*fightRoomY;
    
    //Rooms
    private int roomCounter = 0;
    private int tileUnderPlayer = 1;
    private int playerRow;
    private int playerCollumn;

    //Walkable
    private static final int CONCRETE = 1;

    //Special
    private static final int PLAYER = 90;
    private static final int NEXT_ROOM = 10;

    //Props
    private static final int CITY_1 = 100;

    //Destroyed Props
    private static final int VOID = 0;
    private static final int DESTROYEDCITY_1 = -100;

    private boolean fightStatus = false;
    private int selectorRow;
    private int selectorCollumn;
    private Layout fightLayout = new Layout();
    private int[][] fightRoomLayout = fightLayout.getFightMapping();
    private final int SELECTED_ATTACK = 2;
    private final int UNSELECTED_ATTACK = 1;
    private final int SELECTED_HEALTH = 4;
    private final int UNSELECTED_HEALTH = 3;
    private final int SELECTED_DEFENSE = 6;
    private final int UNSELECTED_DEFENSE = 5;
    private int storedTileChoice = UNSELECTED_ATTACK;
    JPanel fightContainer = new JPanel(new GridLayout(fightRoomX,fightRoomY));


    /**
     * Sets up window and starting room
    */
    public MainWindow(ProgressSaving progressPass,Player playerPass,RoomData roomDataPass,BasicFightingSystem basicFS_Pass, AdvancedFightingSystem advanced_FS_Pass, AudioPlayer audioPass)
    {
        audio = audioPass;
        advanced_FS = advanced_FS_Pass;
        basic_FS = basicFS_Pass;
        player = playerPass;
        roomData = roomDataPass;
        progressSaving = progressPass;
        player = playerPass;

        //750x750
        setTitle("Room Number: [" + 0 + "] VOID GAME");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(Color.black);
        setForeground(Color.black);
        getContentPane().setBackground(Color.black);
        getContentPane().setForeground(Color.BLACK);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        roomContainer.setBounds(0,0,750,750); 
        roomContainer.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        fightContainer.setBounds(750,0,550,550);
        fightContainer.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        for (int i = 0; i < roomTotalTileCount; i++) 
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            roomArray.add(panel);
            roomContainer.add(panel);
        }

        roomLayout = roomData.obtainRoom(progressSaving.obtainSavePoint()[2]);
        roomCounter = progressSaving.obtainSavePoint()[2];

        for(int i = 0; i < fightTotalTileCount; i++)
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            fightArray.add(panel);
            fightContainer.add(panel);
        }
        findPlayer();

        dialougeContainer.setBounds(750,550,550,200);
        dialougeContainer.add(textBox, BorderLayout.CENTER);
        dialougeContainer.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        textBox.setEditable(false);
        textBox.setFocusable(false);
        textBox.setBackground(Color.BLACK);
        textBox.setForeground(Color.WHITE);
        textBox.setFont(new Font("DialogInput", Font.BOLD, 16));
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        
        // remove blinking caret
        textBox.setCaret(new DefaultCaret() {@Override public void paint(Graphics g) {}});

        buildFightContainer();
        buildRoomContainer();
        add(roomContainer);
        add(fightContainer);
        add(dialougeContainer);
        setVisible(true);
    }

    /**
     * @param roomNumber Obtain the new room that player entered, then build it.
     */
    public void enteredRoom(int roomNumber)
    {
        System.out.println("Entered room number: {" + roomNumber + "}");
        setTitle("Room Number: [" + roomNumber + "] VOID GAME");

        roomLayout = roomData.obtainRoom(roomNumber);
        findPlayer();
        progressSaving.setSavePoint(player.getLevel(),player.getXP(),roomNumber);
        buildRoomContainer();

    }

    /**
     * @param dx -> 1 tile left/right (Left == -1) (Right == 1)
     * @param dy -> 1 tile Up/Down  (Down == -1) (Up == 1)
     * <p>
     * [IF] User encounter square 10, move to next room
     * <p>
     * [IF] User encounters square 11, move back a room
     */
    public void movePlayer(int dx,int dy)
    {
        if (dx != 0) movePlayerX(dx);
        if (dy != 0) movePlayerY(dy);

        buildRoomContainer();
    }

    /**
     * Finds the players current (Row,Collumn) position in the 2D-Array.
    */
    private void findPlayer()
    {
        for(int i = 0; i < roomSizeX; i++)
        {
            for(int j = 0; j < roomSizeY; j++)
            {
                if(roomLayout[i][j] == PLAYER)
                {
                    playerRow = i;
                    playerCollumn = j;
                }
            }    
        }

        for(int i = 0; i < fightRoomX; i++)
        {
            for(int j = 0; j < fightRoomY; j++)
            {
                if(fightRoomLayout[i][j] == 2 || fightRoomLayout[i][j] == 4 || fightRoomLayout[i][j] == 6)
                {
                    selectorRow = i;
                    selectorCollumn = j;
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
        int targetTile = roomLayout[playerRow][playerCollumn + dx];

        switch (targetTile) 
        {
            case 1 -> 
            {
                roomLayout[playerRow][playerCollumn] = tileUnderPlayer;
                tileUnderPlayer = targetTile;
                playerCollumn += dx;
                roomLayout[playerRow][playerCollumn] = PLAYER;
            }
            case NEXT_ROOM -> 
            {
                roomCounter++;
                enteredRoom(roomCounter);
            }
            case 11 -> 
            {
                roomCounter--;
                enteredRoom(roomCounter);
            }
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
        int targetTile = roomLayout[playerRow - dy][playerCollumn];

        switch(targetTile)
        {
            case CONCRETE ->
            {
                roomLayout[playerRow][playerCollumn] = tileUnderPlayer;
                tileUnderPlayer = targetTile;
                playerRow -= dy;
                roomLayout[playerRow][playerCollumn] = PLAYER;
            }
            case NEXT_ROOM -> 
            {
                roomCounter++;
                enteredRoom(roomCounter);
            }
            case 11 -> 
            {
                roomCounter--;
                enteredRoom(roomCounter);
            }
        }
    }

    /**
     * Index records as you loop preventing improper iteration 
     * <p>
     * Cleans up all tile panels
     * <p>
     * Loops until each tile is filled
     */
    private void buildRoomContainer() 
    {
        int index = 0;
        for (int r = 0; r < roomSizeX; r++) 
        {
            for (int c = 0; c < roomSizeY; c++) 
            {
                roomArray.get(index).removeAll();
                switch (roomLayout[r][c]) 
                {
                    case VOID -> roomArray.get(index).setBackground(Color.BLACK);
                    case CONCRETE -> setTileImage("Sprites/Walkable/Concrete.png", index);
                    case PLAYER -> setTileImage("Sprites/Walkable/PlayerSpot.gif", index);
                    case DESTROYEDCITY_1 -> setTileImage("Sprites/Barriers/CityDestroyed_1.gif", index);
                    case CITY_1 -> setTileImage("Sprites/Barriers/City_1.png", index);
                    case NEXT_ROOM -> roomArray.get(index).setBackground(Color.BLUE);

                    default -> roomArray.get(index).setBackground(Color.RED);
                }
                index++;
            }
        }

        roomContainer.revalidate();
        roomContainer.repaint();
        //revalidate();
        //repaint();
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

        roomArray.get(index).removeAll();
        roomArray.get(index).add(labelComponent, BorderLayout.CENTER);
        roomArray.get(index).setBackground(Color.RED);
    }

    public int currentRoom()
    {
        return roomCounter;
    }

//-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G

//-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G

//-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G
    

    /**
     * @param move_OR_selected -> 1 tile left/right (Left == -1) (Right == 1)
     * @param dy -> 1 tile Up/Down  (Down == -1) (Up == 1)
     * /!\ WARNING: USABLE FOR RIGHT NOW, BUT PLSSS REMAKE THIS </3
    */
    public void moveSelector(int move_OR_selected)
    {
        findPlayer();
        switch(move_OR_selected)
        {
            case 1 -> // RIGHT
            {
                int targetTile = fightRoomLayout[selectorRow][selectorCollumn + 1];
                switch(targetTile)
                {
                    case UNSELECTED_HEALTH ->
                    {
                        fightRoomLayout[selectorRow][selectorCollumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        fightRoomLayout[selectorRow][selectorCollumn + 1] = SELECTED_HEALTH;
                    }
                    case UNSELECTED_DEFENSE ->
                    {
                        fightRoomLayout[selectorRow][selectorCollumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        fightRoomLayout[selectorRow][selectorCollumn + 1] = SELECTED_DEFENSE;
                    }
                }
            }

            case -1 ->
            {
                int targetTile = fightRoomLayout[selectorRow][selectorCollumn - 1];
                switch(targetTile)
                {
                    case UNSELECTED_ATTACK ->
                    {
                        fightRoomLayout[selectorRow][selectorCollumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        fightRoomLayout[selectorRow][selectorCollumn - 1] = SELECTED_ATTACK;   
                    }
                    case UNSELECTED_HEALTH ->
                    {
                        fightRoomLayout[selectorRow][selectorCollumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        fightRoomLayout[selectorRow][selectorCollumn - 1] = SELECTED_HEALTH;
                    }
                }
            }

            case 90 ->
            {
                findPlayer();

                if(basic_FS.isEnemyAlive())
                {
                    switch (fightRoomLayout[selectorRow][selectorCollumn]) 
                    {
                        case 2 -> basic_FS.attack();
                        case 4 -> basic_FS.heal();
                        case 6 -> basic_FS.defend(); 
                    }
                }

                else if(advanced_FS.isEnemyAlive())
                {
                    switch (fightRoomLayout[selectorRow][selectorCollumn]) 
                    {
                        case 2 -> advanced_FS.attack();
                        case 4 -> advanced_FS.heal();
                        case 6 -> advanced_FS.defend(); 
                    }
                }

            }
        }
        healthStatus();
        buildFightContainer();
    }

    private void healthStatus()
    {
        int healthPercentage = player.healthPercentage();

        if(healthPercentage == 100)
        {
            fightRoomLayout[2][0] = -200;
        }
        else if(healthPercentage >= 75)
        {
            fightRoomLayout[2][0] = -175;
        }
        else if(healthPercentage >= 50)
        {
            fightRoomLayout[2][0] = -150;
        }
        else if(healthPercentage >= 25)
        {
            fightRoomLayout[2][0] = -125;
        }
        else if(healthPercentage > 0)
        {
            fightRoomLayout[2][0] = -124;
        }
        else if(healthPercentage <= 0)
        {
            fightRoomLayout[2][0] = -100;
            //dialougeSystem.dialouge("You have died...\n=={GAMER-OVER}==");
        }
    }

    public void buildFightContainer()
    {
        int index = 0;
        for(int i = 0; i < fightRoomX; i++)
        {
            for(int j = 0; j < fightRoomY; j++)
            {
                fightArray.get(index).removeAll();
                switch (fightRoomLayout[i][j]) 
                {
                    case 0 -> fightArray.get(index).setBackground(Color.BLACK);
                    
                    //ATK
                    case 1 -> setFightingTile("Sprites/Selectors/Unselected_Attack.png", index);
                    case 2 -> setFightingTile("Sprites/Selectors/Selected_Attack.png", index);

                    //HP
                    case 3 -> setFightingTile("Sprites/Selectors/Unselected_Heal.png", index);
                    case 4 -> setFightingTile("Sprites/Selectors/Selected_Heal.png", index);

                    //DFN
                    case 5 -> setFightingTile("Sprites/Selectors/Unselected_Defend.png", index);
                    case 6 -> setFightingTile("Sprites/Selectors/Selected_Defend.png", index);

                    case 300 -> setFightingTile("Sprites/Enemies/Atomize.gif", index);
                    case 301 -> setFightingTile("Sprites/Enemies/ThreeDemons.png", index);
                    case 400 -> setFightingTile("Sprites/Enemies/Frame_Empty.gif", index);

                    case -200 -> setFightingTile("Sprites/HealthStates/MC_Full.png", index);
                    case -175 -> setFightingTile("Sprites/HealthStates/MC_75.png", index);
                    case -150 -> setFightingTile("Sprites/HealthStates/MC_50.png", index);
                    case -125 -> setFightingTile("Sprites/HealthStates/MC_25.png", index);
                    case -124 -> setFightingTile("Sprites/HealthStates/MC_Under25.png", index);
                    case -100 -> setFightingTile("Sprites/HealthStates/MC_Dead.png", index);

                    //ERROR
                    default -> fightArray.get(index).setBackground(Color.magenta);
                }

                index++;
            }
        }
        fightContainer.revalidate();
        fightContainer.repaint();
    }

    /**
     * @param tilePath String for picture of tile
     * @param index Index of the [x]x[y] for loop
     * Set each tile and center them
     * [IF] image not found or error; set tile to magenta to spot it easily
     */
    private void setFightingTile(String tilePath, int index)
    {
        ImageIcon imageComponent = new ImageIcon(tilePath);
        JLabel labelComponent = new JLabel(imageComponent);

        labelComponent.setHorizontalAlignment(JLabel.CENTER);
        labelComponent.setVerticalAlignment(JLabel.CENTER);

        fightArray.get(index).add(labelComponent, BorderLayout.CENTER);  
        fightArray.get(index).setBackground(Color.black);
    }
    /**
     * @return check if a fight is ongoing
     * [IF] Enemy is alive -> True
     * [ELSE-IF] Enemy isnt alive -> False
     * [IF] Player dies -> Show a error message, kill the program once X is or "OK" is pressed
     */
    public boolean fightCheck()
    {
        if(basic_FS.isEnemyAlive() || advanced_FS.isEnemyAlive())
        {
            fightStatus = true;
        }

        else if(!basic_FS.isEnemyAlive() || !advanced_FS.isEnemyAlive())
        {
            fightStatus = false;
            audio.fightMusicStop(player.isFightingAdvanced());
            fightRoomLayout[1][2] = 400;
            setTitle("Enemy: None");
            buildFightContainer();
        }

        if(!player.isAlive())
        {
            JOptionPane.showMessageDialog(null,"YOU DIED","GAME OVER",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return fightStatus;
    }

    public void fightSet(boolean e, String level)
    {
        switch(level)
        {
            case "basic" -> 
            {
                basic_FS.enemyEncounter();
                setBasicEnemyInfo();
                setEnemyFrame();
                buildFightContainer();
            }
            
            case "advanced" ->
            {
                advanced_FS.enemyEncounter();
                setAdvancedEnemyInfo();
                setEnemyFrame();
                buildFightContainer();
            }
        }
        fightStatus = e;
    }

    private void setBasicEnemyInfo()
    {
        setTitle("Enemy: " + basic_FS.getCurrentName());
    }

    private void setAdvancedEnemyInfo()
    {
        setTitle("Enemy: " + advanced_FS.getCurrentName());
    }

    private void setEnemyFrame()
    {
        switch(advanced_FS.getCurrentName())
        {
            case "Atomize" -> fightRoomLayout[1][2] = 300;
            case "ThreeDemons" -> fightRoomLayout[1][2] = 301;
        }
    }

//-----------------------

    public void setNewText(String newText)
    {
        textBox.setText(newText);
    }

    public void dialouge(String newText)
    {
        isDialougeBusy = true;
        setNewText("");
        int[] i = {0};
        Timer timer = new Timer(10, time -> 
            {
                if(i[0] == newText.length())
                {
                    ((Timer)time.getSource()).stop();
                    isDialougeBusy = false;
                }
                else
                {
                    textBox.append(newText.substring(i[0],i[0]+1));
                    i[0] += 1;
                }
            }
        );

        timer.start();
    }

    public boolean isDialougeActive()
    {
        return isDialougeBusy;
    }

}





        




