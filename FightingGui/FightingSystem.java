package FightingGui;

import Stats.Enemy;
import Stats.Player;

public class FightingSystem 
{
    private Player player;
    private Enemy enemy; 
    private Dialouge dialougeSystem;

    public FightingSystem(Player playerPass, Dialouge dialougeSystemPass) 
    {
        player = playerPass;
        dialougeSystem = dialougeSystemPass;
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
        enemy = new Enemy(3, .1, 50, "Enemy Sniper", 0);
        dialougeSystem.setNewText("You have encountered [" + enemy.getName() + "] \n" + "Enemy HP: [" + enemy.getHealth() + "]");
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

            dialougeSystem.setNewText("PARRY FAILED!\n Damage taken: " + damage + "\nYour health: " + player.getHealth());
        }

        else
        {
            dialougeSystem.setNewText("PARRIED!\n" + "Your health: " + player.getHealth());
        }
    }

    /**
     * Attack the enemy
     */
    public void attack()
    {
        enemy.damageRecieved(player.attackAction());
        player.damageRecieved(enemy.Attack() + ((int)(enemy.Attack()*.2)));
        dialougeSystem.setNewText("You have dealt [" + player.attackAction() + "] damage!\nEnemy Health remaining: " + enemy.getHealth() + "\nYour health: " + player.getHealth());
    }



    /**
     * Heal a specified amount, default: 5 HP
     * TODO: Health should be a random % increase honestly
     */
    public void heal()
    {
        player.amountHealed(10);
        player.damageRecieved((int)(enemy.Attack()*.5));
        dialougeSystem.setNewText("Your health: " + player.getHealth());
    }

    //GETTERS
    public boolean isEnemyAlive()
    {
        if(enemy.isAlive() == false)
        {
            dialougeSystem.setNewText("Peaceful...");
        }
        return enemy.isAlive();
    }

    public String getCurrentName()
    {
        return enemy.getName();
    }


}