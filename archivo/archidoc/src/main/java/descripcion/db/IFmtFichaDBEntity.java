package descripcion.db;

import java.util.List;

import common.db.IDBEntity;

import descripcion.vos.FmtFichaVO;

/**
 * Interfaz de comportamiento para la entidad de acceso al formato de la ficha
 * de descripción. <br/>
 * Entidad: <b>ADFMTFICHA</b>
 */
public interface IFmtFichaDBEntity extends IDBEntity {
	/**
	 * Obtiene la definición del formato de una ficha.
	 *
	 * @param id
	 *            Identificador de la definición del formato de la ficha.
	 * @return Definición del formato de una ficha.
	 */
	public abstract FmtFichaVO getFmtFicha(String id);

	/**
	 * Obtiene los formatos de ficha asociados a una lista de acceso
	 *
	 * @param id
	 * @return
	 */
	public abstract List getFmtsFichasXListaAcceso(String idListaAcceso);

	/**
	 * Obtiene la lista de definiciones de formatos de fichas.
	 *
	 * @return Lista de definiciones de formatos de fichas.
	 */
	public abstract List getFmtsFicha(String idFicha);


	/**
	 * Obtiene la lista de definiciones de formatos de fichas.
	 *
	 * @return Lista de definiciones de formatos de fichas.
	 */
	public abstract List getFmtFichas(String idFicha, int tipo);


	/**
	 * Obtiene la lista de definiciones de formatos de fichas.
	 *
	 * @return Lista de definiciones de formatos de fichas.
	 */
	public List getFmtFichas();

	/**
	 * Inserta un formato de ficha ficha.
	 *
	 * @param FmtFichaVO
	 * @return FmtFichaVO
	 */
	public FmtFichaVO createFmtFicha(FmtFichaVO fmtFichaVO);

	/**
	 * Actualiza un formato de la ficha
	 *
	 * @param FmtFichaVO
	 * @return FmtFichaVO
	 */
	public FmtFichaVO updateFmtFicha(FmtFichaVO fmtFichaVO);

	/**
	 * Elimina los formatos de ficha que tengan como idFicha algunos de los
	 * pasados por parámetro
	 *
	 * @param String
	 *            [] idsFichas
	 */
	public void deleteFmtFichas(String[] idsFichas);

	/**
	 * Elimina los formatos de ficha que tengan como idFicha algunos de los
	 * pasados por parámetro
	 *
	 * @param String
	 *            [] idsFichas
	 */
	public void deleteFmtFichasByIds(String[] idsFmtFichas);

	/**
	 * Busca los formatos de ficha filtrados por nombre e idFicha
	 *
	 * @param nombre
	 *            Nombre Nombre de la ficha.
	 * @param idFicha
	 *            Identificador de la ficha
	 * @return Lista de {@link FmtFichaVO}
	 */
	public List findFmtFichas(String nombre, String idFicha);

}