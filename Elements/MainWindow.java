package Elements;
import FightHandling.AdvancedFightingSystem;
import FightHandling.BasicFightingSystem;
import Saving.ProgressSaving;
import Stats.Layout;
import Stats.Player;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
@SuppressWarnings("FieldMayBeFinal")
public class MainWindow extends JFrame 
{
    
    //Classes & Arrays
    private Layout layout;
    private ProgressSaving progressSaving;
    private Player player;
    private AdvancedFightingSystem advanced_FS;
    private BasicFightingSystem basic_FS;
    private JTextArea textBox = new JTextArea();

    //Arrays
    private int[][] roomLayout;
    private ArrayList<JPanel> roomArray = new ArrayList<>();

    //Room Layout Size
    private boolean isDialougeBusy = false;
    private final int roomSizeX = 10;
    private final int roomSizeY = 10;
    private final int roomTotalTileCount = roomSizeX*roomSizeY;
    
    //Rooms
    private int tileUnderPlayer = 1;
    private int playerRow;
    private int playerCollumn;

    //Walkable
    private static final int CONCRETE = 1;

    //Special
    private static final int PLAYER = 90;
    private static final int NEXT_ROOM = 10;

    //Containers
    private JPanel roomContainer = new JPanel(new GridLayout(roomSizeX,roomSizeY,0,0));
    private JPanel fightContainer = new JPanel(null);
    private JPanel dialougeContainer = new JPanel(new BorderLayout());

    //Labels
    private JLabel selector = new JLabel(new ImageIcon("Sprites/Selectors/Attack.png"));
    private JLabel healthStatus = new JLabel(new ImageIcon("Sprites/HealthStates/MC_Full.png"));
    private JLabel battleFrame = new JLabel(new ImageIcon("Frame_Empty.gif"));

    //Other
    private int[] fightLayout = {-200,1};
    private boolean lightSpreading = true;


    @SuppressWarnings("OverridableMethodCallInConstructor")
    /**
     * @param progressPass
     * @param playerPass
     * @param layoutPass
     * @param basicFS_Pass
     * @param advanced_FS_Pass
     * Deals with setting up the MainWindow object.
     */
    public MainWindow(ProgressSaving progressPass,Player playerPass,Layout layoutPass,BasicFightingSystem basicFS_Pass, AdvancedFightingSystem advanced_FS_Pass)
    {
        advanced_FS = advanced_FS_Pass;
        basic_FS = basicFS_Pass;
        player = playerPass;
        layout = layoutPass;
        progressSaving = progressPass;
        player = playerPass;

        //Frame
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        getContentPane().setBackground(Color.black);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //Room Container
        roomContainer.setBounds(0,0,750,750); 
        roomContainer.setBorder(BorderFactory.createLineBorder(Color.WHITE,5));

        //Fight Container
        fightContainer.setBounds(roomContainer.getWidth(),0,550,550);
        fightContainer.setBorder(BorderFactory.createLineBorder(Color.WHITE,5));
        fightContainer.setBackground(Color.black);
        fightContainer.setForeground(Color.black);
        fightContainer.add(selector);
        fightContainer.add(healthStatus);
        fightContainer.add(battleFrame);

        //Labels
        selector.setBounds(125,350,303,101);
        healthStatus.setBounds(20,200,101,101);
        battleFrame.setBackground(Color.white);
        battleFrame.setForeground(Color.white);
        battleFrame.setBounds(130,0,303,303);
        battleFrame.setIcon(new ImageIcon("Sprites/Enemies/Frame_Empty.gif"));

        for (int i = 0; i < roomTotalTileCount; i++) 
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            roomArray.add(panel);
            roomContainer.add(panel);
        }
        layout.setRoomNumber(progressSaving.obtainSavePoint()[2]);
        roomLayout = layout.obtainRoom();
        findPlayer();

        //Dialouge
        dialougeContainer.setBounds(roomContainer.getWidth(),550,550,200);
        dialougeContainer.add(textBox, BorderLayout.CENTER);
        dialougeContainer.setBorder(BorderFactory.createLineBorder(Color.WHITE,5));

