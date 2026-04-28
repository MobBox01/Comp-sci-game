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
    boolean isDialougeBusy = true;

    private BossFightSystem bossFightSystem;

    public BossFightGui(BossFightSystem BossFightSystemPass)
    {
        bossFightSystem = BossFightSystemPass;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        opFull.setBounds(50,250,201,201);
        evilKlus.setBounds(500, 100, 144, 300);
        evilNies.setBounds(300,100,144,364);
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
        textBox.setBounds(800,300,450,300);
        
        // remove blinking caret
        textBox.setCaret(new DefaultCaret() {@Override public void paint(Graphics g) {}});
        add(textBox);

        setNewText("E");
        //TODO: Set up graphics 
        //TODO: Set up movable graphics
        revalidate();
        repaint();
        setVisible(true);
    }

    //TODO:Work on player movement after implementing option graphics
    public void movePlayer(int key)
    {
        System.out.println("movement");
        switch (key) 
        {
            case 1 ->
            {
                if(fightLayout[1] == 3) 
                {}
                else 
                {
                    fightLayout[1] += 1;
                }
            }
            case -1 ->
            {
                if(fightLayout[1] == 1) 
                {}
                else 
                {
                    fightLayout[1] -= 1;
                }
            }
        }

        repaintOptions();
    }

    public boolean isBossFight()
    {
        return true;
    }

    public void setNewText(String newText)
    {
        textBox.setText(newText);
    }

    public void dialouge(String newText)
    {
        setNewText("");
        isDialougeBusy = true;
        System.out.println("Set to true");
        String[] chars = newText.split("");
        for(String e: chars)
        {
            try
            {
                Thread.sleep(100);
            }
            catch(InterruptedException f)
            {
                System.out.println(f);
            }

            textBox.append(e);
        }
        isDialougeBusy = false;
        System.out.println("Set to " + isDialougeBusy);
    }

    public boolean dialougeStatus()
    {
        System.out.println("Status: " + isDialougeBusy);
        return isDialougeBusy;
    }

    public void repaintOptions()
    {
        switch(fightLayout[1])
        {
            case 1 -> option.setIcon(new ImageIcon("Sprites/Selectors/Boss_Selected_Attack.png"));
            case 2 -> option.setIcon(new ImageIcon("Sprites/Selectors/Boss_Selected_Heal.png"));
            case 3 -> option.setIcon(new ImageIcon("Sprites/Selectors/Boss_Selected_Defend.png"));
        }

        repaint();
        revalidate();
    }
}
