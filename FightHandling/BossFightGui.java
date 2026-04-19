package FightHandling;

import javax.swing.*;


//This will be a bit complex to do... focus on stats first
public class BossFightGui extends JFrame
{
    JPanel enemy1 = new JPanel();
    JPanel enemy2 = new JPanel();
    JPanel enemy3 = new JPanel();
    public BossFightGui()
    {
        setBounds(0,0,JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
