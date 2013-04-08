package es.ieci.tecdoc.isicres.admin.estructura.beans;

import java.io.Serializable;

public class GrupoLdap implements Serializable{

	public static final int IUSER_GROUP_TYPE_STANDARD = 0;
	public static final int IUSER_GROUP_TYPE_SICRES    = 1;

   private int _id;
   private String _fullname;
   private String _guid;
   private int _type;
   
   public GrupoLdap() {
	   
   }


	public int get_id() {
		return _id;
	}
	
	public void set_id(int _id) {
		this._id = _id;
	}
	
	public String get_fullname() {
		return _fullname;
	}
	
	public void set_fullname(String _fullname) {
		this._fullname = _fullname;
	}
	
	
	public int get_type() {
		return _type;
	}
	
	public void set_type(int _type) {
		this._type = _type;
	}
	
	public String get_guid() {
		return _guid;
	}
	
	public void set_guid(String _guid) {
		this._guid = _guid;
	}
   
}
