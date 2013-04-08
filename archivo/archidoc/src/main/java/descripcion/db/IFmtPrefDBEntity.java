package descripcion.db;

import common.db.IDBEntity;

import descripcion.vos.FmtPrefFichaVO;

/**
 * Interfaz de comportamiento para la entidad de acceso a la ficha preferente de
 * descripción. <br>
 * Entidad: <b>ADFMTPREF</b>
 */
public interface IFmtPrefDBEntity extends IDBEntity {
	/**
	 * Obtiene la definición del formato de ficha preferente del usuario.
	 * 
	 * @param idFicha
	 *            Identificador de la definición de la ficha.
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @param tipo
	 *            Tipo de formato.
	 * @return Definición del formato de una ficha preferente.
	 */
	public abstract FmtPrefFichaVO getFmtPrefFicha(String idFicha,
			String idUsuario, int tipo);

	/**
	 * Inserta un formato de ficha preferente para un usuario.
	 * 
	 * @param fmtPrefFicha
	 *            Formato de ficha preferente.
	 */
	public void insertFmtPrefFicha(FmtPrefFichaVO fmtPrefFicha);

	/**
	 * Modifica un formato de ficha preferente para un usuario.
	 * 
	 * @param fmtPrefFicha
	 *            Formato de ficha preferente.
	 */
	public void updateFmtPrefFicha(FmtPrefFichaVO fmtPrefFicha);

	/**
	 * Elimina los formatos preferentes de ficha que tengan como idFicha algunos
	 * de los pasados por parámetro
	 * 
	 * @param String
	 *            [] idsFichas
	 */
	public void deleteFmtPrefFichas(String[] idsFichas);
}