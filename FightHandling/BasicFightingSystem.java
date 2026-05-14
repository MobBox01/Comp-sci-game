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

            default -> enemy = new Enemy(20,3,30,.1,"TBD");
        }
        //audio.basicFight();
        window.dialouge("You have encountered [" + enemy.getName() + "] \n" + "Enemy HP: [" + enemy.getHealth() + "]");
    }

    /**
     * 1/2 chance for failure, reduce incoming attack by 50%
     */
    public void defend()
    {
        int random = (int)(Math.random()*3);

        if(random == 1)
        {

            int damage = enemy.Attack()-((int)(enemy.Attack()*.5));
            player.damageRecieved(damage);

            window.dialouge("PARRY FAILED!\n Damage taken: " + damage + "\nYour health: " + player.getHealth() + "\n" + enemy.getName() + " health:" + enemy.getHealth());
        }

        else
        {
            window.dialouge("PARRIED!\nYour health: " + player.getHealth() + "\n" + enemy.getName() + " health:" + enemy.getHealth());
        }
    }

    /**
     * Attack the enemy
     */
    public void attack()
    {
        enemy.damageRecieved(player.attackAction());
        player.damageRecieved(enemy.Attack() + ((int)(enemy.Attack()*.2)));
        window.dialouge("You have dealt [" + player.attackAction() + "] damage!\n" + enemy.getName() + " Health remaining: " + enemy.getHealth() + "\nYour health: " + player.getHealth());
    }

    /**
     * Heal a specified amount, default: 5 HP
     */
    public void heal()
    {
        player.playerHeal(10);
        player.damageRecieved((int)(enemy.Attack()*.5));
        window.dialouge("Your health: " + player.getHealth() + "\n" + enemy.getName() + " health: " + enemy.getHealth());
    }

    //GETTERS
    public boolean isEnemyAlive()
    {
        if(!enemy.isAlive() && !player.isFightingAdvanced() && canRewardBeGiven)
        {
            window.setNewText("Basic Area, Basic Enemies, Peaceful...");
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