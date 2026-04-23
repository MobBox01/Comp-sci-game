package Stats;

@SuppressWarnings("FieldMayBeFinal")
public class Enemy 
{
    private int attack;
    private int health;
    private final int maxHealth;


    private String name;

    private double defense;


    /**
     * @param Attack
     * @param Defense
     * @param Health
     * @param Name
     * @param Shield
     */
    public Enemy(int Attack, Double Defense, int Health, String Name)
    {
        attack = Attack;
        defense = Defense;
        health = Health;
        name = Name;

        maxHealth = Health;
    }

    public String getName()
    {
        return name;
    }

    public int getHealth()
    {
        return health;
    }
    /**
     * @param attackDamageRecieved
     * Damages the enemy, defense reduces a % of the damage 
     */
    public int damageRecieved(int attackDamageRecieved)
    {
        health -= attackDamageRecieved-((int)(attackDamageRecieved*defense));
        return attackDamageRecieved;
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

    }

    public int Attack()
    {
        double increase = ((int)(Math.random() * 10))/100;

        return attack + (int)(attack*increase);
    }

    @Override
    public String toString()
    {
        return "\n[E-HEALTH]: " + health + "\n[E-DEFENSE]: " + defense + "\n[E-ATTACK]: " + attack + "\n[E-MAX-HEALTH]: " + maxHealth + "\n\n[E-ALIVE]: " + isAlive();

    }
}
