package TheEnd;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class EndingWindow extends JFrame
{
    JLabel happyNies = new JLabel(new ImageIcon("Sprites/Happy/HappyNies.jpg"));
    JLabel deadKlus = new JLabel(new ImageIcon("Sprites/Happy/death.jpg"));
    JLabel australianGurrito = new JLabel(new ImageIcon("Sprites/Happy/Austrialian.jpg"));
    JLabel animation = new JLabel("");
    private boolean debounce = true;
    private JTextArea textBox = new JTextArea();
    private JPanel dialougeContainer = new JPanel(new BorderLayout());
        boolean isDialougeBusy = true;

    
    public EndingWindow()
    {
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        add(animation);
        animation.setBounds(300,150,666,666);
        happyNies.setBounds(WIDTH, WIDTH, WIDTH, HEIGHT);
        getContentPane().setBackground(Color.WHITE);
        //Dialouge
        dialougeContainer.setBounds(550,550,550,200);
        dialougeContainer.add(textBox, BorderLayout.CENTER);

        textBox.setEditable(false);
        textBox.setFocusable(false);
        textBox.setBackground(Color.BLACK);
        textBox.setForeground(Color.BLACK);
        textBox.setFont(new Font("DialogInput", Font.BOLD, 16));
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        
        // remove blinking caret
        textBox.setCaret(new DefaultCaret() {@Override public void paint(Graphics g) {}});
        repaint();
        revalidate();
    }

    public void playAnimation()
    {
        //Working on dialouge right now
        //dialouge("As the light takes over... You look back. You see everything destroyed and rubble. Survivors are slowly getting up as your light heals them. What now is infront of you is just light. You have begun to seal the core... \n\nPress enter to continue....");
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




    public void setNewText(String newText)
    {
        textBox.setText(newText);
    }

    
    public void dialouge(String newText)
    {
        isDialougeBusy = true;
        setNewText("");
        int[] i = {0};
        Timer timer = new Timer(30, time -> 
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

    public boolean isDialougeActive()
    {
        return isDialougeBusy;
    }

}
    
