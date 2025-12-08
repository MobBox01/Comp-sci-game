package GUI;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class RoomGui extends JFrame 
{

    private ArrayList<JPanel> panel = new ArrayList<>();
    
    private Rooms room = new Rooms();
    private int[][] roomArray;
    private int x = 10;
    private int y = 10;
    private int product = x*y;
    
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

        roomArray = room.obtainRoom(0);
        setVisible(true);
    }

    /**
     * @param roomNumber
     * Obtain room, then build it
     */
    public void enteredRoom(int roomNumber)
    {
        roomArray = room.obtainRoom(roomNumber);

        buildRoom();
    }


    /**
     * @param x -> 1 tile left/right (Left > 0)
     * @param y Y -> 1 tile Up/Down  (Down > 0)
     */
    public void movePlayer(int x,int y)
    {

    }

    /**
     * Index records as you loop preventing improper iteration 
     * <p>
     * Cleans up all tile panels
     * <p>
     * Loops until each tile is filled 
     * <p>
     * <b>[CASE 0]: Void eyes will always change when u reenter room, lore reason; not a bug.
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
            for (int j = 0; j < x; j++) 
            {
                switch (roomArray[i][j]) 
                {
                    case 0 -> 
                    {
                        if((int)(Math.random()*10) == 2)
                        {
                            setTile("Sprites/VoidEye.jpg",index);
                        }
                        else
                        {
                            panel.get(index).setBackground(Color.BLACK);
                        }
                    }

                    case 1 ->
                    {
                        setTile("Sprites/Flooring.png", index);
                    }

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
     */
    private void setTile(String tilePath, int index)
    {
        ImageIcon icon = new ImageIcon(tilePath);
        Image img = icon.getImage().getScaledInstance(107, 107, Image.SCALE_SMOOTH);
        JLabel l = new JLabel(new ImageIcon(img));
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        panel.get(index).add(l, BorderLayout.CENTER);  
    }
}
