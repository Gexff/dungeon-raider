/*
 * This class extends archer, but overrides the getType() method so a different
 * character image, shooting sound, and projctile image can be loaded.
 */
package finalproject;

/**
 *
 * @author geoff
 */
public class Mage extends Archer implements Enemy {
                
    public Mage(int posX, int posY, int maxHealth, int damage, int fireRate, int projectileSpeed, boolean move, int direction, int xToY, int identifier) {
        super(posX, posY, maxHealth, damage, fireRate, projectileSpeed, move, direction, xToY, identifier);
        
    }
    
    @Override
    public int getType(){
        return 1;
    }                    
}
