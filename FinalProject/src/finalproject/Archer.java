/*
 * This class is for the archer enemy type. It knows its position, health, damage,
 * fire rate, projectile speed, whether it can move or not, its direction, and how 
 * it moves.
 */
package finalproject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author geoff
 */
public class Archer implements Enemy{
    protected FinalProject FP;
    ArrayManager arrayManager = new ArrayManager();
        
    private int posX;
    private int posY;
    private int maxHealth;
    private int health;
    private int projectileSpeed;
    private boolean move;
    private int direction;
    private long startMove;
    private long endMove;
    private long startAttack;
    private long endAttack;
    private int shotTimer;
    private int t = 0;
    private int a = 0;   
    private int identifier;
    private int damage;
    private int fireRate;
    private int xToY; 
    
    // type is used to draw the correct enemy image on the screen
    private int type = 0;
       
    public Archer(int posX, int posY, int maxHealth, int damage, int fireRate, int projectileSpeed, boolean move, int direction, int xToY, int identifier){
        this.posX = posX;
        this.posY = posY;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.projectileSpeed = projectileSpeed;
        this.move = move;
        this.direction = direction;     
        this. identifier = identifier;      
        this.damage = damage;
        this.fireRate = fireRate;
        this.xToY = xToY;
        
        switch(fireRate){
            case(1):
                this.shotTimer = 2000;
                break;
            case(2):
                this.shotTimer = 1500;
                break;
            case(3):
                this.shotTimer = 1000;
                break;
            case(4):
                this.shotTimer = 800;
                break;
            case(5):
                this.shotTimer = 600;
                break;
            case(6):
                this.shotTimer = 500;
                break;
            case(7):
                this.shotTimer = 400;
                break;
            case(8):
                this.shotTimer = 300;
                break;
            case(9):
                this.shotTimer = 200;
                break;
            case(10):
                this.shotTimer = 100;
                break;
        }
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
    public int getMaxHealth(){
        return this.maxHealth;
    }
    public int getHealth(){
        return this.health;
    }
    public void setHealth(int h){
        this.health = h;
    }
    
    public int getDirection(){
        return this.direction;
    }
    public void setDirection(int direction){
        this.direction = direction;
    }
    
    public int getType(){
        return 0;
    }   
    // Used to set the type to 3 in the final boss so the correct image will be drawn
    public void setType(int type){
        this.type = type;
    }
    public int getIdentifier(){
        return this.identifier;
    }
    
    // This moves the archer towards the player based on its position. The xToY
    // random calculation is used to give variance in how every enemy moves. If
    // all the enemies moved the same, they would stack on top of each other.
    public void move(Player player){
        // Move the enemy
        int change = 0;
        int random = (int)Math.floor(9*Math.random()+1);
        if(random < this.xToY){
            if(this.move){
                if(player.getY()>this.posY){
                    change = 1;
                }else if(player.getY()<this.posY){
                    change = -1;
                }
                if(t == 0){
                    startMove = System.currentTimeMillis();
                }
                t++;
                endMove = System.currentTimeMillis();
                if(endMove - startMove >= 10){
                    startMove = System.currentTimeMillis();
                    this.posY += change;
                }
            }
        }else{
            if(this.move){
                if(player.getX()>this.posX){
                    change = 1;
                }else if(player.getX()<this.posX){
                    change = -1;
                }
                if(t == 0){
                    startMove = System.currentTimeMillis();
                }
                t++;
                endMove = System.currentTimeMillis();
                if(endMove - startMove >= 10){
                    startMove = System.currentTimeMillis();
                    this.posX += change;
            }
        }
        }
        
        // Change the enemy's direction     
        if(this.move){
            int x = player.getX() - this.posX; // Up is +   Down is -       
            int y = this.posY - player.getY(); // Right is +   Left is -
            if(Math.abs(x)>Math.abs(y)){
                if(x > 0){
                    this.direction = 1;
                }else if(x < 0){
                    this.direction = 3;
                }
            }else{
                if(y > 0){
                    this.direction = 0;
                }else if(y < 0){
                    this.direction = 2;
                }
            }
        }
        
    }
        
    // Shoots projectiles on a timer based on its direction
    public Projectile[] attack(Projectile[] array, Player player, Graphics g){
        if(a==0){
            startAttack = System.currentTimeMillis();
        }
        a++;
        endAttack = System.currentTimeMillis();
        if(endAttack - startAttack > this.shotTimer){
            array = arrayManager.insertProjectile(array, 0, new Projectile(this.posX, this.posY, this.damage, this.projectileSpeed, this.direction, true, this.getType()));
            if(this.getType()==0){
                playSound("bowShoot.wav");
            }
            else{
                playSound("magic.wav");
            }
            a = 0;
        }
        return array;
    }        
    
    // Plays the given sound
    public static synchronized void playSound(final String fileName) {

        String wav = "WAV/";
        try{

            Clip clip = AudioSystem.getClip();

            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(wav + fileName).getAbsoluteFile());

            clip.open(inputStream);

            clip.start();

        }catch(Exception e){

            System.err.println(e.getMessage());
        }
    }
}
