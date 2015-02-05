package ieci.tdw.ispac.designer.client.objetos;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DrawFlow implements IsSerializable {
	
	private int id;
	private int idOrigen;
	private int idDestino;
	
	private List events= new ArrayList();
	
	

	public List getEvents() {
		return events;
	}

	public void setEvents(List events) {
		this.events = events;
	}

	public int getId() {
		return id;
	}
	
	public String getIdP(){
		
		return "p"+id;
		
	}
	public String  getIdC(){
		return "c"+id;
	}
	public String  getIdS(){
		return "f"+id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdOrigen() {
		return idOrigen;
	}
	public void setIdOrigen(int idOrigen) {
		this.idOrigen = idOrigen;
	}
	public int getIdDestino() {
		return idDestino;
	}
	public void setIdDestino(int idDestino) {
		this.idDestino = idDestino;
	}
	public DrawFlow(int id, int idOrigen, int idDestino) {
		super();
		this.id = id;
		this.idOrigen = idOrigen;
		events=new ArrayList();
		this.idDestino = idDestino;
	}
	public DrawFlow(int id, int idOrigen, int idDestino, List events) {
		super();
		this.id = id;
		this.idOrigen = idOrigen;
		this.idDestino = idDestino;
		this.events= events;
	}
	
	public DrawFlow() {
		super();
		events=new ArrayList();
		// TODO Auto-generated constructor stub
	}


}
