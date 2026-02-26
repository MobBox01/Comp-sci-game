package FightHandling;

import Elements.Dialouge;
import Elements.AudioPlayer;

import Stats.Enemy;
import Stats.Player;

public class AdvancedFightingSystem 
{
    private Player player;
    private Enemy enemy; 
    private Dialouge dialougeSystem;
    private AudioPlayer audio;

    double charge = 0;

    public AdvancedFightingSystem(Player playerPass, Dialouge dialougeSystemPass,AudioPlayer audioPass) 
    {
        player = playerPass;
        dialougeSystem = dialougeSystemPass;
        enemy = new Enemy(0, 1.0, 0, "HOLDER ENEMY");
        audio = audioPass;
    }

    /**
     * Initialize the new enemy
     * Set dialouge text for new enemy encounter
     */
    public void enemyEncounter()
    {
        switch((int)(Math.random()*5))
        {
            case 1 -> enemy = new Enemy(1, .4, 100, "ADVANCED TEST");
            case 2 -> enemy = new Enemy(2, .3, 2, "Atomize");
            case 3 -> enemy = new Enemy(3, .1, 50, "ADVAN Sniper");
            case 4 -> enemy = new Enemy(4,.3,40,"E");

            default -> enemy = new Enemy(1, .1, 5, "ThreeDemons");
        }
        audio.advancedFight();
        player.fightingAdvanced();
        dialougeSystem.setNewText("You have encountered [" + enemy.getName() + "] \n" + "Enemy HP: [" + enemy.getHealth() + "]");
    }

    /**
     * 1/2 chance for failure, reduce incoming attack by 50%
     */
    public void defend()
    {
        int random = (int)(Math.random()*5);

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
            player.damageRecieved(damage*3);
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
     */
    public void attack()
    {
        enemy.damageRecieved(player.attackAction());
        player.damageRecieved(enemy.Attack() + ((int)(enemy.Attack()*.5)));
        dialougeSystem.setNewText("You have dealt [" + player.attackAction() + "] damage!\n" + enemy.getName() + " Health remaining: " + enemy.getHealth() + "\nYour health: " + player.getHealth());
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
        else if(rng < .3 && rng > .2)
        {
            int attack = enemy.Attack()*2;
            player.damageRecieved(attack);
            enemy.heal(10);
            dialougeSystem.setNewText(enemy.getName() + " has broke out the match constraints! You were distracted healing...\n" + enemy.getName() + " has healed [10] HP!\n" + enemy.getName() + " has dealt: [" + attack + "] damage!\nHealth remaining: [" + player.getHealth() + "]");
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