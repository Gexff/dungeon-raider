/*
 * This class detects when the mouse is moved, and when it is clicked.
 * 
 *
 */
package finalproject;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author geoff
 */
public class Mouse extends MouseAdapter {
    protected FinalProject FP;
    
    @Override
    public void mousePressed(MouseEvent e){
        FP.mouseClickedX = e.getX();
        FP.mouseClickedY = e.getY();            
        FP.mouseClicked = true;
        
        //System.out.println("x: "+e.getX());
        //System.out.println("y: "+e.getY());    
    }
    
    @Override
    public void mouseMoved(MouseEvent e){
        FP.mouseMovedX = e.getX();
        FP.mouseMovedY = e.getY();
    }
}
