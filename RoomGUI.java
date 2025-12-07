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
        setTitle("Void Run");
        setSize(750, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 7, 0, 0));

        buildRoom(0);

        setVisible(true);
    }

    /**
     * Fills the entire room with tiles
     */
    private void buildRoom(int roomNumber) 
    {
        roomArray = room.obtainRoom(roomNumber);

        for (int i = 0; i < 7; i++) 
        {
            for (int j = 0; j < 7; j++) 
            {
                JPanel p = new JPanel();
                p.setLayout(new BorderLayout());

                switch (roomArray[i][j]) 
                {
                    case 0 -> 
                    {
                        if((int)(Math.random()*10) == 2)
                        {
                            ImageIcon icon = new ImageIcon("RoomSprites/VoidEye.jpg");
                            Image img = icon.getImage().getScaledInstance(107, 107, Image.SCALE_SMOOTH);
                            JLabel l = new JLabel(new ImageIcon(img));
                            l.setHorizontalAlignment(JLabel.CENTER);
                            l.setVerticalAlignment(JLabel.CENTER);
                            p.add(l, BorderLayout.CENTER);
                        }
                        else
                        {
                            p.setBackground(Color.BLACK);
                        }
                    }

                    case 1 ->
                    {
                        ImageIcon icon = new ImageIcon("RoomSprites/Flooring.png");
                        Image img = icon.getImage().getScaledInstance(107, 107, Image.SCALE_SMOOTH);
                        JLabel l = new JLabel(new ImageIcon(img));
                        l.setHorizontalAlignment(JLabel.CENTER);
                        l.setVerticalAlignment(JLabel.CENTER);
                        p.add(l, BorderLayout.CENTER);                    
                    }

                    default -> p.setBackground(Color.GRAY);
                }

                add(p); 
                panel.add(p);
            }
        }
    }
}
