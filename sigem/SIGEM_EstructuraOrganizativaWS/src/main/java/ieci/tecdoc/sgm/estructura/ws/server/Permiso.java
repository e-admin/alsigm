package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Permiso extends RetornoServicio{

   private int _id;
   private int _dest;
   private int _product;
   private int _perm;
   
   public Permiso() {

   }
   
   public int getId() {
	   return _id;
   }
   
   public int getDest() {
	   return _dest;
   }
   
   public int getProduct() {
	   return _product;
   }
   
   public int getPerm() {
	   return _perm;
   }
   
   public void setId(int _id) {
	   this._id = _id;
   }
   
   public void setDest(int _dest) {
	   this._dest = _dest;
   }
   
   public void setProduct(int _product) {
	   this._product = _product;
   }
   
   public void setPerm(int _perm) {
	   this._perm = _perm;
   }
	
}
