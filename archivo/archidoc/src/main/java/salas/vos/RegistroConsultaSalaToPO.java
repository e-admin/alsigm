package salas.vos;

import org.apache.commons.collections.Transformer;

import salas.model.GestionSalasConsultaBI;

import common.bi.GestionArchivosBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * Transforma value objects en objetos para presentacion
 * 
 */
public class RegistroConsultaSalaToPO implements Transformer {

	private GestionArchivosBI archivosBI = null;
	private GestionSalasConsultaBI salasConsultaBI = null;

	/**
	 * Constructor
	 * 
	 * @param archivosBI
	 *            Servicio de archivos
	 * @param salasConsultaBI
	 *            Servicio de salas
	 */
	RegistroConsultaSalaToPO(GestionArchivosBI archivosBI,
			GestionSalasConsultaBI salasConsultaBI) {
		this.archivosBI = archivosBI;
		this.salasConsultaBI = salasConsultaBI;
	}

	/**
	 * Transforma el VO en un PO {@inheritDoc}
	 * 
	 * @see org.apache.commons.collections.Transformer#transform(java.lang.Object)
	 */
	public Object transform(Object vo) {
		RegistroConsultaSalaPO po = null;
		if (vo != null) {
			po = new RegistroConsultaSalaPO(archivosBI, salasConsultaBI);
			POUtils.copyVOProperties(po, vo);
		}
		return po;
	}

	/**
	 * Permite obtener una instancia del transformer
	 * 
	 * @param services
	 *            Repositorio de servicios
	 * @return transformer {@link RegistroConsultaSalaToPO}
	 */
	public static RegistroConsultaSalaToPO getInstance(
			ServiceRepository services) {
		return new RegistroConsultaSalaToPO(services.lookupGestionArchivosBI(),
				services.lookupGestionSalasBI());
	}
}