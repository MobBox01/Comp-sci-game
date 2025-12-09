package GUI;
import java.util.ArrayList;

public class Rooms 
{
    ArrayList<int[][]> rooms = new ArrayList<>();


    int[][] room0 = 
    {
        {0,-1,0,1,0,0,0,0,0,0},
        {0,0,1,0,0,0,0,0,-1,0},
        {0,0,-1,1,1,1,1,0,0,0},
        {0,0,90,1,1,1,1,1,0,1},
        {0,0,1,0,0,0,0,0,1,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,1,0,0,0,0,0,0},
        {0,0,1,0,0,0,-1,0,0,0},
        {0,0,1,1,0,0,0,0,0,0},
        {1,1,1,-1,1,1,1,1,1,1}

    };

    
    int[][] room1 = 
    {
        {0,0,0,1,1,0,0,1,1,1},
        {1,1,1,1,1,1,1,1,0,0},
        {0,0,1,0,0,0,0,0,0,0},
        {0,0,1,0,0,0,0,1,1,1},
        {0,0,1,1,1,1,0,0,0,0},
        {0,0,1,0,0,1,0,0,0,0},
        {0,0,1,0,0,1,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,1,1,0,0},
        {0,0,0,0,0,0,0,0,0,0}
    };




    public Rooms()
    {
        rooms.add(room0);
        rooms.add(room1);
    }

    

    /**
     * @param e 
     * @return
     * Obtain a 2D-Array which is the room layout.
     * [IF] e == 0, give a black void room for testing.
     */
    public int[][] obtainRoom(int e)
    {
        return rooms.get(e);
    }

}
