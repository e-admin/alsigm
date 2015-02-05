package ieci.tdw.ispac.designer.client.diagrama;

import ieci.tdw.ispac.designer.client.Designer;
import ieci.tdw.ispac.designer.client.connection.RectilinearTwoEndedConnectionCustom;
import ieci.tdw.ispac.designer.client.exceptions.DesignerException;
import ieci.tdw.ispac.designer.client.helper.Html;
import ieci.tdw.ispac.designer.client.helper.Validates;
import ieci.tdw.ispac.designer.client.helper.varDefs;
import ieci.tdw.ispac.designer.client.menuPopUp.MenuPopUpPanel;
import ieci.tdw.ispac.designer.client.objetos.DrawFlow;
import ieci.tdw.ispac.designer.client.objetos.DrawObject;
import ieci.tdw.ispac.designer.client.objetos.Procedimiento;
import ieci.tdw.ispac.designer.client.objetos.TramiteFase;
import ieci.tdw.ispac.designer.client.ui.Arrow;
import ieci.tdw.ispac.designer.client.ui.Node;
import ieci.tdw.ispac.designer.client.ui.Phase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pl.balon.gwt.diagrams.client.connection.AbstractConnection;
import pl.balon.gwt.diagrams.client.connection.Connection;
import pl.balon.gwt.diagrams.client.connector.Connector;
import pl.balon.gwt.diagrams.client.connector.UIObjectConnector;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.DragHandlerAdapter;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.VetoDragException;
import com.allen_sauer.gwt.dragdrop.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dragdrop.client.drop.DropController;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DiagramBuilderExample extends AbstractConnectionsExample {

	CellPanel toolbox;

	
	String style;
	public static MenuPopUpPanel popUpFase;
	public static MenuPopUpPanel popUpNodo;

	public static Label FaseActividad;
	public static Label OR;
	public static Label AND;
	public static Arrow CONECTOR;

	// Variable que indicara cuando el usuario tiene pulsado el conector y puede
	// realizar conexiones
	public static boolean conectar = false;
	// Variable que indicar si el usuario ha pulsado solo un click sobre el
	// conector con lo que na mas que haga una conexión
	// se deshabilitara el modo conexion y si pulsa doble click
	// desconectarAutomatico valdra true y sera el usuario quien pulsará sobre
	// el icono del conector para salir del modo de conectar.
	public static boolean desconectarAutomatico = false;
	public static int numVeces = 0;
	private static List conexionesBorradas = new ArrayList();
	public static Image iTramite;
	public static boolean sel = false;
	public static Map objetos = new HashMap();

	/*
	 * Constructor
	 */
	public DiagramBuilderExample(int idProc, String locale) {

		super(idProc, locale);
		stageId=-1;
	}

	/**
	 * Implementacion del metodo delete contents, cuando se ve un subproceso se
	 * ha de eliminar de la pantalla el contenido ,almacenandolo para ser
	 * guardado a posteriori
	 * 
	 * @param update:
	 *            Indica si se puede modificar o no , para mostrar los pop ups
	 *            con unos mensajes u otros
	 */

	protected void deleteContents(boolean update) {

		Element nombreProc = infoPanel.getWidget(0).getElement();

		Element tbody = DOM.getChild(toolbox.getElement(), 0);
		String literal;
		if (procedimiento.isTipoProcedimiento())
			literal = varDefs.FASE;
		else
			literal = varDefs.ACTIVIDAD;
		Html.changeLiteralNodo(literal, tbody);

		int numHijos = DOM.getChildCount(getArea().getElement());
		for (int i = 1; i < numHijos; i++) {

			Element hijo = DOM.getChild(getArea().getElement(), i);
			DOM.removeChild(getArea().getElement(), hijo);
			numHijos = numHijos - 1;
			i = i - 1;
		}

		if (!update) {

			DOM.setInnerText(nombreProc, mensajes.infoLoading());
			Element hijo = DOM.getElementById("PopUpFase");
			if (hijo != null)
				DOM.removeChild(DOM.getParent(hijo), hijo);
			if (procedimiento.getEstado() == varDefs.borrador) {
				hijo = DOM.getElementById("PopUpNodo");
				if (hijo != null)
					DOM.removeChild(DOM.getParent(hijo), hijo);
			}
			flujos.clear();
			elementos.clear();
			objetos.clear();
		}

	}

	/**
	 * Implementacion del metodo createContents, inicializará los elementos que
	 * va a contener el diseñador y llamará para realizar la precarga de datos
	 * si se obtienen los datos del procedimiento correctamente.
	 */
	
	protected void createContents() {

		final PickupDragController dragController = new PickupDragController(
				getArea(), true);
		AbsolutePositionDropController dropController = new AbsolutePositionDropController(
				getArea());

		toolboxDragController = new ToolboxDragController(dropController,
				dragController);

		toolbox = new HorizontalPanel();
		toolbox.setSpacing(5);

		DOM.setElementAttribute(toolbox.getElement(), "id", "toolbox");
		toolbox.addStyleName("toolbox");
		panel = new AbsolutePanel();

		infoPanel = new HorizontalPanel();
		infoPanel.setStyleName("infoPanel");
		DOM.setElementAttribute(infoPanel.getElement(), "id", "infoPanel");

		vp = new VerticalPanel();
		vp.setStyleName("builder");

		vp.add(infoPanel);
		vp.add(panel);

		Label label = new Label(mensajes.infoLoading());

		DOM.setElementAttribute(label.getElement(), "id", "infoLabel");
		infoPanel.add(label);
		infoPanel.setCellWidth(label, "80%");
		infoPanel.setCellVerticalAlignment(label, VerticalPanel.ALIGN_MIDDLE);
		getArea().add(vp);

		infoPanel.add(toolbox);

		infoPanel.setCellVerticalAlignment(toolbox, VerticalPanel.ALIGN_TOP);
		infoPanel.setCellWidth(toolbox, "30%");
		infoPanel.setSpacing(0);

		// 3) Creamos un objeto asincrono para manejar el resultado
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {

				precargarDatos((Procedimiento) result);
				// Designer.tabs.selectTab(0);
				Designer.update();
			}

			public void onFailure(Throwable caught) {

				Window.alert(mensajeError.accionNoRealizada(100,
						"getProcedure", caught.getLocalizedMessage()));

			}
		};

		servicio.getProcedure(idProcedimiento, callback);

		createToolboxNode("-", varDefs.SFASE);

		AND = new Label();
		DOM.setElementAttribute(AND.getElement(), "id", varDefs.AND);
		AND.setStyleName(varDefs.styleToolboxNodoSinc);
		HTML img1 = new HTML();
		img1
				.setHTML("<img src=\"./img/AndMenu.gif\"  style=\"align:right;\"></img>");

		img1.setSize("auto", "auto");
		DOM.appendChild(AND.getElement(), img1.getElement());

		DOM.setElementAttribute(AND.getElement(), "title", mensajes
				.tooltipAnd());
		toolboxDragController.makeDraggable(AND);

		toolbox.add(AND);
		toolbox.setCellVerticalAlignment(AND, HorizontalPanel.ALIGN_TOP);

		OR = new Label();
		DOM.setElementAttribute(OR.getElement(), "id", varDefs.OR);
		OR.setStyleName(varDefs.styleToolboxNodoSinc);
		HTML img2 = new HTML();
		img2
				.setHTML("<img src=\"./img/OrMenu.gif\"  style=\"align:right;\"></img>");

		img2.setSize("auto", "auto");
		DOM.appendChild(OR.getElement(), img2.getElement());

		DOM.setElementAttribute(OR.getElement(), "title", mensajes.tooltipOr());
		toolboxDragController.makeDraggable(OR);
		toolbox.add(OR);
		toolbox.setCellVerticalAlignment(OR, HorizontalPanel.ALIGN_TOP);

		CONECTOR = new Arrow();

		HTML image = new HTML();
		image
				.setHTML("<img src=\"./img/flecha.gif\"  style=\"align:right;\"></img>");
		image.addStyleName("nodo-sinc");

		image.setSize("auto", "auto");
		CONECTOR.setStyleName("flecha");

		DOM.setElementAttribute(image.getElement(), "id", "iflecha");
		DOM.setElementAttribute(CONECTOR.getElement(), "id", "flecha");
		DOM.appendChild(CONECTOR.getElement(), image.getElement());
		DOM.setElementAttribute(CONECTOR.getElement(), "title", mensajes
				.tooltipConector());

		toolbox.add(CONECTOR);
		toolbox
				.setCellVerticalAlignment(CONECTOR,
						HorizontalPanel.ALIGN_MIDDLE);

		Element aux = DOM.getParent(CONECTOR.getElement());

		// La variable numVeces no debe ser modificada , es necesario debido a
		// que este evento
		// en la misma ejecución ser recorre varias veces
		DOM.setEventListener(image.getElement(), new EventListener() {
			public void onBrowserEvent(Event event) {

				// inhabilitar los otros fase and y or y sus tooltips
				// dar aspecto seleccionado

				if (conectar && DOM.eventGetType(event) == Event.ONCLICK) {
					salirModoConexion();

				} else if (!conectar
						&& (DOM.eventGetType(event) == Event.ONCLICK || DOM
								.eventGetButton(event) == Event.BUTTON_RIGHT)
						&& numVeces == 0) {
					cambiarSeleccionado();
					conectar = true;
					Element divCont = DOM.getElementById("contenedor");
					if (divCont != null)
						DOM.setElementAttribute(divCont, "id",
								"contenedorCursor");

					DOM.setElementAttribute(CONECTOR.getElement(), "title",
							mensajes.tooltipConectorSel());
					toolboxDragController.makeNotDraggable(OR);
					toolboxDragController.makeNotDraggable(AND);
					toolboxDragController.makeNotDraggable(FaseActividad);

					DOM.removeElementAttribute(OR.getElement(), "title");
					DOM.removeElementAttribute(AND.getElement(), "title");
					DOM.removeElementAttribute(FaseActividad.getElement(),
							"title");
					Element labelFlecha = DOM.getElementById("flecha");
					if(AbstractConnectionsExample.condSel!=null){
						String idCond=DOM.getElementProperty(AbstractConnectionsExample.condSel, "id");
						AbstractConnectionsExample.deseleccionarCondicion(idCond);
						}
					DOM.setElementAttribute(labelFlecha, "id", "flechaSel");
					if (DOM.eventGetType(event) == Event.ONCLICK) {

						desconectarAutomatico = false;
					} else if (DOM.eventGetButton(event) == Event.BUTTON_RIGHT)
						desconectarAutomatico = true;
				} else if (!conectar
						&& (DOM.eventGetType(event) == Event.ONCLICK || DOM
								.eventGetButton(event) == Event.BUTTON_RIGHT)) {
					numVeces = 0;

				}

			}

		});

		DOM.addEventPreview(new EventPreview() {

			public boolean onEventPreview(Event event) {

				if (procedimiento.getEstado() == varDefs.borrador && !conectar) {
					if (KeyboardListener.KEY_DELETE == DOM
							.eventGetKeyCode(event)
							&& DOM.eventGetType(event) == Event.ONKEYDOWN) {

						if ((seleccionado == null )
								&& RectilinearTwoEndedConnectionCustom.imagenSel == null && condSel==null) {
							Window.alert(mensajes.infoDelete());

						} else {
							if(condSel!=null ){	
								if (Window.confirm(mensajes.confirmDeleteNode())) {
									if(condSel!=null)
										try {
											eliminarCondicion();
										} catch (Exception e) {
											Window.alert( DiagramBuilderExample.mensajeError.accionNoRealizada(100, 
													"eliminarCondicion",e.getLocalizedMessage()));
										}		
								}
							}
					

							else if (RectilinearTwoEndedConnectionCustom.imagenSel != null){
								if (Window.confirm(mensajes.confirmDeleteFlow())) {
									RectilinearTwoEndedConnectionCustom.eliminarFlujo(idProcedimiento);

								} else {
									RectilinearTwoEndedConnectionCustom.cambiarStilo();
									selected.removeAll(selected);
									seleccionado = null;
									RectilinearTwoEndedConnectionCustom.imagenSel = null;
								}

							}
							else {
								int idFase = DOM.getElementPropertyInt(seleccionado.getElement(), "id");
								elementos.remove(new Integer(idFase));
								borrarSeleccionadoPorTeclado(seleccionado);

							} 
					
						}
					}

				}

				if (Event.ONCLICK == DOM.eventGetType(event)) {
					
					if (RectilinearTwoEndedConnectionCustom.imagenSel != null
							&& !sel) {

						RectilinearTwoEndedConnectionCustom.cambiarStilo();
					}
					if (sel && !conectar)
						sel = false;
					else if (sel == false || conectar) {
						
						/*if (seleccionado != null
								) {
						
							boolean esconector = Html.contains(seleccionado
									.getStyleName(), "conectar");

							cambiarSeleccionado();
							if (!esconector && conectar)
								selected.removeAll(selected);
						}*/
						if (!conectar)
							seleccionado = null;
					}

					if (popUpFase.isVisible()) {
						popUpFase.setVisible(false);
					}
					if (procedimiento.getEstado() == varDefs.borrador
							&& popUpNodo.isVisible()) {
						popUpNodo.setVisible(false);

					}

				}


				return true;

			}
		}

		);

		addIconPrint();
		addIconHelp( );

	}

	
	public void eliminarCondicion() throws NumberFormatException, DesignerException
	{
		
	
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {
				  //Eliminar de la pantalla
				Element padreCond=DOM.getParent(condSel);
				if(padreCond!=null){
				Element abuelo=DOM.getParent(padreCond);
				if(abuelo!=null)
				DOM.removeChild(abuelo, padreCond);
				}
				
			}

			public void onFailure(Throwable caught) {

				Window.alert(mensajeError.accionNoRealizada(100,
						"removeCondition" , caught
								.getLocalizedMessage()));
			}
		};
		
		String sFlujo= DOM.getElementAttribute(condSel, "id");
		String ids=sFlujo.substring(1);
		int id=Integer.parseInt(ids);
	servicio.removeEventRules(varDefs.EVENT_OBJ_FLOW, id, callback);
	
	}
	
	/**
	 * SalirModoConexion: Deshabilita la opcion de crear los conectores y vuelve
	 * a permitir realizar el resto de acciones
	 * 
	 */
	public static void salirModoConexion() {

		Element divContenido = DOM.getElementById("contenedorCursor");
		if (divContenido != null)
			DOM.setElementAttribute(divContenido, "id", "contenedor");

		numVeces = 0;
		eliminarSeleccionados();

		DOM.setElementAttribute(CONECTOR.getElement(), "title", mensajes
				.tooltipConector());
		toolboxDragController.makeDraggable(OR);
		toolboxDragController.makeDraggable(AND);
		toolboxDragController.makeDraggable(FaseActividad);
		DOM.setElementAttribute(OR.getElement(), "title", mensajes.tooltipOr());
		DOM.setElementAttribute(AND.getElement(), "title", mensajes
				.tooltipAnd());
		String auxiliar = DOM.getInnerText(FaseActividad.getElement());
		if (DOM.getInnerText(FaseActividad.getElement()).compareTo(
				varDefs.ACTIVIDAD) == 0)
			DOM.setElementAttribute(FaseActividad.getElement(), "title",
					mensajes.tooltipActividad());
		else
			DOM.setElementAttribute(FaseActividad.getElement(), "title",
					mensajes.tooltipFase());
		desconectarAutomatico = false;
		conectar = false;

		Element labelFlecha = DOM.getElementById("flechaSel");
		if (labelFlecha != null)
			DOM.setElementAttribute(labelFlecha, "id", "flecha");
	}

	/**
	 * Elimina tanto de la pantalla como de bbdd el elemento que es origen del
	 * evento que se lanza y en caso de que tuviera conectores también lo
	 * elimina tanto de diseñador como de bbdd, los conectores se eliminan de la
	 * bbdd cuando se realiza la llamada de remove
	 * 
	 * @param w:
	 *            Elemento sobre el que el usuario ha originado el evento
	 */
	public void borrarSeleccionadoPorTeclado(Widget w) {
		
		
		eliminarConexion(seleccionado);
		Validates.deleteAllNodeConnect(DOM.getElementPropertyInt(seleccionado
				.getElement(), "id"));
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {

				selected.removeAll(selected);
				seleccionado = null;
				
				updateNewFlows((Procedimiento)result);
			}

			public void onFailure(Throwable caught) {

				Window.alert(mensajeError.accionNoRealizada(100,
						"removeStage o removeSyncNode", caught
								.getLocalizedMessage()));
			}
		};
		
		

		if (seleccionado == null) {
			RectilinearTwoEndedConnectionCustom.eliminarFlujo(idProcedimiento);
		}
		// Borrar todos los que hubiera seleccionado, para ello tenemos que
		// saber de que
		// tipo de nodo estamos hablando
		else if (Html.contains(seleccionado.getStyleName(),
				varDefs.styleToolboxNode)
				|| Html.contains(seleccionado.getStyleName(),
						varDefs.styleToolboxPhaseEspecifica)) {

			servicio.removeStage(idProcedimiento, DOM.getElementPropertyInt(
					seleccionado.getElement(), "id"), callback);
		} else if (Html.contains(seleccionado.getStyleName(),
				varDefs.styleToolboxNodoSinc)) {
			servicio.removeSyncNode(idProcedimiento, DOM.getElementPropertyInt(
					seleccionado.getElement(), "id"), callback);
		}

	}

	public static void addConexionesBorrar(String id) {
		conexionesBorradas.add(id);
	}

	/**
	 * 
	 * @param estado
	 *            String con el literal asociado al estado del procedimiento o
	 *            subproceso que estemos tratando
	 */
	public void asignarNombreEstado(String estado) {

		Element nombreProc = infoPanel.getWidget(0).getElement();
		DOM.setInnerText(nombreProc, procedimiento.getNombreProc() + "  "
				+ estado);
	}

	/**
	 * En funcion del estado en el que se encuentre el procedimiento o
	 * subproceso se podrá realizar unas acciones u otras
	 */
	protected void gestionarPermisos() {

		if (procedimiento.getEstado() == varDefs.borrador) {
			readOnly=false;
			asignarNombreEstado(mensajes.stateDraft());
			procedimiento.setEstado(varDefs.borrador);
			construirPopUpFase(true);
			construirPopUpNodo();
			toolbox.setStyleName("toolboxVisible");
			popUpNodo.hide();

			popUpNodo.setVisible(false);
			vp.add(popUpNodo);
		} else {
			
			construirPopUpFase(false);
			toolbox.setStyleName("toolboxHidden");

			if (procedimiento.getEstado() == varDefs.vigente) {
				procedimiento.setEstado(varDefs.vigente);
				asignarNombreEstado(mensajes.stateCurrent());
			} else {
				procedimiento.setEstado(varDefs.historico);
				readOnly=true;
				asignarNombreEstado(mensajes.stateOld());
			}

		}
		popUpFase.setVisible(false);
		popUpFase.hide();
		vp.add(popUpFase);

	}
	
	public void updateNewFlows(Procedimiento proc){
		List flujo = new ArrayList();
		flujo = proc.getFlujos();
		List flujosExistentes=procedimiento.getFlujos();
		for (int i = 0; i < flujo.size(); i++) {
			DrawFlow aux = (DrawFlow) flujo.get(i);
			boolean encontrado=false;
			for(int j=0; j<flujosExistentes.size() && !encontrado; j++){
				if(((DrawFlow) flujo.get(j)).getId()==aux.getId()){
					encontrado=true;
				}
			}
			
			if(!encontrado)
			flujos.put(new String(aux.getIdS()), aux);
			
		}
		try {
			super.updateDiagrama();
		} catch (DesignerException e) {
			Window
			.alert(mensajeError
					.accionNoRealizada(
							100,
							"updateDiagrama",
							e.getLocalizedMessage()));
}
			
	
		
	}
	public void precargarDatos(Procedimiento proc) {

		procedimiento.setNombreProc(proc.getNombreProc());
		procedimiento.setEstado(proc.getEstado());
		procedimiento.setTipoProcedimiento(proc.isTipoProcedimiento());
		procedimiento.setIdProc(proc.getIdProc());
		if(procedimiento.getEstado()==varDefs.vigente)
			readOnly=true;
		gestionarPermisos();
		Element tbody = DOM.getChild(toolbox.getElement(), 0);

		String literal;
		if (procedimiento.isTipoProcedimiento()) {

			DOM.setElementAttribute(FaseActividad.getElement(), "title",
					mensajes.tooltipFase());
			literal = varDefs.FASE;
		} else {
			DOM.setElementAttribute(FaseActividad.getElement(), "title",
					mensajes.tooltipActividad());
			literal = varDefs.ACTIVIDAD;
		}
		Html.changeLiteralNodo(literal, tbody);

		// Estado del procedimiento o subproceso

		// Tenemos que ir guardando las fases creadas (labels) para luego hacer
		// los conectores

		List fases = new ArrayList();
		fases = proc.getFases();

		tramitesFases = new HashMap();
		tramitesFases = proc.getTramitesFases();

		int indiceSinPos = 0;
		// Precargamos las fases
		for (int i = 0; i < fases.size(); i++) {
			DrawObject aux = (DrawObject) fases.get(i);

			if (aux.getLeft() == 0 || aux.getTop() == 0) {
				int numFila = indiceSinPos / 5;
				aux.setTop(100 + (indiceSinPos / 5) * 100);
				if (numFila % 2 == 0)
					aux.setLeft(20 + (indiceSinPos % 5) * 150);
				else
					aux.setLeft(20 + 750 - (indiceSinPos % 5 + 1) * 150);

				indiceSinPos += 1;
			}
			Integer id = new Integer(aux.getId());
			String texto = "";

			Widget p = createElemento(aux, varDefs.stylePhase, varDefs.EFASE,
					false);
			boolean borrar = true;
			if (tramitesFases.containsKey(id)) {
				texto = aux.getNombre();
				List tramites = new ArrayList();
				tramites = (List) tramitesFases.get(id);

				for (int j = 0; j < tramites.size(); j++) {
					borrar = false;
					TramiteFase auxTram = (TramiteFase) tramites.get(j);

					addTramite(aux.getId(), auxTram.getNombre(), auxTram
							.getEstado(), aux.isDesplegado(), auxTram
							.getSubTaskId(), auxTram.getPTaskId(), p
							.getElement());
				}

			}
			if (borrar == true) {
				Element tr = DOM.getChild(DOM.getChild(DOM.getChild(p
						.getElement(), 0), 0), 0);
				DOM.removeChild(tr, DOM.getChild(tr, 0));
				// Html.deletePlus(aux.getId());
			}

			p.addStyleName(varDefs.styleToolboxNode);
			if (aux.getTipo() == -1)
				p.addStyleName(varDefs.styleToolboxPhaseEspecifica);

			elementos.put(id, aux);
			objetos.put(id, p);

		}

		List nodos = new ArrayList();
		nodos = proc.getNodos();
		// Precargamos los nodos
		for (int i = 0; i < nodos.size(); i++) {
			DrawObject aux = (DrawObject) nodos.get(i);
			if (aux.getLeft() == 0 || aux.getTop() == 0) {
				int numFila = indiceSinPos / 5;
				aux.setTop(100 + (indiceSinPos / 5) * 100);
				if (numFila % 2 == 0)
					aux.setLeft(20 + (indiceSinPos % 5) * 150);
				else
					aux.setLeft(20 + 750 - (indiceSinPos % 5 + 1) * 150);

				indiceSinPos += 1;
			}
			Integer id = new Integer(aux.getId());
			Widget p = createElemento(aux, varDefs.styleNode, varDefs.ENODO,
					false);
			p.addStyleName(varDefs.styleToolboxNodoSinc);

			elementos.put(id, aux);
			objetos.put(id, p);

		}

		// Construimos los flujos

		List flujo = new ArrayList();
		flujo = proc.getFlujos();
		for (int i = 0; i < flujo.size(); i++) {
			DrawFlow aux = (DrawFlow) flujo.get(i);
			flujos.put(new String(aux.getIdS()), aux);
			// Obtengo los objetos a conectar
			Label origen = (Label) objetos.get(new Integer(aux.getIdOrigen()));
			Label destino = (Label) objetos
					.get(new Integer(aux.getIdDestino()));
			try {
				connectManual(UIObjectConnector.wrap(origen), UIObjectConnector
						.wrap(destino));
			} catch (DesignerException e) {
				// TODO Auto-generated catch block
				Window.alert( DiagramBuilderExample.mensajeError.accionNoRealizada(100, 

						"connectManual",e.getLocalizedMessage()));
			}
			if(aux.getEvents()!=null && aux.getEvents().size()>0)
			
				addCondicion(aux);

			addArrow(aux);

		}

		// Eliminar todas las lista creadas , destruirlas
		fases.clear();
		flujo.clear();
		nodos.clear();

		Element area = DOM.getElementById("area");

	}

	public static void cambiarSeleccionado() {
	
		if (seleccionado != null ) {

			seleccionado.removeStyleName(varDefs.seleccionado);
			seleccionado.removeStyleName("conectar");
		}

	}

	public static  void accionesElemento(Event event, Widget obj) {

		if (obj != null ) {

			if (DOM.eventGetType(event) == Event.ONCLICK ||DOM.eventGetType(event) == Event.ONMOUSEUP) {

				if (procedimiento.getEstado() == varDefs.borrador && conectar
						//&& seleccionarConectar == 1
						) {

					seleccionado = obj;
					if (seleccionado!= null)
						select(obj);

				}

				if (!conectar) {

					popUpFase.hide();
					if (RectilinearTwoEndedConnectionCustom.imagenSel != null){
						
						
						RectilinearTwoEndedConnectionCustom.cambiarStilo();
						
					RectilinearTwoEndedConnectionCustom.imagenSel = null;
					}
					if (seleccionado != null) {

						cambiarSeleccionado();
					}
					if (obj.getElement() != null)
						obj.addStyleName(varDefs.seleccionado);
					seleccionado = obj;
                   sel = true;

				}

			}

			// Para seleccionar un elemento es pulsando sobre Ã©l con el
			// raton botton derecho
			// Para sacar el menu
			if (DOM.eventGetButton(event) == Event.BUTTON_RIGHT && !conectar) {

				sel = true;
				popUpFase.hide();
				if (seleccionado != null ) {

					cambiarSeleccionado();
				}
				seleccionado = obj;

				if (RectilinearTwoEndedConnectionCustom.imagenSel != null)
					RectilinearTwoEndedConnectionCustom.cambiarStilo();
				showPopUp(event, obj);

			}

			
		}

	}

	/*protected void createNodo(Widget eleCreado, int tipo) {

		eleCreado.setStyleName(varDefs.styleNode);

		String img = "";
		if (tipo == 1)
			img = "<img src=./img/And.JPG />";
		else
			img = "<img src=./img/Or.JPG />";

		HTML html = new HTML(img);
		DOM.appendChild(eleCreado.getElement(), html.getElement());
		
		
	}*/

	protected void createFase(Widget eleCreado, String text, boolean desplegado) {

		
		if(!returnFromSubProc ){
		Element table = DOM.createTable();
		DOM.setElementAttribute(table, "cellspacing", "1");

		Element tbody = DOM.createTBody();
		Element tr = DOM.createTR();
		Element td = DOM.createTD();
		DOM.setElementAttribute(tbody, "id", "tablaNodo");
		Element td1tr = DOM.createTD();
		if (desplegado) {

			DOM.appendChild(td1tr, addImgMinus().getElement());

		} else {
			DOM.appendChild(td1tr, addImgPlus().getElement());
		}

	
		DOM.appendChild(tr, td1tr);

		DOM.setInnerText(td, text);
		DOM.setElementAttribute(td, "id", "cabeceraNodo");
		DOM.appendChild(tr, td);
		DOM.appendChild(tbody, tr);
		DOM.appendChild(table, tbody);
		DOM.appendChild(eleCreado.getElement(), table);

		eleCreado.addStyleName("sizeFase");
		}
		else{
			super.createFase(eleCreado, text, desplegado);
		}

	}

	/**
	 * 
	 * @param text
	 *            Texto que se muestra dentro de la cajita del fase y nodo
	 * @param left
	 *            Posicion left del objeto
	 * @param top
	 *            Posicion top del objeto
	 * @param style
	 *            Estilo de objeto , si es nodo o fase
	 * @param id
	 *            Id que tiene le objeto.
	 * @return El objeto conector
	 */
	public static int seleccionarConectar = 0;

	protected Widget createElemento(DrawObject obj, String style, int tipo,
			boolean desplegado) {
		
		Widget eleCreado=null;
		if (tipo != varDefs.ENODO) {
			eleCreado = new Phase();
			createFase(eleCreado, obj.getNombre(), desplegado);

		} else {
			eleCreado = new Node();
			
			createNodo(eleCreado, obj.getTipo());

		}

		//accionesElemento(event, this);
		if (obj.getId() != -1) {
			DOM.setElementAttribute(eleCreado.getElement(), "id", ""
					+ obj.getId());
		}
		getArea().add(eleCreado, obj.getLeft(), obj.getTop());
		dragController.makeDraggable(eleCreado);
		eleCreado.addStyleName(style);

		return eleCreado;
	}

	private Widget createToolboxNode(String label, String style) {

		FaseActividad = new Label(label);
		if (style.compareTo(varDefs.SFASE) == 0) {

			FaseActividad.setStyleName(varDefs.styleToolboxNode);
		}
		FaseActividad.setSize("15px", "16px");

		DOM.setElementAttribute(FaseActividad.getElement(), "id", label);

		FaseActividad.addStyleName(style);
		String auxiliar = DOM.getInnerText(FaseActividad.getElement());

		if (DOM.getInnerText(FaseActividad.getElement()).compareTo(
				varDefs.ACTIVIDAD) == 0)
			DOM.setElementAttribute(FaseActividad.getElement(), "title",
					mensajes.tooltipActividad());
		else
			DOM.setElementAttribute(FaseActividad.getElement(), "title",
					mensajes.tooltipFase());

		toolbox.add(FaseActividad);
		
		toolbox.setCellVerticalAlignment(FaseActividad,
				HorizontalPanel.ALIGN_MIDDLE);
		toolboxDragController.makeDraggable(FaseActividad);

		return FaseActividad;
	}

	private static List selected = new ArrayList();

	// private RectilinearTwoEndedConnectionCustom transicionCreada = null;

	private static void eliminarSeleccionados() {

		if (selected.size() > 0) {

			cambiarSeleccionado();
			selected.clear();

		}

	}

	private static DrawFlow transicionNew;

	public static void select(Widget w) {

		if (selected.isEmpty() && w.getElement() != null) {

			// guardarPosiciones();

			w.addStyleName(varDefs.selConector);
			selected.add(w);
			sel = true;
			w.addStyleName("conectar");

		} else if (selected.contains(w) && selected.size() == 1) {

			w.addStyleName(varDefs.selConector);
			sel = true;
			w.addStyleName("conectar");
			limpiarModoConexion();
		}

		else if (!selected.contains(w) && selected.size() == 1) {

			conectar = false;
			Widget w2 = (Widget) selected.get(0);

			w2.removeStyleName("conectar");

			w.removeStyleName("conectar");

			w.removeStyleName(varDefs.selConector);
			w2.removeStyleName(varDefs.selConector);
			w.removeStyleName(varDefs.selConector);
			UIObjectConnector or = UIObjectConnector.getWrapper(w2);

			if (or == null) {
				UIObjectConnector.wrap(w2);
				or = UIObjectConnector.getWrapper(w2);
			}
			UIObjectConnector dst = UIObjectConnector.getWrapper(w);
			if (dst == null) {
				UIObjectConnector.wrap(w);
				dst = UIObjectConnector.getWrapper(w);
			}

			int idOrigen = DOM.getElementPropertyInt(w2.getElement(), "id");
			int idDestino = DOM.getElementPropertyInt(w.getElement(), "id");
			if (Validates.existFlow(idOrigen, idDestino)) {
				conectar = true;

				Window.alert(mensajes.flowExists());

				selected.removeAll(selected);
			}

		
			else {

				connect(or, dst);

				RectilinearTwoEndedConnectionCustom.imagen = null;
				RectilinearTwoEndedConnectionCustom.imagen = null;
				// 3) Creamos un objeto asincrono para manejar el resultado
				AsyncCallback callback = new AsyncCallback() {
					public void onSuccess(Object result) {
						if (desconectarAutomatico)
							conectar = true;

						Element e = transicionCreada.getElement();

						transicionNew
								.setId(Integer.parseInt(result.toString()));
						flujos.put(new String(transicionNew.getIdS()),
								transicionNew);
						DOM
								.setElementAttribute(e, "id", transicionNew
										.getIdS());
						transicionCreada.update();
						Element ep = DOM.getElementById(varDefs.pIdFlowTemp);
						
						if (ep != null)
							addArrow(transicionNew);
						transicionCreada.update();

						limpiarModoConexion();
						RectilinearTwoEndedConnectionCustom.imagen = null;

					}

					public void onFailure(Throwable caught) {
						Window.alert(mensajeError.accionNoRealizada(100,
								"addFlow", caught.getLocalizedMessage()));
					}
				};

				transicionNew = new DrawFlow(-1, DOM.getElementPropertyInt(w2
						.getElement(), "id"), DOM.getElementPropertyInt(w
						.getElement(), "id"));
				servicio.addFlow(idProcedimiento, DOM.getElementPropertyInt(w2
						.getElement(), "id"), DOM.getElementPropertyInt(w
						.getElement(), "id"), callback);

			}

		}

	}

	protected static void limpiarModoConexion() {

		eliminarSeleccionados();

		if (!desconectarAutomatico) {
			conectar = false;
			salirModoConexion();

			// seleccionado=new Widget();
			selected.clear();

		}
		seleccionado = null;

	}

	protected static void showPopUp(Event event, Widget w) {
		seleccionado = w;

		String textoObj = DOM.getInnerText(w.getElement());

		int clientX = DOM.eventGetClientX(event);
		int clientY = DOM.eventGetClientY(event);

		if (Html.contains(textoObj, varDefs.FASE)) {
			popUpFase.setVisible(true);
			popUpFase.show();
			popUpFase.setPopupPosition(w.getAbsoluteLeft() + 15, w
					.getAbsoluteTop() + 20);
		} else if (procedimiento.getEstado() == varDefs.borrador) {
			// Hay que diferenciar entre una fase especifica y un nodo
			popUpNodo.setVisible(true);
			popUpNodo.show();
			popUpNodo.setPopupPosition(w.getAbsoluteLeft() + 15, w
					.getAbsoluteTop() + 20);
		}

	}

	String faseSel = "";
	int idSel;

	private Element createFaseOption(final String fase, final int id) {
		Integer aux = new Integer(id);
		RadioButton rb = new RadioButton(aux.toString(), fase);
		rb.setName("grupo1");
		DOM.sinkEvents(rb.getElement(), Event.ONCLICK);
		DOM.setEventListener(rb.getElement(), new EventListener() {
			public void onBrowserEvent(Event event) {

				faseSel = fase;
				idSel = id;

			}
		});

		Element tr = DOM.createTR();
		Element td = DOM.createTD();

		DOM.appendChild(td, rb.getElement());
		DOM.appendChild(tr, td);

		return tr;
	}

	public void closeTram() {
		imgTram.removeFromParent();
		imgTram.setVisible(false);

	}

	public static TextBox textBox = null;

	public void construirNuevoNodo(String texto) {

		Element divListado = DOM.getElementById(varDefs.divListado);

		construirCabecera(divListado, "Asociar " + texto);
		Element e = DOM.getElementById("divModal");
		construirTituloNivel2SinBckColor(e, "Establecer elemento");

		Element dPunteado = DOM.createDiv();
		Element tableNuevaFase = DOM.createTable();
		Element body = DOM.createTBody();

		Element tr = DOM.createTR();
		Element td = DOM.createTD();

		textBox = new TextBox();
		textBox.setText("");

		DOM.setElementAttribute(textBox.getElement(), "id", "nuevaFase");

		DOM.appendChild(td, new Label(mensajes.elementName()).getElement());
		DOM.appendChild(tr, td);
		td = DOM.createTD();
		DOM.appendChild(td, textBox.getElement());
		DOM.appendChild(tr, td);
		td = DOM.createTD();
		addButton(mensajes.buttonSelect(), Event.ONCLICK, td, varDefs.FASENUEVA);
		DOM.appendChild(tr, td);
		DOM.appendChild(body, tr);
		DOM.appendChild(tableNuevaFase, body);
		DOM.appendChild(dPunteado, tableNuevaFase);
		DOM.setElementProperty(dPunteado, "id", "punteado");

		DOM.appendChild(e, dPunteado);
	}

	public void construirListado(List fases) {
		Element divListado = DOM.getElementById(varDefs.divListado);
		construirNuevoNodo("FASE");
		Element e = DOM.getElementById("divModal");
		addBr(e);
		construirTituloNivel2SinBckColor(e, mensajes.selectElement());

		Element dPunteado = DOM.createDiv();
		DOM.setElementProperty(dPunteado, "id", "punteadoN2");
		DOM.appendChild(dPunteado, new Label(fases.size()
				+ mensajes.registersFind()).getElement());
		DOM.appendChild(e, dPunteado);

		construirContenedorListadoNuevaFase(e, fases);

	}

	protected void createNuevoElemento(Object result, int tipo) {

		DOM.setElementAttribute(l.getElement(), "id", "" + result);

		DrawObject nuevaPhase = new DrawObject();
		int id = Integer.parseInt(result.toString());
		nuevaPhase.setId(id);
		nuevaPhase.setLeft(l.getAbsoluteLeft());
		nuevaPhase.setTop(l.getAbsoluteTop());

		if (tipo == varDefs.EFASE) {
			Element table = DOM.getChild(l.getElement(), 0);
			Element tbody = DOM.getChild(table, 0);
			Element tr = DOM.getChild(tbody, 0);
			Element td = DOM.getChild(tr, 0);
			if (Html.contains(l.getStyleName(),
					varDefs.styleToolboxPhaseEspecifica)) {
				Element td2 = DOM.getChild(tr, 1);
				nuevaPhase.setNombre(DOM.getInnerText(td2));
				DOM.removeChild(tr, td);

			} else {

				nuevaPhase.setNombre(DOM.getInnerHTML(td));
			}

		} else {

			if (varDefs.TIPONODOAND == tipo) {
				nuevaPhase.setTipo(varDefs.TIPONODOAND);
				nuevaPhase.setNombre("Nodo AND");
			} else {
				nuevaPhase.setNombre("Nodo OR");
				nuevaPhase.setTipo(varDefs.TIPONODOOR);
			}

		}

		elementos.put(new Integer(id), nuevaPhase);

	}

	static int x;
	static int y;

	protected void addButton(String mensaje, int idEvento, Element padre,
			int tipo) {
		Button but = new Button(mensaje);
		DOM.setElementAttribute(but.getElement(), "id", "" + tipo);
		DOM.sinkEvents(but.getElement(), Event.ONCLICK);
		DOM.appendChild(padre, but.getElement());

		if (tipo == varDefs.FASECATALOGO) {
			DOM.setEventListener(but.getElement(), new EventListener() {
				public void onBrowserEvent(Event event) {

					Element e1 = DOM.getCaptureElement();
					Element table = DOM.getChild(l.getElement(), 0);
					Element tbody = DOM.getChild(table, 0);
					Element tr = DOM.getChild(tbody, 0);
					Element td = DOM.getChild(tr, 0);
					DOM.setInnerText(td, faseSel);
					// 3) Creamos un objeto asincrono para manejar el resultado
					AsyncCallback callback = new AsyncCallback() {
						public void onSuccess(Object result) {
							createNuevoElemento(result, varDefs.EFASE);
						}

						public void onFailure(Throwable caught) {
							
							l.removeFromParent();
							Window.alert(mensajeError.accionNoRealizada(100,
									"addStage", caught.getLocalizedMessage()));
						}
					};

					servicio.addStage(idProcedimiento, idSel, x, y, callback);
					Element listado = DOM.getElementById(varDefs.divListado);
					Html.removeAllChildren(listado);
					Designer.listadoOculto();

				}

			});
		}

		else {
			DOM.setEventListener(but.getElement(), new EventListener() {
				public void onBrowserEvent(Event event) {

					String nuevaFase = textBox.getText();
					;
                     if(nuevaFase.compareTo("")==0)
                    	 Window.alert(mensajes.nameStage());
                     else{
					Element table = DOM.getChild(l.getElement(), 0);
					Element tbody = DOM.getChild(table, 0);
					Element tr = DOM.getChild(tbody, 0);
					Element td = DOM.getChild(tr, 1);

					DOM.setInnerHTML(td, nuevaFase);

					l.addStyleName(varDefs.styleToolboxPhaseEspecifica);

					// 3) Creamos un objeto asincrono para manejar el resultado
					AsyncCallback callback = new AsyncCallback() {
						public void onSuccess(Object result) {

							createNuevoElemento(result, varDefs.EFASE);

						}

						public void onFailure(Throwable caught) {

							l.removeFromParent();
							Window.alert(mensajeError.accionNoRealizada(100,
									"addStage", caught.getLocalizedMessage()));
						}
					};

					servicio.addStage(idProcedimiento, nuevaFase, x, y,
							callback);

					Element listado = DOM.getElementById(varDefs.divListado);
					int numChild = DOM.getChildCount(listado);
					for (int i = numChild - 1; i >= 0; i--) {
						DOM.removeChild(listado, DOM.getChild(listado, i));
					}

					Designer.listadoOculto();

				}
			}});

		}

	}

	int conector = 0;

	int tipoOrAnd;

	protected UIObjectConnector accionEjecutar(Label proxy, AbsolutePanel panel) {
		conector = 0;

		l = new Label() {

			public void onBrowserEvent(Event event) {

				if (DOM.eventGetType(event) == Event.ONCLICK)
					seleccionarConectar += 1;
				else
					seleccionarConectar = 0;

				accionesElemento(event, this);

				super.onBrowserEvent(event);
			}

		};

		// 3) Creamos un objeto asincrono para manejar el resultado
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {

				Element diagrama = DOM.getElementById("diagrama");

				Designer.listadoVisible();
				Element listado = DOM.getElementById(varDefs.divListado);

				if (DOM.getChildCount(listado) == 0 && result != null) {

					construirListado((List) result);
					verVentanaModal();
				}

			}

			public void onFailure(Throwable caught) {

				Window.alert(mensajeError.accionNoRealizada(100, "getCtStages",
						caught.getLocalizedMessage()));

			}
		};

		String tipo = DOM.getElementAttribute(proxy.getElement(), "id");

		tipoOrAnd = 0;

		if (tipo != null && tipo.compareTo(varDefs.AND) == 0) {
			tipoOrAnd = varDefs.TIPONODOAND;
			createNodo(l, varDefs.TIPONODOAND);
		} else if (tipo != null && tipo.compareTo(varDefs.OR) == 0) {
			tipoOrAnd = varDefs.TIPONODOOR;
			createNodo(l, varDefs.TIPONODOOR);
		} else {

			DrawObject dObj = new DrawObject();
			dObj.setNombre(proxy.getText());
			dObj.setDesplegado(false);
			dObj.setId(-1);
			l = createElemento(dObj, varDefs.stylePhase, varDefs.EFASE, false);

		}

		if (conector == 0) {

			if (proxy.getText().compareTo(varDefs.FASE) == 0) {
				// 4)Realizar la llamada
				// Solo se ha de realizar la llamada cuando el usuario este
				// construyendo una fase

				l.addStyleName(varDefs.styleToolboxNode);
				l.addStyleName(varDefs.stylePhase);
				servicio.getCtStages(idProcedimiento, callback);

			} else if (proxy.getText().compareTo(varDefs.ACTIVIDAD) == 0) {

				l.addStyleName(varDefs.styleToolboxNode);
				l.addStyleName(varDefs.stylePhase);
				construirNuevoNodo("Actividad");
				Designer.listadoVisible();
			}

			else {
				l.addStyleName(varDefs.styleToolboxNodoSinc);

				// 3) Creamos un objeto asincrono para manejar el resultado
				callback = new AsyncCallback() {
					public void onSuccess(Object result) {

						createNuevoElemento(result, tipoOrAnd);

					}

					public void onFailure(Throwable caught) {

						l.removeFromParent();
						Window.alert(mensajeError.accionNoRealizada(100,
								"addSyncNode", caught.getLocalizedMessage()));

					}
				};

				servicio
						.addSyncNode(idProcedimiento, tipoOrAnd, x, y, callback);

				l.addStyleName(varDefs.styleNode);
			}

			DOM.setElementProperty(l.getElement(), "position", "absolute");

			DOM.setElementAttribute(l.getElement(), "position", "absolute");

			panel.add(l, proxy.getAbsoluteLeft() - panel.getAbsoluteLeft(),
					proxy.getAbsoluteTop() - panel.getAbsoluteTop());

			x = l.getAbsoluteLeft();
			y = l.getAbsoluteTop();

			dragController.makeDraggable(l);

		}

		return UIObjectConnector.wrap(l);
	}

	public  static final void connect(Connector a, Connector b) {
		RectilinearTwoEndedConnectionCustom aux = null;
		try {
			aux = new RectilinearTwoEndedConnectionCustom(
					idProcedimiento, a, b);
		} catch (DesignerException e) {
			Window.alert( DiagramBuilderExample.mensajeError.accionNoRealizada(100, 

					"RectilinearTwoEndeConnectionCustom",e.getLocalizedMessage()));
		}
	
		addManual(aux);

		transicionCreada = aux;
	}

	protected void connectManual(Connector a, Connector b) throws DesignerException {

		RectilinearTwoEndedConnectionCustom aux = new RectilinearTwoEndedConnectionCustom(
				idProcedimiento, a, b);

		addManual(aux);

		transicionCreada = aux;

	}

	public void setStyle(String style) {
		this.style = style;
	}

	protected static void add(AbstractConnection c) {

		AbstractConnectionsExample.add(c);
		c.update();
	}

	protected static void addManual(AbstractConnection c) {

		AbstractConnectionsExample.add(c);

	}

	protected void construirPopUpNodo() {

		popUpNodo = new MenuPopUpPanel();
		DOM.setElementAttribute(popUpNodo.getElement(), "id", "PopUpNodo");

		HTML eliminarNodo;
		eliminarNodo = new HTML(Html.label(mensajes.delete()));
		eliminarNodo.addStyleName("seleccionable");
		popUpNodo.addMenuItem(eliminarNodo);
		eliminarNodo.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {

				popUpNodo.hide();
				if (Window.confirm(mensajes.confirmDeleteNode())) {
					if (seleccionado.getElement() != null) {

						// 3) Creamos un objeto asincrono para manejar el
						// resultado
						AsyncCallback callback = new AsyncCallback() {
							public void onSuccess(Object result) {
								if (seleccionado.getElement() != null) {
									eliminarConexion(seleccionado);
									int idNodo = DOM.getElementPropertyInt(
											seleccionado.getElement(), "id");
									elementos.remove(new Integer(idNodo));
									Validates.deleteAllNodeConnect(idNodo);
									seleccionado.removeFromParent();
									selected.removeAll(selected);
									seleccionado = null;

								}
								updateNewFlows((Procedimiento)result);
							}

							public void onFailure(Throwable caught) {

								Window.alert(mensajeError.accionNoRealizada(
										100, "removeSyncNode", caught
												.getLocalizedMessage()));

							}
						};

						// Si es un nodo o es una fase especifica , como tienen
						// el mismo menu
						// ambas pueden llegar a este punto
						if (!Html.contains(seleccionado.getStyleName(),
								varDefs.styleToolboxPhaseEspecifica))
							servicio.removeSyncNode(idProcedimiento, DOM
									.getElementPropertyInt(seleccionado
											.getElement(), "id"), callback);

						else {

							if (seleccionado.getElement() != null) {

								callback = new AsyncCallback() {
									public void onSuccess(Object result) {
										int idFase = DOM
												.getElementPropertyInt(
														seleccionado
																.getElement(),
														"id");
										Validates.deleteAllNodeConnect(idFase);
										elementos.remove(new Integer(idFase));
										eliminarConexion(seleccionado);
										seleccionado = null;
										selected.removeAll(selected);
										updateNewFlows((Procedimiento)result);
									}

									public void onFailure(Throwable caught) {

										Window
												.alert(mensajeError
														.accionNoRealizada(
																100,
																"removeStage",
																caught
																		.getLocalizedMessage()));

									}
								};

								int id = DOM.getElementPropertyInt(seleccionado
										.getElement(), "id");

								servicio.removeStage(idProcedimiento, id,
										callback);

							} else {
								seleccionado = null;
							}
						}
						// Borrar todos los que hubiera seleccionado

					}

				} else {

					selected.removeAll(selected);
					seleccionado = null;
				}

			}

		});

	}

	protected void construirPopUpFase(boolean borrador) {

		popUpFase = new MenuPopUpPanel();
		DOM.setElementAttribute(popUpFase.getElement(), "id", "PopUpFase");
		HTML tramite;

		tramite = new HTML(Html.label(mensajes.tramites()));
		//tramite.addStyleName("seleccionable");
		popUpFase.addMenuItem(tramite);
		// Vamos a aÃ±adir un listener para que hago algo cuando clicamos en el
		tramite.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {

				popUpFase.hide();

				if (seleccionado!= null) {

					AsyncCallback callback = new AsyncCallback() {
						public void onSuccess(Object result) {
							Designer.tramitesVisible();
							Element tramites = DOM
									.getElementById(varDefs.divTramites);
							if (DOM.getChildCount(tramites) == 0
									&& result != null) {

								gestionarTramites(seleccionado, (List) result);
								selected.removeAll(selected);
								seleccionado = null;
							}

						}

						public void onFailure(Throwable caught) {

							Window.alert(mensajeError.accionNoRealizada(100,
									"getTasks", caught.getLocalizedMessage()));

						}
					};

					// 4)Realizar la llamada

					servicio.getTasks(DOM.getElementPropertyInt(seleccionado
							.getElement(), "id"), callback);
					// }
				} else {
					selected.removeAll(selected);
					seleccionado = null;
				}

			}

		});

		if (borrador) {

			popUpFase.addSeparator();
			HTML eliminar;

			// eliminar = new HTML(Html.imageWithText("img/borrar.jpg",
			// "Eliminar"));
			eliminar = new HTML(Html.label(mensajes.delete()));
			eliminar.addStyleName("seleccionable");
			popUpFase.addMenuItem(eliminar);
			eliminar.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {

					popUpFase.hide();
					if (Window.confirm(mensajes.confirmDeletePhase())) {
						if (seleccionado != null) {

							AsyncCallback callback = new AsyncCallback() {
								public void onSuccess(Object result) {
									int idFase = DOM.getElementPropertyInt(
											seleccionado.getElement(), "id");

									Validates.deleteAllNodeConnect(idFase);
									elementos.remove(new Integer(idFase));
									eliminarConexion(seleccionado);
									// removeAllConnectors(seleccionado);
									seleccionado = null;
									selected.removeAll(selected);
									
									updateNewFlows((Procedimiento)result);
								}

								public void onFailure(Throwable caught) {

									Window
											.alert(mensajeError
													.accionNoRealizada(
															100,
															"removeStage",
															caught
																	.getLocalizedMessage()));

								}
							};

							int id = DOM.getElementPropertyInt(seleccionado
									.getElement(), "id");

							servicio.removeStage(idProcedimiento, id, callback);

						}

					} else {
						seleccionado = null;
					}

				}

			});
		}

	}

	Widget tempWidget = new Widget();

	private void gestionarTramites(Widget w, List tramites) {
		tempWidget = w;

		int idFase = DOM.getElementPropertyInt(w.getElement(), "id");

		// 3) Creamos un objeto asincrono para manejar el resultado
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {

				// Mostramos los tramites actuales
				List tramitesFase = new ArrayList();
				tramitesFase = (List) result;
				construirTramites(tramitesFase);
				verVentanaModal();

			}

			public void onFailure(Throwable caught) {

				Window.alert(mensajeError.accionNoRealizada(100, "getTasks",
						caught.getLocalizedMessage()));

			}
		};

		// 4)Realizar la llamada
		servicio.getTasks(idFase, callback);

		dragController.restoreDraggableStyle(w);

	}

	public Map tramitesTratados;

	public void seleccionTramites(String label) {

		// Acciones de eliminar o añadir
		boolean hayCheckeado = false;
		if (label.compareTo("Eliminar") == 0 || label.compareTo("Add") == 0) {
			Element origen, destino;
			if (label.compareTo("Eliminar") == 0) {
				origen = DOM.getElementById("bodySeleccionados");
				destino = DOM.getElementById("bodyDisponibles");

			} else {
				destino = DOM.getElementById("bodySeleccionados");
				origen = DOM.getElementById("bodyDisponibles");

			}
			// Recorro las filas de la tabla origen y trato cada uno que este
			// checkeado y lo reseteo

			Element body = DOM.getChild(origen, 0);
			int numChild = DOM.getChildCount(body);

			for (int i = 1; i < numChild; i++) {
				Element tr = DOM.getChild(body, i);
				// Obtengo el check

				Element td = DOM.getChild(tr, 0);
				Element hijo1 = DOM.getChild(td, 0);
				Element input = DOM.getChild(hijo1, 0);

				boolean checked = DOM.getElementPropertyBoolean(input,
						"checked");

				if (checked) {
					hayCheckeado = true;
					// Obtengo el id

					int id = DOM.getElementPropertyInt(tr, "id");
					((TramiteFase) tramitesTratados.get(new Integer(id)))
							.changeAsociado();
					// Lo borro de esta tabla y lo pongo en la otra

					DOM.removeChild(body, tr);
					numChild = numChild - 1;
					i = i - 1;

					DOM.appendChild(DOM.getChild(destino, 0), tr);

				}

			}

		}

		// Subir o bajar
		else {
			Element origen = DOM.getElementById("bodySeleccionados");
			Element body = DOM.getChild(origen, 0);
			int numChild = DOM.getChildCount(body);

			List nuevoOrden = new ArrayList();

			if (label.compareTo("Subir") == 0) {

				for (int i = 1; i < numChild; i++) {

					Element tr = DOM.getChild(body, i);
					// Obtengo el check

					Element td = DOM.getChild(tr, 0);
					Element hijo1 = DOM.getChild(td, 0);
					Element input = DOM.getChild(hijo1, 0);

					boolean checked = DOM.getElementPropertyBoolean(input,
							"checked");
					if (checked)
						hayCheckeado = true;
					if (!checked || i == 1)
						nuevoOrden.add(tr);

					else if (checked && i != 1) {
						Element trAnterior = (Element) nuevoOrden.get(i - 2);
						nuevoOrden.set(i - 2, tr);
						nuevoOrden.add(trAnterior);

					}

				}
			} else if (label.compareTo("Bajar") == 0) {

				for (int i = numChild - 1; i >= 1; i--) {

					Element tr = DOM.getChild(body, i);
					// Obtengo el check

					Element td = DOM.getChild(tr, 0);
					Element hijo1 = DOM.getChild(td, 0);
					Element input = DOM.getChild(hijo1, 0);

					boolean checked = DOM.getElementPropertyBoolean(input,
							"checked");
					if (checked)
						hayCheckeado = true;
					if (!checked || i == numChild - 1)
						nuevoOrden.add(0, tr);

					else if (checked && i != numChild - 1) {
						Element trSgt = (Element) nuevoOrden.get(0);
						nuevoOrden.set(0, tr);
						nuevoOrden.add(0, trSgt);

					}

				}

			}

			// Eliminar todos los hijos desde 1 hasta numChild

			for (int i = 0; i < nuevoOrden.size(); i++) {

				DOM.removeChild(body, (Element) nuevoOrden.get(i));

			}
			for (int i = 0; i < nuevoOrden.size(); i++) {

				DOM.appendChild(body, (Element) nuevoOrden.get(i));

			}

		}

		if (!hayCheckeado) {
			Window.alert(mensajes.infoTramite());
		}

	}

	public ListBox lbDisponibles;
	public ListBox lbAsociados;
	public String nombreFaseSel = "";

	public void construirTituloNivel1(Element e) {
		int idFase = DOM.getElementPropertyInt(tempWidget.getElement(), "id");
		DrawObject fase = (DrawObject) elementos.get(new Integer(idFase));
		nombreFaseSel = fase.getNombre();
		Element dTitulo = DOM.createDiv();
		DOM.setElementProperty(dTitulo, "id", varDefs.idTituloTramites);
		DOM.setInnerText(dTitulo, nombreFaseSel + mensajes.tituloTramites());
		DOM.appendChild(e, dTitulo);
	}

	/**
	 * Se constuye un titulo de nivel 2  sin color de fondo 
	 * 
	 * @param e Div sobre el que añadiremos el html generado en esta funcion
	 * @param texto Texto que se mostrara en el titulo
	 */
	public void construirTituloNivel2SinBckColor(Element e, String texto) {

		Element dTramitesSel = DOM.createDiv();
		DOM.setElementProperty(dTramitesSel, "id", "tituloNivel2");
		DOM.setInnerText(dTramitesSel, texto);
		DOM.appendChild(e, dTramitesSel);
	}
