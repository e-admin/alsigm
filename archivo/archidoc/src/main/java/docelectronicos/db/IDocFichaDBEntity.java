package docelectronicos.db;

import java.util.List;

import common.db.IDBEntity;

import docelectronicos.vos.FichaClfVO;
import docelectronicos.vos.InfoBFichaClfVO;

/**
 * Entidad: <b>ADOCFICHACLF</b>
 * 
 * @author IECISA
 * 
 */
public interface IDocFichaDBEntity extends IDBEntity {

	/**
	 * Obtiene la lista de fichas de clasificadores documentales.
	 * 
	 * @return Listas fichas de clasificadores documentales.
	 */
	public List getFichas();

	/**
	 * Obtiene la fichas de clasificadores documentales.
	 * 
	 * @param id
	 *            Identificador de la ficha.
	 * @return Ficha de clasificadores documentales.
	 */
	public FichaClfVO getFicha(String id);

	/**
	 * Obtiene la información básica de la ficha de clasificadores documentales.
	 * 
	 * @param id
	 *            Identificador de la ficha.
	 * @return Ficha de clasificadores documentales.
	 */
	public InfoBFichaClfVO getInfoBFicha(String id);

}