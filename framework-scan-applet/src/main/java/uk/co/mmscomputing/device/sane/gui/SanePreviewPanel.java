package uk.co.mmscomputing.device.sane.gui;

import java.util.HashMap;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;

import uk.co.mmscomputing.device.sane.*;
import uk.co.mmscomputing.device.sane.option.*;

public class SanePreviewPanel extends JPanel implements Runnable{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


static private String roititlestr = jsane.getResource("gui.SanePreviewPanel.roititlestr");


  SanePreviewImage image=null;
  JScrollPane      scroll=null;

  JLabel posx=new JLabel("0");
  JLabel posy=new JLabel("0");
  JLabel tl_x=null;
  JLabel tl_y=null;
  JLabel br_x=null;
  JLabel br_y=null;
  JLabel lresolution=null;

  @SuppressWarnings("unchecked")
private HashMap        options=null;
  private double         resolution=100.0;
  private Point          off=new Point(0,0),newoff=new Point(0,0);

  private SaneDevice scanner;

  @SuppressWarnings("unchecked")
public SanePreviewPanel(SaneDevice scanner,HashMap options){
    this.options=options;
    this.scanner=scanner;

    setLayout(new BorderLayout());

    image=new SanePreviewImage(this);
    scroll=new JScrollPane(image);
    add(scroll,BorderLayout.CENTER);

    JPanel ROI=new JPanel();
    ROI.setLayout(new GridLayout(3,3));

    posx.setBorder(new TitledBorder("mouse x [pixel]"));ROI.add(posx);
    tl_x=createROILabel("tl-x");ROI.add(tl_x);
    br_x=createROILabel("br-x");ROI.add(br_x);

    posy.setBorder(new TitledBorder("mouse y [pixel]"));ROI.add(posy);
    tl_y=createROILabel("tl-y");ROI.add(tl_y);
    br_y=createROILabel("br-y");ROI.add(br_y);

    lresolution=createROILabel("resolution");ROI.add(lresolution);
    ROI.add(new JLabel());
    ROI.add(new JLabel());

    ROI.setBorder(new TitledBorder(roititlestr));
    add(ROI,BorderLayout.SOUTH);

    setResolution();
  }

  public BufferedImage getImage(){
    return image.getImage();
  }

  public void setImage(BufferedImage img){
    image.setImage(img);
  }

  public void run(){
    try{
      setResolution();                               // save the scan resolution
      BufferedImage img=scanner.getImage(true);      // scan image one only

      if(img!=null){
        off.x=newoff.x;
        off.y=newoff.y;
        image.setImage(img);
      }
    }catch(Throwable t){
      System.gc();
      System.err.println(getClass().getName()+".run\n\t"+t);
      t.printStackTrace();
      System.out.println("9\b"+getClass().getName()+".run\n\t"+t);
    }
  }

  private JLabel createROILabel(String name){
    JLabel label;
    try{
      Descriptor od=(Descriptor)options.get(name);
      label=new JLabel(od.getStringValue());
      TitledBorder tb=new TitledBorder(name+" ["+SaneConstants.SANE_UNIT[od.getUnit()]+"]");
      label.setBorder(tb);
    }catch(Exception e){
      label=new JLabel(name);
      label.setEnabled(false);
    }
    return label;
  }

  public void revalidate(){
    if(options!=null){
      double resolution=100.0;
      try{
        Descriptor res=(Descriptor)options.get("resolution");
        String     resstr=res.getStringValue();
        lresolution.setText(resstr);
        resolution=Double.parseDouble(resstr);
      }catch(Exception e){
//        System.err.println(getClass().getName()+".revalidate:\n\t"+e);      
//        e.printStackTrace();
      }
      try{
        FixedDesc tlxd=(FixedDesc)options.get("tl-x");
        FixedDesc tlyd=(FixedDesc)options.get("tl-y");
        FixedDesc brxd=(FixedDesc)options.get("br-x");
        FixedDesc bryd=(FixedDesc)options.get("br-y");

        tl_x.setText(tlxd.getStringValue(0));
        tl_y.setText(tlyd.getStringValue(0));
        br_x.setText(brxd.getStringValue(0));
        br_y.setText(bryd.getStringValue(0));

        newoff.x=tlxd.getPixelValue(resolution);
        newoff.y=tlyd.getPixelValue(resolution);

      }catch(Exception e){
//        System.err.println(getClass().getName()+".revalidate:\n\t"+e);      
//        e.printStackTrace();
      }
    }
    super.revalidate();
  }

