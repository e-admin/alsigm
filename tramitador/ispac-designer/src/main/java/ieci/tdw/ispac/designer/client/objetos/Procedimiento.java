package ieci.tdw.ispac.designer.client.objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Procedimiento implements IsSerializable  {
	
	
	private int idProc = -1;
	private String nombreProc = null;
	private int estado=-1;
	private boolean tipoProcedimiento=false;
	private boolean procedimientoTerminado=false;
	
	
	private List fases = new ArrayList();
	private List nodos = new ArrayList();
	private List flujos = new ArrayList();
	
	private Map tramitesFases = new HashMap();
	
	
	public List getInstanciasTramites(int idFase, int idTramite)
	{
		List tramites = (List) tramitesFases.get(new Integer(idFase));
		
		for(int i=0; i< tramites.size(); i++)
		{
			TramiteFase tram= (TramiteFase) tramites.get(i);
			if(tram.getPTaskId()==idTramite)
				return tram.getInstancias();
		}
		return new ArrayList();
		
	}
	
	public TramiteFase getTramite(List tramites, int idTramite)
	{if(tramites!=null){
		for(int i=0; i<tramites.size(); i++)
			if(((TramiteFase)tramites.get(i)).getPTaskId()==idTramite)
				return (TramiteFase) tramites.get(i);
	}
		return null;
	}
	public TramiteFase getTramite(int idFase, int idTramite)
	{
		return getTramite( (List) tramitesFases.get(new Integer(idFase)), idTramite);
		
	}
	
	
	
	
	
	public Procedimiento() {
		super();
	}
	
	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Procedimiento(int idProc, String nombreProc) {
		this();
		this.idProc=idProc;
		this.nombreProc=nombreProc;
	}

	public Procedimiento(int idProc, String nombreProc, List fases) {
		this();
		this.idProc=idProc;
		this.nombreProc=nombreProc;
		this.fases=fases;
	}

	public void addTramitesFases(int id  , List tramites){
		tramitesFases.put(new Integer(id), tramites);
		
	}
	
	public Map getTramitesFases() {
		return tramitesFases;
	}
	public void setTramitesFases(Map tramitesFases) {
		this.tramitesFases = tramitesFases;
	}
	
	public void addFase(DrawObject o)
	{
		fases.add(o);
	}
	
	public void addNodo(DrawObject o)
	{
		nodos.add(o);
	}
	
	
	
	public void addFlow(DrawFlow o)
	{
		flujos.add(o);
	}

	public int getIdProc() {
		return idProc;
	}

	public void setIdProc(int idProc) {
		this.idProc = idProc;
	}

	public String getNombreProc() {
		return nombreProc;
	}

	public void setNombreProc(String nombreProc) {
		this.nombreProc = nombreProc;
	}

	public List getFases() {
		return fases;
	}

	public void setFases(List fases) {
		this.fases = fases;
	}
	public List getNodos() {
		return nodos;
	}
	public void setNodos(List nodos) {
		this.nodos = nodos;
	}
	
	public List getFlujos() {
		return flujos;
	}
	public void setFlujos(List flujos) {
		this.flujos = flujos;
	}

	public boolean isTipoProcedimiento() {
		return tipoProcedimiento;
	}

	public void setTipoProcedimiento(boolean tipoProcedimiento) {
		this.tipoProcedimiento = tipoProcedimiento;
	}

	public void setEstadoFase(int idFase, int estado)
	{
		boolean encontrado=false;
		for(int i=0; i<fases.size() && !encontrado;i++)
		{ 
			if(((DrawObject)fases.get(i)).getId()==idFase)
			{
				encontrado=true;
				((DrawObject)fases.get(i)).setEstado(estado);
			}
			
		}
	}
	
	
	public void setEstadoTramiteFase(int idFase, int idTramite, int estado)
	{
		
		boolean encontrado=false;
		
		List tramites= (List) tramitesFases.get(new Integer(idFase));
		
		for(int i=0; i<tramites.size() && !encontrado;i++)
		{ 
			if(((TramiteFase)tramites.get(i)).getPTaskId()==idTramite)
			{
				encontrado=true;
				((TramiteFase)tramites.get(i)).setEstado(estado);
			}
			
		}
		

	}

	public boolean isProcedimientoTerminado() {
		return procedimientoTerminado;
	}

	public void setProcedimientoTerminado(boolean procedimientoTermiado) {
		this.procedimientoTerminado = procedimientoTermiado;
	}
	

}
