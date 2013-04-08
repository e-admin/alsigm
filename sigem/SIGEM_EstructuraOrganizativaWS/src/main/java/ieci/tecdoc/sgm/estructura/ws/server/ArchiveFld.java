
package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;


public class ArchiveFld   extends RetornoServicio{


	private int     m_id;
	private String  m_name;
	private int     m_type;
	private int     m_len;
	private boolean m_isNullable;
	private String  m_colName;
	private boolean m_isDoc;
	private boolean m_isMult;
	private String  m_remarks;

	public ArchiveFld(){
		m_id         = Integer.MAX_VALUE - 1;
		m_name       = null;     
		m_type       = 0;
		m_len        = 0;
		m_isNullable = true;   
		m_colName    = null;
		m_isDoc      = false;
		m_isMult     = false;  
		m_remarks    = null;
	}

	/**
	 * Construye un objeto de tipo ArchiveFld.
	 * 
	 * @param id  Identificador del campo (solo si se ha recuperado de base de datos).
	 * @param name  Nombre del campo.
	 * @param type  Tipo de campo en base de dato 
	 * @param len   Longitud del campo, válido si es texto ó texto largo.
	 * @param isNullable Indica si el campo puede ser nulo (true /false).
	 * @param colName    Nombre de columna en base de datos.
	 * @param isDoc      Indica si el campo es documental.
	 * @param isMult     Indica si el campo es multivalor.
	 * @param remarks    Descripción del campo.
	 */
	protected ArchiveFld(int id, String name, int type, int len, boolean isNullable,
			String colName, boolean isDoc, boolean isMult, String remarks)
	{
		m_id         = id;
		m_name       = name;
		m_type       = type;
		m_len        = len;
		m_isNullable = isNullable;
		m_colName    = colName;
		m_isDoc      = isDoc;
		m_isMult     = isMult;
		m_remarks    = remarks;
	}

	/**
	 * Obtiene el identificador del campo.
	 * 
	 * @return El identificador.
	 */
	public int getId(){
		return m_id;
	}

	/**
	 * Obtiene el nombre del campo.
	 * 
	 * @return El nombre.
	 */
	public String getName(){
		return m_name;
	}

	/**
	 * Establece el nombre del campo.
	 * 
	 * @param name  Nombre del campo.
	 */
	public void setName(String name){
		m_name = name;
	}

	/**
	 * Obtiene la descripción del campo.
	 * 
	 * @return La descripción.
	 */
	public String getRemarks(){
		return m_remarks;
	}

	/**
	 * Establece la descripción del campo
	 * 
	 * @param remarks descripción
	 */
	public void setRemarks(String remarks){
		m_remarks = remarks;
	}

	/**
	 * Obtiene el nombre de la columna en base de datos
	 * referente al campo.
	 * 
	 * @return Nombre de columna.
	 */
	public String getColName(){
		return m_colName;
	}

	/**
	 * Obtiene el tipo de campo en base de datos.
	 * 
	 * @return Tipo de campo
	 */
	public int getType(){
		return m_type;
	}

	/**
	 * Establece el tipo de campo en base de datos
	 *  
	 * @param type Tipo de campo
	 */
	public void setType(int type){
		m_type = type;
	}

	/**
	 * Obtiene la longitud del campo.
	 * 
	 * @return La longitud.
	 */
	public int getLen(){
		return m_len;
	}

	/**
	 * Establece la longitud del campo.
	 * 
	 * @param len longitud
	 */
	public void setLen(int len){
		m_len = len;
	}

	/**
	 * Obtiene la nulidad del campo.
	 * 
	 * @return true - si puede ser null, false - si no puede ser null
	 */
	public boolean isNullable(){
		return m_isNullable;
	}

	/**
	 * Establece o no la obligatoriedad del campo
	 * 
	 * @param nullable true / false
	 */
	public void setNullable(boolean nullable){
		m_isNullable = nullable;
	}

	/**
	 * Obtiene si el campo tiene búsqueda documental.
	 * 
	 * @return true - tiene búsqueda documental, false - no tiene
	 */
	public boolean isDoc(){
		return m_isDoc;
	}

	/**
	 * Establece o no la búsqueda documental de un campo.
	 * 
	 * @param isDoc  true / false
	 */
	public void setDoc(boolean isDoc){
		m_isDoc = isDoc;
	}

	/**
	 * obtiene si el campo es multivalor.
	 * 
	 * @return true - es multivalor, false - no es multivalor
	 */
	public boolean isMult(){
		return m_isMult;
	}

	/**
	 * Establece o no si el campo es multivalor
	 * 
	 * @param mult true / false
	 */
	public void setMult(boolean mult){
		m_isMult = mult;
	}


}

