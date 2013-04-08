package solicitudes.view;

import java.util.Locale;

import org.apache.commons.collections.Transformer;

import common.bi.ServiceRepository;
import common.view.POUtils;

public class DetalleToPO implements Transformer {

	// GestionSistemaBI sistemaBI = null;

	// GestionPrestamosBI prestamosBI = null;
	// GestionConsultasBI consultasBI = null;
	// GestionRelacionesEntregaBI relacionesBI = null;
	// GestionCuadroClasificacionBI cuadroBI = null;
	// GestionDescripcionBI descripcionBI = null;
	boolean inicializarDatos = false;
	ServiceRepository services = null;
	Locale locale = null;

	// DetalleToPO(GestionSistemaBI sistemaBI, GestionPrestamosBI prestamosBI,
	// GestionConsultasBI consultasBI, GestionRelacionesEntregaBI relacionesBI)
	// {
	DetalleToPO(Locale locale, ServiceRepository services,
			boolean inicializarDatos) {

		// this.sistemaBI = sistemaBI;
		// this.prestamosBI = services.lookupGestionPrestamosBI();
		// this.consultasBI = services.lookupGestionConsultasBI();
		// this.relacionesBI = services.lookupGestionRelacionesBI();
		// this.cuadroBI = services.lookupGestionCuadroClasificacionBI();
		// this.descripcionBI = services.lookupGestionDescripcionBI();
		this.inicializarDatos = inicializarDatos;
		this.services = services;
		this.locale = locale;
	}

	public Object transform(Object vo) {
		// DetallePO po = new DetallePO(sistemaBI, prestamosBI, consultasBI,
		// relacionesBI);
		DetallePO po = new DetallePO(locale, services);
		POUtils.copyVOProperties(po, vo);

		if (po != null && inicializarDatos)
			po.getNombreRangos();

		return po;
	}

	public static DetalleToPO getInstance(Locale locale,
			ServiceRepository services, boolean inicializarDatos) {
		// return new DetalleToPO(services.lookupGestionSistemaBI(),
		// services.lookupGestionPrestamosBI(),
		// services.lookupGestionConsultasBI(),
		// services.lookupGestionRelacionesBI());
		return new DetalleToPO(locale, services, inicializarDatos);
	}

}
