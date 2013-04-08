package descripcion.db;

import java.util.List;

import common.db.IDBEntityKeyValue;

import descripcion.model.TipoNorma;
import descripcion.vos.FichaVO;
import descripcion.vos.InfoBFichaVO;

/**
 * Interfaz de comportamiento para la entidad de acceso a la ficha de
 * descripción. <br/>
 * Entidad: <b>ADFICHA</b>
 */
public interface IFichaDBEntity extends IDBEntityKeyValue {
	/**
	 * Obtiene la información de una ficha.
	 * 
	 * @param id
	 *            Identificador de la ficha.
	 * @return Información de la ficha.
	 */
	public FichaVO getFicha(String id);

	/**
	 * Obtiene la información de una ficha por su nombre.
	 * 
	 * @param id
	 *            Identificador de la ficha.
	 * @return Información de la ficha.
	 */
	public FichaVO getFichaByName(String nombre);

	/**
	 * Obtiene la lista de fichas
	 * 
	 * @return una lista de fichas
	 */
	public List getFichas();

	/**
	 * Obtiene la información básica de una ficha.
	 * 
	 * @param id
	 *            Identificador de la ficha.
	 * @return Información básica de la ficha.
	 */
	public InfoBFichaVO getInfoBFicha(String id);

	/**
	 * Obtiene una lista de fichas de unos tipos de nivel determinados.
	 * 
	 * @param tiposNivel
	 *            Tipos de nivel.
	 * @return Lista de fichas.
	 */
	public List getFichasByTiposNivel(int[] tiposNivel);

	/**
	 * Obtiene una lista de fichas de unos tipos de nivel determinados.
	 * 
	 * @param niveles
	 *            Niveles
	 * @return Lista de fichas.
	 */
	public List getFichasByTiposNivelIdFichaPref(List niveles);

	/**
	 * Obtiene la lista de fichas de un tipo de norma.
	 * 
	 * @param tipoNorma
	 *            Tipo de norma ({@link TipoNorma}).
	 * @return Lista de fichas ({@link InfoBFichaVO}).
	 */
	public List getFichasByTipoNorma(int tipoNorma);

	/**
	 * Obtiene la lista de fichas de un tipo de norma y unos tipo de nivel
	 * determinados.
	 * 
	 * @param tipoNorma
	 *            Tipo de norma ({@link TipoNorma}).
	 * @param tiposNivel
	 *            Tipos de nivel.
	 * @return Lista de fichas ({@link InfoBFichaVO}).
	 */
	public List getFichasByTipoNormaYNiveles(int tipoNorma, int[] tiposNivel);

	/**
	 * Inserta una nueva ficha.
	 * 
	 * @param ficha
	 * @return FichaVO
	 */
	public FichaVO createFicha(FichaVO ficha);

	/**
	 * Elimina las fichas correspondientes a los ids pasados por parametro
	 * 
	 * @param String
	 *            [] idsFichas
	 */
	public void deleteFichas(String[] idsFichas);

	/**
	 * Actualiza la ficha pasada por parametro.
	 * 
	 * @param ficha
	 * @return FichaVO
	 */
	public FichaVO updateFicha(FichaVO fichaVO);

	/**
	 * Comprueba en todas las tablas de la base de datos que utilizan la ficha
	 * si hay elementos que usan la ficha con id pasado como parametro.
	 * 
	 * @param idsFichas
	 * @return
	 */
	public int countElementosUsoFicha(String[] idsFichas);
}