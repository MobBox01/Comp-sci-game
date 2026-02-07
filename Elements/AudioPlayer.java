package Elements;
import java.io.*;
import javax.sound.sampled.*;

public class AudioPlayer 
{
    File file;
    AudioInputStream audio;
    Clip clip;

    public void basicFight()
    {
        file = new File("Audio/EpicFight.wav");
        try 
        {
            audio = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audio);

        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
        {
            e.printStackTrace();
        }
        clip.start();
    }   

    public void musicStop()
    {
        clip.stop();
    }

}
