package TheEnd;
import javax.swing.*;

public class EndingGui extends JFrame
{
    JLabel happyNies = new JLabel(new ImageIcon("Sprites/Happy/HappyNies.jpeg"));
    JLabel deadKlus = new JLabel(new ImageIcon("Sprites/Happy/death.jpeg"));
    public EndingGui()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1000,800);

        repaint();
        revalidate();
        setVisible(false);
    }
}