package Unused;
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
    public int[] obtainSavePoint() throws IOException
    {
        sc = new Scanner(new File("SaveFile.txt"));

        info[0] = sc.nextInt();
        info[1] = sc.nextInt();
        info[2] = sc.nextInt();

        return info;

    }

    /**
     * Sets your stats when you save 
     * [1] - Level
     * [2] - XP
     * [3] - Room number
     * /!\ YOU WILL LOSE PROGRESS IF YOU SAVE ANYWHERE ELSE /!\
     */
    public void setSavePoint(int[] input) throws IOException 
    {

        fw = new RandomAccessFile("SaveFile.txt", "rw");
        fw.setLength(0);

        for (int i = 0; i < 3; i++) 
        {
            fw.writeBytes(info[i] + "\n"); 
        }

    }
}
