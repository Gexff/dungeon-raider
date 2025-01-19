/*
 * This class keeps track of all the player's variables, such as x and y position,
 * health, damage, direction, and whether it is firing or not. It is also responsible
 * for moving the players position when the user wants to move.
 */
package finalproject;

/**
 *
 * @author geoff
 */
public class Player {
    protected FinalProject FP;
    
    // Position
    private int posX;
    private int posY;    
    
    private int maxHealth;
    private int health;
    private int defence;
    private int moveSpeed;
    private int damage;
    private int direction;
    
    // Move variables
    boolean moveUp = false;
    boolean allowUp = true;
    boolean moveRight = false;
    boolean allowRight = true;
    boolean moveDown = false;
    boolean allowDown = true;
    boolean moveLeft = false;
    boolean allowLeft = true;
    
    private int t = 0;
    public int f = 0;
    private long moveStart = 0;
    public long fireStart = 0;
    
    boolean firing;
        
    public Player(int posX, int posY, int maxHealth, int damage, int defence, int moveSpeed, int direction){
        this.posX = posX;
        this.posY = posY;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.damage = damage;
        this.defence = defence;
        this.moveSpeed = moveSpeed;
        this.direction = direction;
        
        firing = false;
    }
    
    // Getters and setters
    public int getX(){
        return this.posX;
    }            
    
    public int getY(){
        return this.posY;
    }    
    
    public void setX(int x){
        this.posX = x;
    }
    public void setY(int y){
        this.posY = y;
    }
    
    public int getDirection(){
        return this.direction;
    }
    public void setDirection(int direction){
        this.direction = direction;
    }
    public int getMaxHealth(){
        return this.maxHealth;
    }
    public void setMaxHealth(int h){
        this.maxHealth = h;
    }
    public int getHealth(){
        return this.health;
    }
    public void setHealth(int h){
        this.health = h;
    }
    public int getDefence(){
        return this.defence;
    }
    public void setDefence(int d){
        this.defence = d;
    }
    public int getDamage(){
        return this.damage;
    }
    public void setDamage(int d){
        this.damage = d;
    }
    
    // Moves the player if the keys are pressed, and it is allowed to move.
    // the move variables become true when the movemnet keys are pressed.
    // The allows variables will be set to false by the PlayerCollisionBox class
    // to stop the player from moving through walls.
    public void walk(){
        int changeX = 0;
        int changeY = 0;
        if(moveUp&&moveRight&&allowUp&&allowRight){                    
            if(t==0){
                moveStart = System.currentTimeMillis();            
            }
            t++;
            long end = System.currentTimeMillis();
            if(end-moveStart>=5){                                
                changeY -= 1;
                changeX += 1;      
                t=0;
            }
        }
        if(moveUp&&moveLeft&&allowUp&&allowLeft){                    
            if(t==0){
                moveStart = System.currentTimeMillis();            
            }
            t++;
            long end = System.currentTimeMillis();
            if(end-moveStart>=5){               
                changeY -= 1;
                changeX -= 1;      
                t=0;
            }
        }
        if(moveDown&&moveRight&&allowDown&&allowRight){                    
            if(t==0){
                moveStart = System.currentTimeMillis();            
            }
            t++;
            long end = System.currentTimeMillis();
            if(end-moveStart>=5){                
                changeY += 1;
                changeX += 1;      
                t=0;
            }
        }
        if(moveDown&&moveLeft&&allowDown&&allowLeft){                    
            if(t==0){
                moveStart = System.currentTimeMillis();            
            }
            t++;
            long end = System.currentTimeMillis();
            if(end-moveStart>=5){               
                changeY += 1;
                changeX -= 1;      
                t=0;
            }
        }
        if(moveUp&&allowUp){ 
            if(t==0){
                moveStart = System.currentTimeMillis();            
            }
            t++;
            long end = System.currentTimeMillis();
            if(end-moveStart>=5){                    
                changeY -= 2;      
                t=0;
            }
        }
        if(moveDown&&allowDown){                    
            if(t==0){
                moveStart = System.currentTimeMillis();            
            }
            t++;
            long end = System.currentTimeMillis();
            if(end-moveStart>=5){                
                changeY += 2;      
                t=0;
            }
        }
        if(moveLeft&&allowLeft){                    
            if(t==0){
                moveStart = System.currentTimeMillis();            
            }
            t++;
            long end = System.currentTimeMillis();
            if(end-moveStart>=5){                
                changeX -= 2;      
                t=0;
            }
        }
        if(moveRight&&allowRight){                    
            if(t==0){
                moveStart = System.currentTimeMillis();            
            }
            t++;
            long end = System.currentTimeMillis();
            if(end-moveStart>=5){                
                changeX += 2;      
                t=0;
            }
        }   
        if(moveRight&&moveLeft){                    
            changeX = 0;
        }   
        if(moveUp&&moveDown){                    
            changeY = 0;
        }   
        
        this.posX += changeX;
        this.posY += changeY;
    }
    
    
    
}
