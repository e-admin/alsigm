package docvitales.actions;

import org.apache.commons.collections.Transformer;

import common.view.POUtils;

public class DocumentoVitalToPO implements Transformer {

	/**
	 * Constructor.
	 */
	protected DocumentoVitalToPO() {
	}

	/**
	 * Crea una instancia de la clase.
	 * 
	 * @return Instancia de la clase.
	 */
	public static DocumentoVitalToPO getInstance() {
		return new DocumentoVitalToPO();
	}

	/**
	 * Transforma un objeto en otro.
	 * 
	 * @param vo
	 *            Objeto origen.
	 * @return Objeto transformado.
	 */
	public Object transform(Object vo) {
		DocumentoVitalPO po = new DocumentoVitalPO();
		POUtils.copyVOProperties(po, vo);
		return po;
	}
}