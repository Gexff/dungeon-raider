/*
 * Name   : Geoffrey Jensen
 * Program: Dungeon Raider
 * Date   : June 5 2020
 * Purpose: This is a mini rpg looting game created for an ICS4U final project.
 *          There are 5 stages, each with 4 rooms and a boss. As you progress through 
 *          the game you will fight harder enemies, but also receive better gear. 
 *          This program uses an interface for its enemy types, and utilizes a 
 *          recursive sorting method for organizing the player's inventory.
 */
package finalproject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;


/**
 *
 * @author geoff
 */
public class FinalProject extends JFrame{    
    
    // Boolean for detecting a save file
    boolean saveFound = false;
    
    // Used to reset rooms and load enemies
    boolean initialize = true;    
    
    // Used in the final boss fight to spawn enemies    
    int spawnWave = 0;    
    
    // Variables to check if keys are held on main screen to load the test screen
    boolean pressedI = false;
    boolean pressedC = false;
    boolean pressedS = false;
    boolean pressed4 = false;
    boolean pressedU = false;
    
    // Check if other keys are pressed
    boolean pressedEscape = false;    
    boolean pressedSpace = false;
    boolean pressedF = false;
    boolean pressedE = false;
    
    // Strring formats
    DecimalFormat f1 = new DecimalFormat("##.#");
    DecimalFormat f2 = new DecimalFormat("#");
    
    // Boolean vairables for each screen
    boolean test = false;
    boolean titleScreen = false;
    boolean saveScreen = true;
    boolean inventoryScreen = false;
    boolean stageSelect = false;   
    boolean shop = false;
    boolean stage1a = false;
    boolean stage1b = false;
    boolean stage1c = false;
    boolean stage1d = false;
    boolean stage1e = false;
    boolean stage2a = false;
    boolean stage2b = false;
    boolean stage2c = false;
    boolean stage2d = false;
    boolean stage2e = false;
    boolean stage3a = false;
    boolean stage3b = false;
    boolean stage3c = false;
    boolean stage3d = false;
    boolean stage3e = false;
    boolean stage4a = false;
    boolean stage4b = false;
    boolean stage4c = false;
    boolean stage4d = false;
    boolean stage4e = false;
    boolean stage5a = false;
    boolean stage5b = false;
    boolean stage5c = false;
    boolean stage5d = false;
    boolean stage5e = false;
    boolean died = false;
        
    // Variables for tutorial screens and checking if it is the first time
    boolean tutorial1 = false;
    boolean tutorial2 = false;
    boolean firstTutorial = true;
    
    // Used to prevent gear from dropping when leaving a room;
    boolean roomExited = true;
    
    // Used to determine whether to draw the boss health bar
    boolean boss = false;
    
    // Used in the stage4e boss fight to keep track of the total health of all 3 bosses
    int totalHealth;
    
    // Allows the test controls to be used
    boolean testControls = false;
    
    int roomNumber = 0;
    
    // Modifiers for gear values
    double difficultyModifier = 1;
    double stageModifier = 1;
    double roomModifier = 1;
    double shopModifier = 1;
    
    boolean roomComplete = false;
    
    // Store the amount of coins
    int coins = 0;
    
    // Stage selection
    int stageSelectPage = -1;    
    
    // Track where the mouse is and where it clicks
    int mouseClickedX;
    int mouseClickedY;
    int mouseMovedX;
    int mouseMovedY;
    boolean mouseClicked = false;
    
    protected Image dbImage;
    private Graphics dbg;    
              
    // Used for player movement
    long moveStart = 0;
    int t = 0;
    
    // Used for a death timer
    long deathStart = 0;
    int d = 0;
           
    // Used to cntrol player fire rate
    int fireRate;
    
    // Used to determine which sorting method is to be used in the inventory
    int sortMethod = 0;
    String sortMessage = "";
    
    // ID value for gear. Will increase everytime an item is created
    int currentID = 5;
    
    // Allows the select souns to play once when the mouse is placed over an item
    boolean selectSound = true;    
    
    // ArrayManger for insert, delete, and sort methods
    ArrayManager arrayManager = new ArrayManager();
    
    // Arrays for game content
    public Projectile[] arrows = new Projectile[0];
    public PlayerCollisionBox[] walls = new PlayerCollisionBox[0];
    public Enemy[] enemies = new Enemy[0];
    public Gear[] inventory = new Gear[0];
    public Gear[] equipped = new Gear[5];
    public Gear[] shopGear = new Gear[0];
            
    // Player object
    public Player player = new Player(500,500,100,10,0,5,0);
    
    public FinalProject(){   
                       
        // Create starting gear
        equipped[0] = new Gear(0, 5, 3, 5, 0, 0);
        equipped[1] = new Gear(1, 0, 10, 0, 0, 1);
        equipped[2] = new Gear(2, 0, 10, 0, 0, 2);
        equipped[3] = new Gear(3, 0, 10, 0, 0, 3);
        equipped[4] = new Gear(4, 0, 10, 0, 0, 4);
                   
        // Create PlayerCollisionBoxes for every wall in the game
        walls = arrayManager.insertPlayerCollisionBox(walls, 0, new PlayerCollisionBox(player, -1, 69, -1 ,769));   
        walls = arrayManager.insertPlayerCollisionBox(walls, 1, new PlayerCollisionBox(player, 955, 1025, -1 ,769));   
        walls = arrayManager.insertPlayerCollisionBox(walls, 2, new PlayerCollisionBox(player, -1, 1025, 642 ,769));
        walls = arrayManager.insertPlayerCollisionBox(walls, 3, new PlayerCollisionBox(player, -1, 477, -1 ,88));
        walls = arrayManager.insertPlayerCollisionBox(walls, 4, new PlayerCollisionBox(player, 549, 1025, -1 ,88));
        walls = arrayManager.insertPlayerCollisionBox(walls, 5, new PlayerCollisionBox(player, -1, 1025, -1 ,88));
        walls = arrayManager.insertPlayerCollisionBox(walls, 6, new PlayerCollisionBox(player, 161, 464, -1 ,569));
        walls = arrayManager.insertPlayerCollisionBox(walls, 7, new PlayerCollisionBox(player, 559, 857, -1 ,569));
        walls = arrayManager.insertPlayerCollisionBox(walls, 8, new PlayerCollisionBox(player, 240, 786, 235 ,510));
        walls = arrayManager.insertPlayerCollisionBox(walls, 9, new PlayerCollisionBox(player, -1, 284, -1 ,228));
        walls = arrayManager.insertPlayerCollisionBox(walls, 10, new PlayerCollisionBox(player, 724, 1024, -1 ,228));
        walls = arrayManager.insertPlayerCollisionBox(walls, 11, new PlayerCollisionBox(player, -1, 284, 501, 768));
        walls = arrayManager.insertPlayerCollisionBox(walls, 12, new PlayerCollisionBox(player, 724, 1024, 501 ,768));  
        walls = arrayManager.insertPlayerCollisionBox(walls, 13, new PlayerCollisionBox(player, 187, 415, -1, 768));
        walls = arrayManager.insertPlayerCollisionBox(walls, 14, new PlayerCollisionBox(player, 610, 837, -1 ,768)); 
        walls = arrayManager.insertPlayerCollisionBox(walls, 15, new PlayerCollisionBox(player, -1, 462, 179, 275));
        walls = arrayManager.insertPlayerCollisionBox(walls, 16, new PlayerCollisionBox(player, 561, 1024, 179 ,275)); 
        walls = arrayManager.insertPlayerCollisionBox(walls, 17, new PlayerCollisionBox(player, -1, 1024, 179 ,275));  
        walls = arrayManager.insertPlayerCollisionBox(walls, 18, new PlayerCollisionBox(player, 173, 305, 179 ,807)); 
        walls = arrayManager.insertPlayerCollisionBox(walls, 19, new PlayerCollisionBox(player, 303, 835, 497, 563));
        walls = arrayManager.insertPlayerCollisionBox(walls, 20, new PlayerCollisionBox(player, 432, 964, 342 ,408)); 
        walls = arrayManager.insertPlayerCollisionBox(walls, 21, new PlayerCollisionBox(player, 303, 835, 179 ,245));        
        
        // Initialize shop
        shopModifier = stageModifier*difficultyModifier*roomModifier;
        rollShop();          
    }
    
    public void init(){
        Keyboard keyboard = new Keyboard();
        keyboard.FP = this;
        addKeyListener(keyboard);
        Mouse mouse = new Mouse();
        mouse.FP = this;
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        windowManager();
    }
    
    public void windowManager(){
        setTitle("Dungeon Raider");
        setResizable(false);
        setSize(1024,768);
        setBackground(new Color(211,211,211));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }        
    
    public void paintComponent(Graphics g){
        // Test Screen accessed by pressing ICS4U on the title screen. Used for testing
        if(test){
            if(!inventoryScreen){
                // Initializes room and spawns enemies
                if(initialize){
                    // Turns test controls on
                    testControls = true;
                    roomComplete = false;
                    roomExited = false;
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    //enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(300, 400, 100, 10, 3, 5, true, 1, 10, 0));
                    //enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(300, 200, 100, 10, 500, true, 1, 5, 1));
                    //enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(500, 200, 100, 10, 5, 5, true, 2, 1, 2));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            

                if(pressedEscape){                
                    allScreensOff();
                    titleScreen = true;
                    roomExited = true;
                    wipeGameArrays();
                }                          
                
                playGame(g);
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        // Death screen
        if(died){                        
            g.setColor(Color.BLACK);         
            g.setFont(new Font("Arial",Font.BOLD,120));
            g.drawString("You Died!",235,420);            
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.drawString("Press SPACE to Continue",270,700);
            
            // Add a delay when you die so you don't exit the screen right away
            if(d == 0){
                deathStart = System.currentTimeMillis();
            }
            d++;
            long end = System.currentTimeMillis();
            
            if(end - deathStart >= 500){
                if(pressedSpace){
                    // Go back to the stage select screen
                    stageSelect = true;
                    died = false;
                    pressedSpace = false;
                    initialize = true;
                    roomComplete = false;
                    // Reset health
                    player.setHealth(player.getMaxHealth());
                    // Wipe projectiles and enemies
                    wipeGameArrays();
                }
            }
            repaint();
        }
        // Title Screen
        if(titleScreen){           
            // Text
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial",Font.BOLD,80));
            g.drawString("Dungeon Raider",200, 300);
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.drawString("Press SPACE to Continue",260,700);         
            
            // Draw the 3 enemy types
            g.drawImage(getBufferedImage("melee.png"), 465, 360, this);
            g.drawImage(getBufferedImage("archer.png"), 325, 360, this);
            g.drawImage(getBufferedImage("mage.png"), 605, 360, this);
            
            // Go to the test screen if ICS4U is pressed
            if(pressedI&&pressedC&&pressedS&&pressed4&&pressedU){
                test = true;
                titleScreen = false;
                roomNumber = 0;
                // Puts the dev gloves in the inventory
                inventory = arrayManager.insertGear(inventory, 0, new Gear(3, 99999, 5, 999, 6, -1));
                initialize = true;
                calculateStats();
            }
            
            // Go to the stage select screen if space is presssed
            if(pressedSpace){
                stageSelect = true;
                titleScreen = false;
                pressedSpace = false;
                calculateStats();
            }                                   
            repaint();
        }
        
        // This screen is where save files will be either loaded, or new games will
        // be started.
        if(saveScreen){
            if(initialize){
                initialize = false;
                // Check if a save file already exists.
                try{
                    File save = new File("save.txt");                   
                    if(save.createNewFile()){
                        saveFound = false;
                    }
                    else{
                        saveFound = true;
                    }
                }
                catch(Exception e){}
            }

            // Text
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial",Font.BOLD,50));
            g.drawString("Save Loader",360,100);
            g.fillRect(260, 300, 500, 80);
            g.setColor(Color.WHITE);
            g.drawRect(260, 300, 500, 80);
            
            // If the save file was found
            if(saveFound){
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,35));
                g.drawString("Save File Found!",370,255);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Load Save File",375,355);
                
                // New Game Button
                g.setColor(Color.BLACK);                
                g.fillRect(355, 430, 300, 60);
                g.setColor(Color.WHITE);
                g.drawRect(355, 430, 300, 60);
                g.setFont(new Font("Arial",Font.BOLD,25));
                g.drawString("Start New Game",410,469);
                if(mouseClickedX >= 355 && mouseClickedX <=655 && mouseClickedY >=430 && mouseClickedY <= 490){
                    initialize = true;
                    titleScreen = true;
                    saveScreen = false;
                    calculateStats();
                    resetMousePos();
                }
            }
            // If no save file was found
            else{
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,35));
                g.drawString("No Save File Found!",350,255);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Start New Game",360,355);
            }
            
                                                
            if(mouseClickedX >= 260 && mouseClickedX <=760 && mouseClickedY >=300 && mouseClickedY <= 380){
                if(saveFound){
                    loadProgress();
                }                
                initialize = true;
                titleScreen = true;
                saveScreen = false;
                calculateStats();
                resetMousePos();
            }           
            resetMousePos();
            repaint();
        }
        
