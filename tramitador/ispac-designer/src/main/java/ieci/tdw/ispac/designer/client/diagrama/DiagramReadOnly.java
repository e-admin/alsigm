package ieci.tdw.ispac.designer.client.diagrama;

import ieci.tdw.ispac.designer.client.Designer;
import ieci.tdw.ispac.designer.client.exceptions.DesignerException;
import ieci.tdw.ispac.designer.client.helper.varDefs;
import ieci.tdw.ispac.designer.client.objetos.DrawFlow;
import ieci.tdw.ispac.designer.client.objetos.DrawObject;
import ieci.tdw.ispac.designer.client.objetos.Procedimiento;
import ieci.tdw.ispac.designer.client.objetos.TramiteFase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.catalina.Logger;

import pl.balon.gwt.diagrams.client.connector.Connector;
import pl.balon.gwt.diagrams.client.connector.UIObjectConnector;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class DiagramReadOnly extends AbstractConnectionsExample {
	
	
	//Indica si es un expediente cerrado o abierto.
	int tipoExp=-1;

	/**
	 * 
	 * @param param Parametro con el stageId o nIdProc 
	 * @param tipo indica si esta abierto o cerrado el expediente
	 * @param locale
	 */
	public DiagramReadOnly(int param, String locale, int tipo)
	{
		
		super(param, locale, tipo);
		tipoExp=tipo;
		if(tipo==EXP_ABIERTO)
			stageId=param;
		else{
		nIdProc=param;
		}
	}

	
	/**
	 * Se añade una celda con el número de instancias que ha hecho el usuario del trámite que 
	 * estamos construyendo.
	 * @param tr:Fila 
	 */
	public void addTDNumInstances(Element tr){
		Element td=DOM.createTD();
		DOM.setInnerText(td, "0");
		DOM.appendChild(tr, td);
	}
	
	/**
	 * Implementación del método precargarDatos, lee la información que tiene el objeto 
	 * procedimiento y crea los elementos (fases, nodos y flujos) de los que esta compuestos.
	 * También informa el objeto procedimiento que tenemos como varible global utilizado a lo largo
	 * de la vida del diseñador
	 * 
	 */
	public void precargarDatos(Procedimiento proc){
		
		
		readOnly=true;
		procedimiento.setNombreProc(proc.getNombreProc());
		procedimiento.setEstado(proc.getEstado());
		procedimiento.setTipoProcedimiento(proc.isTipoProcedimiento());
		procedimiento.setTramitesFases(proc.getTramitesFases());
		procedimiento.setIdProc(proc.getIdProc());
		procedimiento.setProcedimientoTerminado(proc.isProcedimientoTerminado());
		idProcedimiento=procedimiento.getIdProc();
		Element nombreProc=DOM.getElementById("infoPanel");
	
		DOM.setInnerText(nombreProc, procedimiento.getNombreProc());
	
		//Estado del procedimiento o subproceso
		
		//Tenemos que ir guardando las fases creadas (labels) para luego hacer los conectores
			
			List fases= new ArrayList();
			fases=proc.getFases();
		
			
			tramitesFases = new HashMap();
			tramitesFases= proc.getTramitesFases();
			
			int indiceSinPos=0;
			//Precargamos las fases
			for(int i=0; i< fases.size(); i++)
			{
				DrawObject aux = (DrawObject) fases.get(i);
			    if(aux.getLeft()==0 || aux.getTop()==0)
			    {
			    	int numFila=indiceSinPos/5;
			    	aux.setTop(100+ (indiceSinPos/5)*100);
			    	if(numFila%2==0)
			    	aux.setLeft(20+ (indiceSinPos%5 )*150);
			    	else
			    		aux.setLeft(20+  750-(indiceSinPos%5 +1 )*150);	
			  
			    	indiceSinPos+=1;
			    }
				Integer id=  new Integer(aux.getId());
				Label p= createElemento(aux,varDefs.stylePhase , varDefs.EFASE, false);
				
				boolean noHayTramites=true;
			
				if(tramitesFases.containsKey(id))
				{
					
					List tramites= new ArrayList();
					tramites=(List) tramitesFases.get(id);
					
					for(int j=0; j<tramites.size(); j++){
				
						TramiteFase auxTram = (TramiteFase) tramites.get(j);
						if(auxTram.getEstado()!=varDefs.NO_INICIADO){
							noHayTramites=false;
						addTramite(aux.getId(),auxTram.getNombre() ,auxTram.getEstado(), aux.isDesplegado(),auxTram.getSubTaskId(), auxTram.getPTaskId(), p.getElement());
						
						}
					}
				
				}
			
				if(!noHayTramites){
					super.addPlus(aux.getId(), p.getElement(),false);
				}
				
				
				p.addStyleName(varDefs.styleToolboxNode);
				if(aux.getTipo()==-1)
					p.addStyleName(varDefs.styleToolboxPhaseEspecifica);
				
				
				elementos.put(id, aux);
				objetos.put( id, p );
			
			}

			List nodos= new ArrayList();
			nodos=proc.getNodos();
			//Precargamos los nodos
			for(int i=0; i< nodos.size(); i++)
			{
				DrawObject aux = (DrawObject) nodos.get(i);
				if(aux.getLeft()==0 || aux.getTop()==0)
			    {
					int numFila=indiceSinPos/5;
			    	aux.setTop(100+ (indiceSinPos/5)*100);
			    	if(numFila%2==0)
			    	aux.setLeft(20+ (indiceSinPos%5 )*150);
			    	else
			    		aux.setLeft(20+  750-(indiceSinPos%5  +1)*150);	
			  
			    	indiceSinPos+=1;
			    }
				Integer id=  new Integer(aux.getId());
				Label p= createElemento(aux, varDefs.styleNode ,varDefs.ENODO,false);
				p.addStyleName(varDefs.styleToolboxNodoSinc);
				
				elementos.put(id, aux);
				objetos.put( id, p );
			
			}
			
			
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
					
					Window.alert( DiagramBuilderExample.mensajeError.accionNoRealizada(100, 

							"connectManual",e.getLocalizedMessage()));
				}
				if(aux.getEvents()!=null && aux.getEvents().size()>0)
					addCondicion(aux);
	
				addArrow(aux);

			}
				//Eliminar todas las lista creadas , destruirlas
			   fases.clear();
			   flujo.clear();
			   nodos.clear();
		}

	
	/**
	 * Implementación del méetodo createElemento, para el 
	 */
	protected   Label createElemento(DrawObject obj,  String style, int tipo,  boolean desplegado) {
		Label eleCreado = new Label() ;
	
	 if(tipo!= varDefs.ENODO){	
	
		 createFase(eleCreado , obj.getNombre(), desplegado);
		 if(AbstractConnectionsExample.EXP_CERRADO==tipoExp && procedimiento.isProcedimientoTerminado())
			 eleCreado.addStyleName("realizado");
		 else{
		 
		 if(obj.getEstado()==varDefs.CERRADO)
			 eleCreado.addStyleName("realizado");
		 else if(obj.getEstado()==varDefs.INICIADO)
			 eleCreado.addStyleName("enCurso");
		 else
			 eleCreado.addStyleName("pendiente");
		 }
		
		
	 }
	 else{
		 
		 
		   createNodo(eleCreado, obj.getTipo());

		 
	 }
	   
	 if(obj.getId()!=-1){
		DOM.setElementAttribute(eleCreado.getElement(), "id", ""+obj.getId());
		getArea().add(eleCreado, obj.getLeft(), obj.getTop());
		dragController.makeDraggable(eleCreado);
	 }
		
	 eleCreado.addStyleName(style);
		
	

		
		return eleCreado;
	}
	protected void createContents() {

		
		panel = new AbsolutePanel();
		vp = new VerticalPanel();
		infoPanel= new HorizontalPanel();
		vp.setStyleName("builder");
		vp.add(infoPanel);
		
		infoPanel.setWidth("100%");
		
		vp.add(panel);
		Label label=new Label(mensajes.infoLoading());
	
		infoPanel.setStyleName("infoPanel");
		DOM.setElementAttribute(label.getElement(), "id", "infoPanel");
		infoPanel.add(label);
		infoPanel.setCellWidth(label, "60%");
		HorizontalPanel leyenda = new HorizontalPanel();
		Label realizado= new Label();
		realizado.setText(mensajes.realizado());
		realizado.addStyleName("realizado");
		realizado.addStyleName("leyenda");
		Label enCurso=new Label();
		enCurso.addStyleName("enCurso");
		enCurso.addStyleName("leyenda");
		enCurso.setText(mensajes.enCurso());
		Label pendiente=new Label();
		pendiente.setText(mensajes.pendiente());
		pendiente.addStyleName("pendiente");
		pendiente.addStyleName("leyenda");
		leyenda.add(realizado);
		leyenda.add(enCurso);
		leyenda.add(pendiente);
		infoPanel.add(leyenda);
		infoPanel.setCellVerticalAlignment(leyenda, VerticalPanel.ALIGN_MIDDLE);
		leyenda.setCellWidth(leyenda, "27%");
		getArea().add(vp);
		addIconPrint();
		//addIconHelp(mensajes.helpEntrySelSubProc());ç
		addIconHelp();
		// 3) Creamos un objeto asincrono para manejar el resultado
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {
				
				precargarDatos((Procedimiento)result);
				
				//Designer.tabs.selectTab(0);
				Designer.update();
				
			}

			public void onFailure(Throwable caught) {
				
			
				Window.alert(mensajeError.accionNoRealizada(100, "getProcedure",caught.getLocalizedMessage()));
			
			}
		};
		
		if(nIdProc==-1)
		servicio.getProcedureByStageId(stageId, callback);
		else
			servicio.getProcedureBynIdProc(nIdProc, callback);

		
	}

	protected  void deleteContents( boolean update) {

		int numHijos=DOM.getChildCount(getArea().getElement());
		for(int i=1;i< numHijos; i++ ){
			
			Element hijo=DOM.getChild(getArea().getElement(), i);
			DOM.removeChild(getArea().getElement(), hijo);
		    numHijos=numHijos-1;
		    i=i-1;
		}

	}
	public static void connect(Connector a, Connector b) {
		// TODO Auto-generated method stub
		
	}

	public void guardarPosicion(Widget ele) {
		// TODO Auto-generated method stub
		
	}

}