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
    @SuppressWarnings("unused")
    private int[][] noFight = fightLayout.getNoFightMapping();
    private int[][] currentLayout = noFight;
    private ArrayList<JPanel> panel = new ArrayList<>();
    private int tileUnderPlayer;


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
     * @param dx -> 1 tile left/right (Left == -1) (Right == 1)
     * @param dy -> 1 tile Up/Down  (Down == -1) (Up == 1)
    */
    public void movePlayer(int dx,int dy)
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

    switch(dx)
    {
        case 1 -> // RIGHT
        {
            int targetTile = currentLayout[row][collumn + 1];

            switch(targetTile)
            {
                case 1 ->
                {
                    currentLayout[row][collumn] = 1;
                    currentLayout[row][collumn + 1] = 2;
                    
                }
                case 3 ->
                {
                    currentLayout[row][collumn] = 3; 
                    currentLayout[row][collumn + 1] = 4;
                }
            }
        }

        case -1 -> // LEFT
        {
            int targetTile = currentLayout[row][collumn - 1];

            if(targetTile == 1 || targetTile == 3)
            {
                currentLayout[row][collumn] = 1;
                currentLayout[row][collumn - 1] = 2;
            }
        }
    }

        buildFightRoom();
    }
    public boolean fight()
    {
        return true;
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
                    case 1 -> setTile("Sprites/Unselected_Attack.png", index);
                    case 2 -> setTile("Sprites/Selected_Attack.png", index);
                    case 3 -> setTile("Sprites/Unselected_Health.png", index);
                    case 4 -> setTile("Sprites/Selected_Health.png", index);
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
