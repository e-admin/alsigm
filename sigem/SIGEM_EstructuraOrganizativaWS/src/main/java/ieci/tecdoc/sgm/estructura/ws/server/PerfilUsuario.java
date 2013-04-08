package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class PerfilUsuario extends RetornoServicio{

   private int _userId;  
   private int _product;
   private int _profile;

   public PerfilUsuario() {

   }
   
   public int getUserId() {
	   return _userId;
   }
   
   public int getProduct() {
	   return _product;
   }
   
   public int getProfile() {
	   return _profile;
   }
   
   public void setUserId(int id) {
	   _userId = id;
   }
   
   public void setProduct(int _product) {
	   this._product = _product;
   }
   
   public void setProfile(int _profile) {
	   this._profile = _profile;
   }
   
}
