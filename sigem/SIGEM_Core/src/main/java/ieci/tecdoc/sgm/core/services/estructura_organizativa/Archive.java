
package ieci.tecdoc.sgm.core.services.estructura_organizativa;

/**
 * Bean de archivador. 
 */



public class Archive {

	private ArchiveFlds archiveFlds;
	private ArchiveIdxs idxs;
	private ArchiveMisc misc;
	private String name;
	private String remarks;
	private int flags;
	private int parentId;	
	private int adminUserId;
	private int typeId;

	/**
	 * Establece información referente a la definición de campos
	 * del archivador,
	 *
	 *  @param fldsArch Estructura con la información mencionada.
	 */
	public void setFldsDef(ArchiveFlds fldsArch){
		archiveFlds = (ArchiveFlds)fldsArch;
	}

	/**
	 * Obtiene la información referente a la definición de campos.
	 * @return Definición de campos.
	 */
	public ArchiveFlds getFldsDef(){
		return archiveFlds;	   
	}

	/**
	 * Establece información referente a la definición de índices 
	 * del archivador.
	 * @param idxsArch Definición  de índices.
	 */
	public void setIdxsDef(ArchiveIdxs idxsArch){
		this.idxs = (ArchiveIdxs)idxsArch;
	}

	/**
	 * Obtiene la información referente a la definición de índices.
	 * @return Definición de índices.
	 */
	public ArchiveIdxs getIdxsDef()	{
		return (ArchiveIdxs)idxs;
	}

	/** 
	 * Establece la información de título de carpetas y lista de volúmenes
	 * @param miscArch La información
	 */
	public void setMiscDef(ArchiveMisc miscArch){
		this.misc = (ArchiveMisc)miscArch;
	}

	/**
	 * Obtiene la información de título de carpetas y lista de volúmenes
	 * @return La información mencionada. 
	 */
	public ArchiveMisc getMiscDef(){	   
		return (ArchiveMisc)misc;
	}

	/**
	 * Obtiene el nombre del archivador.
	 * @return Nombre del archivador.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Establece el nombre del archivador.
	 * @param name del archivador.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Obtiene la descripción del archivador.
	 * @return Descripción del archivador.
	 */
	public String getRemarks(){
		return remarks;
	}

	/**
	 * Establece la descripción del archivador.
	 * @param remarks del archivador.
	 */
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}

	/**
	 * Obtiene si existe búsqueda en contenido de fichero.
	 * @return true / false
	 */
	public boolean isFtsInContents(){
		boolean ftsInContents = false;

		if ((flags & ArchiveDefs.ARCH_FLAG_FTSINCONTENTS) != 0)
			ftsInContents = true;

		return(ftsInContents);

	}

	/**
	 * Establece la existencia de búsqueda en contenido de fichero.
	 * @param ftsInContents true / false
	 */
	public void setFtsInContents(boolean ftsInContents){
		if (ftsInContents)
			flags = flags | ArchiveDefs.ARCH_FLAG_FTSINCONTENTS;
		else
			flags = flags & ~ArchiveDefs.ARCH_FLAG_FTSINCONTENTS;
	}

	public int getParentId(){
		return (parentId);
	}

	/**
	 * Establece el identificador del padre del archivador.
	 * @param parentId Identificador del padre del archivador.
	 */
	public void setParentId (int parentId){
		this.parentId = parentId;
	}

	/**
	 * Establece el identificador del administrador del archivador.
	 * @param admId Identificador mencionado.
	 */
	public void setAdminUserId(int admId){
		adminUserId = admId;
	}

	/**
	 * Obtiene el identificador del administrador del archivador.
	 * @return Identificador mencionado.
	 */
	public int getAdminUserId(){
		return(adminUserId);
	}

	/**
	 * Obtiene el tipo de archivador
	 * @return
	 */
	public int getTypeId() {
		return typeId;
	}

	/**
	 * Asigna el tipo de archivador
	 * @param typeId
	 */
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

}
