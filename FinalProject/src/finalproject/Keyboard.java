/*
 * This class detects when certain keys are pressed and released, and runs different
 * actions accordingly.
 * 
 */
package finalproject;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author geoff
 */
public class Keyboard extends KeyAdapter{
    protected FinalProject FP;
    
    // Detects when keys are pressed
    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_W){
           FP.player.moveUp = true;           
        }
        if(e.getKeyCode()==KeyEvent.VK_S){
           FP.player.moveDown = true;           
        }
        if(e.getKeyCode()==KeyEvent.VK_A){
           FP.player.moveLeft = true;           
        }
        if(e.getKeyCode()==KeyEvent.VK_D){
           FP.player.moveRight = true;           
        }
               
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            FP.player.firing = true;
            FP.pressedSpace = true;                
        }
        
        if(e.getKeyCode()==KeyEvent.VK_I){
            FP.player.setDirection(0);
        }
        if(e.getKeyCode()==KeyEvent.VK_L){
            FP.player.setDirection(1);
        }
        if(e.getKeyCode()==KeyEvent.VK_K){
            FP.player.setDirection(2);
        }
        if(e.getKeyCode()==KeyEvent.VK_J){
            FP.player.setDirection(3);
        }
        
        // Go to the test screen from the title screen if certain keys are pressed at the same time
        if(e.getKeyCode()==KeyEvent.VK_I){           
            FP.pressedI = true;
        }
        if(e.getKeyCode()==KeyEvent.VK_C){            
            FP.pressedC = true;            
        }
        if(e.getKeyCode()==KeyEvent.VK_S){            
            FP.pressedS = true;            
        }
        if(e.getKeyCode()==KeyEvent.VK_4){            
            FP.pressed4 = true;            
        }
        if(e.getKeyCode()==KeyEvent.VK_U){            
            FP.pressedU = true;            
        }
        
        // Go to title screen from test screen if Escape is pressed
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){           
            FP.pressedEscape = true;                               
        }        
        
        if(e.getKeyCode()==KeyEvent.VK_F){            
            FP.pressedF = true;   
        }
                                        
        if(e.getKeyCode()==KeyEvent.VK_E){            
            FP.pressedE = true;
            if(FP.inventoryScreen){
                FP.inventoryScreen = false; 
            }else{
                FP.inventoryScreen = true; 
            }            
        }
        
        // These are all the test controls. They are enabled once you enter the
        // test room for the first time and can then be used anywhere in the game.
        if(FP.testControls){
            // Resets the current room
            if(e.getKeyCode()==KeyEvent.VK_Q){            
                FP.initialize = true;            
            }
            // Test Gear drops in the test room
            if(e.getKeyCode()==KeyEvent.VK_G){                        
                FP.dropGear();            
            }           
            // Spawns archer
            if(e.getKeyCode()==KeyEvent.VK_T){            
                FP.enemies = FP.arrayManager.insertEnemy(FP.enemies, FP.enemies.length, new Archer(300, 400, 100, 2, 3, 5, true, 1, 5, 0));        
            }
            // Spawns melee
            if(e.getKeyCode()==KeyEvent.VK_Y){            
                FP.enemies = FP.arrayManager.insertEnemy(FP.enemies, FP.enemies.length, new Melee(300, 400, 300, 2, 500, true, 2, 5, 0));      
            }
            // Spawns mage
            if(e.getKeyCode()==KeyEvent.VK_U){            
                FP.enemies = FP.arrayManager.insertEnemy(FP.enemies, FP.enemies.length, new Mage(300, 400, 100, 2, 3, 5, true, 1, 5, 0));        
            }
            // Clears room of enemies and projectiles
            if(e.getKeyCode()==KeyEvent.VK_N){            
                FP.wipeGameArrays();            
            }
            // Skips to the next room
            if(e.getKeyCode()==KeyEvent.VK_B){            
                FP.nextScreen();
            }
            // Rerolls the shop
            if(e.getKeyCode()==KeyEvent.VK_V){            
                FP.rollShop();
            }
            if(e.getKeyCode()==KeyEvent.VK_Z){            
                FP.saveProgress();
            }
            if(e.getKeyCode()==KeyEvent.VK_X){            
                FP.loadProgress();
            }
        }
    }
    
    // Detects when keys are released
    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_W){
           FP.player.moveUp = false;
        }
        if(e.getKeyCode()==KeyEvent.VK_S){
           FP.player.moveDown = false;
        }
        if(e.getKeyCode()==KeyEvent.VK_A){
           FP.player.moveLeft = false;
        }
        if(e.getKeyCode()==KeyEvent.VK_D){
           FP.player.moveRight = false;
        }
        
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            FP.player.firing = false;
            FP.pressedSpace = false;
        }
        
        // Set variables to false if the jeys to go to the test screen are released
        if(e.getKeyCode()==KeyEvent.VK_I){            
            FP.pressedI = false;            
        }
        if(e.getKeyCode()==KeyEvent.VK_C){            
            FP.pressedC = false;            
        }
        if(e.getKeyCode()==KeyEvent.VK_S){            
            FP.pressedS = false;            
        }
        if(e.getKeyCode()==KeyEvent.VK_4){            
            FP.pressed4 = false;            
        }
        if(e.getKeyCode()==KeyEvent.VK_U){            
            FP.pressedU = false;            
        }
        
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){           
            FP.pressedEscape = false;                               
        }
        
        if(e.getKeyCode()==KeyEvent.VK_F){            
            FP.pressedF = false;            
        }
        if(e.getKeyCode()==KeyEvent.VK_E){            
            FP.pressedE = false;            
        }
    }
        
}
