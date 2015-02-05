package descripcion.db;

import java.util.List;

import common.db.IDBEntityKeyValue;

import descripcion.model.TipoNorma;
import descripcion.vos.CampoDatoVO;

/**
 * Entidad: <b>ADCAMPODATO</b>
 * 
 * @author IECISA
 */
public interface ICampoDatoDBEntity extends IDBEntityKeyValue {

	/**
	 * Obtiene la lista de campos de una norma.
	 * 
	 * @param tipoNorma
	 *            Tipo de norma ({@link TipoNorma}).
	 * @return Lista de campos de la ficha ({@link CampoDatoVO}).
	 */
	public List getCamposByTipoNorma(int tipoNorma);

	/**
	 * Obtiene una lista de CampoDatoVO pertenecientes a los id de areas pasados
	 * por parámetro
	 * 
	 * @param idAreas
	 * @return List
	 */
	public List getCamposDatoXArea(String[] idAreas, boolean exlcuirCamposTabla);

	/**
	 * Obtiene un CampoDatoVO perteneciente al nombre pasado por parámetro
	 * 
	 * @param nombre
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDatoPorNombre(String nombre);

	/**
	 * Obtiene un CampoDatoVO perteneciente al id pasado por parámetro
	 * 
	 * @param id
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDato(String id);

	/**
	 * Obtiene un CampoDatoVO perteneciente a la etiqueta pasada por parámetro
	 * 
	 * @param etiquetaXml
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDatoByEtiquetaXml(String etiquetaXml);

	/**
	 * Obtiene un CampoDatoVO perteneciente a la etiqueta pasada por parámetro y
	 * su tabla
	 * 
	 * @param etiquetaXml
	 * @param idTabla
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDatoByEtiquetaXml(String etiquetaXml,
			String idTabla);

	/**
	 * Obtiene una lista de CampoDatoVO perteneciente al id de la tabla que se
	 * pasa por parámetro
	 * 
	 * @param idTabla
	 * @return List
	 */
	public List getCamposDatoXIdTabla(String idTabla);

	/**
	 * Obtiene la lista de CamposDatoVO que no pertenecen a ninguna tabla
	 * 
	 * @return una lista de CamposDatoVO
	 */
	public List getCamposDatoSinTabla();

	/**
	 * Obtiene una lista de CampoDatoVO
	 * 
	 * @return una lista de CampoDatoVO
	 */
	public List getCamposDato();

	/**
	 * Obtiene la lista de CamposDatoVO
	 * 
	 * @param String
	 *            idTabla
	 * @return una lista de CamposDatoVO
	 */
	public List getCamposDatoOrderByPosEnTbl(String idTabla);

	/**
	 * Obtiene un CamposDatoVO
	 * 
	 * @param String
	 *            idTabla
	 * @param int posicion
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDatoByPosEnTbl(String idTabla, int pos);

	/**
	 * Inserta un nuevo CampoDatoVO.
	 * 
	 * @param CampoDatoVO
	 * @return CampoDatoVO
	 */
	public CampoDatoVO createCampoDato(CampoDatoVO campoDatoVO);

	/**
	 * Elimina el conjunto de campos indicado por parámetro
	 * 
	 * @param idsCamposDato
	 *            Conjunto de identificadores de campos
	 */
	public void deleteCamposDato(String[] idsCamposDato);

	/**
	 * Actualiza el campo pasado por parametro.
	 * 
	 * @param CampoDatoVO
	 * @return CampoDatoVO
	 */
	public CampoDatoVO updateCampoDato(CampoDatoVO campoDatoVO);

}