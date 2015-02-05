package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.util.Date;

public class Grupo extends RetornoServicio{

   private int _userConnected;
   private int _id;
   private String _name;
   private int _managerId;
   private int _type;
   private String _description;
   private int _creatorId;
   private Date _creationDate;
   private int _updaterId;
   private Date _updateDate;
   //private GenericPermsImpl _permsImpl;
   private PermisosGenericos _permsImpl;
   private Usuarios _users;
   private UsuariosBasicos _adminUsers;
   
   public Grupo() {
	   
   }

	public UsuariosBasicos getAdminUsers() {
		return _adminUsers;
	}
	
	public void setAdminUsers(UsuariosBasicos users) {
		_adminUsers = users;
	}
	
	public Date getCreationDate() {
		return _creationDate;
	}
	
	public void setCreationDate(Date date) {
		_creationDate = date;
	}
	
	public int getCreatorId() {
		return _creatorId;
	}
	
	public void setCreatorId(int id) {
		_creatorId = id;
	}
	
	public String getDescription() {
		return _description;
	}
	
	public void setDescription(String _description) {
		this._description = _description;
	}
	
	public int getId() {
		return _id;
	}
	
	public void setId(int _id) {
		this._id = _id;
	}
	
	public int getManagerId() {
		return _managerId;
	}
	
	public void setManagerId(int id) {
		_managerId = id;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String _name) {
		this._name = _name;
	}
	
	public PermisosGenericos getPerms() {
		return _permsImpl;
	}
	
	public void setPerms(PermisosGenericos impl) {
		_permsImpl = impl;
	}
	
	public int getType() {
		return _type;
	}
	
	public void setType(int _type) {
		this._type = _type;
	}
	
	public Date getUpdateDate() {
		return _updateDate;
	}
	
	public void setUpdateDate(Date date) {
		_updateDate = date;
	}
	
	public int getUpdaterId() {
		return _updaterId;
	}
	
	public void setUpdaterId(int id) {
		_updaterId = id;
	}
	
	public int getUserConnected() {
		return _userConnected;
	}
	
	public void setUserConnected(int connected) {
		_userConnected = connected;
	}
	
	public Usuarios getUsers() {
		return _users;
	}
	
	public void setUsers(Usuarios _users) {
		this._users = _users;
	}
		
}
