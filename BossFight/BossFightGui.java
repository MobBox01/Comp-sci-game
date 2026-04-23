package BossFight;

import java.awt.Color;
import javax.swing.*;
import javax.swing.text.DefaultCaret;


@SuppressWarnings("FieldMayBeFinal")
public class BossFightGui extends JFrame
{
    private JLabel evilKlus = new JLabel(new ImageIcon("Sprites/Boss/Klus.jpg"));
    private JLabel evilNies = new JLabel(new ImageIcon("Sprites/Boss/Nies.jpg"));
    private JLabel evilGurrito = new JLabel(new ImageIcon("Sprites/Boss/Gurrito.jpg"));
    private JTextArea textBox = new JTextArea();
    private int[] options = new int
    {

    };

    
    public BossFightGui()
    {
        setBounds(0,0,1000,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        evilKlus.setBounds(500, 200, 144, 300);
        evilNies.setBounds(0,0,144,364);
        evilGurrito.setBounds(0, 0,144,192);
        add(evilKlus);
        add(evilNies);
        add(evilGurrito);
        setBackground(Color.BLACK);
        setTitle("Holy crap its Evil Klus");

        textBox.setText("Holy Crap you forgot your assignments!");
        textBox.setEditable(false);
        textBox.setFocusable(false);
        textBox.setBackground(Color.BLACK);
        textBox.setForeground(Color.WHITE);
        textBox.setFont(new Font("DialogInput", Font.BOLD, 16));
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        
        // remove blinking caret
        textBox.setCaret(new DefaultCaret() {@Override public void paint(Graphics g) {}});
        add(textBox);
        //TODO: Set a position for textbox
        //TODO: Set up graphics 
        //TODO: Set up movable graphics
        repaint();
        revalidate();
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
}
