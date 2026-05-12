package TheEnd;
import javax.swing.*;

public class EndingGui extends JFrame
{
    JLabel happyNies = new JLabel(new ImageIcon("Sprites/Happy/HappyNies.jpg"));
    JLabel deadKlus = new JLabel(new ImageIcon("Sprites/Happy/death.jpg"));
    JLabel australianGurrito = new JLabel(new ImageIcon("Sprites/Happy/Austrialian.jpg"));
    
    public EndingGui()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1000,800);

        happyNies.setBounds(WIDTH, WIDTH, WIDTH, HEIGHT);
        
        add(happyNies);
        repaint();
        revalidate();
        setVisible(false);
    }
}
    
