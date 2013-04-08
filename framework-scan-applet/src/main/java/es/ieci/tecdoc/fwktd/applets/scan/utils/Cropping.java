package es.ieci.tecdoc.fwktd.applets.scan.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Cropping extends JPanel{
	private static final long serialVersionUID = 1L;
	public BufferedImage image;
    public Dimension size;
    public Rectangle clip;
    public boolean showClip;
    
    public Cropping(BufferedImage image)
    {	    	
        this.image = image;
        size = new Dimension(image.getWidth(), image.getHeight());
        showClip = false;
    }
 
    protected void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	if(image!=null){
	        
	        Graphics2D g2 = (Graphics2D)g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        int x = (getWidth() - size.width)/2;
	        int y = (getHeight() - size.height)/2;
	        g2.drawImage(image, x, y, this);
	        if(showClip)
	        {
	            if(clip == null)
	                createClip();
	            g2.setPaint(Color.red);
	            g2.draw(clip);
	        }
    	}
	     
    }
 
    public void setClip(int x, int y)
    {
        // keep clip within raster
        int x0 = (getWidth() - size.width)/2;
        int y0 = (getHeight() - size.height)/2;
        if(x < x0 || x + clip.width  > x0 + size.width ||
           y < y0 || y + clip.height > y0 + size.height)
            return;
        clip.setLocation(x, y);
        repaint();
    }
 
    public Dimension getPreferredSize()
    {
        return size;
    }
 
    private void createClip()
    {
        clip = new Rectangle(140, 140);
        clip.x = (getWidth() - clip.width)/2;
        clip.y = (getHeight() - clip.height)/2;
    }
 
    public BufferedImage clipImage()
    {
        BufferedImage clipped = null;
        try
        {
            int w = clip.width;
            int h = clip.height;
            int x0 = (getWidth()  - size.width)/2;
            int y0 = (getHeight() - size.height)/2;
            int x = clip.x - x0;
            int y = clip.y - y0;
            clipped = image.getSubimage(x, y, w, h);
            return clipped;
        }
        catch(RasterFormatException rfe)
        {
            System.out.println("raster format error: " + rfe.getMessage());
            return null;
        }
       // JLabel label = new JLabel(new ImageIcon(clipped));
       // JOptionPane.showMessageDialog(this, label, "clipped image",
       //                               JOptionPane.PLAIN_MESSAGE);
    }
 
    public JPanel getUIPanel()
    {
        JButton clip = new JButton("Cortar");
        clip.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                clipImage();	                
            }
        });
        JPanel panel = new JPanel();
        //panel.add(clipBox);
        panel.add(clip);
        return panel;
    }
 
    public static void main(String[] args) throws IOException
    {
        File file = new File("c:\\p.jpg");
        Cropping test = new Cropping(ImageIO.read(file));
        ClipMover mover = new ClipMover(test);
        test.addMouseListener(mover);
        test.addMouseMotionListener(mover);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new JScrollPane(test));
        f.getContentPane().add(test.getUIPanel(), "South");
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}
