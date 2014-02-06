package ieci.tecdoc.idoc.admin.api.volume;

import ieci.tecdoc.idoc.admin.internal.VolumeImpl;
import ieci.tecdoc.idoc.admin.internal.VolumesImpl;
import ieci.tecdoc.core.db.DbConnectionConfig;

/**
 * Maneja la lista de volumenes de invesDoc.
 */
public class Volumes
{
	/**
    * Construye un objeto de la clase.
    *  
    */

   public Volumes()
   {
   	_volumesImpl = new VolumesImpl();
   }
	
	/**
    * Establece la configuración de la conexión de base de datos
    * 
    * @param dbConnConfig Configuración de la conexión de base de datos
    * @throws Exception
    */
	 
   public void setConnectionConfig(DbConnectionConfig dbConnConfig) throws Exception
   {
	  _volumesImpl.setConnectionConfig(dbConnConfig); 
   }
   
   /**
    * Devuelve el número de volúmenes.
    * 
    * @return El número de volúmenes mencionado.
    */

   public int count() 
   {
      return _volumesImpl.count();
   }

   /**
    * Carga la lista de volúmenes de un repositorio con su información básica.
    * 
    * @throws Exception Si se produce algún error en la carga de los volúmenes.
    */

   public void loadByRep(int repId) throws Exception 
   {
   	_volumesImpl.loadByRep(repId);
   }
   
   /**
    * Carga la lista de volúmenes de una lista de volúmenes con su información básica.
    * 
    * @throws Exception Si se produce algún error en la carga de los volúmenes.
    */

   public void loadByVolumeList(int listId) throws Exception 
   {
   	_volumesImpl.loadByVolumeList(listId);
   }

   /**
    * Devuelve un volumen de la lista.
    * 
    * @param index Indice del volumen que se desea recuperar.
    * 
    * @return El volumen mencionado.
    */

   public Volume getVolume(int index) throws Exception 
   {
      return _volumesImpl.get(index);
   }
   
   /**
    * Obtiene la información de la lista de volúmenes en formato XML.
    *  
    * @return La lista de volúmenes mencionada.
    */

   public String toXML()
   {
      return _volumesImpl.toXML(true);
   }

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */

	public String toString()
   {
      return _volumesImpl.toString();
   }
	
	/**
	 * Añade un volumen a la lista.
	 * 
	 * @param volume Volumen que se quiere añadir.
	 */ 
	public void add(VolumeImpl volume)
	{
		_volumesImpl.add(volume);
	}
	
	/**
    * Elimina un volumen de la lista.
    * 
    * @param volId Identificador del volumen a eliminar.
    */
   public void remove(int volId) throws Exception
	{
   	_volumesImpl.remove(volId);
   }
   

   private VolumesImpl _volumesImpl;
}
