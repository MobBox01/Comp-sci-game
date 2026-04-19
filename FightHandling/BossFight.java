package FightHandling;

import Elements.Dialouge;
import Elements.AudioPlayer;

import Stats.Enemy;
import Stats.Player;

public class BossFight 
{
    private Player player;
    private Enemy enemy; 
    private Dialouge dialougeSystem;
    private AudioPlayer audio;

    double charge = 0;

    public BossFight(Player playerPass, Dialouge dialougeSystemPass,AudioPlayer audioPass) 
    {
        player = playerPass;
        dialougeSystem = dialougeSystemPass;
        audio = audioPass;
    }

    /**
     * Initialize the new enemy
     * Set dialouge text for new enemy encounter
     */
    public void bossEncounter(int bossLevel)
    {
        switch(bossLevel)
        {
            case 1 -> enemy = new Enemy(10, .5, 100, "Happily Evil Gurrito");
            case 2 -> enemy = new Enemy(20, .3, 200, "Evil BodyGuard Nies");
            case 3 -> enemy = new Enemy(30, .1, 500, "Evil Boss Klus");
        }
        dialougeSystem.setNewText("You have encountered [" + enemy.getName() + "] \n" + "Enemy HP: [" + enemy.getHealth() + "]");
    }

    /**
     * 1/2 chance for failure, reduce incoming attack by 40%
     */
    public void defend()
    {
        int random = (int)(Math.random()*4);

        if(random == 3)
        {

            int damage = enemy.Attack()-((int)(enemy.Attack()*.5));
            player.damageRecieved(damage);

            dialougeSystem.setNewText("PARRY [FAILED]\nDamage taken: [" + damage*2 + "]\nYour health: [" + player.getHealth() + "]");
            charge -= .2;
            if(charge < 0)
            {
                charge = 0;
            }
        }
        else if(random == 2)
        {
            int damage = enemy.Attack()-((int)(enemy.Attack()*.5));
            player.damageRecieved(damage*2);
            dialougeSystem.setNewText("PARRY [FAILED] ITS A ~~CRITICAL~~\nDamage taken: [" + (damage*3) + "]\nYour health: [" + player.getHealth() + "]");
            charge -= .3;
            if(charge < 0)
            {
                charge = 0;
            }
        }
        else
        {
            dialougeSystem.setNewText("~~PARRIED~~\n" + "Your health: [" + player.getHealth() + "]");
            charge += .2;
            if(charge > 1)
            {
                charge = 1;
            }
        }
    }

    /**
     * Attack the enemy
     * If less then or equal to 40% HP; 50% chance of being counter attacked with x2 damage
     */
    public void attack()
    {
        if(player.healthPercentage() <= 40 && Math.random() < .5)
        {
            player.damageRecieved(enemy.Attack()*2);
            dialougeSystem.setNewText("You have dealt [" + player.attackAction() + "] damage!\n" + enemy.getName() + " Health remaining: " + enemy.getHealth() + "\nYour health: " + player.getHealth());
        }
        else
        {
            enemy.damageRecieved(player.attackAction());
            player.damageRecieved(enemy.Attack() + ((int)(enemy.Attack()*.5)));
            dialougeSystem.setNewText("You have dealt [" + player.attackAction() + "] damage!\n" + enemy.getName() + " Health remaining: " + enemy.getHealth() + "\nYour health: " + player.getHealth());
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
            dialougeSystem.setNewText("Healed: [" + 10 + "]\n" + enemy.getName() + " couldn't heal in time! Lucky you!");
        }
        else
        {
            player.playerHeal(10);
            enemy.heal(20);
            dialougeSystem.setNewText("Healed: [" + 10 + "]\n!!" + enemy.getName() + " healed in time!! \n" + enemy.getName() + " healed: [" + 20 + "]\n" + enemy.getName() + "'s health: [" + enemy.getHealth() + "]\nYour Healh: [" + player.getHealth() + "]");
        }
    }

    //GETTERS
    public boolean isEnemyAlive()
    {
        if(!enemy.isAlive())
        {
            dialougeSystem.setNewText("Ambush at any moment...");
        }
        return enemy.isAlive();
    }

    public String getCurrentName()
    {
        return enemy.getName();
    }
}