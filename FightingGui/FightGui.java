package FightingGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Stats.Player;

public class FightGui extends JFrame 
{
    private int x = 5;
    private int y = 5;
    private int product = x*y;
    private int storedTileChoice = 1;

    private boolean fightStatus = false;

    //Classess & Arrays
    private Layout fightLayout = new Layout();
    private ArrayList<JPanel> panelArray = new ArrayList<>();
    private FightingSystem fightingSystem;
    private Player player;
    private Dialouge dialougeSystem;
    private int[][] fightRoomLayout = fightLayout.getFightMapping();

    public FightGui(FightingSystem fightSystemPass,Player playerPass,Dialouge dialougeSystemPass) throws IOException
    {
        fightingSystem = fightSystemPass;
        player = playerPass;
        dialougeSystem = dialougeSystemPass;

        setLayout(new GridLayout(x,y,0,0));
        setTitle("Enemy: None");
        setBounds(740, 0, 550, 550);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        
        for(int i = 0; i < product; i++)
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panelArray.add(panel);
            add(panel);
        }
        buildFightRoom();
    }

    /**
     * @param move_OR_selected -> 1 tile left/right (Left == -1) (Right == 1)
     * @param dy -> 1 tile Up/Down  (Down == -1) (Up == 1)
     * TODO: Rewrite this method, my brain wants to cry 
     * /!\ WARNING: USABLE FOR RIGHT NOW, BUT PLSSS REMAKE THIS </3
    */
    public void movePlayer(int move_OR_selected)
    {
        int[] playerFound = new int[2];

        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                if(fightRoomLayout[i][j] == 2 || fightRoomLayout[i][j] == 4 || fightRoomLayout[i][j] == 6)
                {
                    playerFound[0] = i;
                    playerFound[1] = j;
                }
            }    
        }

        int row = playerFound[0];
        int collumn = playerFound[1];

        switch(move_OR_selected)
        {
            case 1 -> // RIGHT
            {
                int targetTile = fightRoomLayout[row][collumn + 1];
                switch(targetTile)
                {
                    case 3 ->
                    {
                        fightRoomLayout[row][collumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        fightRoomLayout[row][collumn + 1] = 4;
                    }
                    case 5 ->
                    {
                        fightRoomLayout[row][collumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        fightRoomLayout[row][collumn + 1] = 6;
                    }
                }
            }

            case -1 ->
            {
                int targetTile = fightRoomLayout[row][collumn - 1];
                switch(targetTile)
                {
                    case 1 ->
                    {
                        fightRoomLayout[row][collumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        fightRoomLayout[row][collumn - 1] = 2;   
                    }
                    case 3 ->
                    {
                        fightRoomLayout[row][collumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        fightRoomLayout[row][collumn - 1] = 4;
                    }
                }
            }

            case 90 ->
            {
                for(int i = 0; i < x; i++)
                {
                    for(int j = 0; j < y; j++)
                    {
                        if(fightRoomLayout[i][j] == 2 || fightRoomLayout[i][j] == 4 || fightRoomLayout[i][j] == 6)
                        {
                            playerFound[0] = i;
                            playerFound[1] = j;
                        }
                    }    
                }

                row = playerFound[0];
                collumn = playerFound[1];

                switch (fightRoomLayout[row][collumn]) 
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

    private void healthStatus()
    {
        int hp = player.healthPercentage();

        if(hp == 100)
        {
            fightRoomLayout[2][0] = -200;
        }
        else if(hp >= 75)
        {
            fightRoomLayout[2][0] = -175;
        }
        else if(hp >= 50)
        {
            fightRoomLayout[2][0] = -150;
        }
        else if(hp >= 25)
        {
            fightRoomLayout[2][0] = -125;
        }
        else if(hp <= 0)
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
        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                panelArray.get(index).removeAll();
                switch (fightRoomLayout[i][j]) 
                {
                    case 0 -> panelArray.get(index).setBackground(Color.BLACK);
                    
                    //ATK
                    case 1 -> setTile("Sprites/Unselected_Attack.png", index);
                    case 2 -> setTile("Sprites/Selected_Attack.png", index);

                    //HP
                    case 3 -> setTile("Sprites/Unselected_Health.png", index);
                    case 4 -> setTile("Sprites/Selected_Health.png", index);

                    //DFN
                    case 5 -> setTile("Sprites/Unselected_Defense.png", index);
                    case 6 -> setTile("Sprites/Selected_Defense.png", index);

                    case -200 -> setTile("Sprites/MC_Full.png", index);
                    case -175 -> setTile("Sprites/MC_75.png", index);
                    case -150 -> setTile("Sprites/MC_50.png", index);
                    case -125 -> setTile("Sprites/MC_25.png", index);
                    case -100 -> setTile("Sprites/MC_Dead.png", index);

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
     * TODO: Merge setGif and setTile into one method, this way itd be cleaner and more easier to work with
     */
    private void setTile(String tilePath, int index)
    {
        ImageIcon icon = new ImageIcon(tilePath);
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel l = new JLabel(new ImageIcon(img));
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        panelArray.get(index).add(l, BorderLayout.CENTER);  
        panelArray.get(index).setBackground(Color.black);
    }
    
    public void setGifTile(String tilePath, int index)
    {
        //Inactive, please merge later!
    }
}