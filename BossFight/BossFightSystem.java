package BossFight;

import Stats.Enemy;
import Stats.Player;

@SuppressWarnings("FieldMayBeFinal")
public class BossFightSystem 
{
    //Stats
    private Player player;
    //20, 200,400
    private Enemy evilKlus = new Enemy(10,40, 666, .9, "Evil Klus");
    private Enemy evilNies = new Enemy(100, 60,666, .5, "Evil Nies");
    private Enemy evilGurrito = new Enemy(500,25, 666, 0.0, "Evil Gurrito");
    private Enemy currentEnemy = evilGurrito;
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
        int defenceRNG = (int)(Math.random()*6);
        int damage = 0;
        if(defenceRNG <= 1) // 2/5 chance
        {
            return "You have decided to defend... Charge has increased!\nCurrent Critical Charge: [" + player.getCharge() + "]\nHealth remaining: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.addCharge(((int)(Math.random()*11))+10) + "]\n" + currentEnemy.getName() + " health remaining: [" + currentEnemy.getHealth() + "]";
        }
        else if(defenceRNG == 2)
        {
            return "You have decided to defend... " + currentEnemy.getName() + " has attacked and you slipped up...\n" + currentEnemy.getName() + " has dealt " + damage + "\nHealth remaining: [" + player.getHealth() + "]\n" + currentEnemy.getName() + " health remaining: [" + currentEnemy.getHealth() + "]";
        }
        else if(defenceRNG == 3)
        {
            return "You have decided to defend... " + currentEnemy.getName() + " has attacked you and you both headbutted... [50] damage taken to both, ouchie.\nHealth remaining: [" + player.getHealth() + "]\n" + currentEnemy.getName() + " health remaining: ["  + currentEnemy.getHealth() + "]";
        }
        else
        {
            return "you defended to long, charge hasn't been gained.";
        }
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
            if(!evilGurrito.isAlive())
            {
                currentEnemy = evilNies;
                return "Ah you've defeated me... Evil Nies remaining HP: [" + evilNies.getHealth() + "]";
            }
            return "You have dealt: " + damage + "\nEvil Gurrito remaining HP: [" + evilGurrito.getHealth() + "]\nGurrito: " + extraDialouge[randomDialouge];
        }

        else if(evilNies.isAlive())
        {
            int randomDialouge = (int)((Math.random()*3)+2);

            evilNies.damageRecieved(damage);
            if(!evilNies.isAlive())
            {
                currentEnemy = evilKlus;
                return "Ah you've defeated me... Evil Klus remaining HP: [" + evilKlus.getHealth() + "]";
            }
            return "You have dealt: " + damage + "\nEvil Nies remaining HP: [" + evilKlus.getHealth() + "]\nKlus: " + extraDialouge[randomDialouge];
        }
        else if(evilKlus.isAlive())
        {
            evilKlus.damageRecieved(damage);
            int randomDialouge = (int)((Math.random()*4)+6);

            return "You have dealt: " + damage + "\nEvil Klus remaining HP: [" + evilKlus.getHealth() + "]\nKlus: " + extraDialouge[randomDialouge];
        }
        else
        {
            return "Debug: Boss Attack Action";
        }
    }
 

    /**
     * Heal a specified amount, default: 5 HP
    */
    public String heal()
    {
        int healRng = ((int)(Math.random()*5));

        if(healRng <= 1)
        {
            return "You have healed! ";
        }
        else
        {
            return "l";
        }
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
