package solicitudes.consultas.vos;

import java.util.Locale;

import org.apache.commons.collections.Transformer;

import salas.model.GestionSalasConsultaBI;

import common.bi.GestionConsultasBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

/**
 * Transforma value objects de consulta en objetos para presentacion
 * 
 */
public class ConsultaToPO implements Transformer {

	GestionSistemaBI sistemaBI = null;
	GestionConsultasBI consultasBI = null;
	GestionSalasConsultaBI salasBI = null;
	Locale locale = null;

	ConsultaToPO(Locale locale, GestionSistemaBI sistemaBI,
			GestionConsultasBI consultasBI, GestionSalasConsultaBI salasBI) {
		this.sistemaBI = sistemaBI;
		this.consultasBI = consultasBI;
		this.salasBI = salasBI;
		this.locale = locale;
	}

	public Object transform(Object vo) {
		ConsultaPO po = new ConsultaPO(locale, sistemaBI, consultasBI, salasBI);
		POUtils.copyVOProperties(po, vo);
		return po;
	}

	public static ConsultaToPO getInstance(Locale locale,
			ServiceRepository services) {
		return new ConsultaToPO(locale, services.lookupGestionSistemaBI(),
				services.lookupGestionConsultasBI(),
				services.lookupGestionSalasBI());
	}
}