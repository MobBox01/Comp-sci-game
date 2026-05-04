package Elements;

import java.io.*;
import javax.sound.sampled.*;

public class AudioPlayer 
{
    String[] fightComplexityPath = {"Audio/BasicEnemies.wav", "Audio/AdvancedEnemies.wav", "Audio/Boss.wav"};
    String[] roomComplexityPath = {"Audio/BasicRooms.wav", "Audio/AdvancedRooms.wav"};
    Clip currentClip;
    AudioInputStream audioInput;

    public void setFightAudio(int fightComplexity)
    {  
        try 
        {
            if(currentClip != null)
            {
                currentClip.stop();
                currentClip.close();
                currentClip = null;
                audioInput.close();
                audioInput = null;
            }

            audioInput = AudioSystem.getAudioInputStream(new File(fightComplexityPath[fightComplexity]));
            currentClip = AudioSystem.getClip();
            currentClip.open(audioInput);
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);

            currentClip.start();
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
        {
            e.printStackTrace();
        }
    }

    public void setRoomAudio(int roomComplexity)
    {
        try 
        {
            if(currentClip != null)
            {
                currentClip.stop();
                currentClip.close();
                currentClip = null;
                audioInput.close();
                audioInput = null;
            }

            audioInput = AudioSystem.getAudioInputStream(new File(roomComplexityPath[roomComplexity]));
            currentClip = AudioSystem.getClip();
            currentClip.open(audioInput);
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);

            currentClip.start();
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
        {
            e.printStackTrace();
        }
    }
}
