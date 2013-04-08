package deposito.db;

import java.util.List;

import common.db.IDBEntity;

import deposito.vos.FormatoHuecoVO;

/**
 * Entidad: <b>AGFORMATO</b>
 * 
 * @author IECISA
 * 
 */
public interface IFormatoDbEntity extends IDBEntity {

	public List loadFormatos();

	public List loadFormatosVigentes();

	public List loadFormatosRegulares();

	public List loadFormatosIrregulares();

	public FormatoHuecoVO loadFormato(String id);

	public FormatoHuecoVO insertFormato(FormatoHuecoVO formato);

	public void updateFormato(FormatoHuecoVO formato);

	FormatoHuecoVO getFormatoByName(String name);

	FormatoHuecoVO getFormatoById(String idFormato);

	/**
	 * @param formatosSeleccionados
	 */
	public void deleteFormatos(String[] formatosSeleccionados);
}