package es.ieci.tecdoc.isicres.admin.estructura.beans;

public class UsuarioBasico {

   private String _name;
   private int _id;

   public UsuarioBasico() {
	   
   }

	public UsuarioBasico(String _name, int _id) {
		this._name = _name;
		this._id = _id;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}
   
	 
}