/**
 * Se construe la ventana modal (div) que contendra el html con las fases disponibles aun no asociadas al procedimiento para
 * que el usuario selecciones una 
 * @param e Div que alojara el html generado en esta función
 * @param ele Listaod de fases disponibles (no asociadas) al procedimiento actual
 */
	public void construirContenedorListadoNuevaFase(Element e, List ele) {

		Element contenedorListFases = DOM.createDiv();
		DOM.appendChild(contenedorListFases, DOM.createElement("BR"));

		// ID | Nombre (Link) | Descripcion|

		Element tr1 = DOM.createTR();
		Element td1 = DOM.createTD();
		Element body1 = DOM.createTBody();
		Element table1 = DOM.createTable();

		DOM.setElementAttribute(table1, "cellpadding", "5");
		DOM.setElementAttribute(body1, "cellpadding", "6");
		DOM.setElementAttribute(table1, "id", "disponibles");
		DOM.setElementAttribute(table1, "id", "bodyDisponibles");

		DOM.setElementAttribute(tr1, "id", "cabeceraTabla");

		td1 = DOM.createTD();
		DOM.setInnerText(td1, "Id");
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, mensajes.nombre());
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, mensajes.descripcion());
		DOM.appendChild(tr1, td1);

		DOM.appendChild(body1, tr1);

		for (int i = 0; i < ele.size(); i++) {
			DrawObject aux = (DrawObject) ele.get(i);

			tr1 = DOM.createTR();
			td1 = DOM.createTD();
			DOM.setInnerText(td1, "" + aux.getId());
			DOM.appendChild(tr1, td1);

			td1 = DOM.createTD();

			Element link = DOM.createElement("A");
			DOM.setStyleAttribute(link, "text-decoration", "none");

			DOM.setElementAttribute(link, "class", "displayLink");
			DOM.setElementAttribute(link, "id", "" + aux.getId());
			DOM.setInnerText(link, aux.getNombre());
			DOM.setElementAttribute(link, "href", "javascript:;");

			DOM.sinkEvents(link, Event.ONCLICK);

			DOM.setEventListener(link, new EventListener() {
				public void onBrowserEvent(Event event) {

					Element e1 = DOM.eventGetCurrentTarget(event);

					// Obtenemos la fase seleccionada
					idSel = DOM.getElementPropertyInt(e1, "id");
					faseSel = DOM.getInnerText(e1);
					// Se lo escribimos a la fase que el usr ha arrastrado
					Element table = DOM.getChild(l.getElement(), 0);
					Element tbody = DOM.getChild(table, 0);
					Element tr = DOM.getChild(tbody, 0);

					Element td = DOM.getChild(tr, 1);
					DOM.setInnerText(td, faseSel);
					td = DOM.getChild(tr, 0);
					DOM.removeChild(tr, td);
					// 3) Creamos un objeto asincrono para manejar el resultado
					AsyncCallback callback = new AsyncCallback() {
						public void onSuccess(Object result) {
							createNuevoElemento(result, varDefs.EFASE);

							l.addStyleName(varDefs.styleToolboxNode);
							l.addStyleName(varDefs.stylePhase);
						}

						public void onFailure(Throwable caught) {
							// En caso de fallo lo q tengamos que hacer...
							l.removeFromParent();
							Window.alert(mensajeError.accionNoRealizada(100,
									"addStage", caught.getLocalizedMessage()));

						}
					};

					servicio.addStage(idProcedimiento, idSel, x, y, callback);
					Element listado = DOM.getElementById(varDefs.divListado);
					Html.removeAllChildren(listado);
					Designer.listadoOculto();

				}

			});

			DOM.setElementAttribute(td1, "id", "link");
			DOM.appendChild(td1, link);
			DOM.appendChild(tr1, td1);

			td1 = DOM.createTD();

			if (aux.getDescripcion() != null)
				DOM.setInnerText(td1, aux.getDescripcion());
			DOM.appendChild(tr1, td1);
			DOM.appendChild(body1, tr1);
		}

		DOM.appendChild(table1, body1);

		DOM.appendChild(contenedorListFases, table1);

		DOM.appendChild(e, contenedorListFases);

	}

	/**
	 * 
	 * Se construye el listado de tramites disponibles ( no asociados) de la ventana modal(div) tramites
	 * 
	 * @param e div que contendran el html que se genere en esta funcion
	 * @param ele Listado de tramites no asociados a la fase seleccionada
	 */
	public void construirContenedorTramitesDisp(Element e, List ele) {

		addBr(e);
		Element contenedorSel = DOM.createDiv();

		Button but = new Button(mensajes.buttonAdd());
		DOM.sinkEvents(but.getElement(), Event.ONCLICK);
		DOM.appendChild(contenedorSel, but.getElement());
		DOM.setEventListener(but.getElement(), new EventListener() {
			public void onBrowserEvent(Event event) {
				seleccionTramites("Add");

			}

		});

		DOM.appendChild(contenedorSel, DOM.createElement("BR"));
		DOM.appendChild(contenedorSel, DOM.createElement("BR"));

		Element tr1 = DOM.createTR();
		Element td1 = DOM.createTD();
		Element body1 = DOM.createTBody();
		Element table1 = DOM.createTable();

		DOM.setElementAttribute(table1, "cellpadding", "5");
		DOM.setElementAttribute(body1, "cellpadding", "6");
		DOM.setElementAttribute(table1, "id", "disponibles");
		DOM.setElementAttribute(table1, "id", "bodyDisponibles");
		DOM.setElementAttribute(tr1, "id", "cabeceraTabla");
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, mensajes.nombre());
		DOM.appendChild(tr1, td1);

		DOM.appendChild(body1, tr1);

		for (int i = 0; i < ele.size(); i++) {
			TramiteFase aux = (TramiteFase) ele.get(i);
			tr1 = DOM.createTR();
			DOM.setElementAttribute(tr1, "id", "" + aux.getCtTaskId());
			td1 = DOM.createTD();

			CheckBox ck = new CheckBox();
			ck.setEnabled(true);
			ck.setName("" + aux.getCtTaskId());
			ck.setVisible(true);
			ck.setChecked(false);

			DOM.appendChild(td1, ck.getElement());
			DOM.appendChild(tr1, td1);

			td1 = DOM.createTD();
			DOM.setElementAttribute(td1, "colspan", "4");
			DOM.setInnerText(td1, aux.getNombre());
			DOM.setElementAttribute(td1, "id", "" + aux.getSubTaskId());
			DOM.appendChild(tr1, td1);
			DOM.appendChild(body1, tr1);

		}

		DOM.appendChild(table1, body1);

		DOM.appendChild(contenedorSel, table1);

		DOM.appendChild(e, contenedorSel);

	}

	/**
	 * Se construye el listado de tramites seleccionados (asociados) de la ventana modal(div) tramites
	 * 
	 * @param e div que contendran el html que se genere en esta funcion
	 * @param ele Listado de tramites asociados a la fase seleccionada
	 */
	public void construirContenedorTramitesSel(Element e, List ele) {
		addBr(e);
		Element contenedorSel = DOM.createDiv();
		Element tr = DOM.createTR();
		Element td = DOM.createTD();
		Element body = DOM.createTBody();
		Element table = DOM.createTable();

		DOM.setElementAttribute(table, "id", "botonera");

		Button butE = new Button(mensajes.buttonDelete());
		DOM.sinkEvents(butE.getElement(), Event.ONCLICK);
		DOM.appendChild(td, butE.getElement());
		DOM.setEventListener(butE.getElement(), new EventListener() {
			public void onBrowserEvent(Event event) {

				seleccionTramites("Eliminar");

			}

		});
		DOM.appendChild(tr, td);
		td = DOM.createTD();
		Button butS = new Button(mensajes.buttonSubir());
		DOM.sinkEvents(butS.getElement(), Event.ONCLICK);
		DOM.appendChild(td, butS.getElement());
		DOM.setEventListener(butS.getElement(), new EventListener() {
			public void onBrowserEvent(Event event) {

				seleccionTramites("Subir");

			}

		});

		DOM.appendChild(tr, td);
		td = DOM.createTD();
		Button butB = new Button(mensajes.buttonBajar());
		DOM.sinkEvents(butB.getElement(), Event.ONCLICK);
		DOM.appendChild(td, butB.getElement());
		DOM.setEventListener(butB.getElement(), new EventListener() {
			public void onBrowserEvent(Event event) {

				seleccionTramites("Bajar");

			}

		});

		DOM.appendChild(tr, td);

		DOM.appendChild(body, tr);
		DOM.appendChild(table, body);

		DOM.appendChild(contenedorSel, table);
		DOM.setElementAttribute(table, "border-spacing", "10");
		DOM.setElementAttribute(table, "cellspacing", "0");
		DOM.appendChild(contenedorSel, DOM.createElement("BR"));

		Element tr1 = DOM.createTR();
		Element td1 = DOM.createTD();
		Element body1 = DOM.createTBody();
		Element table1 = DOM.createTable();

		DOM.setElementAttribute(table1, "cellpadding", "6");
		DOM.setElementAttribute(body1, "cellpadding", "6");

		DOM.setElementAttribute(table1, "id", "seleccionados");

		DOM.setElementAttribute(table1, "id", "bodySeleccionados");

		DOM.setElementAttribute(tr1, "id", "cabeceraTabla");
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, mensajes.nombre());
		DOM.appendChild(tr1, td1);

		DOM.appendChild(body1, tr1);

		DOM.appendChild(body1, tr1);

		// Añadimos los elementos
		for (int i = 0; i < ele.size(); i++) {
			TramiteFase aux = (TramiteFase) ele.get(i);
			tr1 = DOM.createTR();
			td1 = DOM.createTD();
			DOM.setElementAttribute(tr1, "id", "" + aux.getCtTaskId());
			CheckBox ck = new CheckBox();
			ck.setEnabled(true);
			ck.setName("" + aux.getCtTaskId());
			ck.setVisible(true);
			ck.setChecked(false);
			DOM.appendChild(td1, ck.getElement());

			DOM.appendChild(tr1, td1);

			td1 = DOM.createTD();

			DOM.setElementAttribute(td1, "colspan", "4");
			DOM.setElementAttribute(td1, "id", "" + aux.getSubTaskId());

			DOM.setInnerText(td1, "" + aux.getNombre());
			DOM.appendChild(tr1, td1);

			DOM.appendChild(body1, tr1);
		}

		DOM.appendChild(table1, body1);

		DOM.appendChild(contenedorSel, table1);
		DOM.appendChild(e, contenedorSel);

	}

	
	
	public static Image imgTram = null;


	/**
	 * 
	 * Se construye la ventana modal (div) para gestionar los tramites
	 * 
	 * @param tramites Lista de tramites que estan asociados a la fase sobre la que el usuario ha pulsado la opción del menú tramites
	 * 
	 */
	public void construirTramites(List tramites) {

		Element divTram = DOM.getElementById(varDefs.divTramites);

		Element divAzul = DOM.createDiv();
		DOM.setElementAttribute(divAzul, "id", "divAzul");
		DOM.setElementAttribute(divAzul, "align", "right");
		if (imgTram == null) {
			imgTram = new Image();

			imgTram.setUrl(".//img//close1.png");

			DOM.sinkEvents(imgTram.getElement(), Event.ONCLICK);

			DOM.setEventListener(imgTram.getElement(), new EventListener() {
				public void onBrowserEvent(Event event) {
				
					salirTramites();

				}
			});
		}
		imgTram.setVisible(true);
		DOM.appendChild(divAzul, imgTram.getElement());
		DOM.appendChild(divTram, divAzul);

		Element e = DOM.createDiv();
		DOM.setElementAttribute(e, "id", "divModal");
		DOM.appendChild(divTram, e);

		addBr(e);

		// Recorremos la lista y en funcion de si esta disponible o no lo
		// añadimos a una tabla u otra

		tramitesTratados = new HashMap();
		List disponibles = new ArrayList();
		List sele = new ArrayList();
		for (int i = 0; i < tramites.size(); i++) {
			TramiteFase aux = (TramiteFase) tramites.get(i);
			tramitesTratados.put(new Integer(aux.getCtTaskId()), aux);
			if (aux.isAsociado())
				sele.add(aux);

			else
				disponibles.add(aux);

		}

		construirTituloNivel1(e);

		addBr(e);
		addBr(e);

		Element dVolver = DOM.createDiv();
		DOM.setElementProperty(dVolver, "id", "dvolver");
		DOM.setInnerText(dVolver, mensajes.selectTramites());
		addBr(dVolver);
		addBr(dVolver);

		Button but = new Button(mensajes.buttonAccept());
		DOM.sinkEvents(but.getElement(), Event.ONCLICK);
		DOM.appendChild(dVolver, but.getElement());

		DOM.setEventListener(but.getElement(), new EventListener() {
			public void onBrowserEvent(Event event) {

				// Hay que informar de los tramites Tratados
			
				int idFase = DOM.getElementPropertyInt(tempWidget.getElement(),
						"id");
				// Añado los tramites pero en el orden seleccionado

				Element origen = DOM.getElementById("bodySeleccionados");
				Element body = DOM.getChild(origen, 0);
				int numChild = DOM.getChildCount(body);
				ids = new int[numChild - 1];
				for (int i = 1; i < numChild; i++) {

					Element tr = DOM.getChild(body, i);
					int ide = DOM.getElementPropertyInt(tr, "id");
					ids[i - 1] = ide;
				}
				ordenado = false;
				modificarTramitesAsociados(idFase);

				if (contDelete == 0 && indice.size() == 0 && !ordenado)
					tratarOrden(idFaseP, tramitesAsociados);

				salirTramites();

			}

		});

		but = new Button(mensajes.buttonCancel());
		DOM.sinkEvents(but.getElement(), Event.ONCLICK);
		DOM.appendChild(dVolver, but.getElement());

		DOM.setEventListener(but.getElement(), new EventListener() {
			public void onBrowserEvent(Event event) {
				
				tramitesTratados.clear();
				salirTramites();

			}

		});

		DOM.appendChild(e, dVolver);
		addBr(e);

		construirTituloNivel2(e, mensajes.associatedTramites());

		construirContenedorTramitesSel(e, sele);

		addBr(e);

		construirTituloNivel2(e, mensajes.disponiblesTramites());

		construirContenedorTramitesDisp(e, disponibles);

	}

	public boolean ordenado = false;
