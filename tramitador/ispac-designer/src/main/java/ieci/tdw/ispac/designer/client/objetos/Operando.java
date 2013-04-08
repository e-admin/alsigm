package ieci.tdw.ispac.designer.client.objetos;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Operando implements IsSerializable {
	
	//Identificador de la entidad, tenemos idpcd y en la tabla
	//sapc_p_entidades relacionamos el proc con la entidad
	//y para sacar el literal estaria en spac_ct
	//entidades
	//int idEntidad;
	

	
	//Como no sabemos a priori de que tipo va a ser este objeto
	//Date , int , String, va en funcion del operador con el que trabaje
	//Sera String , todo obje lo podemos convertir en String
	String valor;
	
	
	//Para optimizar, no tener que consultar cada vez que nos editen la condicion
	//almacenamos el literal de la entidad y del campo
	String nombreEntidad;
	String nombreCampo;
	public Operando() {
		super();
		//idEntidad=-1;
	
	
		// TODO Auto-generated constructor stub
	}
	public Operando(String valor) {
		super();
		this.valor = valor;
		//this.idEntidad=-1;
	
	}
	public Operando( String nombreEntidad,
			String nombreCampo) {
		super();
	//	this.idEntidad = idEntidad;
		this.nombreEntidad = nombreEntidad;
		this.nombreCampo = nombreCampo;
		this.valor=null;
	}
	public Operando( String valor,
			String nombreEntidad, String nombreCampo) {
		super();
		//this.idEntidad = idEntidad;
		this.valor = valor;
		this.nombreEntidad = nombreEntidad;
		this.nombreCampo = nombreCampo;
	}
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	public String getNombreCampo() {
		return nombreCampo;
	}
	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

}
