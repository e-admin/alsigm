package ieci.tdw.ispac.designer.client.ui;


import ieci.tdw.ispac.designer.client.diagrama.AbstractConnectionsExample;
import ieci.tdw.ispac.designer.client.diagrama.DiagramBuilderExample;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;

public class Arrow extends Label{

	
	public Arrow() {
		  super();
		  sinkEvents(Event.ONMOUSEUP | Event.ONDBLCLICK | Event.BUTTON_RIGHT |Event.BUTTON_LEFT | Event.ONCLICK | Event.KEYEVENTS |Event.MOUSEEVENTS);

		}

	public void onBrowserEvent(Event event) {
		

		  switch (DOM.eventGetType(event)) {
		    case Event.ONMOUSEUP:
		    if (DiagramBuilderExample.conectar ) {
		    	DiagramBuilderExample.salirModoConexion();
				
			}
	
		    else if(!DiagramBuilderExample.conectar ){
		    		DiagramBuilderExample.cambiarSeleccionado();
		    		DiagramBuilderExample.conectar = true;
					Element divCont = DOM.getElementById("contenedor");
					if (divCont != null)
						DOM.setElementAttribute(divCont, "id",
								"contenedorCursor");

					DOM.setElementAttribute(DiagramBuilderExample.CONECTOR.getElement(), 
											"title",DiagramBuilderExample.mensajes.tooltipConectorSel());
					DiagramBuilderExample.toolboxDragController.makeNotDraggable(DiagramBuilderExample.OR);
					DiagramBuilderExample.toolboxDragController.makeNotDraggable(DiagramBuilderExample.AND);
					DiagramBuilderExample.toolboxDragController.makeNotDraggable(DiagramBuilderExample.FaseActividad);

					DOM.removeElementAttribute(DiagramBuilderExample.OR.getElement(), "title");
					DOM.removeElementAttribute(DiagramBuilderExample.AND.getElement(), "title");
					DOM.removeElementAttribute(DiagramBuilderExample.FaseActividad.getElement(),
							"title");
					Element labelFlecha = DOM.getElementById("flecha");
					if(AbstractConnectionsExample.condSel!=null){
						String idCond=DOM.getElementProperty(AbstractConnectionsExample.condSel, "id");
						AbstractConnectionsExample.deseleccionarCondicion(idCond);
						}
					DOM.setElementAttribute(labelFlecha, "id", "flechaSel");
					if (DOM.eventGetButton(event) == Event.BUTTON_LEFT) {

						DiagramBuilderExample.desconectarAutomatico = false;
					} else if (DOM.eventGetButton(event) == Event.BUTTON_RIGHT)
						DiagramBuilderExample.desconectarAutomatico = true;
				} else if (!DiagramBuilderExample.conectar
						&& (DOM.eventGetType(event) == Event.BUTTON_LEFT || DOM
								.eventGetButton(event) == Event.BUTTON_RIGHT)) {
						DiagramBuilderExample.numVeces = 0;

				}
		      break;
		    
		    default:
		      break;
		  }//end 

		  super.onBrowserEvent(event);
	}

}
