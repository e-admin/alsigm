package es.ieci.tecdoc.isicres.admin.estructura.dao;

public interface BasicDirectories
{

   /**
    * Devuelve el número de directorios.
    * 
    * @return El número de directorios mencionado.
    */
   
   public int count(); 

   /**
    * Devuelve un directorio de la lista.
    * 
    * @param index Indice del directorio que se desea recuperar.
    * 
    * @return El directorio mencionado.
    */
    
   public BasicDirectory get(int index); 
}