        textBox.setEditable(false);
        textBox.setFocusable(false);
        textBox.setBackground(Color.BLACK);
        textBox.setForeground(Color.WHITE);
        textBox.setFont(new Font("DialogInput", Font.BOLD, 16));
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        
        // remove blinking caret
        textBox.setCaret(new DefaultCaret() {@Override public void paint(Graphics g) {}});

        //Visibility
        setTitle("| ~The~ ~Concealing~ ~Light~ | Room: {" + layout.getRoomNumber() + "} | ");
        buildRoomContainer();
        add(roomContainer);
        add(fightContainer);
        add(dialougeContainer);
        fightContainer.revalidate();
        fightContainer.repaint();
        setVisible(true);
    }

    /**
     * @param roomNumber Obtain the new room that player entered, then build it.
     */
    public void enteredRoom()
    {
        System.out.println("Entered room number: {" + layout.getRoomNumber() + "}");
        roomLayout = layout.obtainRoom();
        findPlayer();
        if(layout.getRoomNumber() < 15 )//6
        {
            progressSaving.setSavePoint(player.getLevel(),player.getXP(),layout.getRoomNumber());
        }
        setTitle("The Concealing Light | Room: {" + layout.getRoomNumber() + "}");

        buildRoomContainer();
    }

    /**
     * @param dx -> 1 tile left/right (Left == -1) (Right == 1)
     * @param dy -> 1 tile Up/Down  (Down == -1) (Up == 1) <p>
     * [IF] 10 -> Next room<p>
     * [IF] 11 -> Previous room
     */
    public void movePlayer(int dx,int dy)
    {
        if (dx != 0) movePlayerX(dx);
        if (dy != 0) movePlayerY(dy);

        if(layout.getRoomNumber() == 17 && lightSpreading)
        {
            lightSpreading = false;
            getContentPane().setBackground(Color.WHITE);
            fightContainer.removeAll();
            fightContainer.setBackground(Color.WHITE);
            textBox.setBackground(Color.WHITE);
            textBox.setForeground(Color.BLACK);
            dialouge("The room fills with light the futhure you go... the void weakens, newly formed bonds break apart and the path behind you reveals damaged nature and rubble as void puddles break apart into energy as the previous link has been broken.");
        }
        buildRoomContainer();
    }

    /**
     * Finds the players current (Row,Collumn) position in the Matrix.
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
                layout.nextRoom(1);
                enteredRoom();
            }
            case 11 -> 
            {
                layout.nextRoom(-1);
                enteredRoom();
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
                layout.nextRoom(1);
                enteredRoom();
            }
            case 11 -> 
            {
                layout.nextRoom(-1);
                enteredRoom();
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
                    case 0 -> roomArray.get(index).setBackground(Color.BLACK);
                    case 1 -> setTileImage("Sprites/Walkable/Concrete.png", index);
                    case 90 -> setTileImage("Sprites/Walkable/PlayerSpot.gif", index);
                    case -100 -> setTileImage("Sprites/Barriers/CityDestroyed_1.gif", index);
                    case 100 -> setTileImage("Sprites/Barriers/City_1.png", index);
                    case 200 -> setTileImage("Sprites/Barriers/Forest.png", index);
                    case NEXT_ROOM -> roomArray.get(index).setBackground(Color.BLUE);

                    default -> roomArray.get(index).setBackground(Color.WHITE);
                }
                index++;
            }
        }

        roomContainer.revalidate();
        roomContainer.repaint();
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

        roomArray.get(index).removeAll();
        roomArray.get(index).add(labelComponent, BorderLayout.CENTER);
        roomArray.get(index).setBackground(Color.RED);
    }


//-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G

//-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G

//-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G-------F--I--G--H--T--I--N--G
    

    /**
     * @param move_OR_selected -> 1 tile left/right (Left == -1) (Right == 1)
     * @param dy -> 1 tile Up/Down  (Down == -1) (Up == 1)
    */
    public void moveSelector(int move_OR_selected)
    {
        switch(move_OR_selected)
        {
            case 1 ->
            {
                if(fightLayout[1]+1 != 4)
                {
                    fightLayout[1] += 1;
                }
            }
            case -1 ->
            {
                if(fightLayout[1] - 1 != 0)
                {
                    fightLayout[1] -= 1;
                }
            }

            case 90 ->
            {
                findPlayer();

                if(basic_FS.isEnemyAlive())
                {
                    switch(fightLayout[1])
                    {
                        case 1 -> basic_FS.attack();
                        case 2 -> basic_FS.heal();
                        case 3 -> basic_FS.defend(); 
                    }
                }
                else if(advanced_FS.isEnemyAlive())
                {
                    switch (fightLayout[1]) 
                    {
                        case 1 -> advanced_FS.attack();
                        case 2 -> advanced_FS.heal();
                        case 3 -> advanced_FS.defend(); 
                    }
                }

            }
        }

        switch(fightLayout[1])
        {
            case 1 -> 
            {
                selector.setIcon(new ImageIcon("Sprites/Selectors/Attack.png"));
            }
            case 2 -> 
            {
                selector.setIcon(new ImageIcon("Sprites/Selectors/Heal.png"));
            }
            case 3 -> 
            {
                selector.setIcon(new ImageIcon("Sprites/Selectors/Defend.png"));
            }
        }
        updateStatus();
    }

    /**
     * Update health status based on health %
     * Update battle frame
     * 
     */
    public void updateStatus()
    {
        int healthPercentage = player.healthPercentage();

        if(healthPercentage == 100)
        {
            fightLayout[0] = -200;
        }
        else if(healthPercentage >= 75)
        {
            fightLayout[0] = -175;
        }
        else if(healthPercentage >= 50)
        {
            fightLayout[0] = -150;
        }
        else if(healthPercentage >= 25)
        {
            fightLayout[0] = -125;
        }
        else if(healthPercentage > 0)
        {
            fightLayout[0] = -124;
        }
        else if(healthPercentage <= 0)
        {
            fightLayout[0] = -100;
            dialouge("You have died...\n=={GAMER-OVER}==");
        }

        switch(fightLayout[0])
        {
            case -200 -> healthStatus.setIcon(new ImageIcon("Sprites/HealthStates/MC_Full.png"));
            case -175 -> healthStatus.setIcon(new ImageIcon("Sprites/HealthStates/MC_75.png"));
            case -150 -> healthStatus.setIcon(new ImageIcon("Sprites/HealthStates/MC_50.png"));
            case -125 -> healthStatus.setIcon(new ImageIcon("Sprites/HealthStates/MC_25.png"));
            case -124 -> healthStatus.setIcon(new ImageIcon("Sprites/HealthStates/MC_Under25.png"));
            case -100 -> healthStatus.setIcon(new ImageIcon("Sprites/HealthStates/MC_Dead.png"));
        }

        if(basic_FS.isEnemyAlive())
        {
            //switch(basic_FS.getName())
        }
        else if(advanced_FS.isEnemyAlive())
        {
            switch(advanced_FS.getCurrentName())
            {
                case "ThreeDemons" -> battleFrame.setIcon(new ImageIcon("Sprites/Enemies/ThreeDemons.png"));
                case "Atomize" -> battleFrame.setIcon(new ImageIcon("Sprites/Enemies/Atomize.gif"));
            }

        }  
        else
        {
            battleFrame.setIcon(new ImageIcon("Sprites/Enemies/Frame_Empty.gif"));
        }  
        fightContainer.revalidate();
        fightContainer.repaint();
    }



//-------D--I--A--L--O--U--G--E-------D--I--A--L--O--U--G--E-------D--I--A--L--O--U--G--E-------D--I--A--L--O--U--G--E

//-------D--I--A--L--O--U--G--E-------D--I--A--L--O--U--G--E-------D--I--A--L--O--U--G--E-------D--I--A--L--O--U--G--E

//-------D--I--A--L--O--U--G--E-------D--I--A--L--O--U--G--E-------D--I--A--L--O--U--G--E-------D--I--A--L--O--U--G--E

    public void setNewText(String newText)
    {
        textBox.setText(newText);
    }

    
    public void dialouge(String newText)
    {
        isDialougeBusy = true;
        setNewText("");
        int[] i = {0};
        Timer timer = new Timer(30, time -> 
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