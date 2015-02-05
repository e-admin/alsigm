package es.ieci.tecdoc.isicres.admin.estructura.beans;

import java.io.Serializable;

public class Permiso implements Serializable{

   private int _id;
   private int _dest;
   private int _product;
   private int _perm;
   
   public Permiso() {

   }
   
   public int get_id() {
	   return _id;
   }
   public int get_dest() {
	   return _dest;
   }
   public int get_product() {
	   return _product;
   }
   public int get_perm() {
	   return _perm;
   }
   public void set_id(int _id) {
	   this._id = _id;
   }
   public void set_dest(int _dest) {
	   this._dest = _dest;
   }
   public void set_product(int _product) {
	   this._product = _product;
   }
   public void set_perm(int _perm) {
	   this._perm = _perm;
   }
	
}
