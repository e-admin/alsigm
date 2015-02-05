
package ieci.tecdoc.sbo.idoc.archive.base;

import java.io.Serializable;
import ieci.tecdoc.sbo.idoc.acs.AcsTokenArchive;

/**
 * Contiene todo tipo de informacion de un archivador. Es decir, encapsula
 * la información básica del archivador, como el nombre, el id, etc. la 
 * información sobre los campos del archivador, sobre la validación de 
 * dichos campos, etc. 
 * 
 * @author
 *
 */
public final class ArchiveToken implements Serializable
{
   
   /**
    * Informacion básica del archivador: Identificador, nombre, tipo 
    * de acceso, etc.
    */
   private ArchiveTokenArchHdr    m_archHdr;
   
   /**
    * Informacion sobre los campos que componen el archivador
    */
   private ArchiveTokenFlds       m_flds;
   
   /**
    * Informacion sobre los clasificadores del archivador
    */
   private ArchiveTokenDividers   m_dividers;
   
   /**
    * Informacion sobre los requisitos de validacion de los campos
    * del archivador
    */
   private ArchiveTokenValidation m_validations;
   
   /**
    * 
    */
   private AcsTokenArchive        m_acs;
   
   /** 
    * Informacion miscelanea del archivador
    */
   private ArchiveTokenMisc       m_misc;
      
   /**
    * Constructor
    *
    */
   public ArchiveToken()
   {
      m_archHdr      = null; 
      m_acs          = null;
      m_flds         = new ArchiveTokenFlds();
      m_dividers     = new ArchiveTokenDividers();
      m_validations  = new ArchiveTokenValidation();
      m_misc         = null;
   }
   
   /**
    * Crea el ArchiveTokenArchHdr del archivador, la informacion de cabecera
    * 
    * @param id Identificador de archivador
    * @param name Nombre del archivador
    * @param tblPrefix Prefijo de tabla del archivador
    * @param flags Flags del archivador
    * @param accessType Tipo de acceso del archivador
    * @param acsId
    */
   public void setArchHdr(int id, String name, String tblPrefix, int flags,
                          int accessType, int acsId)
   {
      m_archHdr = new ArchiveTokenArchHdr(id, name, tblPrefix, flags,
                                          accessType, acsId);
   }
   
   /**
    * Devuelve la informacion de cabecera del archivador
    * 
    * @return
    */
   public ArchiveTokenArchHdr getArchHdr()
   {
      return m_archHdr;
   }
   
   /**
    * Crea el ArchiveTokenMisc del archivador, la informacion miscelanea
    * 
    * @param fdrName 
    * @param volListType Tipo de volumen de almacenamiento de documentos
    * @param volListId Identificador de la lista de volumenes del archivador
    */
   public void setMisc(String fdrName, int volListType, int volListId)
   {
      m_misc = new ArchiveTokenMisc(fdrName, volListType, volListId);
   }
   
   /**
    * Devuelve la información miscelanea del archivador
    * 
    * @return
    */
   public ArchiveTokenMisc getMisc()
   {
      return m_misc;
   }
   
   /**
    * Establece el AcsTokenArchive del archivador
    * 
    * @param acsToken el AcsTokenArchive 
    */
   public void setAcs(AcsTokenArchive acsToken)
   {
      m_acs = acsToken;
   }
   
   /**
    * Devuelve el AcsTokenArchive del archivador
    * 
    * @return
    */
   public AcsTokenArchive getAcs()
   {
      return m_acs;
   }
   
   /**
    * Devuelve el Id del archivador 
    * 
    * @return
    */
   public int getId()
   {
      return m_archHdr.getId();
   }
   
   /**
    * Devuelve el prefijo de la tabla del archivador
    * 
    * @return
    */
   public String getTblPrefix()
   {
      return m_archHdr.getTblPrefix();
   }
   
   /**
    * Agrega un nuevo campo a la lista de campos del archivador
    * 
    * @param fld El campo a agregar en la lista de campos del archivador
    */
   public void addFld(ArchiveTokenFld fld)
   {
      m_flds.add(fld);
   }
   
   /**
    * Devuelve la información de los campos que componen el archivador
    * 
    * @return información de los campos del archivador
    */
   public ArchiveTokenFlds getFlds()
   {
      return m_flds;
   }
   
   /**
    * Delvuelve los campos relacionales del archivador
    * 
    * @return los campos relacionales del archivador
    * @throws Exception
    */
   public ArchiveTokenFlds getRelFlds()throws Exception
   {
      return m_flds.getRelFlds(); 
   }
   
   /**
    * Devuelve los campos multivalor del archivador
    * 
    * @return Los campos multivalor del archivador
    * @throws Exception
    */
   public ArchiveTokenFlds getMultFlds()throws Exception
   {
      return m_flds.getMultFlds(); 
   }
   
   /**
    * Devuelve los campos de texto largo del archivador
    * 
    * @return Los campos de texto largo del archivador
    * @throws Exception
    */
   public ArchiveTokenFlds getExtFlds()throws Exception
   {
      return m_flds.getExtFlds(); 
   }
   
   /**
    * Agrega un nuevo clasificador al archivador
    * 
    * @param div El nuevo clasificador
    */
   public void addDivider(ArchiveTokenDivider div)
   {
      m_dividers.add(div);
   }
   
   /**
    * Devuelve la informacion de los clasificadores del archivador
    * 
    * @return La informacion de los clasificadores del archivador
    */
   public ArchiveTokenDividers getDividers()
   {
      return m_dividers;
   }
   
   /**
    * Agrega una nueva regla de validación para un campo del archivador 
    * 
    * @param fldVld La nueva regla de validacion
    */
   public void addFldValidation(ArchiveTokenFldVld fldVld)
   {
      m_validations.addFldValidation(fldVld);
   }
   
   /**
    * Devuelve las reglas de validacion del archivador
    * 
    * @return Reglas de validacion del archivador
    */
   public ArchiveTokenValidation getArchValidation()
   {
      return m_validations;
   }
   
   /**
    * Devuelve <tt>true</tt> si el archivador tiene activo el flag de 
    * búsqueda documental (Full Text Search In Contents)
    * 
    * @return <tt>true</tt> si el archivador tiene activo el flag de 
    * búsqueda documental
    */
   public boolean isFTSInContent() 
   {
       return this.m_archHdr.getFlags() == ArchiveFlag.FTS_IN_CONTENTS;
   }
   
   /**
    * Devuelve una representacion en forma de String del objeto
    *  
    * @return representacion en forma de String 
    */
   public String toString() {
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("ArchiveToken[");
      buffer.append("m_archHdr = ").append(m_archHdr);
      buffer.append(", m_flds = ").append(m_flds);
      buffer.append(", m_dividers = ").append(m_dividers);
      buffer.append(", m_validations = ").append(m_validations);
      buffer.append(", m_acs = ").append(m_acs);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
