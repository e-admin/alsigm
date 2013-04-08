

package ieci.tdw.ispac.designer.client.diagrama;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public abstract class AbstractExample extends SimplePanel {

	/**
	 * Examples area.
	 */
	private static AbsolutePanel area = new AbsolutePanel();
	
	public static VerticalPanel vpA =new  VerticalPanel();

	/**
	 * Constructor.
	 */
	public AbstractExample() {
		addStyleName("gwt-diagrams-example");
		
	   vpA = new VerticalPanel();
		
		add(vpA);
		area.addStyleName("area");
		DOM.setStyleAttribute(getArea().getElement(), "overflow", "Auto");
		vpA.add(area);
	}
	
	




	/**
	 * Return examples area widget.
	 */
	protected static AbsolutePanel getArea() {
		return area;
	}
	
}
