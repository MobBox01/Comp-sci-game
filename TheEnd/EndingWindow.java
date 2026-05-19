package TheEnd;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

@SuppressWarnings("FieldMayBeFinal")
public class EndingWindow extends JFrame
{
    private JLabel happyNies = new JLabel(new ImageIcon("Sprites/Happy/HappyNies.jpg"));
    private JLabel deadKlus = new JLabel(new ImageIcon("Sprites/Happy/death.jpg"));
    private JLabel australianGurrito = new JLabel(new ImageIcon("Sprites/Happy/Austrialian.jpg"));
    private JLabel animation = new JLabel("");
    private boolean debounce = true;
    private JTextArea textBox = new JTextArea();
    private JPanel dialougeContainer = new JPanel(new BorderLayout());
    private boolean animationStatus = true;
    private boolean dialougeStatus = true;

    
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
        animationStatus = true;
        String[] path = {""};
        int[] i = {0};
        Timer timer = new Timer(300, time ->
            {//pixil-frame-0
                path[0] = "Sprites/Happy/Seal/pixil-frame-" + i[0] + ".png";
                if(i[0] > 53)
                {
                    ((Timer)time.getSource()).stop();
                    animationStatus = false;
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


    public void endingDialouge(String newText)
    {
        dialougeStatus = true;
        setNewText("");
        int[] i = {0};
        Timer timer = new Timer(400, time -> 
            {
                if(i[0] == newText.length())
                {
                    ((Timer)time.getSource()).stop();
                    dialougeStatus = false;
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
         return dialougeStatus;
    }

    public boolean isAnimationActive()
    {
        return animationStatus;
    }

}
    
