package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class UsuarioBasico extends RetornoServicio{

   private String _name;
   private int _id;

   public UsuarioBasico() {
	   
   }

	public UsuarioBasico(String _name, int _id) {
		this._name = _name;
		this._id = _id;
	}

	public int getId() {
		return _id;
	}

	public void setId(int _id) {
		this._id = _id;
	}

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}
   
}
