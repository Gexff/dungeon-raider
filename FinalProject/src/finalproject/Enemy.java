/*
 * This interface is used for the three enemy types in the game, Archer, Melee and
 * Mage. Using this interface allows all of the enemies to be stored in the same array
 * which makes keeping track of all the enemies and calling their actions very easy.
 */
package finalproject;

import java.awt.Graphics;

/**
 *
 * @author geoff
 */
public interface Enemy {
    public void move(Player player);
    public Projectile[] attack(Projectile[] array, Player player, Graphics g);  
    public int getDirection();
    public int getX();
    public void setX(int x);
    public int getY();
    public void setY(int y);
    public int getMaxHealth();
    public int getHealth();
    public void setHealth(int h);
    public int getType();
    public void setType(int type);
    public int getIdentifier();
}
