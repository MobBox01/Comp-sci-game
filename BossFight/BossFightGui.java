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
    private JTextArea textBox = new JTextArea();
    int[] fightLayout = {-200,2,3,5};

    public BossFightGui()
    {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        evilKlus.setBounds(500, 200, 144, 300);
        evilNies.setBounds(0,0,144,364);
        evilGurrito.setBounds(1000, 0,144,192);

        add(evilKlus);
        add(evilNies);
        add(evilGurrito);
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
        textBox.setBounds(100,500,200,200);
        
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
    }

    public boolean isBossFight()
    {
        return false;
    }

    public void setNewText(String newText)
    {
        textBox.setText(newText);
    }

    public  void dialouge(String newText)
    {
        setNewText("");
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
    }
}
