package Elements;

@SuppressWarnings("FieldMayBeFinal")
public class DialougeMapping
{
    //Position for arrays, to keep syncing between all 3 arrays
    private int dialougeKeyPosition;
    private String[] dialouge = 
    {
        "{IO}: Its the final strech... Ferreto don't give up. Destroy that portal. T-10 minutes before demon appearance. You defeated a majority of enemies they are to afraid. Your to powerful for them all shine that light against the void.",
        "{IO}: Ferreto, the portal is near the furthure you go the void will disappear... This long journey(5 minute game lmao) has been worth it, hasn't it? You are strong to fight back against them",
        "{IO}: So like anyway, why the hell didn't you do your computer science homework? Literally was befuddled throughout the entire boss encounter, i mean you got like 6666 XP so thats a win but still.............................................................",
        "{Ferreto}: Oh my god, STOP ASKING ME! I didn't feel like it. They're views have changed now so something positive came out of it, now lets stop this demon from forming\n",
        "{IO}: The portal heart feels weak... we caught it in a early stage of development then but its strange how much void matter came out, probably because of this the void matter is also weaker to your light!",
        "{Ferreto}: I'm ready.",
    };

    private int[] dialougeKey = 
    {
       11,
       12,
       14,
       15,
       16,
       19,
    };

    private boolean[] dialougeStatus = 
    {
        false, 
        false, 
        false, 
        false, 
        false, 
        false,
        false
    };

    /**
     * @param roomNumber Check if current room has a dialouge to play<p>
     * @return <b>True</b> if the room has dialouge. Set the key to this room
     */
    public boolean isDialougeInRoom(int roomNumber)
    {
        for(int key = 0; key < dialougeKey.length; key++)
        {
            if(roomNumber == dialougeKey[key])
            {
                dialougeKeyPosition = key;
                return true;
            }
        }
        return false;
    }

    /**
     * @return Text of current room
     */
    public String getDialougeText()
    {
        dialougeStatus[dialougeKeyPosition] = true;
        return dialouge[dialougeKeyPosition];
    }

    /**
     * @return True if dialouge is active; false otherwise
     */
    public boolean isDialougeActive()
    {
        return dialougeStatus[dialougeKeyPosition];
    }
}