  private void setResolution(){
    try{
      Descriptor od=(Descriptor)options.get("resolution");
      double oldresolution=resolution;
      resolution=Double.parseDouble(od.getStringValue());
      if(oldresolution!=resolution){
        lresolution.setText(od.getStringValue());
        double scale=resolution/oldresolution;

        newoff.x=(int)(newoff.x*scale);
        newoff.y=(int)(newoff.y*scale);

//        System.err.println("res     "+oldresolution+"    "+resolution+"    "+scale);
//        System.err.println("    off "+off.x+"    "+off.y);
//        System.err.println("new off "+newoff.x+"    "+newoff.y);

      }
    }catch(Exception e){
      System.err.println(getClass().getName()+".setResolution:\n\t"+e);      
      e.printStackTrace();
    }
  }

  void setXY(Point p){
    posx.setText(Integer.toString(p.x));
    posy.setText(Integer.toString(p.y));

    scroll(p);
  }

  private double convert(String name, int value){
    Descriptor od=(Descriptor)options.get(name);
    return od.convertPixelValue(resolution,value);
  }

  void setTopLeft(Point p){                        // called from SanePreviewImage
    try{
      tl_x.setText(Double.toString(convert("tl-x",off.x+p.x)));
      tl_y.setText(Double.toString(convert("tl-y",off.y+p.y)));
    }catch(Exception e){
//      System.err.println(getClass().getName()+".setTopLeft:\n\t"+e);      
//      e.printStackTrace();
    }
  }

  void setBottomRight(Point p){                    // called from SanePreviewImage
    try{
      br_x.setText(Double.toString(convert("br-x",off.x+p.x)));
      br_y.setText(Double.toString(convert("br-y",off.y+p.y)));
      scroll(p);
    }catch(Exception e){
//      System.err.println(getClass().getName()+".setBottomRight:\n\t"+e);      
//      e.printStackTrace();
    }
  }

  private void setROI(String name, int value)throws SaneIOException{
    Descriptor od=(Descriptor)options.get(name);
    od.setPixelValue(resolution,value);
  }

  void setROI(Rectangle roi)throws SaneIOException{ // called from SanePreviewImage
    newoff.x=off.x+roi.x;
    newoff.y=off.y+roi.y;
//    try{

    setROI("tl-x",newoff.x);
    setROI("tl-y",newoff.y);
    setROI("br-x",newoff.x+roi.width);
    setROI("br-y",newoff.y+roi.height);

//    }catch(Exception e){
//      System.err.println(getClass().getName()+".setROI:\n\t"+e);      
//      e.printStackTrace();
//    }
  }

  private void scroll(Point p){
    JViewport vp=scroll.getViewport();
    Rectangle vr=vp.getViewRect();

    boolean   xscroll=p.x<(vr.x+10);
    if(xscroll){
      JScrollBar hbar=scroll.getHorizontalScrollBar();
      hbar.setValue(hbar.getValue()-hbar.getUnitIncrement(10));
    }
    boolean   yscroll=p.y<(vr.y+10);
    if(yscroll){
      JScrollBar vbar=scroll.getVerticalScrollBar();
      vbar.setValue(vbar.getValue()-vbar.getUnitIncrement(10));
    }

    xscroll=(vr.x+vr.width-10)<p.x;
    if(xscroll){
      JScrollBar hbar=scroll.getHorizontalScrollBar();
      hbar.setValue(hbar.getValue()+hbar.getUnitIncrement(10));
    }
    yscroll=(vr.y+vr.height-10)<p.y;
    if(yscroll){
      JScrollBar vbar=scroll.getVerticalScrollBar();
      vbar.setValue(vbar.getValue()+vbar.getUnitIncrement(10));
    }
  }
}