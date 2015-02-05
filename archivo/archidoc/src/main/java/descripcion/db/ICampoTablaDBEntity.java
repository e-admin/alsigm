package descripcion.db;

import java.util.List;

import common.db.IDBEntityKeyValue;

import descripcion.vos.CampoTablaVO;

/**
 * Enitdad: <b>ADCAMPOTBL</b>
 * 
 * @author IECISA
 * 
 */
public interface ICampoTablaDBEntity extends IDBEntityKeyValue {

	public CampoTablaVO getCampoTabla(String id);

	/**
	 * Obtiene un CampoDatoVO perteneciente a la etiqueta pasada por parámetro
	 * 
	 * @param etiquetaXml
	 * @return CampoDatoVO
	 */
	public CampoTablaVO getCampoTablaByEtiquetaXml(String etiquetaXml);

	public CampoTablaVO getCampoTablaByEtiquetaFilaXml(String etiquetaFilaXml);

	public CampoTablaVO getCampoTablaPorNombre(String nombre);

	/**
	 * Obtiene la lista de CamposTablaVO
	 * 
	 * @return una lista de CamposTablaVO
	 */
	public List getCamposTabla();

	/**
	 * Obtiene la lista de CamposTablaVO pertenecientes a los id's de areas
	 * pasados por parámetro
	 * 
	 * @param String
	 *            [] idAreas
	 * @return una lista de CamposTablaVO
	 */
	public List getCamposTablaXArea(String[] idAreas);

	/**
	 * Inserta un nuevo CampoTablaVO.
	 * 
	 * @param CampoTablaVO
	 * @return CampoTablaVO
	 */
	public CampoTablaVO createCampoTabla(CampoTablaVO campoTablaVO);

	/**
	 * Actualiza un campoTablaVO y los CampoDatoVO que pertenezcan a la tabla.
	 * 
	 * @param CampoTablaVO
	 * @return CampoTablaVO
	 */
	public CampoTablaVO updateCampoTabla(CampoTablaVO campoTablaVO);

	/**
	 * Elimina el conjunto de campos indicado por parámetro
	 * 
	 * @param idsCamposTabla
	 *            Conjunto de identificadores de campos
	 */
	public void deleteCamposTabla(String[] idsCamposTabla);

	/**
	 * Elimina un campoTabla perteneciente al id pasado por parámetro
	 * 
	 * @param String
	 *            id
	 */
	public void deleteCampoTabla(String id);

	/**
	 * Obtener maximo orden de una tabla
	 * 
	 * @param idElementoCF
	 * @param idsCampos
	 * @return
	 */
	public int getMaxOrdenTablaDescripcion(String idElementoCF,
			String[] idsCampos);
}
