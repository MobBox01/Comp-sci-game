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
        {0,1,90,1,1,1,1,1,22,0},
        {0,0,0,0,1,-100,0,0,1,0},
        {0,29,28,1,28,28,28,28,28,0},
        {0,0,0,1,0,0,0,0,0,0},
        {0,0,22,1,27,27,27,27,27,27},
        {0,0,29,28,28,28,28,28,10,0},
        {0,0,0,0,0,0,0,0,0,0}
    };

    //1 -> Void unwalkable
    //21 -> Verticle
    //22 -> Left side Corner
    //23 -> Right side Corne
    //24 -> Lower left side corner
    //25 -> Lower right side corner
    //26 -> curve around corner
    //27 -> Top walkable
    //28 -> Bottom walkable
    //30-> walkable
    //-30 -> Trap
    //100 -> City sprites
    //-100 -> Destroyed city sprites

    //300 -> Special building parts

    int[][] room1 = 
    {
        {0,0,0,0,0,0,0,0,0,0},
        {11,90,27,27,27,23,0,0,0,0},
        {0,22,30,-30,30,}
    };

    public RoomData()
    {
        rooms.add(room0);
    }

    /**
     * @param roomNumber
     * @return 2D-Array which is the layout of the room
     */
    public int[][] obtainRoom(int roomNumber)
    {
        return rooms.get(roomNumber);
    }

}
