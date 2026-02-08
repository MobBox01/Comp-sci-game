package FightingGui;

public class Layout 
{

    int[][] fightLayout =
    {
        {0,0,0,0,0},
        {0,0,400,0,0},
        {-200,0,0,0,0},
        {0,2,3,5,0},
        {0,0,0,0,0}
    };

    //-200 -> FUll
    //-175 -> 75%
    //-150 -> 50%
    //-125 -> 25%
    //-100 -> DEAD 

    //300 -> Eye Bubbles enemy
    //301 ->


    //1 = Unselected attack
    //2 = Selected attack
    
    //3 = unSelected Hp
    //4 = selected attack

    public int[][] getFightMapping()
    {
        return fightLayout;
    }

}
