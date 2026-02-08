package Elements;

import java.io.*;

import javax.sound.sampled.*;

public class AudioPlayer 
{
    File fightFile;
    AudioInputStream fightAudio;
    Clip fightClip;

    File roomFile;
    AudioInputStream roomAudio;
    Clip roomClip;

    /**
     * Play the song for basic enemies
     */
    public void basicFight()
    {
        setFightSong("Audio/BasicEnemies.wav");
    }

    /**
     * Play the song for advanced enemies
     */
    public void advancedFight()
    {
        setFightSong("Audio/AdvancedEnemies.wav");
    }

    /**
     * @param songPath String that has the path to Audio Folder or Files 
     * <p>
     * Create new Clip pointer.
     */
    private void setFightSong(String songPath)
    {
        fightFile = new File(songPath);
        try 
        {
            fightAudio = AudioSystem.getAudioInputStream(fightFile);
            fightClip = AudioSystem.getClip();
            fightClip.open(fightAudio);

        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
        {
            e.printStackTrace();
        }
        fightClip.start();

        areaMusicStop();
    }   

    /**
     * Stop area music
     */
    public void fightMusicStop(boolean advanced)
    {
        if(fightClip != null)
        {
            fightClip.stop();
            fightClip.close();
            fightClip = null;
            if(advanced == false)
            {
                setAreaSound("Audio/BasicRooms.wav");
            }
            else if(advanced)
            {
                setAreaSound("Audio/AdvancedRooms.wav");
            }
        }
    }

    /**
     * @param songPath String that has the path to Audio Folder or Files 
     * <p>
     * Create new Clip pointer.
     */
    private void setAreaSound(String songPath)
    {
        roomFile = new File(songPath);
        try 
        {
            roomAudio = AudioSystem.getAudioInputStream(roomFile);
            roomClip = AudioSystem.getClip();
            roomClip.open(roomAudio);

        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
        {
            e.printStackTrace();
        }
        roomClip.start();
    }  

    
    /**
     * Stop fight music, enabled area music right after!
     */
    public void areaMusicStop()
    {
        if(roomClip != null)
        {
            roomClip.stop();
            roomClip.close();
            roomClip = null;
        }
    }
}
