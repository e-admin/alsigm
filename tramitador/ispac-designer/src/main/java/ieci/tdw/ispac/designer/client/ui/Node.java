package ieci.tdw.ispac.designer.client.ui;

import ieci.tdw.ispac.designer.client.diagrama.DiagramBuilderExample;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;

public class Node extends Label {

	public Node() {
		  super();
		  sinkEvents(Event.ONMOUSEUP  |  Event.BUTTON_LEFT |  Event.BUTTON_RIGHT | Event.ONCLICK | Event.KEYEVENTS |Event.MOUSEEVENTS |KeyboardListener.KEY_DELETE |Event.ONKEYDOWN);
	}
	
	public void onBrowserEvent(Event event) {

		 switch (DOM.eventGetType(event)) {
		   case Event.ONMOUSEUP: 
			   if (DOM.eventGetButton(event) == Event.BUTTON_RIGHT){
				DiagramBuilderExample.seleccionarConectar = 0;
				DiagramBuilderExample.accionesElemento(event, this);
			}
			   
			   break;
		 case Event.ONCLICK:
				DiagramBuilderExample.seleccionarConectar += 1;
				DiagramBuilderExample.accionesElemento(event, this);
			
			break;
		 }
		 super.onBrowserEvent(event);

}
	
}
