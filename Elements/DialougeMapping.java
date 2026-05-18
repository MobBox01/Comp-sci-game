package Elements;

public class DialougeMapping
{
    private int currentKey;
    private String[] dialouge = 
    {
        "{666}: Its the final strech... Ferreto don't give up. Destroy that portal. T-10 minutes before demon appearance. You defeated a majority of enemies they are to afraid. Your to powerful for them all shine that light against the void.",
        "{666}: Ferreto, the portal is near the furthure you go the void will disappear... This long journey(5 minute game lmao) has been worth it, hasn't it? You are strong to fight back against them",
        "{666}: So like anyway, why the hell didn't you do your computer science homework? Literally was befuddled throughout the entire boss encounter, i mean you got like 6666 XP so thats a win but still.............................................................",
        "{Ferreto}: Oh my god, STOP ASKING ME! I didn't feel like it. They're views have changed now so something positive came out of it, now lets stop this demon from forming\n",
        "{666}: The portal heart feels weak... we caught it in a early stage of development then but its strange how much void matter came out, probably because of this the void matter is also weaker to your light!",
        "{Ferreto}: The light is quite a lot, why does it only conceal it, does the linkage breaking just destroy the void? \n{666}: Yes, your power has the ability to protect, but said protection can also kill depending on if something requires linkage"
    };

    private int[] dialougeKey = 
    {
       11,
       12,
       14,
       15,
       16,
       19
    };
    private boolean[] dialougeStatus = 
    {false, false, false, false, false, false};


    public boolean isDialougeInRoom(int roomNumber)
    {
        for(int i = 0; i < dialougeKey.length; i++)
        {
            if(roomNumber == dialougeKey[i])
            {
                currentKey = dialougeKey[i];
                return true;
            }
        }
        return false;
    }

    public String obtainText()
    {
        dialougeStatus[currentKey] = true;
        return dialouge[currentKey];
    }

    public boolean obtainStatus()
    {
        return dialougeStatus[currentKey];
    }

    

}