/**
 * 
 * @param idFase Identificador de la fase de la que se estan modificando sus tramites tanto asociados como el orden
 * @param tramites Tramites asociados a la fase
 * 
 * Se compara construye el objeto fase que el usuario visualizará junto con sus tramites , pero estos es han de mostrar en el mismo
 * orden que el usuario ha dejado indicado en la ventana de tramites, para ello en el array de ids se han guardado los identificadores
 * de los tramites asociados en el mismo orden que ha indicado el usuario y se añaden al objeto fase atendiendo a ese orden, para ello 
 * hay que buscar el resto de información del tramite en la lista tramites que recibimos como parametro
 */
	public void tratarOrden(int idFase, List tramites) {

		ordenado = true;
		DrawObject fase = (DrawObject) elementos.get(new Integer(idFaseP));

		// Ahora trato el orden,

		// Obtengo el orden que el usuario ha seleccionado y la fase con los
		// tramites hay que reordenarlos

		int[] orden = new int[ids.length];

		for (int i = 0; i < ids.length; i++) {
			boolean encontrado = false;
			for (int j = 0; j < tramites.size() && !encontrado; j++) {
				if (((TramiteFase) tramites.get(j)).getCtTaskId() == ids[i]) {
					encontrado = true;
					TramiteFase auxiliar = (TramiteFase) tramites.get(j);
					addTramite(idFaseP, auxiliar.getNombre(), auxiliar
							.getEstado(), fase.isDesplegado(), auxiliar
							.getSubTaskId(), auxiliar.getPTaskId(), tempWidget
							.getElement());
					orden[i] = ((TramiteFase) tramites.get(j)).getPTaskId();
				}

			}

		}

		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {
				// Mostramos los tramites actuales
				tramitesAsociados = new ArrayList();

				dragController.restoreDraggableStyle(tempWidget);
			}

			public void onFailure(Throwable caught) {

				Window.alert(mensajeError.accionNoRealizada(100,
						"reorderTasks", caught.getLocalizedMessage()));

			}
		};

		// Hago la llamada al servlet
		servicio.reorderTasks(orden, callback);

	}

	
	
	// Atributos globales para poder acceder dentro de las funciones que estan implementadas dentro de los enventos
	public List indice = new ArrayList();
	public int idFaseP = -1;
	public int contDelete = 0;
	public static int[] ids;
	public List tramitesAsociados = new ArrayList();

	
	/**
	 * Una vez que el usuario asocie o elimine tramites a la fase y los reordene si lo estima
	 * oportuno se recorreran los tramites identificando lo que se han añadido o eliminado y actualizando
	 * contra la base de datos si tuvieramos algun cambio, sin embargo el orden siempre se actualiza
	 * @param idFase Identificador de la fase de la que vamos a modificar sus tramites
	 */
	public void modificarTramitesAsociados(int idFase) {
		contDelete = 0;
		idFaseP = idFase;
		Iterator iterator = tramitesTratados.values().iterator();

		List tramites = new ArrayList();
		final TramiteFase aux = new TramiteFase();
		DrawObject fase = (DrawObject) elementos.get(new Integer(idFaseP));

		// Realizar los cambios sobre la bbdd
		// 3) Creamos un objeto asincrono para manejar el resultado
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {
				// Mostramos los tramites actuales
				if (result != null) {

					((TramiteFase) tramitesAsociados.get(Integer
							.parseInt(indice.get(0).toString())))
							.setPTaskId(Integer.parseInt(result.toString()));

					indice.remove(0);

				} else
					contDelete -= 1;

				if (indice.size() == 0 && contDelete == 0) {

					tratarOrden(idFaseP, tramitesAsociados);
				}
				dragController.restoreDraggableStyle(tempWidget);
			}

			public void onFailure(Throwable caught) {

				Window.alert(mensajeError.accionNoRealizada(100,
						"actualizarTramites", caught.getLocalizedMessage()));

			}
		};

		// 4)Realizar la llamada
		int id = DOM.getElementPropertyInt(tempWidget.getElement(), "id");
		Element table = DOM.getChild(tempWidget.getElement(), 0);
		Element tbody = DOM.getChild(table, 0);
		int numChilds = DOM.getChildCount(tbody);
		boolean tieneMas = false;
		for (int j = 1; j < numChilds; j++) {
			tieneMas = true;
			Element hijo = DOM.getChild(tbody, j);
			DOM.removeChild(tbody, hijo);
			j = j - 1;
			numChilds = numChilds - 1;
		}
		// Si no tenia la imagen de mas por no tener previamente tramites lo
		// tenemos que poner
		if (!tieneMas)
			addPlus(id, tempWidget.getElement(),false);

		// Tiene mas y si ya no tiene tramites hay que borrar
		boolean tieneTramites = false;

		List tramitesNuevosFase = new ArrayList();

		while (iterator.hasNext()) {

			TramiteFase auxiliar = (TramiteFase) iterator.next();

			tramites.add(auxiliar);
			if (auxiliar.isChange()) {
				if (auxiliar.isAsociado()) {
					contDelete += 1;
					servicio.removeTask(auxiliar.getPTaskId(), callback);
				} else {
					tieneTramites = true;
					tramitesNuevosFase.add(auxiliar);
					indice.add(new Integer(tramitesAsociados.size()));

					tramitesAsociados.add(auxiliar);
					servicio.addTask(idFase, auxiliar.getCtTaskId(), callback);

				}

			} else if (auxiliar.isAsociado()) {

				tieneTramites = true;
				tramitesNuevosFase.add(auxiliar);
				tramitesAsociados.add(auxiliar);
			}

		}

		tramitesFases.put(new Integer(idFase), tramitesNuevosFase);
		if (!tieneTramites && tieneMas) {

			fase.setDesplegado(false);
			elementos.put(new Integer(id), fase);
			Html.deletePlus(id);
		}

	}

	

	/**
	 * Acciones a ejecutar cuando el usuario cierre la "ventana modal" que te permite
	 * gestionar los tramites 
	 */
	public void salirTramites() {
		DOM.setStyleAttribute(DOM.getElementById("body"), "overflow", "hidden");
		DOM.setStyleAttribute(getArea().getElement(), "overflow", "auto");
		Element tramites = DOM.getElementById(varDefs.divTramites);
		closeTram();
		Html.removeAllChildren(tramites);
		Designer.tramitesOculto();
	}

	/**
	 * 
	 * @param w : Widget del que tenemos que obtener todas sus conexiones y eliminarlas
	 */
	private void eliminarConexion(Widget w) {

		UIObjectConnector conector = UIObjectConnector.getWrapper(seleccionado);
		if (conector == null) {
			seleccionado.removeFromParent();
			return;
		}

		conector.update();

		Collection cons = UIObjectConnector.getWrapper(seleccionado)
				.getConnections();

		Iterator it = cons.iterator();
		List conexiones = new ArrayList();
		boolean encontrado = false;
		while (it.hasNext()) {
			Connection c = (Connection) it.next();

			Element e = DOM.createDiv();
			DOM.setInnerHTML(e, c.toString());
			String id = DOM.getElementAttribute(DOM.getChild(e, 0), "id");
			String idC = "c" + id.substring(1);
			Element cond = DOM.getElementById(idC);
			Element padreC=null;
			if(cond!=null){
			 padreC = DOM.getParent(cond);
			DOM.removeChild(padreC, cond);
			}

			String idP = "p" + id.substring(1);
			Element divp = DOM.getElementById(idP);
			if(divp!=null){
			Element padreP = DOM.getParent(divp);
			DOM.setStyleAttribute(divp, "display", "none");
			//DOM.removeChild(padreP, divp);
			}

			encontrado = false;
			if (conexionesBorradas.size() > 0) {
				if(padreC!=null && cond!=null)
				DOM.removeChild(padreC, cond);
				//conector.disconnect(c);
				for (int i = 0; i < conexionesBorradas.size() && !encontrado; i++) {
					if (conexionesBorradas.get(i).toString().compareTo(id) == 0) {
						encontrado = true;

						conexionesBorradas.remove(i);

					}
				}
			}

			if (!encontrado)
				conexiones.add(c);
		}
		int i;
		for (i = 0; i < conexiones.size(); i++) {
			((Connection) conexiones.get(i)).remove();

		}
		conector.update();
		cons = UIObjectConnector.getWrapper(seleccionado).getConnections();
		seleccionado.removeFromParent();
	}
