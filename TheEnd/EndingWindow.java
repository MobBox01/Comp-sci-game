package TheEnd;
import java.awt.Color;
import javax.swing.*;

public class EndingWindow extends JFrame
{
    JLabel happyNies = new JLabel(new ImageIcon("Sprites/Happy/HappyNies.jpg"));
    JLabel deadKlus = new JLabel(new ImageIcon("Sprites/Happy/death.jpg"));
    JLabel australianGurrito = new JLabel(new ImageIcon("Sprites/Happy/Austrialian.jpg"));
    JLabel animation = new JLabel("");
    private boolean debounce = true;
    
    public EndingWindow()
    {
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        add(animation);
        animation.setBounds(300,150,666,666);
        happyNies.setBounds(WIDTH, WIDTH, WIDTH, HEIGHT);
        getContentPane().setBackground(Color.WHITE);
        repaint();
        revalidate();
    }

    public void playAnimation()
    {
        debounce = false;
        String[] path = {""};
        int[] i = {0};
        Timer timer = new Timer(300, time ->
            {//pixil-frame-0
                path[0] = "Sprites/Happy/Seal/pixil-frame-" + i[0] + ".png";
                if(i[0] > 53)
                {
                    ((Timer)time.getSource()).stop();
                    debounce = true;
                }
                else
                {
                    animation.setIcon(new ImageIcon(path[0]));
                    repaint();
                    i[0]++;
                }
            }
        );

        timer.start();
    }
}
    
