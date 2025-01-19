/*
 * This class is the Melee enemy type. He moves quicker than archers and mages,
 * but can only attack when the player is close. 
 * 
 */
package finalproject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author geoff
 */
public class Melee implements Enemy{
    protected FinalProject FP;
    ArrayManager arrayManager = new ArrayManager();
    
    private int posX;
    private int posY;
    private int maxHealth;
    private int health;
    private boolean move;
    private int direction;
    private long startMove;
    private long endMove;
    private long startAttack;
    private long endAttack;
    private long startAttackAnimation;
    private long endAttackAnimation;
    private boolean attackAnimation;
    private int e = 1;
    private int t = 0;
    private int a = 0;    
    private int identifier;
    private int damage;   
    private int xToY;  
    private int attackTimer;
    private int type = 2;
    
    
    public Melee(int posX, int posY, int maxHealth, int damage, int attackTimer, boolean move, int direction, int xToY, int identifier){
        this.posX = posX;
        this.posY = posY;
        this.maxHealth = maxHealth;
        this.health = maxHealth;        
        this.move = move;
        this.direction = direction;     
        this. identifier = identifier;      
        this.damage = damage;
        this.xToY = xToY;
        this.attackTimer = attackTimer;
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
        return this.type;
    }
    
    // Used to set the type to 3 in the fianl boss so the correct image will be drawn
    @Override
    public void setType(int type){
        this.type = type;
    }
    public int getIdentifier(){
        return this.identifier;
    }
    
    // Moves this enemy closer to the player similarly to archer and mage, but is quicker
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
                if(endMove - startMove >= 6){
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
                if(endMove - startMove >= 6){
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
        
    // Attacks with a sword swipe when the player gets close enough
    public Projectile[] attack(Projectile[] array, Player player, Graphics g){
        AffineTransform at = AffineTransform.getTranslateInstance(this.getX()-25, this.getY()-50);
        int angle = 0;
        if(a==0){
            startAttack = System.currentTimeMillis();
        }
        a++;
        endAttack = System.currentTimeMillis();
        // Attacks id the delay is over, and the player is close enough
        if(endAttack - startAttack > this.attackTimer && (Math.abs((this.posX+25) - (player.getX()+25)) <= 80) && (Math.abs((this.posY+25) - (player.getY()+25)) <= 80)){
            
            player.setHealth((player.getHealth() - (int)((double)this.damage*(1 - (((double)player.getDefence())/((double)player.getDefence()+100))))));
            
            playSound("sword.wav");
            e = 0;
            a = 0;
        }
        
        // Draws the attack animation
        if(e==0){
            startAttackAnimation = System.currentTimeMillis();
        }
        e++;
        endAttackAnimation = System.currentTimeMillis();
        if(endAttackAnimation - startAttackAnimation < 140){
            switch(this.direction){
                case(0):
                    angle = 0;
                    break;
                case(1):
                    angle = 90;
                    break;
                case(2):
                    angle = 180;
                    break;
                case(3):
                    angle = 270;
                    break;
            }
            
            BufferedImage image = getBufferedImage("swipe.png");
            at.rotate(Math.toRadians(angle),50,75);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(image, at, null);
        }
        return array;
    }        
    
    // Plays the given sound from the WAV file
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
    
    public BufferedImage getBufferedImage(String fileName){
        BufferedImage img = null;
        String png = "PNG/";
        try{                    
            img = ImageIO.read(new File(png + fileName));
        }
        catch(Exception e){
            
        }
        
        return img;
    }   
    
}
