package ieci.tecdoc.sgm.core.services.permisos_backoffice;

import java.util.ArrayList;

public class DatosUsuario {

	private int _id;
	private String _nombre;
	private String[] _idAplicaciones;
	private String _nombreReal;
	private String _apellidos;
	
	public DatosUsuario() {
	
	}

	public DatosUsuario(int _id, String[] idAplicaciones, String _nombre) {
		super();
		this._id = _id;
		this._idAplicaciones = idAplicaciones;
		this._nombre = _nombre;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String[] get_idAplicaciones() {
		return _idAplicaciones;
	}

	public void set_idAplicaciones(String[] aplicaciones) {
		_idAplicaciones = aplicaciones;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}

	public String get_apellidos() {
		return _apellidos;
	}

	public void set_apellidos(String _apellidos) {
		this._apellidos = _apellidos;
	}

	public String get_nombreReal() {
		return _nombreReal;
	}

	public void set_nombreReal(String real) {
		_nombreReal = real;
	}
	
	
	
	

	
}
