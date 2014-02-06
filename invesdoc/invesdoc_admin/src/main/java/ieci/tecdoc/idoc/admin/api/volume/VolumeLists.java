package ieci.tecdoc.idoc.admin.api.volume;

import ieci.tecdoc.idoc.admin.internal.VolumeListsImpl;

/**
 * Maneja la lista de la lista de volúmenes de invesDoc.
 */
public class VolumeLists
{
	/**
    * Construye un objeto de la clase.
    *  
    */

   public VolumeLists()
   {
   	_volumeListsImpl = new VolumeListsImpl();
   }

   /**
    * Devuelve el número de lista de volúmenes.
    * 
    * @return El número de lista de volúmenes mencionado.
    */

   public int count() 
   {
      return _volumeListsImpl.count();
   }

   /**
    * Carga la lista de volúmenes con su información básica.
    * 
    * @throws Exception Si se produce algún error en la carga de 
    * las listas de volúmenes.
    */

   public void load() throws Exception 
   {
   	_volumeListsImpl.load();
   }

   /**
    * Devuelve una lista de volúmenes de la lista.
    * 
    * @param index Indice de la lista de volúmenes que se desea recuperar.
    * 
    * @return La lista de volúmenes mencionada.
    */

   public VolumeList getVolumeList(int index) throws Exception 
   {
      return _volumeListsImpl.get(index);
   }
   
   /**
    * Obtiene la información de la lista de la lista de volúmenes en formato XML.
    *  
    * @return La lista mencionada.
    */

   public String toXML()
   {
      return _volumeListsImpl.toXML(true);
   }

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */

	public String toString()
   {
      return _volumeListsImpl.toString();
   }


   private VolumeListsImpl _volumeListsImpl;
}
