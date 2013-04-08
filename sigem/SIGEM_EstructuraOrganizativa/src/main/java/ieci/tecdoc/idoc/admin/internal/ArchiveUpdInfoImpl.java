package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.idoc.admin.api.archive.ArchiveDefs;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveFld;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveFlds;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveIdxs;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveMisc;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveUpdInfo;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveVolListType;

import java.util.ArrayList;

public class ArchiveUpdInfoImpl implements ArchiveUpdInfo
{
   public ArchiveUpdInfoImpl()
   {
      init();
   }
   /**
    * Información necesario para la actualización del archivador
    * 
    * @param name   - nombre 
    * @param remarks - descripción 
    * @param adminUserId - Idenficador del usuario
    * @param ftsInContents - si se utiliza búsqueda en contenido de fichero
    * @param flds - definición de campos (antiguos y nuevos)
    * @param idxs - definición de índices (antiguos y nuevos)
    * @param misc - definición de título de carpeta y lista de volúmenes asociada
    */
   public ArchiveUpdInfoImpl(String name, String remarks, int adminUserId, boolean ftsInContents,
         					ArchiveFldsImpl flds, ArchiveIdxsImpl idxs, ArchiveMiscImpl misc)
   {
      
      init();
      
      _name        = name;
      _remarks     = remarks;
      _adminUserId = adminUserId;      
      //hay que copiar las estructuras
      copyFldsDef(flds);
      copyIdxsDef(idxs);
      copyMiscDef(misc);
      
      setFtsInContents(ftsInContents);
   }
   /**
    * Establece el nombre del archivador
    * 
    * @param name - nombre
    */
   public void setName(String name)
   {
      _name = name;
   }
   
   /**
    * Obtiene el nombre del archivador
    * 
    * @return - nombre
    */
   public String getName()
   {
      return _name;
   }
   
   /**
    * Establece la descripción del archivador
    * 
    * @param remarks - descripción
    */
   public void setRemarks(String remarks)
   {
      _remarks = remarks;
   }
   
   
   /**
    * Obtiene la descripción del archivador
    * 
    * @return - descripción
    */
   public String getRemarks()
   {
      return _remarks;
   }
   
  
   /**
    * Establece el administrador del archivador
    * 
    * @param adminUserId - identificador
    */
   public void setAdminUserId(int adminUserId)
   {
      _adminUserId = adminUserId;
   }
   
   /**
    * Obtiene el administrador del archivador
    * 
    * @return - identificador
    */
   public int getAdminUserId()
   {
      return _adminUserId;
   }
   
   public int getFlags()
   {
      return _flags;
   }
   
   /**
    * Obtiene si hay búsqueda en contenido de fichero en el archivador
    * 
    * @return true / false
    */
   public boolean isFtsInContents()
	{
	   boolean ftsInContents = false;
	   
	   if ((_flags & ArchiveDefs.ARCH_FLAG_FTSINCONTENTS) != 0)
	      ftsInContents = true;
	   
	   return(ftsInContents);
	      
	}
	
   /**
    * Establece la existencia o no de búsqueda en contenidoc de fichero en
    * el archivador
    * 
    * @param ftsInContents true / false
    */
	public void setFtsInContents(boolean ftsInContents)
	{
	   if (ftsInContents)
	      _flags = _flags | ArchiveDefs.ARCH_FLAG_FTSINCONTENTS;
	   else
	      _flags = _flags & ~ArchiveDefs.ARCH_FLAG_FTSINCONTENTS;
	}
   
	/**
	 * Establece la estructura de la definición de campos debe contener
	 * la información de los antiguos y los nuevos
	 * 
	 * @param fldsDef - estructura de campos
	 */
   public void setFldsDef(ArchiveFlds fldsDef) throws Exception
   {
      _flds.clear();
      copyFldsDef((ArchiveFldsImpl)fldsDef);
   }
   
   /**
    * Obtiene la estructura de la definición de los campos del archivador
    * 
    * @return - estructura de campos
    */
   public ArchiveFlds getFldsDef()
   {
      return _flds;
   }
   
   /**
    * Establece la estructura de la definición de índices del archivador, debe
    * contener la información de loa antiguos y de los nuevos
    * 
    * @param idxsDef - estructura de índices
    */
   public void setIdxsDef(ArchiveIdxs idxsDef)
   {
      _idxs.clear();
      copyIdxsDef((ArchiveIdxsImpl)idxsDef);
   }
   
   /**
    * Obtiene la estructura de la definición de índices
    * 
    * @return - estructura de índices
    */
   public ArchiveIdxs getIdxsDef()
   {
      return _idxs;
   }
   
   /**
    * Establece la información del título de carpetas y lista de volúmenes 
    * asociada, si no se cambia debe contener la información antigua
    * 
    * @param miscDef - información
    */
   public void setMiscDef(ArchiveMisc miscDef)
   {
      copyMiscDef((ArchiveMiscImpl)miscDef);
   }
   
   /**
    * Obtiene la información del título de carpetas y lista de volúmenes 
    * asociada
    * 
    * @return - información
    */
   public ArchiveMisc getMiscDef()
   {
      return _misc;
   }
   
   /**
    * Obtiene los identificadores de los índices eliminados
    * 
    * @return
    */
   public ArrayList getDeleteIdxs()
   {
      return _delIdxsId;
   }
   
   /**
    * Obtiene los identificadores de los índices añadidos
    * 
    * @return
    */
   public ArrayList getNewIdx()
   {
      return _newIdxsId;
   }
   
   /**
    * Obtiene los identificadores de los campos eliminados
    * 
    * @return
    */
   public ArrayList getDeleteFlds()
   {
      return _delFldsId;
   }
   
