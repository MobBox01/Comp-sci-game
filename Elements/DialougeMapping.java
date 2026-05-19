package Elements;

@SuppressWarnings("FieldMayBeFinal")
public class DialougeMapping
{
    private int dialougeKeyPosition;
    private int animationKeyPosition;
    private String[] dialouge = 
    {
        "{IO}: Its the final strech... Ferreto don't give up. Destroy that portal. T-10 minutes before demon appearance. You defeated a majority of enemies they are to afraid. Your to powerful for them all shine that light against the void.",
        "{IO}: Ferreto, the portal is near the furthure you go the void will disappear... This long journey(5 minute game lmao) has been worth it, hasn't it? You are strong to fight back against them",
        "{IO}: So like anyway, why the hell didn't you do your computer science homework? Literally was befuddled throughout the entire boss encounter, i mean you got like 6666 XP so thats a win but still.............................................................",
        "{Ferreto}: Oh my god, STOP ASKING ME! I didn't feel like it. They're views have changed now so something positive came out of it, now lets stop this demon from forming\n",
        "{IO}: The portal heart feels weak... we caught it in a early stage of development then but its strange how much void matter came out, probably because of this the void matter is also weaker to your light!",
        "{Ferreto}: The light is quite a lot, why does it only conceal it, does the linkage breaking just destroy the void? \n\n{IO}: Yes, your power has the ability to protect, but said protection can also kill depending on if something requires linkage",
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
        false
    };

    private String[] animation = 
    {
        "Conceal",
        "Nies",
        "Gurrito",
        "Kluss",
        "Happy"
    };

    private int[] animationKey =
    {
        30,
        0,
        0,
        0,
        0
    };

    private boolean[] animationStatus =
    {
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
     * @return Text of current room; set status to true
     */
    public String getDialougeText()
    {
        dialougeStatus[dialougeKeyPosition] = true; //found issue
        return dialouge[dialougeKeyPosition];
    }

    public boolean dialougeStatus()
    {
        return dialougeStatus[dialougeKeyPosition];
    }

    /**
     * @param roomNumber Check if current room has a dialouge to play<p>
      @return <b>True</b> if the room has dialouge. Set the key to this room
     */
    public boolean isAnimationInRoom(int roomNumber)
    {
        for(int key = 0; key < animationKey.length; key++)
        {
            if(roomNumber == animationKey[key])
            {
                animationStatus[animationKeyPosition] = true; 
                animationKeyPosition = key;
                return true;
            }
        }
        return false;
    }

    public boolean animationStatus()
    {
        return animationStatus[animationKeyPosition];
    }





    

}