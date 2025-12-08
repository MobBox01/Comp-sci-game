package Stats;

public class Enemy 
{

    private int attack;
    private int health;
    private int manaShield;

    private String name;

    private double defense;

    private final int maxHealth;
    private final int maxShield;

    /**
     * @param Attack
     * @param Defense
     * @param Health
     * @param Name
     * @param Shield
     */
    public Enemy(int Attack, Double Defense, int Health, String Name, int Shield)
    {
        attack = Attack;
        defense = Defense;
        health = Health;
        name = Name;
        manaShield = Shield;

        maxShield = Shield;
        maxHealth = Health;
    }

    public String getName()
    {
        return name;
    }

    /**
     * @param damage
     * Damages the enemy, defense reduces a % of the damage 
     */
    public int damageRecieved(int damage)
    {
        health -= damage-((int)(damage*defense));
        return damage;
    }

    public boolean isAlive()
    {
        return health > 0;
    }

    /**
     * @param heal
     * Manashield also aids in healing, however manaShield will also reduce! 
     */
    public void heal(int heal)
    {
        if(manaShield <= 0)
        {
            health += heal;
            return; 
        }
        health += (int) (manaShield*defense) + heal;
        manaShield -= (int)(manaShield*defense);
    }

    public int Attack()
    {
        double increase = ((int)(Math.random() * 10))/100;

        return attack + (int)(attack*increase);
    }

    @Override
    public String toString()
    {
        return "\n[E-HEALTH]: " + health + "\n[E-DEFENSE]: " + defense + "\n[E-ATTACK]: " + attack + "\n[E-SHIELD]: " + manaShield + "\n\n[E-MAX-HEALTH]: " + maxHealth + "\n[E-MAX-SHIELD]: " + maxShield + "\n\n[E-ALIVE]: " + isAlive();

    }
}
