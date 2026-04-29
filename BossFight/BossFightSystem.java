package BossFight;

import Elements.AudioPlayer;
import Elements.Dialouge;
import Stats.Enemy;
import Stats.Player;

@SuppressWarnings("FieldMayBeFinal")
public class BossFightSystem 
{
    @SuppressWarnings("unused")
    private Player player;
    @SuppressWarnings("unused")
    private Enemy evilKlus = new Enemy(0, 1.0, 0, "Evil Klus");
    @SuppressWarnings("unused")
    private Enemy evilNies = new Enemy(0, 1.0, 0, "Evil Nies");
    @SuppressWarnings("unused")
    private Enemy happilyEvilGurrito = new Enemy(0, 1.0, 0, "Happily Evil Gurrito");
    @SuppressWarnings("unused")
    private Dialouge dialougeSystem;
    @SuppressWarnings("unused")
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
    public void defend()
    {

    }

    /**
     * Attack the enemy
     * If less then or equal to 40% HP; 50% chance of being counter attacked with x2 damage
     */
    public void attack()
    {
        
    }
 

    /**
     * Heal a specified amount, default: 5 HP
    */
    public void heal()
    {

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