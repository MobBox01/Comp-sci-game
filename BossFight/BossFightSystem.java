package BossFight;

import Stats.Enemy;
import Stats.Player;

@SuppressWarnings("FieldMayBeFinal")
public class BossFightSystem 
{
    private Player player;
    private Enemy evilKlus = new Enemy(1,20, 666, .8, "Evil Klus");
    private Enemy evilNies = new Enemy(1, 10,666, .4, "Evil Nies");
    private Enemy evilGurrito = new Enemy(1,5, 666, 0.2, "Evil Gurrito");
    private BossFightWindow bossFightWindow;

    public BossFightSystem(Player playerPass) 
    {
        player = playerPass;
    }
    //You defeat evil nies, then evil gurrito, finally defeat mr klus
    /**
     * Initialize the new enemy
     * Set dialouge text for new enemy encounter
     */
    public void bossEncounter(int bossLevel)
    {
        //"Rat.. You haven't done your CSA Homework... for this you must pay"
    }

    /**
     * 1/2 chance for failure, reduce incoming attack by 40%
    */
    public String defend()
    {
        return "Debug: Boss Defend option";
    }

    /**
     * Attack the enemy
     * If less then or equal to 40% HP; 50% chance of being counter attacked with x2 damage
     */
    public String attack()
    {
        int damage = player.attackAction();
        double dialougeRandom = Math.random();
        String extraDialouge = "";
        if(evilGurrito.isAlive())
        {
            if(dialougeRandom >= .75)
            { 
                extraDialouge = "You need to do your homework, ferreto, i shall not let you pass";
            }
            else if(dialougeRandom >= .50)
            {
                extraDialouge = "TBD";
            }

            evilGurrito.damageRecieved(damage);

            return "You have dealt: " + damage + "\nEvil Gurrito remaining HP: [" + evilGurrito.getHealth() + "]\nGurrito: " + extraDialouge;
        }
        else if(evilNies.isAlive())
        {
            evilNies.damageRecieved(damage);

            return "You have dealt: " + damage + "\nEvil Nies remaining HP: [" + evilNies.getHealth() + "]";
        }
        else if(evilKlus.isAlive())
        {
            evilKlus.damageRecieved(damage);

            return "You have dealt: " + damage + "\nEvil Klus remaining HP: [" + evilKlus.getHealth() + "]";
        }
        return "Debug: Boss Attack Action";
    }
 

    /**
     * Heal a specified amount, default: 5 HP
    */
    public String heal()
    {
        return "Debug: Boss Heal Action";
    }

    //GETTERS
    public boolean isBossFightOver()
    {
        return !evilKlus.isAlive();
    }

    public String getCurrentName()
    {
        return null;
    }

    public void setGuiConnection(BossFightWindow e)
    {
        bossFightWindow = e;
    }

    public boolean isGurritoDead()
    {
        return evilGurrito.isAlive();
    }
    
    public boolean isNiesDead()
    {
        return evilNies.isAlive();
    }
    public boolean isKlusDead()
    {
        return evilKlus.isAlive();
    }
}