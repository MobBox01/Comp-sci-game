package TheEnd;
import javax.swing.*;

public class EndingGui extends JFrame
{
    JLabel happyNies = new JLabel(new ImageIcon("Sprites/Happy/HappyNies.jpg"));
    JLabel deadKlus = new JLabel(new ImageIcon("Sprites/Happy/death.jpg"));
    JLabel australianGurrito = new JLabel(new ImageIcon("Sprites/Happy/Austrialian.jpg"));
    JLabel animation = new JLabel("");
    
    public EndingGui()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1000,800);

        add(animation);
        animation.setBounds(0,0,666,666);
        happyNies.setBounds(WIDTH, WIDTH, WIDTH, HEIGHT);
        
        //add(happyNies);
        repaint();
        revalidate();
        setVisible(true);
        playAnimation();
    }

    public void playAnimation()
    {
        String[] path = {""};
        int[] i = {0};
        Timer timer = new Timer(300, time ->
            {//pixil-frame-0
                path[0] = "Sprites/Happy/Seal/pixil-frame-" + i[0] + ".png";
                if(i[0] > 53)
                {
                    ((Timer)time.getSource()).stop();
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
    
