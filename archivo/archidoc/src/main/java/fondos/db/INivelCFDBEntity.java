/*
 * Created on 25-nov-2005
 *
 */
package fondos.db;

import java.util.List;

import common.db.IDBEntity;

import fondos.vos.INivelCFVO;
import fondos.vos.JerarquiaNivelCFVO;

/**
 * Entidad: <b>ASGFNIVELCF</b>
 * 
 * @author IECISA
 * 
 */
public interface INivelCFDBEntity extends IDBEntity {
	public abstract List getNivelesCF(String idNivelPadre);

	public abstract List getNivelesNoSerieCF(String idNivelPadre);

	public abstract List getNivelesCF(int tipoNivel, String idNivelPadre);

	public abstract INivelCFVO getNivelCF(String idNivel);

	public abstract List getNivelesPadre(String idNivel, int tipoNivel);

	/**
	 * Obtiene la lista de niveles de descripción (excepto los clasificadores de
	 * fondos).
	 * 
	 * @return Lista de niveles de descripción.
	 */
	public abstract List getNivelesCF();

	/**
	 * Obtiene la lista de niveles de descripción (incluidos los clasificadores
	 * de fondos).
	 * 
	 * @return Lista de niveles de descripción.
	 */
	public abstract List getAllNivelesCF();

	public abstract INivelCFVO getNivelCF(int tipo, int subtipo);

	public abstract INivelCFVO getNivelCFById(String idNivel);

	/**
	 * Obtiene el nivelCF por su nombre
	 * 
	 * @param nombreNivel
	 * @return
	 */
	public INivelCFVO getNivelCFByNameAndTipo(String nombreNivel, int tipoNivel);

	/**
	 * Obtiene el nivelCF por su nombre
	 * 
	 * @param nombreNivel
	 * @return
	 */
	public INivelCFVO getNivelCFByName(String nombreNivel);

	public abstract INivelCFVO insertNivel(INivelCFVO nivelCF);

	public abstract INivelCFVO updateNivel(INivelCFVO nivelCF);

	public abstract void deleteNivelCF(INivelCFVO nivelCF);

	public abstract JerarquiaNivelCFVO insertJerarquiaNivelCF(
			JerarquiaNivelCFVO jerarquiaNivelCF);

	public abstract void deleteJerarquiaNivelCF(
			JerarquiaNivelCFVO jerarquiaNivelCF);

	public abstract boolean isNivelInicial(String idNivel);

	/**
	 * Comprueba si el nivel es hijo de otro nivel excepto cuando se trata del
	 * NIVEL_RAIZ
	 * 
	 * @param idNivelHijo
	 * @return
	 */
	public abstract boolean isNivelHijoNoRaiz(String idNivelHijo);

	/**
	 * Obtiene los posibles niveles que pueden ser agregados a otro nivel
	 * Comprobando que sean tipos de nivel hijo posibles, que ya no esten
	 * agregados y ademas que en la estructura jerarquica no esten como
	 * ID_NIVEL_RAIZ
	 * 
	 * @param tiposHijo
	 * @param nivelesHijo
	 * @return
	 */
	public abstract List getNivelesCFByTipo(int[] tiposHijo, List nivelesHijo);

	/**
	 * Obtiene los niveles en funcion del tipo y los ids de nivel
	 * 
	 * @param tiposNivel
	 * @param niveles
	 * @return
	 */
	public List getNivelesCFByTipo(int[] tiposNivel, String[] niveles);
}