   /**
    * Obtiene los identificadores de los campos añadidos
    * 
    * @return
    */
   public ArrayList getNewFlds()
   {
      return _newFldsId;
   }
   
   /**
    * Establece un array de campos creador, otro de campos eliminados,
    * Si se ha modificado la definición de campos referente a tipo, longitud,
    * documental,obligatorio ó multivalor el parámetro isModifyDefFlds debería se
    * true.
    * 
    * @param newFldsId
    * @param delFldsId
    * @param isModifyDefFlds true / false
    */
   public void setUpdateFlds(ArrayList newFldsId, ArrayList delFldsId, boolean isModifyDefFlds)
   {
      _newFldsId.clear();
      _delFldsId.clear();
      
      if (newFldsId != null)
         _newFldsId = newFldsId;
      
      if (delFldsId != null)
         _delFldsId = delFldsId;
      
      _isModifyDefFlds = isModifyDefFlds;
   }
   
   /**
    * Establece un array de índices creados, otro de índices eliminados
    * 
    * @param newIdxsId
    * @param delIdxsId
    */
   public void setUpdateIdxs(ArrayList newIdxsId, ArrayList delIdxsId)
   {
      _newIdxsId.clear();
      _newIdxsId.clear();
      
      if (newIdxsId != null)
         _newIdxsId = newIdxsId;
      
      if (delIdxsId != null)
         _delIdxsId = delIdxsId;
   }
   
   /**
    * Establece si se ha modificado ó no la definición de los campos referente a
    * tipo, longitud, documental, obligatorio ó multivalor, si alguno de estos valores
    * se ha modificador el parámetro debería ser true
    * 
    * @param modify - true/false
    */
   public void ModifyDefFlds(boolean modify)
   {
      _isModifyDefFlds = modify;
   }
   
   /**
    * Obtiene si se ha modificado la definición de los campos
    * @return
    */
   public boolean isModifyDefFlds()
   {
      return _isModifyDefFlds;
   }
   
   /**
    * Establece si se ha modificado ó no la lista de volúmenes asociada
    * al archivador
    * 
    * @param modify - true / false
    */
   public void ModifyListVols(boolean modify)
   {
      _isModifyListVols = modify;
   }
   
   /**
    * Obtiene si la lista de vólumenes ha sido modificada
    * 
    * @return
    */
   public boolean isModifyListVols()
   {
      return _isModifyListVols;
   }
   
   /**
    * Realiza una copia de la estructura de campos del archivador
    * 
    * @param flds - campos
    */
   private void copyFldsDef(ArchiveFldsImpl flds) 
   {
      ArchiveFldImpl nfld = null;
      
      for (int i = 0; i < flds.count(); i++)
      {
         ArchiveFldImpl fld = (ArchiveFldImpl)flds.get(i);
         nfld = new ArchiveFldImpl(fld.getId(),fld.getName(),fld.getType(),
                                   fld.getLen(), fld.isNullable(),fld.getColName(),
                                   fld.isDoc(),fld.isMult(),fld.getRemarks());
         try
         {
            _flds.addFld((ArchiveFld)nfld);
         }
         catch(Exception e)
         {
            
         }
         
      }
   }
   
   /**
    * Realiza una copia de la estructura de índices
    * 
    * @param idxs - índices
    */
   private void copyIdxsDef(ArchiveIdxsImpl idxs)
   {
      ArchiveIdxImpl nidx = null;
      ArrayList      fldsId = null;
      
      for (int i = 0; i < idxs.count(); i++)
      {
         ArchiveIdxImpl idx = (ArchiveIdxImpl)idxs.get(i);
         ArrayList      ids = idx.getFldsId();
         
         fldsId = new ArrayList();
         for (int j = 0; j < ids.size(); j++)
         {
            Integer id = (Integer)ids.get(j);
            fldsId.add(new Integer(id.intValue()));
         }
         
         nidx = new ArchiveIdxImpl(idx.getId(),idx.getName(),idx.isUnique(), fldsId);
         try
         {
            _idxs.addIdx(nidx);
         }
         catch(Exception e)
         {
            
         }
         
      }
   }
   
   
   /**
    * Realiza una copia de la estructura con información extra del archivador
    * 
    * @param misc - información
    */
   private void copyMiscDef(ArchiveMiscImpl misc)
   {
      _misc.setMisc(misc.getFdrName(),misc.getVolListId(),misc.getVolListType());
   }
   
   private void init()
   {
      _name = "";      
      _remarks = "";          
      _flags = ArchiveDefs.ARCH_FLAG_NONE;
      _adminUserId = Defs.NULL_ID;
      _flds = new ArchiveFldsImpl();
      _idxs = new ArchiveIdxsImpl();
      _misc = new ArchiveMiscImpl("",ArchiveVolListType.NONE,Defs.NULL_ID);
      _newFldsId = new ArrayList();
      _delFldsId = new ArrayList();
      _newIdxsId = new ArrayList();
      _delIdxsId = new ArrayList();
      _isModifyDefFlds = false;   
      _isModifyListVols = false;
   }   
   
   private String          _remarks;
   private String          _name;
   private int             _flags;
   private int             _adminUserId;
   private ArchiveFldsImpl _flds;
   private ArchiveIdxsImpl _idxs;
   private ArchiveMiscImpl _misc;
   private ArrayList       _newFldsId;
   private ArrayList       _delFldsId;
   private boolean         _isModifyDefFlds; //tipo de campo ó longitud
   private boolean         _isModifyListVols; 
   private ArrayList       _delIdxsId;
   private ArrayList       _newIdxsId;
   
}

