package solicitudes.prestamos.vos;

import java.util.Locale;

import org.apache.commons.collections.Transformer;

import common.bi.GestionPrestamosBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * Transforma value objects de prestamos en objetos para presentacion
 * 
 */
public class PrestamoToPO implements Transformer {

	GestionSistemaBI sistemaBI = null;
	GestionPrestamosBI prestamosBI = null;
	Locale locale = null;

	PrestamoToPO(Locale locale, GestionSistemaBI sistemaBI,
			GestionPrestamosBI prestamosBI) {

		this.sistemaBI = sistemaBI;
		this.prestamosBI = prestamosBI;
		this.locale = locale;
	}

	public Object transform(Object vo) {
		PrestamoPO po = new PrestamoPO(locale, sistemaBI, prestamosBI);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static PrestamoToPO getInstance(Locale locale,
			ServiceRepository services) {
		return new PrestamoToPO(locale, services.lookupGestionSistemaBI(),
				services.lookupGestionPrestamosBI());
	}

}
