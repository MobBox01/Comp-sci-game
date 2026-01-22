package FightingGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Stats.Player;

public class FightingGui extends JFrame 
{
    //Room Constants
    private final int roomX = 5;
    private final int roomY = 5;
    private final int roomTotalTiles = roomX*roomY;

    private boolean fightStatus = false;
    private int playerRow;
    private int playerCollumn;

    //Classess & Arrays
    private Layout fightLayout = new Layout();
    private ArrayList<JPanel> panelArray = new ArrayList<>();
    private FightingSystem fightingSystem;
    private Player player;
    private Dialouge dialougeSystem;
    private int[][] fightRoomLayout = fightLayout.getFightMapping();

    //Object ints
    private final int SELECTED_ATTACK = 2;
    private final int UNSELECTED_ATTACK = 1;

    private final int SELECTED_HEALTH = 4;
    private final int UNSELECTED_HEALTH = 3;

    private final int SELECTED_DEFENSE = 6;
    private final int UNSELECTED_DEFENSE = 5;

    private int storedTileChoice = UNSELECTED_ATTACK;


    public FightingGui(FightingSystem fightSystemPass,Player playerPass,Dialouge dialougeSystemPass) throws IOException
    {
        fightingSystem = fightSystemPass;
        player = playerPass;
        dialougeSystem = dialougeSystemPass;

        setLayout(new GridLayout(roomX,roomY,0,0));
        setTitle("Enemy: None");
        setBounds(740, 0, 550, 550);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        
        for(int i = 0; i < roomTotalTiles; i++)
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panelArray.add(panel);
            add(panel);
        }

        findPlayer();
        buildFightRoom();
    }

    /**
     * @param move_OR_selected -> 1 tile left/right (Left == -1) (Right == 1)
     * @param dy -> 1 tile Up/Down  (Down == -1) (Up == 1)
     * /!\ WARNING: USABLE FOR RIGHT NOW, BUT PLSSS REMAKE THIS </3
    */
    public void movePlayer(int move_OR_selected)
    {

        findPlayer();
        switch(move_OR_selected)
        {
            case 1 -> // RIGHT
            {
                int targetTile = fightRoomLayout[playerRow][playerCollumn + 1];
                switch(targetTile)
                {
                    case UNSELECTED_HEALTH ->
                    {
                        fightRoomLayout[playerRow][playerCollumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        fightRoomLayout[playerRow][playerCollumn + 1] = SELECTED_HEALTH;
                    }
                    case UNSELECTED_DEFENSE ->
                    {
                        fightRoomLayout[playerRow][playerCollumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        fightRoomLayout[playerRow][playerCollumn + 1] = SELECTED_DEFENSE;
                    }
                }
            }

            case -1 ->
            {
                int targetTile = fightRoomLayout[playerRow][playerCollumn - 1];
                switch(targetTile)
                {
                    case UNSELECTED_ATTACK ->
                    {
                        fightRoomLayout[playerRow][playerCollumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        fightRoomLayout[playerRow][playerCollumn - 1] = SELECTED_ATTACK;   
                    }
                    case UNSELECTED_HEALTH ->
                    {
                        fightRoomLayout[playerRow][playerCollumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        fightRoomLayout[playerRow][playerCollumn - 1] = SELECTED_HEALTH;
                    }
                }
            }

            case 90 ->
            {
                findPlayer();

                switch (fightRoomLayout[playerRow][playerCollumn]) 
                {
                    case 2 -> fightingSystem.attack();
                    case 4 -> fightingSystem.heal();
                    case 6 -> fightingSystem.defend(); 
                }
            }
        }
        healthStatus();
        buildFightRoom();
    }

    /**
     * @param dx Move player left or right
     * <p>
     * [1] -> Right
     * <p>
     * [-1] -> Left
     
    private void movePlayerX(int dx)
    {

    }
    */

    /**
     * Find player when fight room is constructed
    */
    private void findPlayer()
    {
        for(int i = 0; i < roomX; i++)
        {
            for(int j = 0; j < roomY; j++)
            {
                if(fightRoomLayout[i][j] == 2 || fightRoomLayout[i][j] == 4 || fightRoomLayout[i][j] == 6)
                {
                    playerRow = i;
                    playerCollumn = j;
                }
            }    
        }
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
            dialougeSystem.setNewText("You have died...\n=={GAMER-OVER}==");
        }
    }

    /**
     * @return check if a fight is ongoing
     * [IF] Enemy is alive -> True
     * [ELSE-IF] Enemy isnt alive -> False TODO: Set a new decal to show that there is no enemy when peaceful
     * [IF] Player dies -> Show a error message, kill the program once X is or "OK" is pressed
     */
    public boolean fightCheck()
    {
        if(fightingSystem.isEnemyAlive())
        {
            fightStatus = true;
        }

        else if(!fightingSystem.isEnemyAlive())
        {
            fightStatus = false;
            setTitle("Enemy: None");
        }

        if(!player.isAlive())
        {
            JOptionPane.showMessageDialog(null,"YOU DIED","GAME OVER",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return fightStatus;
    }

    public void fightSet(boolean e)
    {
        fightStatus = e;
        //name = enemyName;
        fightingSystem.enemyEncounter();
        setEnemyInfo();
    }

    private void setEnemyInfo()
    {
        setTitle("Enemy: " + fightingSystem.getCurrentName());
    }

    public void buildFightRoom()
    {
        int index = 0;
        for(int i = 0; i < roomX; i++)
        {
            for(int j = 0; j < roomY; j++)
            {
                panelArray.get(index).removeAll();
                switch (fightRoomLayout[i][j]) 
                {
                    case 0 -> panelArray.get(index).setBackground(Color.BLACK);
                    
                    //ATK
                    case 1 -> setTile("Sprites/Selectors/Unselected_Attack.png", index);
                    case 2 -> setTile("Sprites/Selectors/Selected_Attack.png", index);

                    //HP
                    case 3 -> setTile("Sprites/Selectors/Unselected_Heal.png", index);
                    case 4 -> setTile("Sprites/Selectors/Selected_Heal.png", index);

                    //DFN
                    case 5 -> setTile("Sprites/Selectors/Unselected_Defend.png", index);
                    case 6 -> setTile("Sprites/Selectors/Selected_Defend.png", index);

                    case 300 -> setTile("", index);

                    case -200 -> setTile("Sprites/HealthStates/MC_Full.png", index);
                    case -175 -> setTile("Sprites/HealthStates/MC_75.png", index);
                    case -150 -> setTile("Sprites/HealthStates/MC_50.png", index);
                    case -125 -> setTile("Sprites/HealthStates/MC_25.png", index);
                    case -124 -> setTile("Sprites/HealthStates/MC_Under25.png", index);
                    case -100 -> setTile("Sprites/HealthStates/MC_Dead.png", index);

                    //ERROR
                    default -> panelArray.get(index).setBackground(Color.magenta);
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
        ImageIcon imageComponent = new ImageIcon(tilePath);
        JLabel labelComponent = new JLabel(imageComponent);

        labelComponent.setHorizontalAlignment(JLabel.CENTER);
        labelComponent.setVerticalAlignment(JLabel.CENTER);

        panelArray.get(index).add(labelComponent, BorderLayout.CENTER);  
        panelArray.get(index).setBackground(Color.black);
    }

}