package uk.co.mmscomputing.util;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class UtilTabCloseIcon implements Icon
{
	private final Icon mIcon;
	private JTabbedPane mTabbedPane = null;
	private transient Rectangle mPosition = null;
	private boolean closed;
	
	/**
	 * Creates a new instance of TabCloseIcon.
	 */
	public UtilTabCloseIcon( Icon icon )
	{
		mIcon = icon;
	}
	
	
	/**
	 * Creates a new instance of TabCloseIcon.
	 */
	public UtilTabCloseIcon()
	{
//		this( new ImageIcon( TabCloseIcon.class.getResource("CloseTab.png")) );

    mIcon = new JarImageIcon( getClass(), "16x16/delete.png");
	}
	
	
	/**
	 * when painting, remember last position painted.
	 */
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		if( null==mTabbedPane )
		{
			mTabbedPane = (JTabbedPane)c;
			mTabbedPane.addMouseListener( new MouseAdapter()
			{
				public void mouseReleased( MouseEvent e )
				{
					// asking for isConsumed is *very* important, otherwise more than one tab might get closed!
					if ( !e.isConsumed()  &&   mPosition.contains( e.getX(), e.getY() ) )
					{
						if (closed == false) {
						    if (JOptionPane.showConfirmDialog(new JFrame(),
		    			            "Do you really want to discard this image ?", "Close",
		    			            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
							closed = true;
							final int index = mTabbedPane.getSelectedIndex();
							mTabbedPane.remove( index );
							e.consume();
					    } else {
					    	closed = false;
					    }
					}
				}
			}
		});
	}
		mPosition = new Rectangle( x,y, getIconWidth(), getIconHeight() );
		mIcon.paintIcon(c, g, x, y );
	}
	
	
	/**
	 * just delegate
	 */
	public int getIconWidth()
	{
		return mIcon.getIconWidth();
	}
	
	/**
	 * just delegate
	 */
	public int getIconHeight()
	{
		return mIcon.getIconHeight();
	}
	
}
