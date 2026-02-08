package RoomHandling;
import java.util.ArrayList;

public class RoomData 
{
    ArrayList<int[][]> rooms = new ArrayList<>();


    int[][] room0=
    {
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,100,100,100,-100,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,1,90,1,1,1,1,1,1,0},
        {0,0,0,0,1,-100,0,0,1,0},
        {0,1,1,1,1,1,1,1,1,0},
        {0,0,0,1,0,0,0,0,0,0},
        {0,0,1,1,1,1,0,0,0,0},
        {0,0,0,0,1,1,1,1,10,0},
        {0,0,0,0,0,0,0,0,0,0}
    };

    int[][] room1=
    {
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,100,100,-100,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,1,90,1,0,1,1,1,1,0},
        {0,0,0,0,1,-100,0,1,1,0},
        {0,1,1,1,1,1,1,1,1,0},
        {0,0,0,1,0,0,0,0,0,0},
        {0,0,1,1,1,1,0,0,0,0},
        {0,0,-100,0,1,1,1,1,10,0},
        {0,0,100,-100,-100,-100,100,100,0,0}
    };

    //0 -> Void
    //1 >= x > 10 -> Walkable area

    //10 -> Next Room

    //-100 >= x >= -200 -> Props destroyed by void
    //100 >= x <= 200 -> Props
    
    //300 >= x -> Big multi tile props 

    public RoomData()
    {
        rooms.add(room0);
        rooms.add(room1);
    }

    /**
     * @param roomNumber
     * @return 2D-Array which is the layout of the room
     */
    public int[][] obtainRoom(int roomNumber)
    {
        return rooms.get(roomNumber);
    }

    public boolean isAdvancedRooms(int roomNumber)
    {
        return roomNumber >= 0;
    }

}
