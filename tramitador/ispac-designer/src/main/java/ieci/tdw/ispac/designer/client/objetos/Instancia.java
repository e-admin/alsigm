package ieci.tdw.ispac.designer.client.objetos;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Instancia implements IsSerializable {
	
	private int pTaskId;
	private int ctTaskId;
	private String usrCreador;
	
	public String getUsrCreador() {
		return usrCreador;
	}
	public void setUsrCreador(String usrCreador) {
		this.usrCreador = usrCreador;
	}
	public Instancia() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String fechaCreacion;
	private int subTaskId;
	private int estado;
	
	//Identificador de la intancia cuando es un subproceso
	private int pcdSubId;
	
	
	
	public Instancia(int taskId, int ctTaskId, String fechaCreacion, int estado, int subTaskId , int pcdSubId) {
		super();
		pTaskId = taskId;
		this.ctTaskId = ctTaskId;
		this.fechaCreacion = fechaCreacion;
		this.estado = estado;
		this.subTaskId=subTaskId;
		this.pcdSubId=pcdSubId;
		
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
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
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
	public int getPcdSubId() {
		return pcdSubId;
	}
	public void setPcdSubId(int pcdSubId) {
		this.pcdSubId = pcdSubId;
	}
	

}
