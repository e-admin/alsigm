package ieci.tecdoc.idoc.admin.api.archive;
/**
 * Proporciona toda la funcionalidad necesaria para manejar la actualización
 * de la información de un archivador. 
 */

import java.util.ArrayList;


public interface ArchiveUpdInfo
{

   /**
    * Establece el nombre del archivador.
    * 
    * @param name Nombre
    */
   public void setName(String name);
   
   /**
    * Obtiene el nombre del archivador.
    * 
    * @return Nombre
    */
   public String getName();
   
   /**
    * Establece la descripción del archivador.
    * 
    * @param remarks Descripción
    */
   public void setRemarks(String remarks);
   
   /**
    * Obtiene la descripción del archivador.
    * 
    * @return Descripción
    */
   public String getRemarks();
   
   /**
    * Establece el administrador del archivador.
    * 
    * @param adminUserId Identificador
    */
   public void setAdminUserId(int adminUserId);
   
   /**
    * Obtiene el administrador del archivador.
    * 
    * @return Identificador
    */
   public int getAdminUserId();
   
   /**
    * Obtiene si hay búsqueda en contenido de fichero en el archivador.
    * 
    * @return true / false
    */
   public boolean isFtsInContents();
   
      
   /**
    * Establece la existencia o no de búsqueda en contenidoc de fichero en
    * el archivador.
    * 
    * @param ftsInContents true / false
    */
	public void setFtsInContents(boolean ftsInContents);
	
	/**
	 * Establece la estructura de la definición de campos debe contener
	 * la información de los antiguos y los nuevos.
	 * 
	 * @param fldsDef Estructura de campos.
	 * @see ArchiveFlds
	 */
   public void setFldsDef(ArchiveFlds fldsDef) throws Exception;
   
   /**
    * Obtiene la estructura de la definición de los campos del archivador.
    * 
    * @return Estructura de campos
    */
   public ArchiveFlds getFldsDef();
   
   /**
    * Establece la estructura de la definición de índices del archivador, debe
    * contener la información de loa antiguos y de los nuevos.
    * 
    * @param idxsDef Estructura de índices
    */
   public void setIdxsDef(ArchiveIdxs idxsDef);
   
   
   /**
    * Obtiene la estructura de la definición de índices.
    * 
    * @return Estructura de índices
    */
   public ArchiveIdxs getIdxsDef();
   
   /**
    * Establece la información del título de carpetas y lista de volúmenes 
    * asociada, si no se cambia debe contener la información antigua.
    * 
    * @param miscDef Información mencionada
    */
   public void setMiscDef(ArchiveMisc miscDef);
   
   /**
    * Obtiene la información del título de carpetas y lista de volúmenes 
    * asociada.
    * 
    * @return Información
    */
   public ArchiveMisc getMiscDef();
   
   /**
    * Obtiene los identificadores de los índices eliminados.
    * 
    * @return Lita con los identificadores
    */
   public ArrayList getDeleteIdxs();
   
   /**
    * Obtiene los identificadores de los índices añadidos.
    * 
    * @return Lista con los identificadores
    */
   public ArrayList getNewIdx();
   
   /**
    * Obtiene los identificadores de los campos eliminados.
    * 
    * @return Lista con los identificadores
    */
   public ArrayList getDeleteFlds();
   
   /**
    * Obtiene los identificadores de los campos añadidos.
    * 
    * @return Lista con los identificadores
    */
   public ArrayList getNewFlds();
   
   
   /**
    * Establece un array de campos creador, otro de campos eliminados,
    * Si se ha modificado la definición de campos referente a tipo, longitud,
    * documental,obligatorio ó multivalor el parámetro isModifyDefFlds debería se
    * true.
    * 
    * @param newFldsId Lista con identificadores de campos nuevos
    * @param delFldsId Lista con identificadores de campos eliminados
    * @param isModifyDefFlds true / false
    */
   public void setUpdateFlds(ArrayList newFldsId, ArrayList delFldsId, boolean isModifyDefFlds);
   
   
   /**
    * Establece un array de índices creados, otro de índices eliminados.
    * 
    * @param newIdxsId Lista con los identificadores de los índices nuevos.
    * @param delIdxsId Lista con los identificadores de los índices eliminados.
    */
   public void setUpdateIdxs(ArrayList newIdxsId, ArrayList delIdxsId);
   
   /**
    * Establece si se ha modificado ó no la definición de los campos referente a
    * tipo, longitud, documental, obligatorio ó multivalor, si alguno de estos valores
    * se ha modificador el parámetro debería ser true.
    * 
    * @param modify - true / false
    */
   public void ModifyDefFlds(boolean modify);
   
   
   /**
    * Obtiene si se ha modificado la definición de los campos.
    * 
    * @return true / false
    */
   public boolean isModifyDefFlds();
   
   /**
    * Establece si se ha modificado ó no la lista de volúmenes asociada
    * al archivador.
    * 
    * @param modify true / false
    */
   public void ModifyListVols(boolean modify);
   
   /**
    * Obtiene si la lista de vólumenes ha sido modificada.
    * 
    * @return true / false 
    */
   public boolean isModifyListVols();
   
}
