package ieci.tecdoc.idoc.admin.api.archive;

/**
 * Proporciona toda la funcionalidad necesaria para manejar campos de un archivador. 
 */

import ieci.tecdoc.idoc.admin.internal.ArchiveFldsImpl;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveFldType;

public interface ArchiveFlds
{   
   
   /**
    * Inicializa la estructura de campos
    *
    */
   public void clear();
   
   /**
    * Obtiene el número de campos.
    * 
    * @return Número mencionado
    */
   public int count();
   
   /**
    * Añade una definición de campo. 
    * 
    * @param fld Definición del campo.
    */
   public void addFld(ArchiveFld fld) throws Exception;
   
   /**
    * Añade una definición de campo.
    * (el cálculo del identificador es interno, nunca tiene que se
    * establecido)
    * 
    * @param name Nombre del campo
    * @param type Tipo del campo (ArchiveFldType)
    * 
    *   
    * @see ArchiveFldType
    * @param len  Longitud del campo en caso de texto y texto largo en
    * otro caso es = 0
    * @param isNullable true /false (posibilidad de tener valores nulos)
    * @param isDoc true / false (documental)
    * @param isMult true /false (multivalor)
    * @param remarks Descripción
    * @throws Exception - Errores
    */
   public void add(String name, int type, int len, boolean isNullable,
         			boolean isDoc, boolean isMult, String remarks) throws Exception;
   
   /**
    * Obtiene el identificador del campo a partir del nombre.
    * 
    * @param name Nombre del campo
    * @return Identificador del campo
    * @throws Exception Si no existe el campo
    */
   public int getFldIdByName(String name) throws Exception;
   
   /**
    * Obtiene la definición de un campo a partir del identificador.
    * 
    * @param fldId Identificador del campo
    * @return La definición mencionada.
    * @throws Exception Si el campo no exite
    */
   public ArchiveFld getFldDefById(int fldId) throws Exception;
   
   /**
    * Obtiene la definición de un campo a partir de un índice de la colección.
    * 
    * @param index Indice
    * @return La estructura mencionada.
    */
   public ArchiveFld get(int index);
   
   /**
    * Obtiene el número de campos relacionales en la colección.
    * 
    * @return Número de campos relacionales
    */
   public int getRelFldsCount();
   
   /**
    * Obtiene el número de campos documentales en la colección.
    * 
    * @return Número de campos documentales
    */
   public int getFtsFldsCount();
   
   /**
    * Obtiene una colección con las definiciones de los campos documentales.
    * 
    * @return Colección de campos documentales
    * @throws Exception Errores
    */
   public ArchiveFldsImpl getFtsFlds() throws Exception;
   
   /**
    * Obtiene el número de campos multivalores en la colección.
    * 
    * @return Número de campos multivalores
    */
   public int getMultFldsCount();
   
   /**
    * Obtiene la información de los campos en formato XML.
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
