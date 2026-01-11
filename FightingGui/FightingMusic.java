package FightingGui;
import java.io.*;
import javax.sound.sampled.*;

public class FightingMusic 
{
    File file;
    AudioInputStream audio;
    Clip clip;

    public FightingMusic() 
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
    }

    public void fightingMusicStop()
    {
        clip.stop();
        clip.setFramePosition(0);
    }

    public void fightingMusicStart() 
    {
        clip.start();
    }

}
