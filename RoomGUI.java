import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class RoomGUI extends JFrame 
{

    private ArrayList<JPanel> panel = new ArrayList<>();
    
    private Rooms room = new Rooms();
    private int[][] roomArray;
    
    /**
     * Sets up window and starting room
     */
    public RoomGUI() 
    {
        
        setTitle("Void Game");
        setSize(750, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 7, 0, 0));

        for(int i = 0; i < 49; i++)
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

        for(int i = 0; i < 49; i++)
        {
            panel.get(i).removeAll();
        }

        for (int i = 0; i < 7; i++) 
        {
            for (int j = 0; j < 7; j++) 
            {
                switch (roomArray[i][j]) 
                {
                    case 0 -> 
                    {
                        if((int)(Math.random()*10) == 2)
                        {
                            setTile("RoomSprites/VoidEye.jpg", index);
                        }
                        else
                        {
                            panel.get(index).setBackground(Color.BLACK);
                        }
                    }

                    case 1 ->
                    {
                        setTile("RoomSprites/Flooring.png", index);
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
     * @param index Index of the 7x7 for loop
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
