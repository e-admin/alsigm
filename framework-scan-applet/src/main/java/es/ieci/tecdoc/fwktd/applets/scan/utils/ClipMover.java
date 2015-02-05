package es.ieci.tecdoc.fwktd.applets.scan.utils;



import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

public class ClipMover  extends MouseInputAdapter{
	
	    Cropping cropping;
	    Point offset;
	    boolean dragging;
	    boolean resizing;
	    final int PROX_DIST = 3;
	    
	    public ClipMover(Cropping c)
	    {
	        cropping = c;
	        offset = new Point();
	        dragging = false;
	        resizing = false;
	    }
	 
	    public void mousePressed(MouseEvent e)
	    {
	        Point p = e.getPoint();
	        if(cropping!=null){
	        	
	        	 if(cropping.getCursor() != Cursor.getDefaultCursor()) {
	                 // If cursor is set for resizing, allow dragging.
	             	resizing = true;
	             }else if(cropping.clip.contains(p)){
		            offset.x = p.x - cropping.clip.x;
		            offset.y = p.y - cropping.clip.y;
		            dragging = true;
	             }       	
	        }
	       
	    }
	 
	    public void mouseReleased(MouseEvent e)
	    {
	        dragging = false;
	        resizing = false;
	    }
	 
	    public void mouseDragged(MouseEvent e)
	    {
	        if(dragging)
	        {
	            int x = e.getX() - offset.x;
	            int y = e.getY() - offset.y;
	            cropping.setClip(x, y);
	        }   
	        if(resizing){
	    		Point p = e.getPoint();
	            Rectangle r = cropping.clip;
	            int type = cropping.getCursor().getType();
	            int dx = p.x - r.x;
	            int dy = p.y - r.y;
	            switch(type) {
	                case Cursor.N_RESIZE_CURSOR:
	                    int height = r.height - dy;
	                    r.setRect(r.x, r.y+dy, r.width, height);
	                    break;
	                case Cursor.NW_RESIZE_CURSOR:
	                    int width = r.width - dx;
	                    height = r.height - dy;
	                    r.setRect(r.x+dx, r.y+dy, width, height);
	                    break;
	                case Cursor.W_RESIZE_CURSOR:
	                    width = r.width - dx;
	                    r.setRect(r.x+dx, r.y, width, r.height);
	                    break;
	                case Cursor.SW_RESIZE_CURSOR:
	                    width = r.width - dx;
	                    height = dy;
	                    r.setRect(r.x+dx, r.y, width, height);
	                    break;
	                case Cursor.S_RESIZE_CURSOR:
	                    height = dy;
	                    r.setRect(r.x, r.y, r.width, height);
	                    break;
	                case Cursor.SE_RESIZE_CURSOR:
	                    width = dx;
	                    height = dy;
	                    r.setRect(r.x, r.y, width, height);
	                    break;
	                case Cursor.E_RESIZE_CURSOR:
	                    width = dx;
	                    r.setRect(r.x, r.y, width, r.height);
	                    break;
	                case Cursor.NE_RESIZE_CURSOR:
	                    width = dx;
	                    height = r.height - dy;
	                    r.setRect(r.x, r.y+dy, width, height);
	                    break;
	                default:
	                    System.out.println("unexpected type: " + type);
	            }
	            cropping.repaint();
	    	}
	     }
	    
	    
	    public void mouseMoved(MouseEvent e) {
	    	if(cropping.clip!=null){
	        Point p = e.getPoint();
	        if(!isOverRect(p)) {
	            if(cropping.getCursor() != Cursor.getDefaultCursor()) {
	                // If cursor is not over rect reset it to the default.
	            	cropping.setCursor(Cursor.getDefaultCursor());
	            }
	            return;
	        }
	        // Locate cursor relative to center of rect.
	        int outcode = getOutcode(p);
	        Rectangle r = cropping.clip;
	        switch(outcode) {
	            case Rectangle.OUT_TOP:
	                if(Math.abs(p.y - r.y) < PROX_DIST) {
	                	cropping.setCursor(Cursor.getPredefinedCursor(
	                                        Cursor.N_RESIZE_CURSOR));
	                }
	                break;
	            case Rectangle.OUT_TOP + Rectangle.OUT_LEFT:
	                if(Math.abs(p.y - r.y) < PROX_DIST &&
	                   Math.abs(p.x - r.x) < PROX_DIST) {
	                	cropping.setCursor(Cursor.getPredefinedCursor(
	                                        Cursor.NW_RESIZE_CURSOR));
	                }
	                break;
	            case Rectangle.OUT_LEFT:
	                if(Math.abs(p.x - r.x) < PROX_DIST) {
	                	cropping.setCursor(Cursor.getPredefinedCursor(
	                                        Cursor.W_RESIZE_CURSOR));
	                }
	                break;
	            case Rectangle.OUT_LEFT + Rectangle.OUT_BOTTOM:
	                if(Math.abs(p.x - r.x) < PROX_DIST &&
	                   Math.abs(p.y - (r.y+r.height)) < PROX_DIST) {
	                	cropping.setCursor(Cursor.getPredefinedCursor(
	                                        Cursor.SW_RESIZE_CURSOR));
	                }
	                break;
	            case Rectangle.OUT_BOTTOM:
	                if(Math.abs(p.y - (r.y+r.height)) < PROX_DIST) {
	                	cropping.setCursor(Cursor.getPredefinedCursor(
	                                        Cursor.S_RESIZE_CURSOR));
	                }
	                break;
	            case Rectangle.OUT_BOTTOM + Rectangle.OUT_RIGHT:
	                if(Math.abs(p.x - (r.x+r.width)) < PROX_DIST &&
	                   Math.abs(p.y - (r.y+r.height)) < PROX_DIST) {
	                	cropping.setCursor(Cursor.getPredefinedCursor(
	                                        Cursor.SE_RESIZE_CURSOR));
	                }
	                break;
	            case Rectangle.OUT_RIGHT:
	                if(Math.abs(p.x - (r.x+r.width)) < PROX_DIST) {
	                	cropping.setCursor(Cursor.getPredefinedCursor(
	                                        Cursor.E_RESIZE_CURSOR));
	                }
	                break;
	            case Rectangle.OUT_RIGHT + Rectangle.OUT_TOP:
	                if(Math.abs(p.x - (r.x+r.width)) < PROX_DIST &&
	                   Math.abs(p.y - r.y) < PROX_DIST) {
	                	cropping.setCursor(Cursor.getPredefinedCursor(
	                                        Cursor.NE_RESIZE_CURSOR));
	                }
	                break;
	            default:    // center
	            	cropping.setCursor(Cursor.getDefaultCursor());
	        }
	    	}
	    	
	    }
	    /**
	     * Make a smaller Rectangle and use it to locate the
	     * cursor relative to the Rectangle center.
	     */
	    private int getOutcode(Point p) {
	        Rectangle r = (Rectangle)cropping.clip.clone();
	        r.grow(-PROX_DIST, -PROX_DIST);
	        return r.outcode(p.x, p.y);        
	    }

	    /**
	     * Make a larger Rectangle and check to see if the
	     * cursor is over it.
	     */
	    private boolean isOverRect(Point p) {
	        Rectangle r = (Rectangle)cropping.clip.clone();
	        r.grow(PROX_DIST, PROX_DIST);
	        return r.contains(p);
	    }
	}


