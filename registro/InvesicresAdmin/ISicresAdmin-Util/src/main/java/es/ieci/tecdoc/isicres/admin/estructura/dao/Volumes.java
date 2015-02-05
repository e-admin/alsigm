package es.ieci.tecdoc.isicres.admin.estructura.dao;


/**
 * Maneja la lista de volumenes de invesDoc.
 */
public interface Volumes
{

   /**
    * Devuelve el número de volúmenes.
    *
    * @return El número de volúmenes mencionado.
    */

   public int count();

   /**
    * Carga la lista de volúmenes de un repositorio con su información básica.
    *
    * @throws Exception Si se produce algún error en la carga de los volúmenes.
    */

   public void loadByRep(int repId, String entidad) throws Exception;

   /**
    * Carga la lista de volúmenes de una lista de volúmenes con su información básica.
    *
    * @throws Exception Si se produce algún error en la carga de los volúmenes.
    */

   public void loadByVolumeList(int listId, String entidad) throws Exception;

   /**
    * Devuelve un volumen de la lista.
    *
    * @param index Indice del volumen que se desea recuperar.
    *
    * @return El volumen mencionado.
    */

   public Volume getVolume(int index) throws Exception;

   /**
    * Obtiene la información de la lista de volúmenes en formato XML.
    *
    * @return La lista de volúmenes mencionada.
    */

   public String toXML();

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *
    * @return La representación mencionada.
    */

	public String toString();

	/**
	 * Añade un volumen a la lista.
	 *
	 * @param volume Volumen que se quiere añadir.
	 */
	public void add(Volume volume);

	/**
    * Elimina un volumen de la lista.
    *
    * @param volId Identificador del volumen a eliminar.
    */
   public void remove(int volId) throws Exception;


}
