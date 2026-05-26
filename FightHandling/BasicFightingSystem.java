package FightHandling;

import Elements.MainWindow;
import Stats.Enemy;
import Stats.Player;

@SuppressWarnings("FieldMayBeFinal")
public class BasicFightingSystem 
{
    private Player player;
    private Enemy enemy; 
    private MainWindow window;
    private boolean canRewardBeGiven = true;

    public BasicFightingSystem(Player playerPass,MainWindow windowPass) 
    {
        player = playerPass;
        window = windowPass;
        enemy = new Enemy(0,0, 1, .1, "HOLDER ENEMY");
    }

    /**
     * Initialize the new enemy
     * Set dialouge text for new enemy encounter
     */
    public void enemyEncounter()
    {
        switch((int)(Math.random()*5))
        {
            case 1 -> enemy = new Enemy(30,5,400,.1,"Vampire Tree");
            case 2 -> enemy = new Enemy(20,15,200,.4,"Deal Talk");
            default -> enemy = new Enemy(10,10,300,.8,"Blorbus");
        }
        canRewardBeGiven = true;
        window.dialouge("You have encountered [" + enemy.getName() + "] \n" + "Enemy HP: [" + enemy.getHealth() + "]");
    }


    public void defend()
    {
        window.dialouge("Defending, its pointless... you attack first... Turn nullified. \nHeal or Attack");
    }

    /**
     * Attack the enemy
     */
    public void attack()
    {
        int damageTaken = enemy.Attack() + ((int)(enemy.Attack()*.2));
        enemy.damageRecieved(player.attackAction());
        player.damageRecieved(damageTaken);
        window.dialouge("You have dealt [" + player.attackAction() + "] damage!\n" + enemy.getName() + " Health remaining: [" + enemy.getHealth() + "]\n" + enemy.getName() + " has dealt [" + damageTaken + "] damage!\nHealth remaining: [" + player.getHealth() + "]");
    }

    /**
     * Heal a specified amount, default: 5 HP
     */
    public void heal()
    {
        player.heal(10);
        window.dialouge("You have healed 10 hp!");
    }

    //GETTERS
    public boolean isEnemyAlive()
    {
        if(!enemy.isAlive() && !player.isFightingAdvanced() && canRewardBeGiven)
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

    public void setWindow(MainWindow e)
    {
        window = e;
    }
}