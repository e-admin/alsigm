package ieci.tdw.ispac.designer.client.diagrama;

import ieci.tdw.ispac.designer.client.Designer;
import ieci.tdw.ispac.designer.client.connection.RectilinearTwoEndedConnectionCustom;
import ieci.tdw.ispac.designer.client.exceptions.DesignerException;
import ieci.tdw.ispac.designer.client.helper.Html;
import ieci.tdw.ispac.designer.client.helper.varDefs;
import ieci.tdw.ispac.designer.client.messages.ErrorMensajes;
import ieci.tdw.ispac.designer.client.messages.Mensajes;
import ieci.tdw.ispac.designer.client.objetos.DrawFlow;
import ieci.tdw.ispac.designer.client.objetos.DrawObject;
import ieci.tdw.ispac.designer.client.objetos.Instancia;
import ieci.tdw.ispac.designer.client.objetos.Procedimiento;
import ieci.tdw.ispac.designer.client.objetos.TramiteFase;
import ieci.tdw.ispac.designer.client.service.GwtIService;
import ieci.tdw.ispac.designer.client.service.GwtIServiceAsync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pl.balon.gwt.diagrams.client.connection.AbstractConnection;
import pl.balon.gwt.diagrams.client.connection.Connection;
import pl.balon.gwt.diagrams.client.connector.Connector;
import pl.balon.gwt.diagrams.client.connector.Direction;
import pl.balon.gwt.diagrams.client.connector.UIObjectConnector;

