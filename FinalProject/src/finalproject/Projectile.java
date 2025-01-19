/*
 * This class is responsible for tyhe projectiles fired from the player and enemies in the game.
 * Every projectile knows its x and y position, damage, speed, direction, whether it hurts the
 * player or not, and whether it should be drawn as an arrow or magic ball. Projectiles will
 * also move across the screen based on their direction with the move() method.
 */
package finalproject;

/**
 *
 * @author geoff
 */
public class Projectile {
    // Position
    private int x;
    private int y;
    
    private int speed;
    private int direction;
    
    // Used as a timer for moving the projectiles
    int a = 0;
    long start = 0;
    long end = 0;
    
    private boolean hurtPlayer;    
    private int identifier;
    private int damage;
    
    public Projectile(int x, int y, int damage, int speed, int direction, boolean hurtPlayer, int identifier){
        switch(direction){
            case(0):
                this.x = x+22;
                this.y = y-8;
                break;
            case(1):
                this.x = x+50;
                this.y = y+16;
                break;
            case(2):
                this.x = x+22;
                this.y = y+44;
                break;
            case(3):
                this.x = x-4;
                this.y = y+16;
                break;
        }
        
        
        this.speed = speed;
        this.direction = direction;
        this.hurtPlayer = hurtPlayer;
        this.damage = damage;
        this.identifier = identifier;
    }
    
    // Getters and setters
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getDamage(){
        return this.damage;
    }
    public void setDamage(int d){
        this.damage = d;
    }
    public int getDirection(){
        return this.direction;
    }
    public void setDirection(int direction){
        this.direction = direction;
    }
    public boolean getHurtPlayer(){
        return this.hurtPlayer;
    }
    public int getIdentifier(){
        return this.identifier;
    }
    
    // Moves the projectile based on its direction and speed
    public void move(){
        switch(this.direction){
            case(0):
                if(a==0){
                    start = System.currentTimeMillis();            
                }
                a++;
                end = System.currentTimeMillis();
                if(end-start>=5){
                    this.y -= this.speed;                    
                    a=0;
                }
                break;
            case(1):
                if(a==0){
                    start = System.currentTimeMillis();            
                }
                a++;
                end = System.currentTimeMillis();
                if(end-start>=5){
                    this.x += this.speed;                    
                    a=0;
                }
                break;               
            case(2):
                if(a==0){
                    start = System.currentTimeMillis();            
                }
                a++;
                end = System.currentTimeMillis();
                if(end-start>=5){
                    this.y += this.speed;                    
                    a=0;
                }
                break;     
            case(3):
                if(a==0){
                    start = System.currentTimeMillis();            
                }
                a++;
                end = System.currentTimeMillis();
                if(end-start>=5){
                    this.x -= this.speed;                    
                    a=0;
                }
                break;               
        }
    }
    
}
