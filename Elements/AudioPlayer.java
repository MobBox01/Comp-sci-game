package Elements;

import java.io.*;
import javax.sound.sampled.*;

public class AudioPlayer 
{
    //Arrays
    String[] fightComplexityPath = {"Audio/BasicEnemies.wav", "Audio/AdvancedEnemies.wav", "Audio/Boss.wav"};
    String[] roomComplexityPath = {"Audio/BasicRooms.wav", "Audio/AdvancedRooms.wav", "Audio/FinalRooms.wav"};

    //Audio
    Clip currentClip;
    AudioInputStream audioInput;

    @SuppressWarnings("CallToPrintStackTrace")
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

    @SuppressWarnings("CallToPrintStackTrace")
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
