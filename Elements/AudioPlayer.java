package Elements;

import java.io.*;
import javax.sound.sampled.*;

@SuppressWarnings("FieldMayBeFinal")
public class AudioPlayer 
{
    //Arrays
    private String[] fightComplexityPath = {new File(".") + "/Audio/BasicEnemies.wav", new File(".") + "/Audio/AdvancedEnemies.wav", new File(".") + "/Audio/Boss.wav"};
    private String[] roomComplexityPath = {new File(".") + "/Audio/BasicRooms.wav", new File(".") + "/Audio/AdvancedRooms.wav", new File(".") + "/Audio/FinalRooms.wav", new File(".") + "/Audio/EndingSong1.wav", new File(".") + "/Audio/EndingSongSecret.wav"};

    //Audio
    private Clip currentClip;
    private AudioInputStream audioInput;

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
