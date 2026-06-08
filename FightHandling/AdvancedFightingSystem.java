package FightHandling;

import Elements.MainWindow;
import Stats.Enemy;
import Stats.Player;

@SuppressWarnings("FieldMayBeFinal")
public class AdvancedFightingSystem 
{
    private Player player;
    private Enemy enemy; 
    private MainWindow mainWindow;
    private boolean canRewardBeGiven = true;


    public AdvancedFightingSystem(Player playerPass,MainWindow mainWindowPass) 
    {
        player = playerPass;
        mainWindow = mainWindowPass;
        enemy = new Enemy(0,0, 1, .1, "HOLDER ENEMY");
    }

    /**
     * Initialize the new enemy
     * Set dialouge text for new enemy encounter
     */
    public void enemyEncounter()
    {
        canRewardBeGiven = true;
        switch((int)(Math.random()*5))
        {
            case 1 -> enemy = new Enemy(80, 30, 200, .05, "White Space");
            case 2 -> enemy = new Enemy(120, 35, 300, .10, "Atomize");
            case 3 -> enemy = new Enemy(140, 40, 400, .15, "The Guilty Cross");
            case 4 -> enemy = new Enemy(160, 45, 350, .20, "Masked Emotions");
            default -> enemy = new Enemy(200, 50, 500, .25, "Cubed");
        }
        player.fightingAdvanced();
        mainWindow.dialouge("You have encountered [" + enemy.getName() + "] \n" + enemy.getName() + " health remaining: [" + enemy.getHealth() + "]");
    }

    /**
     * 1/2 chance for failure, reduce incoming attack by 50%
     */
    public void defend()
    {
        int defendRNG = (int)(Math.random()*5);

        switch (defendRNG) {
            case 3 ->
                {
                    int damage = enemy.attack()-((int)(enemy.attack()*.5));
                    player.damageRecieved(damage);
                    player.addCharge((int)((Math.random()*5)+5));
                    mainWindow.dialouge("PARRY ![FAILED]!\nDamage taken: [" + damage*2 + "]\nHealth remaining: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]\n"+ enemy.getName() + " health remaining: [" + enemy.getHealth() + "]");
                }
            case 2 ->
                {
                    int damage = enemy.attack()-((int)(enemy.attack()*.5));
                    player.damageRecieved(damage*3);
                    player.substractCharge((int)(Math.random()*5));
                    mainWindow.dialouge("PARRY [FAILED] ITS A ~~CRITICAL~~\nDamage taken: [" + (damage*3) + "]\nHealth remaining: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]\n" + enemy.getName() + " health remaining: [" + enemy.getHealth() + "]");
                }
            default ->
            {
                player.addCharge((int)((Math.random()*10)+10));
                mainWindow.dialouge("~~PARRIED~~\nHealth remaining: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]\n" + enemy.getName() + " health remaining: [" + enemy.getHealth() + "]");
            }
        }
    }

    /**
     * attack the enemy
     * If less then or equal to 40% HP; 50% chance of being counter attacked with x2 damage
     */
    public void attack()
    {
        int additional = (int) (player.attackAction()*(player.getCharge()/100.0));
        int slightCrit = (int) ((Math.random()*15)+3);
        if(player.healthPercentage() <= 40 && Math.random() < .5)
        {
            player.damageRecieved(enemy.attack()*2);
            mainWindow.dialouge("You have dealt [" + (player.attackAction()+additional+slightCrit) + "] damage!\n" + enemy.getName() + " health remaining: [" + enemy.getHealth() + "]\n" + enemy.getName() + " has hit you with a critical! Damage taken: [" +  (enemy.attack()*2) + "] Heal up!\nHealth remaining: [" + player.getHealth() + "]\nCritical Charge remaining: [" + player.substractCharge(2+slightCrit) + "]\n" + enemy.getName() + "health remaining: [" + enemy.getHealth() + "]");

        }
        else if(player.getHealth() == 1)
        {
            mainWindow.dialouge("You felt a god like presense behind you. With 1 health remaining the enemy infront of you has been cleansed.");
            enemy.damageRecieved(-666666);
            player.heal(666666);
            player.substractCharge(666666);
            player.gainedXp(666);
        }
        else
        {
            enemy.damageRecieved(player.attackAction()+additional+slightCrit);
            player.damageRecieved(enemy.attack() + ((int)(enemy.attack()*.5)));
            mainWindow.dialouge("You have dealt [" + (player.attackAction()+additional+slightCrit) + "] damage!\n" + enemy.getName() + " health remaining: [" + enemy.getHealth() + "]\n" + enemy.getName() + " has dealt [" + enemy.attack() + "] damage!\nHealth remaining: [" + player.getHealth() + "]\nCritical Charge remaining: [" + player.substractCharge(2+slightCrit) + "]");
        }
    }
 
    /**
     * Heal a specified amount, default: 5 HP
    */
    public void heal()
    {
        double rng = Math.random();
        if(rng >= .4 && rng < .8)
        {
            int heal = (int)((Math.random()*30)+40);
            player.heal(heal);
            player.substractCharge(5);
            mainWindow.dialouge(enemy.getName() + " couldn't heal in time! Lucky you! Some charge has been removed however...\nHealed [" + heal + "] HP!\nHealth remaining: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]\n" + enemy.getName() + " health remaining: [" + enemy.getHealth() + "]");
        }
        else if(rng < .4 && rng > .3)
        {
            int attack = (int)(enemy.attack()*1.2);
            player.damageRecieved(attack);
            enemy.heal(10);
            player.substractCharge((int)((Math.random()*5)+5));
            mainWindow.dialouge(enemy.getName() + " has broke out the match constraints! You were distracted...\n" + enemy.getName() + " has healed [10] HP!\n" + enemy.getName() + " health remaining: [" + enemy.getHealth() + "]\n" + enemy.getName() + " has dealt [" + attack + "] damage!\nHealth remaining: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]");
        }
        else if(rng > .8 && rng < .9 && player.getCharge() >= 50)
        {
            mainWindow.dialouge("You have been blessed, However.. Since you have above 50 charge.... Charge has been reset, you feel amazing and healed up to the max! Enemy has recieved 90 damage.");
            player.substractCharge(666);
            player.heal(666);
            enemy.damageRecieved(90);
        }
        else
        {
            int heal = (int)((Math.random()*20)+30);
            player.heal(heal);
            enemy.heal(10);
            mainWindow.dialouge("Healed [" + heal + "] HP!\n!!" + enemy.getName() + " healed in time!! \n" + enemy.getName() + " healed: [" + 10 + "]\nHealth remaining: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]\n" + enemy.getName() + "remaining health: [" + enemy.getHealth() + "]");
        }
    }

    //GETTERS
    public boolean isEnemyAlive()
    {
        if(!enemy.isAlive() && canRewardBeGiven && !mainWindow.isDialougeActive())
        {
            player.gainedXp(enemy.xpReward());
            canRewardBeGiven = false;
        }
        return enemy.isAlive();
    }

    public String getCurrentName()
    {
        return enemy.getName();
    }

    public void setMainWindow(MainWindow e)
    {
        mainWindow = e;
    }
}