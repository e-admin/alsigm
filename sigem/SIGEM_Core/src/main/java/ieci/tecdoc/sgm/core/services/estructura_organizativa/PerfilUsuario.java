package ieci.tecdoc.sgm.core.services.estructura_organizativa;

import java.io.Serializable;

public class PerfilUsuario implements Serializable{

   public static final int SIN_PERMISOS = 0;
   public static final int OPERADOR_REGISTRO = 1;
   public static final int SUPERUSUARIO = 3;
	
   private int _userId;  
   private int _product;
   private int _profile;

   public PerfilUsuario() {
	   
}
   
   public int get_userId() {
	   return _userId;
   }
   public int get_product() {
	   return _product;
   }
   public int get_profile() {
	   return _profile;
   }
   
   public void set_userId(int id) {
	   _userId = id;
   }
   public void set_product(int _product) {
	   this._product = _product;
   }
   public void set_profile(int _profile) {
	   this._profile = _profile;
   }
   
}
