package TheEnd;
import Elements.AudioPlayer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
@SuppressWarnings("FieldMayBeFinal")
//3, 4 Secret ending song
public class EndingWindow extends JFrame
{
    //Classess 
    private AudioPlayer audioPlayer;
    //Images
    private JLabel happyNies = new JLabel(new ImageIcon(new File(".").getAbsolutePath() + "/Sprites/Happy/HappyChemistryTeacherNies.jpg"));
    private JLabel happyKlus = new JLabel(new ImageIcon(new File(".").getAbsolutePath() + "/Sprites/Happy/HappyBandKlus.jpg"));
    private JLabel happyGurrito = new JLabel(new ImageIcon(new File(".").getAbsolutePath() + "/Sprites/Happy/HappyAustralianGurrito.jpg"));
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
        "Klus",
        "Happy"
    };
    private String[] endingDialougeList =
    {
        "Your light shines. The portal infront of you gets concealed. The world is saved as you see the darkness vanish in the world it tried to torment. You look up to see the sky clearing up as the evil energy disperses.",
        "Mr Nies has become very happy; and now is a chemistry teacher at the ######## high school. After returning to ######## he found his long lost sister Mrs.Nephew... Then he discovered he had his own niece",
        "Mr Gurrito has become a;ustralian; and blue;;;;;, he now works on his english project to destroy the british. Gurrito now fights for; ;kung; f;u; pan;da and fights against;; evil like giving kids candy.",
        "Mr Klus has formed the band \"The null pointer exception\". Klus still torments those who don't do their computer science homework and now writes books in his retirement of 1 hour.",
        "{IO}: The happy gang have a good ending, so does the world. But will you have a happy ending? Thats up to you Ferreto, my job here is done and i am going back to the {BIOS} realm to go back to eternal slumber."
    };

    
    public EndingWindow(AudioPlayer audioPlayer)
    {
        this.audioPlayer = audioPlayer;
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.WHITE);

        animation.setBounds(300,100,666,666);
        happyKlus.setBounds(800,200,300,400);
        happyNies.setBounds(200, 200, 300, 400);
        happyGurrito.setBounds(500,200,300,400);
        happyGurrito.setVisible(false);
        happyNies.setVisible(false);
        happyKlus.setVisible(false);
        //Dialouge
        dialougeContainer.setBounds(50,50,1200,120);
        dialougeContainer.add(textBox, BorderLayout.CENTER);

        //Text box
        textBox.setEditable(false);
        textBox.setFocusable(false);
        textBox.setBackground(Color.WHITE);
        textBox.setForeground(Color.BLACK);
        textBox.setFont(new Font("DialogInput", Font.BOLD, 25));
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        
        //Remove blinking caret
        textBox.setCaret(new DefaultCaret() {@Override public void paint(Graphics g) {}});

        add(dialougeContainer);
        add(animation);
        add(happyNies);
        add(happyGurrito);
        add(happyKlus);
        repaint();
        revalidate();
    }

    public void playHeartAnimation()
    {
        String[] path = {""};
        int[] i = {0};
        
        Timer timer = new Timer(200, time ->
            {//pixil-frame-0
                path[0] = "Sprites/Happy/Seal/pixil-frame-" + i[0] + ".png";
                if(i[0] == 100)
                {
                    ((Timer)time.getSource()).stop();
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
        happyGurrito.setVisible(true);
        happyGurrito.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
    }

    public void playNiesAnimation()
    {
        animation.setVisible(false);
        happyNies.setVisible(true);
        happyNies.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
    }

    private void timeForAnimation()
    {
        animationStatus = true;
        int[] i = {0};
        Timer timer = new Timer(7500,time ->
            {
                if(i[0] == 2)
                {
                    animationStatus = false;
                    ((Timer)time.getSource()).stop();
                }
                else 
                {
                    i[0]++;
                }
            }
        );

        timer.start();
    }

    public void playKlusAnimation()
    {
        happyKlus.setVisible(true);
        happyKlus.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
    }

    public void playAnimations()
    {
        isActive = true;

        if(Math.random() < .10) //10% chance of playing secret audio
        {
            audioPlayer.setRoomAudio(4);
        }
        else
        {
            audioPlayer.setRoomAudio(3);
        }
        
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
                            isActive = false;
                            ((Timer)loop.getSource()).stop();
                        }
                    }

                    timeForAnimation();
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
        Timer timer = new Timer(50 , time -> 
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
    
