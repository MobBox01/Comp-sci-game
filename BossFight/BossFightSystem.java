package BossFight;

import Stats.Enemy;
import Stats.Player;

@SuppressWarnings("FieldMayBeFinal")
public class BossFightSystem 
{
    //Stats
    private Player player;
    //20, 200,400
    private Enemy evilKlus = new Enemy(100,40, 666, .9, "Evil Klus");
    private Enemy evilNies = new Enemy(200, 60,666, .5, "Evil Nies");
    private Enemy evilGurrito = new Enemy(1,25, 666, 0.2, "Evil Gurrito");
    private String[] extraDialouge = 
    {
        "You need to do your homework, ferreto, i shall not let you pass",
        "Ferreto, your english project is DUE TODAY!!!!!",
        "Ferreto, your sins will never be forgiven",
        "Dihydrogen Monoxide scares me....",
        "What is Dihydrogen Monoxide?",
        "HA! Dihydrogen Monoxide attack!",
        "Be ready for your computer science exam...",
        "Mr.Klus prepares you for your computer science exam *ASMR* 24/7 study for your AP exam!",
        "I want to develop a band..."
    };

    //Other
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
    
        if(evilGurrito.isAlive())
        {
            int randomDialouge = (int)((Math.random()*3));
            evilGurrito.damageRecieved(damage);
            return "You have dealt: " + damage + "\nEvil Gurrito remaining HP: [" + evilGurrito.getHealth() + "]\nGurrito: " + extraDialouge[randomDialouge];
        }
        else if(evilNies.isAlive())
        {
            int randomDialouge = (int)((Math.random()*4)+3);

            evilNies.damageRecieved(damage);

            return "You have dealt: " + damage + "\nEvil Nies remaining HP: [" + evilNies.getHealth() + "]\nNies: " + extraDialouge[randomDialouge];
        }
        else if(evilKlus.isAlive())
        {
            evilKlus.damageRecieved(damage);
            int randomDialouge = (int)((Math.random()*4)+6);
            return "You have dealt: " + damage + "\nEvil Klus remaining HP: [" + evilKlus.getHealth() + "]\nKlus: " + extraDialouge[randomDialouge];
        }
        else
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
        return !evilGurrito.isAlive();
    }
    
    public boolean isNiesDead()
    {
        return !evilNies.isAlive();
    }
    public boolean isKlusDead()
    {
        return !evilKlus.isAlive();
    }
}
