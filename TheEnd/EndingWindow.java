package TheEnd;
import Elements.AudioPlayer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

@SuppressWarnings("FieldMayBeFinal")
//3, 4 Secret ending song
public class EndingWindow extends JFrame
{
    //Classess 
    private AudioPlayer audioPlayer;
    //Images
    private JLabel happyNies = new JLabel(new ImageIcon("Sprites/Happy/HappyChemistryTeacherNies.jpg"));
    //private JLabel deadKlus = new JLabel(new ImageIcon("Sprites/Happy/Happy.jpg"));
    private JLabel happyGurrito = new JLabel(new ImageIcon("Sprites/Happy/HappyAustralianGurrito.jpg"));
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
        "Your light shines, as the portal infront of you gets concealed and destroyed. The world is saved as you see the darkness vanish, all portals get destroyed as you see the energy in the sky finally disperse",
        "Mr Nies has become very happy, and now is a chemistry teacher at the fairport highschool. AFter returning to fairport, he found his long lost sister Mrs.Nephew... Then he discovered he had his own niece",
        "Mr Gurrito has become australian and blue, he now works on his english project to destroy the british. He now fights for kung fu panda in china.",
        "Mr Klus has formed the band \"The null pointer exception\" and now torments those who do their computer science homework and saved the world from certain anniahlation",
        ""
    };

    
    public EndingWindow(AudioPlayer audioPlayer)
    {
        this.audioPlayer = audioPlayer;
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.WHITE);

        animation.setBounds(300,100,666,666);
        happyNies.setBounds(200, 100, 300, 500);
        happyGurrito.setBounds(500,100,300,500);
        happyGurrito.setVisible(false);
        happyNies.setVisible(false);
        
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
        //TODO: Make Klus Animation
        //MR KLUS STARTED HIS OWN BAND, THE NULL POINTER EXCEPTION. IT BECAME VERY FAMOUS AND HE NOW SHARES HIS
        //EXPERIENCES IN FAIRPORT HIGHSCHOOL, AND NOW TRAUMATIZES KIDS WITH THE NULL POINTER EXCEPTION BEING A ANIMORPHIC
        //BEING

    }

    public void playHappyGangAnimation()
    {
        //TODO: Make Happy Gang Animation

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
                            playHappyGangAnimation();
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
    
