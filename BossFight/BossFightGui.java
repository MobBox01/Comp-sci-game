package BossFight;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.text.DefaultCaret;


@SuppressWarnings("FieldMayBeFinal")
public class BossFightGui extends JFrame
{
    private JLabel evilKlus = new JLabel(new ImageIcon("Sprites/Boss/Klus.jpg"));
    private JLabel evilNies = new JLabel(new ImageIcon("Sprites/Boss/Nies.jpg"));
    private JLabel evilGurrito = new JLabel(new ImageIcon("Sprites/Boss/Gurrito.jpg"));
    private JLabel opFull = new JLabel(new ImageIcon("Sprites/HealthStates/OP_Full.png"));
    private JTextArea textBox = new JTextArea();
    private JLabel option = new JLabel(new ImageIcon("Sprites/Selectors/Boss_Selected_Attack.png"));
    int[] fightLayout = {-200,1};
    boolean isDialougeBusy = false;

    private BossFightSystem bossFightSystem;

    public BossFightGui(BossFightSystem BossFightSystemPass)
    {
        bossFightSystem = BossFightSystemPass;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        opFull.setBounds(50,250,201,201);
        evilKlus.setBounds(500, 100, 144, 300);
        evilNies.setBounds(300,50,144,364);
        evilGurrito.setBounds(700, 100,192,144);
        option.setBounds(650,450,610,220);

        add(evilKlus);
        add(evilNies);
        add(evilGurrito);
        add(opFull);
        add(option);

        setBackground(Color.BLACK);
        setTitle("Holy crap its Evil Klus");
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);

        textBox.setEditable(false);
        textBox.setFocusable(false);
        textBox.setBackground(Color.BLACK);
        textBox.setForeground(Color.WHITE);
        textBox.setFont(new Font("DialogInput", Font.BOLD, 20));
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        textBox.setBounds(800,300,450,100);
        
        // remove blinking caret
        textBox.setCaret(new DefaultCaret() {@Override public void paint(Graphics g) {}});
        add(textBox);

        setNewText("E");
        revalidate();
        repaint();
    }

    /**
    * @param move_OR_selected -> Move the player <b>[IF]</b> the enter key is pressed preform a action
    */
    public void movePlayer(int move_OR_selected)
    {
        switch(move_OR_selected) 
        {
            case 1 ->
            {
                if(fightLayout[1]+1 != 4)
                {
                    fightLayout[1] += 1;
                }
            }
            case -1 ->
            {
                if(fightLayout[1] - 1 != 0)
                {
                    fightLayout[1] -= 1;
                }
            }
            case 90 ->
            {
                switch(fightLayout[1])
                {
                    case 1 -> dialouge(bossFightSystem.attack());
                    case 2 -> dialouge(bossFightSystem.heal());
                    case 3 -> dialouge(bossFightSystem.defend());
                }
            }
        }

        repaintOptions();
    }

    /**
     * @param newText -> Sets the text; No usage of rebounds or timers.
     */
    private void setNewText(String newText)
    {
        textBox.setText(newText);
    }

    /**
    * @param newText -> text appears at a fixed 100 milisecond rate.
    * there is a <b>REBOUND'</b> so you have to wait for the text to finish 
    * before doing anything else.
    * <p>   
    * <b>REBOUND':</b> A variable that is set to true and only becomes false once the method/wait is done
    * This prevents you from repeating a method or movement
    */
    public void dialouge(String newText)
    {
        isDialougeBusy = true;
        setNewText("");
        int[] i = {0};
        Timer timer = new Timer(100, time -> 
            {
                if(i[0] == newText.length())
                {
                    ((Timer)time.getSource()).stop();
                    isDialougeBusy = false;
                }
                else
                {
                    textBox.append(newText.substring(i[0],i[0]+1));
                    i[0] += 1;
                }
            }
        );

        timer.start();
    }

    /**
    * @return 
    * <b>[FALSE]:</b> If dialouge is not still being written
    * <p>
    * <b>[TRUE"]:</b> If dialouge is still being written
    */
    public boolean dialougeStatus()
    {
        return isDialougeBusy;
    }

    public void repaintOptions()
    {
        switch(fightLayout[1])
        {
            case 1 -> 
            {
                option.setIcon(new ImageIcon("Sprites/Selectors/Boss_Selected_Attack.png"));
            }
            case 2 -> 
            {
                option.setIcon(new ImageIcon("Sprites/Selectors/Boss_Selected_Heal.png"));
            }
            case 3 -> 
            {
                option.setIcon(new ImageIcon("Sprites/Selectors/Boss_Selected_Defend.png"));
            }
        }

        repaint();
        revalidate();
    }
}
