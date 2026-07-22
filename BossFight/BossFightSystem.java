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
    private Enemy evilNies = new Enemy(50, 60,666, .5, "Evil Nies");
    private Enemy evilGurrito = new Enemy(100,25, 666, 0.0, "Evil Gurrito");
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

    public String defend()
    {
        int defenceRNG = (int)(Math.random()*6);
        int damage = ((int)(currentEnemy.attack()*1.2));

        if(defenceRNG <= 1) // 2/5 chance
        {
            return "You have decided to defend... Charge has increased!\nCurrent Critical Charge: [" + player.addCharge(((int)(Math.random()*11))+10) + "]\nHealth remaining: [" + player.getHealth() + "]\n" + currentEnemy.getName() + " health remaining: [" + currentEnemy.getHealth() + "]";
        }
        else if(defenceRNG == 2)
        {
            damage = (int)(currentEnemy.attack()*1.2);
            player.damageRecieved(damage);
            return "You have decided to defend... " + currentEnemy.getName() + " has attacked and you slipped up...\n" + currentEnemy.getName() + " has dealt [" + damage + " ]damage!\nHealth remaining: [" + player.getHealth() + "]\n" + currentEnemy.getName() + " health remaining: [" + currentEnemy.getHealth() + "]\nYou have gained [" + player.addCharge(((int)(Math.random()*20))+10) + "] critical charge!\nCurrent Critical Charge: [" + player.getCharge() + "]";
        }
        else if(defenceRNG == 3)
        {
            player.substractCharge(4);
            player.damageRecieved(currentEnemy.attack()+ ((int)(currentEnemy.attack()*(Math.random()/100))));
            currentEnemy.damageRecieved(player.attackAction()+ ((int)(player.attackAction()*(Math.random()/100))));
            return "You have decided to defend... " + currentEnemy.getName() + " has attacked you and... you both headbutted... lmao! [50] damage taken to both of you, womp womp.\nHealth remaining: [" + player.getHealth() + "]\n" + currentEnemy.getName() + " health remaining: ["  + currentEnemy.getHealth() + "]\nLost 5 charge.";
        }
        else
        {
            return "you defended to long, charge hasn't been gained.";
        }
    }

    /**
     * Attack the enemy; Enemy attacks back (constant)
     */
    public String attack()
    {
        int damage = player.attackAction() + ((int)(player.attackAction()*(player.getCharge()/100)));
        
        if(evilGurrito.isAlive())
        {
            int randomDialouge = (int)((Math.random()*3));
            return attackDialouge(damage, randomDialouge);
        }
        else if(evilNies.isAlive())
        {
            int randomDialouge = (int)((Math.random()*3)+2);
            return attackDialouge(damage, randomDialouge);

        }
        else if(evilKlus.isAlive())
        {
            int randomDialouge = (int)((Math.random()*4)+5);
            return attackDialouge(damage, randomDialouge);

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
        int damage = (int)(currentEnemy.attack() * 1.2);
        if(healRng >= 1) //Healed
        {
            player.heal(healRng); 
            return "You have healed!\nHealth remaining: " + player.getHealth() + "\n" + currentEnemy.getName() + " health remaining: " + currentEnemy.getHealth();
        }
        else if(healRng ==4) //Attacked
        {
            return currentEnemy.getName() + " has attacked! You were caught off guard and took [" + player.damageRecieved(damage) + "] damage!\nHealth remaining: [" + player.getHealth() + "\n"  + currentEnemy.getName() + " health remaining: [" + currentEnemy.getHealth() + "]\nCurrent Critical Charge: ["  + player.getCharge() + "]";
        }
        else //Healed and charged
        {
            return "You healed [" + player.heal(((int)(Math.random()*20))+10) + "] HP! You also gained [" + player.addCharge(((int)(Math.random()*20))+10) + "] critical charge!\nHealth remaining: [" + player.getHealth() + "\nCurrent Critical Charge: [" + player.getCharge() + "]\n" + currentEnemy.getName() + " health remaining: [" + currentEnemy.getHealth() + "]";
        }
    }

    //GETTERS
    public boolean isBossFightOver()
    {
        if(!evilKlus.isAlive())
        {
            player.heal(999999);
        }
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

    public String attackDialouge(int damage,int randomDialouge)
    {
        //Defeat Case
        if(evilGurrito == currentEnemy && !evilGurrito.isAlive())
        {
            currentEnemy = evilNies;
            return "Ah you have defeated your english teacher that you never had officially and only for a english recovery course... how could you.\nEvil Nies remaining health: [" + currentEnemy.getHealth() + "]";
        }
        else if(evilNies == currentEnemy && !evilNies.isAlive())
        {
            currentEnemy = evilKlus;
            return "Ah you have defeated your chemistry teacher that you never had officially, ever...........Evil Klus remaining health: [" + currentEnemy.getHealth() + "]";
        }
        else if(evilKlus == currentEnemy && !evilKlus.isAlive())
        {
            currentEnemy = null;
            return "Ah, you defeated me Ferreto............";
        }
        

        //Default case
        return "You have dealt: " + currentEnemy.damageRecieved(damage) + "\n" + currentEnemy.getName() + " remaining HP: [" + currentEnemy.getHealth() + "]\n" + currentEnemy.getName() + " has attacked! " + currentEnemy.getName() + " has dealt [" + player.damageRecieved(currentEnemy.attack()) + "]\nHealth remaining: [" + player.getHealth() + "]\n" + currentEnemy.getName() + ": "+ extraDialouge[randomDialouge];
    }
}