/**
 * 
 *Clase que nos permite tratar los objetos cuando se estan moviendo
 *
 */
	private class ToolboxDragController extends PickupDragController {

		public Map nodes = new HashMap();

		public ToolboxDragController(final DropController dropController,
				final DragController nodesDragController) {
			super(getArea(), false);
			setDragProxyEnabled(true);
			registerDropController(dropController);

			addDragHandler(new DragHandlerAdapter() {

				public void onPreviewDragEnd(DragEndEvent event)
						throws VetoDragException {
					Widget node = (Widget) event.getSource();
					Widget proxy = (Widget) nodes.get(node);

					AbsolutePanel panel = (AbsolutePanel) dropController
							.getDropTarget();
					accionEjecutar((Label) proxy, panel);

					throw new VetoDragException();
				}

			});

		}

		/**
		 * Cuando se crea un elementr dragable  necesitamos tener almacenado el elemento que queremos duplicar cuando 
		 * el usuario suelte el objeto para ello se guardara en la estructura nodes
		 */
		protected Widget maybeNewDraggableProxy(Widget draggable) {

			int numChild = DOM.getChildCount(draggable.getElement());
			Widget proxy = new Label();
			Label node = (Label) draggable;

			if (numChild == 0) {

				proxy = new Label(node.getText());
				proxy.addStyleName(varDefs.toolboxnodeProxy);
				proxy.addStyleName(CSS_PROXY);

			} else // Es un nodo
			{

				DOM.setInnerHTML(proxy.getElement(), DOM.getInnerHTML(draggable
						.getElement()));
				DOM.setElementAttribute(proxy.getElement(), "id", DOM
						.getElementAttribute(node.getElement(), "id"));

			}

			nodes.put(node, proxy);

			return proxy;
		}

	}
	public  void guardarPosicion(Widget ele) {
	
		if (!elementos.isEmpty()) {
			// Creamos un objeto asincrono para manejar el resultado
			AsyncCallback callback = new AsyncCallback() {
				public void onSuccess(Object result) {


				}

				public void onFailure(Throwable caught) {

					Window.alert(mensajeError.accionNoRealizada(100,
							"updatePosicion", caught.getLocalizedMessage()));
				}
			};
			DrawObject aux = (DrawObject) elementos.get(new Integer(DOM.getElementPropertyInt(ele.getElement(), "id")));

			if (aux != null) {
				int left = DOM.getAbsoluteLeft(ele.getElement());
				int top = DOM.getAbsoluteTop(ele.getElement());
				if(left!=aux.getLeft())
					aux.setLeft(left - varDefs.difTamanioLeft);
				if(top!=aux.getTop())
					aux.setTop(top-varDefs.difTamanioTop);

			}

			if(!readOnly)
				elementos.put(new Integer(aux.getId()), aux);
			servicio.updatePosition(aux, callback);
		}
	

}

}
