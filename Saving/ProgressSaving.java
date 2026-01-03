package Saving;
import java.io.*;
import java.util.Scanner;

public class ProgressSaving 
{

    private Scanner sc;
    private RandomAccessFile fw;
    private int[] info = new int[3];


    /**
     * @return Array to initialize player, room etc.
     * [0] - Level
     * [1] - Xp
     * [3] - Room number - <b>DO NOT USE IN PLAYER CLASS<b>
     */
    public int[] obtainSavePoint() 
    {
        try
        {
        sc = new Scanner(new File("Saving/SaveFile.txt"));

        info[0] = sc.nextInt();
        info[1] = sc.nextInt();
        info[2] = sc.nextInt();
        }

        catch(IOException e)
        {
            System.out.println(e);
        }
        return info;
    }

    /**
     * Sets your stats when you enter a new room 
     * [1] - Level
     * [2] - XP
     * [3] - Room number
     */
    public void setSavePoint(int level, int xp, int roomNumber) 
    {
        try
        {
            info[0] = level;
            info[1] = xp;
            info[2] = roomNumber;
            fw = new RandomAccessFile("Saving/SaveFile.txt", "rw");
            fw.setLength(0);

            for (int i = 0; i < 3; i++) 
            {
                fw.writeBytes(info[i] + "\n"); 
            }
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

    }
}
