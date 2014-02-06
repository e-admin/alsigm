package ieci.tecdoc.idoc.admin.api.archive;

public interface ArchiveMisc
{
   
   /**
    * Obtiene el título de las carpetas.
    * 
    * @return Título mencionado
    */
   public String getFdrName();
   
   /**
    * Establece el título de las carpetas
    * 
    * @param fdrName Título mencionado
    */
   public void setFdrName(String fdrName);
      
   /**
    * Obtiene el identificador de la lista de volúmenes.
    * 
    * @return Identificador mencionado
    */
   public int getVolListId();   
   
   
   /**
    * Establece el identificador de la lista de volúmenes.
    * 
    * @param volListId Identificador de la lista de volúmenes
    */
   public void setVolListId(int volListId);
   
   
   /**
    * Obtiene el tipo de lista de volúmenes.
    * 
    * @return Tipo uno de: ArchiveVolListType.STATIC
    * 						  ArchiveVolListType.NONE
    *                       
    */
   public int getVolListType();
   
   /**
    * Establece el tipo de lista de volúmenes.
    * 
    * @param volListType Tipo (Ej: ArchiveVolListType.STATIC) 
    * 
    */
   public void setVolListType(int volListType);
   
   
   /**
    * Establece los datos referenciados en los parámetros.
    * 
    * @param fdrName Título de las carpetas
    * @param volListId Lista de volúmenes
    * @param volListType Tipo de lista de volúmenes
    */
   public void setMisc(String fdrName, int volListId, int volListType);
   
   /**
    * Obtiene la información de miscelánea en formato XML.
    *
    * @return La información mencionada.
    */

   public String toXML(); 

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */

	public String toString();
}
