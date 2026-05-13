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
            case 2 -> enemy = new Enemy(60,5,60,.2,"Atomize");
            default -> enemy = new Enemy(100, 6,69,.3,"Three Demons");
        }
        //audio.advancedFight();
        player.fightingAdvanced();
        mainWindow.dialouge("You have encountered [" + enemy.getName() + "] \n" + "Enemy HP: [" + enemy.getHealth() + "]");
    }

    /**
     * 1/2 chance for failure, reduce incoming attack by 50%
     */
    public void defend()
    {
        int random = (int)(Math.random()*5);

        switch (random) {
            case 3 ->
                {
                    int damage = enemy.Attack()-((int)(enemy.Attack()*.5));
                    player.damageRecieved(damage);
                    mainWindow.dialouge("PARRY ![FAILED]!\nDamage taken: [" + damage*2 + "]\nHealth remaining: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]\n"+ enemy.getName() + " health remaining: [" + enemy.getHealth() + "]");
                    player.addCharge(2);
                }
            case 2 ->
                {
                    int damage = enemy.Attack()-((int)(enemy.Attack()*.5));
                    player.damageRecieved(damage*3);
                    player.substractCharge(4);
                    mainWindow.dialouge("PARRY [FAILED] ITS A ~~CRITICAL~~\nDamage taken: [" + (damage*3) + "]\nHealth remaining: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]\n" + enemy.getName() + " health remaining: [" + enemy.getHealth() + "]");
                }
            default ->
            {
                player.addCharge(5);
                mainWindow.dialouge("~~PARRIED~~\nYour health: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]\n" + enemy.getName() + " health remaining: [" + enemy.getHealth() + "]");
            }
        }
    }

    /**
     * Attack the enemy
     * If less then or equal to 40% HP; 50% chance of being counter attacked with x2 damage
     */
    public void attack()
    {
        int additional = (int) (player.attackAction()*(player.getCharge()/100.0));
        int slightCrit = (int) (Math.random()*10);
        if(player.healthPercentage() <= 40 && Math.random() < .5)
        {
            player.damageRecieved(enemy.Attack()*2);
            mainWindow.dialouge("You have dealt [" + (player.attackAction()+additional+slightCrit) + "] damage!\n" + enemy.getName() + " health remaining: [" + enemy.getHealth() + "]\n" + enemy.getName() + " has hit you with a critical! Damage taken: [" +  (enemy.Attack()*2) + "] Heal up!\nHealth remaining: [" + player.getHealth() + "]\nCritical Charge remaining: [" + player.substractCharge(2+slightCrit) + "]\n" + enemy.getName() + "health remaining: [" + enemy.getHealth() + "]");

        }
        else if(player.getHealth() == 1)
        {
            mainWindow.dialouge("You felt a god like presense behind you. With 1 health remaining the enemy infront of you has been killed.");
            enemy.damageRecieved(-666666);
            player.playerHeal(666666);
            player.substractCharge(666666);
            player.gainedXp(666);
        }
        else
        {
            enemy.damageRecieved(player.attackAction()+additional+slightCrit);
            player.damageRecieved(enemy.Attack() + ((int)(enemy.Attack()*.5)));
            mainWindow.dialouge("You have dealt [" + (player.attackAction()+additional+slightCrit) + "] damage!\n" + enemy.getName() + " Enemy health remaining: [" + enemy.getHealth() + "]\n" + enemy.getName() + " has dealt [" + enemy.Attack() + "] damage!\nHealth remaining: " + player.getHealth() + "\nCritical Charge remaining: [" + player.substractCharge(2+slightCrit) + "]");
        }
    }
 

    /**
     * Heal a specified amount, default: 5 HP
    */
    public void heal()
    {
        double rng = Math.random();
        if(rng >= .3)
        {
            player.playerHeal(10);
            mainWindow.dialouge(enemy.getName() + " couldn't heal in time! Lucky you!\nHealed [" + 10 + "] HP!\nHealth remaining: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]\n" + enemy.getName() + " health remaining: [" + enemy.getHealth() + "]");
            player.substractCharge(4);
        }
        else if(rng < .3 && rng > .2)
        {
            int attack = enemy.Attack()*2;
            player.damageRecieved(attack);
            enemy.heal(10);
            mainWindow.dialouge(enemy.getName() + " has broke out the match constraints! You were distracted...\n" + enemy.getName() + " has healed [10] HP!\n" + enemy.getName() + " health remaining: [" + enemy.getHealth() + "]\n" + enemy.getName() + " has dealt [" + attack + "] damage!\nHealth remaining: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]");
            player.substractCharge(10);
        }
        else if(rng > .8 && rng < .9 && player.getCharge() >= 50)
        {
            mainWindow.dialouge("You have been blessed.However.. Since you have above 50 charge.... Charge has been reset, you feel amazing and healed up to the max! Enemy has recieved 10 damage.");
            player.substractCharge(666);
            enemy.damageRecieved(10);
        }
        else
        {
            player.playerHeal(20);
            enemy.heal(10);
            mainWindow.dialouge("Healed [" + 20 + "] HP!\n!!" + enemy.getName() + " healed in time!! \n" + enemy.getName() + " healed: [" + 10 + "]\n Your health: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]\n" + enemy.getName() + "remaining health: [" + enemy.getHealth() + "]");
        }
    }

    //GETTERS
    public boolean isEnemyAlive()
    {
        if(!enemy.isAlive() && canRewardBeGiven && !mainWindow.isDialougeActive())
        {
            mainWindow.setNewText("Advanced Area, Advanced Enemies, Tension... Cities burn.");
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