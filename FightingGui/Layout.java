package FightingGui;

public class Layout 
{

    int[][] fightLayout =
    {
        {0,0,0,0,0},
        {0,0,1,0,0},
        {-200,0,0,0,0},
        {0,2,3,5,0},
        {0,0,0,0,0}
    };


    //-200 -> FUll
    //-175 -> 75%
    //-150 -> 50%
    //-125 -> 25%
    //-100 -> DEAD 


    //1 = Unselected attack
    //2 = Selected attack
    
    //3 = unSelected Hp
    //4 = selected attack

    public int[][] getFightMapping()
    {
        return fightLayout;
    }

}
