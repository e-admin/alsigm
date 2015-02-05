package uk.co.mmscomputing.device.sane.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

import uk.co.mmscomputing.device.sane.*;

public class SanePreviewImage extends JComponent{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
protected BufferedImage     image=new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
  protected Rectangle         roi  =new Rectangle(-1,-1,0,0);
  protected SanePreviewPanel  spp  =null;

  public SanePreviewImage(SanePreviewPanel spp){
    MIA mia=new MIA();
    addMouseListener(mia);
    addMouseMotionListener(mia);
    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    this.spp=spp;
  }

  public BufferedImage getImage(){
    return image;
  }

  public void setImage(BufferedImage img){
    image=(img!=null)?img:new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
    roi.setBounds(-1,-1,0,0);
    setSize(image.getWidth(),image.getHeight());
    validate();
  }

  public void paint(Graphics gc){
    try{
      Graphics2D g=(Graphics2D)gc;
      g.drawImage(image,0,0,null);
      if((roi.width>0)&&(roi.height>0)){
        g.drawRect(roi.x,roi.y,roi.width,roi.height);
      }
    }catch(Error err){                                    // probably out of memory error
      setImage(null);
      System.gc();
      System.err.println(getClass().getName()+".paint:\n\t"+err);
      err.printStackTrace();
      System.out.println("9\b"+getClass().getName()+".paint:\n\t"+err);
    }
  }

  public Dimension getPreferredSize(){
    return new Dimension(image.getWidth(),image.getHeight());
  }

  class MIA extends MouseInputAdapter{

    public void mouseClicked(MouseEvent e){
      roi.setBounds(-1,-1,0,0);
      repaint();
    }

    public void mousePressed(MouseEvent e){
      Point t=e.getPoint();
      roi.setBounds(t.x,t.y,0,0);
      spp.setTopLeft(t);
    }

    public void mouseReleased(MouseEvent e){
      if((roi.width>0)&&(roi.height>0)){
        try{
          spp.setROI(roi);
        }catch(SaneIOException sioe){
          sioe.printStackTrace();
        }
      }
    }

    public void mouseMoved(MouseEvent e){
      spp.setXY(e.getPoint());
    }

    public void mouseDragged(MouseEvent e){
      Point b=e.getPoint();
      roi.setBounds(roi.x,roi.y,b.x-roi.x+1,b.y-roi.y+1);
      spp.setBottomRight(b);
      repaint();
    }
  }
}




