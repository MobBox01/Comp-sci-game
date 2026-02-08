package Saving;

import java.io.*;

import java.util.Scanner;

public class ProgressSaving 
{
    private Scanner fileScanner;
    private RandomAccessFile fileWriter;

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
        fileScanner = new Scanner(new File("Saving/SaveFile.txt"));

        info[0] = fileScanner.nextInt();
        info[1] = fileScanner.nextInt();
        info[2] = fileScanner.nextInt();
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
            fileWriter = new RandomAccessFile("Saving/SaveFile.txt", "rw");
            fileWriter.setLength(0);

            for (int i = 0; i < 3; i++) 
            {
                fileWriter.writeBytes(info[i] + "\n"); 
            }
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

    }
}
