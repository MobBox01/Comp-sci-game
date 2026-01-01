package FightingGui;

import Saving.ProgressSaving;
import Stats.Enemy;
import Stats.Player;
import java.io.IOException;

public class FightingSystem 
{
    private Player player;
    private Enemy enemy; 
    private Dialouge message = new Dialouge();

    public FightingSystem() throws IOException
    {
        ProgressSaving saving = new ProgressSaving();
        player = new Player(saving.obtainSavePoint());
        enemy = new Enemy(0, 1.0, 0, "HOLDER ENEMY", 0);
    }

    /**
     * Initialize the new enemy
     * Set dialouge text for new enemy encounter
     */
    public void enemyEncounter()
    {
        /*switch((int)(Math.random()*5))
        {
            case 2 -> enemy = new Enemy(0, null, 0, null, 0);
            case 3 -> enemy = new Enemy(0, null, 0, null, 0);
        }
        */
        enemy = new Enemy(10, .2, 100, "Testing", 0);
        message.setNewText("You have encountered [" + enemy.getName() + "] \n" + "Enemy HP: [" + enemy.getHealth() + "]");
    }

    /**
     * 1/3 chance for failure, reduce incoming attack by 50%
     */
    public void defend()
    {
        int random = (int)(Math.random()*4);

        if(random == 1)
        {

            int damage = enemy.Attack()-((int)(enemy.Attack()*.5));
            player.damageRecieved(damage);

            message.setNewText("PARRY FAILED!\n Damage taken: " + damage + "\nYour health: " + player.getHealth());
        }

        else
        {
            message.setNewText("PARRIED!\n" + "Your health: " + player.getHealth());
        }
    }

    /**
     * Attack the enemy
     */
    public void attack()
    {
        enemy.damageRecieved(player.attackAction());
        player.damageRecieved(enemy.Attack() + ((int)(enemy.Attack()*.2)));
        message.setNewText("You have dealt [" + player.attackAction() + "] damage!\nEnemy Health remaining: " + enemy.getHealth() + "\nYour health: " + player.getHealth());

    }



    /**
     * Heal a specified amount, default: 5 HP
     * TODO: Make RNG Change for more health
     */
    public void heal()
    {
        player.amountHealed(10);
        player.damageRecieved((int)(enemy.Attack()*.5));
        message.setNewText("Your health: " + player.getHealth());
    }

    /**
     * @return the current % of players hp
     */
    public int getPlayerHealthPercentage()
    {
        int hp = player.healthPercentage();

        if (hp >= 100) return 100;
        if (hp >= 75)  return 75;
        if (hp >= 50)  return 50;
        if (hp > 0)  return 25;
        message.setNewText("You have DIED! \nGame over...");
        return 0;
    }

    //GETTERS
    public boolean isEnemyAlive()
    {
        if(enemy.isAlive() == false)
        {
            message.setNewText("Peaceful...");
        }
        return enemy.isAlive();
    }

    public boolean isPlayerAlive()
    {
        return player.isAlive();
    }

    public String getCurrentName()
    {
        return enemy.getName();
    }


}