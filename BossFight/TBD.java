package BossFight;
import javax.swing.*;
import Stats.Player;
public class TBD extends JFrame
{
    Player player;
    public TBD(Player playerPass)
    {
        player = playerPass;

        setBounds(0,0,JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(false);
    }

    public boolean isBossFight()
    {
        return false;
    }
}
