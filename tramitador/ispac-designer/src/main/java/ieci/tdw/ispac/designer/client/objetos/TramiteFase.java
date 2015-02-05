package ieci.tdw.ispac.designer.client.objetos;



import ieci.tdw.ispac.designer.client.helper.varDefs;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;



public class TramiteFase implements IsSerializable {
	
	private int pTaskId;
	private int ctTaskId;
	private String nombre;
	private boolean asociado;
	private boolean change;
	private int subTaskId;
	
	//Orden del tramite dentro de la fase va de 1 a n, como el ordenTemp
	private int orden=0;
	
	//Orden que tendrá hasta que el usuario finalmente salve, si cancela no se tiene en cuenta
	private int ordenTemp=0;
	
	private int numInstancesOpen=0;
	private int numInstancesClose=0;
	

	
	private List instancias;
	

	
   public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
public List getInstancias() {
		return instancias;
	}
	public void setInstancias(List instancias) {
		this.instancias = instancias;
	}
	
	
	/*
	 * Atributo utilizado para los tramites
	 * estado=0 No iniciado
	 * estado=1 En curso
	 * estado=2 Realizada
	 * */
	private int estado=varDefs.NO_INICIADO;

	
	
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getSubTaskId() {
		return subTaskId;
	}
	public void setSubTaskId(int subTaskId) {
		this.subTaskId = subTaskId;
	}
	public int getPTaskId() {
		return pTaskId;
	}
	public void setPTaskId(int taskId) {
		pTaskId = taskId;
	}
	public int getCtTaskId() {
		return ctTaskId;
	}
	public void setCtTaskId(int ctTaskId) {
		this.ctTaskId = ctTaskId;
	}
	public boolean isAsociado() {
		return asociado;
	}
	public void setAsociado(boolean asociado) {
		this.asociado = asociado;
	}
	
	public boolean isChange() {
		return change;
	}
	public void setChange(boolean change) {
		this.change = change;
	}
	public void changeAsociado(){
		change=!change;
	/*	if(asociado)
			asociado=false;
		else 
			asociado=true;*/
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public TramiteFase(String nombre, int subTaskId)
	{
		super();
		this.nombre=nombre;
		this.subTaskId=subTaskId;
		
	}
	public TramiteFase(String nombre, int subTaskId, int pTaskId)
	{
		super();
		this.nombre=nombre;
		this.subTaskId=subTaskId;
		this.pTaskId=pTaskId;
		
	}
	public TramiteFase(int cTaskId, int pTaskId, String nombre, boolean asociado) {
		super();
		this.ctTaskId=cTaskId;
		this.pTaskId=pTaskId;
		this.nombre = nombre;
		this.asociado=asociado;
		this.change=false;
		
	}
	public TramiteFase(int cTaskId, int pTaskId, String nombre, boolean asociado, int subTaskId) {
		super();
		this.ctTaskId=cTaskId;
		this.pTaskId=pTaskId;
		this.nombre = nombre;
		this.asociado=asociado;
		this.change=false;
		this.subTaskId=subTaskId;
	
	}
	
	public TramiteFase() {
	
		super();
	
	}

	
	public int getNumInstancesOpen() {
		return numInstancesOpen;
	}
	public void setNumInstancesOpen(int numInstancesOpen) {
		this.numInstancesOpen = numInstancesOpen;
	}
	public int getNumInstancesClose() {
		return numInstancesClose;
	}
	public void setNumInstancesClose(int numInstancesClose) {
		this.numInstancesClose = numInstancesClose;
	}
	public TramiteFase(int taskId, int ctTaskId, String nombre,
			boolean asociado, boolean change, int subTaskId,
			int numInstancesOpen, int numInstancesClose, List instancias,
			int estado) {
		super();
		pTaskId = taskId;
		this.ctTaskId = ctTaskId;
		this.nombre = nombre;
		this.asociado = asociado;
		this.change = change;
		this.subTaskId = subTaskId;
		this.numInstancesOpen = numInstancesOpen;
		this.numInstancesClose = numInstancesClose;
		this.instancias = instancias;
		this.estado = estado;
	}
	public int getOrdenTemp() {
		return ordenTemp;
	}
	public void setOrdenTemp(int ordenTemp) {
		this.ordenTemp = ordenTemp;
	}


}
