package TheEnd;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class IntroWindow extends JFrame
{
    private JLabel tutorialImage = new JLabel(new File(".").getAbsolutePath() + "/Sprites/Intro/Tutorial.png");
    public IntroWindow()
    {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(false);
        tutorialImage.setBounds(200,200,666,666);

        add(tutorialImage);
    }
}
//TODO: Work on intro screen