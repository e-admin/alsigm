package ieci.tdw.ispac.designer.client;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import ieci.tdw.ispac.designer.client.diagrama.AbstractConnectionsExample;
import ieci.tdw.ispac.designer.client.diagrama.DiagramBuilderExample;
import ieci.tdw.ispac.designer.client.diagrama.DiagramReadOnly;
import ieci.tdw.ispac.designer.client.helper.varDefs;
import ieci.tdw.ispac.designer.client.messages.ErrorMensajes;
import ieci.tdw.ispac.designer.client.messages.Mensajes;
import ieci.tdw.ispac.designer.client.service.GwtIService;
import ieci.tdw.ispac.designer.client.service.GwtIServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Designer extends BaseExamplesEntryPoint {


	//public final static TabPanel tabs = new TabPanel();
	public final static AbsolutePanel absPanel = new AbsolutePanel();
	public GwtIServiceAsync servicio;
	public Mensajes mensajes=(Mensajes) GWT.create(Mensajes.class);
    public int entorno;
	public static ErrorMensajes mensajeError=(ErrorMensajes) GWT.create(ErrorMensajes.class);
	
	
	
	public static HashMap parseParamString(String string){

		  String[] ray = string.substring(1, string.length()).split("&");

		  HashMap map = new HashMap();

		 

		  for (int i=0; i<ray.length; i++){
		    GWT.log("ray["+i+"]="+ray[i],null);
		    String[] substrRay = ray[i].split("=");
		    map.put(substrRay[0], substrRay[1]);

		  }

		  return map;

		}
	public static native String getParamString() /*-{

    return $wnd.location.search;

}-*/;
	public void validar(){
		
		//Obtengo el parametro que recibimos , si recibimos pcdId estamos en el entornocatalogo si recibimos numExp en el tramitador
		
		//TODO Validar en sesión que si nos pasan el pcdId verdaderamente estamos en el catalogo y lo mismo para el tramitador
	
		// 1)Creo el proxy del cliente
		servicio = (GwtIServiceAsync) GWT.create(GwtIService.class);

		// 2)Especificar la url que en la que nuestra implementación de servicio
		// esta corriendo
		ServiceDefTarget endpoint = (ServiceDefTarget) servicio;
		String URLmodulo = GWT.getModuleBaseURL() + "GwtIService.gwt";
		endpoint.setServiceEntryPoint(URLmodulo);
		
		
		
		String paramstr =getParamString();
		if(paramstr!=null && paramstr.trim().compareTo("")!=0)
		{
			HashMap params = parseParamString(paramstr);
			Object localeParam= params.get("locale");
			String locale="es";
			if(localeParam!=null)
				locale= localeParam.toString();
			 if(params.containsKey("stageId") && params.get("stageId")!=null){
				 entorno=varDefs.TRAMITADOR;
				 cargaInfo(0 ,Integer.parseInt( params.get("stageId").toString()),-1, locale);
			}
			 else if(params.containsKey("pcdId") && params.get("pcdId")!=null){
				 entorno=varDefs.CATALOGO;
				 cargaInfo(Integer.parseInt(params.get("pcdId").toString()), 0, -1, locale);
			 }
			 else if(params.containsKey("processId") &&  params.get("processId")!=null){
				 entorno=varDefs.TRAMITADOR;
				 cargaInfo(0, 0, Integer.parseInt(params.get("processId").toString()), locale);
			 }
			 else
			 {
				 Window.alert(mensajeError.parametroNoRecibido(200, "pcdId-stageId-processId"));
			 }
			
		}
	
		 
		 else{
			 Window.alert(mensajeError.parametroNoRecibido(200, "pcdId-stageId-processId"));
			 
		 }
		 
		
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {
			
			}
		
			public void onFailure(Throwable caught) {	
				Window.alert(mensajeError.accionNoRealizada(100, "Validar",caught.getLocalizedMessage()));	
		}
			
		};
		//servicio.getEntorno(callback);
	
	
		
		
	}
	public void cargaInfo(int idProc, int stageId, int nIdProc, String locale){
		//RootPanel.get(varDefs.divDiagrama).add(tabs);
		RootPanel.get(varDefs.divDiagrama).add(absPanel);
		
		 if(locale==null || locale.compareTo("")==0)
		       locale="es";
		
		if(entorno==1) {
			//tabs.add(new DiagramBuilderExample(idProc), mensajes.designerG() );
			absPanel.add(new DiagramBuilderExample(idProc, locale));
		} else {
			//tabs.add(new DiagramReadOnly(idProc), mensajes.designerG());
			if(nIdProc==-1)
			absPanel.add(new DiagramReadOnly(stageId, locale, AbstractConnectionsExample.EXP_ABIERTO));
			else
				absPanel.add(new DiagramReadOnly(nIdProc, locale,AbstractConnectionsExample.EXP_CERRADO));
		}
		
		RootPanel.get(varDefs.divListado).setVisible(false);
		RootPanel.get(varDefs.divTramites).setVisible(false);
		
		RootPanel.get(varDefs.divSubProceso).setVisible(false);
		RootPanel.get("layer").setVisible(false);
	
	
	}
	
	
	public void onLoad() {
		
		validar();
		
	

	}
public static void listadoVisible(){
		

	    RootPanel.get("layer").setVisible(true);
		RootPanel.get(varDefs.divListado).setVisible(true);
	
	}
	
	public static void listadoOculto(){
		
		RootPanel.get(varDefs.divListado).setVisible(false);
		RootPanel.get("layer").setVisible(false);
		RootPanel.get(varDefs.divTramites).setVisible(false);
		RootPanel.get(varDefs.divDiagrama).setVisible(true);
		
	}
	

	
	public static void tramitesOculto(){
		
		RootPanel.get(varDefs.divListado).setVisible(false);
		RootPanel.get("layer").setVisible(false);
		RootPanel.get(varDefs.divTramites).setVisible(false);
		RootPanel.get(varDefs.divDiagrama).setVisible(true);
		
	}

	
	
	public static void tramitesVisible(){

		RootPanel.get("layer").setVisible(true);
		
		RootPanel.get(varDefs.divListado).setVisible(false);
		Element tramites=RootPanel.get(varDefs.divTramites).getElement();
		DOM.setStyleAttribute(tramites,"visibility" , "visible");
		RootPanel.get(varDefs.divTramites).setVisible(true);
		
	}
		
	public static void removeAllConnections()
	{
		
		AbstractConnectionsExample e = (AbstractConnectionsExample) absPanel.getWidget(0);
		e.removeAll();
	}
	public static void update()
	{
		AbstractConnectionsExample e = (AbstractConnectionsExample) absPanel
			.getWidget(0);

        e.update(false);
        
        
	}

	




}