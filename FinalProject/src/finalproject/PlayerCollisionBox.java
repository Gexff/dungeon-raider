/*
 * This class is used the create walls that block player movement. The walls are
 * initially created, and then activated by calling the testPlayerCollision method.
 * 
 */
package finalproject;

/**
 *
 * @author geoff
 */
public class PlayerCollisionBox {
    protected FinalProject FP;
    
    private Player player;   
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    
    
    public PlayerCollisionBox(Player player,  int x1, int x2, int y1, int y2){
        this.player = player;        
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    // This method looks at the position were the player would if they were to move.
    // If the player would be in the wall, they are not allowed to move.
    public void testPlayerCollision(){
        
        if(((player.getX()>=this.x1&&player.getX()<=this.x2)||(player.getX()+25>=this.x1&&player.getX()+25<=this.x2)||(player.getX()+51>=this.x1&&player.getX()+51<=this.x2))&&player.getY()-2<=this.y2&&player.getY()>=this.y1){
            player.allowUp = false;
        }
        if(((player.getX()>=this.x1&&player.getX()<=this.x2)||(player.getX()+25>=this.x1&&player.getX()+25<=this.x2)||(player.getX()+51>=this.x1&&player.getX()+51<=this.x2))&&player.getY()+53>=this.y1&&player.getY()+51<=this.y2){
            player.allowDown = false;
        }
        if(((player.getY()>=this.y1&&player.getY()<=this.y2)||(player.getY()+25>=this.y1&&player.getY()+25<=this.y2)||(player.getY()+51>=this.y1&&player.getY()+51<=this.y2))&&player.getX()-2<=this.x2&&player.getX()+51>=this.x1){
            player.allowLeft = false;
        }
        if(((player.getY()>=this.y1&&player.getY()<=this.y2)||(player.getY()+25>=this.y1&&player.getY()+25<=this.y2)||(player.getY()+51>=this.y1&&player.getY()+51<=this.y2))&&player.getX()+53>=this.x1&&player.getX()+51<=this.x2){
            player.allowRight = false;
        }                
    }     
}
