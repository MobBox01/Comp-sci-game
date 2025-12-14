package FightingGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FightGui extends JFrame 
{
    private int x = 5;
    private int y = 5;
    private int product = x*y;
    private Layout fightLayout = new Layout();
    private int[][] fightMap = fightLayout.getFightMapping();
    private int[][] noFight = fightLayout.getNoFightMapping();
    private int[][] currentLayout = noFight;
    private ArrayList<JPanel> panel = new ArrayList<>();
    private int storedTileChoice = 1;
    private boolean fightStatus = false;


    public FightGui()
    {
        setLayout(new GridLayout(x,y,0,0));
        setTitle("ENEMY: None");
        setLocation(740,0);
        setSize(550, 550);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        for(int i = 0; i < product; i++)
        {
            JPanel p = new JPanel();
            p.setLayout(new BorderLayout());
            panel.add(p);
            add(p);
        }

        buildFightRoom();

    }

    /**
     * @param move_OR_selected -> 1 tile left/right (Left == -1) (Right == 1)
     * @param dy -> 1 tile Up/Down  (Down == -1) (Up == 1)
    */
    public void movePlayer(int move_OR_selected)
    {
        int[] playerFound = new int[2];

        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                if(currentLayout[i][j] == 2 || currentLayout[i][j] == 4 || currentLayout[i][j] == 6)
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
                int targetTile = currentLayout[row][collumn + 1];
                switch(targetTile)
                {

                    case 3 ->
                    {
                        currentLayout[row][collumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        currentLayout[row][collumn + 1] = 4;
                    }
                    case 5 ->
                    {
                        currentLayout[row][collumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        currentLayout[row][collumn + 1] = 6;
                    }
                }
            }

            case -1 ->
            {
                int targetTile = currentLayout[row][collumn - 1];
                switch(targetTile)
                {
                    case 1 ->
                    {
                        currentLayout[row][collumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        currentLayout[row][collumn - 1] = 2;
                        
                    }
                    case 3 ->
                    {
                        currentLayout[row][collumn] = storedTileChoice;
                        storedTileChoice = targetTile;
                        currentLayout[row][collumn - 1] = 4;
                    }

                }
            }

            case 90 ->
            {
                System.out.println("ENTERED");
            }
        }

        buildFightRoom();
    }
    public boolean fightCheck()
    {

        return fightStatus;
    }

    public boolean fightSet(boolean e)
    {
        fightStatus = e;
        return fightStatus;

    }


    private void layoutManager()
    {
    }


    public void getEnemyInfo()
    {
        setTitle("HOI");
    }

    public void buildFightRoom()
    {
        int index = 0;
        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                panel.get(index).removeAll();
                switch (currentLayout[i][j]) 
                {
                    case 0 -> panel.get(index).setBackground(Color.BLACK);
                    
                    //ATK
                    case 1 -> setTile("Sprites/Unselected_Attack.png", index);
                    case 2 -> setTile("Sprites/Selected_Attack.png", index);

                    //HP
                    case 3 -> setTile("Sprites/Unselected_Health.png", index);
                    case 4 -> setTile("Sprites/Selected_Health.png", index);

                    //DFN
                    case 5 -> setTile("Sprites/Unselected_Defense.png", index);
                    case 6 -> setTile("Sprites/Selected_Defense.png", index);

                    //ERROR
                    default -> panel.get(index).setBackground(Color.magenta);
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
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel l = new JLabel(new ImageIcon(img));
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        panel.get(index).add(l, BorderLayout.CENTER);  
        panel.get(index).setBackground(Color.black);
    }
    
    public void setGifTile(String tilePath, int index)
    {

    }
}