import com.allen_sauer.gwt.dragdrop.client.DragController;
import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.drop.BoundaryDropController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractConnectionsExample extends AbstractExample {


	public AbsolutePanel panel;
	public static DragController toolboxDragController;
	public CellPanel infoPanel;
	
	public static boolean readOnly=false;
	public static String localePorDefecto="es";
	public static String locale="es";


	public String style;
	public Label FaseActividad;

	public static int stado;
	public static VerticalPanel vp;

	public static Image iTramite;
	protected DragController dragController;

	protected static int idProcPadre = -1;
	public static Map tramitesFases = new HashMap();
	public static Map elementos = new HashMap();
	public static Map flujos = new HashMap();
	public static Map objetos = new HashMap();

	public static Map elementosPadre = new HashMap();
	public static Map flujosPadre = new HashMap();
	public static Map tramitesFasesPadre = new HashMap();

	private static List connections = new ArrayList();
	public static int idProcedimiento = -1;
	public static GwtIServiceAsync servicio;
	public static Mensajes mensajes = (Mensajes) GWT.create(Mensajes.class);
	public static ErrorMensajes mensajeError = (ErrorMensajes) GWT
			.create(ErrorMensajes.class);
	protected List selected = new ArrayList();
	protected static RectilinearTwoEndedConnectionCustom transicionCreada = null;

	public static Procedimiento procedimiento = new Procedimiento();
	public static Procedimiento procedimientoPadre = new Procedimiento();
	public Element auxE;
	public static Widget seleccionado = null;
	public int stageId=-1;
	public int nIdProc=-1;
	public static int EXP_ABIERTO=0;
	public static int EXP_CERRADO=1;
	
	public static boolean returnFromSubProc=false;

	
	
	
	/**
	 * Inicializa el servlet el DragController el procedimiento que vamos a tratar y crea el contenido que va a tener
	 * el diseñador, el metodo createContents se define en el hijo , pues es el hijo el que sabe que va a tener 
	 * @param idProc idProcedimiento que tenemos que tratar en el diseñador
	 */
	public AbstractConnectionsExample(int param, String locale, int tipo) {

		idProcedimiento = param;
		this.locale=locale;
		if(EXP_ABIERTO==tipo)
		stageId=param;
		else
			nIdProc=param;
		inicializar();
		
	
	}
	
	/**
	 * Inicializa el servlet el DragController el procedimiento que vamos a tratar y crea el contenido que va a tener
	 * el diseñador, el metodo createContents se define en el hijo , pues es el hijo el que sabe que va a tener 
	 * @param idProc idProcedimiento que tenemos que tratar en el diseñador
	 */
	public AbstractConnectionsExample(int param, String locale) {

		idProcedimiento = param;
		this.locale=locale;
		
		inicializar();
		
	
	}

	
	public void inicializar(){


		DOM.setElementAttribute(Designer.absPanel.getElement(), "id", "tab");
		DOM.setStyleAttribute(getArea().getElement(), "overflow", "auto");
		this.dragController = createDragController();
		DOM.setElementAttribute(getArea().getElement(),"id", "area");
		DOM.setStyleAttribute(getArea().getElement(),"height", Window.getClientHeight()-5+"px");
		DOM.setStyleAttribute(getArea().getElement(), "width" , Window.getClientWidth()-30+"px");


		// 1)Creo el proxy del cliente
		servicio = (GwtIServiceAsync) GWT.create(GwtIService.class);

		// 2)Especificar la url que en la que nuestra implementación de servicio
		// esta corriendo
		ServiceDefTarget endpoint = (ServiceDefTarget) servicio;
		String URLmodulo = GWT.getModuleBaseURL() + "GwtIService.gwt";
		endpoint.setServiceEntryPoint(URLmodulo);

		createContents();
	}
	
	public void addIconPrint()
	{
		//Image print=new Image("./img/printer.png");
		
		
		HTML print=new HTML();
		print.addStyleName("nodo-sinc");
		print.setHTML("<img class=\"seleccionable\" src=\"./img/printer.png\" onclick=\"javascript:window.print();\" style=\"align:right;\">"
				+ mensajes.tooltipPrint() + "</img>");
		print.setTitle(mensajes.tooltipPrint());
		DOM.setStyleAttribute(print.getElement(), "ALIGN", "RIGHT");

		infoPanel.add(print);
		infoPanel.setWidth("100%");
		infoPanel.setSpacing(0);
		infoPanel.setCellWidth(print, "6%");
		infoPanel.setCellHorizontalAlignment(print, HorizontalPanel.ALIGN_RIGHT);
	
		
	}
	
	
	/** tested.
	 * @return true if dialog was displayed, false if browser not capable.
	 */
	public native boolean showPrintDialog () /*-{
	  if (window.print) {
	 
	
	    window.print();
	    return true;
	  }
	  else {
	    return false;
	  }

	}-*/; 
	public  void addIconHelp()
	 {
	
		Label lhelp=new Label();
		lhelp.addStyleName("nodo-sinc");
		
		HTML help=new HTML();
		help.addStyleName("nodo-sinc");
		help.setHTML("<img src=\"./img/help.gif\" class=\"seleccionable\" onclick=\"window.parent.showHelp();\"  style=\"align:right;\">" 
				+ mensajes.help() + "</img>");
		help.setTitle(mensajes.help());
		DOM.setStyleAttribute(lhelp.getElement(), "ALIGN", "RIGHT");
		DOM.appendChild(lhelp.getElement(), help.getElement());
		infoPanel.add(lhelp);
		infoPanel.setWidth("100%");
		infoPanel.setCellWidth(help, "7%");
		infoPanel.setCellHorizontalAlignment(help, HorizontalPanel.ALIGN_RIGHT);
	
		
		 
	 }
	/**
	 * 
	 * @param obj de tipo DrawObject que representa contine los datos de un nodo o de una fase
	 * @param style Estilo que tendra el objeto 
	 * @param tipo Tipo de Objeto que vamos a crear, afecta solo a los nodos , tipo=1 nodo And, tipo=2 nodo Or
	 * @param desplegado Indica si el objeto esta desplegado o no 
	 * @return Objeto de tipo Label creado
	 */
	protected abstract Widget createElemento(DrawObject obj, String style,
			int tipo, boolean desplegado);

	/**
	 * 
	 * @param eleCreado Nodo de sincronizacion a crear
	 * @param tipo tipo del nodo de sincronizacion 1 si es And 2 si es Or
	 */
	protected void createNodo(Widget eleCreado, int tipo) {

		eleCreado.setStyleName(varDefs.styleNode);

		String img = "";
		if (tipo == 1)
			img = "<img src=./img/And.JPG />";
		else
			img = "<img src=./img/Or.JPG />";

		HTML html = new HTML(img);
		DOM.appendChild(eleCreado.getElement(), html.getElement());
	}

	/**
	 * Metodo abstracto: Definido en cada hijo, pues cada hijo borra el contenido de manera distinta
	 * @param update indica si hay que actualizar el diagrama o no
	 */
	protected abstract void deleteContents(boolean update);

	/**
	 * Método que se redefinira en el catalogo pues en ese entorno si importan los permisos
	 */
	protected void gestionarPermisos() {
	}

	/**
	 * Actualiza el diagrama 
	 * @throws DesignerException 
	 */
	public void updateDiagrama() throws DesignerException
	{
		//guardarPosiciones();
		returnFromSubProc=true;
		deleteContents(true);
		Designer.removeAllConnections();
		Iterator itr = elementos.values().iterator();
		//Precargamos las fases
		while(itr.hasNext())
		{
			Widget p=null;
			DrawObject aux = (DrawObject) itr.next();
			Integer id=  new Integer(aux.getId());
			String texto="";
			if(aux.getNombre().compareTo("Nodo AND")!=0 && aux.getNombre().compareTo("Nodo OR")!=0)
			{
			 p= createElemento(aux,varDefs.stylePhase , varDefs.EFASE, aux.isDesplegado());
			 
			 boolean noHayTramites=true;
				
				if(tramitesFases.containsKey(id))
				{
				
					texto= aux.getNombre();
					List tramites= new ArrayList();
					tramites=(List) tramitesFases.get(id);
					
					for(int j=0; j<tramites.size(); j++){
				
						TramiteFase auxTram = (TramiteFase) tramites.get(j);
						if((stageId==-1 && nIdProc==-1) || ( auxTram.getEstado()!=varDefs.NO_INICIADO)){
							noHayTramites=false;
						addTramite(aux.getId(),auxTram.getNombre() ,auxTram.getEstado(), aux.isDesplegado(),auxTram.getSubTaskId(), auxTram.getPTaskId(), p.getElement());
						
						}
					}
				
				}
			
				if(!noHayTramites){
					addPlus(aux.getId(), p.getElement(), aux.isDesplegado());
					
				}

			}
			else //Nodo
			{
				 p = createElemento(aux, varDefs.styleNode ,varDefs.ENODO, false);
				 p.addStyleName(varDefs.styleToolboxNodoSinc);
				
			}
			objetos.put( id, p );
		
		}

		//Construimos los flujos
		 itr = flujos.values().iterator();
		//Precargamos las fases
		while(itr.hasNext()){
			DrawFlow aux = (DrawFlow)itr.next();
			//Obtengo los objetos a conectar
			Label origen = (Label) objetos.get(new Integer(aux.getIdOrigen()));
			Label destino = (Label) objetos.get(new Integer( aux.getIdDestino()));
			connectManual(UIObjectConnector.wrap(origen), UIObjectConnector.wrap(destino));
			DOM.setElementAttribute(transicionCreada.getElement(), "id", ""+aux.getIdS());
			if(aux.getEvents()!=null && aux.getEvents().size()>0)
			addCondicion(aux);
			addArrow(aux);
						
			
		}
		
		returnFromSubProc=false;
		Designer.update();
		
	}


	/**
	 * 
	 * @param idSubProceso : Id del subproceso que se va a visualizar en el diseñador
	 * @param ver : Indica si podemos ver el subproceso , true, o false en caso contrario
	 * @throws DesignerException 
	 */
	public void verSubProceso(int idSubProceso, boolean ver, int idInstanceSubProc) throws DesignerException {

		//guardarPosiciones();
		Designer.removeAllConnections();
		//Volvemos al padre
		if (!ver) {
			idProcedimiento = idProcPadre;
			procedimiento.setEstado(procedimientoPadre.getEstado());
			procedimiento.setNombreProc(procedimientoPadre.getNombreProc());
			procedimiento.setTipoProcedimiento(procedimientoPadre
					.isTipoProcedimiento());
			procedimiento.setTramitesFases(procedimientoPadre.getTramitesFases());

			procedimiento.setIdProc(procedimientoPadre.getIdProc());
			procedimiento.setProcedimientoTerminado(procedimientoPadre.isProcedimientoTerminado());
			idProcedimiento=procedimiento.getIdProc();
			idProcPadre = -1;
              
			elementos = new HashMap();
			flujos = new HashMap();
			tramitesFases = new HashMap();
			elementos.putAll(elementosPadre);
			flujos.putAll(flujosPadre);
			tramitesFases.putAll(tramitesFasesPadre);
			gestionarPermisos();
			updateDiagrama();

		} else {

			procedimientoPadre.setEstado(procedimiento.getEstado());
			procedimientoPadre.setNombreProc(procedimiento.getNombreProc());
			procedimientoPadre.setTipoProcedimiento(procedimiento
					.isTipoProcedimiento());
			procedimientoPadre.setTramitesFases(procedimiento.getTramitesFases());
			idProcPadre = idProcedimiento;
			idProcedimiento = idSubProceso;
			elementosPadre.clear();
			flujosPadre.clear();
			tramitesFasesPadre.clear();
			elementosPadre.putAll(elementos);
			flujosPadre.putAll(flujos);
			tramitesFasesPadre.putAll(tramitesFases);

			deleteContents(false);
			createReturn();
			// 3) Creamos un objeto asincrono para manejar el resultado
			AsyncCallback callback = new AsyncCallback() {
				public void onSuccess(Object result) {

					precargarDatos((Procedimiento) result);
					//Designer.tabs.selectTab(0);
					
					Designer.update();
				}

				public void onFailure(Throwable caught) {
					Window.alert(mensajeError.accionNoRealizada(100,
							"getProcedure", caught.getLocalizedMessage()));
				}
			};

			if(!readOnly)
			servicio.getProcedure(idProcedimiento, callback);
			else
				servicio.getSubProcByPcdId( idSubProceso, idInstanceSubProc,   callback);

		}

	}

	/**
	 * 
	 * @param aux Objeto que identifica el flujo al que esta asociado la condición
	 */
	public static Element condSel=null;
	public static int vez=0;
	public static RectilinearTwoEndedConnectionCustom rectilinear=null;
	public static HTML addImgCondicion(DrawFlow aux)
	{
		Element e = DOM.getElementById("c"+ aux.getIdC()); 

		if(e==null || e.toString().compareTo("")==0){
			HTML imageC= new HTML();
			Element img =DOM.createImg();
			DOM.setElementAttribute(img, "id", aux.getIdC());
			DOM.setElementAttribute(img, "src", "./img/query.gif");
			imageC.addStyleName("seleccionable");
			DOM.setStyleAttribute(img, "position", "absolute");
			DOM.setStyleAttribute(img, "top", "0px");
			DOM.setStyleAttribute(img, "left", "0px");
			DOM.setStyleAttribute(img, "z-index", "10000");
			DOM.appendChild(imageC.getElement(), img);


			DOM.sinkEvents(img, Event.ONDBLCLICK);

			DOM.setEventListener(img, new EventListener() {
				public void onBrowserEvent(Event event) {

					HTML imageC= new HTML();
					vez=0;
					Element e= DOM.eventGetCurrentTarget(event);
					imageC.setHTML(e.toString());
					if(DOM.eventGetType(event)==Event.ONDBLCLICK)
					{  
						RectilinearTwoEndedConnectionCustom.sinCondicion=false;
						if(vez==0 )
						{  
							RectilinearTwoEndedConnectionCustom.hayCondicion=true;
							DrawFlow flujo= RectilinearTwoEndedConnectionCustom.getArrowSel(DOM.eventGetCurrentTarget(event));
							RectilinearTwoEndedConnectionCustom.idFlujo=flujo.getId();
							RectilinearTwoEndedConnectionCustom.flujoSel=flujo;
							RectilinearTwoEndedConnectionCustom.getCTRules();

						}
					}
				}
			});

			return imageC;
		}
		else
			return null;
	}
	public void addCondicion(DrawFlow aux)
	{
		//if(aux.getCondition()!=null){
			HTML imageC=addImgCondicion(aux);
			if(imageC!=null){
		DOM.setElementAttribute(transicionCreada.getElement(), "id", aux.getIdS());
			getArea().add(imageC);}
		//}
		
	}
	
	public static boolean deseleccionarCondicion(String id){
		String idSel=DOM.getElementAttribute(condSel, "id");
		boolean desSel=false;
		if(idSel.compareTo(id)==0)
			desSel=true;
		if(idSel.substring(0, 1).compareTo("c")==0)
		{
			
			DOM.setStyleAttribute(condSel, "border", "none");
		}
		else{
			seleccionado=null;
			condSel=null;
		}
		
		condSel=null;
		return desSel;
	}
	public static Element createArrow(String id){
		Element divp=DOM.createDiv();

		DOM.setElementAttribute(divp, "id", id);
		DOM.setStyleAttribute(divp, "position", "absolute");
		DOM.setStyleAttribute(divp, "height", "auto");
		DOM.setStyleAttribute(divp, "widht", "auto");
		
		Element imgUp= DOM.createImg();
		DOM.setStyleAttribute(imgUp, "position", "absolute");
		DOM.setElementAttribute(imgUp, "class", "imgFlecha");
		DOM.setElementAttribute(imgUp, "id",""+varDefs.UP);
		DOM.setStyleAttribute(imgUp, "display", "none");
		DOM.setElementProperty(imgUp, "src", varDefs.fUp);
		DOM.appendChild(divp, imgUp);
		
		Element imgDown= DOM.createImg();
		DOM.setStyleAttribute(imgDown, "position", "absolute");
		DOM.setElementAttribute(imgDown, "class", "imgFlecha");
		DOM.setElementAttribute(imgDown, "id",""+varDefs.DOWN);
		DOM.setStyleAttribute(imgDown, "display", "none");
		DOM.setElementProperty(imgDown, "src", varDefs.fDown);
		DOM.appendChild(divp, imgDown);
		
	
		Element imgLeft= DOM.createImg();
		DOM.setStyleAttribute(imgLeft, "position", "absolute");
		DOM.setElementAttribute(imgLeft, "class", "imgFlecha");
		DOM.setElementAttribute(imgLeft, "id",""+varDefs.LEFT);
		DOM.setStyleAttribute(imgLeft, "display", "none");
		DOM.setElementProperty(imgLeft, "src", varDefs.fLeft);
		DOM.appendChild(divp, imgLeft);
		
		
		Element imgRight= DOM.createImg();
		DOM.setStyleAttribute(imgRight, "position", "absolute");
		DOM.setElementAttribute(imgRight, "class", "imgFlecha");
		DOM.setElementAttribute(imgRight, "id",""+varDefs.RIGHT);
		DOM.setStyleAttribute(imgRight, "display", "none");
		DOM.setElementProperty(imgRight, "src", varDefs.fRight);
		DOM.appendChild(divp, imgRight);
	
		DOM.setElementAttribute(divp, "style","");
		Element area=DOM.getElementById("area");
		DOM.appendChild(area, divp);
		
		
	  gestionEventosFlechas(divp);
		return divp;
		
	}
	
	public static void gestionEventosFlechas(Element div)
	{
		
		DOM.sinkEvents(div, Event.BUTTON_RIGHT);
		DOM.sinkEvents(div, Event.ONCLICK);
		DOM.sinkEvents(div, KeyboardListener.KEY_DELETE );
	
		DOM.setEventListener(div, new EventListener() {
	         public void onBrowserEvent(Event event) {
	        	 
	        	 
	        	 Element e= DOM.eventGetCurrentTarget(event);
	        	 String id= DOM.getElementAttribute(e, "id");
	        	 String fid="f"+id.substring(1);
	        		Element flujo=DOM.getElementById(fid);
	        		if(flujo!=null && DOM.getChildCount(flujo)>0){
	        			//RectilinearTwoEndedConnectionCustom.imagenSel=DOM.getChild(flujo, 0);
	        			RectilinearTwoEndedConnectionCustom.accionesEventosFlechas(event , DOM.getChild(flujo, 0));
	        		}
	        			
	         }
	         });
		
	}
	public static void addArrow(DrawFlow aux)
	{
		
		DOM.setElementAttribute(transicionCreada.getElement(), "id", aux.getIdS());
	    Element divp= DOM.getElementById(varDefs.pIdFlowTemp);
	    if(divp!=null)
	    	DOM.setElementAttribute(divp, "id", aux.getIdP());
	    else{
	    	createArrow(aux.getIdP());
	    }
	}
	
	/**
	 * 
	 * @param proc: Procedimiento que vamos a cargar en el diseñador
	 */
	public abstract void precargarDatos(Procedimiento proc);

	/**
	 * Método abstracto, cada hijo creara el boton de volver cuando estemos en el subproceso , y las acciones
	 * que se van a realizar cuando el usuario pulse el botón, pues tiene que volver al padre y precargar el aspecto 
	 * del diseñador y solo los hijos saben que aspecto tiene el diseñador en función del entorno en el que estemos
	 
	protected abstract void createReturn();*/

	/**
	 * 
	 * @param idFase : Idteficador de la fase a la que le vamos a añadir el trámite
	 * @param texto: Texto del trámite a añadir
	 * @param visible: Indica si los tramites de esa fase estan visibles o no
	 * @param idSubProc: Hay tramites complejos , estos llevan otra imagen asociada , idSubProc=-1 no es tramite complejo
	 * en caso contrario nos informara del id del subProceso, necesario para poder visualizarlo cuando el usuario pulse sobre 
	 * la imagen
	 */
	
	public void addTramite(int idFase, String texto,int estado, boolean visible,
			int idSubProc, int idTramite, Element div) {
		//Obtengo el nodo con ese id
		
		Element table = DOM.getChild(div, 0);
		
		Element tbody = DOM.getChild(table, 0);
    if(readOnly && idSubProc!=-1 && tbody==null )
	{
		tbody=DOM.getParent(DOM.getParent(div));
		table=DOM.getParent(tbody);
		
	}
		DOM.setStyleAttribute(tbody, "display", "block");
		Element tr = DOM.createTR();
		DOM.setElementAttribute(tr, "id", "tramitesNodo");
		if (!visible)
			DOM.setStyleAttribute(tr, "display", "none");
		else{
			DOM.setStyleAttribute(tr, "display", "block");
		}
		Element tdImage = DOM.createTD();
	

		if (idSubProc == -1){
			Element imagenCerrado= DOM.createImg();
			Element imagenAbierto= DOM.createImg();
		
		    if(estado!=varDefs.CERRADO){
		    	DOM.setElementAttribute(imagenAbierto, "src", "./img/tramite.gif");
		    	DOM.appendChild(tdImage,imagenAbierto);
		    }
		    else{
		    	DOM.setElementAttribute(imagenCerrado, "src", "./img/tramiteCerrado.gif");
		    	DOM.appendChild(tdImage, imagenCerrado);
		    
		    }
		    
		    if(readOnly && stageId!=-1)
		    {
		     
		    	DOM.setElementAttribute(tdImage, "id", ""+idTramite);
		        DOM.setElementAttribute(imagenCerrado, "id", "seleccionable");
		        DOM.setElementAttribute(imagenAbierto, "id", "seleccionable");
		    	DOM.sinkEvents(tdImage, Event.ONCLICK);
				DOM.setEventListener(tdImage, new EventListener() {
					public void onBrowserEvent(Event event) {

						Element e = DOM.eventGetCurrentTarget(event);
						
						Element tr = DOM.getParent(e);
						
					
							Element tbody= DOM.getParent(tr);
						
							Element table=DOM.getParent(tbody);
							Element trCabecera= DOM.getChild(tbody, 0);
							Element td=DOM.getChild(trCabecera, 1);
							
							Element fasediv=DOM.getParent(table);
							consultaTramite(DOM.getElementPropertyInt(fasediv, "id"), DOM.getElementPropertyInt(e, "id"), DOM.getInnerText(td));

					}
				});

		    	
		    }
		}
		    	
		else {
			String aux="";
			
			 if(estado!=varDefs.CERRADO){
			 aux = "<img border='0' align='top' src='" + varDefs.iProc
					+ "'/>";
			 }
			 else{
				aux = "<img border='0' align='top' src='" + varDefs.iProcRealizado
					+ "'/>";
			 }

			HTML img = new HTML(aux);
			img.addStyleName("seleccionable");
			DOM.setElementAttribute(tdImage, "id", ""+idTramite);
			DOM.appendChild(tdImage, img.getElement());
			DOM.appendChild(tr, tdImage);
			DOM.sinkEvents(img.getElement(), Event.ONCLICK);

			DOM.sinkEvents(img.getElement(), Event.ONCLICK);
			DOM.setEventListener(img.getElement(), new EventListener() {
				public void onBrowserEvent(Event event) {

					Element e = DOM.eventGetCurrentTarget(event);
					Element div = DOM.getParent(e);
					Element tr = DOM.getParent(div);
					auxE = DOM.getChild(tr, 1);
					if(stageId==-1){
					

					int id = DOM.getElementPropertyInt(auxE, "id");
					try {
						verSubProceso(id, true, -1);
					} catch (DesignerException e1) {
						Window.alert( DiagramBuilderExample.mensajeError.accionNoRealizada(100, 

								"verSubProceso",e1.getLocalizedMessage()));
					}
					}
					else
					{
						Element tbody= DOM.getParent(tr);
						Element table=DOM.getParent(tbody);
						Element fasediv=DOM.getParent(table);
						Element trCabecera= DOM.getChild(tbody, 0);
						Element td=DOM.getChild(trCabecera, 1);
					    consultaTramite(DOM.getElementPropertyInt(fasediv, "id"), DOM.getElementPropertyInt(div, "id"), DOM.getInnerText(td));
					}

				}
			});

		
		}
		DOM.appendChild(tr, tdImage);

		Element td = DOM.createTD();
		DOM.setInnerText(td, texto);
		DOM.setElementAttribute(td, "id", "" + idSubProc);
		DOM.appendChild(tr, td);
		DOM.appendChild(tbody, tr);

	}
	
	

	public static Image imgList = null;
	public void closeListado()
	{
		imgList.removeFromParent();
		imgList.setVisible(false);
		
	}

	Widget l = null;
	public void salirListado(){
		if(l!=null && l.getElement()!=null)
			l.removeFromParent();
			closeListado();
			Html.removeAllChildren(DOM.getElementById(varDefs.divListado));
			Designer.listadoOculto();	
			DOM.setStyleAttribute(DOM.getElementById("body"), "overflow", "hidden");
			DOM.setStyleAttribute(getArea().getElement(), "overflow", "auto");
	}
	public void construirCabecera(Element e, String text)
	{
		
		Element divAzul = DOM.createDiv();
		DOM.setElementAttribute(divAzul, "id", "divAzul");
		
		if(imgList==null){
			imgList = new Image();
			imgList.setUrl("./img/close1.png");
			DOM.setElementAttribute(divAzul, "align", "right");
			DOM.sinkEvents(imgList.getElement(), Event.ONCLICK);
			DOM.setEventListener(imgList.getElement(), new EventListener() {
				public void onBrowserEvent(Event event) {
					salirListado();
				}
			});
		}
		
		imgList.setVisible(true);
		DOM.appendChild(divAzul, imgList.getElement());
		DOM.setElementAttribute(divAzul, "align", "right");
		DOM.appendChild(e, divAzul);
		
		Element divModal= DOM.createDiv();
		DOM.setElementAttribute(divModal, "id", "divModal");
		DOM.appendChild(e, divModal);
		 addBr(divModal);
		
		 Element dTitulo = DOM.createDiv();
		 DOM.setElementProperty(dTitulo, "id", varDefs.idTituloTramites);
		 DOM.setInnerText(dTitulo, text);
		 DOM.appendChild(divModal, dTitulo);
		addBr(divModal);
	}
	 public static void addBr(Element e)
	 {
		 DOM.appendChild(e, DOM.createElement("BR"));
		
	 }
	 
	 public void construirTituloNivel2(Element e, String texto)
	 {
		Element dTramitesSel = DOM.createDiv();
		DOM.setElementProperty(dTramitesSel, "id","tituloTramitesSel");
		DOM.setInnerText(dTramitesSel,texto);
		DOM.appendChild(e, dTramitesSel);
	 }
	 
	 public void verVentanaModal(){
		 Element body= DOM.getElementById("body");
		 DOM.setStyleAttribute(body, "overflow", "scroll");
		 DOM.setStyleAttribute(getArea().getElement(), "overflow", "hidden");
	 }
	 
	 
	protected void consultaTramite(int idFase, int idTramite, String nombreFase)
	{
		
		Element divListado = DOM.getElementById(varDefs.divListado);
		TramiteFase tram = procedimiento.getTramite(idFase, idTramite);
		construirCabecera(divListado, nombreFase + " > " + tram.getNombre());
		Element e = DOM.getElementById("divModal");
		construirTituloNivel2(e, mensajes.instanciasCreadas());

		Element contenedorListInstancias = DOM.createDiv();
		DOM.appendChild(contenedorListInstancias, DOM.createElement("BR"));

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
		DOM.setInnerText(td1, mensajes.instance());
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, mensajes.fecha());
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, mensajes.estado());
		DOM.appendChild(tr1, td1);
		DOM.appendChild(body1, tr1);

		List instancias = procedimiento
				.getInstanciasTramites(idFase, idTramite);

		for (int i = 0; i < instancias.size(); i++) {

			Instancia aux = (Instancia) instancias.get(i);

			tr1 = DOM.createTR();

			DOM.setElementAttribute(tr1, "id", "" + aux.getSubTaskId());
			td1 = DOM.createTD();
			DOM.setInnerText(td1, "" + (i + 1));
			DOM.appendChild(tr1, td1);

			td1 = DOM.createTD();

			if (aux.getFechaCreacion() != null)
				DOM.setInnerText(td1, aux.getFechaCreacion());

			DOM.appendChild(tr1, td1);
			td1 = DOM.createTD();
			String mensaje = "";
			if (aux.getEstado() == varDefs.tramiteAbierto) {
				mensaje = mensajes.stateOpen();
			} else
				mensaje = mensajes.stateClose();
			if (aux.getPcdSubId() == -1)
				DOM.setInnerText(td1, mensaje);
			else {
				Element link = DOM.createElement("A");
				DOM.setStyleAttribute(link, "text-decoration", "none");
				DOM.setElementAttribute(link, "class", "displayLink");
				DOM.setElementAttribute(link, "id", "" + aux.getPcdSubId());
				DOM.setInnerText(link, mensaje);
				DOM.setElementAttribute(link, "href", "javascript:;");

				DOM.sinkEvents(link, Event.ONCLICK);
				DOM.setEventListener(link, new EventListener() {
					public void onBrowserEvent(Event event) {

						Element e1 = DOM.eventGetCurrentTarget(event);

						// Obtenemos el subproceso
						int idInstanceSubProc = DOM.getElementPropertyInt(e1,
								"id");
						Element td = DOM.getParent(e1);
						Element tr = DOM.getParent(td);
						int idSubProc = DOM.getElementPropertyInt(tr, "id");
						Element listado = DOM
								.getElementById(varDefs.divListado);
						Html.removeAllChildren(listado);
						Designer.listadoOculto();
						try {
							verSubProceso(idSubProc, true, idInstanceSubProc);
						} catch (DesignerException e) {
							// TODO Auto-generated catch block
							Window.alert(DiagramBuilderExample.mensajeError
									.accionNoRealizada(100,

									"verSubProceso", e.getLocalizedMessage()));
						}

					}
				});
				DOM.setElementAttribute(td1, "id", "link");
				DOM.appendChild(td1, link);
			}

			DOM.appendChild(tr1, td1);

			DOM.appendChild(body1, tr1);

		}

		DOM.appendChild(table1, body1);
		DOM.appendChild(contenedorListInstancias, table1);
		DOM.appendChild(e, contenedorListInstancias);
		Designer.listadoVisible();
		verVentanaModal();
	}
	/**
	 * Crea el botón que aparece cuando vemos un tramite complejo , e indica el evento que va a escuchar 
	 * y las acciones que van a ser realizadas cuando se produzca dicho evento
	 */
	protected void createReturn(){

		Button b = new Button(mensajes.buttonReturn());
		b.addStyleName("nodo-sinc");
		DOM.setElementAttribute(b.getElement(), "id", "volverPadre");
		b.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				try {
					verSubProceso(-1, false, -1);
				} catch (DesignerException e) {
					Window.alert( DiagramBuilderExample.mensajeError.accionNoRealizada(100, 

							"verSubProceso",e.getLocalizedMessage()));
				}
				Element but=DOM.getElementById("volverPadre");
				DOM.removeChild(DOM.getParent(but), but);
			}

		});
		int numHijos=infoPanel.getWidgetCount();
	
		Widget help, print;
		help=infoPanel.getWidget(numHijos-1);
		print=infoPanel.getWidget(numHijos-2);
	
		infoPanel.remove(help);
		infoPanel.remove(print);
		infoPanel.add(b);
		infoPanel.add(print);
		infoPanel.add(help);
		infoPanel.setSpacing(0);
		infoPanel.setCellVerticalAlignment(b, VerticalPanel.ALIGN_MIDDLE);
		
	
	}
	
	
	
	/**
	 * Por definicion no se añadiran ninguna celda con el numero de instancias, este método
	 * solo hay que redefinirlo para el caso que el entorno sea tramitador
	 * @param tr:Fila 
	 */
	public void addTDNumInstances(Element tr){
	
	}
	
	public HTML addNoImg() {
		String aux = "<img id='gif1' border='0' align='top' src='" + varDefs.iNoIcon
		+ "'/>";

		HTML img = new HTML(aux);
		return img;
		
	}
	/**
	 * Crea el objeto que se va a añadir a la fase para indicar que los tramites estan visibles y al pulsar sobre el se colapsan
	 * @return Objeto HTML con el codigo html que se correponde a la imagen (-)
	 */
	public HTML addImgMinus() {
		
		
		String aux = "<img id='gif' border='0' align='top' src='" + varDefs.iMinus
				+ "'/>";

		HTML img = new HTML(aux);

		DOM.sinkEvents(img.getElement(), Event.ONCLICK);
		DOM.setEventListener(img.getElement(), new EventListener() {
			public void onBrowserEvent(Event event) {

				Element auxi= DOM.eventGetTarget(event);
				String src=DOM.getElementAttribute(auxi,"src");
				if(src!=null && "".compareTo(src)!=0 && Html.contains(src, "img")){
		      
					displayTramitesFase(DOM.eventGetTarget(event), false);
				
				}
				
				Designer.update();

			}
		});

		return img;

	}

	
	public abstract void guardarPosicion(Widget ele) ;
		

	/**
	 * Este método sera llamado para todas aquellos conectores que se precargan, los que va creando el usuario dinámicamente utilizan
	 * el método add
	 * @param c conexion a añadir 
	 */
	protected static void addManual(AbstractConnection c) {
		add(c);

	}

	/**
	 * Crea una conexión de tipo Rectilinea entre los conectores recibidos como parámetro, se almacena en una vble global
	 * la transición creada para ser posteriormente almacenada en las estructuras internas que maneja el diseñador
	 * @param a conector origen 
	 * @param b conector destino
	 * @throws DesignerException 
	 */
	protected void connectManual(Connector a, Connector b) throws DesignerException {

		RectilinearTwoEndedConnectionCustom aux = new RectilinearTwoEndedConnectionCustom(
				idProcedimiento, a, b);

		addManual(aux);

		transicionCreada = aux;

	}

	
	/**
	 * 
	 * @param img: Imagen que produce el evento, sobre la que el usuario ha pulsado
	 * @param visible: True si hay que visualizar los trámites , false en caso contrario
	 */
	public void displayTramitesFase(Element img, boolean visible) {

		
		//A través de la imagen accedemos a la tabla que aloja toda la fase para obtener su id
		if(img!=null){
		Element div = DOM.getParent(img);
		Element td = DOM.getParent(div);
		Element tr = DOM.getParent(td);
		Element tbody = DOM.getParent(tr);
		Element table = DOM.getParent(tbody);
		Element pTable = DOM.getParent(table);

		int idFase = DOM.getElementPropertyInt(pTable, "id");
	
		//Obtenemos los datos de la fase sobre la que tenemos que mostrar/ocultar los tramites
		DrawObject drawObject = (DrawObject) elementos
				.get(new Integer(idFase));
		if (drawObject != null) {
			drawObject.setDesplegado(visible);
			elementos.put(new Integer(idFase), drawObject);
		}

		//La manera de hacer visible o invisible los tramites no es añadir el stylo none si queremos que no se vea
		//o block si queremos que se vea
		int numChild = DOM.getChildCount(tbody);
		String display;
		//Cambiar el + por - si visible a true y al reves en caso contrario
		Element td1tr = DOM.getChild(tr, 0);
		DOM.removeChild(td1tr, DOM.getChild(td1tr, 0));
		DrawObject fase = (DrawObject) elementos
				.get(new Integer(idFase));
		
	
		if (visible) {
			display = "block";

			fase.setDesplegado(true);
			elementos.put(new Integer(idFase), fase);
			DOM.appendChild(td1tr, addImgMinus().getElement());

		} else {
			display = "none";

			fase.setDesplegado(false);
			elementos.put(new Integer(idFase), fase);

			DOM.appendChild(td1tr, addImgPlus().getElement());
		}
		for (int i = 1; i < numChild; i++) {

			 Element hijo=DOM.getChild(tbody, i);
		
			DOM.setStyleAttribute(hijo, "display", display);

		}
		Element hijo1=DOM.getChild(tbody,0);
		DOM.setStyleAttribute(hijo1, "display", "block");

		}
		
	}

	
	/**
	 * Crea la imagen que va a ser añadida a las fases y captura sus eventos
	 * @param Devuelve un objeto de tipo HTML con el codigo de la imagen creada
	 */
	HTML img=null;
	public HTML addImgPlus() {
		String aux = "<img id='gif' border='0' align='top' src='" + varDefs.iplus
				+ "'/>";

		 img = new HTML(aux);

		DOM.sinkEvents(img.getElement(), Event.ONCLICK);
		DOM.setEventListener(img.getElement(), new EventListener() {
			public void onBrowserEvent(Event event) {
				Element auxi = DOM.eventGetTarget(event);
				String src=DOM.getElementAttribute(auxi,"src");
				if( src!=null && "".compareTo(src)!=0 && Html.contains(src, "img")){
				
				displayTramitesFase(DOM.eventGetTarget(event), true);
				}
	
			}
		});

		return img;

	}

	
	
	public void addPlus(int idFase, Element div, boolean desplegado) {

		Element table = DOM.getChild(div, 0);
		Element tbody = DOM.getChild(table, 0);
		Element trFase = DOM.getChild(tbody, 0);
		Element tdFase = DOM.getChild(trFase, 0);
		DOM.removeChild(trFase, tdFase);
		Element tdImg = DOM.createTD();
		if(desplegado){
			DOM.appendChild(tdImg, addImgMinus().getElement());
		}
		else{
			DOM.appendChild(tdImg, addImgPlus().getElement());
		}
		DOM.appendChild(trFase, tdImg);
		DOM.appendChild(trFase, tdFase);
	}

	/**
	 * 
	 * Se crea una tabla que se le añadirá como hijo al parámetro eleCreado, esta tabla contendra la imagen + o - , el nombre de la fase
	 * y los tramites que tiene actualmente asociados, si no tuviera ninguno ,la tabla solo contendra el nombre de la fase
	 * @param eleCreado : Objeto de tipo widget, perosin informacion
	 * @param text: Nombre de la fase 
	 * @param desplegado: True si vamos a mostrar sus tramites o false en caso contario
	 */
	protected void createFase(Widget eleCreado, String text, boolean desplegado) {
		
		
		Element table = DOM.createTable();
		Element tbody = DOM.createTBody();
		Element tr = DOM.createTR();
		Element td = DOM.createTD();
		DOM.setElementAttribute(tbody, "id", "tablaNodo");
		DOM.setInnerText(td, text);
		DOM.setElementAttribute(td, "id", "cabeceraNodo");
		DOM.appendChild(tr, td);
		DOM.appendChild(tbody, tr);
		DOM.appendChild(table, tbody);
		DOM.appendChild(eleCreado.getElement(), table);
     
		eleCreado.setSize(varDefs.AnchoFase, varDefs.LargoFase);
		eleCreado.setStyleName(eleCreado.getStyleName()+" "+ varDefs.styleToolboxNode);
		

	}

	/**
	 * Constructor primitivo
	 */
	public AbstractConnectionsExample() {
		this.dragController = createDragController();
		DOM.setStyleAttribute(getArea().getElement(), "overflow", "scroll");
		DOM.setStyleAttribute(getArea().getElement(), "width", "100%");
		DOM.setStyleAttribute(getArea().getElement(), "height", "100%");

		createContents();
	}

	/**
	 * Método abstracto, cada hijo implementara el contenido que ha de tener el diseñador
	 */
	protected abstract void createContents();

	/**
	 * Añade coneciones al diseñador
	 * 
	 * @param c conexion a ser añadida
	 */
	protected static void add(AbstractConnection c) {
		getArea().add(c);
		connections.add(c);
	}

	/**
	 * Elimina la conexion
	 * 
	 * @param c
	 */
	protected void remove(Connection c) {
		connections.remove(c);
	}

	/**
	 * Elimina todas las conexiones
	 */
	public void removeAll() {
		connections.clear();
	}

	/**
	 * Recalcula y dibuja todas las conexion del diseñador
	 */
	public void update(boolean flag) {

		for (Iterator i = connections.iterator(); i.hasNext();) {
			Connection c = (Connection) i.next();
			c.update();
		}

	}

	/**
	 * Conecta dos conectores
	 * Método abstracto que ha de ser implementado en los hijos
	 * 
	 * @param a: Conector origen
	 * @param b: Conector destino
	 */
	public static void connect(Connector a, Connector b){};

	/**
	 * Crea un conector
	 * 
	 * @param text : Label del conector
	 * @param left: Posicion horizontal
	 * @param top : Posicion vertical 
	 * @param direction : Indica la direccion permitida para este conector, si es nulo quiere decir que las permite todas
	 *          
	 * @return un objeto de tipo UIObjectConnector 
	 */
	protected UIObjectConnector createConnector(String text, int left, int top,
			Direction direction) {
		Label l = new Label(text);
		l.addStyleName("example-connector");
		getArea().add(l, left, top);
		dragController.makeDraggable(l);
		if (direction != null) {
			return UIObjectConnector.wrap(l, new Direction[] { direction });
		}
		return UIObjectConnector.wrap(l);
	}

	/**
	 *
	 * Crea el controlador DRAG, que permite que los objetos se muevan
	 * @return DragController
	 */
	protected DragController createDragController() {
		PickupDragController dragController = new PickupDragController(
				getArea(), true) {
			public BoundaryDropController newBoundaryDropController(
					AbsolutePanel boundaryPanel, boolean allowDropping) {
				return new BoundaryDropController(boundaryPanel, allowDropping) {
					
					public DragEndEvent onDrop(Widget reference,
							Widget draggable, DragController dragController) {
						
						DragEndEvent dragEndEvent = super.onDrop(reference,
								draggable, dragController);
						
						// Guardar la posición del elemento
						guardarPosicion(draggable);
						
						return dragEndEvent;
					}

					public void onMove(Widget reference, Widget draggable,
							DragController dragController) {
				
						super.onMove(reference, draggable, dragController);
						UIObjectConnector c = UIObjectConnector
								.getWrapper(draggable);
						if (c != null) {
							c.update();
						}
					}
				};
				
			}

			public void makeDraggable(Widget widget) {
				super.makeDraggable(widget);
				DOM.setStyleAttribute(widget.getElement(), "position",
						"absolute");
				DOM.setStyleAttribute(widget.getElement(), "zIndex", "100");
			}
		};
		return dragController;
	}

	public static boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	
	
	
}
