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


    public AdvancedFightingSystem(Player playerPass, Dialouge dialougeSystemPass,AudioPlayer audioPass) 
    {
        player = playerPass;
        dialougeSystem = dialougeSystemPass;
        enemy = new Enemy(0,0, 1, .1, "HOLDER ENEMY");
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
            case 2 -> enemy = new Enemy(100,7,60,.2,"Atomize");
            default -> enemy = new Enemy(200, 10,69,.1,"ThreeDemons");
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
            player.addCharge(2);
        }
        else if(random == 2)
        {
            int damage = enemy.Attack()-((int)(enemy.Attack()*.5));
            player.damageRecieved(damage*3);
            player.substractCharge(4);
            dialougeSystem.setNewText("PARRY [FAILED] ITS A ~~CRITICAL~~\nDamage taken: [" + (damage*3) + "]\nYour health: [" + player.getHealth() + "]\nCurrent Critical Charge: " + player.getCharge() + "\n Enemy health remaining: [" + enemy.getHealth() + "]");
        }
        else
        {
            player.addCharge(5);
            dialougeSystem.setNewText("~~PARRIED~~\n" + "Your health: [" + player.getHealth() + "]\nCurrent Critical Charge: [" + player.getCharge() + "]\nEnemy health remaining: [" + enemy.getHealth() + "]");
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
            dialougeSystem.setNewText("You have dealt [" + (player.attackAction()+additional+slightCrit) + "] damage!\n" + enemy.getName() + " Enemy health remaining: " + enemy.getHealth() + "\n" + enemy.getName() + " has hit you with a critical! Damage taken: " +  (enemy.Attack()*2) + "Heal up!\nYour health: " + player.getHealth() + "\nCritical Charge remaining: [" + player.substractCharge(2+slightCrit) + "]");

        }
        else if(player.getHealth() == 1)
        {
            dialougeSystem.setNewText("You felt a god like presense behind you. With 1 health remaining the enemy infront of you has been killed.");
            enemy.damageRecieved(-9999999);
            player.playerHeal(9999);
            player.substractCharge(99999);
            player.gainedXp(400);
        }
        else
        {
            enemy.damageRecieved(player.attackAction()+additional+slightCrit);
            player.damageRecieved(enemy.Attack() + ((int)(enemy.Attack()*.5)));
            dialougeSystem.setNewText("You have dealt [" + (player.attackAction()+additional+slightCrit) + "] damage!\n" + enemy.getName() + " Enemy health remaining: " + enemy.getHealth() + "\n" + enemy.getName() + " has attacked! Damage taken: " +  (enemy.Attack()) + "\nYour health: " + player.getHealth() + "\nCritical Charge remaining: [" + player.substractCharge(2+slightCrit) + "]");
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
        else if(rng < .3 && rng > .2)
        {
            int attack = enemy.Attack()*2;
            player.damageRecieved(attack);
            enemy.heal(10);
            dialougeSystem.setNewText(enemy.getName() + " has broke out the match constraints! You were distracted healing...\n" + enemy.getName() + " has healed [10] HP!\n" + enemy.getName() + " has dealt: [" + attack + "] damage!\nHealth remaining: [" + player.getHealth() + "]");
        }
        else if(rng > .8 && rng < .9 && player.getCharge() >= 50)
        {
            dialougeSystem.setNewText("You have been blessed.However.. Since you have above 50 charge.... Charge has been reset, you feel amazing and healed up to the max!");
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
            player.gainedXp(enemy.xpReward());
        }
        return enemy.isAlive();
    }

    public String getCurrentName()
    {
        return enemy.getName();
    }
}