package es.ieci.tecdoc.isicres.admin.estructura.dao;



public interface BasicArchives
{

   /**
    * Devuelve el número de archivadores.
    * 
    * @return El número de archivadores mencionado.
    */
   
   public int count(); 

   /**
    * Devuelve un archivador de la lista.
    * 
    * @param index Indice del archivador que se desea recuperar.
    * 
    * @return El archivador mencionado.
    */
    
   public BasicArchive get(int index); 

}
