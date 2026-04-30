package BossFight;

import Elements.AudioPlayer;
import Elements.Dialouge;
import Stats.Enemy;
import Stats.Player;

@SuppressWarnings("FieldMayBeFinal")
public class BossFightSystem 
{
    private Player player;
    private Enemy evilKlus = new Enemy(1000,0, 0, .5, "Evil Klus");
    private Enemy evilNies = new Enemy(1000, 10,1, 0., "Evil Nies");
    private Enemy evilGurrito = new Enemy(1,0, 10, 70., "Evil Gurrito");
    private Dialouge dialougeSystem;
    private AudioPlayer audio;

    public BossFightSystem(Player playerPass, Dialouge dialougeSystemPass,AudioPlayer audioPass) 
    {
        player = playerPass;
        dialougeSystem = dialougeSystemPass;
        audio = audioPass;
    }
    //You defeat evil nies, then evil gurrito, finally defeat mr klus
    /**
     * Initialize the new enemy
     * Set dialouge text for new enemy encounter
     */
    public void bossEncounter(int bossLevel)
    {
        //"Rat.. You haven't done your CSA Homework... for this you must pay"
    }

    /**
     * 1/2 chance for failure, reduce incoming attack by 40%
     */
    public String defend()
    {
        return "Debug: Boss Defend option";
    }

    /**
     * Attack the enemy
     * If less then or equal to 40% HP; 50% chance of being counter attacked with x2 damage
     */
    public String attack()
    {
        int damage = player.attackAction();
        if(evilGurrito.isAlive())
        {
            evilGurrito.damageRecieved(damage);

            return "You have dealt: " + damage + "\nEvil Gurrito remaining HP: [" + evilGurrito.getHealth() + "]";
        }

        return "Debug: Boss Attack Action";
    }
 

    /**
     * Heal a specified amount, default: 5 HP
    */
    public String heal()
    {
        return "Debug: Boss Heal Action";
    }

    //GETTERS
    public boolean isEnemyAlive()
    {
        return false;
    }

    public String getCurrentName()
    {
        return null;
    }
}