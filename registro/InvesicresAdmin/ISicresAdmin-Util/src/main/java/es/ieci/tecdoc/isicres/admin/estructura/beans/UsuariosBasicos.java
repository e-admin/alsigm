package es.ieci.tecdoc.isicres.admin.estructura.beans;

import java.util.ArrayList;

public class UsuariosBasicos {

	   private ArrayList _list;	
	
	   public UsuariosBasicos()
	   {
	      _list = new ArrayList();
	   }

	   public int count() 
	   {
	      return _list.size();
	   }
	    
	   public UsuarioBasico get(int index) 
	   {
	      return (UsuarioBasico)_list.get(index);
	   }

	   public void add(UsuarioBasico usuarioBasico) 
	   {
	      _list.add(usuarioBasico);
	   }
	
}