        // Shop screen
        if(shop){
            if(!inventoryScreen){
                // Text
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,50));
                g.drawString("Shop",440,100);
                
                // COin count
                g.drawImage(getBufferedImage("coin.png"),30 ,50, this);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,90));
                g.drawString(coins+"",100,120);
                
                // Draws the items on the screen
                try{
                    drawGear(168, 270, shopGear[0], g);
                }catch(Exception e){}
                g.drawRect(168, 270, 70, 70);
                try{
                    drawGear(468, 270, shopGear[1], g);
                }catch(Exception e){}
                g.drawRect(468, 270, 70, 70);
                try{
                    drawGear(768, 270, shopGear[2], g);
                }catch(Exception e){}
                g.drawRect(768, 270, 70, 70);
                
                // Reroll shop button
                g.setColor(Color.GREEN);
                g.fillRect(200, 500, 612, 100);
                g.setColor(Color.BLACK);
                g.drawRect(200, 500, 612, 100);
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Reroll Shop for",250,566);
                
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,90));
                g.drawString(coins+"",100,120);
                g.drawImage(getBufferedImage("coin.png"), 560, 514, this);
                
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,90));
                g.drawString(""+f2.format((int)(5*shopModifier)),620,584);
                
                if(mouseClickedX >= 200 && mouseClickedX <=812 && mouseClickedY >=500 && mouseClickedY <= 600){
                    if(coins >= ((int)(5*shopModifier))){
                        playSound("reroll.wav");
                        rollShop();
                        coins -= ((int)(5*shopModifier));
                        saveProgress();
                    }
                    resetMousePos();
                }
                
                // Back Button
                g.setColor(Color.GREEN);
                g.fillRect(820, 40, 180, 50);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Go Back",830,78);
                g.setColor(Color.BLACK);
                g.drawRect(820, 40, 180, 50);                
                if(mouseClickedX >= 820 && mouseClickedX <=1000 && mouseClickedY >=40 && mouseClickedY <= 90){
                    stageSelect = true;
                    shop = false;
                    resetMousePos();
                }
                
                // Play sounds and show stat of gear when the mouse is over the item
                if(mouseMovedX >= 168 && mouseMovedX <=238 && mouseMovedY >=270 && mouseMovedY <= 340){
                    if(selectSound){
                        playSound("select.wav");
                        selectSound = false;
                    }
                    try{
                        drawGearStats(false, shopGear[0], g);
                    }catch(Exception e){}
                    try{
                        buyGear(0);
                    }catch(Exception e){}
                }
                else if(mouseMovedX >= 468 && mouseMovedX <=538 && mouseMovedY >=270 && mouseMovedY <= 340){
                    if(selectSound){
                        playSound("select.wav");
                        selectSound = false;
                    }
                    try{
                        drawGearStats(true, shopGear[1], g);
                    }catch(Exception e){}
                    try{
                        buyGear(1);
                    }catch(Exception e){}
                }
                else if(mouseMovedX >= 768 && mouseMovedX <=838 && mouseMovedY >=270 && mouseMovedY <= 340){
                    if(selectSound){
                        playSound("select.wav");
                        selectSound = false;
                    }
                    try{
                        drawGearStats(true, shopGear[2], g);
                    }catch(Exception e){}
                    try{
                        buyGear(2);
                    }catch(Exception e){}
                }
                else{
                    selectSound = true;
                    mouseClicked = false;
                }
                
                // Draw the buy prices
                try{
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial",Font.BOLD,40));
                    g.drawString(""+(shopGear[0].getSellPrice()*3),163,260);
                }catch(Exception e){}
                try{
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial",Font.BOLD,40));
                    g.drawString(""+(shopGear[1].getSellPrice()*3),463,260);
                }catch(Exception e){}
                try{
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial",Font.BOLD,40));
                    g.drawString(""+(shopGear[2].getSellPrice()*3),763,260);
                }catch(Exception e){}
                
                if(pressedEscape){
                    stageSelect = true;
                    shop = false;
                    calculateStats();
                    pressedEscape = false;
                }
            }
            else{
                goToInventory(g);
            }
            resetMousePos();
            repaint();
        }
        
        // Stage select screen
        if(stageSelect){
            if(!inventoryScreen){   
                // Go back to the title screen if escape is pressed
                if(pressedEscape){
                    titleScreen = true;
                    stageSelect = false;                               
                }
                // Draw the images and the stage number for each stage based on
                // stageSelectPage
                switch (stageSelectPage){
                    case(-1):
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("Arial",Font.BOLD,50));
                        g.drawString("Tutorial",400,100);
                        g.drawImage(getBufferedImage("bow.png"), 265, 260, this);
                        g.drawImage(getBufferedImage("helmet.png"), 365, 260, this);
                        g.drawImage(getBufferedImage("glove.png"), 465, 260, this);                        
                        g.drawImage(getBufferedImage("chestpiece.png"), 565, 260, this);
                        g.drawImage(getBufferedImage("boots.png"), 665, 260, this);
                        break;
                    case(0):
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("Arial",Font.BOLD,50));
                        g.drawString("Stage 1",405,100);
                        g.drawImage(getBufferedImage("archer.png"), 465, 200, this);
                        g.drawImage(getBufferedImage("arrow.png"), 500, 280, this);
                        g.drawImage(getBufferedImage("arrow.png"), 465, 330, this);
                        g.drawImage(getBufferedImage("arrow.png"), 485, 390, this);
                        break;
                    case(1):
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("Arial",Font.BOLD,50));
                        g.drawString("Stage 2",405,100);
                        g.drawImage(getBufferedImage("mage.png"), 465, 200, this);
                        g.drawImage(getBufferedImage("magic.png"), 475, 270, this);
                        g.drawImage(getBufferedImage("magic.png"), 500, 320, this);
                        g.drawImage(getBufferedImage("magic.png"), 480, 400, this);
                        break;
                    case(2):
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("Arial",Font.BOLD,50));
                        g.drawString("Stage 3",405,100);
                        g.drawImage(getBufferedImage("melee.png"), 465, 210, this);
                        g.drawImage(getBufferedImage("archer.png"), 365, 310, this);
                        g.drawImage(getBufferedImage("archer.png"), 565, 310, this);
                        break;
                    case(3):
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("Arial",Font.BOLD,50));
                        g.drawString("Stage 4",405,100);
                        g.drawImage(getBufferedImage("melee.png"), 465, 260, this);
                        g.drawImage(getBufferedImage("archer.png"), 365, 260, this);
                        g.drawImage(getBufferedImage("mage.png"), 565, 260, this);
                        break;
                    case(4):
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("Arial",Font.BOLD,50));
                        g.drawString("Stage 5",405,100);
                        g.drawImage(getBufferedImage("boss.png"), 465, 220, this);
                        g.drawImage(getBufferedImage("melee.png"), 465, 320, this);
                        g.drawImage(getBufferedImage("archer.png"), 365, 320, this);
                        g.drawImage(getBufferedImage("mage.png"), 565, 320, this);
                        break;
                }
                // Draws the two arrows on both sides of the screens
                AffineTransform at = AffineTransform.getTranslateInstance(60,270);
                BufferedImage arrow2 = getBufferedImage("arrow2.png");
                Graphics2D g2 = (Graphics2D) g;
                // Left button
                if(!(stageSelectPage <= -1)){
                    g2.drawImage(arrow2, at, null);
                    if(mouseClickedX >= 60 && mouseClickedX <=140 && mouseClickedY >=270 && mouseClickedY <= 370){
                        if(stageSelectPage>=0){
                            stageSelectPage -= 1;
                        }                
                        resetMousePos();
                    }
                }
                // Right button
                if(!(stageSelectPage >= 4)){
                    at = AffineTransform.getTranslateInstance(880,270);
                    at.rotate(Math.toRadians(180),arrow2.getWidth()/2,arrow2.getHeight()/2);                   
                    g2.drawImage(arrow2, at, null);

                    if(mouseClickedX >= 880 && mouseClickedX <=960 && mouseClickedY >=270 && mouseClickedY <= 370){
                        if(stageSelectPage<=3){
                            stageSelectPage += 1;
                        }                
                        resetMousePos();
                    }
                }
                // Difficulty text
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Difficulty",407,500);
                g.setColor(Color.GREEN);
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Easy",160,600);

                g.setColor(Color.YELLOW);
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Medium",418,600);

                g.setColor(Color.RED);
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Hard",750,600);

                // Difficulty buttons
                if(mouseClickedX >= 116 && mouseClickedX <=296 && mouseClickedY >=556 && mouseClickedY <= 616){                
                    difficultyModifier = 1;                                
                    resetMousePos();
                }
                if(mouseClickedX >= 400 && mouseClickedX <=580 && mouseClickedY >=556 && mouseClickedY <= 616){                
                    difficultyModifier = 2;                                
                    resetMousePos();
                }
                if(mouseClickedX >= 705 && mouseClickedX <=885 && mouseClickedY >=556 && mouseClickedY <= 616){                
                    difficultyModifier = 3;                                
                    resetMousePos();
                }

                // Draws a rectangle around the selected difficulty
                switch((int)difficultyModifier){
                    case(1):
                        g.setColor(Color.BLACK);
                        g.drawRect(116, 556, 180, 60);
                        break;
                    case(2):
                        g.setColor(Color.BLACK);
                        g.drawRect(400, 556, 180, 60);
                        break;
                    case(3):
                        g.setColor(Color.BLACK);
                        g.drawRect(705, 556, 180, 60);
                        break;
                }

                // Start button
                g.setColor(Color.GREEN);
                g.fillRect(400, 656, 180, 60);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Start",445,700);
                g.drawRect(400, 656, 180, 60);
                if(mouseClickedX >= 400 && mouseClickedX <=580 && mouseClickedY >=656 && mouseClickedY <= 716){
                    roomExited = false;
                    initialize = true;
                    calculateStats();

                    // Loads the selected stage
                    switch(stageSelectPage){
                        case(-1):
                            allScreensOff();
                            tutorial1 = true;
                            roomNumber = -2;
                            roomModifier = 1;
                            stageModifier = 1;   
                            break;
                        case(0):
                            allScreensOff();
                            stage1a = true;
                            roomNumber = 1;
                            roomModifier = 1;
                            stageModifier = 1;                                                 
                            break;
                        case(1):
                            allScreensOff();
                            stage2a = true;
                            roomNumber = 6;
                            roomModifier = 1;
                            stageModifier = 2;                        
                            break;
                        case(2):
                            allScreensOff();
                            stage3a = true;
                            roomNumber = 11;
                            roomModifier = 1;
                            stageModifier = 3;                        
                            break;
                        case(3):
                            allScreensOff();
                            stage4a = true;
                            roomNumber = 16;
                            roomModifier = 1;
                            stageModifier = 4;                       
                            break;
                        case(4):
                            allScreensOff();
                            stage5a = true;
                            roomNumber = 21;
                            roomModifier = 1;
                            stageModifier = 5;                                               
                            break;
                    }
                    resetMousePos();
                }
                // Coin count
                g.drawImage(getBufferedImage("coin.png"),30 ,50, this);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,90));
                g.drawString(coins+"",100,120);
                
                // Shop button
                g.setColor(Color.GREEN);
                g.fillRect(820, 40, 180, 50);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Shop",860,77);
                g.setColor(Color.BLACK);
                g.drawRect(820, 40, 180, 50);                
                if(mouseClickedX >= 820 && mouseClickedX <=1000 && mouseClickedY >=40 && mouseClickedY <= 90){
                    stageSelect = false;
                    shop = true;
                    resetMousePos();
                }
            }        
            else{
                goToInventory(g);
            }
            resetMousePos();
            repaint();
        }
        
        // First tutorial room
        if(tutorial1){
            if(!inventoryScreen){
                // Initializes room
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                    
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(488, 90,(int)(30*bonusModifier), (int)(5*bonusModifier), 1000, false, 2, 5, 0));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            

                // Draws controls on th screen
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Move", 225, 280);
                
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,20));
                g.drawRect(250, 300, 50, 50);                
                g.drawString("W", 265, 334);
                g.drawRect(195, 360, 50, 50);                
                g.drawString("A", 214, 394);
                g.drawRect(250, 360, 50, 50);                
                g.drawString("S", 269, 394);
                g.drawRect(305, 360, 50, 50);                
                g.drawString("D", 324, 394);
                
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Shoot", 450, 440);
                
                g.setFont(new Font("Arial",Font.BOLD,30));
                g.drawRect(362, 460, 300, 50);                
                g.drawString("Space", 463, 494);
                
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Look", 685, 280);
                
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,20));
                g.drawRect(710, 300, 50, 50);                
                g.drawString("I", 733, 334);
                g.drawRect(655, 360, 50, 50);                
                g.drawString("J", 674, 394);
                g.drawRect(710, 360, 50, 50);                
                g.drawString("K", 729, 394);
                g.drawRect(765, 360, 50, 50);                
                g.drawString("L", 784, 394);
                
                // Draws an arrow if the room is complete
                if(roomComplete){
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial",Font.BOLD,70));
                    g.drawString("↑",496, 180);
                }
                
                // Go back to the stage select screen if escape is pressed
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                                          
                playGame(g);
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        // Second tutorial screen
        if(tutorial2){
            if(!inventoryScreen){
                // Initializes room
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();                                                
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            

                // Draws the "Open Inventory" and "E" text
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,40));
                g.drawString("Open Inventory", 370, 280);
                
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,20));
                g.drawRect(488, 300, 50, 50);                
                g.drawString("E", 506, 334);
                                                
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial",Font.BOLD,70));
                g.drawString("↑",496, 180);
                
                // Completes room when the inventory is opened
                if(pressedE){
                    roomComplete = true;
                }
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        /* The next 25 if statements are for loading each of the rooms in the game.
        *  Initialize is for when the room is first loaded, and it adds the enemies
        *  for room, and resets the game variables, such as the player's health, and
        *  player position. It also deletes any projectiles that may still be in the
        *  array from the previous room. Some rooms also have walls that are drawn in,
        *  then the collision box is created when the specific PlayerCollisonBox from
        *  the walls array is called with the testPlayerCollision() method from its
        *  class. If its a boss, the boss variable will be set to true, and then
        *  the health bar will be drawn at the top. playGame(g) calls several other
        *  methods that are responsible for creating the gameplay, such as movement
        *  for the player, enemies, and projectiles. All of this is inside an if
        *  statement for inventoryScreen. This is so the inventory screen can be
        *  accessed, and when it is exited, you will be brought back to the correct
        *  screen. The health and damage of the enemies scales with 3 variables,
        *  these will increase as the stage, difficulty, and room increases.
        */
        if(stage1a){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                    
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(230, 115,(int)(50*bonusModifier), (int)(10*bonusModifier), 3, 4, true, 2, 2, 0));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            

                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage1b){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                    
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(120, 173,(int)(50*bonusModifier), (int)(10*bonusModifier), 4, 5, true, 2, 10, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(855, 497,(int)(50*bonusModifier), (int)(10*bonusModifier), 3, 9, true, 2, 10, 1));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            

                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage1c){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                        
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(488, 200,(int)(80*bonusModifier), (int)(15*bonusModifier), 1000, true, 2, 5, 1));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            

                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage1d){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                        
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(860, 200,(int)(80*bonusModifier), (int)(15*bonusModifier), 1000, true, 2, 7, 1));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(150, 173,(int)(50*bonusModifier), (int)(10*bonusModifier), 4, 5, true, 2, 10, 0));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            

                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage1e){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();  
                    boss = true;
                    double bonusModifier = (difficultyModifier);                        
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(488, 300,(int)(300*bonusModifier), (int)(8*bonusModifier), 2, 20, true, 2, 5, 0));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            

                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage2a){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                           
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(88, 100,(int)(50*bonusModifier), (int)(10*bonusModifier), 2, 20, false, 2, 10, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(488, 100,(int)(50*bonusModifier), (int)(10*bonusModifier), 2, 20, false, 2, 10, 1));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(880, 100,(int)(50*bonusModifier), (int)(10*bonusModifier), 2, 20, false, 2, 10, 2));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);
                // Draws walls
                g.setColor(Color.BLACK);
                g.fillRect(160, 88, 305, 482);
                walls[6].testPlayerCollision();
                g.fillRect(558, 88, 300, 482);
                walls[7].testPlayerCollision();
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage2b){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                           
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(877, 512,(int)(50*bonusModifier), (int)(10*bonusModifier), 1, 30, false, 3, 10, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(73, 177,(int)(50*bonusModifier), (int)(10*bonusModifier), 1, 30, false, 1, 10, 1));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(169, 100,(int)(50*bonusModifier), (int)(10*bonusModifier), 1, 30, false, 2, 10, 2));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(791, 100,(int)(50*bonusModifier), (int)(10*bonusModifier), 1, 30, false, 2, 10, 3));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);
                // Draws walls
                g.setColor(Color.BLACK);
                g.fillRect(239, 234, 548, 277);    
                walls[8].testPlayerCollision();
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage2c){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                            
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(92, 250,(int)(50*bonusModifier), (int)(10*bonusModifier), 500, true, 2, 8, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(860, 250,(int)(50*bonusModifier), (int)(10*bonusModifier), 500, true, 2, 8, 1));                  
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage2d){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                            
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(677, 100,(int)(50*bonusModifier), (int)(10*bonusModifier), 1, 30, true, 3, 2, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(488, 250,(int)(50*bonusModifier), (int)(10*bonusModifier), 500, true, 2, 3, 1));                  
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        if(stage2e){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = true;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                            
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(488, 300,(int)(300*bonusModifier), (int)(7*bonusModifier), 6, 9, true, 2, 5, 0));
                                      
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage3a){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                            
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(120, 173,(int)(50*bonusModifier), (int)(10*bonusModifier), 2, 6, true, 2, 10, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(855, 497,(int)(50*bonusModifier), (int)(10*bonusModifier), 1, 5, true, 2, 10, 1));        
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(388, 100,(int)(120*bonusModifier), (int)(7*bonusModifier), 5, 13, true, 2, 1, 2));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage3b){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                            
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(488, 200,(int)(80*bonusModifier), (int)(15*bonusModifier), 400, true, 2, 5, 0));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);
                // Draws walls
                g.setColor(Color.BLACK);  
                g.fillRect(-1, 89, 286, 140);    
                walls[9].testPlayerCollision();
                g.fillRect(721, 89, 304, 140);    
                walls[10].testPlayerCollision();
                g.fillRect(-1, 498, 286, 142);    
                walls[11].testPlayerCollision();
                g.fillRect(721, 498, 304, 142);    
                walls[12].testPlayerCollision();
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage3c){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                            
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(89, 570,(int)(80*bonusModifier), (int)(15*bonusModifier), 400, true, 2, 6, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(850, 107,(int)(80*bonusModifier), (int)(15*bonusModifier), 400, true, 2, 7, 1));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(290, 107,(int)(50*bonusModifier), (int)(10*bonusModifier), 1, 5, true, 2, 1, 2));  
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage3d){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                                                
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(89, 160,(int)(100*bonusModifier), (int)(6*bonusModifier), 4, 15, true, 2, 7, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(300, 110,(int)(100*bonusModifier), (int)(6*bonusModifier), 5, 10, true, 2, 2, 1));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(850, 500,(int)(100*bonusModifier), (int)(6*bonusModifier), 3, 8, true, 2, 6, 2));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage3e){
            if(!inventoryScreen){
                if(initialize){                    
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = true;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);             
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(488, 107,(int)(400*bonusModifier), (int)(13*bonusModifier), 400, true, 2, 5, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(120, 173,(int)(Integer.MAX_VALUE), (int)(10*bonusModifier), 2, 12, true, 2, 10, 1));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(855, 497,(int)(Integer.MAX_VALUE), (int)(10*bonusModifier), 3, 9, true, 2, 10, 2));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                                           
                
                if(enemies.length == 2){
                    wipeGameArrays();
                }
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage4a){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                                                
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(100, 173,(int)(50*bonusModifier), (int)(10*bonusModifier), 3, 30, false, 1, 10, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(100, 497,(int)(50*bonusModifier), (int)(10*bonusModifier), 2, 25, false, 1, 10, 1));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(875, 173,(int)(50*bonusModifier), (int)(10*bonusModifier), 3, 30, false, 3, 10, 2));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(875, 497,(int)(50*bonusModifier), (int)(10*bonusModifier), 2, 25, false, 3, 10, 3));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                // Draws water
                g.setColor(new Color(0, 109, 240));
                g.fillRect(186, 84, 231, 600);                
                g.fillRect(608, 84, 231, 600);
                walls[13].testPlayerCollision();
                walls[14].testPlayerCollision();
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        if(stage4b){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                                                
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(100, 173,(int)(60*bonusModifier), (int)(12*bonusModifier), 3, 20, true, 1, 10, 0));                    
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(875, 373,(int)(60*bonusModifier), (int)(12*bonusModifier), 2, 35, true, 3, 10, 1));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(488, 107,(int)(100*bonusModifier), (int)(13*bonusModifier), 400, true, 2, 2, 2));                   
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                // Draws water
                g.setColor(new Color(0, 109, 240));
                g.fillRect(186, 84, 231, 600);                
                g.fillRect(608, 84, 231, 600);
                walls[13].testPlayerCollision();
                walls[14].testPlayerCollision();
                
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage4c){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                                                
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(100, 100,(int)(60*bonusModifier), (int)(12*bonusModifier), 3, 20, true, 1, 1, 0));                                      
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(150, 507,(int)(100*bonusModifier), (int)(13*bonusModifier), 400, true, 2, 8, 1)); 
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(850, 407,(int)(100*bonusModifier), (int)(13*bonusModifier), 400, true, 2, 8, 2));         
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                // Draws water
                g.setColor(new Color(0, 109, 240));
                g.fillRect(61, 177, 897, 100);                              
                walls[15].testPlayerCollision();
                walls[16].testPlayerCollision();
                
                // If the room isn't complete, the path will be blocked by water,
                // but a bridge will appear if the room is complete.
                if(!roomComplete){
                    walls[17].testPlayerCollision();
                }
                else{
                    g.setColor(new Color(120, 42, 0));
                    g.fillRect(464, 177, 95, 100);           
                }
                               
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage4d){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                                                
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(100, 270,(int)(50*bonusModifier), (int)(10*bonusModifier), 5, 35, false, 1, 10, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(100, 428,(int)(50*bonusModifier), (int)(10*bonusModifier), 4, 25, false, 1, 10, 1));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(788, 100,(int)(120*bonusModifier), (int)(7*bonusModifier), 6, 15, true, 2, 1, 2));
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                
                // Water
                g.setColor(new Color(0, 109, 240));
                g.fillRect(171, 177, 136, 632);                                
                g.fillRect(303, 495, 534, 70);
                g.fillRect(430, 340, 534, 70);
                g.fillRect(303, 177, 534, 70);
                walls[18].testPlayerCollision();
                walls[19].testPlayerCollision();
                walls[20].testPlayerCollision();
                walls[21].testPlayerCollision();               
                               
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage4e){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = true;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                                                
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(188, 100,(int)(200*bonusModifier), (int)(10*bonusModifier), 3, 35, true, 1, 9, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(488, 100,(int)(400*bonusModifier), (int)(13*bonusModifier), 300, true, 2, 5, 1));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(788, 100,(int)(300*bonusModifier), (int)(7*bonusModifier), 4, 15, true, 2, 2, 2));
                    totalHealth = enemies[0].getHealth()+enemies[1].getHealth()+enemies[2].getHealth();
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                                                          
                               
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage5a){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                                                                
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(188, 507,(int)(100*bonusModifier), (int)(13*bonusModifier), 400, true, 2, 4, 0)); 
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(788, 507,(int)(100*bonusModifier), (int)(13*bonusModifier), 400, true, 2, 6, 1)); 
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(488, 107,(int)(100*bonusModifier), (int)(13*bonusModifier), 400, true, 2, 5, 2)); 
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                                                               
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage5b){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);    
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(100, 100,(int)(50*bonusModifier), (int)(10*bonusModifier), 2, 35, true, 1, 1, 0));          
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(300, 100,(int)(50*bonusModifier), (int)(10*bonusModifier), 1, 35, true, 1, 3, 1));          
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(600, 100,(int)(50*bonusModifier), (int)(10*bonusModifier), 3, 35, true, 1, 2, 2));   
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(800, 100,(int)(50*bonusModifier), (int)(10*bonusModifier), 2, 35, true, 1, 4, 3));          
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                                                               
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage5c){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);    
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(300, 100,(int)(50*bonusModifier), (int)(10*bonusModifier), 2, 35, true, 1, 1, 0));    
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(188, 507,(int)(100*bonusModifier), (int)(13*bonusModifier), 400, true, 2, 4, 1)); 
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(788, 507,(int)(100*bonusModifier), (int)(13*bonusModifier), 400, true, 2, 6, 2));    
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(855, 100,(int)(90*bonusModifier), (int)(8*bonusModifier), 3, 35, true, 1, 10, 3));    
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                                                               
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        if(stage5d){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = false;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);            
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(130, 380,(int)(100*bonusModifier), (int)(8*bonusModifier), 4, 15, true, 2, 7, 0));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(200, 100,(int)(100*bonusModifier), (int)(8*bonusModifier), 5, 10, true, 2, 2, 1));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(850, 500,(int)(100*bonusModifier), (int)(8*bonusModifier), 3, 8, true, 2, 6, 2));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(488, 107,(int)(110*bonusModifier), (int)(13*bonusModifier), 400, true, 2, 6, 3));                               
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                            
                                                               
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
        
        // Final boss battle
        if(stage5e){
            if(!inventoryScreen){
                if(initialize){
                    player.setHealth(player.getMaxHealth());
                    wipeGameArrays();
                    boss = true;
                    double bonusModifier = (difficultyModifier*stageModifier*roomModifier);                                
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(488, 107,(int)(600*bonusModifier), (int)(15*bonusModifier), 300, true, 2, 5, 0));
                    enemies[0].setType(3);
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(120, 173,(int)(80*bonusModifier), (int)(9*bonusModifier), 4, 28, true, 2, 10, 1));
                    enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(855, 497,(int)(80*bonusModifier), (int)(9*bonusModifier), 3, 35, true, 2, 10, 2));
                    spawnWave = 0;                   
                    initialize = false;
                    resetPlayerPos();
                    roomComplete = false; 
                }                                                                                                                                   
                // When the boss is defeated, clear the room
                if(enemies.length > 0){
                    if(!(enemies[0].getIdentifier()==0)){
                        wipeGameArrays();
                    }    
                }
                if(enemies.length>0){
                    // Spawn enemies everytime the boss' health goes down 25%           
                    if(((double)enemies[0].getHealth() / (double)enemies[0].getMaxHealth()<0.75) && spawnWave == 0){
                        double bonusModifier = (difficultyModifier*stageModifier*roomModifier);       
                        enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(140, 100,(int)(80*bonusModifier), (int)(9*bonusModifier), 4, 28, true, 2, 1, 3));
                        enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(800, 584,(int)(80*bonusModifier), (int)(9*bonusModifier), 3, 35, true, 2, 1, 4));
                        spawnWave++;
                    }
                    else if(((double)enemies[0].getHealth() / (double)enemies[0].getMaxHealth()<0.5) && spawnWave == 1){
                        double bonusModifier = (difficultyModifier*stageModifier*roomModifier);       
                        enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(788, 570,(int)(100*bonusModifier), (int)(13*bonusModifier), 500, true, 2, 4, 5)); 
                        enemies = arrayManager.insertEnemy(enemies, enemies.length, new Melee(188, 107,(int)(100*bonusModifier), (int)(13*bonusModifier), 500, true, 2, 6, 6));
                        spawnWave++;
                    }
                    else if(((double)enemies[0].getHealth() / (double)enemies[0].getMaxHealth()<0.25) && spawnWave == 2){
                        double bonusModifier = (difficultyModifier*stageModifier*roomModifier);       
                        enemies = arrayManager.insertEnemy(enemies, enemies.length, new Archer(120, 173,(int)(80*bonusModifier), (int)(9*bonusModifier), 5, 30, true, 2, 8, 7));
                        enemies = arrayManager.insertEnemy(enemies, enemies.length, new Mage(855, 497,(int)(80*bonusModifier), (int)(9*bonusModifier), 4, 37, true, 2, 8, 8));
                        spawnWave++;
                    }
                }
                if(pressedEscape){                
                    allScreensOff();
                    stageSelect = true;
                    roomExited = true;
                    wipeGameArrays();
                    pressedEscape = false;
                }                          
                
                playGame(g);                
            }
            else{
                goToInventory(g);
            }
            repaint();
        }
    }
    
    // Fetches the given image from the PNG folder
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
    
    // Draws the player on the screen based on position and player direction
    public void drawPlayer(Graphics g){
        AffineTransform at = AffineTransform.getTranslateInstance(player.getX(),player.getY());
        int angle = 0;
        
        // Changes rotation angle based on direction
        switch(player.getDirection()){
            case(0):
                angle = 180;
                break;
            case(1):
                angle = 270;
                break;
            case(2):
                angle = 0;
                break;
            case(3):
                angle = 90;
                break;
        }
        BufferedImage player = getBufferedImage("player.png");
        at.rotate(Math.toRadians(angle),player.getWidth()/2,player.getHeight()/2);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(player, at, null);
    }
    
    // Allows the player to fire projectiles when space is held.
    public void fire(){
        if(player.firing){
            if(player.f==0){
                player.fireStart = System.currentTimeMillis();   
                // Creates a projectile based on the player's position, direction,
                // arrow speed, and damage
                arrows = arrayManager.insertProjectile(arrows, 0, new Projectile(player.getX(), player.getY(), player.getDamage(), equipped[0].getStat3(), player.getDirection(), false, 0));   
                playSound("bowShoot.wav");
            }
            player.f++;
            long end = System.currentTimeMillis();
            if(end-player.fireStart>=fireRate){                
                player.f=0;
            }
            
        }
    }
    
    // Draws all the projectiles from the arrows array on the screen and tests collisoion
    public void drawArrows(Graphics g){        
        for(int i = 0; i<arrows.length; i++){
            
            // Move the arrow
            arrows[i].move();
            
            AffineTransform at = AffineTransform.getTranslateInstance(arrows[i].getX(),arrows[i].getY());
            int angle = 0;
        
            switch(arrows[i].getDirection()){
                case(0):
                    angle = 180;
                    break;
                case(1):
                    angle = 270;
                    break;
                case(2):
                    angle = 0;
                    break;
                case(3):
                    angle = 90;
                    break;
            }
            // Get the correct image based on the projectile's identifier
            BufferedImage projectile = getBufferedImage("arrow.png");
            if(arrows[i].getIdentifier()==0){
                projectile = getBufferedImage("arrow.png");
            }
            else{
                projectile = getBufferedImage("magic.png");
            }
            
            at.rotate(Math.toRadians(angle),projectile.getWidth()/2,projectile.getHeight()/2);        
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(projectile, at, null);            
            
            //Test Arrow Collision
            //Delete arrows when they go off screen
            if(arrows[i].getX()<=0||arrows[i].getX()>=1024||arrows[i].getY()<=0||arrows[i].getY()>=768&&i>=0){                
                arrows = arrayManager.deleteProjectile(arrows, i);
                i--;
            }
            if(i>=0){   
                // arrow hits enemy and was fired from the player
                if(!arrows[i].getHurtPlayer()){
                    // Checks for every enemy in the array
                    for(int j = 0; j < enemies.length; j++){                       
                        try{
                            if(arrows[i].getX()>=enemies[j].getX()-8&&arrows[i].getX()<=enemies[j].getX()+50&&arrows[i].getY()>=enemies[j].getY()-10&&arrows[i].getY()<=enemies[j].getY()+43){                                                                                                     
                                playSound("hit.wav");

                                enemies[j].setHealth(enemies[j].getHealth() - arrows[i].getDamage());
                                arrows = arrayManager.deleteProjectile(arrows, i);  
                                i--;
                                if(enemies[j].getHealth()<=0){
                                    // Deletes enemy from the array
                                    enemies = arrayManager.deleteEnemy(enemies, arrayManager.findByValue(enemies ,enemies[j].getIdentifier()));
                                    coins++;                                                                        
                                }
                            }    
                        }catch(Exception e){}                                               
                    }
                }
                // Arrow hits player and was fired from an enemy
                else{
                    if(arrows[i].getX()>=player.getX()-5&&arrows[i].getX()<=player.getX()+51&&arrows[i].getY()>=player.getY()-7&&arrows[i].getY()<=player.getY()+51){                                        
                        // Decrease player health by the enemy's damage and the player's defence
                        player.setHealth((player.getHealth() - (int)((double)arrows[i].getDamage()*(1 - (((double)player.getDefence())/((double)player.getDefence()+100))))));                        
                        playSound("hit.wav");
                        arrows = arrayManager.deleteProjectile(arrows, i);
                        i--;
                    }
                }
            }    
            // Delete arrows if they go into the walls on certain stages
            if(i>=0){
                if(stage2a){
                    if(arrows[i].getX()>=160&&arrows[i].getX()<=465&&arrows[i].getY()>=-1&&arrows[i].getY()<=570){
                        arrows = arrayManager.deleteProjectile(arrows, i);
                        i--;
                    }
                    else if(arrows[i].getX()>=558&&arrows[i].getX()<=858&&arrows[i].getY()>=-1&&arrows[i].getY()<=570){
                        arrows = arrayManager.deleteProjectile(arrows, i);
                        i--;
                    }
                }
                else if(stage2b){
                    if(arrows[i].getX()>=239&&arrows[i].getX()<=787&&arrows[i].getY()>=234&&arrows[i].getY()<=511){
                        arrows = arrayManager.deleteProjectile(arrows, i);
                        i--;
                    }
                }
            }
        }
    }
    
    // Draws the borders, player health bar, coins, and sets up the playerCollisonBoxes
    // along the border of the screen
    public void setupGame(Graphics g){
        g.setColor(Color.BLACK);
        
        // Left wall
        g.fillRect(-1, -1, 70, 769);
        walls[0].testPlayerCollision();
        
        // Top wall and door
        g.fillRect(-1, -1, 1026, 90);
        walls[3].testPlayerCollision();
        walls[4].testPlayerCollision();
        if(roomComplete){
            g.drawImage(getBufferedImage("openDoor.png"),477 ,36, this);
            if(player.getY()<60){
                allScreensOff();
                nextScreen();
            }
        }
        else{
            g.drawImage(getBufferedImage("closedDoor.png"),477 ,36, this);
            walls[5].testPlayerCollision();
        }
                
        //Right wall
        g.fillRect(954, -1, 71, 769);
        walls[1].testPlayerCollision();
        
        //Bottom wall
        g.fillRect(-1, 640, 1026, 769);
        walls[2].testPlayerCollision();
        
        //Coin and coin count
        g.drawImage(getBufferedImage("coin.png"),740 ,665, this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,90));
        if(coins>999){
            g.drawString("999+",800,735);
        }
        else{
            g.drawString(coins+"",800,735);
        }        
        
        // Health bar
        int healthBarRemaining = (int)Math.ceil(((double)player.getHealth() / (double)player.getMaxHealth()) * 630);
        g.setColor(Color.RED);
        g.fillRect(70, 660, healthBarRemaining, 80);
        g.drawImage(getBufferedImage("healthbar.png"),46 ,650, this);        
    }
    
    // Draws enemies based on their position and direction, and type. Also draws 
    // health bars for enemies and bosses
    public void drawEnemy(Enemy enemy, Graphics g, int i){
        AffineTransform at = AffineTransform.getTranslateInstance(enemy.getX(), enemy.getY());
        int angle = 0;
        // Change rotation angle based on direction
        switch(enemy.getDirection()){
            case(0):
                angle = 180;
                break;
            case(1):
                angle = 270;
                break;
            case(2):
                angle = 0;
                break;
            case(3):
                angle = 90;
                break;
        }        
        // Get image based on type
        BufferedImage image = getBufferedImage("player.png");
        switch(enemies[i].getType()){
            case(0):
                image = getBufferedImage("archer.png");
                break;           
            case(1):
                image = getBufferedImage("mage.png");
                break;
            case(2):
                image = getBufferedImage("melee.png");
                break;
            case(3):
                image = getBufferedImage("boss.png");
                break;
        }
        
        at.rotate(Math.toRadians(angle),image.getWidth()/2,image.getHeight()/2);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, at, null);
        
        //Draw health bar and boss health bars
        // Regular health bars
        if(!boss&&!stage4e){
            g.setColor(Color.RED);
            g.fillRect(enemy.getX(), enemy.getY()+55, 51, 5);

            int healthBarRemaining = (int)Math.ceil(((double)enemy.getHealth() / (double)enemy.getMaxHealth()) * 51);        
            g.setColor(Color.GREEN);
            g.fillRect(enemy.getX(), enemy.getY()+55, healthBarRemaining, 5);
        }
        // Draws boss health bar based on all three on the mini bosses
        else if(stage4e){
            g.setColor(Color.RED);
            g.fillRect(170, 38, 683, 50);
            int healthBarRemaining = 0;            
            switch(enemies.length){
                case(1):
                    healthBarRemaining = (int)Math.ceil(((double)enemies[0].getHealth() / (double)totalHealth) * 683); 
                    break;
                case(2):
                    healthBarRemaining = (int)Math.ceil(((double)(enemies[0].getHealth()+enemies[1].getHealth()) / (double)totalHealth) * 683); 
                    break;
                case(3):
                    healthBarRemaining = (int)Math.ceil((((double)(enemies[0].getHealth()+enemies[1].getHealth()+enemies[2].getHealth())) / (double)totalHealth) * 683); 
                    break;
            }             
            g.setColor(Color.GREEN);
            g.fillRect(170, 38, healthBarRemaining, 50);      
            
            g.setColor(Color.RED);
            g.fillRect(enemy.getX(), enemy.getY()+55, 51, 5);
            healthBarRemaining = (int)Math.ceil(((double)enemy.getHealth() / (double)enemy.getMaxHealth()) * 51);        
            g.setColor(Color.GREEN);
            g.fillRect(enemy.getX(), enemy.getY()+55, healthBarRemaining, 5);
        }
        // Draw boss health bar, and enemy health bars
        else if(stage5e){
            if(enemies.length>0){
                // Draw boss health bar
                g.setColor(Color.RED);
                g.fillRect(170, 38, 683, 50);
                int healthBarRemaining = (int)Math.ceil(((double)enemies[0].getHealth() / (double)enemies[0].getMaxHealth()) * 683);        
                g.setColor(Color.GREEN);
                g.fillRect(170, 38, healthBarRemaining, 50);

                // Draw enemy health bars
                g.setColor(Color.RED);
                g.fillRect(enemy.getX(), enemy.getY()+55, 51, 5);

                healthBarRemaining = (int)Math.ceil(((double)enemy.getHealth() / (double)enemy.getMaxHealth()) * 51);        
                g.setColor(Color.GREEN);
                g.fillRect(enemy.getX(), enemy.getY()+55, healthBarRemaining, 5);
            }
        }
        // Draws boss health bar
        else{
            g.setColor(Color.RED);
            g.fillRect(170, 38, 683, 50);
            int healthBarRemaining = (int)Math.ceil(((double)enemies[0].getHealth() / (double)enemies[0].getMaxHealth()) * 683);        
            g.setColor(Color.GREEN);
            g.fillRect(170, 38, healthBarRemaining, 50);
        }
    }
    
    // Calls the methods for every enemy to allow movement, and attack. Also draws
    // the enemy
    public void enemyActions(Graphics g){
        for(int i = 0; i<enemies.length; i++){
            enemies[i].move(player);
            arrows = enemies[i].attack(arrows, player, g);
            drawEnemy(enemies[i], g, i);
        }
    }
    
    // Checks if the room is complete based on the length of the enemies array.
    public void checkRoomCompletion(){
        if(enemies.length == 0 && !tutorial2){
            if(!roomComplete){
                roomComplete = true;      
                // Gives gear if the room wasn't exited
                if(!roomExited&&!tutorial1){
                    playSound("roomComplete.wav");
                    dropGear();
                    wipeGameArrays();                    
                }
                // Drops gear for the first time the tutorial is completed
                if(tutorial1&&firstTutorial){
                    playSound("roomComplete.wav");
                    stageModifier = 1;
                    difficultyModifier = 1;
                    roomModifier = 1;
                    dropGear();
                    wipeGameArrays();   
                    firstTutorial = false;
                }
            }           
        }
    }
    
    // Turn off all screens that could be running. Used primarily for when the player dies,
    // and when the room changes.
    public void allScreensOff(){
        test = false;
        titleScreen = false;
        saveScreen = false;
        inventoryScreen = false;
        stageSelect = false;   
        shop = false;
        stage1a = false;
        stage1b = false;
        stage1c = false;
        stage1d = false;
        stage1e = false;
        stage2a = false;
        stage2b = false;
        stage2c = false;
        stage2d = false;
        stage2e = false;
        stage3a = false;
        stage3b = false;
        stage3c = false;
        stage3d = false;
        stage3e = false;
        stage4a = false;
        stage4b = false;
        stage4c = false;
        stage4d = false;
        stage4e = false;
        stage5a = false;
        stage5b = false;
        stage5c = false;
        stage5d = false;
        stage5e = false;
        died = false;
        tutorial1 = false;
        tutorial2 = false;
    }
    
    // Wipes areas. Primarily used for when the player dies, or a room is completed
    public void wipeGameArrays(){
        for(int i = 0; i < arrows.length; i++){
            arrows = new Projectile[0];
        }
        for(int i = 0; i < enemies.length; i++){
            enemies = new Enemy[0];
        }        
    }
    
    // Called when the player enters a door at the end of a room. Changes the screen
    // to the next room or stage select screen based on the room number. Increases
    // room modifier for enemy and gear values.
    public void nextScreen(){        
        boss = false;
        initialize = true;                
        player.setHealth(player.getMaxHealth());
        allScreensOff();
        switch(roomNumber){
            case(-2):
                tutorial2 = true;
                roomNumber++;
                break;
            case(-1):
                stageSelect = true;
                break;
            case(0):
                titleScreen = true;
                break;
            case(1):
                stage1b = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(2):
                stage1c = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(3):
                stage1d = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(4):
                stage1e = true;  
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(5):
                stageSelect = true;  
                shopModifier = stageModifier*difficultyModifier*roomModifier;
                rollShop();
                saveProgress();
                break;
            case(6):
                stage2b = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(7):
                stage2c = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(8):
                stage2d = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(9):
                stage2e = true;  
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(10):
                stageSelect = true;    
                shopModifier = stageModifier*difficultyModifier*roomModifier;
                rollShop();
                saveProgress();
                break;
            case(11):
                stage3b = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(12):
                stage3c = true;
                roomNumber++;
                roomModifier += 0.05;
                break;
            case(13):
                stage3d = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(14):
                stage3e = true;  
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(15):
                stageSelect = true;  
                shopModifier = stageModifier*difficultyModifier*roomModifier;
                rollShop();
                saveProgress();
                break;
            case(16):
                stage4b = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(17):
                stage4c = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(18):
                stage4d = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(19):
                stage4e = true;  
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(20):
                stageSelect = true;   
                shopModifier = stageModifier*difficultyModifier*roomModifier;
                rollShop();
                saveProgress();
                break;
            case(21):
                stage5b = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(22):
                stage5c = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(23):
                stage5d = true;
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(24):
                stage5e = true;  
                roomNumber++;
                roomModifier += 0.1;
                break;
            case(25):
                stageSelect = true;    
                shopModifier = stageModifier*difficultyModifier*roomModifier;
                rollShop();
                saveProgress();
                break;
            default:
                titleScreen = true;
                break;
        }
    }
    
    // Loads the inventory screen
    public void goToInventory(Graphics g){
        if(inventoryScreen){      
            // Draws stat values
            g.setColor(Color.BLACK);    
            g.setFont(new Font("Arial",Font.BOLD,70));
            g.drawString("Inventory",360,100);
            g.setFont(new Font("Arial",Font.BOLD,50));
            g.drawString("Stats",300,200);            
            
            g.setFont(new Font("Arial",Font.BOLD,30));                       
            g.drawString("Max Health: "+player.getMaxHealth(),250,300);            
            g.drawString("Defence: "+player.getDefence(),275,370);
            g.setFont(new Font("Arial",Font.BOLD,22));      
            g.drawString("Damage Resistance: "+f1.format((double)player.getDefence()/(double)(player.getDefence()+100)*100)+"%",225,400);
            g.setFont(new Font("Arial",Font.BOLD,30));      
            g.drawString("Attack: "+player.getDamage(),285,470);
            
            // Draws sort method
            switch(sortMethod){
                case(0):
                    sortMessage = "Order Acquired";
                    break;
                case(1):
                    sortMessage = "Item Type";
                    break;
                case(2):
                    sortMessage = "Rarity";
                    break;
            }
            
            g.setFont(new Font("Arial",Font.BOLD,25));
            g.drawString("Sort By: "+sortMessage,690,140);
            
            // Changes sort method and sorts the inventory when the text is clicked
            if(mouseClickedX >= 690 && mouseClickedX <=975 && mouseClickedY >=118 && mouseClickedY <= 142){
                switch(sortMethod){
                case(0):
                    sortMethod = 1;
                    saveProgress();
                    break;
                case(1):
                    sortMethod = 2;
                    saveProgress();
                    break;
                case(2):
                    sortMethod = 0;
                    saveProgress();
                    break;
                }
                sortInventory();
                resetMousePos();
            }            
                             
            // Coin count
            g.drawImage(getBufferedImage("coin.png"),80 ,655, this);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial",Font.BOLD,90));
            g.drawString(coins+"",140,725);
            
            g.setFont(new Font("Arial",Font.BOLD,25));
            g.drawString("Click to Equip",684,680);
            g.drawString("Press F to Sell",681,720);
            
            // Draws Gear that is equipped and in the inventory
            // Equipped
            try{
                drawGear(80, 150, equipped[0], g);
            }catch(Exception e){}
            g.drawRect(80, 150, 70, 70);                                               
            try{
                drawGear(80, 250, equipped[1], g);
            }catch(Exception e){}
            g.drawRect(80, 250, 70, 70);
            try{
                drawGear(80, 350, equipped[2], g);
            }catch(Exception e){}
            g.drawRect(80, 350, 70, 70);
            try{
                drawGear(80, 450, equipped[3], g);
            }catch(Exception e){}
            g.drawRect(80, 450, 70, 70);
            try{
                drawGear(80, 550, equipped[4], g);
            }catch(Exception e){}
            g.drawRect(80, 550, 70, 70);

            // Row 1           
            try{
                drawGear(580, 150, inventory[0], g);
            }catch(Exception e){}
            g.drawRect(580, 150, 70, 70);
            try{
                drawGear(660, 150, inventory[1], g);
            }catch(Exception e){}
            g.drawRect(660, 150, 70, 70);
            try{
                drawGear(740, 150, inventory[2], g);
            }catch(Exception e){}
            g.drawRect(740, 150, 70, 70);
            try{
                drawGear(820, 150, inventory[3], g);
            }catch(Exception e){}
            g.drawRect(820, 150, 70, 70);
            try{
                drawGear(900, 150, inventory[4], g);
            }catch(Exception e){}
            g.drawRect(900, 150, 70, 70);
            
            //Row 2    
            try{
                drawGear(580, 230, inventory[5], g);
            }catch(Exception e){}
            g.drawRect(580, 230, 70, 70);
            try{
                drawGear(660, 230, inventory[6], g);
            }catch(Exception e){}
            g.drawRect(660, 230, 70, 70);
            try{
                drawGear(740, 230, inventory[7], g);
            }catch(Exception e){}
            g.drawRect(740, 230, 70, 70);
            try{
                drawGear(820, 230, inventory[8], g);
            }catch(Exception e){}
            g.drawRect(820, 230, 70, 70);
            try{
                drawGear(900, 230, inventory[9], g);
            }catch(Exception e){}
            g.drawRect(900, 230, 70, 70);
            
            //Row 3            
            try{
                drawGear(580, 310, inventory[10], g);
            }catch(Exception e){}
            g.drawRect(580, 310, 70, 70);
            try{
                drawGear(660, 310, inventory[11], g);
            }catch(Exception e){}
            g.drawRect(660, 310, 70, 70);
            try{
                drawGear(740, 310, inventory[12], g);
            }catch(Exception e){}
            g.drawRect(740, 310, 70, 70);
            try{
                drawGear(820, 310, inventory[13], g);
            }catch(Exception e){}
            g.drawRect(820, 310, 70, 70);
            try{
                drawGear(900, 310, inventory[14], g);
            }catch(Exception e){}
            g.drawRect(900, 310, 70, 70);
            
            //Row 4       
            try{
                drawGear(580, 390, inventory[15], g);
            }catch(Exception e){}
            g.drawRect(580, 390, 70, 70);
            try{
                drawGear(660, 390, inventory[16], g);
            }catch(Exception e){}
            g.drawRect(660, 390, 70, 70);
            try{
                drawGear(740, 390, inventory[17], g);
            }catch(Exception e){}
            g.drawRect(740, 390, 70, 70);
            try{
                drawGear(820, 390, inventory[18], g);
            }catch(Exception e){}
            g.drawRect(820, 390, 70, 70);
            try{
                drawGear(900, 390, inventory[19], g);
            }catch(Exception e){}
            g.drawRect(900, 390, 70, 70);
            
            //Row 5            
            try{
                drawGear(580, 470, inventory[20], g);
            }catch(Exception e){}
            g.drawRect(580, 470, 70, 70);
            try{
                drawGear(660, 470, inventory[21], g);
            }catch(Exception e){}
            g.drawRect(660, 470, 70, 70);
            try{
                drawGear(740, 470, inventory[22], g);
            }catch(Exception e){}
            g.drawRect(740, 470, 70, 70);            
            try{
                drawGear(820, 470, inventory[23], g);
            }catch(Exception e){}            
            g.drawRect(820, 470, 70, 70);
            try{
                drawGear(900, 470, inventory[24], g);
            }catch(Exception e){}
            g.drawRect(900, 470, 70, 70);
                       
            //Row 6            
            try{
                drawGear(580, 550, inventory[25], g);
            }catch(Exception e){}
            g.drawRect(580, 550, 70, 70);
            try{
                drawGear(660, 550, inventory[26], g);
            }catch(Exception e){}
            g.drawRect(660, 550, 70, 70);
            try{
                drawGear(740, 550, inventory[27], g);
            }catch(Exception e){}
            g.drawRect(740, 550, 70, 70);
            try{
                drawGear(820, 550, inventory[28], g);
            }catch(Exception e){}
            g.drawRect(820, 550, 70, 70);
            try{
                drawGear(900, 550, inventory[29], g);
            }catch(Exception e){}
            g.drawRect(900, 550, 70, 70);
        
            // Play sounds and show stat of gear when the mouse is over the item. If the item
            // is in the inventory, the sellGear() and swapGear() methods are called.
            if(mouseMovedX >= 80 && mouseMovedX <=150 && mouseMovedY >=150 && mouseMovedY <= 220){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }
                try{
                    drawGearStats(false, equipped[0], g);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 80 && mouseMovedX <=150 && mouseMovedY >=250 && mouseMovedY <= 320){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }            
                try{
                    drawGearStats(false, equipped[1], g);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 80 && mouseMovedX <=150 && mouseMovedY >=350 && mouseMovedY <= 420){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }            
                try{
                    drawGearStats(false, equipped[2], g);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 80 && mouseMovedX <=150 && mouseMovedY >=450 && mouseMovedY <= 520){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }            
                try{
                    drawGearStats(false, equipped[3], g);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 80 && mouseMovedX <=150 && mouseMovedY >=550 && mouseMovedY <= 620){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }          
                try{
                    drawGearStats(false, equipped[4], g);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 580 && mouseMovedX <=650 && mouseMovedY >=150 && mouseMovedY <= 220){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }  
                try{
                    drawGearStats(true, inventory[0], g);
                }catch(Exception e){}
                try{
                    swapGear(0);
                }catch(Exception e){}
                try{
                    sellGear(0);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 660 && mouseMovedX <=730 && mouseMovedY >=150 && mouseMovedY <= 220){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }                  
                try{
                    drawGearStats(true, inventory[1], g);
                }catch(Exception e){}
                try{
                    swapGear(1);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(1);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 740 && mouseMovedX <=810 && mouseMovedY >=150 && mouseMovedY <= 220){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }            
                try{
                    drawGearStats(true, inventory[2], g);
                }catch(Exception e){}
                try{
                    swapGear(2);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(2);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 820 && mouseMovedX <=890 && mouseMovedY >=150 && mouseMovedY <= 220){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }        
                try{
                    drawGearStats(true, inventory[3], g);
                }catch(Exception e){}
                try{
                    swapGear(3);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(3);
                }catch(Exception e){}
            }            
            else if(mouseMovedX >= 900 && mouseMovedX <=970 && mouseMovedY >=150 && mouseMovedY <= 220){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }    
                try{
                    drawGearStats(true, inventory[4], g);
                }catch(Exception e){}
                try{
                    swapGear(4);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(4);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 580 && mouseMovedX <=650 && mouseMovedY >=230 && mouseMovedY <= 300){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }          
                try{
                    drawGearStats(true, inventory[5], g);
                }catch(Exception e){}
                try{
                    swapGear(5);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(5);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 660 && mouseMovedX <=730 && mouseMovedY >=230 && mouseMovedY <= 300){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }     
                try{
                    drawGearStats(true, inventory[6], g);
                }catch(Exception e){}
                try{
                    swapGear(6);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(6);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 740 && mouseMovedX <=810 && mouseMovedY >=230 && mouseMovedY <= 300){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }       
                try{
                    drawGearStats(true, inventory[7], g);
                }catch(Exception e){}
                try{
                    swapGear(7);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(7);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 820 && mouseMovedX <=890 && mouseMovedY >=230 && mouseMovedY <= 300){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }            
                try{
                    drawGearStats(true, inventory[8], g);
                }catch(Exception e){}
                try{
                    swapGear(8);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(8);
                }catch(Exception e){}
            }            
            else if(mouseMovedX >= 900 && mouseMovedX <=970 && mouseMovedY >=230 && mouseMovedY <= 300){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                } 
                try{
                    drawGearStats(true, inventory[9], g);
                }catch(Exception e){}
                try{
                    swapGear(9);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(9);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 580 && mouseMovedX <=650 && mouseMovedY >=310 && mouseMovedY <= 380){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }    
                try{
                    drawGearStats(true, inventory[10], g);
                }catch(Exception e){}
                try{
                    swapGear(10);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(10);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 660 && mouseMovedX <=730 && mouseMovedY >=310 && mouseMovedY <= 380){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }  
                try{
                    drawGearStats(true, inventory[11], g);
                }catch(Exception e){}
                try{
                    swapGear(11);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(11);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 740 && mouseMovedX <=810 && mouseMovedY >=310 && mouseMovedY <= 380){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }     
                try{
                    drawGearStats(true, inventory[12], g);
                }catch(Exception e){}
                try{
                    swapGear(12);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(12);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 820 && mouseMovedX <=890 && mouseMovedY >=310 && mouseMovedY <= 380){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }           
                try{
                    drawGearStats(true, inventory[13], g);
                }catch(Exception e){}
                try{
                    swapGear(13);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(13);
                }catch(Exception e){}
            }            
            else if(mouseMovedX >= 900 && mouseMovedX <=970 && mouseMovedY >=310 && mouseMovedY <= 380){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }    
                try{
                    drawGearStats(true, inventory[14], g);
                }catch(Exception e){}
                try{
                    swapGear(14);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(14);
                }catch(Exception e){}
            }
            
            else if(mouseMovedX >= 580 && mouseMovedX <=650 && mouseMovedY >=390 && mouseMovedY <= 460){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }          
                try{
                    drawGearStats(true, inventory[15], g);
                }catch(Exception e){}
                try{
                    swapGear(15);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(15);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 660 && mouseMovedX <=730 && mouseMovedY >=390 && mouseMovedY <= 460){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }    
                try{
                    drawGearStats(true, inventory[16], g);
                }catch(Exception e){}
                try{
                    swapGear(16);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(16);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 740 && mouseMovedX <=810 && mouseMovedY >=390 && mouseMovedY <= 460){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }      
                try{
                    drawGearStats(true, inventory[17], g);
                }catch(Exception e){}
                try{
                    swapGear(17);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(17);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 820 && mouseMovedX <=890 && mouseMovedY >=390 && mouseMovedY <= 460){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }      
                try{
                    drawGearStats(true, inventory[18], g);
                }catch(Exception e){}
                try{
                    swapGear(18);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(18);
                }catch(Exception e){}
            }            
            else if(mouseMovedX >= 900 && mouseMovedX <=970 && mouseMovedY >=390 && mouseMovedY <= 460){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }    
                try{
                    drawGearStats(true, inventory[19], g);
                }catch(Exception e){}
                try{
                    swapGear(19);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(19);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 580 && mouseMovedX <=650 && mouseMovedY >=470 && mouseMovedY <= 540){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }      
                try{
                    drawGearStats(true, inventory[20], g);
                }catch(Exception e){}
                try{
                    swapGear(20);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(20);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 660 && mouseMovedX <=730 && mouseMovedY >=470 && mouseMovedY <= 540){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }        
                try{
                    drawGearStats(true, inventory[21], g);
                }catch(Exception e){}
                try{
                    swapGear(21);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(21);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 740 && mouseMovedX <=810 && mouseMovedY >=470 && mouseMovedY <= 540){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }   
                try{
                    drawGearStats(true, inventory[22], g);
                }catch(Exception e){}
                try{
                    swapGear(22);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(22);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 820 && mouseMovedX <=890 && mouseMovedY >=470 && mouseMovedY <= 540){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                } 
                try{
                    drawGearStats(true, inventory[23], g);
                }catch(Exception e){}
                try{
                    swapGear(23);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(23);
                }catch(Exception e){}
            }            
            else if(mouseMovedX >= 900 && mouseMovedX <=970 && mouseMovedY >=470 && mouseMovedY <= 540){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }        
                try{
                    drawGearStats(true, inventory[24], g);
                }catch(Exception e){}
                try{
                    swapGear(24);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(24);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 580 && mouseMovedX <=650 && mouseMovedY >=550 && mouseMovedY <= 620){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }         
                try{
                    drawGearStats(true, inventory[25], g);
                }catch(Exception e){}
                try{
                    swapGear(25);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(25);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 660 && mouseMovedX <=730 && mouseMovedY >=550 && mouseMovedY <= 620){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }       
                try{
                    drawGearStats(true, inventory[26], g);
                }catch(Exception e){}
                try{
                    swapGear(26);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(26);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 740 && mouseMovedX <=810 && mouseMovedY >=550 && mouseMovedY <= 620){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }    
                try{
                    drawGearStats(true, inventory[27], g);
                }catch(Exception e){}
                try{
                    swapGear(27);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(27);
                }catch(Exception e){}
            }
            else if(mouseMovedX >= 820 && mouseMovedX <=890 && mouseMovedY >=550 && mouseMovedY <= 620){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }    
                try{
                    drawGearStats(true, inventory[28], g);
                }catch(Exception e){}
                try{
                    swapGear(28);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(28);
                }catch(Exception e){}
            }            
            else if(mouseMovedX >= 900 && mouseMovedX <=970 && mouseMovedY >=550 && mouseMovedY <= 620){
                if(selectSound){
                    playSound("select.wav");
                    selectSound = false;
                }         
                try{
                    drawGearStats(true, inventory[29], g);
                }catch(Exception e){}
                try{
                    swapGear(29);
                }catch(Exception e){
                    mouseClicked = false;
                }
                try{
                    sellGear(29);
                }catch(Exception e){}
            }
            else{
                selectSound = true;
                mouseClicked = false;
                pressedF = false;
            }
        }
    }
    
    // Draws the gear based on what item it is and it's rarity
    public void drawGear(int x, int y, Gear gear, Graphics g){         
        String image = "";

        switch(gear.getItem()){
            case(0):
                image = "bow.png";
                break;
            case(1):
                image = "helmet.png";
                break;
            case(2):
                image = "chestpiece.png";
                break;
            case(3):
                image = "glove.png";
                break;
            case(4):
                image = "boots.png";
                break;
        }

        switch(gear.getRarity()){
            case(0):
                g.setColor(Color.WHITE);
                break;
            case(1):
                g.setColor(Color.GREEN);
                break;
            case(2):
                g.setColor(Color.BLUE);
                break;
            case(3):
                g.setColor(Color.MAGENTA);
                break;
            case(4):
                g.setColor(Color.ORANGE);
                break;
            case(5):                    
                g.setColor(Color.YELLOW);
                break;
            case(6):                    
                g.setColor(Color.RED);
                break;
        }

        g.fillRect(x, y, 70, 70);
        g.drawImage(getBufferedImage(image),x ,y, this);
        g.setColor(Color.BLACK);                           
    }
    
    // Draws the gear stats at the x and y positions. The boolean variable determines
    // which side the stats are drawn on
    public void drawGearStats(boolean left, Gear gear, Graphics g){
        String item = "";
        String stat1 = "";
        String stat2 = "";
        String stat3 = "";
        String rarity = "";
        
        switch(gear.getItem()){
            case(0):
                item = "Bow";
                stat1 = "Attack: ";
                stat2 = "Rate of Fire: ";
                stat3 = "Arrow Speed: ";
                break;
            case(1):
                item = "Helmet";
                stat1 = "Defence: ";
                stat2 = "Health: ";
                stat3 = "Bonus Attack: ";
                break;
            case(2):
                item = "Chestpiece";
                stat1 = "Defence: ";
                stat2 = "Health: ";
                stat3 = "Bonus Attack: ";
                break;
            case(3):
                item = "Gloves";
                stat1 = "Defence: ";
                stat2 = "Health: ";
                stat3 = "Bonus Attack: ";
                break;
            case(4):
                item = "Boots";
                stat1 = "Defence: ";
                stat2 = "Health: ";
                stat3 = "Bonus Attack: ";
                break;
        }
        
        if(left){                    
            g.setColor(Color.BLACK);
            g.fillRect(mouseMovedX-190, mouseMovedY, 190, 185);
            switch(gear.getRarity()){
                case(0):
                    rarity = "Common";
                    g.setColor(Color.WHITE);
                    break;
                case(1):
                    rarity = "Uncommon";
                    g.setColor(Color.GREEN);
                    break;
                case(2):
                    rarity = "Rare";
                    g.setColor(Color.BLUE);
                    break;
                case(3):
                    rarity = "Epic";
                    g.setColor(Color.MAGENTA);
                    break;
                case(4):
                    rarity = "Legendary";
                    g.setColor(Color.ORANGE);
                    break;
                case(5):
                    rarity = "Unique";
                    g.setColor(Color.YELLOW);
                    break;
                case(6):    
                    rarity = "Dev Item";
                    g.setColor(Color.RED);
                    break;
            }
            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString(rarity,mouseMovedX-180,mouseMovedY+50);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString(item,mouseMovedX-180,mouseMovedY+25);   
            g.drawString(stat1+gear.getStat1(),mouseMovedX-180,mouseMovedY+75);
            g.drawString(stat2+gear.getStat2(),mouseMovedX-180,mouseMovedY+100);   
            g.drawString(stat3+gear.getStat3(),mouseMovedX-180,mouseMovedY+125);
            g.drawString("Sell Value: "+gear.getSellPrice(), mouseMovedX-180, mouseMovedY+150);                                                                
            g.drawString("ID: "+gear.getOrderAcquired(),mouseMovedX-180,mouseMovedY+175);   
                        
            g.drawRect(mouseMovedX-190, mouseMovedY, 190, 185);
            g.setColor(Color.BLACK);
        }
        else{
            g.setColor(Color.BLACK);
            g.fillRect(mouseMovedX, mouseMovedY, 200, 185);
            switch(gear.getRarity()){
                case(0):
                    rarity = "Common";
                    g.setColor(Color.WHITE);
                    break;
                case(1):
                    rarity = "Uncommon";
                    g.setColor(Color.GREEN);
                    break;
                case(2):
                    rarity = "Rare";
                    g.setColor(Color.BLUE);
                    break;
                case(3):
                    rarity = "Epic";
                    g.setColor(Color.MAGENTA);
                    break;
                case(4):
                    rarity = "Legendary";
                    g.setColor(Color.ORANGE);
                    break;
                case(5):
                    rarity = "Unique";
                    g.setColor(Color.YELLOW);
                    break;
                case(6):    
                    rarity = "Dev Item";
                    g.setColor(Color.RED);
                    break;
            }
            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString(rarity,mouseMovedX+10,mouseMovedY+50);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString(item,mouseMovedX+10,mouseMovedY+25);   
            g.drawString(stat1+gear.getStat1(),mouseMovedX+10,mouseMovedY+75);
            g.drawString(stat2+gear.getStat2(),mouseMovedX+10,mouseMovedY+100);   
            g.drawString(stat3+gear.getStat3(),mouseMovedX+10,mouseMovedY+125);           
            g.drawString("Sell Value: "+gear.getSellPrice(), mouseMovedX+10, mouseMovedY+150);                       
            g.drawString("ID: "+gear.getOrderAcquired(),mouseMovedX+10,mouseMovedY+175);   
            
            
            g.drawRect(mouseMovedX, mouseMovedY, 200, 185);
            g.setColor(Color.BLACK);
        }
        
    }
    
    // Changes the equipped item with the item at the specified index from the inventory
    public void swapGear(int index){
        if(mouseClicked){
            int slot = inventory[index].getItem();
            Gear tmp = equipped[slot];
            equipped[slot] = inventory[index];
            inventory[index] = tmp;
            
            sortInventory();
            calculateStats();
            saveProgress();
            mouseClicked = false;
        }
    }
    
    // Deletes the gear at the specified index and adds the sell value of the item to
    // the coin count
    public void sellGear(int index){
        if(pressedF){            
            coins += inventory[index].getSellPrice();                                                   
            inventory = arrayManager.deleteGear(inventory, index);
            pressedF = false;
            sortInventory();
            saveProgress();
        }
    }
    
    // Checks if the player is dead
    public void checkIfDead(){
        if(player.getHealth()<=0){                            
            allScreensOff();
            died = true;
            d = 0;
        }
    }
    
    // Calculates the player's stats based on equipped items
    public void calculateStats(){
        player.setDamage(equipped[0].getStat1()+equipped[1].getStat3()+equipped[2].getStat3()+equipped[3].getStat3()+equipped[4].getStat3());
        player.setDefence(equipped[1].getStat1()+equipped[2].getStat1()+equipped[3].getStat1()+equipped[4].getStat1());
        player.setMaxHealth(equipped[1].getStat2()+equipped[2].getStat2()+equipped[3].getStat2()+equipped[4].getStat2());
        if(player.getMaxHealth()<player.getHealth()){
            player.setHealth(player.getMaxHealth());
        }
        switch(equipped[0].getStat2()){
            case(1):
                fireRate = 600;
                break;
            case(2):
                fireRate = 525;
                break;
            case(3):
                fireRate = 450;
                break;
            case(4):
                fireRate = 375;
                break;
            case(5):
                fireRate = 300;
                break;
        }        
    }
    
    // Adds a random gear oject to the inventory array if it isn't full
    public void dropGear(){
        if(inventory.length<30){
            int item = (int)Math.floor(Math.random()*5);
            int rarity = 1;

            int rnd = (int)(Math.floor(Math.random()*100)+1);
            if(rnd<=5){
                rarity = 4;
            }
            else if(rnd<=15){
                rarity = 3;
            }
            else if(rnd<=35){
                rarity = 2;        
            }
            else if(rnd<=65){
                rarity = 1;
            }
            else rarity = 0;

            int stat1 = (int)Math.ceil((rarity+1)*difficultyModifier*stageModifier*roomModifier*0.75)*((int)(Math.floor(Math.random()*5)+1));
            int stat2 = 1;
            int stat3 = 1;
            if(item==0){
                stat2 = (int)(Math.floor(Math.random()*5)+1);
            }
            else{
                stat2 = (int)(0.5*(difficultyModifier*stageModifier*roomModifier)*(int)(Math.floor(Math.random()*10)+15));
            }

            if(item==0){
                stat3 = (int)(Math.floor(Math.random()*15)+6);
            }
            else{
                stat3 = (int)Math.floor(Math.random()*5*(0.5*difficultyModifier*stageModifier*roomModifier));
            }

            inventory = arrayManager.insertGear(inventory, 0, new Gear(item, stat1, stat2, stat3, rarity, currentID));
            currentID++;
            sortInventory();
            saveProgress();
        }
    }
    
    // Creates three random gear objects, with the first always being a bow and the next 2 being armour
    public void rollShop(){
        shopGear = new Gear[0];
        for(int i = 0; i <= 2; i++){
            int item = 0;
            if(i > 0){
                item = (int)Math.floor((Math.random()*4)+1);
            }           
            
            int rarity = 1;

            int rnd = (int)(Math.floor(Math.random()*100)+1);
            if(rnd<=5){
                rarity = 4;
            }
            else if(rnd<=15){
                rarity = 3;
            }
            else if(rnd<=35){
                rarity = 2;        
            }
            else if(rnd<=65){
                rarity = 1;
            }
            else rarity = 0;

            int stat1 = (int)Math.ceil((rarity+1)*shopModifier)*((int)(Math.floor(Math.random()*5)+1));
            int stat2 = 1;
            int stat3 = 1;
            if(item==0){
                stat2 = (int)(Math.floor(Math.random()*5)+1);
            }
            else{
                stat2 = (int)(0.5*(shopModifier*1.2)*(int)(Math.floor(Math.random()*10)+15));
            }

            if(item==0){
                stat3 = (int)(Math.floor(Math.random()*15)+6);
            }
            else{
                stat3 = (int)Math.floor(Math.random()*5*(0.7*shopModifier));
            }

            shopGear = arrayManager.insertGear(shopGear, i, new Gear(item, stat1, stat2, stat3, rarity, currentID));
            currentID++;            
        }    
    }
    
    // Adds the item from the shopGear array to the inventory, then takes away 
    // the buy price from the coin count.
    public void buyGear(int index){
        if(mouseClicked){
            if(inventory.length<30 && coins >= (shopGear[index].getSellPrice()*3)){
                playSound("buy.wav");
                coins -= (shopGear[index].getSellPrice()*3);
                inventory = arrayManager.insertGear(inventory, 0, shopGear[index]);
                shopGear[index] = null;               
            }
            saveProgress();
        }
    }
    
    // Sorts the inventory based on the selected sort method
    public void sortInventory(){
        switch(sortMethod){
            case(0):
                arrayManager.sort(inventory, 0, inventory.length-1, 0);
                break;
            case(1):
                arrayManager.sort(inventory, 0, inventory.length-1, 1);
                break;
            case(2):
                arrayManager.sort(inventory, 0, inventory.length-1, 2);
                break;
        }
    }
    
    // Runs all the methods that are responsible for running the game
    public void playGame(Graphics g){
        fire();
        drawArrows(g);    
        setupGame(g);
        drawPlayer(g);
                
        player.walk();     
        // Resets variables for playerCollisionBox to work
        player.allowUp = true;
        player.allowDown = true;
        player.allowLeft = true;
        player.allowRight = true;
                                     
        enemyActions(g);
        checkRoomCompletion();
        checkIfDead();
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
    
    // Saves important variables for progression in a .txt file that will be read
    // when the program is opened
    public void saveProgress(){
        try {
            
            // Creates the file
            File save = new File("save.txt");
                       
            FileWriter fw = new FileWriter(save.getAbsoluteFile());            
            BufferedWriter bw = new BufferedWriter(fw);
            
            // Writes all of the variables to the file
            bw.write(coins+"\n");
            bw.write(currentID+"\n");  
            bw.write(sortMethod+"\n");
            bw.write(shopModifier+"\n");
            for(int i = 0; i < 5; i++){
                bw.write(equipped[i].getItem()+"\n");
                bw.write(equipped[i].getStat1()+"\n");
                bw.write(equipped[i].getStat2()+"\n");
                bw.write(equipped[i].getStat3()+"\n");
                bw.write(equipped[i].getRarity()+"\n");
                bw.write(equipped[i].getOrderAcquired()+"\n");
            }            
            bw.write(inventory.length+"\n");
            for(int i = 0; i < inventory.length; i++){
                bw.write(inventory[i].getItem()+"\n");
                bw.write(inventory[i].getStat1()+"\n");
                bw.write(inventory[i].getStat2()+"\n");
                bw.write(inventory[i].getStat3()+"\n");
                bw.write(inventory[i].getRarity()+"\n");
                bw.write(inventory[i].getOrderAcquired()+"\n");
            }            
            for(int i = 0; i < 3; i++){
                if(shopGear[i]==null){
                    bw.write("null\n");
                }
                else{
                    bw.write(shopGear[i].getItem()+"\n");
                    bw.write(shopGear[i].getStat1()+"\n");
                    bw.write(shopGear[i].getStat2()+"\n");
                    bw.write(shopGear[i].getStat3()+"\n");
                    bw.write(shopGear[i].getRarity()+"\n");
                    bw.write(shopGear[i].getOrderAcquired()+"\n");
                }
            }
            
            bw.flush();
            bw.close();    
            System.out.println("Save complete");
        }
        catch(Exception e){
            System.out.println("Failed to save");
            System.out.println(e);
        }
    }
    
    
    // Reads the save.txt file if there is one and sets the variables/creates
    // Gear objects with those values
    public void loadProgress(){
        try{                        
            BufferedReader br = new BufferedReader(new FileReader("save.txt"));
            coins = Integer.parseInt(br.readLine());
            currentID = Integer.parseInt(br.readLine());
            sortMethod = Integer.parseInt(br.readLine());
            shopModifier = Double.parseDouble(br.readLine());
            for(int i = 0; i < 5; i++){
                equipped[i] = new Gear(Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()));
            }
            int size = Integer.parseInt(br.readLine());
            inventory = new Gear[size];
            for(int i = 0; i < size; i++){
                inventory[i] = new Gear(Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()));
            }
            String line = "";
            shopGear = new Gear[3];
            for(int i = 0; i < 3; i++){
                line = br.readLine();
                if(!(line.equals("null"))){
                    shopGear[i] = new Gear(Integer.parseInt(line),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()));
                }                
            }            
            System.out.println("Load complete");
        }
        catch(Exception e){
            System.out.println("Failed to load saved data");
            System.out.println(e);
        }
    }
    
    // resets player position
    public void resetPlayerPos(){
        player.setX(488);
        player.setY(584);
        player.setDirection(0);
    }
    
    // Resets mouse position
    public void resetMousePos(){
        mouseClickedX = -1;
        mouseClickedY = -1;
    }
    
    @Override
    public void paint(Graphics g){
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage,0,0,this);
    }    
    
    /**
     * @param args the command line arguments
     */    
        
    public static void main(String[] args) {
        FinalProject FP = new FinalProject();
        FP.init();
    }
    
}
