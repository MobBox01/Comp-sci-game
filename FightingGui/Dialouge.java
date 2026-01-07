package FightingGui;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class Dialouge extends JFrame
{
    private JTextArea textBox = new JTextArea();

    public Dialouge()
    {
        setBounds(740, 540, 550, 180);
        setTitle("Dialogue");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.black);
        setLayout(new BorderLayout()); 
        setNewText("Peaceful...");

        setVisible(true);
    }

    /**
     * @param newText -> String, Dialouge you want to put 
     */
    public void setNewText(String newText)
    {
        textBox.setText(newText);
        textBox.setEditable(false);
        textBox.setFocusable(false);
        textBox.setBackground(Color.BLACK);
        textBox.setForeground(Color.WHITE);
        textBox.setFont(new Font("DialogInput", Font.BOLD, 16));
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        
        // remove blinking caret
        textBox.setCaret(new DefaultCaret() {@Override public void paint(Graphics g) {}});

        add(textBox, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}