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
    //Images
    private JLabel happyNies = new JLabel(new ImageIcon("Sprites/Happy/HappyNies.jpg"));
    private JLabel deadKlus = new JLabel(new ImageIcon("Sprites/Happy/death.jpg"));
    private JLabel australianGurrito = new JLabel(new ImageIcon("Sprites/Happy/Austrialian.jpg"));
    private JLabel animation = new JLabel("");

    //Dialouge
    private JTextArea textBox = new JTextArea();
    private JPanel dialougeContainer = new JPanel(new BorderLayout());

    //Booleans
    private boolean animationStatus = false;
    private boolean dialougeStatus = false;
    private boolean isActive = false;

    //Arrays
    private String[] animationNames = 
    {
        "Conceal",
        "Nies",
        "Gurrito",
        "Kluss",
        "Happy"
    };
    private String[] endingDialougeList =
    {
        "Your light shines, as the portal infront of you gets concealed and destroyed",
        "",
        "",
        "",
        ""
    };

    
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
        dialougeContainer.setBounds(0,0,550,200);
        dialougeContainer.add(textBox, BorderLayout.CENTER);

        //Text box
        textBox.setEditable(false);
        textBox.setFocusable(false);
        textBox.setBackground(Color.WHITE);
        textBox.setForeground(Color.BLACK);
        textBox.setFont(new Font("DialogInput", Font.BOLD, 16));
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        
        //Remove blinking caret
        textBox.setCaret(new DefaultCaret() {@Override public void paint(Graphics g) {}});

        add(dialougeContainer);
        repaint();
        revalidate();
    }

    public void playHeartAnimation()
    {
        animationStatus = true;
        String[] path = {""};
        int[] i = {0};
        Timer timer = new Timer(300, time ->
            {//pixil-frame-0
                path[0] = "Sprites/Happy/Seal/pixil-frame-" + i[0] + ".png";
                if(i[0] == 100)
                {
                    ((Timer)time.getSource()).stop();
                    animationStatus = false;
                }
                else if(i[0] < 54)
                {
                    animation.setIcon(new ImageIcon(path[0]));
                    repaint();
                    i[0]++;
                }
                else
                {
                    i[0]++;
                }
            }
        );

        timer.start();
    }

    public void playGurritoAnimation()
    {
        //TODO: Make Gurrito Animation
    }

    public void playNiesAnimation()
    {
        //TODO: Make Nies Animation
    }

    public void playKlusAnimation()
    {
        //TODO: Make Klus Animation

    }

    public void playHappyGangAnimation()
    {
        //TODO: Make Happy Gang Animation

    }

    public void playAnimations()
    {
        isActive = true;
        int[] i = {0};
        Timer looper = new Timer(500, loop ->
            {
                if(!dialougeStatus && !animationStatus)
                {
                    switch(animationNames[i[0]])
                    {
                        case "Conceal" -> playHeartAnimation();
                        case "Gurrito" -> playGurritoAnimation();
                        case "Nies" -> playNiesAnimation();
                        case "Klus" -> playKlusAnimation();
                        case "Happy" -> 
                        {
                            playHappyGangAnimation();
                            isActive = false;
                            ((Timer)loop.getSource()).stop();
                        }
                    }

                    endingDialouge(endingDialougeList[i[0]]);
                    i[0]++;
                }
            }
        );
        
        looper.start();
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
        Timer timer = new Timer(170, time -> 
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

    public boolean endingOngoing()
    {
        return this.isActive;
    }
    /*public boolean isDialougeActive()
    {
        return dialougeStatus;
    }

    public boolean isAnimationActive()
    {
        return animationStatus;
    }
*/
}
    
