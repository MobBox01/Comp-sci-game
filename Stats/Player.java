package Stats;

public class Player
{
    private int[] attack = {3,5,7,9,11};
    private int[] health = {55,65,75,85,100};
    private int[] maxHealth = {55,65,75,85,100};

    private double[] defense = {.1,.2,.3,.4,.5};

    private int level = 0;
    private int xp = 0;

    private boolean fightingAdvanced = false;

    public Player(int[] info)
    {
        level = info[0];
        xp = info[1];
    }

    /**
     * Resets stats to restore old values to their maximum statee
     */
    public void savePointReset()
    {
        health[level] = maxHealth[level];
    }

    /**
     * @param damage
     * Defense is a % and reduces how much attack damages the players
     */
    public int damageRecieved(int damage)
    {
        health[level] -= damage-((int)(damage*defense[level]));

        return damage;
    }

    /**
     * @param xp XP gained from monster
     * level up if required xp is reached and level is not the limit
     * [level - 1] should be used 
     */
    public int gainedXp(int xpGain)
    {
        xp += xpGain;
        while(xp >= 1000 && level != 4)
        {
            xp -= 1000;
            level += 1;
            savePointReset();
        }
        return xpGain;
    }

    /**
     * @return attack towards enemy
     */
    public int attackAction()
    {
        return attack[level];
    }

    public int getHealth()
    {
        return health[level];
    }
    /**
     * @param heal
     * Heals 100% of the healed amount
     * Avoids health overflow
     */
    public void amountHealed(int heal)
    {
        health[level] += heal;

        if(health[level] >= maxHealth[level])
        {
            health[level] = maxHealth[level];
        }
    }


    public int healthPercentage()
    {
        return (int)((health[level] * 100.0) / maxHealth[level]);
    }    


    /**
     * @return [TRUE] - if alive, [FALSE] if dead, [INBETWEEN] when limit x -> klus = DNE 
     */
    public boolean isAlive()
    {
        return health[level] > 0;
    }

    public int getLevel()
    {
        return level;
    }

    public int getXP()
    {
        return xp;
    }

    public boolean isFightingAdvanced()
    {
        return fightingAdvanced;
    }

    public void fightingAdvanced()
    {
        fightingAdvanced = true;
    }
    
    @Override
    public String toString()
    {
        return "\n[HEALTH]: " + health[level] + "\n[DEFENSE]: " + defense[level] + "\n[ATTACK]: " + attack[level] + "\n\n[MAX-HEALTH]: " + maxHealth[level] + "\n\n[ALIVE]: " + isAlive() + "\n[LEVEL]: " + level + "\n[XP]: " + xp;
    }
}














    /**
     * @param Mana
     * Reduces mana stored
     * [IF] Player goes past empty -> Take damage
     * [IF] mana < 0, set {mana = 0}. Negative mana cant exist 
     
    public void manaUsed(int Mana)
    {
        mana[level] -= Mana;

        if(mana[level] < 0)
        {
            health[level] += mana[level] - (int)(mana[level]*defense[level]);
            mana[level] = 0;
        }
    }
    */